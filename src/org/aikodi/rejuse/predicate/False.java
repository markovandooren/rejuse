package org.aikodi.rejuse.predicate;

import org.aikodi.rejuse.action.Nothing;

/**
 * <p>A special total predicate that always returns <code>false</code> for 
 * <a href="False.html#eval(java.lang.Object)">eval()<code></code></a>.</p>
 *
 * <center><img src="doc-files/False.png"/></center>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class False extends UniversalPredicate<Object,Nothing> {

 public False() {
		super(Object.class);
	}

	/*@
	 @ also public behavior
	 @
	 @ post \result == false;
	 @*/
	public /*@ pure @*/ boolean uncheckedEval(Object object) {
		return false;
	}
	
	@Override
	public String toString() {
		return "false";
	}

	@Override
		public Predicate<Object, Nothing> and(
				Predicate<? super Object, ? extends Nothing> other) {
			return this;
		}
	
	@Override
		public UniversalPredicate<Object, Nothing> or(
				UniversalPredicate other) {
			return other;
		}
	
	@Override
		public Predicate<Object, Nothing> or(
				Predicate other) {
			return other;
		}
}