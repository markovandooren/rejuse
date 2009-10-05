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
	public Conflict(Prop<E> first, Prop<E> second) {
		_first = first;
		_second = second;
	}

	private Prop<E> _first;

	public Prop<E> first() {
		return _first;
	}

	private Prop<E> _second;

	public Prop<E> second() {
		return _second;
	}

	

}