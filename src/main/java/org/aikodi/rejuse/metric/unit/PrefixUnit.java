package org.aikodi.rejuse.metric.unit;

import org.aikodi.rejuse.metric.Prefix;

/**
 * @author Marko van Dooren
 */
public class PrefixUnit extends TransformedUnit {
  
  /**
   * Initialize a new prefix unit with the given base unit, name, symbol
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
   @ pre prefix != null;
   @
   @ post getUnit() == unit;
   @ post getPrefix() == prefix;
   @*/
  PrefixUnit(NonCompositeUnit unit, Prefix prefix) {
    super(unit, prefix.getName() + unit.name(), prefix.getSymbol()+unit.symbol(), prefix.getFactor(), 0);
    _prefix = prefix;
  }

  /**
   * Return the prefix of this prefix unit.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
  public Prefix prefix() {
    return _prefix;
  }

  private Prefix _prefix;
  
}
