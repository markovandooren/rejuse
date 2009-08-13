package org.rejuse.java.collections;
/**
 * A class of exceptions indicating the attempt to use
 * a class in this package with an object array that
 * has at least one dimension equal to zero.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class ZeroDimensionException extends RuntimeException {

  //MvDMvDMvD: should this class be public ? It does make sense
  //           in the case of the ArrayCursor, since you simply
  //           can't point to an index of an array that can't 
  //           contain any elements. Or can we work around that
  //           in an elegant way ?

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /**
   * Initialize a new ZeroDimensionException with the given array.
   */
 /*@
   @ // array may not be null
   @ pre array != null;
   @
   @ // The array of this ZeroDimensionException is set to array.
   @ post getArray() == array;
   @*/  
  public ZeroDimensionException(Object[] array) {
    _array=array;
  }

  /**
   * Initialize a new ZeroDimensionException with the given array
   * and message.
   */
 /*@
   @ // array may not be null
   @ pre array != null;
   @
   @ // The array of this ZeroDimensionException is set to array.
   @ post getArray() == array;
   @ // The message of this ZeroDimensionException is set to <msg>.
   @ post getMessage() == msg;
   @*/  
  public ZeroDimensionException(Object[] array, String msg) {
    super(msg);
    _array=array;
  }

  /**
   * Return the array that caused this ZeroDimensionException.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
  public /*@ pure @*/ Object[] getArray() {
    return _array;
  }  
  
  /*@
    @ private invariant _array != null;
    @*/
  private Object[] _array;
} 

/*<copyright>Copyright (C) 1997-2001. This software is copyrighted by 
the people and entities mentioned after the "@author" tags above, on 
behalf of the JUTIL.ORG Project. The copyright is dated by the dates 
after the "@date" tags above. All rights reserved.
This software is published under the terms of the JUTIL.ORG Software
License version 1.1 or later, a copy of which has been included with
this distribution in the LICENSE file, which can also be found at
http://org-jutil.sourceforge.net/LICENSE. This software is distributed WITHOUT 
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
or FITNESS FOR A PARTICULAR PURPOSE. See the JUTIL.ORG Software 
License for more details.
For more information, please see http://org-jutil.sourceforge.net/</copyright>*/
