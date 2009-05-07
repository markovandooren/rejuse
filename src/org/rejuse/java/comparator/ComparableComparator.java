package org.rejuse.java.comparator;

/**
 * <p>Trivial Comparator that uses the Comparable interface
 * of objects to compare.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Tom Schrijvers
 * @author  Marko van Dooren
 * @release $Name$
 */
public class ComparableComparator extends ExtendedComparator {

  /* The revision of this class */
    public final static String CVS_REVISION ="$Revision$";

	/**
	 * See <a href="http://java.sun.com/j2se/1.4/docs/api/java/util/Comparator.html#compare(java.lang.Object, java.lang.Object)">superclass</a>.
	 */
 /*@
	 @ also public behavior
	 @
	 @ pre o1 instanceof Comparable;
	 @
	 @ post \result == ((Comparable)o1).compareTo(o2);
	 @*/
	public /*@ pure @*/ int compare(Object o1, Object o2) {
		return ((Comparable)o1).compareTo(o2);
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
