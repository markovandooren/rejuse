package org.rejuse.predicate;

import java.util.List;
import java.util.ArrayList;

/**
 * <p>A class of predicates that have no subpredicates.</p>
 *
 * <center><img src="doc-files/PrimitivePredicate.png"/></center>
 *
 * <p>Since a primitive predicate does not contain any subpredicates, this
 * abstract class implements 
 * <a href="PrimitivePredicate.html#nbSubPredicates()"><code>nbSubPredicates()</code></a> 
 * and <a href="PrimitivePredicate.html#getSubPredicates()"><code>getSubPredicates()</code></a>.</p>
 *
 * <p>Typically, this class will be used as an anonymous inner class as follows:</p>
 * <pre><code>
 *Predicate myPredicate = new PrimitivePredicate() {
 *       /oo
 *        o also public behavior
 *        o
 *        o post postcondition;
 *        o/
 *       public boolean eval(Object o) throws MyException {
 *         //calculate boolean value
 *         //using the given object
 *       }
 *   };
 * </code></pre>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public abstract class PrimitivePredicate<T> extends AbstractPredicate<T> {

  /* The revision of this class */
  public final static String CVS_REVISION ="$Revision$";
    
 /*@
	 @ also public behavior
	 @
	 @ post \result.size() == 0;
	 @*/
	public /*@ pure @*/ List<Predicate<T>> getSubPredicates() {
		return new ArrayList<Predicate<T>>();
	}
	
	/**
	 * See superclass
	 */
	public /*@ pure @*/ int nbSubPredicates() {
		return 0;
	}
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
