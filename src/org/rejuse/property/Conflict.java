package org.rejuse.property;

/**
 * A class representing a conflict between two properties.
 * 
 * @author Marko van Dooren
 *
 * @param <E>
 */
public class Conflict<E> {
	
	/**
	 * Initialize a new conflict for the given two properties. The properties must contradict each other.
	 */
 /*@
   @ public behavior
   @
   @ pre first != null;
   @ pre second != null;
   @ pre first.contradicts(second);
   @
   @ post first() == first;
   @ post second() == second;
   @*/
	public Conflict(Property<E> first, Property<E> second) {
		_first = first;
		_second = second;
	}

	private Property<E> _first;

	public Property<E> first() {
		return _first;
	}

	private Property<E> _second;

	public Property<E> second() {
		return _second;
	}

	

}