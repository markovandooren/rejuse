package org.rejuse.java.collections;


import java.util.Collection;
import java.util.Iterator;
import java.util.ConcurrentModificationException;


/**
 * <p>A boolean accumulator for collections. </p>
 *
 * <center>
 *   <img src="doc-files/BooleanAccumulator.png"/>
 * </center>
 *
 * <p>BooleanAccumulators process each element of a collection,
 * and add the result of the process to the accumulator. The original collection remains
 * unchanged.</p>
 *
 * <p>Instead of an <code>accumulate(Collection></code> method, it has an <code><a href="BooleanAccumulator.html#in(java.util.Collection)">in(Collection)</a></code> method.
 * That name just seems more convenient.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @author  Jan Dockx
 * @release $Name$
 */
public abstract class BooleanAccumulator implements CollectionOperator {
  
	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
   * <p>Subclasses should implement this method to return the initialized accumulator.
   * This method is called at the start of the accumulation. The result will be used
   * in the application of <code>public boolean accumulate(Object element, boolean acc)</code>
   * for the first element.</p>
   */
  public abstract /*@ pure @*/ boolean initialAccumulator();
  
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
	 @ public behavior
	 @
   @ pre isValidElement(element);
   @*/
  public abstract /*@ pure @*/ boolean accumulate(Object element, boolean acc);
  
  /**
   * <p>Perform the accumulation defined in
   * <code>public boolean accumulate(Object element, boolean acc)</code> for each
   * element of <collection>. For the first element, the object returned by
   * <code>public boolean initialAccumulator()</code> is used as accumulator.
   * For the other elements, the result of the application of
   * <code>public boolean accumulate(Object element, boolean acc)</code> on the
   * previous element is used as accumulator.</p>
   * <p>The contents of <collection> is not changed.</p>
   * <p>The result of this method is the object returned by the application of
   * <code>public boolean accumulate(Object element, boolean acc)</code> on the
   * last element of the collection to be processed.</p>
   *
   * <p>The precondition is variable. Subclasses could strengthen the postcondition
   * of isValidElement. To the user, this means that this method cannot be called
   * safely in a polymorphic context, since she has no idea what the actual
   * precondition will become in subclasses. This can be resolved by making
   * the model method isValidElement final in the polymorph supertype she needs
   * to use.</p>
	 *
   * @param collection
	 *        The collection for which the ForAll operator has to be computed.
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
  public /*@ pure @*/ boolean in(Collection collection) throws ConcurrentModificationException {
    boolean acc = initialAccumulator();
		if(collection != null) {
    	Iterator iter = collection.iterator();
    	while (iter.hasNext()) {
      	acc = accumulate(iter.next(), acc);
    	}
    }
    return acc;
  }
}

