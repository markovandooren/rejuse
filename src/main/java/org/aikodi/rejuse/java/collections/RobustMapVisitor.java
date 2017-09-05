package org.aikodi.rejuse.java.collections;


import java.util.ConcurrentModificationException;
import java.util.Map;

/**
 * <p>A robust visitor of maps. The code in visit is performed for each element
 * pair in the visited map.</p>
 *
 * <center>
 *   <img src="doc-files/RobustMapVisitor.png"/>
 * </center>
 *
 * <p>In addition to the functionality of MapVisitor, RobustMapVisitor allows the 
 * <code><a href="RobustMapVisitor.html#visit(java.util.Map)">visit</a></code> method to throw an exception. It also has support for undoing the changes
 * done before the exception occurred.</p>
 * <p>See <code><a href="RobustVisitor.html">RobustVisitor</a> for more
 * information.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public abstract class RobustMapVisitor implements MapOperator {
  
	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /**
   * The code to be applied to all element pairs of a map.
   *
   * @param  key
   *         The key of the element pair the code should be applied to.
   * @param  element
   *         The value of the element pair the code should be applied to.
   */
 /*@
	 @ public behavior
	 @
	 @ pre isValidPair(key, value);
	 @
   @ post (* returns Data which enables to undo what visit has done
   @         in unvisit. ()
   @   
   @ signals (Exception) (* Something went wrong while visiting the elements. *);
   @*/
  public abstract Object visit(Object key, Object value) throws Exception;

  /**
   * This method will be called when the visit method has raised an
   * exception for some key,value pair which was visited after <key>,<value>.
   * The implementation should undo whatever visit did on the <key>,<value> pair.
   * For that, <unvisitData> can be used.
   */  
 /*@
	 @ public behavior
	 @
	 @ pre isValidPair(key, value);
   @ // unvisitData is the data returned by the visit method.
   @ pre (* unvisitData == visit(key,value) *);
   @
   @ post (* the changes on <key>,<value> done by visit are undone *);
   @*/
  public abstract void unvisit(Object key, Object value, Object unvisitData);  
	//MvDMvDMvD visit is a mutator, so we can't use it in the specs.
    
  /**
   * <p>Perform the visitation defined in <code>public void visit(Object)</code>
   * on <map>. The contents of <map> is not changed.</p>
   * <p>The collection is returned, so
   * that further operations can be applied to it inline.</p>
   *
   * @param  map
   *         The collection to perform this visitation on. This can be null.
   */
 /*@
	 @ public behavior
	 @
	 @ pre (\forall Map.Entry entry; map.entrySet().contains(entry);
	 @       isValidPair(entry.getKey(), entry.getValue()));
	 @
   @ // The changes are applied to the given set and it is returned afterwards.
   @ post \result == map;
	 @ post (* <code>public void visit(Object)</code> is called for all elements of
   @         <map>.
   @       | for all k, for all v: (map.containsKey(k) and (map.get(k) = v))
   @             ==> visit(k, v) *);
	 @
	 @ signals (ConcurrentModificationException) (* The collection was modified while visiting the map. *);
	 @ signals (Exception) (* Something has gone wrong while visiting the map. *);
   @*/
  public final Map applyTo(Map map) throws Exception, ConcurrentModificationException {
    if (map != null) {
      new RobustVisitor() {
        public Object visit(Object element) throws Exception{
          Map.Entry entry = (Map.Entry)element;
          return RobustMapVisitor.this.visit(entry.getKey(), entry.getValue());
        }
        
        public void unvisit(Object element, Object undoData) {
          Map.Entry entry = (Map.Entry)element;          
          RobustMapVisitor.this.unvisit(entry.getKey(), entry.getValue(),undoData);
        }
      }.applyTo(map.entrySet());
    }
    return map;
  }
}

