package org.rejuse.java.collections;


/**
 * <p>Synchronized fifo list that will block the request to pop the first object
 * until a first object is present.</p>
 *
 * <center>
 *   <img src="doc-files/BlockingFifoList.png"/>
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
public class BlockingFifoList	extends FifoList {

  /* The revision of this class */
    public final static String CVS_REVISION ="$Revision$";

	/**
	 * Initialize a new empty blocking fifo list.
	 */
 /*@
	 @ public behavior
	 @
	 @ post size() == 0;
	 @*/
  public BlockingFifoList(){
  }

  /**
	 * See superclass.
	 */
	public synchronized void push(Object object) {
	  super.push(object);
	  notify();
  }

  /**
   * See superclass.
   * Will block until the list is non-empty.
   */
 /*@
	 @ also public behavior
	 @
	 @ pre true;
	 @*/
  public synchronized Object pop() {
		// While loop in case an InterruptedException is thrown
		// while there is no object available yet.
		// MVDMVDMVD: Shouldn't the exception be thrown to the
		// caller ?
	  while(isEmpty()) {
	    try {
		    wait();
	    } 
			catch (InterruptedException ie) {
	    }
	  }
	  return super.pop();
  }

  /**
    * See superclass.
    */
  public synchronized /*@ pure @*/ Object getFirst() {
	  while (isEmpty()) {
		try {
			wait();
		} catch (InterruptedException ie) {
		}
	  }
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
