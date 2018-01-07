package org.aikodi.rejuse.logic.relation;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public interface WeakTotalOrder<E> extends WeakPartialOrder<E> {

  default E minimum(E first, E second) throws Exception {
    if(contains(first,second)) {
      return first;
    } else {
      return second;
    }
  }
  
  default E maximum(E first, E second) throws Exception {
    if(contains(first,second)) {
      return second;
    } else {
      return first;
    }
  }
  
  default E minimum(Collection<E> collection) throws Exception {
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
  
  default E maximum(Collection<E> collection) throws Exception {
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
