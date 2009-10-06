package org.rejuse.property;

import java.util.HashSet;
import java.util.Set;

import org.rejuse.logic.ternary.Ternary;



/**
 * A class of properties that cannot only be assigned to an object explicitly, but
 * that can also apply to an object based on the state of that object.
 *
 * @author Marko van Dooren
 */
public abstract class DynamicProperty<E,F extends Property<E,F>> extends PropertyImpl<E,F> {

  protected static abstract class InverseProperty<E,F extends Property<E,F>> extends DynamicProperty<E, F> {
		public InverseProperty(String name, PropertyUniverse<F> universe, PropertyMutex<F> mutex, F inverse) {
			super(name, universe, mutex, inverse);
		}

		@Override
		public Ternary appliesTo(E element) {
		  return inverse().appliesTo(element).not();
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

	public DynamicProperty(String name, PropertyUniverse<F> universe, PropertyMutex<F> mutex) {
    super(name, universe, mutex);
  }
  
  protected DynamicProperty(String name, PropertyUniverse<F> universe, PropertyMutex<F> mutex, F inverse) {
    super(name, universe, mutex, inverse);
  }
  
//  protected void createInverse(String name, PropertyUniverse<F> universe) {
//    new InverseProperty("not "+name, universe, mutex(), (F)this);
//  }
}
