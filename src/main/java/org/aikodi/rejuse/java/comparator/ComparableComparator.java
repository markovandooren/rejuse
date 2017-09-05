package org.aikodi.rejuse.java.comparator;

/**
 * <p>Trivial Comparator that uses the Comparable interface
 * of objects to compare.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Tom Schrijvers
 * @author  Marko van Dooren
 * @release $Name$
 */
public class ComparableComparator extends ExtendedComparator {

  /* The revision of this class */
    public final static String CVS_REVISION ="$Revision$";

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
	public /*@ pure @*/ int compare(Object o1, Object o2) {
		return ((Comparable)o1).compareTo(o2);
	}
	
}

