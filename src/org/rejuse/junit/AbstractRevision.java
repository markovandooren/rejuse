package org.rejuse.junit;

/**
 * A class that implements the non-basic methods of Revision. Subclasses only need
 * implements <a href="Revision.html#getNumber(int)">getNumber(int)</a> and 
 * <a href="Revision.html#length()">length()</a>.
 *
 * <center>
 *   <img src="doc-files/AbstractRevision.png"/>
 * </center>
 *
  * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public abstract class AbstractRevision implements Revision {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * See superclass
	 */
	public /*@ pure @*/ int getMajor() {
		return getNumber(1);
	}

	/**
	 * See superclass
	 */
	public /*@ pure @*/ int getMinor() {
		return getNumber(2);
	}

	/**
	 * See superclass
	 */
	public /*@ pure @*/ int getMicro() {
		return getNumber(3);
	}

	/**
	 * See superclass.
	 */
	public /*@ pure @*/ boolean equals(Object other) {
		if (!(other instanceof Revision)) {
			return false;
		}
		Revision otherRevision=(Revision)other;
		int nb=Math.max(length(), otherRevision.length());
		for(int i=1; i<=nb; i++) {
			if(getNumber(i) != otherRevision.getNumber(i)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * See superclass
	 */
	public /*@ pure @*/ String toString() {
		// Use a StringBuffer in order not to be too inefficient
		StringBuffer buffer = new StringBuffer();
		// Treat the first number separately, it is not preceeded
		// by a "."
		buffer.append(String.valueOf(getNumber(1)));
		// cache length() for efficiency.
		int length=length();
		// append all numbers other than the first one
		// by appending a ".", and then the string representation
		// of the number.
		for(int i=2; i<= length; i++) {
			buffer.append(".");
			buffer.append(String.valueOf(getNumber(i)));
		}
		return buffer.toString();
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
