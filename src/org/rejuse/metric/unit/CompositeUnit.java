package org.rejuse.metric.unit;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import org.rejuse.java.collections.DoubleAccumulator;
import org.rejuse.predicate.PrimitiveTotalPredicate;
import org.rejuse.metric.Prefix;
import org.rejuse.metric.dimension.Dimension;
import org.rejuse.InitializationException;
import org.rejuse.java.collections.Accumulator;
import org.rejuse.java.collections.MapAccumulator;
import org.rejuse.java.collections.Visitor;

/**
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 */
public class CompositeUnit extends Unit {

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
  public CompositeUnit(String name, String symbol, Map map) {
    super(name, symbol, computeQuantity(map), getHashCode(map));
    _map = new HashMap();
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
  public CompositeUnit(String name, String symbol, SpecialUnit baseUnit, double exponent) {
    super(name, symbol, baseUnit.getDimension().pow(exponent),
          baseUnit.hashCode() * ((int)exponent));
    _map = new HashMap();
    _map.put(baseUnit, new Double(exponent));
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
                       SpecialUnit baseUnit1, double exponent1,
                       SpecialUnit baseUnit2, double exponent2) {
    super(name, symbol, 
          baseUnit1.getDimension().pow(exponent1).times(
          baseUnit2.getDimension().pow(exponent2)),
          (baseUnit1.hashCode() * ((int)exponent1)) +
          (baseUnit2.hashCode() * ((int)exponent2))
         );
    _map = new HashMap();
    _map.put(baseUnit1, new Double(exponent1));
    _map.put(baseUnit2, new Double(exponent2));
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
                       SpecialUnit baseUnit1, double exponent1,
                       SpecialUnit baseUnit2, double exponent2,
                       SpecialUnit baseUnit3, double exponent3) {
    super(name, symbol, 
          baseUnit1.getDimension().pow(exponent1).times(
          baseUnit2.getDimension().pow(exponent2).times(
          baseUnit3.getDimension().pow(exponent3))),
          (baseUnit1.hashCode() * ((int)exponent1)) +
          (baseUnit2.hashCode() * ((int)exponent2)) +
          (baseUnit3.hashCode() * ((int)exponent3))
         );
    _map = new HashMap();
    _map.put(baseUnit1, new Double(exponent1));
    _map.put(baseUnit2, new Double(exponent2));
    _map.put(baseUnit3, new Double(exponent3));
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
                       SpecialUnit baseUnit1, double exponent1,
                       SpecialUnit baseUnit2, double exponent2,
                       SpecialUnit baseUnit3, double exponent3,
                       SpecialUnit baseUnit4, double exponent4) {
    super(name, symbol, 
          baseUnit1.getDimension().pow(exponent1).times(
          baseUnit2.getDimension().pow(exponent2).times(
          baseUnit3.getDimension().pow(exponent3).times(
          baseUnit4.getDimension().pow(exponent4)))),
          (baseUnit1.hashCode() * ((int)exponent1)) +
          (baseUnit2.hashCode() * ((int)exponent2)) +
          (baseUnit3.hashCode() * ((int)exponent3)) +
          (baseUnit4.hashCode() * ((int)exponent4))
         );
    _map = new HashMap();
    _map.put(baseUnit1, new Double(exponent1));
    _map.put(baseUnit2, new Double(exponent2));
    _map.put(baseUnit3, new Double(exponent3));
    _map.put(baseUnit4, new Double(exponent4));
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
  CompositeUnit(String name, String symbol, Dimension dimension, Map map) {
    super(name, symbol, dimension, getHashCode(map));
    _map = new HashMap();
    _map.putAll(map);
    Unit.ensureUnit(this);
  }

/***************
 * COMPOSITION *
 ***************

  /**
   * See superclass.
   */
  public /*@ pure @*/ double getExponent(SpecialUnit baseUnit) {
    Double result = (Double) _map.get(baseUnit);
    if(result == null) {
      // The given base unit is not in the map
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
  public /*@ pure @*/ Set getSpecialUnits() {
    return _map.keySet();
  }

  /**
   * See superclass
   */
  public /*@ pure @*/ boolean equals(final Object other) {
    Set baseUnits = getSpecialUnits();
    return (other == this) ||
           (
            (other instanceof Unit) &&
            (baseUnits.size() == ((Unit)other).getSpecialUnits().size()) &&
            (new PrimitiveTotalPredicate() {
              public boolean eval(Object o) {
                SpecialUnit base = (SpecialUnit)o;
                return getExponent(base) == ((Unit)other).getExponent(base);
              }
            }.forall(baseUnits))
           );
  }

  /*@
    @ private invariant _map != null;
    @*/
  private HashMap _map;

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
    return new PrimitiveTotalPredicate() {
             public /*@ pure @*/ boolean eval(Object o) {
               return ((Unit)o).convertsIsomorphToBaseUnit();
             }
           }.forall(getSpecialUnits());
  }

  /**
   * FIXME : Specs
   */
  public /*@ pure @*/ double convertFromBase(final double value) throws ConversionException {
    Set baseUnits = getSpecialUnits();
    return new DoubleAccumulator() {
           /*@
             @ also public behavior
             @
             @ post \result == value;
             @*/
            public /*@ pure @*/ double initialAccumulator() {
              return value;
            }

            public /*@ pure @*/ double accumulate(Object element, double acc) throws ConversionException {
              //FIXME this accumulate method can throw exceptions
              SpecialUnit baseUnit = (SpecialUnit)element;
              return acc * baseUnit.convertFromBase(1);
            }
          }.in(baseUnits);
  }

  /**
   * FIXME : Specs
   */
  public /*@ pure @*/ double convertToBase(final double value) throws ConversionException {
    Set baseUnits = getSpecialUnits();
    return new DoubleAccumulator() {
           /*@
             @ also public behavior
             @
             @ post \result == value;
             @*/
            public /*@ pure @*/ double initialAccumulator() {
              return value;
            }

            public /*@ pure @*/ double accumulate(Object element, double acc) throws ConversionException {
              //FIXME this accumulate method can throw exceptions
              SpecialUnit baseUnit = (SpecialUnit)element;
              return acc * baseUnit.convertToBase(1);
            }
          }.in(baseUnits);
  }

  /**
   * @see Unit.getStandardUnit()
   */
  public /*@ pure @*/ Unit getBaseUnit() throws InitializationException {
    return (Unit) new Accumulator() {
          /*@
            @ also public behavior
            @
            @ post \result == One.getPrototype();
            @*/
           public /*@ pure @*/ Object initialAccumulator() {
             return One.PROTOTYPE;
           }

           public /*@ pure @*/ Object accumulate(Object element, Object acc) {
             return ((Unit)acc).times(((Unit)element).getBaseUnit());
           }
    }.accumulate(getSpecialUnits());
  }

  /*@
    @ also public behavior
    @*/
  public Unit prefix(final Prefix prefix) {
    final Map map = new HashMap();
    new Visitor() {
      public void visit(Object o) {
        SpecialUnit su = (SpecialUnit)o;
        Unit prefixed = su.prefix(prefix);
        map.put(prefixed, new Double(getExponent(su)));
      }
    }.applyTo(getSpecialUnits());

    Unit temp = new CompositeUnit(createName(map), createSymbol(map), getDimension().inverse(), map);
    return getPrototype(temp);
  }
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
