package org.aikodi.rejuse.logic.relation;

import java.util.Collection;

/**
 * A class of partial orders. <b>Note that this class does not specify
 * whether the order is weak or strict. That is done in the corresponding subclasses.</b>
 * 
 * @author Marko van Dooren
 *
 * @param <E>
 */
public interface PartialOrder<E> extends Relation<E> {

  /*@
    @ public behavior
    @
    @ invariant equal(first,second) == equal(second,first).
    @*/
  
  /**
   * If a partial order contains (e1,e2), this means that e1 is smaller than 
   * (or equal to, depending on the strictness) e2.
   */
  boolean contains(E first, E second) throws Exception;

  /**
   * Check whether the given element are equal according to this partial order.
   * Equality is symmetric: equal(first,second) == equal(second,first). 
   * 
   * @param first
   * @param second
   * @return
   * @throws Exception
   */
  boolean equal(E first, E second) throws Exception;

  /**
   * Remove those elements from the given collection that are bigger
   * than another element of the collection.
   */
  default void removeBiggerElements(Collection<E> collection) throws Exception {
    new Relation<E>() {
      public boolean contains(E first, E second) throws Exception {
        return PartialOrder.this.contains(second,first) && (! PartialOrder.this.contains(first,second));
      }
    }.removeLeftOperands(collection);
  }

  /**
   * Remove those elements from the given collection that are smaller
   * than another element of the collection.
   */
  default void removeSmallerElements(Collection<E> collection) throws Exception {
    new Relation<E>() {
      public boolean contains(E first, E second) throws Exception {
            return PartialOrder.this.contains(first,second) && (! PartialOrder.this.contains(second,first));
      }
    }.removeLeftOperands(collection);
  }

}
