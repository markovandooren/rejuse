package org.aikodi.rejuse.metric.dimension;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.aikodi.rejuse.java.collections.Sets;

/**
 * <p>This is a class of base dimensions. Base dimensions are 'composed' of only
 * themselves and can be used to create other dimensions.</p>
 *
 * <p>In order to keep things a bit efficient, a base dimension is only equal
 * to itself or a composite dimension that is only composed of that base dimension
 * with 1 as exponent. Note that this situation can only occur if you construct
 * that composite unit manually; the framework tries to keep only a single object
 * for each dimension.</p>
 *
 * @author  Marko van Dooren
 */
public class BaseDimension extends BasicDimension {
  
  /**
   * Initialize a new BaseDimension with the given name.
   *
   * @param name
   *        The name of the new BaseDimension.
   */
 /*@
   @ public behavior
   @
   @ pre name != null;
   @
   @ post getName() == name;
   @*/
  public BaseDimension(String name) {
    super(name, Dimension.getBaseDimensionHashCode());
    ensureDimension(this);
  }

  /**
   * {@inheritDoc}
   */
  public final /*@ pure @*/ boolean equals(Object other) {
    return (other == this) || 
           (
             (other instanceof Dimension) && 
             (! (other instanceof BaseDimension)) &&
             (((Dimension)other).baseDimensions().size() == 1) &&
             (((Dimension)other).exponentOf(this) == 1)
           );
  }

  /**
   * See superclass
   */
 /*@
   @ also public behavior
   @
   @ post (this.equals(base)) ==> \result == 1;
   @ post (! this.equals(base)) ==> \result == 0;
   @*/
  public /*@ pure @*/ int exponentOf(BaseDimension base) {
    return this.equals(base) ? 1 : 0;
  }

  /**
   * See superclass
   */
  public /*@ pure @*/ Set<BaseDimension> baseDimensions() {
    return Sets.of(this); 
  }

  /**
   * See superclass
   */
  protected /*@ pure @*/ Dimension makeInverse() {
    Map<BaseDimension, Integer> map = new HashMap<>();
    map.put(this, -1);
    return new CompositeDimension(createName(map), map);
  }
}

