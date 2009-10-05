package org.rejuse.property;

import java.util.Set;

import org.rejuse.association.MultiAssociation;
import org.rejuse.association.SingleAssociation;
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
public interface Property<E> {

	/**
	 * Check if this property currently applies to the given element
	 * by verifying its state.
	 * 
	 * @param element
	 */
	public Ternary appliesTo(E element);

	/**
	 * Return the name of this property.
	 */
	/*@
	  @ behavior
	  @
	  @ post \result != null;
	  @*/
	public String name();

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
	public void setName(String name);

	/**
	 * Return the universe to which this property is attached.
	 * @return
	 */
	/*@
	  @ behavior
	  @
	  @ post \result != null;
	  @*/
	public PropertyUniverse<E,? extends Property<E>> universe();

	/**
	 * Return the inverse of this property.
	 */
	/*@
	  @ behavior
	  @
	  @ post \result != null;
	  @ post \result.contradicts(this);
	  @*/
	public Property<E> inverse();

	public PropertyMutex<E> mutex();

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
	public boolean implies(Property<?> other);

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
	public Set<Property<E>> impliedProperties();

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
	public Set<Property<E>> directlyImpliedProperties();

	/*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @ post \result.contains(this);
	 @*/
	public Set<Property<E>> implicitlyImpliedProperties();

	/*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @ post (\forall Property<E> p; siblings().contains(p); 
	 @         \result.contains(p.inverse()));
	 @ post \result.size() == siblings().size();
	 @*/
	public Set<Property<E>> inverseSiblings();

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
	public void addImplication(Property<E> p);

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
	public boolean impliedBy(Property<E> other);

	/**
	 * Return the set of all properties that imply this property both directly and indirectly.
	 */
	/*@
	  @ public behavior
	  @
	  @ post \result != null;
	  @ post \result.containsAll(directlyImpliedProperties());
	  @ post (\forall Property p; result.contains(p); 
	  @               \result.containsAll(p.directlyImpliedProperties()));
	  @*/
	public Set<Property<E>> impliedByProperties();

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
	public Set<Property<E>> directlyImpliedByProperties();

	/*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @ post \result.contains(this);
	 @*/
	public Set<Property<E>> implicitlyImpliedByProperties();

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
	public boolean contradicts(Property<E> other);

	/**
	 * Return the properties contradicting this property.
	 * 
	 * This set contains of all properties Z that imply a property Y that
	 * contradicts a property X implied by this property.
	 * 
	 * this ----(implied)---> X ---(contradicts)---> Y ---(impliedBy)---> Z
	 * @return
	 */
	public Set<Property<E>> contradictedProperties();

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
	public Set<Property<E>> directlyContradictedProperties();

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
	public Set<Property<E>> implicitlyContradictedProperties();

	/*@
	 @ public behavior
	 @
	 @ post \result == family().membersWithout(this);
	 @*/
	public Set<Property<E>> siblings();

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
	public void addContradiction(Property<E> p);

	public MultiAssociation<Property<E>,Property<E>> contradictedLink(); 
	
	public MultiAssociation<Property<E>,Property<E>> impliedLink(); 
	
	public MultiAssociation<Property<E>,Property<E>> impliedByLink();

  public SingleAssociation<Property<E>, Property<E>> inverseLink();

}