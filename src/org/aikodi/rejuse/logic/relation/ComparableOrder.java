package org.aikodi.rejuse.logic.relation;

public class ComparableOrder<T extends Comparable<T>> extends StrictPartialOrder<T> {

	@Override
	public boolean contains(T first, T second) throws Exception {
		return (first != null && second != null) && (first.compareTo(second) < 0);
	}

	@Override
	public boolean equal(T first, T second) throws Exception {
		return (first == null && second == null) || 
				   ((first != null && second != null) && (first.compareTo(second) == 0));
	}

}
