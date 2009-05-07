package org.rejuse.junit;

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
