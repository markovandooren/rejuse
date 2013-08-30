package be.kuleuven.cs.distrinet.rejuse.predicate;

import be.kuleuven.cs.distrinet.rejuse.action.Nothing;

/**
 * <p>A special total predicate that always returns <code>true</code> for 
 * <a href="True.html#eval(java.lang.Object)">eval()<code></code></a>.</p>
 *
 * <center><img src="doc-files/True.png"/></center>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class True extends UniversalPredicate<Object,Nothing> {

	public True() {
		super(Object.class);
	}

	/*@
	 @ also public behavior
	 @
	 @ post \result == true;
	 @*/
	public /*@ pure @*/ boolean uncheckedEval(Object object) {
		return true;
	}
	
	@Override
	public String toString() {
		return "true";
	}
	
	@Override
	public Predicate<Object, Nothing> or(
			Predicate<? super Object, ? extends Nothing> other) {
		return this;
	}
	
	@Override
	public UniversalPredicate<Object, Nothing> and(
			UniversalPredicate other) {
		return other;
	}
	
	@Override
	public Predicate<Object, Nothing> and(
			Predicate other) {
		return other;
	}
}

