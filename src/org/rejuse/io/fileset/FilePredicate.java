package org.rejuse.io.fileset;

import org.rejuse.predicate.AbstractPredicate;
import java.io.File;

/**
 * <p>A class of predicate meant to check properties of files.</p>
 *
 * <center>
 *   <img src="doc-files/FilePredicate.png"/>
 * </center>
 *
 * <p>All FilePredicates will return <code>false</code> if the argument passed to 
 * <a href="FilePredicate.html#eval(java.lang.Object)"><code>eval()</code></a> is not a
 * File.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public abstract class FilePredicate extends AbstractPredicate<File> {
	
// /*@
//	 @ also public behavior
//	 @
//	 @ // If the given object is not a file (or is a null reference),
//	 @ // <code>false</code> is returned.
//	 @ post ! (object instanceof File) ==> \result == false;
//	 @ // If the given object is a non-null File, the result of
//	 @ // evalFile is returned.
//	 @ post object instanceof File ==> \result == evalFile((File)object);
//	 @*/
//	public /*@ pure @*/ boolean eval(File object) throws Exception {
//			return evalFile(object);
//	}
//
//	/**
//	 * Evaluate this FilePredicate for the given file
//	 *
//	 * @param file
//	 *        The file to evaluate this FilePredicate with
//	 */
// /*@
//	 @ public behavior
//	 @
//	 @ pre file != null;
//	 @
//	 @ post \result == true | \result == false;
//	 @*/
//	public abstract /*@ pure @*/ boolean evalFile(File file) throws Exception;

}