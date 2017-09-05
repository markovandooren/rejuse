package org.aikodi.rejuse.property;

import java.util.Set;

import org.aikodi.rejuse.association.MultiAssociation;

/**
 * A universe of properties. Properties are added to a universe by creating new properties
 * and passing a universe to the constructor.
 * 
 * @author Marko van Dooren
 */
public interface PropertyUniverse<P extends Property<?,P>> {
  
  /**
   * Return the object representing the association between this language and the
   * properties to which it is attached.
   * 
   * DO NOT MODIFY THE RESULTING OBJECT. IT IS ACCESSIBLE ONLY BECAUSE OF THE 
   * VERY DUMB ACCESS CONTROL IN JAVA.
   */
  public MultiAssociation<? extends PropertyUniverse<P>, P> propertyLink();
  
  /**
   * Return the set of properties in this universe of properties.
   * @return
   */
 /*@
   @ behavior
   @
   @ post \result != null;
   @*/
  public Set<P> properties();
  
  /**
   * Flush any cache held by the properties in this property universe.
   * This method is invoked by a property whenever one of the relations between 
   * the properties is modified.
   * 
   * This normally only occurs during initialization.
   */
  public void flushCache();

}
