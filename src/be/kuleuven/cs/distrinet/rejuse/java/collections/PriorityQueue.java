package be.kuleuven.cs.distrinet.rejuse.java.collections;

import java.util.Comparator;

/**
 * <p>A PriorityQueue is a container of objects with an associated priority/ordering,
 * that allows the retrieval of the element with the smallest value. 
 * The order of the elements is determined by a Comparator.</p>
 *
 * <p>See also <a href="http://priority-queues.webhop.org/">priority-queues.webhop.org</a>
 * for more information on priority queues.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Tom Schrijvers
 * @author  Marko van Dooren
 * @release $Name$
 */
public interface PriorityQueue extends Dispenser{
  
  /* The revision of this class */
  public final static String CVS_REVISION ="$Revision$";

  /**
   * Return the smallest object in this PriorityQueue.
   */
 /*@
   @ public behavior
   @
   @ pre ! isEmpty();
   @
   @ // The result is in this PriorityQueue.
   @ post nbExplicitOccurrences(\result) > 0;
   @ // The result is the minimum element.
   @ post (\forall Object o; nbExplicitOccurrences(o) > 0;
   @       ExtendedComparator.ensureExtended(getComparator()).notGreater(o,\result));
   @*/
  public /*@ pure @*/ Object min();

 /*@
   @ also public behavior
   @
   @ post \result == min();
   @*/
  public /*@ pure @*/ Object getNext();
  
  /**
   * Return the smallest object in this PriorityQueue and
   * remove it.
   */
 /*@
   @ public behavior
   @
   @ pre ! isEmpty();
   @
   @ // The minimum is returned.
   @ post \result == \old(min());
   @ // The instance that is returned, is removed.
   @ post nbExplicitOccurrences(\result) == \old(nbExplicitOccurrences(getNext())) - 1;
   @*/
  public Object pop();

  /**
   * Return the comparator that is used to determine the order of the elements.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
  public /*@ pure @*/ Comparator getComparator();
}

