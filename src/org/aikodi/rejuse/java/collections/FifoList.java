package org.aikodi.rejuse.java.collections;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * FifoList is a class of objects that represent
 * simple first-in first-out lists .
 *
 * <center>
 *   <img src="doc-files/FifoList.png"/>
 * </center>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author   Tom Schrijvers
 * @author   Marko van Dooren
 * @release $Name$
 */
public class FifoList extends AbstractFifo {

  /* The revision of this class */
  public final static String CVS_REVISION ="$Revision$";

  /**
   * Initialize a new empty FifoList.
   */
 /*@
   @ public behavior
   @
   @ post size() == 0;
   @*/
  public /*@ pure @*/ FifoList() {
    _queue = new LinkedList();
  }
  
  /**
   * See superclass.
   */
  public void push(Object object) {
    _queue.addLast(object);
  }

  /**
   * See superclass.
   */
  public Object pop() {
    return _queue.removeFirst();
  }

  /**
   * See superclass/
   */
  public void clear() {
    _queue.clear();
  }

  /**
   * See superclass.
   */
  public /*@ pure @*/ Iterator iterator() {
    return _queue.iterator();
  }

  /**
   * See superclass.
   */
  public /*@ pure @*/ int size() {
    return _queue.size();
  }

  /**
   * See superclass.
   */
  public /*@ pure @*/ Object getFirst() {
    return _queue.getFirst();
  }

  /**
   * See superclass.
   */
  public /*@ pure @*/ int nbExplicitOccurrences(Object item) {
    return Collections.nbExplicitOccurrences(item,_queue);
  }
  
 /*@
   @ private invariant _queue != null;
   @*/
  private LinkedList _queue;
}

