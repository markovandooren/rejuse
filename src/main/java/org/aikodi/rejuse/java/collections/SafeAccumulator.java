package org.aikodi.rejuse.java.collections;

import java.util.Collection;
import java.util.ConcurrentModificationException;


public abstract class SafeAccumulator<E,A> extends Accumulator<E,A> {

	@Override
	public abstract A accumulate(E element, A acc);

	@Override
	public abstract A initialAccumulator();

  public final /*@ pure @*/ A accumulate(Collection<E> collection) throws ConcurrentModificationException {
    try {
    	return super.accumulate(collection);
    } catch(RuntimeException exc) {
    	throw exc;
    } catch(Exception exc) {
    	throw new Error(exc);
    }
  }
}
