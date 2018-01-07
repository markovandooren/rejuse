package org.aikodi.rejuse.metric.dimension;

import java.util.HashSet;
import java.util.Set;

import org.aikodi.rejuse.predicate.SafePredicate;

/**
 * A class representing the dimensionless dimension.
 *
 * @version $Revision$
 * @date    $Date$
 * @author  Marko van Dooren
 */
public final class Dimensionless extends BasicDimension {

 /*@
   @ public invariant getName().equals("dimensionless");
   @*/

  /**
   * Initialize a new Dimensionless object.
   */
 /*@
   @ private behavior
   @
   @ post getName().equals("dimensionless");
   @ post hashCode() == 0;
   @*/
  private Dimensionless() {
    super("dimensionless", 0);
    Dimension.initializeDimensions(this);
  }

  /**
   * See superclass
   */
 /*@
   @ also public behavior
   @
   @ post \result == (other == this);
   @*/
  public final /*@ pure @*/ boolean equals(final Object other) {
    // There can only be on instance of this class.
    return (other == this) || 
           (
            (other != null) &&
            (other instanceof Dimension) &&
             new SafePredicate<BaseDimension>() {
               public boolean eval(BaseDimension o) {
                 return ((Dimension)other).exponentOf((BaseDimension)o) == 0;
               }
             }.forAll(((Dimension)other).baseDimensions())
           );
  }

  /**
   * See superclass
   */
 /*@
   @ also public behavior
   @
   @ post \result == 0;
   @*/
  public /*@ pure @*/ int exponentOf(BaseDimension dimension) {
    return 0;
  }

  /**
   * See superclass
   */
 /*@
   @ also public behavior
   @
   @ post \result.size() == 0;
   @*/
  public /*@ pure @*/ Set<BaseDimension> baseDimensions() {
    return new HashSet<>();
  }

  /**
   * Return the prototype of this class.
   */
 /*@
   @ public behavior
   @
   @ post \result == PROTOTYPE;
   @*/
  public static /*@ pure @*/ Dimensionless instance() {
    return PROTOTYPE;
  }

  /*@
    @ public invariant PROTOTYPE != null;
    @*/
  private final static Dimensionless PROTOTYPE = new Dimensionless();
}

