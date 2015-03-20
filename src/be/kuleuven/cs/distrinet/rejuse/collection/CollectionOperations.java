package be.kuleuven.cs.distrinet.rejuse.collection;

import java.util.Collection;

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
  
  public static <T,E extends Exception> void filter (Collection<T> collection, Predicate<? super T,E> predicate) throws E {
    predicate.filter(collection);
  }
}
