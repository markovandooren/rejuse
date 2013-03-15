package be.kuleuven.cs.distrinet.rejuse.io.fileset;

import java.io.File;

import be.kuleuven.cs.distrinet.rejuse.predicate.AbstractPredicate;

/**
 * <p>A class of <code>FilePredicates</code> that need a base directory
 * in order to be used conveniently.</p>
 *
 * <center>
 *   <img src="doc-files/BaseDirPredicate.png"/>
 * </center>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public abstract class BaseDirPredicate extends AbstractPredicate<File> {

  /* The revision of this class */
    public final static String CVS_REVISION ="$Revision$";

	/**
	 * Initialize a new BaseDirPredicate with the given base directory.
	 *
	 * @param baseDir
	 *        The base directory for the new BaseDirPredicate.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre baseDir != null;
	 @ pre baseDir.isDirectory();
	 @
	 @ post getBaseDir() == baseDir;
	 @*/
	public BaseDirPredicate(File baseDir) {
		_baseDir = baseDir;
	}

	/**
	 * Return the base directory of this BaseDirPredicate.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @ post \result.isDirectory();
	 @*/
	public /*@ pure @*/ File getBaseDir() {
		return _baseDir;
	}

	/**
	 * The base dir of this BaseDirPredicate.
	 */
 /*@
	 @ private invariant _baseDir != null;
	 @ private invariant _baseDir.isDirectory();
	 @*/
	private File _baseDir;
}

