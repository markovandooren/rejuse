package org.rejuse.java.collections; 


/**
 * <p>An IntegerAccumulator that counts the number of items in a collection that
 * satisfies some criterion.</p>
 *
 * <center>
 *  <img src="doc-files/Counter.png"/>
 * </center>
 *
 * <p>A convenience accumulator of collections that checks 
 * elements of a collection satisfy the criterion defined in 
 * <code>public boolean criterion(Object element)</code>.</p>
 *
 * <p>As with the accumulator, this class can best be used as an anonymous
 * inner class, as shown below.</p>
 *
 * <pre><code>
 * int number = new Counter() {
 *                public boolean criterion(Object element) {
 *                  // criterion code
 *                }
 *              }.in(collection);
 * </code></pre>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public abstract class Counter extends IntegerAccumulator {
  
	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /*@
	  @ also public behavior
		@
    @ post \result == 0;
    @*/
  public /*@ pure @*/ int initialAccumulator() {
    return 0;
  }
  
  /*@
	  @ also public behavior
		@
    @ // If the element satisfies the criterion, acc + 1 is returned to
    @ // indicate 1 more element satisfies the criterion.
    @ // otherwise acc is returned.
    @ post criterion(element) == false ==> \result == acc;
    @ post criterion(element) == true ==> \result == acc + 1;
    @*/
  public /*@ pure @*/ int accumulate(Object element, int acc) {
    if(criterion(element)) {
      return acc + 1;
    }
    else {
      return acc;
    }
  }
	
  /**
   * The criterion used to determine whether or not an element
   * has to be counted.
   */
 /*@
	 @ //As usual, criterion must be consistent with the semantics of equals
   @ post (\forall Object o1;;
   @         (\forall Object o2; o1.equals(o2);
   @            criterion(o1) == criterion(o2)));
   @*/
  public abstract /*@ pure @*/ boolean criterion(Object element);
  
//  /*@ //FIXME
//    @ // Count the number of elements in the given collection that
//    @ // satisfy the criterion.
//    @ post_redundantly \result == (\num_of Object o; collection.contains(o); criterion(o));
//    @*/
// //@//public int in(Collection collection);
}
/*
<copyright>Copyright (C) 1997-2001. This software is copyrighted by 
the people and entities mentioned after the "@author" tags above, on 
behalf of the JUTIL.ORG Project. The copyright is dated by the dates 
after the "@date" tags above. All rights reserved.
This software is published under the terms of the JUTIL.ORG Software
License version 1.1 or later, a copy of which has been included with
this distribution in the LICENSE file, which can also be found at
http://org-jutil.sourceforge.net/LICENSE. This software is distributed 
WITHOUT ANY WARRANTY; without even the implied warranty of 
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
See the JUTIL.ORG Software License for more details. For more information,
please see http://org-jutil.sourceforge.net/</copyright>/
*/
