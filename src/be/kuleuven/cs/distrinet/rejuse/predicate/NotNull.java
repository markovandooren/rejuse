package be.kuleuven.cs.distrinet.rejuse.predicate;

/**
 * <p>A special total predicate that checks whether or not objects are different
 * from <code>null</code>.</p>
 *
 * <center><img src="doc-files/NotNull.png"/></center>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class NotNull<T> extends SafePredicate<T> {

  /* The revision of this class */
    public final static String CVS_REVISION ="$Revision$";
    
    /*@
	 @ also public behavior
	 @
	 @ post \result == (object == null);
	 @*/
	public /*@ pure @*/ boolean eval(T object) {
		return (object == null);
	}
}

