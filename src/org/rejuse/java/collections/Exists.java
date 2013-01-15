package org.rejuse.java.collections;


import java.util.*;


/**
 * <p>A boolean exists operator.</p>
 * <center>
 *   <img src="doc-files/Exists.png"/>
 * </center>
 * <p>A convenience accumulator of collections that checks whether some
 * element of a collection satisfies the criterion defined in the abstract 
 * method <code>public boolean criterion(Object element)</code>.</p>
 *
 * <p>As with <code>Accumulator</code>, this class can best be used as an anonymous
 * inner class.</p>
 *<pre><code>
 *boolean bool =
 *  new Exists() {
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
 * @release $Name$
 */
public abstract class Exists extends BooleanAccumulator {
  
	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
   * Check whether the given object satisfies the criterion
   * for this Exists accumulator
   */
 /*@
   @ public behavior
   @
   @ // criterion should be consistent with equals
   @ post (\forall Object o1;;
   @        (\forall Object o2; o2.equals(o1);criterion(o1) == criterion(o2)));
	 @
   @*/
  public abstract /*@ pure @*/ boolean criterion(Object element);
  
 /*@
   @ also public behavior
   @
   @ // Returns false
   @ post \result == false;
   @*/
  public /*@ pure @*/ boolean initialAccumulator() {
    return false;
  }
  
 /*@
   @ also public behavior
   @
   @ post \result == acc || criterion(element);
   @*/
  public /*@ pure @*/ boolean accumulate(Object element, boolean acc) {
    return (criterion(element) || acc);
  }
  
 /*@
   @ also public behavior
   @
   @ post \result == (\exists Object o;collection.contains(o);criterion(o));
   @*/
  public /*@ pure @*/ boolean in(Collection collection) {
    boolean acc = false;
		if(collection != null) {
    	Iterator iter = collection.iterator();
    	// variant : the number of elements in the explicit collection that
    	//           have not yet been checked.
    	// invariant : acc == false if none of the previous elements in the
    	//             explicit collection satisfies the criterion.
    	//             Otherwise, it is true.
    	while (iter.hasNext() && (! acc)) {
      	acc = accumulate(iter.next(), acc);
    	}
    }
    return acc;
  }
}

