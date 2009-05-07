package org.rejuse.predicate;

/**
 * <p>A class of predicates that evaluates to the <code>or</code> of a number
 * of other predicates.</p>
 *
 * <center><img src="doc-files/Or.png"/></center>
 *
 * <p>For efficiency reasons, the conditional <code>or</code> will be 
 * computed.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class Or<T> extends CompositePredicate<T> {
	
  /* The revision of this class */
    public final static String CVS_REVISION ="$Revision$";
    
    /**
	 * Initialize a new empty Or.
	 */
 /*@
	 @ public behavior
	 @
	 @ post nbSubPredicates() == 0;
	 @*/
	public Or() {
	}

	/**
	 * Initialize a new Or with the given predicates.
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
	 @ post predicateAt(1) == first;
	 @ post predicateAt(2) == second;
	 @*/
	public Or(Predicate<T> first, Predicate<T> second) {
		add(first);
		add(second);
	}

	/**
	 * Initialize a new Or with the given predicates.
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
	public Or(Predicate<T>[] predicates) {
		super(predicates);
	}

 /*@
	 @ also public behavior
	 @
	 @ post \result == (\exists Predicate p; getSubPredicates().contains(p);
	 @                    p.eval(object));
	 @*/
	public /*@ pure @*/ boolean eval(T object) throws Exception {
		boolean result = false;
		int size = nbSubPredicates();
		int i = 1;
		while ((i <= size) && (! result)) {
			result = predicateAt(i).eval(object);
      i++;
		}
		return result;
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
