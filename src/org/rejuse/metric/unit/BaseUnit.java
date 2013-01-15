package org.rejuse.metric.unit;

import org.rejuse.metric.dimension.BaseDimension;
import org.rejuse.InitializationException;

/**
 * A class of units that can are the primitive building blocks for constructing units.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @author  Marko van Dooren
 */
public class BaseUnit extends SpecialUnit {
  
 /*@
   @ public invariant getQuantity() instanceof BaseDimension;
   @*/

  /**
   * Initialize a new BaseUnit with the given name
   * and symbol.
   *
   * @param name
   *        The name of the new BaseUnit.
   * @param symbol
   *        The symbol of the new BaseUnit.
   * @param baseQuantity
   *        The dimension of the new BaseUnit.
   */
 /*@
   @ public behavior
   @
   @ pre name != null;
   @ pre symbol != null;
   @ pre baseDimension != null;
   @ pre ! getStandardUnitMap().containsKey(baseDimension);
   @
   @ post getName() == name;
   @ post getSymbol() == symbol;
   @ post getQuantity() == baseDimension;
   @ post Unit.getStandardUnit(baseDimension) == this;
   @*/
  public BaseUnit(String name, String symbol, BaseDimension baseDimension) throws InitializationException {
    super(name, symbol, baseDimension);
    Unit.addStandardUnit(this);
  }

/**************
 * CONVERSION *
 **************/

 /*@
   @ also public behavior
   @
   @ post \result == true;
   @*/
  public /*@ pure @*/ boolean convertsIsomorphToBaseUnit() {
    return true;
  }
  
  /**
   * See superclass
   */
 /*@
   @ also public behavior
   @
   @ post \result == value;
   @*/
  public final /*@ pure @*/ double convertToBase(double value) {
    return value;
  }

  /**
   * See superclass
   */
 /*@
   @ also public behavior
   @
   @ post \result == value;
   @*/
  public final /*@ pure @*/ double convertFromBase(double value) {
    return value;
  }

 /*@
   @ also public behavior
   @
   @ post \result == this;
   @*/
  public /*@ pure @*/ Unit getBaseUnit() {
    return this;
  }
}

