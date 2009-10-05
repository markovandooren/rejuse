package org.rejuse.property;

/**
 * A class representing a conflict between two properties.
 * 
 * @author Marko van Dooren
 *
 * @param <E>
 */
public class Conflict<F extends Property<?,F>> {
	
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
	public Conflict(F first, F second) {
		_first = first;
		_second = second;
	}

	private F _first;

	public F first() {
		return _first;
	}

	private F _second;

	public F second() {
		return _second;
	}

	

}