package org.aikodi.rejuse.metric.unit;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.aikodi.rejuse.InitializationException;
import org.aikodi.rejuse.metric.Prefix;
import org.aikodi.rejuse.metric.dimension.Dimension;
import org.aikodi.rejuse.metric.dimension.Dimensionless;
import org.aikodi.rejuse.predicate.SafePredicate;

/**
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 */
public class CompositeUnit extends Unit {

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
   private static /*@ pure @*/ Dimension computeDimension(Map<SpecialUnit, Integer> map) {
	  return map.entrySet().stream().map(e -> e.getKey().dimension().pow(e.getValue())).reduce(Dimensionless.instance(), (first, second) -> first.times(second));
   }

  /**
   * Initialize a new CompositeUnit with the given name and
   * (SpecialUnit -> Double) map.
   *
   * @param name
   *        The name of the new CompositeUnit
   * @param symbol
   *        The symbol of the new CompositeUnit
   * @param map
   *        A map containing (SpecialUnit, Double) pairs.
   */
 /*@
   @ public behavior
   @
   @ pre name != null;
   @ pre symbol != null;
   @ pre validUnitMap(map);
   @
   @ post getName().equals(name);
   @ post (\forall Map.Entry e; map.entrySet().contains(e);
   @        getExponent((SpecialUnit)e.getKey()) == ((Double)e.getValue()).doubleValue());
   @*/
  public CompositeUnit(String name, String symbol, Map<SpecialUnit, Integer> map) {
    super(name, symbol, computeDimension(map), getHashCode(map));
    _map = new HashMap<>();
    _map.putAll(map);
    Unit.ensureUnit(this);
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
   @ pre validUnitMap(map);
   @
   @ post true;
   @*/
  private static int getHashCode(Map<SpecialUnit, Integer> map) {
  	return map.entrySet().stream().mapToInt(entry -> entry.getKey().hashCode() * entry.getValue()).sum();
  }


  /**
   * Initialize a new CompositeUnit with the given name,
   * SpecialUnit and exponent.
   *
   * @param name
   *        The name of the new CompositeUnit
   * @param symbol
   *        The symbol of the new CompositeUnit
   * @param baseUnit
   *        The only non-zero base unit of the new CompositeUnit.
   * @param exponent
   *        The exponent of the base unit.
   */
 /*@
   @ public behavior
   @
   @ pre name != null;
   @ pre symbol != null;
   @ pre baseUnit != null;
   @ pre exponent != 0;
   @
   @ post getName().equals(name);
   @ post getExponent(baseUnit) == exponent;
   @ post getBaseUnits().size() == 2;
   @*/
  public CompositeUnit(String name, String symbol, SpecialUnit baseUnit, int exponent) {
    super(name, symbol, baseUnit.dimension().pow(exponent),
          baseUnit.hashCode() * ((int)exponent));
    _map = new HashMap<>();
    _map.put(baseUnit, exponent);
    Unit.ensureUnit(this);
  }

  /**
   * Initialize a new CompositeUnit with the given name,
   * base units and exponents.
   *
   * @param name
   *        The name of the new CompositeUnit
   * @param symbol
   *        The symbol of the new CompositeUnit
   * @param baseUnit1
   *        A base unit of the new CompositeUnit with a non-zero exponent.
   * @param exponent1
   *        The exponent of the first base unit.
   * @param baseUnit2
   *        A base unit of the new CompositeUnit with a non-zero exponent.
   * @param exponent2
   *        The exponent of the second base unit.
   */
 /*@
   @ public behavior
   @
   @ pre name != null;
   @ pre symbol != null;
   @ pre baseUnit1 != null;
   @ pre baseUnit2 != null;
   @ pre baseUnit1 != baseUnit2;
   @ pre exponent1 != 0;
   @ pre exponent2 != 0;
   @
   @ post getName().equals(name);
   @ post getExponent(baseUnit1) == exponent1;
   @ post getExponent(baseUnit2) == exponent2;
   @ post getBaseUnits().size() == 2;
   @*/
  public CompositeUnit(String name, String symbol, 
                       SpecialUnit baseUnit1, int exponent1,
                       SpecialUnit baseUnit2, int exponent2) {
    super(name, symbol, 
          baseUnit1.dimension().pow(exponent1).times(
          baseUnit2.dimension().pow(exponent2)),
          (baseUnit1.hashCode() * exponent1) +
          (baseUnit2.hashCode() * exponent2)
         );
    _map = new HashMap<>();
    _map.put(baseUnit1, exponent1);
    _map.put(baseUnit2, exponent2);
    Unit.ensureUnit(this);
  }

  /**
   * Initialize a new CompositeUnit with the given name,
   * base units and exponents.
   *
   * @param name
   *        The name of the new CompositeUnit
   * @param symbol
   *        The symbol of the new CompositeUnit
   * @param baseUnit1
   *        A base unit of the new CompositeUnit with a non-zero exponent.
   * @param exponent1
   *        The exponent of the first base unit.
   * @param baseUnit2
   *        A base unit of the new CompositeUnit with a non-zero exponent.
   * @param exponent2
   *        The exponent of the second base unit.
   * @param baseUnit3
   *        A base unit of the new CompositeUnit with a non-zero exponent.
   * @param exponent3
   *        The exponent of the third base unit.
   */
 /*@
   @ public behavior
   @
   @ pre name != null;
   @ pre symbol != null;
   @ pre baseUnit1 != null;
   @ pre baseUnit2 != null;
   @ pre baseUnit3 != null;
   @ pre baseUnit1 != baseUnit2;
   @ pre baseUnit1 != baseUnit3;
   @ pre baseUnit2 != baseUnit3;
   @ pre exponent1 != 0;
   @ pre exponent2 != 0;
   @ pre exponent3 != 0;
   @
   @ post getName().equals(name);
   @ post getExponent(baseUnit1) == exponent1;
   @ post getExponent(baseUnit2) == exponent2;
   @ post getExponent(baseUnit3) == exponent3;
   @ post getBaseUnits().size() == 3;
   @*/
  public CompositeUnit(String name, String symbol, 
                       SpecialUnit baseUnit1, int exponent1,
                       SpecialUnit baseUnit2, int exponent2,
                       SpecialUnit baseUnit3, int exponent3) {
    super(name, symbol, 
          baseUnit1.dimension().pow(exponent1).times(
          baseUnit2.dimension().pow(exponent2).times(
          baseUnit3.dimension().pow(exponent3))),
          (baseUnit1.hashCode() * exponent1) +
          (baseUnit2.hashCode() * exponent2) +
          (baseUnit3.hashCode() * exponent3)
         );
    _map = new HashMap<>();
    _map.put(baseUnit1, exponent1);
    _map.put(baseUnit2, exponent2);
    _map.put(baseUnit3, exponent3);
    Unit.ensureUnit(this);
  }

  /**
   * Initialize a new CompositeUnit with the given name,
   * base units and exponents.
   *
   * @param name
   *        The name of the new CompositeUnit
   * @param symbol
   *        The symbol of the new CompositeUnit
   * @param baseUnit1
   *        A base unit of the new CompositeUnit with a non-zero exponent.
   * @param exponent1
   *        The exponent of the first base unit.
   * @param baseUnit2
   *        A base unit of the new CompositeUnit with a non-zero exponent.
   * @param exponent2
   *        The exponent of the second base unit.
   * @param baseUnit3
   *        A base unit of the new CompositeUnit with a non-zero exponent.
   * @param exponent3
   *        The exponent of the third base unit.
   * @param baseUnit4
   *        A base unit of the new CompositeUnit with a non-zero exponent.
   * @param exponent4
   *        The exponent of the third base unit.
   */
 /*@
   @ public behavior
   @
   @ pre name != null;
   @ pre symbol != null;
   @ pre baseUnit1 != null;
   @ pre baseUnit2 != null;
   @ pre baseUnit3 != null;
   @ pre baseUnit4 != null;
   @ pre baseUnit1 != baseUnit2;
   @ pre baseUnit1 != baseUnit3;
   @ pre baseUnit1 != baseUnit4;
   @ pre baseUnit2 != baseUnit3;
   @ pre baseUnit2 != baseUnit4;
   @ pre baseUnit3 != baseUnit4;
   @ pre exponent1 != 0;
   @ pre exponent2 != 0;
   @ pre exponent3 != 0;
   @ pre exponent4 != 0;
   @
   @ post getName().equals(name);
   @ post getExponent(baseUnit1) == exponent1;
   @ post getExponent(baseUnit2) == exponent2;
   @ post getExponent(baseUnit3) == exponent3;
   @ post getExponent(baseUnit4) == exponent4;
   @ post getBaseUnits().size() == 4;
   @*/
  public CompositeUnit(String name, String symbol, 
                       SpecialUnit baseUnit1, int exponent1,
                       SpecialUnit baseUnit2, int exponent2,
                       SpecialUnit baseUnit3, int exponent3,
                       SpecialUnit baseUnit4, int exponent4) {
    super(name, symbol, 
          baseUnit1.dimension().pow(exponent1).times(
          baseUnit2.dimension().pow(exponent2).times(
          baseUnit3.dimension().pow(exponent3).times(
          baseUnit4.dimension().pow(exponent4)))),
          (baseUnit1.hashCode() * exponent1) +
          (baseUnit2.hashCode() * exponent2) +
          (baseUnit3.hashCode() * exponent3) +
          (baseUnit4.hashCode() * exponent4)
         );
    _map = new HashMap<>();
    _map.put(baseUnit1, exponent1);
    _map.put(baseUnit2, exponent2);
    _map.put(baseUnit3, exponent3);
    _map.put(baseUnit4, exponent4);
    Unit.ensureUnit(this);
  }

  /**
   * Initialize a new CompositeUnit with the given name and
   * (SpecialUnit -> Double) map.
   *
   * @param name
   *        The name of the new CompositeUnit
   * @param symbol
   *        The symbol of the new CompositeUnit
   * @param map
   *        A map containing (SpecialUnit, Double) pairs.
   */
 /*@
   @ behavior
   @
   @ pre name != null;
   @ pre symbol != null;
   @ pre computeQuantity(map).equals(dimension);
   @ pre validUnitMap(map);
   @
   @ post getName().equals(name);
   @ post getQuantity() == dimension;
   @ post (\forall Map.Entry e; map.entrySet().contains(e);
   @        getExponent((SpecialUnit)e.getKey()) == ((Double)e.getValue()).doubleValue());
   @*/
  CompositeUnit(String name, String symbol, Dimension dimension, Map<SpecialUnit, Integer> map) {
    super(name, symbol, dimension, getHashCode(map));
    _map = new HashMap<>();
    _map.putAll(map);
    Unit.ensureUnit(this);
  }

/***************
 * COMPOSITION *
 ***************

  /**
   * See superclass.
   */
  @Override
  public /*@ pure @*/ int exponentOf(SpecialUnit baseUnit) {
    Integer result = _map.get(baseUnit);
    return result == null ? 0 : result;
  }

  /**
   * See superclass
   */
  public /*@ pure @*/ Set<SpecialUnit> components() {
    return _map.keySet();
  }

  /**
   * See superclass
   */
  public /*@ pure @*/ boolean equals(final Object other) {
    Set baseUnits = components();
    return (other == this) ||
           (
            (other instanceof Unit) &&
            (baseUnits.size() == ((Unit)other).components().size()) &&
            (new SafePredicate<SpecialUnit>() {
              public boolean eval(SpecialUnit base) {
                return exponentOf(base) == ((Unit)other).exponentOf(base);
              }
            }.forAll(baseUnits))
           );
  }

  /*@
    @ private invariant _map != null;
    @*/
  private HashMap<SpecialUnit, Integer> _map;

/**************
 * CONVERSION *
 **************/

 /*@
   @ also public behavior
   @
   @ post \result == (\forall SpecialUnit s; getSpecialUnits().contains(s);
   @                     s.convertsIsomorphToBaseUnit());
   @*/
  public /*@ pure @*/ boolean convertsIsomorphToBaseUnit() {
    return new SafePredicate<Unit>() {
             public /*@ pure @*/ boolean eval(Unit o) {
               return ((Unit)o).convertsIsomorphToBaseUnit();
             }
           }.forAll(components());
  }

  /**
   * FIXME : Specs
   */
  public /*@ pure @*/ double convertFromBase(final double value) throws ConversionException {
    return value * components().stream().mapToDouble(su -> su.convertFromBase(1)).reduce((l,r) -> l * r).getAsDouble();
  }

  /**
   * FIXME : Specs
   */
  public /*@ pure @*/ double convertToBase(final double value) throws ConversionException {
    return value * components().stream().mapToDouble(su -> su.convertToBase(1)).reduce((l,r) -> l * r).getAsDouble();
  }

  /**
   * @see Unit.getStandardUnit()
   */
  public /*@ pure @*/ Unit baseUnit() throws InitializationException {
		return components().stream().map(s -> (Unit)s).reduce(One.prototype(), (l, r) -> (Unit)l.times(r.baseUnit()));
  }

  /*@
    @ also public behavior
    @*/
  public Unit prefix(final Prefix prefix) {
    final Map<SpecialUnit, Integer> map = new HashMap<>();
    components().forEach(su -> {
        PrefixUnit prefixed = su.prefix(prefix);
        map.put(prefixed, exponentOf(su));
      });

    Unit temp = new CompositeUnit(createName(map), createSymbol(map), dimension().inverse(), map);
    return prototype(temp);
  }
}

