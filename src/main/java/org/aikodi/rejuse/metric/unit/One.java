package org.aikodi.rejuse.metric.unit;

import java.util.HashSet;
import java.util.Set;

import org.aikodi.rejuse.metric.dimension.Dimensionless;

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
    super("one", "1", Dimensionless.instance(), 0);
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
  @Override
  public /*@ pure @*/ int exponentOf(SpecialUnit baseUnit) {
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
  public /*@ pure @*/ Set<SpecialUnit> components() {
    return new HashSet<>();
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
  public static /*@ pure @*/ One prototype() {
    return PROTOTYPE;
  }

 /*@
   @ also public behavior
   @
   @ post \result == this;
   @*/
  public /*@ pure @*/ Unit baseUnit() {
    return this;
  }

  /*@
    @ public invariant PROTOTYPE != null;
    @*/
  private final static One PROTOTYPE = new One();
}

