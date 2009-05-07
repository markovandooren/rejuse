package org.rejuse.java.collections;

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
/*
 * <copyright>Copyright (C) 1997-2001. This software is copyrighted by 
 * the people and entities mentioned after the "@author" tags above, on 
 * behalf of the JUTIL.ORG Project. The copyright is dated by the dates 
 * after the "@date" tags above. All rights reserved.
 * This software is published under the terms of the JUTIL.ORG Software
 * License version 1.1 or later, a copy of which has been included with
 * this distribution in the LICENSE file, which can also be found at
 * http://org-jutil.sourceforge.net/LICENSE. This software is distributed 
 * WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the JUTIL.ORG Software License for more details. For more information,
 * please see http://org-jutil.sourceforge.net/</copyright>
 */
