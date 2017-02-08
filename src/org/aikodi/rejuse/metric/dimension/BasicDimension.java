package org.aikodi.rejuse.metric.dimension;

/**
 * <p>A class of dimensions that only consist of themselves or nothing.
 * The difference with BaseDimension is that this class also contains
 * a dimension that always returns 0 for 
 * <a href="Dimension.html#getExponent(org.rejuse.metric.BaseDimension)"><code>getExponent()</code></a>.
 *
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @author  Marko van Dooren
 */
public abstract class BasicDimension extends Dimension {
  
 /*@
   @ // Objects of this class return only 1 for getExponent() when given
   @ // themselves as a parameter, otherwise the result is 0.
   @ public invariant (\forall BasicDimension basic; basic != null;
   @                    (\forall BaseDimension base; base != null;
   @                      (base != basic ==> basic.getExponent(base) == 0) &&
   @                      (base == basic ==> basic.getExponent(base) == 1)));
   @*/

  /**
   * Initialize a new basic dimension with the given name.
   *
   * @param name
   *        The name of the new basic dimension.
   * @param hashCode
   *        The hash code of the new basic dimension.
   */
 /*@
   @ protected behavior
   @
   @ pre name != null;
   @
   @ post getName() == name;
   @ post hashCode() == hashCode;
   @*/
  protected BasicDimension(String name, int hashCode) {
    super(name, hashCode);
  }
}

