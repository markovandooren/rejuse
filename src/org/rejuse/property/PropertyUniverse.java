package org.rejuse.property;

import java.util.Set;

import org.rejuse.association.MultiAssociation;

/**
 * A universe of properties. Properties are added to a universe by creating new properties
 * and passing a universe to the constructor.
 * 
 * @author Marko van Dooren
 */
public interface PropertyUniverse<E> {
  
  /**
   * Return the object representing the association between this language and the
   * properties to which it is attached.
   * 
   * DO NOT MODIFY THE RESULTING OBJECT. IT IS ACCESSIBLE ONLY BECAUSE OF THE 
   * VERY DUMB ACCESS CONTROL IN JAVA.
   */
  public MultiAssociation<PropertyUniverse<E>,Property<E>> propertyLink();
  
  /**
   * Return the set of properties in this universe of properties.
   * @return
   */
 /*@
   @ behavior
   @
   @ post \result != null;
   @*/
  public Set<Property<E>> properties();

}
