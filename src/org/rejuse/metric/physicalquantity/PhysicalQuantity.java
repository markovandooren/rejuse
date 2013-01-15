package org.rejuse.metric.physicalquantity;

import org.rejuse.metric.unit.Unit;
import org.rejuse.metric.unit.ConversionException;

/**
 * @version $Revision$
 * @date    $Date$
 * @author  Marko van Dooren
 */
public abstract class PhysicalQuantity {

  /**
   * Initialize a new physical quantity with the given unit
   * and value.
   *
   * @param unit
   *        The unit of the new physical quantity.
   * @param value
   *        The value of the new physical quantity expressed
   *        in the given unit.
   */
 /*@
   @ public behavior
   @
   @ post getUnit() == unit;
   @ post getValue() == value;
   @*/
  public /*@ pure @*/ PhysicalQuantity(Unit unit, double value) {
    _unit = unit;
    _value = value;
  }

/********
 * UNIT *
 ********/

  /**
   * Return the unit of this PhysicalQuantity.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
  public /*@ pure @*/ Unit getUnit() {
    return _unit;
  }

  /**
   * Set the unit of this physical quantity.
   *
   * @param unit
   *        The new unit of this physical quantity.
   */
 /*@
   @ protected behavior
   @
   @ pre unit != null;
   @
   @ post getUnit() == unit;
   @*/
  protected void setUnit(Unit unit) {
    _unit = unit;
  }

 /*@
   @ private invariant _unit != null;
   @*/
  private Unit _unit;

/*********
 * VALUE *
 *********/

  /**
   * Return the value of this physical quantity in its current unit.
   */
  public /*@ pure @*/ double getValue() {
    return _value;
  }

  /**
   * Set the value of this physical quantity to the given value.
   *
   * @param value
   *        The new value for this physical quantity.
   */
 /*@
   @ protected behavior
   @
   @ post getValue() == value;
   @*/
  protected void setValue(double value) {
    _value = value;
  }

  /**
   * The value of this physical quantity.
   */
  private double _value;

/***************
 * COMPUTATION *
 ***************/

  /**
   * Return the product of this physical quantity and the
   * given other physical quantity.
   *
   * @param other
   *        The physical quantity this physical quantity
   *        has to be multiplied with.
   */
 /*@
   @ public behavior
   @
   @ pre other != null;
   @
   @ post \fresh(\result);
   @ post \result.getUnit().equals(getUnit().times(other.getUnit()));
   @ post \result.getValue() == getValue() * other.getValue();
   @*/
  public abstract /*@ pure @*/ PhysicalQuantity times(PhysicalQuantity other);

  /**
   * Multiply this physical quantity with the given physical
   * quantity
   *
   * @param other
   *        The physical quantity to multiply this one with.
   */
 /*@
   @ public behavior
   @
   @ pre other != null;
   @
   @ post getUnit().equals(getUnit().times(other.getUnit()));
   @ post getValue() == (getValue() * other.getValue());
   @*/
  public abstract void multiply(PhysicalQuantity other);

  /**
   * Return the division of this physical quantity by the
   * given other physical quantity.
   *
   * @param other
   *        The physical quantity this physical quantity
   *        has to be divided by.
   */
 /*@
   @ public behavior
   @
   @ pre other != null;
   @
   @ post \fresh(\result);
   @ post \result.getUnit().equals(getUnit().dividedBy(other.getUnit()));
   @ post \result.getValue() == getValue() / other.getValue();
   @*/
  public abstract /*@ pure @*/ PhysicalQuantity dividedBy(PhysicalQuantity other);


  /**
   * Multiply this physical quantity with the given physical
   * quantity
   *
   * @param other
   *        The physical quantity to multiply this one with.
   */
 /*@
   @ public behavior
   @
   @ pre other != null;
   @
   @ post getUnit().equals(getUnit().dividedBy(other.getUnit()));
   @ post getValue() == (getValue() * other.getValue());
   @*/
  public abstract void divide(PhysicalQuantity other);

  /**
   * Return the sum of this physical quantity and the
   * given other physical quantity.
   *
   * @param other
   *        The physical quantity that has to be added
   *        to this physical quantity.
   */
 /*@
   @ public behavior
   @
   @ pre other != null;
   @ pre getUnit().isCompatibleWith(other.getUnit());
   @
   @ post \fresh(\result);
   @ post \result.getValue() == getValue() + other.getValueAs(getUnit());
   @
   @ signals (ConversionException) (* An error occurred during the conversion *);
   @*/
  public abstract /*@ pure @*/ PhysicalQuantity plus(PhysicalQuantity other);

  /**
   * Add the given physical quantity to this quantity.
   *
   * @param other
   *        The physical quantity that has to be added
   *        to this physical quantity.
   */
 /*@
   @ public behavior
   @
   @ pre other != null;
   @ pre getUnit().isCompatibleWith(other.getUnit());
   @
   @ post getValue() == \old(getValue()) + other.getValueAs(getUnit());
   @
   @ signals (ConversionException) (* An error occurred during the conversion *);
   @*/
  public abstract void add(PhysicalQuantity other);

  /**
   * Return the difference of this physical quantity and the
   * given other physical quantity.
   *
   * @param other
   *        The physical quantity that has to be subtracted
   *        from this physical quantity.
   */
 /*@
   @ public behavior
   @
   @ pre other != null;
   @
   @ post \fresh(\result);
   @ post \result.getValue() == getValue() - other.getValueAs(getUnit());
   @
   @ signals (ConversionException) (* An error occurred during the conversion *);
   @*/
  public abstract /*@ pure @*/ PhysicalQuantity minus(PhysicalQuantity other);

  /**
   * Subtract the given physical quantity from this quantity.
   *
   * @param other
   *        The physical quantity that has to be subtracted
   *        from this physical quantity.
   */
 /*@
   @ public behavior
   @
   @ pre other != null;
   @ pre getUnit().isCompatibleWith(other.getUnit());
   @
   @ post getValue() == \old(getValue()) + other.getValueAs(getUnit());
   @
   @ signals (ConversionException) (* An error occurred during the conversion *);
   @*/
  public abstract void subtract(PhysicalQuantity other);

/**************
 * CONVERSION *
 **************/

  /**
   * Return the value of this unit expressed as a value of the
   * given unit.
   *
   * @param unit
   *        The unit in which the value of this physical quantity
   *        must be expressed.
   */
 /*@
   @ public behavior
   @
   @ pre unit != null;
   @ pre getUnit().isCompatibleWith(unit);
   @ 
   @ signals (ConversionException) ! getUnit().convertsIsomorphTo(unit); 
   @*/
  public abstract /*@ pure @*/ double getValueAs(Unit unit) throws ConversionException; 

  public abstract /*@ pure @*/ void convertTo(Unit unit) throws ConversionException;

}

