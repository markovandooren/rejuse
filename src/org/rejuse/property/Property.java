package org.rejuse.property;

import java.util.HashSet;
import java.util.Set;

import org.rejuse.association.Reference;
import org.rejuse.association.ReferenceSet;
import org.rejuse.java.collections.Accumulator;
import org.rejuse.java.collections.TransitiveClosure;

/**
 * <p>A class representing properties of model elements. Properties can be explicitly assigned to objects by in
 * an application specific manner, or they can apply to an object based on the state of that object.</p>
 *  
 * <p>The properties for a model are kept in a property universe. Relations between those properties are managed by adding
 * implication and contradiction relations between them.</p>
 *
 * Examples of properties are:
 * <ul>
 *   <li>Being overridable (or not)</li>
 *   <li>Being an instance (or class) member</li>
 * </ul> 
 * 
 * <h3>Design rationale</h3>
 * 
 * <p>By separating modifiers from the properties they assign to model elements, we obtain a
 * more extensible design.</p>
 * <p>First of all, it allows us to detects conflicts in the models in a general way.</p>
 * <p>Second, it allows us to create modifiers that set a number of properties, while still being able to
 * check their presence or absence in a unique way.</p> 
 * 
 * @author Marko van Dooren
 */
public abstract class Property<E> {
  
 /*@
   @ behavior
   @
   @ invariant inverse().inverse() == this; 
   @*/

  /**
   * Initialize a new property with the given name for the given language
   * @param name
   *        The name of the property
   * @param universe
   *        The property universe to which the new property will be attached
   */
 /*@
   @ behavior
   @
   @ pre name != null;
   @ pre universe != null;
   @
   @ post name().equals(name);
   @ post universe().equals(universe);
   @ post universe.properties().contains(this);
   @
   @ post (\exists Property p; universe.properties().contains(p);
   @       inverse() == p && p.name().equals("not "+name())) 
   @*/
  public Property(String name, PropertyUniverse<E> universe, PropertyMutex<E> family) {
    init(name, universe);
    setFamily(family);
    // Create the inverse property
    createInverse(name, universe);
  }
  
  /**
   * Check if this property currently applies to the given element
   * by verifying its state.
   * 
   * @param element
   */
  public abstract boolean appliesTo(E element);


  protected abstract void createInverse(String name, PropertyUniverse<E> universe);

  protected Property(String name, PropertyUniverse<E> universe, PropertyMutex<E> family, Property<E> inverse) {
    init(name, universe);
    _inverse.connectTo(inverse._inverse);
    addContradiction(inverse);
  }
  
 /*@
   @ behavior
   @
   @ name() == name;
   @ universe() == universe;
   @*/
  private void init(String name, PropertyUniverse<E> universe) {
    setName(name);
    _universe.connectTo(universe.propertyLink());
  }
  
  /**
   * Return the name of this property.
   */
 /*@
   @ behavior
   @
   @ post \result != null;
   @*/
  public String name() {
    return _name;
  }
  
  /**
   * Set the name of this property to the given name.
   * @param name
   *        The new name of the property
   */
 /*@
   @ behavior
   @
   @ pre name != null;
   @ post name().equals(name);
   @*/
  public void setName(String name) {
    _name = name;
  }
  
  private String _name;

  /**
   * Return the universe to which this property is attached.
   * @return
   */
 /*@
   @ behavior
   @
   @ post \result != null;
   @*/
  public PropertyUniverse<E> universe() {
    return _universe.getOtherEnd();
  }
  
  private Reference<Property<E>, PropertyUniverse<E>> _universe = new Reference<Property<E>, PropertyUniverse<E>>(this);
  
  /**
   * Return the inverse of this property.
   */
 /*@
   @ behavior
   @
   @ post \result != null;
   @ post \result.contradicts(this);
   @*/
  public Property<E> inverse() {
    return _inverse.getOtherEnd();
  }
  
  private Reference<Property<E>, Property<E>> _inverse = new Reference<Property<E>,Property<E>>(this);

  public PropertyMutex<E> mutex() {
  	return _family.getOtherEnd();
  }
  
  private void setFamily(PropertyMutex<E> family) {
  	if(family != null) {
  	  _family.connectTo(family.memberLink());
  	}
  }
  
  private Reference<Property<E>, PropertyMutex<E>> _family = new Reference<Property<E>,PropertyMutex<E>>(this);
  
  /**
   * Check whether this property implies the given property.
   * 
   * this => p
	 * 
	 * @param other
	 *        The property at the right-hand side of the implication
	 * @return
	 *        True if the property is present in the set of implied properties.
	 */
 /*@
   @ behavior
   @
   @ post \result == impliedProperties().contains(other); 
   @*/
	public boolean implies(Property<?> other) {
	  return impliedProperties().contains(other);
	}
	
	/**
	 * Return the set of properties implied by this property.
	 *
	 * @return
	 */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ post \result.containsAll(directlyImpliedProperties());
   @ post (\forall Property p; result.contains(p); 
   @               \result.containsAll(p.directlyImpliedProperties()));
   @*/
	public Set<Property<E>> impliedProperties() {
		return new TransitiveClosure<Property<E>>() {
			public Set<Property<E>> getConnectedNodes(Property<E> p) {
				return p.directlyImpliedProperties();
			}
		}.closure(this);
	}
	
  /**
   * Return the set of properties directly implied by this property.
   * @return
   */
 /*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @ post \result.containsAll(implicitlyImpliedProperties());
	 @*/
	public Set<Property<E>> directlyImpliedProperties() {
		Set<Property<E>> result = new HashSet<Property<E>>(_implied.getOtherEnds());
		result.addAll(implicitlyImpliedProperties());
		return result;
	}
	
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ post \result.contains(this);
   @*/
	public Set<Property<E>> implicitlyImpliedProperties() {
		Set<Property<E>> result = new HashSet<Property<E>>();
		result.add(this);
		result.addAll(inverseSiblings());
		return result;
	}

	
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ post (\forall Property<E> p; siblings().contains(p); 
   @         \result.contains(p.inverse()));
   @ post \result.size() == siblings().size();
   @*/
	public Set<Property<E>> inverseSiblings() {
		Set<Property<E>> result = new HashSet<Property<E>>();
		for(Property<E> p:siblings()) {
			result.add(p.inverse());
		}
		return result;
	}
	
	/**
	 * Add an implication from this property to the given property
	 * @param p
	 */
 /*@
   @ public behavior
   @
   @ pre p != null;
   @
   @ post directlyImpliedProperties().contains(p);
   @ post p.directlyImpliedByProperties().contains(this);
   @*/
	public void addImplication(Property<E> p) {
	  if(p != null) {
	    _implied.add(p._impliedBy);
	  }
	}
	
  /**
   * Check whether this property is implied by the given property.
   * 
   * p => this
   * 
   * @param other
   *        The property at the left-hand side of the implication
   * @return
   *        True if the property is present in the set of implied properties.
   */
 /*@
   @ public behavior
   @
   @ post \result == impliedProperties().contains(other); 
   @*/
	public boolean impliedBy(Property<E> other) {
	  return impliedProperties().contains(other);
	}
	
	/**
	 * Return the set of all properties that imply this property
	 */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ post \result.containsAll(directlyImpliedProperties());
   @ post (\forall Property p; result.contains(p); 
   @               \result.containsAll(p.directlyImpliedProperties()));
   @*/
	public Set<Property<E>> impliedByProperties() {
		return new TransitiveClosure<Property<E>>() {
			public Set<Property<E>> getConnectedNodes(Property<E> p) {
				return p.directlyImpliedByProperties();
			}
		}.closure(this);
	}

  /**
   * Return the set of properties that directly imply this property.
   * @return
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ post \result.containsAll(implicitlyImpliedByProperties());
   @*/
	public Set<Property<E>> directlyImpliedByProperties() {
		Set<Property<E>> result = new HashSet<Property<E>>(_impliedBy.getOtherEnds());
		result.addAll(implicitlyImpliedByProperties());
		return result;
	}

	
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ post \result.contains(this);
   @*/
	public Set<Property<E>> implicitlyImpliedByProperties() {
		Set<Property<E>> result = new HashSet<Property<E>>();
		result.add(this);
		return result;
	}
	
  /**
   * Check whether this property contradicts the given property.
   * 
   * p => this
   * 
   * @param other
   *        The property checked for a contradiction
   * @return
   *        True if the property is present in the set of contradicted properties.
   */
 /*@
   @ public behavior
   @
   @ post \result == contradictedProperties().contains(other); 
   @*/
	public boolean contradicts(Property<E> other) {
		return contradictedProperties().contains(other);
	}
	
	/**
	 * Return the properties contradicting this property.
	 * 
	 * This set contains of all properties Z that imply a property Y that
	 * contradicts a property X implied by this property.
	 * 
	 * this ----(implied)---> X ---(contradicts)---> Y ---(impliedBy)---> Z
	 * @return
	 */
	public Set<Property<E>> contradictedProperties() {
		// 1) all implied properties including the current property
    Set<Property<E>> implied = impliedProperties();
    // 2) all properties directly contradicting these properties
    final Set<Property<E>> directlyContradicted = new Accumulator<Property<E>, Set<Property<E>>>() {

      @Override
      public Set<Property<E>> accumulate(Property<E> element, Set<Property<E>> acc) {
        acc.addAll(element.directlyContradictedProperties());
        return acc;
      }

      @Override
      public Set<Property<E>> initialAccumulator() {
        return new HashSet<Property<E>>();
      }

    }.accumulate(implied);

    // 3) add all properties implying the properties in directlyContradicted
    return new Accumulator<Property<E>, Set<Property<E>>>() {

      @Override
      public Set<Property<E>> accumulate(Property<E> element, Set<Property<E>> acc) {
        acc.addAll(element.impliedByProperties());
        return acc;
      }

      @Override
      public Set<Property<E>> initialAccumulator() {
        return new HashSet<Property<E>>(directlyContradicted);
      }

    }.accumulate(directlyContradicted);
	}
	
  /**
   * Return the set of properties directly contradicted by this property. This 
   * includes the properties that are implicitly contradicted by this property,
   * and those for which explicit contradictions are added.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ post \result.containsAll(implicitlyContradictedProperties());
   @*/
	public Set<Property<E>> directlyContradictedProperties() {
		Set<Property<E>> result = new HashSet<Property<E>>(_contradicted.getOtherEnds());
		result.addAll(implicitlyContradictedProperties());
		return result;
	}
	
	/**
	 * Return the properties implicitly contradicted by this property. This
	 * includes the inverse of this property, and its siblings in its property
	 * family.
	 */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ post \result.contains(inverse());
   @*/
	public Set<Property<E>> implicitlyContradictedProperties() {
		Set<Property<E>> result = siblings();
		result.add(inverse());
		return result;
	}

	/*@
	 @ public behavior
	 @
	 @ post \result == family().membersWithout(this);
	 @*/
	public Set<Property<E>> siblings() {
		return mutex().membersWithout(this);
	}
	
	

	/**
   * Add an contradiction from this property to the given property
   * @param p
   */
 /*@
   @ public behavior
   @
   @ pre p != null;
   @
   @ post contradictedProperties().contains(p);
   @ post p.contradictedProperties().contains(this);
   @*/
  public void addContradiction(Property<E> p) {
    if(p != null) {
      _contradicted.add(p._contradicted);
    }
  }

	private ReferenceSet<Property<E>,Property<E>> _contradicted = new ReferenceSet<Property<E>,Property<E>>(this); 
	
	private ReferenceSet<Property<E>,Property<E>> _implied = new ReferenceSet<Property<E>,Property<E>>(this); 
	
	private ReferenceSet<Property<E>,Property<E>> _impliedBy = new ReferenceSet<Property<E>,Property<E>>(this);
	
}
