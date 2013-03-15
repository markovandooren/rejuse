package be.kuleuven.cs.distrinet.rejuse.logic.relation;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class WeakTotalOrder<E> extends WeakPartialOrder<E> {

  public E minimum(E first, E second) throws Exception {
    if(contains(first,second)) {
      return first;
    } else {
      return second;
    }
  }
  
  public E maximum(E first, E second) throws Exception {
    if(contains(first,second)) {
      return second;
    } else {
      return first;
    }
  }
  
  public E minimum(Collection<E> collection) throws Exception {
    Iterator<E> iter = collection.iterator();
    try {
      E result = iter.next();
      while(iter.hasNext()) {
        E temp = iter.next();
        result = minimum(result,temp);
      }
      return result;
    }
    catch(NoSuchElementException exc) {
      return null;
    }
  }
  
  public E maximum(Collection<E> collection) throws Exception {
    Iterator<E> iter = collection.iterator();
    try {
      E result = iter.next();
      while(iter.hasNext()) {
        E temp = iter.next();
        result = minimum(result,temp);
      }
      return result;
    }
    catch(NoSuchElementException exc) {
      return null;
    }
  }
}
