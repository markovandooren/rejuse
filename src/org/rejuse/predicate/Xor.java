package org.rejuse.predicate;

/**
 * <p>A class of predicates that evaluates to the <code>xor</code> of two
 * other predicates.</p>
 *
 * <center><img src="doc-files/Xor.png"/></center>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class Xor<T> extends BinaryPredicate<T> {
	
  /* The revision of this class */
    public final static String CVS_REVISION ="$Revision$";
    
  /**
	 * Initialize a new Xor with the given predicates.
	 *
	 * @param first
	 *        The first predicate.
	 * @param second
	 *        The second predicate.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre first != null;
	 @ pre second != null;
	 @
	 @ post getFirst() == first;
	 @ post getSecond() == second;
	 @*/
	public Xor(Predicate<T> first, Predicate<T> second) {
		super(first, second);
	}

  /*@
	  @ also public behavior
		@
		@ post \result == getFirst().eval(object) ^ getSecond().eval(object);
		@*/
	public /*@ pure @*/ boolean eval(T object) throws Exception {
		return (getFirst().eval(object) ^ getSecond().eval(object));
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
