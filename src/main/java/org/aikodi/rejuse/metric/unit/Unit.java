package org.aikodi.rejuse.metric.unit;

import java.util.HashMap;
/*@ import org.jutil.java.collections.Collections; @*/
/*@ import org.jutil.metric.quantity.BaseDimension; @*/
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.aikodi.rejuse.InitializationException;
import org.aikodi.rejuse.metric.Prefix;
import org.aikodi.rejuse.metric.dimension.Dimension;

/**
 * A class of units.
 *
 * <p>From <a href="http://physics.nist.gov/cuu/Units/introduction.html">http://physics.nist.gov/cuu/Units/introduction.html</a>:</p>
 *
 * <p>A <b>unit</b> is a particular physical quantity, defined and adopted by convention, with which other 
 * particular quantities of the same kind are compared to express their value.</p>
 *
 * <p>There are two ways to compare units:</p>
 * <ol>
 *  <li>The first way to compare units is to check whether or not they are 
 * <a href="Unit.html#isCompatibleWith(org.rejuse.metric.unit.SpecialUnit)">compatible</a>.
 * Two units are compatible if their quantities are equal. Physical values represented in compatible
 * units can be compared, added and subtracted.</li>
 *  <li>The second way to compare units is to check whether or not they are
 *  equal. Two units are equal if they return the same standard value for each value. In practice, we
 *  say two units are equal when their composition of base units is the same, since implementing two
 *  equal base units makes little sense.</li>
 * </ol>
 * <p>In general, a unit consists of a product of <a href="SpecialUnit.html">special units</a> with a certain exponent.
 * So it is possible to have both an exponent for 'meter' and for 'foot' since they are not equal. 
 * However, when converting a value to the standard value, 'foot' will be converted to 'meter' because 'meter' 
 * is a <a href="SpecialUnit.html">base unit</a>, and 'foot' is not.</p>
 *
 * @author  Marko van Dooren
 */
public /*@ pure @*/ abstract class Unit {

  /**
   * <p>A map containing all different types of units every constructed. For
   * each equivalence class (determined by <code>equal()</code>), the first instance
   * is kept in this map.</p>
   *
   * <p>The reason for choosing a map is that you can only check if an element equal
   * to another one is in a set, but you cannot easily get a reference to the one that
   * is in the set in order to replace to entry that is not in the set. If we use a map instead
   * we can invoke <code>get(EntryNotInMap)</code> in order to accomplish that.</p>
   */
 /*@
   @ private invariant _units != null;
   @ private invariant _units.keySet().contains(One.getPrototype());
   @ private invariant (\forall Object o; _units.containsKey(o); o instanceof Unit);
   @ private invariant (\forall Map.Entry me; _units.entrySet().contains(me);
   @                      me.getKey() == me.getValue());
   @*/
 private static Map<Unit, Unit> _units = new HashMap<>();

  /**
   * Initialize a new unit with the given name, symbol and quantity.
   *
   * @param name
   *        The name of the new Unit.
   * @param symbol
   *        The symbol of the new Unit.
   * @param quantity
   *        The quantity of the new Unit.
   * @param hashCode
   *        The hash code of the new Unit.
   */
 /*@
   @ protected behavior
   @
   @ pre name != null;
   @ pre symbol != null;
   @ pre quantity != null;
   @
   @ post getName() == name;
   @ post getSymbol() == symbol;
   @ post getQuantity() == quantity;
   @ post hashCode() == hashCode;
   @*/
  protected Unit(String name, String symbol, Dimension quantity, int hashCode) {
    _hashCode = hashCode;
    _name = name;
    _symbol = symbol;
    _dimension = quantity;
  }

/********
 * NAME *
 ********/

  /**
   * Return the name of this Unit.
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
   * The name of this Unit.
   */
 /*@
   @ private invariant _name != null;
   @*/
  private String _name;

/**********
 * SYMBOL *
 **********/

  /**
   * Return the symbol of this Unit.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
  public /*@ pure @*/ String symbol() {
    return _symbol;
  }

  /**
   * The symbol of this Unit.
   */
 /*@
   @ private invariant _symbol != null;
   @*/
  private String _symbol;

/************
 * QUANTITY *
 ************/

  /**
   * Return the quantity of this Unit.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
  public /*@ pure @*/ Dimension dimension() {
    return _dimension;
  }

  /*@
    @ private invariant _dimension != null;
    @*/
  private Dimension _dimension;

/***************
 * COMPOSITION *
 ***************/

  /**
   * Return the exponent of the given unit in this Unit.
   *
   * @param baseUnit
   *        The base unit of which the exponent is requested.
   */
 /*@
   @ public behavior
   @
   @ pre baseUnit != null;
   @*/
  public abstract /*@ pure @*/ int exponentOf(SpecialUnit baseUnit);

  /**
   * Return the set of base units for which this Unit
   * has a non-zero exponent.
   */
 /*@
   @ public behavior
   @
   @ post \fresh(\result);
   @ post (\forall SpecialUnit bu;;
   @        (\result.contains(bu)) == (getExponent(bu) != 0));
   @*/
  public abstract /*@ pure @*/ Set<SpecialUnit> components();

  /**
   * See superclass
   */
 /*@
   @ also public behavior
   @
   @ post \result == (other != null) &&
   @                 (other instanceof Unit) &&
   @                 (\forall SpecialUnit b; b!= null;
   @                    getExponent(b) == ((Unit)other).getExponent(b));
   @*/
  public abstract /*@ pure @*/ boolean equals(Object other);

  /**
   * See superclass
   */
  public /*@ pure @*/ int hashCode() {
    return _hashCode;
  }
  
  /**
   * The hashcode of this unit. We need this because
   * we use a HashMap to store the set of "all" unit. This
   * is needed in order to map equal units on eachother.
   */
  private int _hashCode;
  

  /**
   * Return a hashcode for the next base unit to be constructed.
   */
  static synchronized int getBaseUnitHashCode() {
    return _baseUnitHashCode++;
  }

  private static int _baseUnitHashCode = 1;

  /**
   * Check whether or not this Unit is compatible with the
   * given Unit.
   *
   * @param other
   *        The other unit.
   */
 /*@
   @ public behavior
   @
   @ pre other != null;
   @
   @ post \result == getQuantity().equals(other.getQuantity());
   @*/
  public /*@ pure @*/ boolean isCompatibleWith(Unit other) {
    return (other == this) || dimension().equals(other.dimension());
  }
  
/**********
 * PREFIX *
 **********/
  
  /**
   * Return the prefix of this unit.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
  public Prefix prefix() {
    return Prefix.ONE;
  }

  /**
   * Return this unit prefixed by the given prefix.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
  public abstract /*@ pure @*/ Unit prefix(Prefix prefix);

/***************
 * COMPUTATION *
 ***************/

  /**
   * Return the inverse unit of this unit.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ post (\forall SpecialUnit b; b!= null;
   @       \result.getExponent(b) == -getExponent(b));
   @*/
  public /*@ pure @*/ Unit inverse() {
    if(_inverse == null) {
      _inverse = makeInverse();
      _inverse = prototype(_inverse);
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
   * Construct a unit that equals the inverse of this Unit.
   */
 /*@
   @ protected behavior
   @
   @ post \fresh(\result);
   @ post \result.times(this).getBaseUnits().isEmpty();
   @*/
  protected /*@ pure @*/ Unit makeInverse() {
    final Map<SpecialUnit, Integer> inverseMap = new HashMap<>();
    // For every key in the key set (which only contains BaseQuantities)
    // get the double value, create a new double value that equals the inverse
    // and add it to the new map
    components().forEach(o -> inverseMap.put(o, -exponentOf(o)));
    return new CompositeUnit(createName(inverseMap), createSymbol(inverseMap), dimension().inverse(), inverseMap);
  }

  /**
   * Return this Unit raised to the given power
   *
   * @param exponent
   *        The exponent.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ post (\forall SpecialUnit b; b != null;
   @       \result.getExponent(b) == exponent * getExponent(b));
   @*/
  public /*@ pure @*/ Unit pow(int exponent) {
    // This is not very efficient
    Unit result = (Unit)_powerMap.get(exponent);
    if(result == null) {
      result = prototype(makePower(exponent));
      addPower(exponent, result);
    }
    return result;
  }

  /**
   * Construct a unit that equals this Unit raised
   * to the given power.
   * 
   * @param power
   *        The power to which this Unit is raised.
   */
 /*@
   @ protected behavior
   @
   @ post \fresh(\result);
   @ post (\forall SpecialUnit b;;
   @        \result.getExponent(b) == power * getExponent(b));
   @*/
  protected /*@ pure @*/ Unit makePower(final int power) {
    final Map<SpecialUnit, Integer> powerMap = new HashMap<>();
    // For every key in the key set (which only contains BaseQuantities)
    // get the double value, create a new double value that equals the inverse
    // and add it to the new map
    components().forEach(o -> powerMap.put(o, power * exponentOf(o)));
    return new CompositeUnit(createName(powerMap), createSymbol(powerMap), dimension().pow(power), powerMap);
  }
  
  /**
   * Return the product of this Unit and the other Unit.
   *
   * @param other
   *        The Unit to multiply this one with.
   */
 /*@
   @ public behavior
   @
   @ pre other != null;
   @
   @ post \result != null;
   @ post (\forall SpecialUnit b; b != null;
   @       \result.getExponent(b) == getExponent(b) + other.getExponent(b));
   @*/
  public /*@ pure @*/ Unit times(Unit other) {
    Unit result = (Unit) _productMap.get(other);
    if(result == null) {
      result = prototype(makeProduct(other));
      // Add the result to our own cache
      addProduct(other, result);
      // Add the result to the cache of the other unit
      //if(other != this) {
      //  other.addProduct(this, result);
      //}
    }
    return result;
  }

  /**
   * Construct a new unit that equals the product of this
   * Unit and the given other unit.
   *
   * @param other
   *        The unit with which this Unit is multiplied
   */
 /*@
   @ protected behavior
   @
   @ pre other != null;
   @
   @ post \fresh(\result);
   @ post (\forall SpecialUnit b;;
   @        \result.getExponent(b) == getExponent(b) + other.getExponent(b));
   @*/
  protected /*@ pure @*/ Unit makeProduct(final Unit other) {
    final Map<SpecialUnit, Integer> productMap = new HashMap<>();
    Set<SpecialUnit> allBase = new HashSet<>();
    allBase.addAll(components());
    allBase.addAll(other.components());
    allBase.forEach(o -> {
        int sum = exponentOf(o) + other.exponentOf(o);
        if(sum != 0) {
          productMap.put(o, sum);
        }
      });
    return new CompositeUnit(createName(productMap), createSymbol(productMap), dimension().times(other.dimension()), productMap);
  }

  /**
   * Return the quotient of this Unit and the other Unit.
   *
   * @param other
   *        The Unit to divide this one by.
   */
 /*@
   @ public behavior
   @
   @ pre other != null;
   @
   @ post \result != null;
   @ post (\forall SpecialUnit b; b != null;
   @       \result.getExponent(b) == getExponent(b) - other.getExponent(b));
   @*/
  public /*@ pure @*/ Unit dividedBy(Unit other) {
    Unit result = (Unit) _divisionMap.get(other);
    if(result == null) {
      result = prototype(makeQuotient(other));
      // Add the result to our own cache
      addDivision(other, result);
      // Add the result to the cache of the other unit
      //if(other != this) {
      //  other.addDivision(this, result);
      //}
    }
    return result;
  }

  /**
   * Construct a new unit that equals the product of this
   * Unit and the given other unit.
   *
   * @param other
   *        The unit with which this Unit is multiplied
   */
 /*@
   @ protected behavior
   @
   @ pre other != null;
   @
   @ post \fresh(\result);
   @ post (\forall SpecialUnit q;;
   @        \result.getExponent(q) == getExponent(q) + other.getExponent(q));
   @*/
  protected /*@ pure @*/ Unit makeQuotient(final Unit other) {
    final Map<SpecialUnit, Integer> divisionMap = new HashMap<>();
    Set <SpecialUnit> allBase = new HashSet<>();
    // This is dirty
    allBase.addAll(components());
    allBase.addAll(other.components());
    allBase.forEach(o -> {
        int diff = exponentOf(o) - other.exponentOf((SpecialUnit)o);
        if(diff != 0) {
          divisionMap.put(o, diff);
        }
      });
    return new CompositeUnit(createName(divisionMap), createSymbol(divisionMap), dimension().dividedBy(other.dimension()), divisionMap);
  }

  /**
   * Add the given pair to the product map of this Unit.
   *
   * @param unit
   *        The unit with which this Unit has been multiplied.
   * @param result
   *        The result of that multiplication.
   */
 /*@
   @ behavior
   @
   @ post getProductMap().containsKey(unit);
   @ post getProductMap().get(unit) == result;
   @ post (\forall Unit u; u != unit;
   @        getProductMap().get(u) == \old(getProductMap().get(u)));
   @*/
  void addProduct(Unit unit, Unit result) {
    _productMap.put(unit, result);
  }

  /**
   * Return the product map of this Unit
   */
 /*@
   @ behavior
   @
   @ post \fresh(\result);
   @*/
  /*@ pure @*/ Map<Unit, Unit> getProductMap() {
    return new HashMap<>(_productMap);
  }

  /**
   * Add the given pair to the division map of this Unit.
   *
   * @param unit
   *        The unit by which this Unit has been divided.
   * @param result
   *        The result of that division.
   */
 /*@
   @ behavior
   @
   @ post getDivisionMap().containsKey(unit);
   @ post getDivisionMap().get(unit) == result;
   @ post (\forall Unit q; q != unit;
   @        getDivisionMap().get(q) == \old(getDivisionMap().get(q)));
   @*/
  void addDivision(Unit unit, Unit result) {
    _divisionMap.put(unit, result);
  }

  /**
   * Add the given pair to the power map of this Unit.
   *
   * @param power
   *        The power to  which this Unit is raised.
   * @param result
   *        The result.
   */
 /*@
   @ behavior
   @
   @ post getPowerMap().containsKey(power);
   @ post getPowerMap().get(power) == result;
   @*/
  void addPower(int power, Unit result) {
    _powerMap.put(power, result);
  }

  /**
   * The inverse of this Unit.
   */
  private Unit _inverse;

  /**
   * A map containing the results of multiplying this
   * Unit with other units. The key is the
   * unit this unit is multiplied with, the value
   * is the result.
   */
 /*@
   @ private invariant _productMap != null;
   @ private invariant (\forall Map.Entry e; _productMap.entrySet().contains(e);
   @                     (e != null) &&
   @                     (e.getKey() instanceof Unit) &&
   @                     (e.getValue() instanceof Unit));
   @*/
  private Map<Unit, Unit> _productMap = new HashMap<>();
  
  /**
   * A map containing the results of dividing this
   * Unit by other units. The key is the
   * unit by which this unit is divided, the value
   * is the result.
   */
 /*@
   @ private invariant _divisionMap != null;
   @ private invariant (\forall Map.Entry e; _productMap.entrySet().contains(e);
   @                     (e != null) &&
   @                     (e.getKey() instanceof Unit) &&
   @                     (e.getValue() instanceof Unit));
   @*/
  private Map<Unit, Unit> _divisionMap = new HashMap<>();

  /**
   * A map containting the results of raising this Unit
   * to a certain power. The key of the map is the exponent,
   * the value is the result.
   */
 /*@
   @ private invariant _powerMap != null;
   @ private invariant (\forall Map.Entry e; _productMap.entrySet().contains(e);
   @                     (e != null) &&
   @                     (e.getKey() instanceof Double) &&
   @                     (e.getValue() instanceof Unit));
   @*/
  private Map<Integer, Unit> _powerMap = new HashMap<>();
  

/**************
 * CONVERSION *
 **************/

 /*@
   @ // FIXME public invariant (\forall double d; isValid(d);
   @ //                   fromStandard(toStandard(d)));
   @*/
  
  /**
   * Check whether or not the conversion of this unit into the given
   * other unit is an isomorphism with respect to addition, subtraction,
   * multiplication and division.
   *
   * @param unit
   *        The other unit.
   */
 /*@
   @ public behavior
   @
   @ pre unit != null;
   @
   @ post equals(unit) ==> \result == true;
   @ post ! unit.getDimension().equals(getDimension()) ==> \result == false;
   @ // The relation is symmetric
   @ post \result == unit.convertsIsomorphTo(this);
   @*/
  public final /*@ pure @*/ boolean convertsIsomorphTo(Unit unit) {
    // TODO too strong
    if(equals(unit)) {
      return true;
    }
    else if(! dimension().equals(dimension())) {
      return false;
    }
    return convertsIsomorphToBaseUnit() && unit.convertsIsomorphToBaseUnit();
  }
  
  public abstract /*@ pure @*/ boolean convertsIsomorphToBaseUnit();

  /**
   * Convert the given value in this unit to
   * its corresponding value according to the standard
   * unit of the quantity of this unit.
   *
   * @param value
   *        The value to convert.
   */
 /*@
   @ public behavior
   @
   @ signals (ConversionException) ! convertsIsomorphTo(getStandardUnit());
   @*/
  public abstract /*@ pure @*/ double convertToBase(double value) throws ConversionException;

  /**
   * Convert the given value in the standard
   * unit of the quantity of this unit to the corresponding
   * value in this unit.
   *
   * @param value
   *        The value to convert.
   */
 /*@
   @ public behavior
   @
   @ signals (ConversionException) ! convertsIsomorphTo(getStandardUnit());
   @*/
  public abstract /*@ pure @*/ double convertFromBase(double value) throws ConversionException;

  /**
   * Convert the given value expressed in this unit to the corresponding
   * number expressed in the given unit.
   *
   * @param other
   *        The unit to convert to.
   * @param value
   *        The value to convert.
   */
 /*@
   @ public behavior
   @
   @ post \result == other.convertFromStandard(convertToStandard(value));
   @
   @ signals (ConversionException) (! convertsIsomorphTo(getStandardUnit())) ||
   @                               (! other.convertsIsomorphTo(getStandardUnit()));
   @*/
  public /*@ pure @*/ double convertTo(Unit other, double value) throws ConversionException {
    return other.convertFromBase(convertToBase(value));
  }

  /**
   * Convert the given value expressed in the given unit to the corresponding
   * number expressed in this unit.
   *
   * @param other
   *        The unit to convert from.
   * @param value
   *        The value to convert.
   */
 /*@
   @ public behavior
   @
   @ post \result == convertFromStandard(other.convertToStandard(value));
   @
   @ signals (ConversionException) (! convertsIsomorphTo(getStandardUnit())) ||
   @                               (! other.convertsIsomorphTo(getStandardUnit()));
   @*/
  public /*@ pure @*/ double convertFrom(Unit other, double value) throws ConversionException {
    return convertFromBase(other.convertToBase(value));
  }

/**************
 * BASE UNITS *
 **************/

  /**
   * Return the standard unit of the quantity of this unit. Since this also
   * applies to composite units, the return type is <code>Unit</unit>. 
   * The standard unit of a composite is a composite unit itself.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ post \result.getQuantity().equals(getQuantity());
   @ post (\result instanceof BaseUnit) || (\result instanceof One);
   @ post (\forall SpecialUnit b; \result.getExponent(b) != 0;
   @         b instanceof BaseUnit);
   @*/
  public abstract /*@ pure @*/ Unit baseUnit() throws InitializationException;


/******************
 * STATIC METHODS *
 ******************/

  /**
   * Create a string representing the name of a unit
   * when the given map is its mapping from base units to
   * doubles.
   *
   * @param map
   *        A (SpecialUnit -> Double) map.
   */
 /*@
   @ protected behavior
   @
   @ pre validUnitMap(map);
   @
   @ post \result != null;
   @*/
  protected static /*@ pure @*/ String createName(Map<? extends Unit, Integer> map) {
    final StringBuffer temp = new StringBuffer();
    map.entrySet().forEach(entry -> temp.append(entry.getKey().name()+"^("+entry.getValue()+")"));
    return temp.toString();
  }

  /**
   * Create a string representing the symbol of a unit
   * when the given map is its mapping from base units to
   * doubles.
   *
   * @param map
   *        A (SpecialUnit -> Double) map.
   */
 /*@
   @ protected behavior
   @
   @ pre validUnitMap(map);
   @
   @ post \result != null;
   @*/
  protected static /*@ pure @*/ String createSymbol(Map<? extends Unit, Integer> map) {
    final StringBuffer temp = new StringBuffer();
    map.entrySet().forEach(entry -> {
        temp.append(entry.getKey().symbol()+"^("+entry.getValue()+")");
      });
    return temp.toString();
  }

  /**
   * <p>Return the main instance of the given unit. In order to speed up things
   * and use less memory, the implementation tries to use the same objects as much
   * as possible by:</p>
   * <ul>
   *   <li>caching the results of computations with units</li>
   *   <li>replacing newly created instances of units with the first instance
   *   that equals them.</li>
   *
   * @param unit
   *        The unit of which we want the prototype
   */
 /*@
   @ public behavior
   @
   @ pre unit != null;
   @
   @ post \result != null;
   @ post (!\old(getAllUnits().contains(unit))) ==>
   @        (getAllUnits().contains(unit)) &&
   @        (\result == unit);
   @ post (\old(getAllUnits().contains(unit))) ==>
   @        (getAllUnits().contains(\result)) &&
   @        (\result.equals(unit));
   @*/
  public static synchronized Unit prototype(Unit unit) {
    ensureUnit(unit);
    return _units.get(unit);
  }

  /**
   * Make sure that the set of all units contains a unit that
   * equals the given unit.
   *
   * @param unit
   *        The unit of which an instance should be in the set of
   *        all units.
   */
 /*@
   @ public behavior
   @
   @ pre unit != null;
   @
   @ post (!\old(getAllUnits().contains(unit))) ==>
   @        Collections.containsExplicitly(getAllUnits(), unit);
   @*/
  public static synchronized void ensureUnit(Unit unit) {
    if (! _units.containsKey(unit)) {
      _units.put(unit, unit);
    }
  }

}

