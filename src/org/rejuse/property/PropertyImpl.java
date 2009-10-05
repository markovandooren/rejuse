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
public abstract class PropertyImpl<E> implements Prop<E> {
  
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

  protected PropertyImpl(String name, PropertyUniverse<E> universe, PropertyMutex<E> family, Prop<E> inverse) {
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
  
  private SingleAssociation<Prop<E>, PropertyUniverse<E>> _universe = new SingleAssociation<Prop<E>, PropertyUniverse<E>>(this);
  
  /* (non-Javadoc)
	 * @see org.rejuse.property.Prop#inverse()
	 */
 /*@
   @ behavior
   @
   @ post \result != null;
   @ post \result.contradicts(this);
   @*/
  public Prop<E> inverse() {
    return _inverse.getOtherEnd();
  }
  
  public SingleAssociation<Prop<E>, Prop<E>> inverseLink() {
  	return _inverse;
  }
  
  private SingleAssociation<Prop<E>, Prop<E>> _inverse = new SingleAssociation<Prop<E>,Prop<E>>(this);

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
  
  private SingleAssociation<Prop<E>, PropertyMutex<E>> _family = new SingleAssociation<Prop<E>,PropertyMutex<E>>(this);
  
  /* (non-Javadoc)
	 * @see org.rejuse.property.Prop#implies(org.rejuse.property.Prop)
	 */
 /*@
   @ behavior
   @
   @ post \result == impliedProperties().contains(other); 
   @*/
	public boolean implies(Prop<?> other) {
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
	public Set<Prop<E>> impliedProperties() {
		return new SafeTransitiveClosure<Prop<E>>() {
			public Set<Prop<E>> getConnectedNodes(Prop<E> p) {
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
	public Set<Prop<E>> directlyImpliedProperties() {
		Set<Prop<E>> result = new HashSet<Prop<E>>(_implied.getOtherEnds());
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
	public Set<Prop<E>> implicitlyImpliedProperties() {
		Set<Prop<E>> result = new HashSet<Prop<E>>();
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
	public Set<Prop<E>> inverseSiblings() {
		Set<Prop<E>> result = new HashSet<Prop<E>>();
		for(Prop<E> p:siblings()) {
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
	public void addImplication(Prop<E> p) {
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
	public boolean impliedBy(Prop<E> other) {
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
	public Set<Prop<E>> impliedByProperties() {
		return new SafeTransitiveClosure<Prop<E>>() {
			public Set<Prop<E>> getConnectedNodes(Prop<E> p) {
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
	public Set<Prop<E>> directlyImpliedByProperties() {
		Set<Prop<E>> result = new HashSet<Prop<E>>(_impliedBy.getOtherEnds());
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
	public Set<Prop<E>> implicitlyImpliedByProperties() {
		Set<Prop<E>> result = new HashSet<Prop<E>>();
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
	public boolean contradicts(Prop<E> other) {
		return contradictedProperties().contains(other);
	}
	
	/* (non-Javadoc)
	 * @see org.rejuse.property.Prop#contradictedProperties()
	 */
	public Set<Prop<E>> contradictedProperties() {
		// 1) all implied properties including the current property
    Set<Prop<E>> implied = impliedProperties();
    // 2) all properties directly contradicting these properties
    final Set<Prop<E>> directlyContradicted = new SafeAccumulator<Prop<E>, Set<Prop<E>>>() {

      @Override
      public Set<Prop<E>> accumulate(Prop<E> element, Set<Prop<E>> acc) {
        acc.addAll(element.directlyContradictedProperties());
        return acc;
      }

      @Override
      public Set<Prop<E>> initialAccumulator() {
        return new HashSet<Prop<E>>();
      }

    }.accumulate(implied);

    // 3) add all properties implying the properties in directlyContradicted
    return new SafeAccumulator<Prop<E>, Set<Prop<E>>>() {

      @Override
      public Set<Prop<E>> accumulate(Prop<E> element, Set<Prop<E>> acc) {
        acc.addAll(element.impliedByProperties());
        return acc;
      }

      @Override
      public Set<Prop<E>> initialAccumulator() {
        return new HashSet<Prop<E>>(directlyContradicted);
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
	public Set<Prop<E>> directlyContradictedProperties() {
		Set<Prop<E>> result = new HashSet<Prop<E>>(_contradicted.getOtherEnds());
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
	public Set<Prop<E>> implicitlyContradictedProperties() {
		Set<Prop<E>> result = siblings();
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
	public Set<Prop<E>> siblings() {
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
  public void addContradiction(Prop<E> p) {
    if(p != null) {
      _contradicted.add(p.contradictedLink());
    }
  }

	public MultiAssociation<Prop<E>,Prop<E>> contradictedLink() {
		return _contradicted;
	}
	
	public MultiAssociation<Prop<E>,Prop<E>> impliedLink() {
		return _implied;
	}
	
	public MultiAssociation<Prop<E>,Prop<E>> impliedByLink() {
		return _impliedBy;
	}

	private MultiAssociation<Prop<E>,Prop<E>> _contradicted = new MultiAssociation<Prop<E>,Prop<E>>(this); 
	
	private MultiAssociation<Prop<E>,Prop<E>> _implied = new MultiAssociation<Prop<E>,Prop<E>>(this); 
	
	private MultiAssociation<Prop<E>,Prop<E>> _impliedBy = new MultiAssociation<Prop<E>,Prop<E>>(this);
	
}
