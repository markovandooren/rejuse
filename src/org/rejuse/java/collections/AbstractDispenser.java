package org.rejuse.java.collections;


/**
 * AbstractDispenser provides an implementation for the add method
 * that always returns true and delegates to an abstract method
 * for the actual adding.
 * 
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author Tom Schrijvers
 * @release $Name$
 */
public abstract class AbstractDispenser /* extends AbstractCollection */ implements Dispenser {

  /* The revision of this class */
    public final static String CVS_REVISION ="$Revision$";

  /**
   * See superclass.
   * FIXME
   */
  public final boolean add(Object item) {
    addImpl(item);
    return true;
  }

  /**
   * Add the given item to this Dispenser.
   *
   * @param item
   *        the item to be added
   */
 /*@
   @ protected behavior
   @
   @ pre item != null;
   @
   @ post nbExplicitOccurrences(item) ==
   @        \old(nbExplicitOccurrences(item)) + 1;
   @*/
  protected abstract void addImpl(Object item);


  /**
   * Temporary substitue
   */
  public /*@ pure @*/ boolean isEmpty() {
    return size() == 0;
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
