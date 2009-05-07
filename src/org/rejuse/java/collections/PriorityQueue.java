package org.rejuse.java.collections;

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
