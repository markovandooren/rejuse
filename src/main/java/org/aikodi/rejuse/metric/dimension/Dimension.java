package org.aikodi.rejuse.metric.dimension;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.aikodi.rejuse.predicate.SafePredicate;

/**
 * A class of dimensions.
 *
 * <p>From <a href="http://physics.nist.gov/cuu/Units/introduction.html">http://physics.nist.gov/cuu/Units/introduction.html</a>:</p>
 *
 * <p><i>A <b>dimension in the general sense</b> is a property ascribed to
 * phenomena, bodies, or substances that can be quantified for, or assigned to,
 * a particular phenomenon, body, or substance. Examples are mass and electric
 * charge.</i></p>
 *
 * <p><i>A <b>quantity in the particular sense</b> is a quantifiable or assignable property ascribed to a particular phenomenon, body, or substance. Examples are the mass of the moon and the electric charge of the proton.</i></p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @author  Marko van Dooren
 */
public abstract class Dimension {

 /*@
   @ public invariant (\forall Dimension q; (q != null) && (equals(q));
   @                     q.hashCode() == hashCode());
   @*/

  /**
   * Initialize a new dimension with the given name.
   *
   * @param name
   *        The name of the new dimension.
   * @param hashCode
   *        The hashCode of the new dimension.
   */
 /*@
   @ protected behavior
   @
   @ pre name != null;
   @
   @ post getName() == name;
   @*/
  protected Dimension(String name, int hashCode) {
    _hashCode = hashCode;
    _name = name;
    _productMap = new HashMap();
    _divisionMap = new HashMap();
    _powerMap = new HashMap();
  }

/********
 * NAME *
 ********/

  /**
   * Return the name of this Dimension.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
  public /*@ pure @*/ String name() {
    return _name;
  }

  /**
   * The name of this Dimension.
   */
 /*@
   @ private invariant _name != null;
   @*/
  private String _name;

/***************
 * COMPOSITION *
 ***************/

  /**
   * Return the exponent of the given base dimension in this Dimension.
   *
   * @param base
   *        The base dimension of which the exponent is requested.
   */
 /*@
   @ public behavior
   @
   @ pre base != null;
   @*/
  public abstract /*@ pure @*/ int exponentOf(BaseDimension base);

  /**
   * Return the set of base dimensions this dimension is
   * composed of.
   *
   */
 /*@
   @ public behavior
   @
   @ post \fresh(\result);
   @ post (\forall Object o; o != null;
   @       \result.contains(o) == 
   @       (o instanceof BaseDimension) && 
   @       (getExponent((BaseDimension)o) != 0));
   @*/
  public abstract /*@ pure @*/ Set<BaseDimension> baseDimensions();

  /**
   * See superclass
   */
 /*@
   @ also public behavior
   @
   @ post \result == (other instanceof Dimension) &&
   @                 (\forall BaseDimension b; b!= null;
   @                    getExponent(b) == ((BaseDimension)other).getExponent(b));
   @*/
  public abstract /*@ pure @*/ boolean equals(final Object other);

  /**
   * See superclass
   */
  public /*@ pure @*/ int hashCode() {
    return _hashCode;
  }
  
  /**
   * The hashcode of this dimension. We need this because
   * we use a HashMap to store the set of "all" dimensions. This
   * is needed in order to map equal dimensions on eachother.
   */
  private int _hashCode;
  
/***************
 * COMPUTATION *
 ***************

  /**
   * Return the inverse dimension of this Dimension.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ post (\forall BaseDimension b; b!= null;
   @       \result.getExponent(b) == -getExponent(b));
   @*/
  public /*@ pure @*/ Dimension inverse() {
    if(_inverse == null) {
      _inverse = makeInverse();
      _inverse = getPrototype(_inverse);
      // The next statement is not "safe". It can't introduce
      // any real problem, but we aren't sure this Dimension is
      // a prototype while _inverse now is a reference to a prototype.
      // It would be best to wait until inverse() is invoked on _inverse
      // if that hasn't already been done.
      //
      //_inverse.setInverse(this);
    }
    return _inverse;
  }

  /**
   * Construct a dimension that equals the inverse of this dimension.
   */
 /*@
   @ protected behavior
   @
   @ post \fresh(\result);
   @ post \result.times(this).getBaseDimensions().isEmpty();
   @*/
  protected /*@ pure @*/ Dimension makeInverse() {
    final Map<BaseDimension, Integer> inverseMap = new HashMap<>();
    // For every key in the key set (which only contains BaseDimensions)
    // get the double value, create a new double value that equals the inverse
    // and add it to the new map
    baseDimensions().forEach(dimension -> inverseMap.put(dimension, -exponentOf(dimension)));
    return new CompositeDimension(createName(inverseMap), inverseMap);
  }

  /**
   * Return this Dimension raised to the given power
   *
   * @param exponent
   *        The exponent.
   */
 /*@
   @ public behavior
   @
   @ post (\forall BaseDimension b; b != null;
   @       \result.getExponent(b) == exponent * getExponent(b));
   @*/
  public /*@ pure @*/ Dimension pow(int exponent) {
    // This is not very efficient
    Dimension result = _powerMap.get(exponent);
    if(result == null) {
      result = getPrototype(makePower(exponent));
      addPower(exponent, result);
    }
    return result;
  }

  /**
   * Construct a dimension that equals this dimension raised to the given power.
   * 
   * @param power
   *        The power to which this Dimension is raised.
   */
 /*@
   @ protected behavior
   @
   @ post \fresh(\result);
   @ post (\forall BaseDimension q;;
   @        \result.getExponent(q) == power * getExponent(q));
   @*/
  protected /*@ pure @*/ Dimension makePower(final double power) {
    final Map powerMap = new HashMap();
    // For every key in the key set (which only contains BaseDimensions)
    // get the double value, create a new double value that equals the inverse
    // and add it to the new map
    baseDimensions().forEach(o -> {
        Double inverse = new Double(power * exponentOf(o));
        powerMap.put(o, inverse);
      });
    return new CompositeDimension(createName(powerMap), powerMap);
  }
  
  /**
   * Return the product of this Dimension and the other Dimension.
   *
   * @param other
   *        The Dimension to multiply this one with.
   */
 /*@
   @ public behavior
   @
   @ pre other != null;
   @
   @ post \result != null;
   @ post (\forall BaseDimension b; b != null;
   @       \result.getExponent(b) == getExponent(b) + other.getExponent(b));
   @*/
  public /*@ pure @*/ Dimension times(Dimension other) {
    Dimension result = (Dimension) _productMap.get(other);
    Dimension temp = result;
    if(result == null) {
      result = getPrototype(makeProduct(other));
      // Add the result to our own cache
      addProduct(other, result);
      // Add the result to the cache of the other dimension
      //if(other != this) {
      //  other.addProduct(this, result);
      //}
    }
    return result;
  }

  /**
   * Construct a new dimension that equals the product of this
   * dimension and the given other dimension.
   *
   * @param other
   *        The dimension with which this dimension is multiplied
   */
 /*@
   @ protected behavior
   @
   @ pre other != null;
   @
   @ post \fresh(\result);
   @ post (\forall BaseDimension q;;
   @        \result.getExponent(q) == getExponent(q) + other.getExponent(q));
   @*/
  protected /*@ pure @*/ Dimension makeProduct(final Dimension other) {
    final Map productMap = new HashMap();
    Set<BaseDimension> allBase = new HashSet<>();
    allBase.addAll(baseDimensions());
    allBase.addAll(other.baseDimensions());
    allBase.forEach(o -> {
        double sum = exponentOf(o) + other.exponentOf((BaseDimension)o);
        if(sum != 0) {
          Double objectSum = new Double(sum);
          productMap.put(o, objectSum);
        }
      });
    return new CompositeDimension(createName(productMap), productMap);
  }

  /**
   * Return the quotient of this Dimension and the other Dimension.
   *
   * @param other
   *        The Dimension to divide this one by.
   */
 /*@
   @ public behavior
   @
   @ pre other != null;
   @
   @ post \fresh(\result);
   @ post (\forall BaseDimension b; b != null;
   @       \result.getExponent(b) == getExponent(b) - other.getExponent(b));
   @*/
  public /*@ pure @*/ Dimension dividedBy(Dimension other) {
    Dimension result = (Dimension) _divisionMap.get(other);
    if(result == null) {
      result = getPrototype(makeQuotient(other));
      // Add the result to our own cache
      addDivision(other, result);
      // Add the result to the cache of the other dimension
      //if(other != this) {
      //  other.addDivision(this, result);
      //}
    }
    return result;
  }

  /**
   * Construct a new dimension that equals the product of this
   * Dimension and the given other dimension.
   *
   * @param other
   *        The dimension with which this dimension is multiplied
   */
 /*@
   @ protected behavior
   @
   @ pre other != null;
   @
   @ post \fresh(\result);
   @ post (\forall BaseDimension q;;
   @        \result.getExponent(q) == getExponent(q) + other.getExponent(q));
   @*/
  protected /*@ pure @*/ Dimension makeQuotient(final Dimension other) {
    final Map divisionMap = new HashMap();
    Set<BaseDimension> allBase = new HashSet<>();
    // This is dirty
    allBase.addAll(baseDimensions());
    allBase.addAll(other.baseDimensions());
    allBase.forEach(o -> {
        double diff = exponentOf(o) - other.exponentOf(o);
        if(diff != 0) {
          Double objectDiff = new Double(diff);
          divisionMap.put(o, objectDiff);
        }
      });
    return new CompositeDimension(createName(divisionMap), divisionMap);
  }

  /**
   * Set the inverse of this dimension
   *
   * @param inverse
   *        The inverse of this dimension.
   */
 /*@
   @ private behavior
   @
   @ post inverse() == inverse;
   @*/
  private void setInverse(Dimension inverse) {
    _inverse = inverse;
  }

  /**
   * Add the given pair to the product map of this dimension.
   *
   * @param dimension
   *        The dimension with which this dimension has been multiplied.
   * @param result
   *        The result of that multiplication.
   */
 /*@
   @ behavior
   @
   @ post getProductMap().containsKey(dimension);
   @ post getProductMap().get(dimension) == result;
   @ post (\forall Dimension d; d != dimension;
   @        getProductMap().get(d) == \old(getProductMap().get(d)));
   @*/
  void addProduct(Dimension dimension, Dimension result) {
    _productMap.put(dimension, result);
  }

  /**
   * Return the product map of this Dimension
   */
 /*@
   @ behavior
   @
   @ post \fresh(\result);
   @*/
  /*@ pure @*/ Map getProductMap() {
    return new HashMap(_productMap);
  }

  /**
   * Add the given pair to the division map of this dimension.
   *
   * @param dimension
   *        The dimension by which this dimension has been divided.
   * @param result
   *        The result of that division.
   */
 /*@
   @ behavior
   @
   @ post getDivisionMap().containsKey(dimension);
   @ post getDivisionMap().get(dimension) == result;
   @ post (\forall Dimension d; d != dimension;
   @        getDivisionMap().get(d) == \old(getDivisionMap().get(d)));
   @*/
  void addDivision(Dimension dimension, Dimension result) {
    _divisionMap.put(dimension, result);
  }

  /**
   * Return the division map of this Dimension.
   */
 /*@
   @ behavior
   @
   @ post \fresh(\result);
   @*/
  /*@ pure @*/ Map getDivisionMap() {
    return new HashMap(_divisionMap);
  }

  /**
   * Add the given pair to the power map of this Dimension.
   *
   * @param power
   *        The power to  which this Dimension is raised.
   * @param result
   *        The result.
   */
 /*@
   @ behavior
   @
   @ post getPowerMap().containsKey(power);
   @ post getPowerMap().get(power) == result;
   @*/
  void addPower(int power, Dimension result) {
    _powerMap.put(power, result);
  }

  /**
   * Return the division map of this Dimension.
   */
 /*@
   @ behavior
   @
   @ post \fresh(\result);
   @*/
  /*@ pure @*/ Map<Integer, Dimension> getPowerMap() {
    return new HashMap(_powerMap);
  }

  /**
   * The inverse of this Dimension.
   */
  private Dimension _inverse;

  /**
   * A map containing the results of multiplying this
   * dimension with other dimensions. The key is the
   * dimension this dimension is multiplied with, the value
   * is the result.
   */
 /*@
   @ private invariant _productMap != null;
   @ private invariant (\forall Map.Entry e; _productMap.entrySet().contains(e);
   @                     (e != null));
   @*/
  private Map<Dimension, Dimension> _productMap;
  
  /**
   * A map containing the results of dividing this
   * dimension by other dimensions. The key is the
   * dimension by which this dimension is divided, the value
   * is the result.
   */
 /*@
   @ private invariant _divisionMap != null;
   @ private invariant (\forall Map.Entry e; _productMap.entrySet().contains(e);
   @                     (e != null));
   @*/
  private Map<Dimension, Dimension> _divisionMap;

  /**
   * A map containting the results of raising this Dimension
   * to a certain power. The key of the map is the exponent,
   * the value is the result.
   */
 /*@
   @ private invariant _powerMap != null;
   @ private invariant (\forall Map.Entry e; _productMap.entrySet().contains(e);
   @                     (e != null) &&
   @                     (e.getKey() instanceof Double) &&
   @                     (e.getValue() instanceof Dimension));
   @*/
  private Map<Integer, Dimension> _powerMap;
  
/******************
 * STATIC METHODS *
 ******************/

  /**
   * Create a string representing the name of a dimension
   * when the given map is its mapping from base dimensions to
   * doubles.
   *
   * @param map
   *        A (BaseDimension -> Double) map.
   */
 /*@
   @ protected behavior
   @
   @ pre validDimensionMap(map);
   @
   @ post \result != null;
   @*/
  protected static /*@ pure @*/ String createName(Map<BaseDimension, Integer> map) {
    StringBuilder temp = new StringBuilder();
    map.entrySet().forEach(entry -> temp.append(entry.getKey().name()+"^("+entry.getValue()+")"));
    return temp.toString();
  }

  /**
   * <p>Return the main instance of the given dimension. In order to speed up things
   * and use less memory, the implementation tries to use the same objects as much
   * as possible by:</p>
   * <ul>
   *   <li>caching the results of computations with dimensions</li>
   *   <li>replacing newly created instances of dimensions with the first instance
   *   that equals them.</li>
   *
   * @param dimension
   *        The dimension of which we want the prototype
   */
 /*@
   @ public behavior
   @
   @ pre dimension != null;
   @
   @ post \result != null;
   @ post (!\old(getAllDimensions().contains(dimension))) ==>
   @        (getAllDimensions().contains(dimension)) &&
   @        (\result == dimension);
   @ post (\old(getAllDimensions().contains(dimension))) ==>
   @        (getAllDimensions().contains(\result)) &&
   @        (\result.equals(dimension));
   @*/
  public static synchronized /*@ pure @*/ Dimension getPrototype(Dimension dimension) {
    ensureDimension(dimension);
    return (Dimension)_dimensions.get(dimension);
  }

  /**
   * Make sure that the set of all dimensions contains a dimension that
   * equals the given dimension.
   *
   * @param dimension
   *        The dimension of which an instance should be in the set of
   *        all dimensions.
   */
 /*@
   @ public behavior
   @
   @ pre dimension != null;
   @
   @ post (!\old(getAllDimensions().contains(dimension))) ==>
   @        Collections.containsExplicitly(getAllDimensions(), dimension);
   @*/
  public static synchronized void ensureDimension(Dimension dimension) {
    if (! _dimensions.containsKey(dimension)) {
      _dimensions.put(dimension, dimension);
    }
  }

  /**
   * Return a set containing all dimensions that are computed using the
   * methods in this class, including the first instances of each type of 
   * base dimension.
   */
 /*@
   @ public behavior
   @
   @ post \fresh(\result);
   @ post (\forall Object o; Collections.containsExplicitly(\result, o);
   @        o instanceof Dimension);
   @*/
  public static synchronized /*@ pure @*/ Set getAllDimensions() {
    return _dimensions.keySet();
  }

  /**
   * Return a hashcode for the next base dimension to be constructed.
   */
  static synchronized int getBaseDimensionHashCode() {
    return _baseDimensionHashCode++;
  }

  private static int _baseDimensionHashCode = 1;

  /**
   * Check whether or not the given map is a valid mapping
   * of BaseDimensions to Doubles for a dimension.
   *
   * @param map
   *        The map to check
   */
 /*@
   @ public behavior
   @
   @ // The map may no be null, all entries in the map must have a non-null
   @ // BaseDimension as key, and a non-null, non-zero Double as value.
   @ post \result == (map != null) &&
   @                 (\forall Object o; map.entrySet().contains(o);
   @                   (((Map.Entry)o).getKey() instanceof BaseDimension) &&
   @                   (((Map.Entry)o).getValue() instanceof Double) &&
   @                   (((Double)((Map.Entry)o).getValue()).doubleValue() != 0));
   @*/
  public static /*@ pure @*/ boolean validDimensionMap(Map map) {
    if (map == null) {
      return false;
    }
    Set entries = map.entrySet();
    return new SafePredicate<Map.Entry>() {
      public boolean eval(Map.Entry o) {
        return (o.getKey() instanceof BaseDimension) &&
               (o.getValue() instanceof Double) &&
               (((Double)o.getValue()).doubleValue() != 0);
      }
    }.forAll(entries);
  }

   /**
    * <p>A map containing all different types of dimensions every constructed. For
    * each equivalence class (determined by <code>equal()</code>), the first instance
    * is kept in this map.</p>
    *
    * <p>The reason for choosing a map is that you can only check if an element equal
    * to another one is in a set, but you cannot easily get a reference to the one that
    * is in the set in order to replace to entry that is not in the set. If we use a map instead
    * we can invoke <code>get(EntryNotInMap)</code> in order to accomplish that.</p>
    */
  /*@
    @ private invariant _dimensions != null;
    @ private invariant _dimensions.keySet().contains(Dimensionless.getPrototype());
    @ private invariant (\forall Object o; _dimensions.containsKey(o); o instanceof Dimension);
    @ private invariant (\forall Map.Entry me; _dimensions.entrySet().contains(me);
    @                      me.getKey() == me.getValue());
    @*/
  private static Map _dimensions = new HashMap();

  {
    // Force classloading of Dimensionless
    Dimensionless.instance();
  }
  
  static void initializeDimensions(Dimensionless singleton) {
    _dimensions.put(singleton, singleton);
  }
}

