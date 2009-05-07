package org.rejuse.predicate;

import java.util.List;
import java.util.ArrayList;
/*@ model import org.jutil.java.collections.Collections; @*/

/**
 * <p>A class of predicates that evaluates an object using two other predicates.</p>
 *
 * <center><img src="doc-files/BinaryPredicate.png"/></center>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public abstract class BinaryPredicate<T> extends AbstractPredicate<T> {
	
  /* The revision of this class */
    public final static String CVS_REVISION ="$Revision$";
    
  /**
	 * Initialize a new BinaryPredicate with the given predicates.
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
	public BinaryPredicate(Predicate<T> first, Predicate<T> second) {
		_first = first;
		_second = second;
	}

	/**
	 * Return the first total predicate of this BinaryPredicate.
	 */
 /*@
   @ public behavior
 	 @
	 @ post \result != null;
	 @*/
	public /*@ pure @*/ Predicate<T> getFirst() {
		return _first;
	}

  /**
   * Set the first predicate of this BinaryPredicate.
   *
   * @param predicate
   *        The new first predicate of this BinaryPredicate.
   */
 /*@
   @ public behavior
   @
	 @ pre predicate != null;
	 @ pre predicate != this;
	 @ pre ! predicate.getSubPredicates().contains(this);
	 @
   @ post getFirst() == predicate; 
   @*/
  public void setFirst(Predicate<T> predicate) {
    _first = predicate;
  }

	/**
	 * Return the second total predicate of this BinaryPredicate.
	 */
 /*@
   @ public behavior
 	 @
	 @ post \result != null;
	 @*/
	public /*@ pure @*/ Predicate<T> getSecond() {
		return _second;
	}

  /**
   * Set the second predicate of this BinaryPredicate.
   *
   * @param predicate
   *        The new second predicate of this BinaryPredicate.
   */
 /*@
   @ public behavior
   @
   @ pre predicate != null;
   @ pre predicate != this;
   @ pre ! predicate.getSubPredicates().contains(this);
   @
   @ post getSecond() == predicate; 
   @*/
  public void setSecond(Predicate<T> predicate) {
    _second = predicate;
  }

 /*@
	 @ also public behavior
	 @
	 @ post Collections.containsExplicitly(\result,getFirst());
	 @ post Collections.containsExplicitly(\result,getSecond());
	 @ post \result.size() == 2;
	 @*/
	public /*@ pure @*/ List<Predicate<T>> getSubPredicates() {
		ArrayList result = new ArrayList();
		result.add(_first);
		result.add(_second);
		return result;
	}

	/**
	 * See superclass
	 */
 /*@
   @ also public behavior
   @
   @ post \result == 2;
   @*/
	public /*@ pure @*/ int nbSubPredicates() {
		return 2;
	}

 /*@
	 @ private invariant _first != null;
	 @ private invariant _first != this;
	 @*/
	private Predicate<T> _first;

 /*@
	 @ private invariant _second != null;
	 @ private invariant _second != this;
	 @*/
	private Predicate<T> _second;
}
/*
 * <copyright>Copyright (C) 1997-2002. This software is copyrighted by 
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
