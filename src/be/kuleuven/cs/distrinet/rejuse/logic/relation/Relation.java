package be.kuleuven.cs.distrinet.rejuse.logic.relation;

import java.util.Collection;
import java.util.Iterator;

/**
 * A class of 2-place relations.
 * 
 * @author Marko van Dooren
 *
 * @param <E>
 */
public abstract class Relation<E> {
  
  /**
   * Check if the pair (first,second) is an element of this relation.
   * 
   * @param first
   * @param second
   * @throws Exception
   *         An exception was thrown while checking for the presence of (first,second)
   */
  public abstract boolean contains(E first, E second) throws Exception;
  
  protected void removeLeftOperands(Collection<E> collection) throws Exception {
  	if(collection != null) {
  		Iterator<E> iter = collection.iterator();
  		while(iter.hasNext()) {
  			E e1 = iter.next();
  			Iterator<E> iter2 = collection.iterator();
  			while(iter2.hasNext()) {
  				E e2 = iter2.next();
  				if(contains(e1, e2)) {
  					iter.remove();
  					break;
  				}
  			}
  		}
  	}
  }
}
