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
public abstract class FilePredicate extends AbstractPredicate {
	
 /*@
	 @ also public behavior
	 @
	 @ // If the given object is not a file (or is a null reference),
	 @ // <code>false</code> is returned.
	 @ post ! (object instanceof File) ==> \result == false;
	 @ // If the given object is a non-null File, the result of
	 @ // evalFile is returned.
	 @ post object instanceof File ==> \result == evalFile((File)object);
	 @*/
	public /*@ pure @*/ boolean eval(Object object) throws Exception {
		if (! (object instanceof File)) {
			return false;
		}
		else {
			return evalFile((File)object);
		}
	}

	/**
	 * Evaluate this FilePredicate for the given file
	 *
	 * @param file
	 *        The file to evaluate this FilePredicate with
	 */
 /*@
	 @ public behavior
	 @
	 @ pre file != null;
	 @
	 @ post \result == true | \result == false;
	 @*/
	public abstract /*@ pure @*/ boolean evalFile(File file) throws Exception;

}
/*
 * <copyright>Copyright (C) 1997-2001. This software is copyrighted by 
 * the people and entities mentioned after the "@author" tags above, on 
 * behalf of the JUTIL.ORG Project. The copyright is dated by the dates 
 * after the "@date" tags above. All rights reserved.
 * This software is published under the terms of the JUTIL.ORG Software
 * License version 1.1 or later, a copy of which has been included with
 * this distribution in the LICENSE file, which can also be found at
 * http://org-jutil.sourceforge.net/LICENSE. This software is distributed 
 * WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the JUTIL.ORG Software License for more details. For more information,
 * please see http://org-jutil.sourceforge.net/</copyright>
 */
