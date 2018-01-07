package org.aikodi.rejuse.metric.dimension;

import static org.aikodi.contract.Contract.requireNotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.aikodi.rejuse.predicate.SafePredicate;

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
  public CompositeDimension(String name, Map<BaseDimension, Integer> map) {
    super(name, getHashCode(map));
    _map = new HashMap<>();
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
  private static int getHashCode(Map<BaseDimension, Integer> map) {
	requireNotNull(map);
	
	return map.entrySet().stream().mapToInt(e -> e.getKey().hashCode() * e.getValue()).sum();
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
  public CompositeDimension(String name, BaseDimension baseDimension, int exponent) {
    super(name, baseDimension.hashCode() * exponent);
    _map = new HashMap<>();
    _map.put(baseDimension, exponent);
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
                           BaseDimension baseDimension1, int exponent1, 
                           BaseDimension baseDimension2, int exponent2) {
    super(name, 
          (baseDimension1.hashCode() * exponent1) +
          (baseDimension2.hashCode() * exponent2)
         );
    _map = new HashMap<>();
    _map.put(baseDimension1, exponent1);
    _map.put(baseDimension2, exponent2);
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
                           BaseDimension baseDimension1, int exponent1, 
                           BaseDimension baseDimension2, int exponent2,
                           BaseDimension baseDimension3, int exponent3) {
    super(name, 
          (baseDimension1.hashCode() * exponent1) +
          (baseDimension2.hashCode() * exponent2) +
          (baseDimension3.hashCode() * exponent3)
         );
    _map = new HashMap<>();
    _map.put(baseDimension1, exponent1);
    _map.put(baseDimension2, exponent2);
    _map.put(baseDimension3, exponent3);
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
                           BaseDimension baseDimension1, int exponent1, 
                           BaseDimension baseDimension2, int exponent2,
                           BaseDimension baseDimension3, int exponent3,
                           BaseDimension baseDimension4, int exponent4) {
    super(name, 
          (baseDimension1.hashCode() * ((int)exponent1)) +
          (baseDimension2.hashCode() * ((int)exponent2)) +
          (baseDimension3.hashCode() * ((int)exponent3)) +
          (baseDimension4.hashCode() * ((int)exponent4))
         );
    _map = new HashMap<>();
    _map.put(baseDimension1, exponent1);
    _map.put(baseDimension2, exponent2);
    _map.put(baseDimension3, exponent3);
    _map.put(baseDimension4, exponent4);
    ensureDimension(this);
  }

  /**
   * See superclass.
   */
  public /*@ pure @*/ int exponentOf(BaseDimension base) {
    Integer result = _map.get(base);
    return result == null ? 0 : result;
  }

  /**
   * See superclass
   */
  public /*@ pure @*/ Set<BaseDimension> baseDimensions() {
    return _map.keySet();
  }

  /**
   * See superclass
   */
  public /*@ pure @*/ boolean equals(final Object other) {
    Set<BaseDimension> baseDimensions = baseDimensions();
    return (other == this) ||
           (
            (other instanceof Dimension) &&
            (baseDimensions.size() == ((Dimension)other).baseDimensions().size()) &&
            (new SafePredicate<BaseDimension>() {
              public /*@ pure @*/ boolean eval(BaseDimension o) {
                BaseDimension base = (BaseDimension)o;
                return exponentOf(base) == ((Dimension)other).exponentOf(base);
              }
            }.forAll(baseDimensions))
           );
  }
  
  /*@
    @ private invariant _map != null;
    @*/
  private Map<BaseDimension, Integer> _map;
}

