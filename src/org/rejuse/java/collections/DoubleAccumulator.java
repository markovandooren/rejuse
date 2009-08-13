package org.rejuse.java.collections;


import java.util.Collection;
import java.util.Iterator;

/*@ model import java.util.ConcurrentModificationException; @*/

/**
 * <p>A double accumulator for collections. </p>
 *
 * <center>
 *   <img src="doc-files/DoubleAccumulator.png"/>
 * </center>
 *
 * <p>DoubleAccumulators process each element of a collection,
 * and update an accumulator while doing so. 
 * The original collection remains unchanged.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @author  Jan Dockx
 * @author  Marko van Dooren
 */
public abstract class DoubleAccumulator implements CollectionOperator {
  
	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /**
   * <p>Subclasses should implement this method to return the initialized accumulator.
   * This method is called at the start of the accumulation. The result will be used
   * in the application of <code>public double accumulate(Object element, double acc)</code>
   * for the first element.</p>
   */
  public abstract /*@ pure @*/ double initialAccumulator();
  
  /**
   * <p>This method is called for each element in the collection we are accumulating.
   * Subclasses should implement this method to process <element> and accumulate
   * the result in acc.</p>
   * <p>The result is the accumulator that will be used for the next element of the
   * collection to process.</p>.
   *
   * @param  element
   *         The object the method will process and change the accumulator with.
   * @param  acc
   *         The accumulator for the accumulation.
   *         For the first element to be processed, the result of initialAccumulator
   *         is used. For the other elements, the result of this method applied on
   *         the previous element is used.
   */
 /*@
   @ // The given element may not be null
   @ pre element != null;
   @*/
  public abstract /*@ pure @*/ double accumulate(Object element, double acc);
  
  /**
   * <p>Perform the accumulation defined in
   * <code>public int accumulate(Object element, int acc)</code> for each
   * element of <collection>. For the first element, the object returned by
   * <code>public int initialAccumulator()</code> is used as accumulator.
   * For the other elements, the result of the application of
   * <code>public int accumulate(Object element, int acc)</code> on the
   * previous element is used as accumulator.</p>
   * <p>The contents of <collection> is not changed.</p>
   * <p>The result of this method is the object returned by the application of
   * <code>public int accumulate(Object element, int acc)</code> on the
   * last element of the collection to be processed.</p>
   *
   * @param  collection
   *         The collection to perform this accumulation on. It will not be changed.
   *         This can be null, in which the initial accumulator is returned.
   */
 /*@
   @ public behavior
   @
   @ pre (\forall Object o; collection.contains(o); isValidElement(o));
	 @
	 @ post (* the result of the accumulation is returned *);
	 @ post collection == null ==> \result == initialAccumulator();
	 @
	 @ signals (ConcurrentModificationException) (* The collection was modified while filtering *);
	 @*/
  public /*@ pure @*/ double in(Collection collection) {
    double acc = initialAccumulator();
    if (collection != null) {
      Iterator iter = collection.iterator();
      while (iter.hasNext()) {
        acc = accumulate(iter.next(), acc);
      }
    }
    return acc;
  }
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
