package org.rejuse.metric.unit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.rejuse.metric.Prefix;
import org.rejuse.metric.dimension.Dimension;

/**
 * <p>This is a class of base units. Base units are 'composed' of only
 * themselves and can be used to create other units.</p>
 *
 * @version $Revision$
 * @date    $Date$
 * @author  Marko van Dooren
 */
public abstract class SpecialUnit extends NonCompositeUnit {
  
  /**
   * Initialize a new SpecialUnit with the given name
   * and symbol.
   *
   * @param name
   *        The name of the new SpecialUnit.
   * @param symbol
   *        The symbol of the new SpecialUnit.
   * @param dimension
   *        The dimension of the new SpecialUnit.
   */
 /*@
   @ public behavior
   @
   @ pre name != null;
   @ pre symbol != null;
   @ pre dimension != null;
   @
   @ post getName() == name;
   @ post getSymbol() == symbol;
   @ post getQuantity() == dimension;
   @*/
  public SpecialUnit(String name, String symbol, Dimension dimension) {
    super(name, symbol, dimension, Unit.getBaseUnitHashCode());
    Unit.ensureUnit(this);
  }
  
/***************
 * COMPOSITION *
 ***************/

  /**
   * See superclass
   */
 /*@
   @ also public behavior
   @
   @ post (this.equals(baseUnit)) ==> \result == 1;
   @ post (! this.equals(baseUnit)) ==> \result == 0;
   @*/
  public /*@ pure @*/ double getExponent(SpecialUnit baseUnit) {
    if(this.equals(baseUnit)) {
      return 1.0;
    }
    else {
      return 0;
    }
  }

  /**
   * See superclass
   */
  public /*@ pure @*/ Set getSpecialUnits() {
    Set result = new HashSet();
    result.add(this);
    return result;
  }

  /**
   * See superclass
   */
 /*@
   @ also public behavior
   @
   @ post \result == (other == this) ||
   @                 (
   @                  (other instanceof CompositeUnit) &&
   @                  (((CompositeUnit)other).getBaseUnits().size() == 1) &&
   @                  (((CompositeUnit)other).getExponent(this) == 1)
   @                 ); 
   @*/
  public final /*@ pure @*/ boolean equals(Object other) {
    return (other == this) ||
           (
            (other instanceof CompositeUnit) &&
            (((CompositeUnit)other).getSpecialUnits().size() == 1) &&
            (((CompositeUnit)other).getExponent(this) == 1)
           ); 
  }

/***************
 * COMPUTATION *
 ***************/

  /**
   * See superclass
   */
  protected /*@ pure @*/ Unit makeInverse() {
    Map map = new HashMap();
    map.put(this, new Double(-1));
    return new CompositeUnit(createName(map), createSymbol(map), getDimension().inverse(), map);
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
