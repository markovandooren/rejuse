package be.kuleuven.cs.distrinet.rejuse.java.collections;

import java.util.*;


public abstract class UnsafeAccumulator implements CollectionOperator {

  public UnsafeAccumulator() {
  }

  public abstract Object initialAccumulator();

  public abstract Object accumulate(Object obj, Object obj1)
    throws Exception;

  public Object accumulate(Collection collection) throws ConcurrentModificationException, Exception {
    Object acc = initialAccumulator();
    if(collection != null) {
      for(Iterator iter = collection.iterator(); iter.hasNext();) {
        acc = accumulate(iter.next(), acc);
      }

    }
    return acc;
  }

  public static final String CVS_REVISION = "$Revision$";
}
