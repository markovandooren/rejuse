package org.rejuse.java.collections;

/**
 * <p>Synchronized version of a FifoList.</p>
 *
 * <center>
 *   <img src="doc-files/SynchronizedFifoList.png"/>
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
public class SynchronizedFifoList extends FifoList{
  
  /* The revision of this class */
  public final static String CVS_REVISION ="$Revision$";

  /**
   * See superclass.
   */
  public synchronized void push(Object obj) {
    super.push(obj);
  }
  
  /**
   * See superclass.
   */
  public synchronized Object pop() {
    return super.pop();
  }

  /**
   * See superclass.
   */
  public synchronized /*@ pure @*/ Object getFirst() {
    return super.getFirst();
  }
  
  /**
   * See superclass/
   */
  public synchronized void clear() {
    super.clear();
  }

  /**
   * See superclass.
   */
  public synchronized /*@ pure @*/ int size() {
    return super.size();
  }

  /**
   * See superclass.
   */
  public synchronized /*@ pure @*/ int nbExplicitOccurrences(Object item) {
    return super.nbExplicitOccurrences(item);
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
