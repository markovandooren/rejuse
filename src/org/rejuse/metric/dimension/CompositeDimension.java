package org.rejuse.metric.dimension;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import org.rejuse.java.collections.MapAccumulator;
import org.rejuse.predicate.SafePredicate;

/**
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 */
public class CompositeDimension extends Dimension {

  /**
   * Initialize a new CompositeDimension with the given name and
   * (BaseDimension -> Double) map.
   *
   * @param name
   *        The name of the new CompositeDimension
   * @param map
   *        A map containing (BaseDimension, Double) pairs.
 /*@
   @ public behavior
   @
   @ pre name != null;
   @ pre validDimensionMap(map);
   @
   @ post getName().equals(name);
   @ post (\forall Map.Entry e; map.entrySet().contains(e);
   @        getExponent((BaseDimension)e.getKey()) == ((Double)e.getValue()).doubleValue());
   @*/
  public CompositeDimension(String name, Map map) {
    super(name, getHashCode(map));
    _map = new HashMap();
    _map.putAll(map);
    ensureDimension(this);
  }
  
  /**
   * Return the hashcode for the given map
   *
   * @param map
   *        The map for which the hashcode must be generated.
   */
 /*@
   @ private behavior
   @
   @ pre validDimensionMap(map);
   @
   @ post true;
   @*/
  private static int getHashCode(Map map) {
    return ((Integer)new MapAccumulator() {
      public Object initialAccumulator() {
        return new Integer(0);
      }

      public Object accumulate(Object key, Object value, Object acc) {
        return new Integer(
          (key.hashCode() * ((Double)value).intValue()) +
          ((Integer) acc).intValue()
        );
      }
    }.accumulate(map)).intValue();
  }

  /**
   * Initialize a new CompositeDimension with the given name, BaseDimension
   * and exponent.
   *
   * @param name
   *        The name of the new CompositeDimension
   * @param baseDimension
   *        The only base dimension that will have a non-null exponent.
   * @param exponent
   *        The exponent of the only non-null base dimension.
   */
 /*@
   @ public behavior
   @
   @ pre name != null;
   @ pre baseDimension != null;
   @ pre exponent != 0;
   @
   @ post getName().equals(name);
   @ post getExponent(baseDimension) == exponent;
   @ post getBaseDimensions().size() == 1;
   @*/
  public CompositeDimension(String name, BaseDimension baseDimension, double exponent) {
    super(name, baseDimension.hashCode() * ((int)exponent));
    _map = new HashMap();
    _map.put(baseDimension, new Double(exponent));
    ensureDimension(this);
  }

  /**
   * Initialize a new CompositeDimension with the given name, base dimensions
   * and exponents.
   *
   * @param name
   *        The name of the new CompositeDimension
   * @param baseDimension1
   *        A base dimension that will have a non-null exponent.
   * @param exponent1
   *        The exponent of the first non-null base dimension.
   * @param baseDimension2
   *        A base dimension that will have a non-null exponent.
   * @param exponent2
   *        The exponent of the second non-null base dimension.
   */
 /*@
   @ public behavior
   @
   @ pre name != null;
   @ pre baseDimension1 != null;
   @ pre baseDimension2 != null;
   @ pre exponent1 != 0;
   @ pre exponent2 != 0;
   @ pre baseDimension1 != baseDimension2;
   @
   @ post getName().equals(name);
   @ post getExponent(baseDimension1) == exponent1;
   @ post getExponent(baseDimension2) == exponent2;
   @ post getBaseDimensions().size() == 2;
   @*/
  public CompositeDimension(String name, 
                           BaseDimension baseDimension1, double exponent1, 
                           BaseDimension baseDimension2, double exponent2) {
    super(name, 
          (baseDimension1.hashCode() * ((int)exponent1)) +
          (baseDimension2.hashCode() * ((int)exponent2))
         );
    _map = new HashMap();
    _map.put(baseDimension1, new Double(exponent1));
    _map.put(baseDimension2, new Double(exponent2));
    ensureDimension(this);
  }

  /**
   * Initialize a new CompositeDimension with the given name, base dimensions
   * and exponents.
   *
   * @param name
   *        The name of the new CompositeDimension
   * @param baseDimension1
   *        A base dimension that will have a non-null exponent.
   * @param exponent1
   *        The exponent of the first non-null base dimension.
   * @param baseDimension2
   *        A base dimension that will have a non-null exponent.
   * @param exponent2
   *        The exponent of the second non-null base dimension.
   * @param baseDimension3
   *        A base dimension that will have a non-null exponent.
   * @param exponent3
   *        The exponent of the third non-null base dimension.
   */
 /*@
   @ public behavior
   @
   @ pre name != null;
   @ pre baseDimension1 != null;
   @ pre baseDimension2 != null;
   @ pre baseDimension3 != null;
   @ pre exponent1 != 0;
   @ pre exponent2 != 0;
   @ pre exponent3 != 0;
   @ pre baseDimension1 != baseDimension2;
   @ pre baseDimension1 != baseDimension3;
   @ pre baseDimension2 != baseDimension3;
   @
   @ post getName().equals(name);
   @ post getExponent(baseDimension1) == exponent1;
   @ post getExponent(baseDimension2) == exponent2;
   @ post getExponent(baseDimension3) == exponent3;
   @ post getBaseDimensions().size() == 3;
   @*/
  public CompositeDimension(String name, 
                           BaseDimension baseDimension1, double exponent1, 
                           BaseDimension baseDimension2, double exponent2,
                           BaseDimension baseDimension3, double exponent3) {
    super(name, 
          (baseDimension1.hashCode() * ((int)exponent1)) +
          (baseDimension2.hashCode() * ((int)exponent2)) +
          (baseDimension3.hashCode() * ((int)exponent3))
         );
    _map = new HashMap();
    _map.put(baseDimension1, new Double(exponent1));
    _map.put(baseDimension2, new Double(exponent2));
    _map.put(baseDimension3, new Double(exponent3));
    ensureDimension(this);
  }

  /**
   * Initialize a new CompositeDimension with the given name, base dimensions
   * and exponents.
   *
   * @param name
   *        The name of the new CompositeDimension
   * @param baseDimension1
   *        A base dimension that will have a non-null exponent.
   * @param exponent1
   *        The exponent of the first non-null base dimension.
   * @param baseDimension2
   *        A base dimension that will have a non-null exponent.
   * @param exponent2
   *        The exponent of the second non-null base dimension.
   * @param baseDimension3
   *        A base dimension that will have a non-null exponent.
   * @param exponent3
   *        The exponent of the third non-null base dimension.
   * @param baseDimension4
   *        A base dimension that will have a non-null exponent.
   * @param exponent4
   *        The exponent of the fourth non-null base dimension.
   */
 /*@
   @ public behavior
   @
   @ pre name != null;
   @ pre baseDimension1 != null;
   @ pre baseDimension2 != null;
   @ pre baseDimension3 != null;
   @ pre baseDimension4 != null;
   @ pre exponent1 != 0;
   @ pre exponent2 != 0;
   @ pre exponent3 != 0;
   @ pre exponent4 != 0;
   @ pre baseDimension1 != baseDimension2;
   @ pre baseDimension1 != baseDimension3;
   @ pre baseDimension1 != baseDimension4;
   @ pre baseDimension2 != baseDimension3;
   @ pre baseDimension2 != baseDimension4;
   @ pre baseDimension3 != baseDimension4;
   @
   @ post getName().equals(name);
   @ post getExponent(baseDimension1) == exponent1;
   @ post getExponent(baseDimension2) == exponent2;
   @ post getExponent(baseDimension3) == exponent3;
   @ post getExponent(baseDimension4) == exponent4;
   @ post getBaseDimensions().size() == 4;
   @*/
  public CompositeDimension(String name, 
                           BaseDimension baseDimension1, double exponent1, 
                           BaseDimension baseDimension2, double exponent2,
                           BaseDimension baseDimension3, double exponent3,
                           BaseDimension baseDimension4, double exponent4) {
    super(name, 
          (baseDimension1.hashCode() * ((int)exponent1)) +
          (baseDimension2.hashCode() * ((int)exponent2)) +
          (baseDimension3.hashCode() * ((int)exponent3)) +
          (baseDimension4.hashCode() * ((int)exponent4))
         );
    _map = new HashMap();
    _map.put(baseDimension1, new Double(exponent1));
    _map.put(baseDimension2, new Double(exponent2));
    _map.put(baseDimension3, new Double(exponent3));
    _map.put(baseDimension4, new Double(exponent4));
    ensureDimension(this);
  }

  /**
   * See superclass.
   */
  public /*@ pure @*/ double getExponent(BaseDimension base) {
    Double result = (Double) _map.get(base);
    if(result == null) {
      // The given base dimension is not in the map
      // so it has exponent 0.
      return 0;
    }
    else {
      return result.doubleValue();
    }
  }

  /**
   * See superclass
   */
  public /*@ pure @*/ Set getBaseDimensions() {
    return _map.keySet();
  }

  /**
   * See superclass
   */
  public /*@ pure @*/ boolean equals(final Object other) {
    Set baseDimensions = getBaseDimensions();
    return (other == this) ||
           (
            (other instanceof Dimension) &&
            (baseDimensions.size() == ((Dimension)other).getBaseDimensions().size()) &&
            (new SafePredicate() {
              public /*@ pure @*/ boolean eval(Object o) {
                BaseDimension base = (BaseDimension)o;
                return getExponent(base) == ((Dimension)other).getExponent(base);
              }
            }.forall(baseDimensions))
           );
  }
  
  /*@
    @ private invariant _map != null;
    @*/
  private HashMap _map;
}

