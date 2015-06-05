package be.kuleuven.cs.distrinet.rejuse.property;

import static be.kuleuven.cs.distrinet.rejuse.collection.CollectionOperations.forAll;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import be.kuleuven.cs.distrinet.rejuse.collection.CollectionOperations;
import be.kuleuven.cs.distrinet.rejuse.logic.ternary.Ternary;
import be.kuleuven.cs.distrinet.rejuse.predicate.SafePredicate;

/**
 * A class of sets of properties. A property set offer functionality to compute
 * for example, if its properties are consistent with each other, or whether its
 * properties imply a given property.
 * 
 * @author Marko van Dooren
 *
 * @param <E> The type of the elements that can have properties.
 * @param <P> The type of the properties.
 */
public class PropertySet<E,P extends Property<E,P>> {

	/**
	 * Create an empty property set.
	 */
 /*@
   @ public behavior
   @
   @ post isEmpty();
   @*/
	public PropertySet() {
	}
	
	/**
	 * Create a new property set that contains the given properties
	 * @param properties
	 */
 /*@
   @ public behavior
   @
   @ pre properties != null;
   @
   @ post containsAll(properties); 
   @*/
	public PropertySet(P... properties) {
		for(P p: properties){
			add(p);
		}
	}
	
	public PropertySet(PropertySet<E, P> toBeCopied) {
		if(toBeCopied == null) {
			throw new IllegalArgumentException();
		}
		for(P p: toBeCopied._properties) {
			add(p);
		}
	}

	/**
	 * Create a new property set that contains the given properties
	 * @param properties
	 */
 /*@
   @ public behavior
   @
   @ pre properties != null;
   @
   @ post containsAll(properties); 
   @*/
	public PropertySet(Collection<P> properties) {
		addAll(properties);
	}
	
	/**
	 * Check if the given property is in this property set.
	 * @param property
	 * @return
	 */
 /*@
   @ public behavior
   @
   @ post \result == properties().contains(property);
   @*/
	public boolean contains(P property) {
		return _properties.contains(property);
	}
	
	/**
	 * Check if all of the given properties are in this property set.
	 * @param property
	 * @return
	 */
 /*@
   @ public behavior
   @
   @ post \result == properties().containsAll(properties);
   @*/
	public boolean containsAll(Collection<P> properties) {
		return _properties.containsAll(properties);
	}
	
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
  public void add(P p) {
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
  public PropertySet<E,P> clone() {
    PropertySet<E,P> result = new PropertySet<E,P>();
    result.addAll(unsafeProperties());
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
  public PropertySet<E,P> with(P property) {
    PropertySet<E,P> result = clone();
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
  public void addAll(Collection<P> properties) {
    if(properties != null) {
      _properties.addAll(properties);
    } else {
      throw new IllegalArgumentException("Trying to add the properties of a null set to a property set.");
    }
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
  public void addAll(PropertySet<E,P> properties) {
    addAll(properties.unsafeProperties());
  }

  protected Set<P> unsafeProperties() {
  	return _properties;
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
  public void removeProperty(P p) {
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
  public Set<P> properties() {
    return new HashSet<P>(_properties);
  }
  
  public boolean hasPropertyFor(PropertyMutex mutex) {
  	boolean result = false;
  	for(Property property: _properties) {
  		if(property.mutex() == mutex) {
  			result = true;
  		}
  	}
  	return result;
  }
  
  /**
   * Check if the properties in this property set are consistent.
   * @return
   */
 /*@
   @ behavior
   @
   @ post \result == forAll(unsafeProperties(), p -> internallyConsistent(p));
   @*/
  public boolean consistent() {
    return forAll(unsafeProperties(), p -> internallyConsistent(p));
  }
  
  /**
   * Return all conflicts in this property set.
   */
 /*@
   @ public behavior
   @
   @ post (\forall Property<E> first, second; ; 
   @            first.contradicts(second) <=> (\exists Conflict<E> c; c.first()==first && c.second()==second; \result.contains(c)); 
   @*/
  public Collection<Conflict<P>> conflicts() {
  	Collection<Conflict<P>> result = new ArrayList<Conflict<P>>();
  	// Create a list to cut the comparisons in half
  	List<P> properties = new ArrayList<P>(_properties);
  	int size = properties.size();
		for(int firstIndex = 0; firstIndex < size; firstIndex++) {
			for(int secondIndex = firstIndex+1; secondIndex < size; secondIndex++) {
				P first = properties.get(firstIndex);
				P second = properties.get(secondIndex);
  			if(first.contradicts(second)) {
  				result.add(new Conflict<P>(first,second));
  			}
			}
  	}
    return result;
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
  public boolean internallyConsistent(final Property<E,?> property) {
    return forAll(unsafeProperties(), p -> ! property.contradicts(p));
  }
  
  /**
   * Check if the properties in this property set imply a given property.
   * 
   * Let IMPLYING be the set of properties that imply the given property.
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
  public Ternary implies(Property<E,?> property) {
    Set properties = propertiesView();
        boolean implied = property.impliedBy(properties);
    boolean contradicted = property.contradictedBy(properties);
    if(implied && (! contradicted)) {
        return Ternary.TRUE;
    } else if ((! implied) && contradicted) {
        return Ternary.FALSE;
    } else {
        return Ternary.UNKNOWN; 
    }

  }
  
  
  
  private Set<P> propertiesView() {
  	if(_propertiesCache == null) {
  		_propertiesCache = Collections.unmodifiableSet((Set)unsafeProperties());
  	}
  	return _propertiesCache;
  }
  
  private Set<P> _propertiesCache;
  
  /** 
   * Check if the properties in this property set contradict the given property.
   * The result is the negation of the implies method.
   * 
   * @param property
   * @return
   */
 /*@
   @ public behavior
   @
   @ post \result == implies(property).not();
   @*/
  public Ternary contradicts(Property<E,?> property) {
  	return implies(property).not();
  }
  
 /*@
   @ public behavior
   @
   @ post (\forall Property<E> p; contains(p); set.contradicts(p) != Ternary.TRUE);
   @*/
  public void removeContradictingProperties(PropertySet<E,P> set) {
  	Iterator<P> ps = unsafeProperties().iterator();
  	while(ps.hasNext()) {
  		P p = ps.next();
  		if(set.contradicts(p) == Ternary.TRUE) {
  			ps.remove();
  		}
  	}
  }
  
 /*@
   @ public behavior
   @
   @ post (\forall Property<E> p; contains(p); set.contradicts(p) != TRUE == \result.contains(p));
   @*/
  public PropertySet<E,P> withoutContradictingProperties(PropertySet<E,P> set) {
  	PropertySet<E,P> result = clone();
  	result.removeContradictingProperties(set);
  	return result;
  }
  
  /**
   * The properties in this property set.
   */
  private Set<P> _properties = new HashSet<P>();
}
