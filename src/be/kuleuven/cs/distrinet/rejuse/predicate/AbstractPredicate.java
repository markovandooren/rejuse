package be.kuleuven.cs.distrinet.rejuse.predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

/**
 *
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
    public <X extends T> void filter(Collection<X> collection) throws ConcurrentModificationException, Exception {
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
                Iterator<X> iter = backup.iterator();
                while (iter.hasNext()) {
                	collection.add(iter.next());
                }
                //collection.addAll(backup);
                throw exc;
            }
        }
    }

    public <X extends T>  List<X> filteredList(Collection<X> collection) throws ConcurrentModificationException, Exception {
    	List<X> result = new ArrayList<X>();
    	for(X x: collection) {
    		if(eval(x)) {
    			result.add(x);
    		}
    	}
    	return result;
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
//    public /*@ pure @*/ int nbSubPredicates() {
//        return getSubPredicates().size();
//    }
}
