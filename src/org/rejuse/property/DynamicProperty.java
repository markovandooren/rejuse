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
public abstract class DynamicProperty<E> extends PropertyImpl<E> {

  public DynamicProperty(String name, PropertyUniverse<E> universe, PropertyMutex<E> mutex) {
    super(name, universe, mutex);
  }
  
  protected DynamicProperty(String name, PropertyUniverse<E> universe, PropertyMutex<E> mutex, Prop<E> inverse) {
    super(name, universe, mutex, inverse);
  }
  
  protected void createInverse(String name, PropertyUniverse<E> universe) {
    new DynamicProperty<E>("not "+name, universe, mutex(), this) {
      @Override
      public Ternary appliesTo(E element) {
        return inverse().appliesTo(element).not();
      }
    	public Set<Prop<E>> implicitlyContradictedProperties() {
    		Set<Prop<E>> result = new HashSet<Prop<E>>();
    		result.add(inverse());
    		return result;
    	}
    	
    	public Set<Prop<E>> implicitlyImpliedByProperties() {
    		Set<Prop<E>> result = inverse().siblings();
    		result.add(this);
    		return result;
    	}
    	
    	public Set<Prop<E>> implicitlyImpliedProperties() {
    		Set<Prop<E>> result = new HashSet<Prop<E>>();
    		result.add(this);
    		return result;
    	}

    };
  }
}
