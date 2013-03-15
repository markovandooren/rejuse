package be.kuleuven.cs.distrinet.rejuse.metric;

//import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

/**
 * A class of objects representing prefixes. Examples are kilo, nano,...
 * 
 * @version $Revision$
 * @date    $Date$
 * @author  Marko van Dooren
 */
public class Prefix {
  
/****************
 * CONSTRUCTION *
 ****************/
  
  /**
   * Initialize a new prefix witht the given name, symbol, base number
   * and exponent.
   *
   * @param name
   *        The name of the new prefix
   * @param symbol
   *        The symbol of the new prefix
   * @param base
   *        The base number of the new prefix
   * @param exponent
   *        The exponent of the new prefix
   */
 /*@
   @ public behavior
   @
   @ pre name != null;
   @ pre symbol != null;
   @ pre base != 0;
   @
   @ post getName() == name;
   @ post getSymbol() == symbol;
   @ post getBase() == base;
   @ post getExponent() == exponent;
   @ // The set of all prefixes contains <b>a</b> prefix that is
   @ // equal to the newly created prefix.
   @ post getAllPrefixes().contains(this);
   @*/
  public Prefix(String name, String symbol, int base, int exponent) {
    _name = name;
    _symbol = symbol;
    _base = base;
    _exponent = exponent;
    _hashCode = (int)Math.pow(base,exponent);
    ensurePrefix(this);
  }

  /**
   * Initialize a new prefix witht the given base number
   * and exponent. The name and the symbol will be created
   * automatically. The new prefix is not added to the set
   * of all prefixes.
   *
   * @param base
   *        The base number of the new prefix
   * @param exponent
   *        The exponent of the new prefix
   */
 /*@
   @ public behavior
   @
   @ pre name != null;
   @ pre symbol != null;
   @ pre base != 0;
   @
   @ post getName().equals(base + "^" + exponent);
   @ post getSymbol() == getName();
   @ post getBase() == base;
   @ post getExponent() == exponent;
   @*/
  protected Prefix(int base, int exponent) {
    _name = base + "^"+exponent;
    _symbol = _name;
    _base = base;
    _exponent = exponent;
    _hashCode = (int)Math.pow(base,exponent);
  }

  /**
   * Return the hash code
   */
 /*@
   @ also public behavior
   @
   @ post \result == (int)Math.pow(getBase(), getExponent());
   @*/
  public final /*@ pure @*/ int hashCode() {
    return _hashCode;
  }

  /**
   * Note : the implementation first checks for == for reasons
   * of efficiency.
   */
 /*@
   @ also public behavior
   @
   @ post result == (other != null) &&
   @                (other instanceof Prefix) &&
   @                (getName().equals(((Prefix)other).getName())) &&
   @                (getSymbol().equals(((Prefix)other).getSymbol())) &&
   @                (getBase() == ((Prefix)other).getBase()) &&
   @                (getExponent() == ((Prefix)other).getExponent()) &&
   @*/
  public final /*@ pure @*/ boolean equals(Object other) {
    return (other == this) ||
           (
             (other != null) &&
             (other instanceof Prefix) &&
             (getName().equals(((Prefix)other).getName())) &&
             (getSymbol().equals(((Prefix)other).getSymbol())) &&
             (getBase() == ((Prefix)other).getBase()) &&
             (getExponent() == ((Prefix)other).getExponent())
           );
  }

  /**
   * The hashcode is cached since the number of prefixes is very limited
   * so it is more interesting to gain some performance at the cost of a
   * few bytes of memory.
   */
  private int _hashCode;

/********
 * NAME *
 ********/

  /**
   * Return the name of this prefix.
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
   * The name of this prefix.
   */
 /*@
   @ private invariant _name != null;
   @*/
  private String _name;

/**********
 * SYMBOL *
 **********/

  /**
   * Return the symbol of this prefix.
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
   * The symbol of this prefix.
   */
 /*@
   @ private invariant _symbol != null;
   @*/
  private String _symbol;

/********
 * BASE *
 ********/

  /**
   * Return the base number of this prefix.
   */
 /*@
   @ public behavior
   @
   @ post \result != 0;
   @*/
  public /*@ pure @*/ int getBase() {
    return _base;
  }

  /**
   * The base number of this prefix
   */
  private int _base;

/************
 * EXPONENT *
 ************/

  /**
   * Return the exponent of this prefix.
   */
  public /*@ pure @*/ int getExponent() {
    return _exponent;
  }

  /**
   * The exponent of this prefix.
   */
  private int _exponent;

/**************
 * OPERATIONS *
 **************/


  /**
   * Return the factor represented by this prefix.
   */
 /*@
   @ public behavior
   @
   @ post \result == Math.pow(getBase(), getExponent());
   @*/
  public double getFactor() {
    return Math.pow(_base, _exponent);
  }

  /**
   * Return the inverse prefix of this prefix.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ post (\forall SpecialPrefix b; b!= null;
   @       \result.getExponent(b) == -getExponent(b));
   @*/
  public /*@ pure @*/ Prefix inverse() {
    if(_inverse == null) {
      _inverse = makeInverse();
      _inverse = getPrototype(_inverse);
      // The next statement is not "safe". It can't introduce
      // any real problem, but we aren't sure this prefix is
      // a prototype while _inverse now is a reference to a prototype.
      // It would be best to wait until inverse() is invoked on _inverse
      // if that hasn't already been done.
      //
      //_inverse.setInverse(this);
    }
    return _inverse;
  }

  /**
   * Construct a prefix that equals the inverse of this Prefix.
   */
 /*@
   @ protected behavior
   @
   @ post \fresh(\result);
   @ post \result.getBase() == getBase();
   @ post \result.getExponent() == -getExponent();
   @*/
  protected /*@ pure @*/ Prefix makeInverse() {
    return new Prefix(_base, -_exponent);
  }

  /**
   * Return this prefix raised to the given power
   *
   * @param exponent
   *        The exponent.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ post \result.getBase() == getBase();
   @ post \result.getExponent() == exponent * getExponent());
   @*/
  public /*@ pure @*/ Prefix pow(int exponent) {
    // This is not very efficient
    Integer power = new Integer(exponent);
    Prefix result = (Prefix)_powerMap.get(power);
    if(result == null) {
      result = getPrototype(makePower(exponent));
      addPower(power, result);
    }
    return result;
  }

  /**
   * Construct a prefix that equals this prefix raised
   * to the given power.
   * 
   * @param power
   *        The power to which this prefix is raised.
   */
 /*@
   @ protected behavior
   @
   @ post \fresh(\result);
   @ post \result.getBase() == getBase();
   @ post \result.getExponent() == power * getExponent();
   @*/
  protected /*@ pure @*/ Prefix makePower(int power) {
    return new Prefix(_base, _exponent * power);
  }
  
  /**
   * Return the product of this prefix and the other prefix.
   *
   * @param other
   *        The prefix to multiply this one with.
   */
 /*@
   @ public behavior
   @
   @ pre other != null;
   @ pre other.getBase() == getBase();
   @
   @ post \result != null;
   @ post getExponent() == 0 ==> \result.getBase() == other.getBase();
   @ post ! getExponent() == 0 ==> \result.getBase() == getBase();
   @ post \result.getExponent() == getExponent() + other.getExponent();
   @*/
  public /*@ pure @*/ Prefix times(Prefix other) {
    Prefix result = (Prefix) _productMap.get(other);
    if(result == null) {
      if(_exponent == 0) {
        result = other;
      }
      else if(other.getExponent() == 0) {
        result = this;
      }
      else {
        result = getPrototype(makeProduct(other));
        // Add the result to our own cache
        addProduct(other, result);
        // Add the result to the cache of the other prefix
        //if(other != this) {
        //  other.addDivision(this, result);
        //}
      }
    }
    return result;
  }

  /**
   * Construct a new prefix that equals the product of this
   * Prefix and the given other prefix.
   *
   * @param other
   *        The prefix with which this Prefix is multiplied
   */
 /*@
   @ protected behavior
   @
   @ pre other != null;
   @ pre other.getBase() == getBase();
   @
   @ post \fresh(\result);
   @ post \result.getBase() == getBase();
   @ post \result.getExponent() == getExponent() + other.getExponent();
   @*/
  protected /*@ pure @*/ Prefix makeProduct(Prefix other) {
    return new Prefix(_base, _exponent + other.getExponent());
  }
  
  /**
   * Check whether or not the given other prefix is compatible with
   * this one.
   * 
   * @param other
   *        The prefix to be checked.
   */
 /*@
   @ public behavior
   @
   @ post \result == (other != null) &&
   @                 (
   @                   (getExponent() == 0) || 
   @                   (other.getExponent() == 0) || 
   @                   (other.getBase() == getBase())
   @                 ); 
   @*/
  public boolean compatibleWith(Prefix other) {
    return (other != null) &&
           (
             (getExponent() == 0) || 
             (other.getExponent() == 0) || 
             (other.getBase() == getBase())
           ); 
  }

  /**
   * Return the quotient of this Prefix and the other Prefix.
   *
   * @param other
   *        The Prefix to divide this one by.
   */
 /*@
   @ public behavior
   @
   @ pre other != null;
   @ pre compatibleWith(other);
   @
   @ post \result != null;
   @ post getExponent() == 0 ==> \result.getBase() == other.getBase();
   @ post ! getExponent() == 0 ==> \result.getBase() == getBase();
   @ post \result.getExponent() == getExponent() - other.getExponent();
   @*/
  public /*@ pure @*/ Prefix dividedBy(Prefix other) {
    Prefix result = (Prefix) _divisionMap.get(other);
    if(result == null) {
      if(_exponent == 0) {
        result = other;
      }
      else if(other.getExponent() == 0) {
        result = this;
      }
      else {
        result = getPrototype(makeQuotient(other));
        // Add the result to our own cache
        addDivision(other, result);
        // Add the result to the cache of the other prefix
        //if(other != this) {
        //  other.addProduct(this, result);
        //}
      }
    }
    return result;
  }

  /**
   * Construct a new prefix that equals the product of this
   * Prefix and the given other prefix.
   *
   * @param other
   *        The prefix with which this Prefix is multiplied
   */
 /*@
   @ protected behavior
   @
   @ pre other != null;
   @ pre other.getBase() == getBase();
   @
   @ post \fresh(\result);
   @ post \result.getBase() == getBase();
   @ post \result.getExponent() == getExponent() - other.getExponent();
   @*/
  protected /*@ pure @*/ Prefix makeQuotient(final Prefix other) {
    return new Prefix(_base, _exponent - other.getExponent());
  }

  /**
   * Set the inverse of this prefix to the given prefix.
   *
   * @param inverse
   *        The inverse of this prefix.
   */
 /*@
   @ private behavior
   @
   @ post inverse() == inverse;
   @*/
  private void setInverse(Prefix inverse) {
    _inverse = inverse;
  }

  /**
   * Add the given pair to the product map of this prefix.
   *
   * @param prefix
   *        The prefix with which this prefix has been multiplied.
   * @param result
   *        The result of that multiplication.
   */
 /*@
   @ behavior
   @
   @ post getProductMap().containsKey(prefix);
   @ post getProductMap().get(prefix) == result;
   @ post (\forall Prefix p; p != prefix;
   @        getProductMap().get(p) == \old(getProductMap().get(p)));
   @*/
  void addProduct(Prefix prefix, Prefix result) {
    _productMap.put(prefix, result);
  }

  /**
   * Return the product map of this Prefix
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
   * Add the given pair to the division map of this prefix.
   *
   * @param prefix
   *        The prefix by which this prefix has been divided.
   * @param result
   *        The result of that division.
   */
 /*@
   @ behavior
   @
   @ post getDivisionMap().containsKey(prefix);
   @ post getDivisionMap().get(prefix) == result;
   @ post (\forall Prefix p; p != prefix;
   @        getDivisionMap().get(p) == \old(getDivisionMap().get(p)));
   @*/
  void addDivision(Prefix prefix, Prefix result) {
    _divisionMap.put(prefix, result);
  }

  /**
   * Return the division map of this prefix.
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
   * Add the given pair to the power map of this prefix.
   *
   * @param power
   *        The power to  which this prefix is raised.
   * @param result
   *        The result.
   */
 /*@
   @ behavior
   @
   @ post getPowerMap().containsKey(power);
   @ post getPowerMap().get(power) == result;
   @*/
  void addPower(Integer power, Prefix result) {
    _powerMap.put(power, result);
  }

  /**
   * Return the division map of this prefix.
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
   * The inverse of this prefix.
   */
  private Prefix _inverse;

  /**
   * A map containing the results of multiplying this
   * Prefix with other prefixs. The key is the
   * prefix this prefix is multiplied with, the value
   * is the result.
   */
 /*@
   @ private invariant _productMap != null;
   @ private invariant (\forall Map.Entry e; _productMap.entrySet().contains(e);
   @                     (e != null) &&
   @                     (e.getKey() instanceof Prefix) &&
   @                     (e.getValue() instanceof Prefix));
   @*/
  private HashMap _productMap = new HashMap();
  
  /**
   * A map containing the results of dividing this
   * Prefix by other prefixs. The key is the
   * prefix by which this prefix is divided, the value
   * is the result.
   */
 /*@
   @ private invariant _divisionMap != null;
   @ private invariant (\forall Map.Entry e; _productMap.entrySet().contains(e);
   @                     (e != null) &&
   @                     (e.getKey() instanceof Prefix) &&
   @                     (e.getValue() instanceof Prefix));
   @*/
  private HashMap _divisionMap = new HashMap();

  /**
   * A map containting the results of raising this Prefix
   * to a certain power. The key of the map is the exponent,
   * the value is the result.
   */
 /*@
   @ private invariant _powerMap != null;
   @ private invariant (\forall Map.Entry e; _productMap.entrySet().contains(e);
   @                     (e != null) &&
   @                     (e.getKey() instanceof Double) &&
   @                     (e.getValue() instanceof Prefix));
   @*/
  private HashMap _powerMap = new HashMap();
  

/********
 * POOL *
 ********/
  
  /**
   * <p>Return the main instance of the given prefix. In order to speed up things
   * and use less memory, the implementation tries to use the same objects as much
   * as possible by:</p>
   * <ul>
   *   <li>caching the results of computations with prefixs</li>
   *   <li>replacing newly created instances of prefixs with the first instance
   *   that equals them.</li>
   *
   * @param prefix
   *        The prefix of which we want the prototype
   */
 /*@
   @ public behavior
   @
   @ pre prefix != null;
   @
   @ post \result != null;
   @ post (!\old(getAllUnits().contains(prefix))) ==>
   @        (getAllUnits().contains(prefix)) &&
   @        (\result == prefix);
   @ post (\old(getAllUnits().contains(prefix))) ==>
   @        (getAllUnits().contains(\result)) &&
   @        (\result.equals(prefix));
   @*/
  public static synchronized Prefix getPrototype(Prefix prefix) {
    ensurePrefix(prefix);
    return (Prefix)_prefixes.get(prefix);
  }

  /**
   * Make sure that the set of all prefixes contains a prefix that
   * equals the given prefix.
   *
   * @param prefix
   *        The prefix of which an instance should be in the set of
   *        all prefixes.
   */
 /*@
   @ public behavior
   @
   @ pre prefix != null;
   @
   @ post (!\old(getAllPrefixes().contains(prefix))) ==>
   @        Collections.containsExplicitly(getAllPrefixes(), prefix);
   @*/
  public static synchronized void ensurePrefix(Prefix prefix) {
    if (! _prefixes.containsKey(prefix)) {
      _prefixes.put(prefix, prefix);
    }
  }

  /**
   * Return a set containing all prefixes currently in use.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ post \fresh(\result);
   @*/
  public static synchronized Set getAllPrefixes() {
    return _prefixes.keySet();
  }

  /**
   * An identity map containing all the prefixes.
   *
   * This is a map because we need to perform a get(prefix) in order
   * to obtain a prefix from the pool instead of creating a new one.
   */
 /*@
   @ private invariant _prefixes != null;
   @ private invariant (\forall Object o; _prefixes.keySet().contains(o);
   @                      (o instanceof Prefix) && (_prefixes.get(o) == o));
   @*/
  private static Map _prefixes = new HashMap();
  
 /*@
   @ public invariant ONE.getName().equals("");
   @ public invariant ONE.getSymbol().equals("");
   @ public invariant ONE.getExponent() == 0;
   @ public inavriant ONE.getBase() != 0;
   @*/
  public final static Prefix ONE = new Prefix("","",1,0);
  
}
