package org.aikodi.rejuse.metric.unit;

import org.aikodi.rejuse.metric.Prefix;
import org.aikodi.rejuse.metric.dimension.Dimension;

/**
 * @author Marko van Dooren
 */
public abstract class NonCompositeUnit extends Unit {
  /**
   * Initialize a new unit with the given name, symbol and quantity.
   *
   * @param name
   *        The name of the new Unit.
   * @param symbol
   *        The symbol of the new Unit.
   * @param quantity
   *        The quantity of the new Unit.
   * @param hashCode
   *        The hash code of the new Unit.
   */
 /*@
   @ protected behavior
   @
   @ pre name != null;
   @ pre symbol != null;
   @ pre quantity != null;
   @
   @ post getName() == name;
   @ post getSymbol() == symbol;
   @ post getQuantity() == quantity;
   @ post hashCode() == hashCode;
   @*/
  protected NonCompositeUnit(String name, String symbol, Dimension quantity, int hashCode) {
    super(name, symbol, quantity, hashCode);
  }

  /**
   * see superclass
   */
  public /*@ pure @*/ Unit prefix(Prefix prefix) {
    return new PrefixUnit(this, prefix);
  }
  

}
