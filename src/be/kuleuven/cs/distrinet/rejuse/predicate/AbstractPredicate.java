package be.kuleuven.cs.distrinet.rejuse.predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import be.kuleuven.cs.distrinet.rejuse.action.Nothing;

/**
 *
 */
public abstract class AbstractPredicate<T,E extends Exception> implements Predicate<T,E> {

    /**
     * See superclass
     */
    public /*@ pure @*/ boolean exists(Collection<T> collection) throws E {
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
    public /*@ pure @*/ boolean forAll(Collection<T> collection) throws E {
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
    public /*@ pure @*/ int count(Collection<T> collection) throws E {
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
    public <X extends T> void filter(Collection<X> collection) throws E {
    	if (collection!=null) {
    		Iterator<? extends T> iter = collection.iterator();
    		while (iter.hasNext()) {
    			if (! eval(iter.next())) {
    				iter.remove();
    			}
    		}
    	}
    }

    public <X extends T>  List<X> filteredList(Collection<X> collection) throws E {
    	List<X> result = new ArrayList<X>();
    	for(X x: collection) {
    		if(eval(x)) {
    			result.add(x);
    		}
    	}
    	return result;
    }
    
    public Predicate<T,E> and(final Predicate<? super T, ? extends E> other) {
    	return new AbstractPredicate<T, E>() {
				@Override
				public boolean eval(T object) throws E {
					return AbstractPredicate.this.eval(object) && other.eval(object);
				}
			};
    }
    
    public Predicate<T,E> or(final Predicate<? super T, ? extends E> other) {
    	return new AbstractPredicate<T, E>() {
				@Override
				public boolean eval(T object) throws E {
					return AbstractPredicate.this.eval(object) || other.eval(object);
				}
			};
    }
    
    public Predicate<T,E> negation() {
    	return new AbstractPredicate<T, E>() {
				@Override
				public boolean eval(T object) throws E {
					return ! AbstractPredicate.this.eval(object);
				}
			};
    }
    
    public Predicate<T,E> implies(final Predicate<? super T, ? extends E> other) {
    	return new AbstractPredicate<T, E>() {
				@Override
				public boolean eval(T object) throws E {
					return ! AbstractPredicate.this.eval(object) || other.eval(object);
				}
			};
    }

    public Predicate<T,E> xor(final Predicate<? super T, ? extends E> other) {
    	return new AbstractPredicate<T, E>() {
				@Override
				public boolean eval(T object) throws E {
					return AbstractPredicate.this.eval(object) ^ other.eval(object);
				}
			};
    }
    
    public Predicate<T,Nothing> guard(final boolean value) {
    	return new AbstractPredicate<T, Nothing>() {
				@Override
				public boolean eval(T object) throws Nothing {
					try {
						return AbstractPredicate.this.eval(object);
					} catch(Exception e) {
						return value;
					}
				}
    		
    	};
    }
    
    public <X extends T> UniversalPredicate<X,E> makeUniversal(Class<X> type) {
    	return new UniversalPredicate<X, E>(type) {
				@Override
				public boolean uncheckedEval(X t) throws E {
					return AbstractPredicate.this.eval(t);
				}
			};
    }
}
