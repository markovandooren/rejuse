package org.aikodi.rejuse.metric.physicalquantity;

import org.aikodi.rejuse.metric.unit.ConversionException;
import org.aikodi.rejuse.metric.unit.Unit;

/**
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @author  Marko van Dooren
 */
public class FastQuantity extends PhysicalQuantity {
  
  /**
   * Initialize a new fast physical quantity with the given unit
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
  public /*@ pure @*/ FastQuantity(Unit unit, double value) {
    super(unit, value);
  }

 /*@
   @ also public behavior
   @
   @ post \result instanceof FastQuantity;
   @*/
  public /*@ pure @*/ PhysicalQuantity times(PhysicalQuantity other) {
    return new FastQuantity(getUnit().times(other.getUnit()), getValue() * other.getValue());
  }

  /**
   * See superclass
   */
  public void multiply(PhysicalQuantity other) {
    setValue(getValue() * other.getValue());
    setUnit(getUnit().times(other.getUnit()));
  }
  
 /*@
   @ also public behavior
   @
   @ post \result instanceof FastQuantity;
   @*/
  public /*@ pure @*/ PhysicalQuantity dividedBy(PhysicalQuantity other) {
    return new FastQuantity(getUnit().dividedBy(other.getUnit()), getValue() / other.getValue());
  }

  /**
   * See superclass
   */
  public void divide(PhysicalQuantity other) {
    setValue(getValue() / other.getValue());
    setUnit(getUnit().dividedBy(other.getUnit()));
  }
  
 /*@
   @ also public behavior
   @
   @ post \result instanceof FastQuantity;
   @*/
  public /*@ pure @*/ PhysicalQuantity plus(PhysicalQuantity other) {
    return new FastQuantity(getUnit(), getValue() + other.getValue());
  }

  /**
   * See superclass
   */
  public void add(PhysicalQuantity other) {
    setValue(getValue() + other.getValue());
  }
  
 /*@
   @ also public behavior
   @
   @ post \result instanceof FastQuantity;
   @*/
  public /*@ pure @*/ PhysicalQuantity minus(PhysicalQuantity other) {
    return new FastQuantity(getUnit(), getValue() + other.getValue());
  }

  /**
   * See superclass
   */
  public void subtract(PhysicalQuantity other) {
    setValue(getValue() + other.getValue());
  }

  /**
   * See superclass
   */
  public /*@ pure @*/ double getValueAs(Unit unit) throws ConversionException {
    return getUnit().convertTo(unit, getValue());
  }

  public void convertTo(Unit unit) throws ConversionException {
    setValue(getValueAs(unit));
    setUnit(unit);
  }
}

