package be.kuleuven.cs.distrinet.rejuse.metric.unit;

import be.kuleuven.cs.distrinet.rejuse.InitializationException;
import be.kuleuven.cs.distrinet.rejuse.java.collections.MapAccumulator;
import be.kuleuven.cs.distrinet.rejuse.java.collections.Visitor;
import be.kuleuven.cs.distrinet.rejuse.metric.Prefix;
import be.kuleuven.cs.distrinet.rejuse.metric.dimension.BasicDimension;
import be.kuleuven.cs.distrinet.rejuse.metric.dimension.Dimension;
import be.kuleuven.cs.distrinet.rejuse.metric.dimension.Dimensionless;
import be.kuleuven.cs.distrinet.rejuse.predicate.SafePredicate;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
/*@ import org.jutil.java.collections.Collections; @*/
/*@ import org.jutil.metric.quantity.BaseDimension; @*/

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
 * @version $Revision$
 * @date    $Date$
 * @author  Marko van Dooren
 */
public /*@ pure @*/ abstract class Unit {

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
  public /*@ pure @*/ String getName() {
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
  public /*@ pure @*/ String getSymbol() {
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
  public /*@ pure @*/ Dimension getDimension() {
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
  public abstract /*@ pure @*/ double getExponent(SpecialUnit baseUnit);

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
  public abstract /*@ pure @*/ Set getSpecialUnits();

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
    return (other == this) || getDimension().equals(other.getDimension());
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
  public Prefix getPrefix() {
    //default implementation
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
   * Construct a unit that equals the inverse of this Unit.
   */
 /*@
   @ protected behavior
   @
   @ post \fresh(\result);
   @ post \result.times(this).getBaseUnits().isEmpty();
   @*/
  protected /*@ pure @*/ Unit makeInverse() {
    final Map inverseMap = new HashMap();
    // For every key in the key set (which only contains BaseQuantities)
    // get the double value, create a new double value that equals the inverse
    // and add it to the new map
    new Visitor() {
      public void visit(Object o) {
        Double inverse = new Double(-getExponent((SpecialUnit)o));
        inverseMap.put(o, inverse);
      }
    }.applyTo(getSpecialUnits());
    return new CompositeUnit(createName(inverseMap), createSymbol(inverseMap), getDimension().inverse(), inverseMap);
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
  public /*@ pure @*/ Unit pow(double exponent) {
    // This is not very efficient
    Double power = new Double(exponent);
    Unit result = (Unit)_powerMap.get(power);
    if(result == null) {
      result = getPrototype(makePower(exponent));
      addPower(power, result);
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
  protected /*@ pure @*/ Unit makePower(final double power) {
    final Map powerMap = new HashMap();
    // For every key in the key set (which only contains BaseQuantities)
    // get the double value, create a new double value that equals the inverse
    // and add it to the new map
    new Visitor() {
      public void visit(Object o) {
        Double inverse = new Double(power * getExponent((SpecialUnit)o));
        powerMap.put(o, inverse);
      }
    }.applyTo(getSpecialUnits());
    return new CompositeUnit(createName(powerMap), createSymbol(powerMap), getDimension().pow(power), powerMap);
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
      result = getPrototype(makeProduct(other));
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
    final Map productMap = new HashMap();
    Set allBase = new HashSet();
    allBase.addAll(getSpecialUnits());
    allBase.addAll(other.getSpecialUnits());
    new Visitor() {
      public void visit(Object o) {
        double sum = getExponent((SpecialUnit)o) + other.getExponent((SpecialUnit)o);
        if(sum != 0) {
          Double objectSum = new Double(sum);
          productMap.put(o, objectSum);
        }
      }
    }.applyTo(allBase);
    return new CompositeUnit(createName(productMap), createSymbol(productMap), getDimension().times(other.getDimension()), productMap);
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
      result = getPrototype(makeQuotient(other));
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
    final Map divisionMap = new HashMap();
    Set allBase = new HashSet();
    // This is dirty
    allBase.addAll(getSpecialUnits());
    allBase.addAll(other.getSpecialUnits());
    new Visitor() {
      public void visit(Object o) {
        double diff = getExponent((SpecialUnit)o) - other.getExponent((SpecialUnit)o);
        if(diff != 0) {
          Double objectDiff = new Double(diff);
          divisionMap.put(o, objectDiff);
        }
      }
    }.applyTo(allBase);
    return new CompositeUnit(createName(divisionMap), createSymbol(divisionMap), getDimension().dividedBy(other.getDimension()), divisionMap);
  }

  /**
   * Set the inverse of this unit to the given unit.
   *
   * @param inverse
   *        The inverse of this unit.
   */
 /*@
   @ private behavior
   @
   @ post inverse() == inverse;
   @*/
  private void setInverse(Unit inverse) {
    _inverse = inverse;
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
  /*@ pure @*/ Map getProductMap() {
    return new HashMap(_productMap);
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
   * Return the division map of this Unit.
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
  void addPower(Double power, Unit result) {
    _powerMap.put(power, result);
  }

  /**
   * Return the division map of this Unit.
   */
 /*@
   @ behavior
   @
   @ post \fresh(\result);
   @*/
  /*@ pure @*/ Map getPowerMap() {
    return new HashMap(_powerMap);
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
  private HashMap _productMap = new HashMap();
  
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
  private HashMap _divisionMap = new HashMap();

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
  private HashMap _powerMap = new HashMap();
  

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
    else if(! getDimension().equals(getDimension())) {
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
   * applies to composite units, the returntype is <code>Unit</unit>. 
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
  public abstract /*@ pure @*/ Unit getBaseUnit() throws InitializationException;

  /**
   * Return the standard unit for the given basic quantity.
   *
   * @param basicQuantity
   *        The basic quantity for which the standard unit is
   *        requested.
   */
 /*@
   @ public behavior
   @
   @ post \result == getStandardUnitMap().get(basicQuantity);
   @
   @ signals (InitializationException) getStandardUnitMap().get(basicQuantity) == null;
   @*/
  public static /*@ pure @*/ Unit getBaseUnit(BasicDimension basicQuantity) throws InitializationException {
    Unit result = (Unit) _standardUnits.get(basicQuantity);
    if (result == null) {
      throw new InitializationException("A standard unit for a basic quantity could not be found. The basic quantity is "+basicQuantity.getName());
    }
    return result;
  }
  
  /**
   * Return a map that maps base quantities to their standard unit.
   */
 /*@
   @ public behavior
   @
   @ post \fresh(\result);
   @ post (\forall Map.Entry me; \result.entrySet().contains(me);
   @        (me.getKey() instanceof BasicDimension) &&
   @        ((me.getValue() instanceof BaseUnit) || (me.getValue() instanceof One)) &&
   @        ((BaseUnit)me.getValue()).getQuantity() == (Dimension)me.getKey() &&
   @        (\result.get(Dimensionless.getPrototype()) == One.getPrototype()));
   @*/
  public static /*@ pure @*/ Map getBaseUnitMap() {
    HashMap result = new HashMap();
    result.putAll(_standardUnits);
    return result;
  }

  /**
   * Add the given standard unit to the set of all
   * standard units.
   *
   * @param standardUnit
   *        The standard unit to add.
   */
 /*@
   @ behavior
   @
   @ pre standardUnit != null;
   @ pre ! getStandardUnitMap().containsKey(standardUnit.getQuantity());
   @
   @ post (\forall BaseDimension b; b != standardUnit.getQuantity();
   @        (getStandardUnitMap().keySet().contains(b) == 
   @        \old(getStandardUnitMap().keySet().contains(b))) &&
   @        (getStandardUnit(b) == \old(getStandardUnit(b))));
   @ post getStandardUnitMap().get(standardUnit.getQuantity()) == standardUnit;
   @
   @ // The map already contains the base quantity of the given standard unit.
   @ signals (InitializationException) getStandardUnitMap().containsKey(standardUnit.getQuantity());
   @*/
  static void addStandardUnit(BaseUnit standardUnit) {

    _standardUnits.put(standardUnit.getDimension(), standardUnit);
  }

 /*@
   @ private invariant _standardUnits != null;
   @ private invariant _standardUnits.keySet().contains(Dimensionless.getPrototype());
   @ private invariant (\forall Map.Entry me; _standardUnits.entrySet().contains(me);
   @                      (me.getKey() instanceof BasicDimension) &&
   @                      ((me.getValue() instanceof BaseUnit) || (me.getValue() instanceof One)) &&
   @                      ((BaseUnit)me.getValue()).getQuantity() == (BasicDimension)me.getKey() &&
   @                      (_standardUnits.get(Dimensionless.getPrototype()) == One.getPrototype()));
   @*/
  private static Map _standardUnits = new HashMap();

  {
   // This line caused class One to be loaded, but
   // since that is a subclass of this class, this piece of initialisation code
   // will be run again when One calls its super constructor. One.getPrototype()
   // will still be null at this point.
   //
   // Therefore we add a dummy invocation of One.getPrototype() to force loading
   // of class One, which will add its prototype to the standard units map.
   //
   // What an awful hack. Better solutions are welcome !!!
   //
   //_standardUnits.put(One.getPrototype().getQuantity(), One.getPrototype());
   One.getPrototype();
  }

  static void init(One one) {
   _standardUnits.put(one.getDimension(), one);
   _units.put(one, one);
  }

/******************
 * STATIC METHODS *
 ******************/

  /**
   * Compute the quantity of the unit represented by the given
   * (SpecialUnit -> Double) map.
   *
   * @param map
   *        The (SpecialUnit -> Double) map representing the unit
   *        of which the quantity must be computed.
   */
 /*@
   @ public behavior
   @
   @ pre validUnitMap(map);
   @
   @ post \result.equals(new BaseUnitMapConvertor().convert(map));
   @*/
  public static /*@ pure @*/ Dimension computeQuantity(Map map) {
    return new BaseUnitMapConvertor().convert(map);
  }

  /**
   * A class of map accumulators that convert a (SpecialUnit -> Double)
   * map into the quantity of the unit of which the map represents
   * the composition.
   */
  public static class BaseUnitMapConvertor extends MapAccumulator {

        /**
         * The pair is valid when the key is a non-null SpecialUnit
         * and the value is a non-null, non-zero Double object.
         */
       /*@
         @ also public behavior
         @
         @ post \result == (key != null) && 
         @                 (value != null) && 
         @                 (key instanceof SpecialUnit) &&
         @                 (value instanceof Double) &&
         @                 (((Double)value).doubleValue() != 0);
         @*/
        public /*@ pure @*/ boolean isValidPair(Object key, Object value) {
          return (key != null) && 
                 (value != null) && 
                 (key instanceof SpecialUnit) &&
                 (value instanceof Double) &&
                 (((Double)value).doubleValue() != 0);
        }

        /**
         * The accumulation starts with the dimensionless quantity.
         */
       /*@
         @ also public behavior
         @
         @ post \result == Dimensionless.getPrototype();
         @*/
        public /*@ pure @*/ Object initialAccumulator() {
          return Dimensionless.getPrototype();
        }

        /**
         * When accumulating, the accumulator is multiplied by the 'key' quantity
         * raised to the 'value'th power.
         */
       /*@
         @ also public behavior
         @
         @ post \result == ((Dimension)key).pow(((Double)value).doubleValue()).times((Dimension)accumulator);
         @*/
        public /*@ pure @*/ Object accumulate(Object key, Object value, Object accumulator) {
          return ((Dimension)key).pow(((Double)value).doubleValue()).times((Dimension)accumulator);
        }

        /**
         * Convert the given (SpecialUnit -> Double) map to the quantity of the unit
         * of which the given map represents the composition.
         *
         * @param map
         *        The map to convert.
         */
       /*@
         @ public behavior
         @
         @ pre map != null;
         @
         @ post (* FIXME *);
         @*/
        public /*@ pure @*/ Dimension convert(Map map) {
          return (Dimension) accumulate(map);
        }
  }

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
  protected static /*@ pure @*/ String createName(Map map) {
    final StringBuffer temp = new StringBuffer();
    new Visitor() {
      public void visit(Object o) {
        Map.Entry entry = (Map.Entry)o;
        SpecialUnit baseUnit = (SpecialUnit)entry.getKey();
        Double exponent = (Double)entry.getValue();
        temp.append(baseUnit.getName()+"^("+exponent+")");
      }
    }.applyTo(map.entrySet());
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
  protected static /*@ pure @*/ String createSymbol(Map map) {
    final StringBuffer temp = new StringBuffer();
    new Visitor() {
      public void visit(Object o) {
        Map.Entry entry = (Map.Entry)o;
        SpecialUnit baseUnit = (SpecialUnit)entry.getKey();
        Double exponent = (Double)entry.getValue();
        temp.append(baseUnit.getSymbol()+"^("+exponent+")");
      }
    }.applyTo(map.entrySet());
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
  public static synchronized Unit getPrototype(Unit unit) {
    ensureUnit(unit);
    return (Unit)_units.get(unit);
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

  /**
   * Return a set containing all units that are computed using the
   * methods in this class, including the first instances of each type of 
   * base unit.
   */
 /*@
   @ public behavior
   @
   @ post \fresh(\result);
   @ post (\forall Object o; Collections.containsExplicitly(\result, o);
   @        o instanceof Unit);
   @*/
  public static synchronized /*@ pure @*/ Set getAllUnits() {
    return _units.keySet();
  }

  /**
   * Check whether or not the given map is a valid mapping
   * of BaseUnits to Doubles for a unit.
   *
   * @param map
   *        The map to check
   */
 /*@
   @ public behavior
   @
   @ // The map may not be null, all entries in the map must have a non-null
   @ // SpecialUnit as key, and a non-null, non-zero Double as value.
   @ post \result == (map != null) &&
   @                 (\forall Object o; map.entrySet().contains(o);
   @                   (o.getKey() instanceof SpecialUnit) &&
   @                   (o.getValue() instanceof Double) &&
   @                   (((Double)o.getValue()).doubleValue() != 0));
   @*/
  public static /*@ pure @*/ boolean validUnitMap(Map map) {
    if (map == null) {
      return false;
    }
    Set entries = map.entrySet();
    return new SafePredicate<Map.Entry>() {
      public boolean eval(Map.Entry o) {
        return (o.getKey() instanceof SpecialUnit) &&
               (o.getValue() instanceof Double) &&
               (((Double)o.getValue()).doubleValue() != 0);
      }
    }.forAll(entries);
  }

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
  private static Map _units = new HashMap();

  /**
   * A class of predicate that evaluate to <code>true</code> when
   * the given object is a unit that converts isomorph to this unit.
   */
  public static class ConvertsIsomorphTo extends SafePredicate {

    /**
     * Initialize a new ConvertsIsomorphTo with the given unit
     *
     * @param unit
     *        The unit of the new ConvertsIsomorphTo predicate.
     */
   /*@
     @ public behavior
     @
     @ pre unit != null;
     @
     @ post getUnit() == unit;
     @*/
    public /*@ pure @*/ ConvertsIsomorphTo(Unit unit) {
      _unit = unit;
    }

    /**
     * Return the unit of this Unit.
     */
   /*@
     @ public behavior
     @
     @ post \result != null;
     @*/
    public /*@ pure @*/ Unit getUnit() {
      return _unit;
    }

    /**
     * The unit of this predicate
     */
   /*@
     @ private invariant _unit != null;
     @*/
    private Unit _unit;

   /*@
     @ also public behavior
     @
     @ post \result == (o instanceof Unit) &&
     @                 ((Unit)o).convertsIsomorphTo(getUnit());
     @*/
    public /*@ pure @*/ boolean eval(Object o) {
      return (o instanceof Unit) &&
             ((Unit)o).convertsIsomorphTo(_unit);
    }
  }
}

