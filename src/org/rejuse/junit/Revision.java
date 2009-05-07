package org.rejuse.junit;

/**
 * <p>A class of Revisions of something.</p>
 *
 * <p>An example of a revision is software revison. A revision consists of a sequence of numbers.
 * The first three numbers are called "major", "minor" and "micro". Further numbers have no name.</p>
 *
 * <center>
 *   <img src="doc-files/Revision.png"/>
 * </center>
 *
  * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public interface Revision {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * Return the major number of this Revision.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result == getNumber(1);
	 @*/
	public /*@ pure @*/ int getMajor();

	/**
	 * Return the minor number of this Revision.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result == getNumber(2);
	 @*/
	public /*@ pure @*/ int getMinor();

	/**
	 * Return the micro number of this Revision.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result == getNumber(3);
	 @*/
	public /*@ pure @*/ int getMicro();

	/**
	 * Return the index'th number of this Revision.
	 *
	 * @param index
	 * 				The index of the requested number.
	 */
 /*@
	 @ pre index >= 1;
	 @
	 @ post \result >=0;
	 @ post (index > length()) ==> (\result == 0);
	 @*/
	public /*@ pure @*/ int getNumber(int index);

	/**
	 * Return the number of numbers in this Revision.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result >= 1;
	 @*/
	public /*@ pure @*/ int length();

	/**
	 * Check whether or not this revision is equal to another
	 *
	 * @param other
	 *        The other revision.
	 */
 /*@
	 @ also public behavior
	 @
	 @ post (! (other instanceof Revision)) ==> (\result == false);
	 @ post (other instanceof Revision) ==> \result == 
	 @                                      (\forall int i; i>=1;  
	 @                                        getNumber(i) == ((Revision)other).getNumber(i));
	 @*/
	public /*@ pure @*/ boolean equals(Object other);
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
