package org.rejuse.predicate;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
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
public abstract class SafePredicate<T> extends AbstractPredicate<T> {

  /* The revision of this class */
  public final static String CVS_REVISION = "$Revision$";

  /*@
  @ also public behavior
  @
  @ // A total predicate never throws an exception.
  @ signals (Exception) false;
  @*/
  public abstract /*@ pure @*/ boolean eval(T object);

  /*@
    @ also public behavior
    @
    @ post \result == true;
    @
    @ public model pure boolean isValidElement(Object o);
    @*/

  /*@
  @ also public behavior
    @
    @ // No Exception can be thrown
    @ signals (Exception) false;
    @*/
  public /*@ pure @*/ boolean exists(Collection<T> collection) {
      try {
          return super.exists(collection);
      } catch (ConcurrentModificationException exc) {
          throw exc;
      } catch (Exception exc) {
          throw new Error("Exception occurred in org.jutil.predicate.total.TotalPredicate.exists");
      }
  }

  /*@
  @ also public behavior
    @
    @ // No Exception can be thrown
    @ signals (Exception) false;
    @*/
  public /*@ pure @*/ boolean forall(Collection<T> collection) {
      return forAll(collection);
  }

  /*@
  @ also public behavior
    @
    @ // No Exception can be thrown
    @ signals (Exception) false;
    @*/
  public /*@ pure @*/ boolean forAll(Collection<T> collection) {
      try {
          return super.forAll(collection);
      } catch (ConcurrentModificationException exc) {
          throw exc;
      } catch (Exception exc) {
          throw new Error("Exception occurred in org.jutil.predicate.total.TotalPredicate.forall");
      }
  }

  /*@
  @ also public behavior
    @
    @ // No Exception can be thrown
    @ signals (Exception) false;
    @*/
  public /*@ pure @*/ int count(Collection<T> collection) {
      try {
          return super.count(collection);
      } catch (ConcurrentModificationException exc) {
          throw exc;
      } catch (Exception exc) {
          throw new Error("Exception occurred in org.jutil.predicate.total.TotalPredicate.count");
      }
  }

  /*@
  @ also public behavior
    @
    @ // No Exception can be thrown
    @ signals (Exception) false;
    @*/
  @Override public <X extends T> void filter(Collection<X> collection) {
      // The backup costs too much, we don't want to do that here.
      if (collection!=null) {
          Iterator<? extends T> iter = collection.iterator();
          while (iter.hasNext()) {
              if (! eval(iter.next())) {
                  iter.remove();
              }
          }
      }
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
