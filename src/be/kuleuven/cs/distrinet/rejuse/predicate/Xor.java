package be.kuleuven.cs.distrinet.rejuse.predicate;

/**
 * <p>A class of predicates that evaluates to the <code>xor</code> of two
 * other predicates.</p>
 *
 * <center><img src="doc-files/Xor.png"/></center>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class Xor<T> extends BinaryPredicate<T> {
	
  /* The revision of this class */
    public final static String CVS_REVISION ="$Revision$";
    
  /**
	 * Initialize a new Xor with the given predicates.
	 *
	 * @param first
	 *        The first predicate.
	 * @param second
	 *        The second predicate.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre first != null;
	 @ pre second != null;
	 @
	 @ post getFirst() == first;
	 @ post getSecond() == second;
	 @*/
	public Xor(Predicate<T> first, Predicate<T> second) {
		super(first, second);
	}

  /*@
	  @ also public behavior
		@
		@ post \result == getFirst().eval(object) ^ getSecond().eval(object);
		@*/
	public /*@ pure @*/ boolean eval(T object) throws Exception {
		return (getFirst().eval(object) ^ getSecond().eval(object));
	}
}

