package org.rejuse.java.comparator;

import java.util.Comparator;

/**
 * An ExtendedComparator that inverts another Comparator.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Tom Schrijvers
 * @release $Name$
 */
public class InverseComparator extends ExtendedComparator {

  /* The revision of this class */
    public final static String CVS_REVISION ="$Revision$";

	/**
	 * The inverted comparator.
	 */
 /*@
	 @ private invariant _comparator != null;
	 @*/
  private final Comparator _comparator;

	/**
	 * Initialize a new InverseComparator with the given comparator.
	 * 
	 * @param comparator
	 *        The comparator to invert.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre comparator != null;
	 @
	 @ post getComparator() == comparator;
	 @*/
	public /*@ pure @*/ InverseComparator(Comparator comparator) {
		_comparator = comparator;
	}

	/**
	 * Return the comparator inverted by this.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @*/
	public /*@ pure @*/ Comparator getComparator() {
		return _comparator;
	}

	/**
	 * Return the inverse of the what the
	 * inverted comparator would return.
	 *
	 * @param first
	 *        The left-hand side object
	 * @param second
	 *        The right-hand side object
	 */
 /*@
	 @ also public behavior
	 @
	 @ post \result == -getComparator().compare(first, second);
	 @*/
	public /*@ pure @*/ int compare(Object first, Object second) {
		return -_comparator.compare(first, second);
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
