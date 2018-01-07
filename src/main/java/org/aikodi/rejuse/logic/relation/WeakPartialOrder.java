package org.aikodi.rejuse.logic.relation;


/**
 * A class of weak partial orders. In a partial order, the result
 * of a comparison of two elements a and b is either
 * a <= b, a = b, b <= a, or a and b are unrelated.
 * 
 * <ul>
 *   <li>a <= b if and only if contains(a,b)</li> 
 *   <li>a >= b if and only if contains(b,a)</li>
 *   <li>a = b if and only if contains(a,b) and contains(b,a)</li>
 *   <li>a and b are unrelated if not contains(a,b) and not contains(b,a).</li>
 * </ul>
 * 
 * @author Marko van Dooren
 *
 * @param <E> The type of the elements in the relation.
 */
public interface WeakPartialOrder<E> extends PartialOrder<E> {

	/**
	 * Determine whether the two given objects are equal according to this
	 * weak partial order.
	 */
 /*@
   @ public behavior
   @
   @ post \result == contains(first, second) && contains(second, first);
   @*/
  default boolean equal(E first, E second) throws Exception {
    return contains(first, second) && contains(second, first);
  }

  /**
   * Return the strict partial order corresponding to this
   * weak partial order.
   * 
   * For the resulting strict partial order, a pair (a,b) is
   * in the relation if and only if the pair is in this weak
   * partial order, and a and b are not equal according to this
   * weak partial order.
   * 
   * In terms of logic: a < b <=> a <= b ^ (! b <= a).
   * 
   * In terms of method invocations: result.contains(a,b) == contains(a,b) &&
   * ! contains(b,a).
   */
  default StrictPartialOrder<E> strictOrder() {
    return new StrictPartialOrder<E>() {

      @Override
      public boolean contains(E first, E second) throws Exception {
        return WeakPartialOrder.this.contains(first,second) && (! WeakPartialOrder.this.contains(second,first));
      }

      @Override
      public boolean equal(E first, E second) throws Exception {
        return WeakPartialOrder.this.equal(first,second);
      }
    };
  }
}
