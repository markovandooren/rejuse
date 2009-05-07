package org.rejuse.event;


import java.util.EventListener;
import java.util.EventObject;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
/*@ model import org.jmlspecs.models.JMLObjectSequence; @*/


/**
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Jan Dockx
 * @release $Name$
 */
final public class ChainNotifier implements Notifier {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /*@
    @ public behavior
    @   assignable notifiers;
    @   post notifiers.isEmpty();
    @*/
  public ChainNotifier() {
    $chainElements = new LinkedList();
  }
  
  /*@
    @ private behavior
    @
    @ assignable notifiers;
    @
    @ post isModelFor(notifiers, l);
    @*/
  private ChainNotifier(List l) {
    $chainElements = new LinkedList(l);
  }

  /*@
    @ public invariant notifiers != null;

    @ public invariant (\forall Object o; notifiers.has(o); o != null);

    @ public invariant (\forall Object o; notifiers.has(o);
    @                     o instanceof ApplicabilityNotifier);
    @*/

  /*@
    @ public model JMLObjectSequence notifiers;
    @*/
    
  public void notifyEventListener(EventListener listener, EventObject event) {
    Iterator iter = $chainElements.iterator();
    while (iter.hasNext()) {
      ApplicabilityNotifier notifier = (ApplicabilityNotifier)iter.next();
      if (notifier.isApplicable(listener, event)) {
        notifier.notifyEventListener(listener, event);
        break; // notifier has accepted the challenge, and we are done
      }
    }
  }
  
  /*@
    @ public behavior
    @   post \result == notifiers.length();
    @*/
  final public /*@ pure @*/ int size() {
    return $chainElements.size();
  }

  /*@
    @ public behavior
    @   post \result == notifiers.isEmpty();
    @*/
  final public /*@ pure @*/ boolean isEmpty() {
    return $chainElements.isEmpty();
  }

  /*@
    @ public behavior
    @   post \result == notifiers.has(notifier);
    @*/
  final public /*@ pure @*/ boolean contains(ApplicabilityNotifier notifier) {
    return $chainElements.contains(notifier);
  } 

/* JDJDJD consider adding iterators and toArray methods */

  /*@
    @ public behavior
    @   assignable notifiers;
    @   post notifiers.equals(\old(notifiers.removeItemAt(
    @                                 notifiers.indexOf(notifier))));
    @*/
  final public void remove(ApplicabilityNotifier notifier) {
    $chainElements.remove(notifier);
  }

  /**
   * Appends the specified element to the end of this list.
   */
  /*@
    @ public behavior
    @   assignable notifiers if notifier != null;
    @   post notifier != null ==>
    @           notifiers.equals(\old(notifiers.insertBack(notifier)));
    @*/
  final public void add(ApplicabilityNotifier notifier) {
    if (notifier != null) {
      $chainElements.add(notifier);
    }
  }

// Returns true if this list contains all of the elements of the specified collection.
//  public boolean containsAll(ChainNotifier enc) {
//    return $chainElements.containsAll(enc.getNotifiers());
//  }
  
// Appends all of the elements in the specified collection to the end of this list, in the order that they are returned by the specified collection's iterator (optional operation).
//  public void addAll(ChainNotifier enc) {
//    $chainElements.addAll(enc.getNotifiers());
//  }

// Inserts all of the elements in the specified collection into this list at the specified position (optional operation).
//  public void addAll(int index, ChainNotifier enc) {
//    $chainElements.addAll(index, enc.getNotifiers());
//  }

// Removes from this list all the elements that are contained in the specified collection (optional operation).
//  public void removeAll(ChainNotifier enc) {
//    $chainElements.removeAll(enc.getNotifiers());
//  }


// Retains only the elements in this list that are contained in the specified collection (optional operation).
//  public void retainAll(ChainNotifier enc) {
//    $chainElements.retainAll(enc.getNotifiers());
//  }

  /*@
    @ public behavior
    @   assignable notifiers;
    @   post notifiers.isEmpty();
    @*/
  final public void clear() {
    $chainElements.clear();
  }

  /*@
    @ public behavior
    @   post \result == notifiers.itemAt(index);
    @   signals (IndexOutOfBoundsException)
    @             (index < 0) || (index >= notifiers.length());
    @*/
 final public /*@ pure @*/ Object get(int index)
      throws IndexOutOfBoundsException {
    return (ApplicabilityNotifier)$chainElements.get(index);
  }
  
  /**
   * Replaces the element at the specified position in this list with
   * the specified element.
   */
  /*@
    @ public behavior
    @   assignable notifiers if notifier != null;
    @   post notifier != null ==>
    @         notifiers.equals(\old(notifiers.replaceItemAt(index, notifier)));
    @   post notifier != null ==> \result == \old(notifiers.itemAt(index));
    @   post notifier == null ==> \result == null;
    @   signals (IndexOutOfBoundsException)
    @             (index < 0) || (index >= notifiers.length());
    @*/
  final public ApplicabilityNotifier set(int index,
                                          ApplicabilityNotifier notifier)
      throws IndexOutOfBoundsException {
    if (notifier == null) {
      return null;
    }
    else {
      return (ApplicabilityNotifier)$chainElements.set(index, notifier);
    }
  }

  /**
   * Inserts the specified element at the specified position in this list.
   */
  /*@
    @ public behavior
    @   assignable notifiers if notifier != null;
    @   post notifier != null ==>
    @         notifiers.equals(\old(notifiers.
    @                                 insertBeforeIndex(index, notifier)));
    @   signals (IndexOutOfBoundsException)
    @             (index < 0) || (index >= notifiers.length());
    @*/
  public void add(int index, ApplicabilityNotifier notifier)
      throws IndexOutOfBoundsException {
    if (notifier != null) {
      $chainElements.add(index, notifier);
    }
  }

  /*@
    @ public behavior
    @   assignable notifiers;
    @   post notifiers.equals(\old(notifiers.removeItemAt(index)));
    @   signals (IndexOutOfBoundsException)
    @             (index < 0) || (index >= notifiers.length());
    @*/
  public ApplicabilityNotifier remove(int index)
      throws IndexOutOfBoundsException {
    return (ApplicabilityNotifier)$chainElements.remove(index);
  }

  /**
   * Returns the index in this list of the first occurrence of the specified
   * element, or -1 if this list does not contain this element.
   */
  /*@
    @ public behavior
    @   post notifiers.has(notifier) ==>
    @          \result == notifiers.indexOf(notifier);
    @   post ! notifiers.has(notifier) ==> \result == -1;
    @   ensures_redundantly \result >= -1;
    @*/
  public /*@ pure @*/ int indexOf(ApplicabilityNotifier notifier) {
    return $chainElements.indexOf(notifier);
  }

  /**
   * Returns the index in this list of the last occurrence of the specified
   * element, or -1 if this list does not contain this element.
   */
  /*@
    @ public behavior
    @   post notifiers.has(notifier) ==>
    @          \result == notifiers.length() -
    @                       notifiers.reverse().indexOf(notifier);
    @   post ! notifiers.has(notifier) ==> \result == -1;
    @   ensures_redundantly \result >= -1;
    @*/
  public int lastIndexOf(ApplicabilityNotifier notifier) {
    return $chainElements.lastIndexOf(notifier);
  }

/* JDJDJD consider adding list iterators */

// Returns a view of the portion of this list between the specified fromIndex, inclusive, and toIndex, exclusive.
//  public ChainNotifier subChain(int fromIndex, int toIndex) throws IndexOutOfBoundsException {
//    return new ChainNotifier($chainElements.subList(fromIndex, toIndex));
//  }

  /*@
    @ public behavior
    @   post isModelFor(notifiers, \result);
    @*/
  public /*@ pure @*/ List getNotifiers() {
    return new LinkedList($chainElements);
  }
  
  /**
   * <formal-arg>jmlSeq</formal-arg> and <formal-arg>javaList</formal-arg>
   * contain the same elements in the same order.
   */
  /*@
    @ public behavior
    @   pre jmlSeq != null;
    @   pre javaList != null;
    @   post \result <==>
    @           (jmlSeq.length() == javaList.size()) &&
    @           (\forall int i; 0 <= i && i < jmlSeq.length();
    @                jmlSeq.itemAt(i) == javaList.get(i));
    @ static public pure model boolean isModelFor(JMLObjectSequence jmlSeq,
    @                                         List javaList);
    @*/

  /*@
    @ depends notifiers <- \fields_of($chainElements);
    
    @ represents notifiers \such_that isModelFor(notifiers, $chainElements);

    @ private invariant $chainElements != null;

    @ private invariant (\forall Object o; $chainElements.contains(o);
    @                       o != null);

    @ private invariant (\forall Object o; $chainElements.contains(o);
    @                     o instanceof ApplicabilityNotifier);
    @*/
  private List $chainElements;

}
/*
<copyright>Copyright (C) 1997-2001. This software is copyrighted by 
the people and entities mentioned after the "@author" tags above, on 
behalf of the JUTIL.ORG Project. The copyright is dated by the dates 
after the "@date" tags above. All rights reserved.
This software is published under the terms of the JUTIL.ORG Software
License version 1.1 or later, a copy of which has been included with
this distribution in the LICENSE file, which can also be found at
http://org-jutil.sourceforge.net/LICENSE. This software is distributed 
WITHOUT ANY WARRANTY; without even the implied warranty of 
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
See the JUTIL.ORG Software License for more details. For more information,
please see http://org-jutil.sourceforge.net/</copyright>/
*/
