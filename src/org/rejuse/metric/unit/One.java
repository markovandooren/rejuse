package org.rejuse.metric.unit;

import org.rejuse.metric.dimension.Dimensionless;
import java.util.Set;
import java.util.TreeSet;

/**
 * A class representing the unit of the dimensionless dimension.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @author  Marko van Dooren
 */
public final class One extends NonCompositeUnit {

 /*@
   @ public invariant hashCode() == 0;
   @*/

  /**
   * Initialize a new One.
   */
 /*@
   @ private behavior
   @
   @ post getName() == "one";
   @ post getSymbol() == "1";
   @ post hashCode() == 0;
   @*/
  private One() {
    super("one", "1", Dimensionless.getPrototype(), 0);
    Unit.init(this);
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
  public /*@ pure @*/ double convertToBase(double value) {
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
  public /*@ pure @*/ double convertFromBase(double value) {
    return value;
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
   @ post \result == 0;
   @*/
  public /*@ pure @*/ double getExponent(SpecialUnit baseUnit) {
    return 0;
  }

  /**
   * See superclass
   */
 /*@
   @ also public behavior
   @
   @ post \result.isEmpty();
   @*/
  public /*@ pure @*/ Set getSpecialUnits() {
    return new TreeSet();
  }

  /**
   * See superclass
   */
 /*@
   @ also public behavior
   @
   @ post \result == (other == this);
   @*/
  public final /*@ pure @*/ boolean equals(Object other) {
    return (other == this);
  }

  /**
   * Return the prototype of this class.
   */
 /*@
   @ public behavior
   @
   @ post \result == PROTOTYPE;
   @*/
  public static /*@ pure @*/ One getPrototype() {
    return PROTOTYPE;
  }

 /*@
   @ also public behavior
   @
   @ post \result == this;
   @*/
  public /*@ pure @*/ Unit getBaseUnit() {
    return this;
  }

  /*@
    @ public invariant PROTOTYPE != null;
    @*/
  public final static One PROTOTYPE = new One();
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
