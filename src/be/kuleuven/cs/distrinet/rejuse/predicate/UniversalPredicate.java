package be.kuleuven.cs.distrinet.rejuse.predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import be.kuleuven.cs.distrinet.rejuse.contract.Contracts;

public abstract class UniversalPredicate<T, E extends Exception> extends AbstractPredicate<Object,E> {

	public UniversalPredicate(Class<T> type) {
		Contracts.notNull(type, "The class object of a universal predicate cannot be null.");
	}
	
	@Override
	public boolean eval(Object object) throws E {
		if(type().isInstance(object)) {
			return uncheckedEval((T)object);
		}
		return false;
	}
	
	public abstract boolean uncheckedEval(T t) throws E;

	private Class<T> _type;
	
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

}
