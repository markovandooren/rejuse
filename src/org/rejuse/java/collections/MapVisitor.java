package org.rejuse.java.collections;


import java.util.ConcurrentModificationException;
import java.util.Map;


/**
 * <p>A visitor of maps. The code in visit is performed for each element
 * pair in the visited map.</p>
 *
 * <center><img src="doc-files/MapVisitor.png"/></center>
 *
 * <p>MapVisitor is very much like <code><a href="Visitor.html">Visitor</a></code>.
 * The main difference is that the <code><a href="MapVisitor.html#visit(java.lang.Object,
 * java.lang.Object)">visit</a></code> now has 2 arguments: a key and a value.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Jan Dockx
 * @author  Jamal Said
 * @author  Pieter Bekaert
 * @author  Marko van Dooren
 * @release $Name$
 */
public abstract class MapVisitor implements MapOperator {
  
 	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

 /**
   * The code to be applied to all element pairs of a map.
   *
   * @param  key
   *         The key of the element pair the code should be applied to.
   * @param  element
   *         The value of the element pair the code should be applied to.
   */
 /*@
	 @ public behavior
	 @
	 @ pre isValidPair(key, value);
	 @*/
  public abstract void visit(Object key, Object value);
  
  /**
   * <p>Perform the visitation defined in <code>public void visit(Object)</code>
   * on <map>. The contents of <map> is not changed.</p>
   * <p>The collection is returned, so
   * that further operations can be applied to it inline.</p>
   *
   * @param  map
   *         The collection to perform this visitation on. This can be null.
   */
 /*@
	 @ public behavior
	 @
	 @ pre (\forall Map.Entry entry; map.entrySet().contains(entry);
	 @       isValidPair(entry.getKey(), entry.getValue()));
	 @
	 @ // code>public void visit(Object)</code> is called for all elements of <map>
	 @ post (* for all k, for all v: (map.containsKey(k) and (map.get(k) = v) ==> visit(k, v) *);
   @ // The changes are applied to the given set and it is returned afterwards.
   @ post \result == map;
	 @
	 @ signals (ConcurrentModificationException) (* The collection was modified while performing the visit. *);
   @*/
  public final Map applyTo(Map map) throws ConcurrentModificationException {
    if (map != null) {
      new Visitor() {
        public void visit(Object element) {
          Map.Entry entry = (Map.Entry)element;
          MapVisitor.this.visit(entry.getKey(), entry.getValue());
        }
      }.applyTo(map.entrySet());
    }
    return map;
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
