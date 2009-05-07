package org.rejuse.predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

/**
 * @author ?????
 * @version $Revision$
 * @path $Source$
 * @date $Date$
 * @state $State$
 * @release $Name$
 */
public abstract class AbstractPredicate<T> implements Predicate<T> {

    /**
     * See superclass
     */
    public /*@ pure @*/ boolean exists(Collection<T> collection) throws ConcurrentModificationException, Exception {
        boolean acc = false;
        if (collection!=null) {
            Iterator<T> iter = collection.iterator();
            // variant : the number of elements in the explicit collection that
            //           have not yet been checked.
            // invariant : acc == false if none of the previous elements in the
            //             explicit collection satisfies the criterion.
            //             Otherwise, it is true.
            while (iter.hasNext() && (!acc)) {
                acc = eval(iter.next());
            }
        }
        return acc;
    }

    /**
     * See superclass
     */
    public /*@ pure @*/ boolean forall(Collection<T> collection) throws ConcurrentModificationException, Exception {
        return forAll(collection);
    }

    /**
     * See superclass
     */
    public /*@ pure @*/ boolean forAll(Collection<T> collection) throws ConcurrentModificationException, Exception {
        boolean acc = true;
        if (collection!=null) {
            Iterator<T> iter = collection.iterator();
            // variant : the number of elements in the explicit collection that
            //           have not yet been checked.
            // invariant : acc == true if all previous elements in the explicit collection
            //             satisfy the criterion. Otherwise, it is false.
            while (iter.hasNext() && acc) {
                acc = eval(iter.next());
            }
        }
        return acc;
    }

    /**
     * See superclass
     */
    public /*@ pure @*/ int count(Collection<T> collection) throws ConcurrentModificationException, Exception {
        int count = 0;
        if (collection!=null) {
            Iterator<T> iter = collection.iterator();
            while (iter.hasNext()) {
                if (eval(iter.next())) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * See superclass
     */
    public void filter(Collection<? extends T> collection) throws ConcurrentModificationException, Exception {
        if (collection!=null) {
            // Make a backup, just in case something goes wrong
            List backup = new ArrayList<T>(collection);
            try {
                Iterator<? extends T> iter = collection.iterator();
                while (iter.hasNext()) {
                    if (! eval(iter.next())) {
                        iter.remove();
                    }
                }
            } catch (Exception exc) {
                // clear whatever is left in the collection
//                collection.clear();
//                Iterator<T> iter = backup.iterator();
//                while (iter.hasNext()) {
//                	collection.add((T)iter.next());
//                }
//                //collection.addAll(backup);
//                throw exc;
            }
        }
    }

    /**
     * See superclass
     */
    public final /*@ pure @*/ boolean equals(Object other) {
        //default implementation
        return (other==this);
    }

    /**
     * See superclass
     */
    public /*@ pure @*/ int nbSubPredicates() {
        return getSubPredicates().size();
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
