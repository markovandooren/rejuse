package org.aikodi.rejuse.java.collections;


import java.util.Iterator;
import java.util.Collection;
import java.util.ConcurrentModificationException;

/**
 * <p>A class of objects that accumulate over a collection.</p>
 *
 * <center>
 * 	<img src="doc-files/Accumulator.png"/>
 * </center>
 * 
 * <p>The Accumulator class is the general class for visiting a collection
 * while passing some object to the next element.  The original collection remains
 * unchanged. In relational algebra, this is aggregation.</p>
 * <p>The initial object is returned by the <code><a  href="Accumulator.html#initialAccumulator()">initialAccumulator()</a></code>
 * method and passed to the <code><a
 * href="Accumulator.html#accumulate(java.lang.Object,
 * java.lang.Object)">accumulate(Object element, Object acc)</a></code> method.
 * This method then returns the object to be given to the next element and so
 * on. The object returned by the visit of the last element of the collection
 * is returned to the user. The accumulation is started by invoking the
 * <code><a
 * href="Accumulator.html#accumulate(java.util.Collection)">accumulate(Collection
 * collection)</a></code> method.</p>
 * 
 * <p>An example of an accumulation is shown below. It is used to calculate the
 * state of a list of components based on the state of its components. The
 * traditional code is also shown.</p>
 * 
 * <p><b><underline>Accumulation the traditional way.</underline></b></p>
 * <pre><code>
 * Iterator iter = collection.iterator();
 * MyType myAccumulator = (* initial value *);
 * while(iterator.hasNext()) {
 *   MyType element = (MyType) iter.next();
 *   myAccumulator = (* accumulation of element and myAccumulator *);
 * }
 * </code></pre>
 * 
 * <p><b><underline>Accumulation using the <code>Accumulator</code> class.</underline></b></p>
 * <pre><code>
 * MyType myAccumulator = 
 *     (MyType) new Accumulator() {
 *       public Object initialAccumulator() {
 *         return (* initial value *);
 *       }
 *
 *       public Object accumulate(Object element, Object acc) {
 *         return (* accumulation of element and myAccumulator *);
 *       }
 *     }.accumulate(collection);
 * </code></pre>
 * <p><b><underline>The same, but now with specifications.</underline></b></p>
 * <pre><code>
 * MyType myAccumulator = 
 *     (MyType) new Accumulator() {
 *      /oo
 *        o also public behavior
 *        o
 *        o post (* preconditions for accumulate *);
 *        o
 *        o public model boolean isValidElement(Object element);
 *        oo/
 *
 *      /oo
 *        o public behavior
 *        o
 *        o post \result = (* initial value *);
 *        oo/
 *       public Object initialAccumulator() {
 *         return (* initial value *);
 *       }
 *
 *      /oo
 *        o public behavior
 *        o
 *        o post \result = (* accumulation of element and myAccumulator *);
 *        oo/
 *       public Object accumulate(Object element, Object acc) {
 *         return (* accumulation of element and myAccumulator *);
 *       }
 *     }.accumulate(collection);
 * </code></pre>
 *
 * <p>The Jutil.org version requires slightly more code, but is
 * easier to understand than the traditional code because there are no control
 * statements anymore.<p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Jan Dockx
 * @author  Marko van Dooren
 * @release $Name$
 */
public abstract class Accumulator<E,A> {
  
	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /**
   * <p>Subclasses should implement this method to return the initialized accumulator.
   * This method is called at the start of the accumulation. The result will be used
   * in the application of <code>public Object accumulate(Object element, Object acc)</code>
   * for the first element.</p>
   */
  public abstract /*@ pure @*/ A initialAccumulator();
  
  /**
   * <p>This method is called for each element in the collection we are accumulating.
   * Subclasses should implement this method to process <element> and accumulate
   * the result in acc.</p>
   * <p>The result is the accumulator that will be used for the next element of the
   * collection to process.</p>.
   *
   * //MvDMvDMvD : names aren't clear : accumulator is not an Accumulator
   *
   * @param  element
   *         The object the method will process and change the accumulator with.
   * @param  acc
   *         The accumulator for the accumulation.
   *         For the first element to be processed, the result of initialAccumulator
   *         is used. For the other elements, the result of this method applied on
   *         the previous element is used.
   */
 /*@
	 @ public behavior
	 @
   @ pre isValidElement(element);
   @*/
  public abstract /*@ pure @*/ A accumulate(E element, A acc) throws Exception;
  

  /**
   * <p>Perform the accumulation defined in
   * <code>public Object accumulate(Object element, Object acc)</code> for each
   * element of <code>collection</code>. For the first element, the object returned by
   * <code>public Object initialAccumulator()</code> is used as accumulator.
   * For the other elements, the result of the application of
   * <code>public Object accumulate(Object element, Object acc)</code> on the
   * previous element is used as accumulator.</p>
   * <p>The contents of <code>collection</code> is not changed.</p>
   * <p>The result of this method is the object returned by the application of
   * <code>public Object accumulate(Object element, Object acc)</code> on the
   * last element of the collection to be processed.</p>
   *
   * @param  collection
   *         The collection to perform this accumulation on. It will not be changed.
   *         This can be null, in which the initial accumulator is returned.
   * @result The accumulator returned by the final call of accumulate.
   */
 /*@
   @ public behavior
   @
   @ pre (\forall Object o; collection.contains(o); isValidElement(o));
	 @
	 @ post (* the result of the accumulation is returned *);
	 @ post collection == null ==> \result == initialAccumulator();
	 @
	 @ signals (ConcurrentModificationException) (* The collection was modified while accumulating *);
   @*/
  public /*@ pure @*/ A accumulate(Collection<E> collection) throws ConcurrentModificationException, Exception {
    A acc = initialAccumulator();
    if (collection != null) {
      Iterator<E> iter = collection.iterator();
      while (iter.hasNext()) {
        acc = accumulate(iter.next(), acc);
      }
    }
    return acc;
  }
}
