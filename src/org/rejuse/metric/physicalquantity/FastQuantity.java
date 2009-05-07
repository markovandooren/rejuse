package org.rejuse.metric.physicalquantity;

import org.rejuse.metric.unit.Unit;
import org.rejuse.metric.unit.ConversionException;

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
