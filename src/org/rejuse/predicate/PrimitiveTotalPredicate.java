package org.rejuse.predicate;

import java.util.List;
import java.util.ArrayList;

/**
 * <p>A class of total predicates that have no subpredicates.</p>
 *
 * <center><img src="doc-files/PrimitiveTotalPredicate.png"/></center>
 *
 * <p>This class implements the 
 * <a href="PrimitiveTotalPredicate.html#nbSubPredicates()"><code>nbSubPredicates()</code></a> 
 * and <a href="PrimitiveTotalPredicate.html#getSubPredicates()"><code>getSubPredicates()</code></a>
 * methods for predicates that don't contain any sub predicates.
 *
 * <p>Typically, this class will be used as an anonymous inner class as follows:</p>
 *<pre><code>
 *TotalPredicate myPredicate = new PrimitiveTotalPredicate() {
 *            public boolean eval(Object o) {
 *              //calculate boolean value
 *              //using the given object
 *            }
 *          };
 *</pre></code>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public abstract class PrimitiveTotalPredicate<T> extends TotalPredicate<T> {

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
