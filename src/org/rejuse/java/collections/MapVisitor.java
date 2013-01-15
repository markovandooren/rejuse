package org.rejuse.java.collections;


import java.util.ConcurrentModificationException;
import java.util.Map;


/**
 * <p>A visitor of maps. The code in visit is performed for each element
 * pair in the visited map.</p>
 *
 * <center><img src="doc-files/MapVisitor.png"/></center>
 *
 * <p>MapVisitor is very much like <code><a href="Visitor.html">Visitor</a></code>.
 * The main difference is that the <code><a href="MapVisitor.html#visit(java.lang.Object,
 * java.lang.Object)">visit</a></code> now has 2 arguments: a key and a value.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Jan Dockx
 * @author  Jamal Said
 * @author  Pieter Bekaert
 * @author  Marko van Dooren
 * @release $Name$
 */
public abstract class MapVisitor implements MapOperator {
  
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
	 @*/
  public abstract void visit(Object key, Object value);
  
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
	 @ // code>public void visit(Object)</code> is called for all elements of <map>
	 @ post (* for all k, for all v: (map.containsKey(k) and (map.get(k) = v) ==> visit(k, v) *);
   @ // The changes are applied to the given set and it is returned afterwards.
   @ post \result == map;
	 @
	 @ signals (ConcurrentModificationException) (* The collection was modified while performing the visit. *);
   @*/
  public final Map applyTo(Map map) throws ConcurrentModificationException {
    if (map != null) {
      new Visitor() {
        public void visit(Object element) {
          Map.Entry entry = (Map.Entry)element;
          MapVisitor.this.visit(entry.getKey(), entry.getValue());
        }
      }.applyTo(map.entrySet());
    }
    return map;
  }
}

