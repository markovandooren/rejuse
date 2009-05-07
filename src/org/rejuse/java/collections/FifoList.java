package org.rejuse.java.collections;

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
