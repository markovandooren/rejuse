package org.rejuse.property;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.rejuse.logic.ternary.Ternary;
import org.rejuse.predicate.PrimitiveTotalPredicate;

/**
 * A class of sets of properties. A property set offer functionality to compute
 * for example, if its properties are consistent with each other, or whether its
 * properties imply a given property.
 * 
 * @author Marko van Dooren
 *
 * @param <E> The type of the elements that can have properties.
 */
public class PropertySet<E> {

  /**
   * Add the given property to the property set.
   * @param p
   *        The property to be added.
   */
 /*@
   @ behavior
   @
   @ pre p != null;
   @
   @ post properties().contains(p);
   @*/
  public void add(Property<E> p) {
    if(p != null) {
      _properties.add(p);
    } else {
      throw new IllegalArgumentException("Trying to add null to a property set.");
    }
  }
  
  /**
   * Return a clone of this property set.
   */
 /*@
   @ behavior
   @
   @ post \result != null;
   @ post \fresh(\result);
   @ post \result.containsAll(properties());
   @ post \result.size() == size(); 
   @*/
  public PropertySet<E> clone() {
    PropertySet<E> result = new PropertySet<E>();
    result.addAll(properties());
    return result;    
  }
  
  /**
   * Return a new property set that equals this property set plus the given property 
   * @param property
   *        The extra property
   */
 /*@
   @ pre property != null
   @
   @ post \result != null;
   @ post \fresh(\result);
   @ post \result.containsAll(properties());
   @ post \result.contains(property);
   @ post properties().contains(property) ==> \result.size() == size();
   @ post ! properties().contains(property) ==> \result.size() == size() + 1;
   @*/
  public PropertySet<E> with(Property<E> property) {
    PropertySet<E> result = clone();
    result.add(property);
    return result;
  }
  
 /*@
   @ behavior
   @
   @ post \result == properties().size();
   @*/
  public int size() {
    return _properties.size();
  }
  
  /**
   * Add the given properties to the property set.
   * @param p
   *        The collection containing the properties to be added.
   */
 /*@
   @ behavior
   @
   @ pre properties != null;
   @
   @ post properties().containsAll(properties);
  */
  public void addAll(Collection<Property<E>> properties) {
    if(properties != null) {
      _properties.addAll(properties);
    } else {
      throw new IllegalArgumentException("Trying to add the properties of a null set to a property set.");
    }
  }
  

  
  /**
   * Remove the given property from the property set.
   * @param p
   *        The property to be removed.
   */
 /*@
   @ behavior
   @
   @ pre p != null;
   @
   @ post ! properties().contains(p);
  */
  public void removeProperty(Property<E> p) {
    _properties.remove(p);
  }
  
  /**
   * Return the set of properties in the property set.
   * @return
   */
 /*@
   @ behavior
   @
   @ post \result != null;
   @*/
  public Set<Property<E>> properties() {
    return new HashSet<Property<E>>(_properties);
  }
  
  /**
   * Check if the properties in this property set are consistent.
   * @return
   */
 /*@
   @ behavior
   @
   @ post \result == (\forall p1,p2; properties().contains(p1) & properties().contains(p2);
   @                   ! p1.contradicts(p2));
   @*/
  public boolean consistent() {
    return new PrimitiveTotalPredicate<Property<E>>() {
      @Override
      public boolean eval(final Property<E> p1) {
        return internallyConsistent(p1);
      }
    }.forAll(properties());
  }
  
  /**
   * Check if the given property is consistent with all other
   * properties in this property set.
   */
 /*@
   @ behavior
   @
   @ post \result == (\forall p; properties().contains(p);
   @                   ! property.contradicts(p));
   @*/
  public boolean internallyConsistent(final Property<E> property) {
    return new PrimitiveTotalPredicate<Property<E>>() {
      @Override
      public boolean eval(Property<E> p2) {
        return ! property.contradicts(p2);
      }
    }.forAll(properties());
  }
  
  /**
   * Check if the properties in this property set imply a given property.
   * 
   * Let IMPLYING be the set of properties that imply the given properties.
   * Let IMPLYING_INTERSECT be the set of properties in IMPLYING that are part
   * of this property set. Let IMPLYING_INTERSECT_CON be the set of properties in
   * IMPLYING_INTERSECT that are internally consistent in this property set.
   *
   * Let CONTRADICTING, CONTRADICTING_INTERSECT, and CONTRADICTING_INTERSECT_CON be
   * the corresponding sets for properties contradicting the given property.
   * 
   * If IMPLYING_INTERSECT_CON is not empty, and CONTRADICTING_INTERSECT_CON is empty, 
   * the result is TRUE. If IMPLYING_INTERSECT_CON is empty, and CONTRADICTING_INTERSECT_CON is not empty, 
   * the result is FALSE. In all other cases, the result is UNKNOWN.
   * 
   * Rationale: if a property A implies or contradicts the given property, but there is another
   * property in this property set that contradicts A (making it not internally consistent), then
   * we cannot take A into account; therefore, we discard it. 
   * 
   * @param property
   *        The property to be verified.
   * @return
   */
 /*@
   @ behavior
   @
   @ pre property != null;
   @
   @*/
  public Ternary implies(Property<E> property) {
    PrimitiveTotalPredicate<Property<E>> propertyFilter = new PrimitiveTotalPredicate<Property<E>>() {
      @Override
      public boolean eval(Property<E> property) {
        return properties().contains(property) && internallyConsistent(property);
      }
    };
    Set<Property<E>> implying = property.impliedByProperties();
    propertyFilter.filter(implying);
    
    Set<Property<E>> contradicting = property.contradictedProperties();
    propertyFilter.filter(contradicting);
    
    if((! implying.isEmpty()) && (contradicting.isEmpty())) {
        return Ternary.TRUE;
    } else if ((implying.isEmpty()) && (! contradicting.isEmpty())) {
        return Ternary.FALSE;
    } else {
      return Ternary.UNKNOWN; 
    }
    
  }
  
  private Set<Property<E>> _properties = new HashSet<Property<E>>();
}
