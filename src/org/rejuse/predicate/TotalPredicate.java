package org.rejuse.predicate;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * <p>A class of Predicates that do not throw exceptions.</p>
 *
 * <center><img src="doc-files/TotalPredicate.png"/></center>
 *
 * <p>If your predicate don't throw exceptions, you should subclass
 * TotalPredicate. This saves the client from writing useless try-catch
 * blocks.</p>
 *
 * <p>Typically, this class will be used as an anonymous inner class as follows:</p>
 * <pre>
 * TotalPredicate myPredicate = new TotalPredicate() {
 *            public boolean eval(Object o) {
 *              //calculate boolean value
 *              //using the given object
 *            }
 *
 *            public int nbSubPredicates() {
 *              // ...
 *            }
 *
 *            public List getSubPredicates() {
 *              // ...
 *            }
 *          };
 * </pre>
 *
 * <p>Note that if your predicate doesn't contain any sub predicates,
 * it should extend <a href="PrimitiveTotalPredicate.html"><code>PrimitiveTotalPredicate</code></a>
 * instead of this class.</p>
 *
 * @author Marko van Dooren
 * @version $Revision$
 * @path $Source$
 * @date $Date$
 * @state $State$
 * @release $Name$
 */
public abstract class TotalPredicate<T> extends AbstractPredicate<T> {

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
    @Override public void filter(Collection<? extends T> collection) {
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
