package org.rejuse.io.fileset;

import java.io.File;

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
public abstract class BaseDirPredicate extends FilePredicate {

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
