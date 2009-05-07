package org.rejuse.java.collections;

/**
 * AbstractFifo provides an implementation for several Dispenser
 * methods, delegating them to the corresponding Fifo methods.
 * 
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author Tom Schrijvers
 * @release $Name$
 */
public abstract class AbstractFifo extends AbstractDispenser implements Fifo {

 /* The revision of this class */
  public final static String CVS_REVISION ="$Revision$";
    
  /**
   * See superclass
   */
	protected void addImpl(Object item) {
		push(item);
	}

  /**
   * See superclass
   */
	public void removeNext() {
		pop();
	}

  /**
   * See superclass
   */
	public /*@ pure @*/ Object getNext() {
		return getFirst();
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
