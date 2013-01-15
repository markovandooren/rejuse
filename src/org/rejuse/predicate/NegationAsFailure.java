package org.rejuse.predicate;

import java.util.List;
import java.util.ArrayList;
/*@ model import org.jutil.java.collections.Collections; @*/

/**
 * <p>A class of predicates that wrap another predicate and treat the 
 * occurrence of an exception in
 * <a href="NegationAsFailure.html#eval(java.lang.Object)"><code>eval()</code></a>
 * as <code>false</code>.</p>
 *
 * <center><img src="doc-files/NegationAsFailure.png"/></center>
 *
 * <p>Note that if you wrap a NegationAsFailure object in another predicate,
 * you will not get the same result as when switching the wrapping predicate
 * and the NegationAsFailure. E.g. <code>new Not(new NegationAsFailure(predicate))</code>
 * will not always return the same value as <code>new NegationAsFailure(new Not(predicate))</code>.</p>
 *
 * @author  Marko van Dooren
 */
public class NegationAsFailure<T> extends SafePredicate<T> {
	
  /* The revision of this class */
    public final static String CVS_REVISION ="$Revision$";
    
    /**
	 * Initialize a new NegationAsFailure with the given predicate.
	 *
	 * @param predicate
	 *        The predicate to be wrapped.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre predicate != null;
	 @
	 @ post getPredicate() == predicate;
	 @*/
	public NegationAsFailure(Predicate<T> predicate) {
		_predicate = predicate;
	}

  /*@
	  @ also public behavior
		@
		@ post getPredicate().isValidElement(object) ==> \result == getPredicate().eval(object);
		@ post ! getPredicate().isValidElement(object) ==> false;
		@*/
	public /*@ pure @*/ boolean eval(T object) {
		//FIXME: the spec is not correct, FIX IT
    //MVD: I can't, not without anchored exceptions
		try {
			return _predicate.eval(object);
		}
		catch(Exception exc) {
			return false;
		}
	}

	/**
	 * Return the wrapped predicate.
	 */
 /*@
   @ public behavior
 	 @
	 @ post \result == getSubPredicates().get(1);
	 @*/
	public /*@ pure @*/ Predicate<T> getPredicate() {
		return _predicate;
	}

 /*@
	 @ also public behavior
	 @
	 @ post Collections.containsExplicitly(\result, getPredicate());
	 @ post \result.size() == 1;
	 @*/
	public /*@ pure @*/ List<Predicate<T>> getSubPredicates() {
		List<Predicate<T>> result = new ArrayList<Predicate<T>>();
		result.add(_predicate);
		return result;
	}

  /**
	 * See superclass
	 */
	public /*@ pure @*/ int nbSubPredicates() {
		return 1;
	}

  /**
   * The wrapped predicate.
   */
 /*@
	 @ private invariant _predicate != null;
	 @ private invariant _predicate != this;
	 @*/
	private Predicate<T> _predicate;
}

