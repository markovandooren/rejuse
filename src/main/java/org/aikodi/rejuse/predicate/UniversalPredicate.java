package org.aikodi.rejuse.predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.aikodi.rejuse.action.Nothing;
import org.aikodi.rejuse.contract.Contracts;

/**
 * A class of predicate that can be applied to any object, but can only return
 * true for objects that are a subtype of type parameter T.
 * @author Marko van Dooren
 *
 * @param <T>
 * @param <E>
 */
public abstract class UniversalPredicate<T, E extends Exception> implements Predicate<Object,E> {

	private Class<T> _type;
	
  public static <T,E extends Exception> UniversalPredicate<T,E> of(Class<T> kind, Predicate<? super T,E> predicate) {
    return new UniversalPredicate<T, E>(kind){
    
        @Override
        public boolean uncheckedEval(T t) throws E {
          return predicate.eval(t);
        }
    };
  }
  
  public static UniversalPredicate<Object, Nothing> isTrue() {
  	return of(Object.class, o -> true);
  }
  
  public static UniversalPredicate<Object, Nothing> isFalse() {
  	return of(Object.class, o -> true);
  }
  
	public UniversalPredicate(Class<T> type) {
		Contracts.notNull(type, "The class object of a universal predicate cannot be null.");
		_type = type;
	}
	
	@Override
	public boolean eval(Object object) throws E {
		return type().isInstance(object) && uncheckedEval((T)object);
	}
	
	public <O extends E> O evalAndCast(O object) throws E {
		if(eval(object)) {
			return (O) object;
		}
		return null;
	}
	
	public abstract boolean uncheckedEval(T t) throws E;

	public Class<T> type() {
		return _type;
	}
	
  public List<T> downCastedList(Collection<?> collection) throws E {
  	List<T> result = new ArrayList<T>();
  	for(Object o: collection) {
  		if(eval(o)) {
  			result.add((T) o);
  		}
  	}
  	return result;
  }
  
  public UniversalPredicate<T,E> and(final UniversalPredicate<? super T, ? extends E> other) {
  	return new UniversalPredicate<T, E>(type()) {
			@Override
			public boolean uncheckedEval(T object) throws E {
				return UniversalPredicate.this.eval(object) && other.eval(object);
			}
			
			@Override
			public String toString() {
				return "(" + UniversalPredicate.this.toString() +" & " + other.toString() +")";
			}

		};
  }
  
  public UniversalPredicate<T,E> or(final UniversalPredicate<? super T, ? extends E> other) {
  	return new UniversalPredicate<T, E>(type()) {
			@Override
			public boolean uncheckedEval(T object) throws E {
				return UniversalPredicate.this.eval(object) || other.eval(object);
			}
			
			@Override
			public String toString() {
				return "(" + UniversalPredicate.this.toString() +" | " + other.toString() +")";
			}
		};
  }
  
  @Override
  public UniversalPredicate<T, Nothing> guard(final boolean value) {
  	return new UniversalPredicate<T, Nothing>(type()) {

			@Override
			public boolean uncheckedEval(T object) throws Nothing {
				try {
					return UniversalPredicate.this.eval(object);
				} catch(Exception e) {
					return value;
				}
			}
  		
  	};
  }

}
