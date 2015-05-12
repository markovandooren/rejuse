package be.kuleuven.cs.distrinet.rejuse.collection;

import java.util.Collection;
import java.util.Iterator;

import be.kuleuven.cs.distrinet.rejuse.function.BiFunction;
import be.kuleuven.cs.distrinet.rejuse.predicate.Predicate;

public interface CollectionOperations {

  public static <T,E extends Exception> boolean exists(Collection<T> collection, Predicate<? super T,E> predicate) throws E {
    return predicate.exists(collection);
  }

  public static <T,E extends Exception> boolean forAll(Collection<T> collection, Predicate<? super T,E> predicate) throws E {
    return predicate.forAll(collection);
  }

  public static <T,E extends Exception> int count(Collection<T> collection, Predicate<? super T,E> predicate) throws E {
    return predicate.count(collection);
  }
  
  public static <T,E extends Exception> void filter(Collection<T> collection, Predicate<? super T,E> predicate) throws E {
    predicate.filter(collection);
  }
  
  public static <T,E extends Exception> T findFirst(Collection<T> collection, Predicate<? super T,E> predicate) throws E {
    Iterator<T> iterator = collection.iterator();
    while(iterator.hasNext()) {
      T t = iterator.next();
      if(predicate.eval(t)) {
        return t;
      }
    }
    return null;
  }
  
  public static <T1,T2,E extends Exception> boolean forAll(Collection<T1> first, Collection<T2> second, BiFunction<T1, T2, Boolean,E> predicate) throws E {
  	if(first == null || second == null) {
  		throw new IllegalArgumentException("The collections cannot be null.");
  	}
  	if(first.size() != second.size()) {
  		throw new IllegalArgumentException("The size of both collections is different: "+first.size()+" and "+second.size());
  	}
  	boolean result = true;
		Iterator<T1> firstIterator = first.iterator();
		Iterator<T2> secondIterator = second.iterator();
		while(firstIterator.hasNext() && result) {
			T1 one = firstIterator.next();
			T2 two = secondIterator.next();
			result = predicate.apply(one,two);
		}
		return result;
  }
}
