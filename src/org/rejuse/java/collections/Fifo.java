package org.rejuse.java.collections;

/*@ model import java.util.List; @*/

/**
 * <p>Interface for first-in first-out like datastructures.</p>
 *
 * <center>
 *   <img src="doc-files/Fifo.png"/>
 * </center>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Tom Schrijvers
 * @author  Marko van Dooren
 * @release $Name$
 */
public interface Fifo extends Dispenser {
  
  /* The revision of this class */
  public final static String CVS_REVISION ="$Revision$";

 /*@
   @ // A model field representing the elements in this Fifo. Necessary
   @ // in order to write full specifications, but we don't want to provide
   @ // a getElements() method.
   @ public model List elements;
   @ public invariant elements != null;
   @*/

  /**
   * See superclass.
   */
 /*@
   @ also public behavior
   @
   @ post \result == elements.size();
   @*/
  public /*@ pure @*/ int size();

  /**
   * Push a new object in the Fifo.
   *
   * @param item
   *        The object to be put in the fifo.
   */
 /*@
   @ public behavior
   @
   @ pre size() < Integer.MAX_VALUE;
   @
   @ post elements.get(elements.size() - 1) == item;
   @ post nbExplicitOccurrences(item) == \old(nbExplicitOccurrences(item)) + 1;
   @*/
  public void push(Object item);

 /*@
   @ public behavior
   @
   @ pre ! isEmpty();
   @
   @ post \result == elements.get(0);
   @*/
  public /*@ pure @*/ Object getFirst();

  /**
   * See superclass.
   */
 /*@
   @ also public behavior
   @
   @ post \result == getFirst();
   @*/
  public /*@ pure @*/ Object getNext();
  
  /**
   * Pop the first object from this fifo.
   */
 /*@
   @ public behavior
   @
   @ pre ! isEmpty();
   @
   @ post \result == \old(getFirst());
   @ post nbExplicitOccurrences(\result) ==
   @        \old(nbExplicitOccurrences(getNext())) - 1;
   @ post (\forall int index; index >= 0 && index < size();
   @          elements.get(index) == \old(elements.get(index + 1)));
   @*/
  public Object pop();

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
