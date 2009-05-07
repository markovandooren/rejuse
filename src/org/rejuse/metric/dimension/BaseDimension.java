package org.rejuse.metric.dimension;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

/**
 * <p>This is a class of base dimensions. Base dimensions are 'composed' of only
 * themselves and can be used to create other dimensions.</p>
 *
 * <p>In order to keep things a bit efficient, a base dimension is only equal
 * to itself or a composite dimension that is only composed of that base dimension
 * with 1 as exponent. Note that this situation can only occur if you construct
 * that composite unit manually; the framework tries to keep only a single object
 * for each dimension.</p>
 *
 * <p><b>Only create object of classes BaseDimension and CompositeDimension (or 
 * subclasses of these classes) !</b> The framework has not been developed to
 * support other dimensions because I feel they are not needed, and I want
 * to keep things as efficient as possible.</p>
 *
 * @version $Revision$
 * @date    $Date$
 * @author  Marko van Dooren
 */
public class BaseDimension extends BasicDimension {
  
  /**
   * Initialize a new BaseDimension with the given name.
   *
   * @param name
   *        The name of the new BaseDimension.
   */
 /*@
   @ public behavior
   @
   @ pre name != null;
   @
   @ post getName() == name;
   @*/
  public BaseDimension(String name) {
    super(name, Dimension.getBaseDimensionHashCode());
    ensureDimension(this);
  }

  /**
   * See superclass
   */
  public final /*@ pure @*/ boolean equals(Object other) {
    return (other == this) || 
           (
             (other instanceof Dimension) && 
             (! (other instanceof BaseDimension)) &&
             (((Dimension)other).getBaseDimensions().size() == 1) &&
             (((Dimension)other).getExponent(this) == 1)
           );
  }

  /**
   * See superclass
   */
 /*@
   @ also public behavior
   @
   @ post (this.equals(base)) ==> \result == 1;
   @ post (! this.equals(base)) ==> \result == 0;
   @*/
  public /*@ pure @*/ double getExponent(BaseDimension base) {
    if(this.equals(base)) {
      return 1.0;
    }
    else {
      return 0;
    }
  }

  /**
   * See superclass
   */
  public /*@ pure @*/ Set getBaseDimensions() {
    Set result = new HashSet();
    result.add(this);
    return result;
  }

  /**
   * See superclass
   */
  protected /*@ pure @*/ Dimension makeInverse() {
    Map map = new HashMap();
    map.put(this, new Double(-1));
    return new CompositeDimension(createName(map), map);
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
