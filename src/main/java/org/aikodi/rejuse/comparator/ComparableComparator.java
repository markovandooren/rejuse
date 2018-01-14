package org.aikodi.rejuse.comparator;

/**
 * <p>Trivial Comparator that uses the Comparable interface
 * of objects to compare.</p>
 *
 * @author  Tom Schrijvers
 * @author  Marko van Dooren
 */
public class ComparableComparator<T extends Comparable<T>> extends ExtendedComparator<T> {

	/**
	 * See <a href="http://java.sun.com/j2se/1.4/docs/api/java/util/Comparator.html#compare(java.lang.Object, java.lang.Object)">superclass</a>.
	 */
   /*@
	 @ also public behavior
	 @
	 @ pre o1 instanceof Comparable;
	 @
	 @ post \result == ((Comparable)o1).compareTo(o2);
	 @*/
	public /*@ pure @*/ int compare(T o1, T o2) {
		return o1.compareTo(o2);
	}
	
}

