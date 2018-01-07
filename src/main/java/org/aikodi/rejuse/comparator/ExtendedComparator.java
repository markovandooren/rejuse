package org.aikodi.rejuse.comparator;

import java.util.Comparator;

/**
 * <p>A Comparator with more convenient methods than <code>java.util.Comparator</code>.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Tom Schrijvers
 * @author  Marko van Dooren
 * @release $Name$
 */
public abstract class ExtendedComparator<T> implements Comparator<T> {

  /* The revision of this class */
    public final static String CVS_REVISION ="$Revision$";

	/**
	 * <p>Return the maximum of both objects. In case of a tie, any
	 * one of both can be returned.</p>
	 *
	 * @param first
	 *        The first object
	 * @param second
	 *        The second object
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result == first | \result == second;
	 @ post compare(\result, first) >= 0;
	 @ post compare(\result, second) >= 0;
	 @*/
	public /*@ pure @*/ T max(T first, T second) {
		if (compare(first,second) >= 0) {
			return first;
		}
		return second;
	}
	
	/**
	 * <p>Return the minimum of both objects. In case of a tie, any
	 * one of both can be returned.</p>
	 *
	 * @param first
	 *        The first object
	 * @param second
	 *        The second object
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result == first | \result == second;
	 @ post compare(\result, first) <= 0;
	 @ post compare(\result, second) <= 0;
	 @*/
	public /*@ pure @*/ T min(T first, T second) {
		if (compare(first,second) <= 0) {
			return first;
		}
		return second;
	}

	/**
	 * <p>Check whether or not the first object is greater than
	 * the second one.</p>
	 *
	 * @param first
	 *        The first object
	 * @param second
	 *        The second object
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result == (compare(first, second) > 0);
	 @*/
	public /*@ pure @*/ boolean greater(T first, T second) {
		return (compare(first,second) > 0);
	}

	/**
	 * <p>Check whether or not the first object is smaller than
	 * the second one.</p>
	 *
	 * @param first
	 *        The first object
	 * @param second
	 *        The second object
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result == (compare(first, second) < 0);
	 @*/
	public /*@ pure @*/ boolean smaller(T first, T second) {
		return (compare(first,second) < 0);
	}


	/**
	 * <p>Check whether or not the first object is greater than
	 * or equal to the second one.</p>
	 *
	 * @param first
	 *        The first object
	 * @param second
	 *        The second object
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result == ! smaller(first, second);
	 @*/
	public /*@ pure @*/ boolean notSmaller(T first, T second) {
		return (compare(first,second) >= 0);
	}

	/**
	 * <p>Check whether or not the first object is smaller than
	 * or equal to the second one.</p>
	 *
	 * @param first
	 *        The first object
	 * @param second
	 *        The second object
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result == ! greater(first, second);
	 @*/
	public /*@ pure @*/ boolean notGreater(T first, T second) {
		return (compare(first,second) <= 0);
	}

	/**
	 * @return An extended comparator that gives the inverse results.
	 *         its {@link #compare(Object, Object)} function returns
	 *         the negation of the {@link #compare(Object, Object)} function
	 *         of this object. 
	 */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ post (\forall Object o1, o2; \result.compare(o1, o2) == -compare(o1, o2););
   @*/
	public ExtendedComparator<T> inverse() {
		return new ExtendedComparator<T>() {
			public /*@ pure @*/ int compare(T first, T second) {
				return -ExtendedComparator.this.compare(first, second);
			}
		};
	}
	
	/**
	 * Ensure that the result is an ExtendedComparator.
	 * If the given Comparator is not an ExtendedComparator, it
	 * will be wrapped.
	 *
	 * @param comparator
	 *        The comparator of which one wants an ExtendedComparator
	 *        that behaves the same.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre comparator != null;
	 @
	 @ // The result must behave exactly the same as the given comparator.
	 @ post (\forall Object o1; ;
	 @       (\forall Object o2; ;
	 @         \result.compare(o1, o2) == comparator.compare(o1, o2)));
	 @*/
	public static /*@ pure @*/ <T> ExtendedComparator<T> ensureExtended(Comparator<T> comparator) {
		if (comparator instanceof ExtendedComparator) {
			return (ExtendedComparator<T>) comparator;
		}
		else {
			return new ExtendedComparatorWrapper<>(comparator);
		}
	}

	/**
	 * An ExtendedComparator that wraps around a normal Comparator.
	 */
	private static class ExtendedComparatorWrapper<T> extends ExtendedComparator<T> {

		/**
		 * Initialize a new ExtendedComparator that wraps the given comparator.
		 *
		 * @param comparator
		 *        The Comparator to wrap.
		 */
	 /*@
		 @ public behavior
		 @
		 @ pre comparator != null;
		 @
		 @ post getComparator() == comparator;
		 @*/
		public ExtendedComparatorWrapper(Comparator<T> comparator) {
			if (comparator == null) {
				throw new IllegalArgumentException();
			}
			_comparator = comparator;
		}

		/**
		 * See superclass
		 */
	 /*@
		 @ also public behavior
		 @
		 @ post \result == getComparator().compare(first, second);
		 @*/
		public /*@ pure @*/ int compare(T first, T second) {
			return comparator().compare(first, second);
		}

		/**
		 * Return the wrapped Comparator.
		 */
	 /*@
		 @ public behavior
		 @
		 @ post \result != null;
		 @*/
		public /*@ pure @*/ Comparator<T> comparator() {
			return _comparator;
		}

		/**
		 * The wrapped comparator.
		 */
	 /*@
		 @ private invariant _comparator != null;
		 @*/
		private final Comparator<T> _comparator;
	}
	
}