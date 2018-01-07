package org.aikodi.rejuse.property;

import org.aikodi.contract.Contract;

/**
 * A class representing a conflict between two properties.
 * 
 * @author Marko van Dooren
 *
 * @param <P> The type of the properties
 */
public class Conflict<P extends Property<?,P>> {
	
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
	public Conflict(P first, P second) {
		Contract.requireNotNull(first);
		Contract.requireNotNull(second);
		_first = first;
		_second = second;
	}

	private P _first;

	/**
	 * @return The first property of the conflict.
	 */
	public P first() {
		return _first;
	}

	private P _second;

	/**
	 * @return The second property of the conflict.
	 */
	public P second() {
		return _second;
	}

}