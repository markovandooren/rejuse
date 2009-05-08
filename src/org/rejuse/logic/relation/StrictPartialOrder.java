package org.rejuse.logic.relation;

/**
 * A class of strict orders. In a strict partial order, the result
 * of a comparison of two elements a and b is either
 * a < b, a = b, b < a, or a and b are unrelated.
 * 
 * <ul>
 * <li>a < b if and only if contains(a,b)</li> 
 * <li>a > b if and only if contains(b,a)</li>
 * <li>a = b if and only if equal(b,a)</li>
 * <li>a and b are unrelated if not contains(a,b) and not contains(b,a).</li>
 * </ul>
 * 
 * 
 * @author Marko van Dooren
 *
 * @param <E>
 */
public abstract class StrictPartialOrder<E> extends PartialOrder<E> {

  /**
   * Return the weak partial order corresponding to this
   * strict partial order.
   * 
   * For the resulting weak partial order, a pair (a,b) is
   * in the relation if and only if the pair is in this strict
   * partial order, or a and b are equal according to this
   * strict partial order.
   * 
   * In terms of logic: a < b <=> a <= b ^ (! b <= a).
   * 
   * In terms of method invocations: result.contains(a,b) == contains(a,b) &&
   * ! contains(b,a).
   */
  public WeakPartialOrder<E> weakOrder() {
    return new WeakPartialOrder<E>() {

      @Override
      public boolean contains(E first, E second) throws Exception {
        return StrictPartialOrder.this.contains(first,second) || StrictPartialOrder.this.equal(first,second);
      }

      @Override
      public boolean equal(E first, E second) throws Exception {
        return StrictPartialOrder.this.equal(first,second);
      }
    };
  }

}
