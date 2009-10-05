package org.rejuse.property;

import java.util.HashSet;
import java.util.Set;

import org.rejuse.association.SingleAssociation;
import org.rejuse.association.MultiAssociation;
import org.rejuse.java.collections.Accumulator;
import org.rejuse.java.collections.SafeAccumulator;
import org.rejuse.java.collections.SafeTransitiveClosure;
import org.rejuse.logic.ternary.Ternary;

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
public abstract class PropertyImpl<E> implements Property<E> {
  
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
  public PropertyImpl(String name, PropertyUniverse<E> universe, PropertyMutex<E> family) {
    init(name, universe);
    setFamily(family);
    // Create the inverse property
    createInverse(name, universe);
  }
  
  /* (non-Javadoc)
	 * @see org.rejuse.property.Prop#appliesTo(E)
	 */
  public abstract Ternary appliesTo(E element);


  protected abstract void createInverse(String name, PropertyUniverse<E> universe);

  protected PropertyImpl(String name, PropertyUniverse<E> universe, PropertyMutex<E> family, Property<E> inverse) {
    init(name, universe);
    _inverse.connectTo(inverse.inverseLink());
    addContradiction(inverse);
  }
  
 /*@
   @ behavior
   @
   @ post name() == name;
   @ post  universe() == universe;
   @*/
  private void init(String name, PropertyUniverse<E> universe) {
    setName(name);
    _universe.connectTo(universe.propertyLink());
  }
  
  /* (non-Javadoc)
	 * @see org.rejuse.property.Prop#name()
	 */
 /*@
   @ behavior
   @
   @ post \result != null;
   @*/
  public String name() {
    return _name;
  }
  
  /* (non-Javadoc)
	 * @see org.rejuse.property.Prop#setName(java.lang.String)
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

  /* (non-Javadoc)
	 * @see org.rejuse.property.Prop#universe()
	 */
 /*@
   @ behavior
   @
   @ post \result != null;
   @*/
  public PropertyUniverse<E> universe() {
    return _universe.getOtherEnd();
  }
  
  private SingleAssociation<Property<E>, PropertyUniverse<E>> _universe = new SingleAssociation<Property<E>, PropertyUniverse<E>>(this);
  
  /* (non-Javadoc)
	 * @see org.rejuse.property.Prop#inverse()
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
  
  public SingleAssociation<Property<E>, Property<E>> inverseLink() {
  	return _inverse;
  }
  
  private SingleAssociation<Property<E>, Property<E>> _inverse = new SingleAssociation<Property<E>,Property<E>>(this);

  /* (non-Javadoc)
	 * @see org.rejuse.property.Prop#mutex()
	 */
  public PropertyMutex<E> mutex() {
  	return _family.getOtherEnd();
  }
  
  private void setFamily(PropertyMutex<E> family) {
  	if(family != null) {
  	  _family.connectTo(family.memberLink());
  	}
  }
  
  private SingleAssociation<Property<E>, PropertyMutex<E>> _family = new SingleAssociation<Property<E>,PropertyMutex<E>>(this);
  
  /* (non-Javadoc)
	 * @see org.rejuse.property.Prop#implies(org.rejuse.property.Prop)
	 */
 /*@
   @ behavior
   @
   @ post \result == impliedProperties().contains(other); 
   @*/
	public boolean implies(Property<?> other) {
	  return impliedProperties().contains(other);
	}
	
	/* (non-Javadoc)
	 * @see org.rejuse.property.Prop#impliedProperties()
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
		return new SafeTransitiveClosure<Property<E>>() {
			public Set<Property<E>> getConnectedNodes(Property<E> p) {
				return p.directlyImpliedProperties();
			}
		}.closure(this);
	}
	
  /* (non-Javadoc)
	 * @see org.rejuse.property.Prop#directlyImpliedProperties()
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
	/* (non-Javadoc)
	 * @see org.rejuse.property.Prop#implicitlyImpliedProperties()
	 */
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
	/* (non-Javadoc)
	 * @see org.rejuse.property.Prop#inverseSiblings()
	 */
	public Set<Property<E>> inverseSiblings() {
		Set<Property<E>> result = new HashSet<Property<E>>();
		for(Property<E> p:siblings()) {
			result.add(p.inverse());
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see org.rejuse.property.Prop#addImplication(org.rejuse.property.Property)
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
	    _implied.add(p.impliedByLink());
	  }
	}
	
  /* (non-Javadoc)
	 * @see org.rejuse.property.Prop#impliedBy(org.rejuse.property.Prop)
	 */
 /*@
   @ public behavior
   @
   @ post \result == impliedProperties().contains(other); 
   @*/
	public boolean impliedBy(Property<E> other) {
	  return impliedProperties().contains(other);
	}
	
	/* (non-Javadoc)
	 * @see org.rejuse.property.Prop#impliedByProperties()
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
		return new SafeTransitiveClosure<Property<E>>() {
			public Set<Property<E>> getConnectedNodes(Property<E> p) {
				return p.directlyImpliedByProperties();
			}
		}.closure(this);
	}

  /* (non-Javadoc)
	 * @see org.rejuse.property.Prop#directlyImpliedByProperties()
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
	/* (non-Javadoc)
	 * @see org.rejuse.property.Prop#implicitlyImpliedByProperties()
	 */
	public Set<Property<E>> implicitlyImpliedByProperties() {
		Set<Property<E>> result = new HashSet<Property<E>>();
		result.add(this);
		return result;
	}
	
  /* (non-Javadoc)
	 * @see org.rejuse.property.Prop#contradicts(org.rejuse.property.Prop)
	 */
 /*@
   @ public behavior
   @
   @ post \result == contradictedProperties().contains(other); 
   @*/
	public boolean contradicts(Property<E> other) {
		return contradictedProperties().contains(other);
	}
	
	/* (non-Javadoc)
	 * @see org.rejuse.property.Prop#contradictedProperties()
	 */
	public Set<Property<E>> contradictedProperties() {
		// 1) all implied properties including the current property
    Set<Property<E>> implied = impliedProperties();
    // 2) all properties directly contradicting these properties
    final Set<Property<E>> directlyContradicted = new SafeAccumulator<Property<E>, Set<Property<E>>>() {

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
    return new SafeAccumulator<Property<E>, Set<Property<E>>>() {

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
	
  /* (non-Javadoc)
	 * @see org.rejuse.property.Prop#directlyContradictedProperties()
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
	
	/* (non-Javadoc)
	 * @see org.rejuse.property.Prop#implicitlyContradictedProperties()
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
	/* (non-Javadoc)
	 * @see org.rejuse.property.Prop#siblings()
	 */
	public Set<Property<E>> siblings() {
		return mutex().membersWithout(this);
	}
	
	

	/* (non-Javadoc)
	 * @see org.rejuse.property.Prop#addContradiction(org.rejuse.property.Property)
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
      _contradicted.add(p.contradictedLink());
    }
  }

	public MultiAssociation<Property<E>,Property<E>> contradictedLink() {
		return _contradicted;
	}
	
	public MultiAssociation<Property<E>,Property<E>> impliedLink() {
		return _implied;
	}
	
	public MultiAssociation<Property<E>,Property<E>> impliedByLink() {
		return _impliedBy;
	}

	private MultiAssociation<Property<E>,Property<E>> _contradicted = new MultiAssociation<Property<E>,Property<E>>(this); 
	
	private MultiAssociation<Property<E>,Property<E>> _implied = new MultiAssociation<Property<E>,Property<E>>(this); 
	
	private MultiAssociation<Property<E>,Property<E>> _impliedBy = new MultiAssociation<Property<E>,Property<E>>(this);
	
}
