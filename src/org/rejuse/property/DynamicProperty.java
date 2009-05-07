package org.rejuse.property;

import java.util.HashSet;
import java.util.Set;



/**
 * A class of properties that cannot only be assigned to an object explicitly, but
 * that can also apply to an object based on the state of that object.
 *
 * @author Marko van Dooren
 */
public abstract class DynamicProperty<E> extends Property<E> {

  public DynamicProperty(String name, PropertyUniverse<E> universe, PropertyMutex<E> mutex) {
    super(name, universe, mutex);
  }
  
  protected DynamicProperty(String name, PropertyUniverse universe, PropertyMutex<E> mutex, Property inverse) {
    super(name, universe, mutex, inverse);
  }
  
  protected void createInverse(String name, PropertyUniverse<E> universe) {
    new DynamicProperty<E>("not "+name, universe, mutex(), this) {
      @Override
      public boolean appliesTo(E element) {
        return ! inverse().appliesTo(element);
      }
    	public Set<Property<E>> implicitlyContradictedProperties() {
    		Set<Property<E>> result = new HashSet<Property<E>>();
    		result.add(inverse());
    		return result;
    	}
    	
    	public Set<Property<E>> implicitlyImpliedByProperties() {
    		Set<Property<E>> result = inverse().siblings();
    		result.add(this);
    		return result;
    	}
    	
    	public Set<Property<E>> implicitlyImpliedProperties() {
    		Set<Property<E>> result = new HashSet<Property<E>>();
    		result.add(this);
    		return result;
    	}

    };
  }
}
