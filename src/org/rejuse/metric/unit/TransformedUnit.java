package org.rejuse.metric.unit;

/**
 * @version $Revision$
 * @date    $Date$
 * @author  Marko van Dooren
 */
public class TransformedUnit extends SpecialUnit {
  
 /*@
   @ public invariant getQuantity() == getUnit().getQuantity()
   @*/

  /**
   * Initialize a new transformed unit with the given base unit, name, symbol
   * , factor and offset.
   *
   * @param unit
   *        The base unit for the new transformed unit.
   * @param name
   *        The name for the new transformed unit.
   * @param symbol
   *        The symbol for the new transformed unit.
   * @param factor
   *        The factor for the new transformed unit.
   * @param offset
   *        The offset for the new transformed unit.
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
   @ post getOffset() == offset;
   @*/
  public TransformedUnit(NonCompositeUnit unit, String name, String symbol, double factor, double offset) {
    super(name, symbol, unit.getDimension());
    _unit = unit;
    _offset = offset;
    _factor = factor;
  }

/*****************
 * STANDARD UNIT *
 *****************/

 /*@
   @ also public behavior
   @
   @ post \result == getBaseUnit().getStandardUnit();
   @*/
  public Unit getBaseUnit() {
    return _unit.getBaseUnit();
  }

/**************
 * CONVERSION *
 **************/

 /*@
   @ also public behavior
   @
   @ post \result == (getOffset() == 0) &&
   @                 (getUnit().convertsIsomorphToBaseUnit());
   @*/
  public /*@ pure @*/ boolean convertsIsomorphToBaseUnit() {
    return (getOffset() == 0) && (_unit.convertsIsomorphToBaseUnit());
  }

 /*@
   @ also public behavior
   @
   @ post \result == getUnit().convertToStandard((value - getOffset())/getFactor());
   @*/
  public /*@ pure @*/ double convertToBase(double value) throws ConversionException {
    return _unit.convertToBase((value - _offset)/_factor);
  }

 /*@
   @ also public behavior
   @
   @ post \result == getFactor() * getUnit().convertFromStandard() + getOffset();
   @*/
  public /*@ pure @*/ double convertFromBase(double value) throws ConversionException {
    return (_factor * _unit.convertFromBase(value)) + _offset;
  }

/*************
 * BASE UNIT *
 *************/

  /**
   * Return the base unit of which this unit is a transformed version.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
  public /*@ pure @*/ NonCompositeUnit getUnit() {
    return _unit;
  }

  /**
   * The base unit of this transformed unit.
   */
 /*@
   @ private invariant _unit != null;
   @*/
  private NonCompositeUnit _unit;

/**********
 * OFFSET *
 **********/

  /**
   * Return the offset of this transformed unit
   */
  public /*@ pure @*/ double getOffset() {
    return _offset;
  }

  /**
   * The offset of this transformed unit.
   */
  private double _offset;

/**********
 * FACTOR *
 **********/

  /**
   * Return the factor of this transformed unit
   */
  public /*@ pure @*/ double getFactor() {
    return _factor;
  }

  /**
   * The factor of this transformed unit
   */
  private double _factor;

}
