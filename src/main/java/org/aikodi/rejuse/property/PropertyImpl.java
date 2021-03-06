package org.aikodi.rejuse.property;

import java.util.HashSet;
import java.util.Set;

import org.aikodi.rejuse.action.Nothing;
import org.aikodi.rejuse.association.AssociationListener;
import org.aikodi.rejuse.association.MultiAssociation;
import org.aikodi.rejuse.association.SingleAssociation;
import org.aikodi.rejuse.java.collections.Sets;
import org.aikodi.rejuse.java.collections.TransitiveClosure;
import org.aikodi.rejuse.logic.ternary.Ternary;

import com.google.common.collect.ImmutableSet;

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
public abstract class PropertyImpl<E,F extends Property<E,F>> implements Property<E,F> {

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
  public PropertyImpl(String name, PropertyMutex<F> family) {
    init(name);
    setFamily(family);
    // Create the inverse property
    createInverse(name);
  }

  /* (non-Javadoc)
   * @see org.rejuse.property.Prop#appliesTo(E)
   */
  public abstract Ternary appliesTo(E element);


  protected abstract void createInverse(String name);

  protected PropertyImpl(String name, PropertyMutex<F> family, F inverse) {
    init(name);
    _inverse.connectTo(inverse.inverseLink());
    addContradiction(inverse);
  }

  /*@
   @ behavior
   @
   @ post name() == name;
   @ post  universe() == universe;
   @*/
  private void init(String name) {
    AssociationListener<F> listener = new AssociationListener<F>() {
      @Override
      public void notifyElementAdded(F element) {
        flushCache();
      }

      @Override
      public void notifyElementRemoved(F element) {
        flushCache();
      }
    };
    _contradicted.addListener(listener);
    _implied.addListener(listener);
    _inverse.addListener(listener);
    AssociationListener<PropertyUniverse> ulistener = new AssociationListener<PropertyUniverse>() {
      @Override
      public void notifyElementAdded(PropertyUniverse element) {
        element.flushCache();
      }

      @Override
      public void notifyElementRemoved(PropertyUniverse element) {
        element.flushCache();
      }
    };
//    _universe.addListener(ulistener);

    setName(name);
//    _universe.connectTo(universe.propertyLink());
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

  public String toString() {
    return name();
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

//  /* (non-Javadoc)
//   * @see org.rejuse.property.Prop#universe()
//   */
//  /*@
//   @ behavior
//   @
//   @ post \result != null;
//   @*/
//  public PropertyUniverse<F> universe() {
//    return _universe.getOtherEnd();
//  }

//  private SingleAssociation<F, PropertyUniverse<F>> _universe = new SingleAssociation<F, PropertyUniverse<F>>((F) this);

  /* (non-Javadoc)
   * @see org.rejuse.property.Prop#inverse()
   */
  /*@
   @ behavior
   @
   @ post \result != null;
   @ post \result.contradicts(this);
   @*/
  public F inverse() {
    return _inverse.getOtherEnd();
  }

  public SingleAssociation<F,F> inverseLink() {
    return _inverse;
  }

  private SingleAssociation<F,F> _inverse = new SingleAssociation<F,F>((F) this);

  /* (non-Javadoc)
   * @see org.rejuse.property.Prop#mutex()
   */
  public PropertyMutex<F> mutex() {
    return _family.getOtherEnd();
  }

  private void setFamily(PropertyMutex<F> family) {
    if(family != null) {
      _family.connectTo(family.memberLink());
    }
  }

  private SingleAssociation<F, PropertyMutex<F>> _family = new SingleAssociation<F,PropertyMutex<F>>((F) this);

  /* (non-Javadoc)
   * @see org.rejuse.property.Prop#implies(org.rejuse.property.Prop)
   */
  /*@
   @ behavior
   @
   @ post \result == impliedProperties().contains(other); 
   @*/
  public boolean implies(Property<E,?> other) {
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
  public Set<F> impliedProperties() {
    return new TransitiveClosure<F, Nothing>() {
      public void addConnectedNodes(F p, Set<F> acc) {
        acc.addAll(p.directlyImpliedProperties());
      }
    }.closure((F) this);
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
  public Set<F> directlyImpliedProperties() {
    Set<F> result = implicitlyImpliedProperties();
    _implied.addOtherEndsTo(result);
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
  public Set<F> implicitlyImpliedProperties() {
    Set<F> result = new HashSet<F>();
    result.add((F) this);
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
  public Set<F> inverseSiblings() {
    Set<F> result = new HashSet<F>();
    for(F p:siblings()) {
      F inverse = p.inverse();
      if(inverse != null) {
        result.add(inverse);
      }
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
  public void addImplication(F p) {
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
  public boolean impliedBy(Property<E,?> other) {
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
  public Set<F> impliedByProperties() {
    if(_impliedByCache == null) {
      _impliedByCache = new TransitiveClosure<F, Nothing>() {
        public void addConnectedNodes(F p, Set<F> acc) {
          acc.addAll(p.directlyImpliedByProperties());
        }
      }.closure((F) this);
    }
    return _impliedByCache;
  }

  private Set<F> _impliedByCache;

  private Set<F> _contradictedByCache;

  public void flushLocalCache() {
    _impliedByCache = null;
    _contradictedByCache = null;
  }

  public void flushCache() {
    //		universe().flushCache();
    flushCache(new HashSet<>());
  }

  public void flushCache(Set<Property<E,F>> flushed) {
    if(! flushed.contains(this)) {
      flushed.add(this);
      flushLocalCache();
      for(Property<E,F> property: directlyImpliedProperties()) {
        property.flushCache(flushed);
      }
      for(Property<E,F> property: directlyImpliedByProperties()) {
        property.flushCache(flushed);
      }
      for(Property<E,F> property: directlyContradictedProperties()) {
        property.flushCache(flushed);
      }
    }
  }

  public boolean contradictedBy(Set<F> set) {
    Set<F> mine = contradictedProperties();
    for(F f: set) {
      if(mine.contains(f)) {
        return true;
      }
    }
    return false;
  }

  public boolean impliedBy(Set<F> set) {
    if(set.contains(this)) {
      return true;
    }
    Set<F> mine = impliedByProperties();
    for(F f: set) {
      if(mine.contains(f)) {
        return true;
      }
    }
    return false;
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
  public Set<F> directlyImpliedByProperties() {
    Set<F> result = implicitlyImpliedByProperties();
    //		result.addAll(_impliedBy.getOtherEnds());
    _impliedBy.addOtherEndsTo(result);
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
  public Set<F> implicitlyImpliedByProperties() {
    Set<F> result = new HashSet<F>();
    result.add((F) this);
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
  public boolean contradicts(Property<E,?> other) {
    return contradictedProperties().contains(other);
  }

  /* (non-Javadoc)
   * @see org.rejuse.property.Prop#contradictedProperties()
   */
  public Set<F> contradictedProperties() {
    if(_contradictedByCache == null) {
      // 1) all implied properties including the current property
      Set<F> implied = impliedProperties();
      // 2) all properties directly contradicting these properties
      Set<F> directlyContradicted = implied.stream().reduce(Sets.empty(), (acc,  element) -> {
      	acc.addAll(element.directlyContradictedProperties());
      	return acc;
      }, Sets.union());
      
      
      // 3) add all properties implying the properties in directlyContradicted
      _contradictedByCache = directlyContradicted.stream().reduce(Sets.clone(directlyContradicted), (acc, element) -> {
        acc.addAll(element.impliedByProperties());
        return acc;
      }, Sets.union());
      
    }
    return _contradictedByCache;
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
  public Set<F> directlyContradictedProperties() {
    Set<F> result = implicitlyContradictedProperties();
    _contradicted.addOtherEndsTo(result);
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
  public Set<F> implicitlyContradictedProperties() {
    Set<F> result = siblings();
    F inverse = inverse();
    if(inverse != null) {
      result.add(inverse);
    }
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
  public Set<F> siblings() {
    return mutex().membersWithout((F) this);
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
  public void addContradiction(F p) {
    if(p != null) {
      _contradicted.add(p.contradictedLink());
    }
  }

  public MultiAssociation<F,F> contradictedLink() {
    return _contradicted;
  }

  public MultiAssociation<F,F> impliedLink() {
    return _implied;
  }

  public MultiAssociation<F,F> impliedByLink() {
    return _impliedBy;
  }

  /**
   * (P1 => ! P2) == (P2 => ! P1), so the association is
   * inherently bidirectional. Therefore we only need a single
   * association object. There is no need to distinguish the roles. 
   */
  private MultiAssociation<F,F> _contradicted = new MultiAssociation<F,F>((F) this); 

  private MultiAssociation<F,F> _implied = new MultiAssociation<F,F>((F) this); 

  private MultiAssociation<F,F> _impliedBy = new MultiAssociation<F,F>((F) this);

}
