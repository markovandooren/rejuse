package org.rejuse.java.collections;


import java.util.Iterator;
import java.util.Set;
import java.util.Map;

/**
 * <p>A class of objects that accumulate over a map.</p>
 *
 * <center>
 * 	<img src="doc-files/MapAccumulator.png"/>
 * </center>
 * 
 * <p>The MapAccumulator class is the general class for visiting a map
 * while passing some object to the next element.  The original map remains
 * unchanged. In relational algebra, this is aggregation.</p>
 * <p>The initial object is returned by the <code><a  href="Accumulator.html#initialAccumulator()">initialAccumulator()</a></code>
 * method and passed to the <code><a
 * href="Accumulator.html#accumulate(java.lang.Object,
 * java.lang.Object, java.lang.Object)">accumulate(Object key, Object value, Object acc)</a></code> method.
 * This method then returns the object to be given to the next element and so
 * on. The object returned by the visit of the last element of the collection
 * is returned to the user. The accumulation is started by invoking the
 * <code><a
 * href="Accumulator.html#accumulate(java.util.Map)">accumulate(Map
 * map)</a></code> method.</p>
 * 
 * <p>An example of an accumulation is shown below. It is used to calculate the
 * state of a list of components based on the state of its components. The
 * traditional code is also shown.</p>
 * 
 * <p><b><underline>Accumulation the traditional way.</underline></b></p>
 * <pre><code>
 * Iterator iter = map.keySet().iterator();
 * MyType myAccumulator = (* initial value *);
 * while(iterator.hasNext()) {
 *   MyType key = (MyType) iter.next();
 *   myAccumulator = (* accumulation of key, map.get(key) and myAccumulator *);
 * }
 * </code></pre>
 * 
 * <p><b><underline>Accumulation using the <code>Accumulator</code> class.</underline></b></p>
 * <pre><code>
 * MyType myAccumulator = 
 *     (MyType) new MapAccumulator() {
 *       public Object initialAccumulator() {
 *         return (* initial value *);
 *       }
 *
 *       public Object accumulate(Object key, Object value, Object acc) {
 *         return (* accumulation of element and myAccumulator *);
 *       }
 *     }.accumulate(map);
 * </code></pre>
 * <p><b><underline>The same, but now with specifications.</underline></b></p>
 * <pre><code>
 * MyType myAccumulator = 
 *     (MyType) new Accumulator() {
 *      /oo
 *        o also public behavior
 *        o
 *        o post (* preconditions for accumulate *);
 *        o
 *        o public model boolean isValidElement(Object element);
 *        oo/
 *
 *      /oo
 *        o public behavior
 *        o
 *        o post \result = (* initial value *);
 *        oo/
 *       public Object initialAccumulator() {
 *         return (* initial value *);
 *       }
 *
 *      /oo
 *        o public behavior
 *        o
 *        o post \result = (* accumulation of element and myAccumulator *);
 *        oo/
 *       public Object accumulate(Object element, Object acc) {
 *         return (* accumulation of element and myAccumulator *);
 *       }
 *     }.accumulate(collection);
 * </code></pre>
 *
 * <p>The Jutil.org version requires slightly more code, but is
 * easier to understand than the traditional code because there are no control
 * statements anymore.<p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Jan Dockx
 * @author  Marko van Dooren
 * @release $Name$
 */
public abstract class MapAccumulator implements MapOperator {
  
	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /**
   * <p>Subclasses should implement this method to return the initialized accumulator.
   * This method is called at the start of the accumulation. The result will be used
   * in the application of <code>public Object accumulate(Object element, Object acc)</code>
   * for the first element.</p>
   */
  public abstract /*@ pure @*/ Object initialAccumulator();
  
  /**
   * <p>This method is called for each 9key, value) pair in the map we are accumulating.
   * Subclasses should implement this method to process 'key' and 'value', and accumulate
   * the result in 'acc'.</p>
   * <p>The result is the accumulator that will be used for the next element of the
   * collection to process.</p>.
   *
   * @param  key
   *         The key that the method will process and change the accumulator with.
   * @param  value
   *         The value that the method will process and change the accumulator with.
   * @param  acc
   *         The accumulator for the accumulation.
   *         For the first element to be processed, the result of initialAccumulator
   *         is used. For the other elements, the result of this method applied on
   *         the previous element is used.
   */
 /*@
	 @ public behavior
	 @
   @ pre isValidPair(key, value);
   @*/
  public abstract /*@ pure @*/ Object accumulate(Object key, Object value, Object acc);
  
  /**
   * <p>Perform the accumulation defined in
   * <code>public Object accumulate(Object key, Object value, Object acc)</code> for each
   * element of <code>map</code>. For the first element, the object returned by
   * <code>public Object initialAccumulator()</code> is used as accumulator.
   * For the other elements, the result of the application of
   * <code>public Object accumulate(Object key, Object value, Object acc)</code> on the
   * previous element is used as accumulator.</p>
   *
   * <p>The contents of <code>map</code> is not changed.</p>
   *
   * <p>The result of this method is the object returned by the application of
   * <code>public Object accumulate(Object key, Object value, Object acc)</code> on the
   * last element of the collection to be processed.</p>
   *
   * @param  map
   *         The map to perform this accumulation on. It will not be changed.
   *         This can be null, in which the initial accumulator is returned.
   */
 /*@
   @ public behavior
   @
   @ pre (\forall Map.Entry mapEntry; map.keySet().contains(mapEntry); isValidPair(mapEntry.getKey(), mapEntry.getValue()));
	 @
	 @ post (* the result of the accumulation is returned *);
	 @ post map == null ==> \result == initialAccumulator();
   @*/
  public final /*@ pure @*/ Object accumulate(Map map) {
    Object acc = initialAccumulator();
    if (map != null) {
      Set set = map.entrySet();
      Iterator iter = set.iterator();
      while (iter.hasNext()) {
        Map.Entry mapEntry = (Map.Entry)iter.next();
        acc = accumulate(mapEntry.getKey(), mapEntry.getValue(), acc);
      }
    }
    return acc;
  }
}
/*<copyright>Copyright (C) 1997-2002. This software is copyrighted by 
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
