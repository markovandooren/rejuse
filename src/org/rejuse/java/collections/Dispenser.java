package org.rejuse.java.collections;


/**
 * <p>A Dispenser is a Collection with an
 * inherent order of removal of its objects,
 * e.g. last-in first-out, first-in first-out,
 * priority based, ...</p>
 *
 * An object can occur more than once in a Dispenser.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author Tom Schrijvers
 * @release $Name$
 */
public interface Dispenser /* extends Collection */ {

 /* The revision of this class */
  public final static String CVS_REVISION ="$Revision$";

  /**
   * See superclass.
   *
   * The item is always added.
   */
 /*@
   @ public behavior
   @
   @ pre item != null;
   @
   @ post nbExplicitOccurrences(item) ==
   @        \old(nbExplicitOccurrences(item)) + 1;
   @
   @ post \result == true;
   @*/
  public boolean add(Object item);

  /**
   * Remove the next item in the
   * removal ordering.
   */
 /*@
   @ public behavior
   @
   @ pre ! isEmpty();
   @
   @ post nbExplicitOccurrences(\old(getNext())) ==
   @      \old(nbExplicitOccurrences(getNext())) - 1;
   @*/
  public void removeNext();

  /**
   * Return the object that is next to be removed.
   */
 /*@
   @ public behavior
   @ 
   @ pre ! isEmpty();
   @
   @ post nbExplicitOccurrences(\result) > 0;
   @*/
  public /*@ pure @*/ Object getNext();

  /**
   * See superclass.
   */
 /*@
   @ public behavior
   @
   @ post \result == (\sum Object item; ; nbExplicitOccurrences(item));
   @*/
  public /*@ pure @*/ int size();

  /**
   * Return the nummer of explicit occurrences of the given
   * object.
   *
   * @param item
   *        the object
   */
 /*@
   @ public behavior
   @
   @ post \result >= 0;
   @*/
  public /*@ pure @*/ int nbExplicitOccurrences(Object item);

  /**
   * Temporary substitute
   */
  public /*@ pure @*/ boolean isEmpty();
//  /**
//   * Return an iterator over the elements of the Dispenser,
//   * in order of removal.
//   */
//  public Iterator iterator();
  
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
