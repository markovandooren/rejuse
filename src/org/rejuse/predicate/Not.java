package org.rejuse.predicate;

import java.util.List;
import java.util.ArrayList;

/**
 * <p>A class of predicates that negate another predicate.</p>
 *
 * <center><img src="doc-files/Not.png"/></center>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class Not<T> extends AbstractPredicate<T> {
	
  /* The revision of this class */
  public final static String CVS_REVISION ="$Revision$";

	/**
	 * Initialize a new Not with the given predicate.
	 *
	 * @param predicate
	 *        The predicate to be negated.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre predicate != null;
	 @
	 @ post getPredicate() == predicate;
	 @*/
	public Not(Predicate<T> predicate) {
		_predicate = predicate;
	}

  /*@
	  @ also public behavior
		@
		@ post \result == ! getPredicate().eval(object);
		@*/
	public /*@ pure @*/ boolean eval(T object) throws Exception {
		return ! _predicate.eval(object);
	}

	/**
	 * Return the negated predicate.
	 */
 /*@
   @ public behavior
 	 @
	 @ post \result == getSubPredicates().get(1);
	 @*/
	public /*@ pure @*/ Predicate<T> getPredicate() {
		return _predicate;
	}

	/**
	 * See superclass
	 */
	public /*@ pure @*/ List<Predicate<T>> getSubPredicates() {
		List<Predicate<T>> result = new ArrayList<Predicate<T>>();
		result.add(_predicate);
		return result;
	}

	/**
	 * See superclass
	 */
 /*@
   @ also public behavior
   @
   @ post \result == 1;
   @*/
	public /*@ pure @*/ int nbSubPredicates() {
		return 1;
	}

 /*@
	 @ private invariant _predicate != null;
	 @ private invariant _predicate != this;
	 @*/
	private Predicate<T> _predicate;
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
