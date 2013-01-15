package org.rejuse.java.collections;


import java.util.Collection;
import java.util.Iterator;

/**
 * <p>A boolean for-all operator.</p>
 *
 * <center>
 *   <img src="doc-files/ForAll.png"/>
 * </center>
 *
 * <p>A convenience accumulator of collections that checks whether all
 * elements of a collection satisfy the criterion defined in the abstract 
 * method <code>public boolean criterion(Object element)</code>.</p>
 *
 * <p>As with <code>Accumulator</code>, this class can best be used as an anonymous
 * inner class.</p>
 *<pre><code>
 *boolean bool =
 *  new ForAll() {
 *
 *       /oo
 *         o also public behavior
 *         o
 *         o post (* additional precondition for criterion method *)
 *         o
 *         o public model boolean isValidElement(Object element);
 *         o/
 *
 *       /oo
 *         o also public behavior
 *         o
 *         o post \result == ((MyType)element.someProperty() ...)
 *         o/
 *        public boolean criterion(Object element) {
 *          // criterion code
 *        }
 *      }.in(collection);
 *</code></pre>
 * 
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @author  Jan Dockx
 * @release $Name$
 */
public abstract class ForAll extends BooleanAccumulator {

  public final static String CVS_REVISION="$Revision$";
	
  /**
   * Check whether the given object satisfies the criterion
   * for this ForAll accumulator.
   */
 /*@
   @ public behavior
   @
   @ pre isValidElement(element);
   @
   @ // criterion should be consistent with equals; subclass can say more
   @ post (\forall Object o1;;
   @        (\forall Object o2; o2.equals(o1); criterion(o1) == criterion(o2)));
   @*/
  public abstract /*@ pure @*/ boolean criterion(Object element);
  
 /*@
   @ also public behavior
   @
   @ post \result == true;
   @*/
  final public /*@ pure @*/ boolean initialAccumulator() {
    return true;
  }
  
  /*@
    @ also public behavior
    @
    @ post \result == (acc && criterion(element));
    @*/
  final public /*@ pure @*/ boolean accumulate(Object element, boolean acc) {
    return (criterion(element) && acc);
  }
  
 /*@
   @ also public behavior
   @
   @ post \result == (\forall Object o; collection.contains(o); criterion(o));
   @*/
  public /*@ pure @*/ boolean in(Collection collection) {
    boolean acc = true;
		if(collection != null) {
    	Iterator iter = collection.iterator();
    	// variant : the number of elements in the explicit collection that
    	//           have not yet been checked.
    	// invariant : acc == true if all previous elements in the explicit collection
    	//             satisfy the criterion. Otherwise, it is false.
    	while (iter.hasNext() && acc) {
      	acc = accumulate(iter.next(), acc);
    	}
    }
    return acc;
  }
}

