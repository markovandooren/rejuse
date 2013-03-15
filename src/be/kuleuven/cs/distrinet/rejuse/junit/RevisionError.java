package be.kuleuven.cs.distrinet.rejuse.junit;

/**
 * A class of errors indicating the use of a test class when
 * the tested revision is no longer the latest revision of the tested class.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class RevisionError extends Error {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * Initialize a new RevisionError with a null message
	 */
 /*@
	 @ public behavior
	 @
	 @ post getMessage() == null;
	 @*/
	public RevisionError() {
	}

	/**
	 * Initialize a new RevisionError with the given message.
	 *
	 * @param msg
	 *        The message of the RevisionError.
	 */
 /*@
	 @ public behavior
	 @
	 @ post getMessage() == msg;
	 @*/
	public RevisionError(String msg) {
		super(msg);
	}
}

