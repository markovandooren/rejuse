package org.rejuse.predicate;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;

/**
 * <p>A class of predicates that evaluates an object using a number of
 * other predicates.</p>
 *
 * <center><img src="doc-files/CompositePredicate.png"/></center>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public abstract class CompositePredicate<T> extends AbstractPredicate<T> {
	
  /* The revision of this class */
  public final static String CVS_REVISION ="$Revision$";
    
  /**
	 * Initialize a new empty CompositePredicate.
	 */
 /*@
	 @ public behavior
	 @
	 @ post nbSubPredicates() == 0;
	 @*/
	public CompositePredicate() {
		_predicates = new ArrayList<Predicate<T>>();
	}

	/**
	 * Initialize a new CompositePredicate with the given predicates.
	 *
	 * @param predicates
	 *        An array containing the predicates.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre predicates != null;
	 @ pre (\forall int i; i>=0 && i<predicates.length;
	 @       predicates[i] != null);
	 @
	 @ post (\forall int i; i>=0 && i<predicates.length;
	 @        getSubPredicates().get(i) == predicates[i]);
	 @ post nbSubPredicates() == predicates.length;
	 @*/
	public CompositePredicate(Predicate<T>[] predicates) {
		_predicates = new ArrayList<Predicate<T>>(Arrays.asList(predicates));
	}

 /*@
	 @ also public behavior
	 @
	 @ post \result == (\forall Predicate p; getSubPredicates().contains(p);
	 @                    p.isValidElement(o));
	 @ public model boolean isValidElement(Object o);
	 @*/

	/**
	 * Add the given predicate to the end of this CompositePredicate.
	 *
	 * @param predicate
	 *        The Predicate to add.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre predicate != null;
	 @ pre predicate != this;
	 @ pre ! predicate.getSubPredicates().contains(this);
	 @
	 @ post nbSubPredicates() == \old(nbSubPredicates()) + 1;
	 @ post predicateAt(nbSubPredicates()) == predicate;
	 @*/
	public void add(Predicate<T> predicate) {
		_predicates.add(predicate);
	}

	/**
	 * Clear this CompositePredicate.
	 */
 /*@
	 @ public behavior
	 @
	 @ post nbSubPredicates() == 0;
	 @*/
	public void clear() {
		_predicates.clear();
	}

	/**
	 * Remove the index'th subpredicate from this CompositePredicate.
	 *
	 * @param index
	 *        The index of the predicate to remove.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre index > 0;
	 @ pre index <= nbSubPredicates();
	 @
	 @ // The size decreased by 1
	 @ post nbSubPredicates() == \old(nbSubPredicates()) - 1;
	 @ // The predicate after the given index are moved
	 @ // to the left.
	 @ post (\forall int i; i > index && i <= \old(nbSubPredicates());
	 @        predicateAt(i-1) == \old(predicateAt(i)));
	 @*/
	public void removedPredicateAt(int index) {
		_predicates.remove(index-1);
	}
	
	/**
	 * Remove all occurrences of the given predicate from
	 * this CompositePredicate.
	 *
	 * @param predicate
	 *        The predicate to be removed.
	 */
 /*@
	 @ public behavior
	 @
	 @ // All predicates different from the given predicate
	 @ // are moved x places to the left, where x is the
	 @ // number of occurrences of the given predicate
	 @ // that occur before that predicate.
	 @ post (\forall int i; i > 0 && i < \old(nbSubPredicates());
	 @   \old(predicateAt(i)) != predicate ==> (
	 @   		\old(predicateAt(i)) == predicateAt(
	 @      	i - (int)(\num_of int j; j > 0 && j < i; 
	 @               \old(predicateAt(j)) == predicate
	 @            )
	 @      )
	 @   )
	 @ );
	 @ // The new number of subpredicates equals the old number
	 @ // of subpredicates minus the number of occurrences of the
	 @ // given predicate.
	 @ post nbSubPredicates() == \old(nbSubPredicates()) -
	 @          (\num_of int i; i> 0 && i <= \old(nbSubPredicates());
	 @             \old(predicateAt(i)) == predicate);
   @ 
   @ signals (ConcurrentModificationException) (* An exception was
   @           raised because another thread was modifying this
   @           CompositePredicate*);
	 @*/
	public void removeAll(Predicate<T> predicate) {
		try {
			new Not(new Identical(predicate)).filter(_predicates);
		}
    catch(ConcurrentModificationException exc) {
      throw exc;
    }
		catch(Exception exc) {
			// this cannot happen since Identical is total and
			// not only propagates
			// TODO : ShouldNotHappen
			throw new Error("new Not(new Identical(predicate)) raised an exception.");
		}
	}

	/**
	 * Return the index'th subpredicate of this CompositePredicate.
	 *
	 * @param index
	 *        The index of the requested predicate.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre index > 0;
	 @ pre index <= nbSubPredicates();
	 @
	 @ post \result == getSubPredicates().get(index-1);
	 @*/
	public /*@ pure @*/ Predicate<T> predicateAt(int index) {
		return _predicates.get(index-1);
	}

	/**
	 * See superclass
	 */
	public /*@ pure @*/ List<Predicate<T>> getSubPredicates() {
		return new ArrayList<Predicate<T>>(_predicates);
	}

	/**
	 * See superclass
	 */
	public /*@ pure @*/ int nbSubPredicates() {
		return _predicates.size();
	}

//	/**
//	 * Make a clone of this CompositePredicate.
//	 */
// /*@
//	 @ also public behavior
//	 @
//	 @ post \result instanceof CompositePredicate;
//	 @ post (\forall int i; i>0 && i<nbSubPredicates();
//	 @        ((CompositePredicate)\result).predicateAt(i) ==
//	 @         predicateAt(i));
//	 @ post ((CompositePredicate)\result).nbSubPredicates() == nbSubPredicates();
//	 @*/
//	//public abstract Object clone();

 /*@
	 @ private invariant _predicates != null;
	 @ private invariant (\forall Object o; _predicates.contains(o);
	 @                       o instanceof Predicate);
	 @*/
	private ArrayList<Predicate<T>> _predicates;
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
