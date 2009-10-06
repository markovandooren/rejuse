package org.rejuse.property;

import java.util.HashSet;
import java.util.Set;

import org.rejuse.logic.ternary.Ternary;

public abstract class StaticProperty<E, F extends Property<E,F>> extends PropertyImpl<E,F> {
  
  protected static abstract class InverseProperty<E, F extends Property<E,F>> extends StaticProperty<E,F> {
  	
  	public InverseProperty(String name, PropertyUniverse<F> universe, PropertyMutex<F> family, F inverse) {
			super(name, universe, family, inverse);
		}

		public Set<F> implicitlyContradictedProperties() {
			Set<F> result = new HashSet<F>();
			result.add(inverse());
			return result;
		}

		public Set<F> implicitlyImpliedByProperties() {
			Set<F> result = inverse().siblings();
			result.add((F) this);
			return result;
		}

		public Set<F> implicitlyImpliedProperties() {
			Set<F> result = new HashSet<F>();
			result.add((F) this);
			return result;
		}
	}

	/**
   * Initialize a new static property with the given name for the given property universe and mutex
   * @param name
   *        The name of the static property
   * @param universe
   *        The property universe to which the new property will be attached
   * @param mutex
   *        The mutex used for this property
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
   @ post family() == mutex;
   @
   @ post (\exists Property p; universe.properties().contains(p);
   @       inverse() == p && p.name().equals("not "+name())) 
   @*/
  public StaticProperty(String name, PropertyUniverse<F> universe, PropertyMutex<F> mutex) {
    super(name, universe, mutex);
  }

	/**
   * Initialize a new static property with the given name for the given property universe
   * @param name
   *        The name of the static property
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
   @ post \fresh(family());
   @
   @ post (\exists Property p; universe.properties().contains(p);
   @       inverse() == p && p.name().equals("not "+name())) 
   @*/
  public StaticProperty(String name, PropertyUniverse<F> universe) {
    super(name, universe, new PropertyMutex<F>());
  }

  /**
   * Initialize a new static property with the given name for the given language
   * @param name
   *        The name of the static property
   * @param universe
   *        The property universe to which the new property will be attached
   * @param inverse
   *        The property that is the inverse of this property
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
   @ post inverse() == inverse;
   @*/
  protected StaticProperty(String name, PropertyUniverse<F> universe, PropertyMutex<F> family, F inverse) {
    super(name, universe, family, inverse);
  }
  
//  protected void createInverse(String name, PropertyUniverse<F> universe) {
//    new InverseProperty("not "+name, universe, mutex(), this);
//  }
  
  /**
   * A static property does not know if it applies to any element based on the
   * state of that element.
   */
 /*@
   @ behavior
   @
   @ post \result == false;
   @*/
  public Ternary appliesTo(E element) {
    return Ternary.UNKNOWN;
  }

}
