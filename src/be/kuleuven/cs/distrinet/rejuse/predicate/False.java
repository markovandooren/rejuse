package be.kuleuven.cs.distrinet.rejuse.predicate;

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
public class False<T> extends SafePredicate<T> {

  /* The revision of this class */
    public final static String CVS_REVISION ="$Revision$";
    
    /*@
	 @ also public behavior
	 @
	 @ post \result == false;
	 @*/
	public /*@ pure @*/ boolean eval(T object) {
		return false;
	}
	
}