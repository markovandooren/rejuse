package be.kuleuven.cs.distrinet.rejuse.java.collections;

import java.util.Iterator;
import java.util.LinkedList;

/*@ model import java.util.List; @*/

/**
  * A Stack is a Dispenser whose elements
  * can be removed in last-in first-out order.
  * 
  * @path    $Source$
  * @version $Revision$
  * @date    $Date$
  * @state   $State$
  * @author Tom Schrijvers
  * @release $Name$
  */
public class Stack extends AbstractDispenser {

  /* The revision of this class */
  public final static String CVS_REVISION ="$Revision$";

 /*@
   @ // A model field representing the elements in this Fifo. Necessary
   @ // in order to write full specifications, but we don't want to provide
   @ // a getElements() method.
   @ public model List elements;
   @ public invariant elements != null;
   @*/

  private final LinkedList _list = new LinkedList();

  /**
   * Initialize a new empty LinkedListStack.
   */
 /*@
   @ public behavior
   @
   @ post size() == 0;
   @*/
  public Stack() {
  }

  /**
   * Add a new element on top of the stack.
   *
   * @param item
   *        the item to add
   */
 /*@
   @ public behavior
   @
   @ pre item != null;
   @
   @ post elements.get(size() - 1) == item;
   @ post nbExplicitOccurrences(item) == \old(nbExplicitOccurrences(item)) + 1;
   @*/
  public void push(Object item) {
    _list.addFirst(item);
  }

  /**
   * Return the top element on the stack.
   */
 /*@
   @ public behavior
   @
   @ pre ! isEmpty();
   @
   @ post \result == elements.get(size() - 1);
   @*/
  public /*@ pure @*/ Object top() {
    return _list.getFirst();
  }

  /**
   * Remove and return the top element on the stack.
   */
 /*@
   @ public behavior
   @
   @ pre ! isEmpty();
   @
   @ post \result == \old(top());
   @ post nbExplicitOccurrences(\old(top())) ==
   @        \old(nbExplicitOccurrences(top())) - 1;
   @*/
  public Object pop() {
    return _list.removeFirst();
  }

  /**
   * See superclass.
   */
 /*@
   @ also public behavior
   @ 
   @ post \result == elements.size();
   @*/
  public /*@ pure @*/ int size() {
    return _list.size();
  }

  /**
   * See superclass.
   */
  public /*@ pure @*/ int nbExplicitOccurrences(Object item) {
    return Collections.nbExplicitOccurrences(item, _list);
  }

  /**
   * See superclass.
   */
 /*@
   @ also public behavior
   @
   @ post \result == top();
   @*/
  public /*@ pure @*/ Object getNext() {
    return top();
  }

  /**
   * See superclass.
   */
  public void removeNext() {
    pop();
  }

  /**
   * See superclass.
   */
 /*@
   @ also protected behavior
   @ 
   @ post elements.get(size() - 1) == item;
   @*/
  protected void addImpl(Object item) {
    push(item);
  }

  /**
   * See superclass.
   */
  public /*@ pure @*/ Iterator iterator() {
    return _list.iterator();
  }

  /**
   * See superclass.
   */
  public void clear() {
    _list.clear();
  }
}

