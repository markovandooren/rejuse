package org.rejuse.metric.dimension;

import java.util.Set;
import java.util.TreeSet;
import org.rejuse.predicate.PrimitiveTotalPredicate;

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
             new PrimitiveTotalPredicate() {
               public boolean eval(Object o) {
                 return ((Dimension)other).getExponent((BaseDimension)o) == 0;
               }
             }.forall(((Dimension)other).getBaseDimensions())
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
  public /*@ pure @*/ double getExponent(BaseDimension dimension) {
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
  public /*@ pure @*/ Set getBaseDimensions() {
    return new TreeSet();
  }

  /**
   * Return the prototype of this class.
   */
 /*@
   @ public behavior
   @
   @ post \result == PROTOTYPE;
   @*/
  public static /*@ pure @*/ Dimensionless getPrototype() {
    return PROTOTYPE;
  }

  /*@
    @ public invariant PROTOTYPE != null;
    @*/
  public final static Dimensionless PROTOTYPE = new Dimensionless();
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
