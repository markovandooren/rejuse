package org.aikodi.rejuse.metric.unit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.aikodi.rejuse.java.collections.Sets;
import org.aikodi.rejuse.metric.dimension.Dimension;

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
  @Override
  public /*@ pure @*/ int exponentOf(SpecialUnit baseUnit) {
    return this.equals(baseUnit) ? 1 : 0;
  }

  /**
   * See superclass
   */
  @Override
  public /*@ pure @*/ Set<SpecialUnit> components() {
    return Sets.of(this);
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
            (((CompositeUnit)other).components().size() == 1) &&
            (((CompositeUnit)other).exponentOf(this) == 1)
           ); 
  }

/***************
 * COMPUTATION *
 ***************/

  /**
   * See superclass
   */
  protected /*@ pure @*/ Unit makeInverse() {
    Map<SpecialUnit, Integer> map = new HashMap<>();
    map.put(this, -1);
    return new CompositeUnit(createName(map), createSymbol(map), dimension().inverse(), map);
  }
}

