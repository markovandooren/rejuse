package be.kuleuven.cs.distrinet.rejuse.property;

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
		_first = first;
		_second = second;
	}

	private P _first;

	public P first() {
		return _first;
	}

	private P _second;

	public P second() {
		return _second;
	}

	

}