package org.rejuse.metric.unit;

/**
 * @author Marko van Dooren
 */
public class ScaledUnit extends TransformedUnit {

 /*@
   @ public invariant getOffSet() == 0; 
   @*/

  /**
   * Initialize a new scaled unit with the given base unit, name, symbol
   * and factor.
   *
   * @param unit
   *        The base unit for the new transformed unit.
   * @param name
   *        The name for the new transformed unit.
   * @param symbol
   *        The symbol for the new transformed unit.
   * @param factor
   *        The factor for the new transformed unit.
   */
 /*@
   @ public behavior
   @
   @ pre unit != null;
   @ pre name != null;
   @ pre symbol != null;
   @ pre factor != 0;
   @
   @ post getUnit() == unit;
   @ post getName() == name;
   @ post getSymbol() == symbol;
   @ post getFactor() == factor;
   @ post getOffset() == 0;
   @*/
  public ScaledUnit(NonCompositeUnit unit, String name, String symbol, double factor) {
    super(unit, name, symbol, factor, 0);
  }

}
