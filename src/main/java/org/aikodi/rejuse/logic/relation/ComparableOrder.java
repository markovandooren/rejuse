package org.aikodi.rejuse.logic.relation;

public interface ComparableOrder<T extends Comparable<T>> extends StrictPartialOrder<T> {

	@Override
	default boolean contains(T first, T second) throws Exception {
		return (first != null && second != null) && (first.compareTo(second) < 0);
	}

	@Override
	default boolean equal(T first, T second) throws Exception {
		return (first == null && second == null) || 
				   ((first != null && second != null) && (first.compareTo(second) == 0));
	}

}
