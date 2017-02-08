package org.aikodi.rejuse.java.collections;


import java.util.Collection;
import java.util.Iterator;

/*@ model import java.util.ConcurrentModificationException; @*/

/**
 * <p>An integer accumulator for collections. </p>
 *
 * <center>
 *   <img src="doc-files/IntegerAccumulator.png"/>
 * </center>
 *
 * <p>IntegerAccumulators process each element of a collection,
 * and update an accumulator while doing so. 
 * The original collection remains unchanged.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Jan Dockx
 * @author  Marko van Dooren
 * @release $Name$
 */
public abstract class IntegerAccumulator implements CollectionOperator {
  
	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /**
   * <p>Subclasses should implement this method to return the initialized accumulator.
   * This method is called at the start of the accumulation. The result will be used
   * in the application of <code>public int accumulate(Object element, int acc)</code>
   * for the first element.</p>
   */
  public abstract /*@ pure @*/ int initialAccumulator();
  
  /**
   * <p>This method is called for each element in the collection we are accumulating.
   * Subclasses should implement this method to process <element> and accumulate
   * the result in acc.</p>
   * <p>The result is the accumulator that will be used for the next element of the
   * collection to process.</p>.
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
   @ // The given element may not be null
   @ pre element != null;
   @*/
  public abstract /*@ pure @*/ int accumulate(Object element, int acc);
  
  /**
   * <p>Perform the accumulation defined in
   * <code>public int accumulate(Object element, int acc)</code> for each
   * element of <collection>. For the first element, the object returned by
   * <code>public int initialAccumulator()</code> is used as accumulator.
   * For the other elements, the result of the application of
   * <code>public int accumulate(Object element, int acc)</code> on the
   * previous element is used as accumulator.</p>
   * <p>The contents of <collection> is not changed.</p>
   * <p>The result of this method is the object returned by the application of
   * <code>public int accumulate(Object element, int acc)</code> on the
   * last element of the collection to be processed.</p>
   *
   * @param  collection
   *         The collection to perform this accumulation on. It will not be changed.
   *         This can be null, in which the initial accumulator is returned.
   */
 /*@
   @ public behavior
   @
   @ pre (\forall Object o; collection.contains(o); isValidElement(o));
	 @
	 @ post (* the result of the accumulation is returned *);
	 @ post collection == null ==> \result == initialAccumulator();
	 @
	 @ signals (ConcurrentModificationException) (* The collection was modified while filtering *);
	 @*/
  public /*@ pure @*/ int in(Collection collection) {
    int acc = initialAccumulator();
    if (collection != null) {
      Iterator iter = collection.iterator();
      while (iter.hasNext()) {
        acc = accumulate(iter.next(), acc);
      }
    }
    return acc;
  }
}

