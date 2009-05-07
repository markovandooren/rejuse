package org.rejuse.metric.dimension;

/**
 * <p>A class of dimensions that only consist of themselves or nothing.
 * The difference with BaseDimension is that this class also contains
 * a dimension that always returns 0 for 
 * <a href="Dimension.html#getExponent(org.rejuse.metric.BaseDimension)"><code>getExponent()</code></a>.
 *
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @author  Marko van Dooren
 */
public abstract class BasicDimension extends Dimension {
  
 /*@
   @ // Objects of this class return only 1 for getExponent() when given
   @ // themselves as a parameter, otherwise the result is 0.
   @ public invariant (\forall BasicDimension basic; basic != null;
   @                    (\forall BaseDimension base; base != null;
   @                      (base != basic ==> basic.getExponent(base) == 0) &&
   @                      (base == basic ==> basic.getExponent(base) == 1)));
   @*/

  /**
   * Initialize a new basic dimension with the given name.
   *
   * @param name
   *        The name of the new basic dimension.
   * @param hashCode
   *        The hash code of the new basic dimension.
   */
 /*@
   @ protected behavior
   @
   @ pre name != null;
   @
   @ post getName() == name;
   @ post hashCode() == hashCode;
   @*/
  protected BasicDimension(String name, int hashCode) {
    super(name, hashCode);
  }
}
/*
 * <copyright>Copyright (C) 1997-2002. This software is copyrighted by 
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
