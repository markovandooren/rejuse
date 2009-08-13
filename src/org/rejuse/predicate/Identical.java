package org.rejuse.predicate;

/**
 * <p>A class of predicate that check whether or not the given argument
 * is the same as some object.</p>
 *
 * <center><img src="doc-files/Identical.png"/></center>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public class Identical<T> extends SafePredicate<T> {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /**
   * <p>Initialize a new Identical with the given object</p>
   *
   * @param object
   *        The object that will be used for the identity check.
   */
 /*@
	 @ public behavior
	 @
   @ post getObject() == object;
   @*/
  public Identical(T object) {
    _object = object;
  }

	/**
	 * Return the object of this Identical.
	 */
  public /*@ pure @*/ T getObject() {
    return _object;
  }

 /*@
	 @ also public behavior
	 @
	 @ post \result == (getObject() == object);
	 @*/
	public /*@ pure @*/ boolean eval(T object) {
		return _object == object;
	}

	/**
	 * The object to be used for the identity test.
	 */
  private T _object;
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
