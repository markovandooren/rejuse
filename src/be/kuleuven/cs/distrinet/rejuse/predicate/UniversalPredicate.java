package be.kuleuven.cs.distrinet.rejuse.predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import be.kuleuven.cs.distrinet.rejuse.action.Nothing;
import be.kuleuven.cs.distrinet.rejuse.contract.Contracts;

public abstract class UniversalPredicate<T, E extends Exception> extends AbstractPredicate<Object,E> {

	public UniversalPredicate(Class<? extends T> type) {
		Contracts.notNull(type, "The class object of a universal predicate cannot be null.");
		_type = type;
	}
	
	@Override
	public boolean eval(Object object) throws E {
		if(type().isInstance(object)) {
			return uncheckedEval((T)object);
		}
		return false;
	}
	
	public <O extends E> O evalAndCast(O object) throws E {
		if(eval(object)) {
			return (O) object;
		}
		return null;
	}
	
	public abstract boolean uncheckedEval(T t) throws E;

	private Class<? extends T> _type;
	
	public Class<? extends T> type() {
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
