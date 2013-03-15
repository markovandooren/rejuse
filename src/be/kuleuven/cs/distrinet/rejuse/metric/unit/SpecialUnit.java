package be.kuleuven.cs.distrinet.rejuse.metric.unit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import be.kuleuven.cs.distrinet.rejuse.metric.dimension.Dimension;

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

