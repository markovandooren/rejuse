package org.rejuse.association;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IAssociation<FROM, TO> {

	/**
	 * Check whether or not the given association is connected
	 * to this one.
	 *
	 * @param association
	 *        The association which is possibly on the other end
	 *        of the binding.
	 */
	/*@
	  @ public behavior
	  @
	  @ post \result == getOtherAssociations().contains(association);
	  @*/
	public/*@ pure @*/boolean contains(Association<? extends TO, ? super FROM> association);

	/*@
	 @ also public behavior
	 @
	 @ post \result == (other == this);
	 @*/
	public/*@ pure @*/boolean equals(Object other);

	/**
	 * Check whether or not the current state corresponds to connecting
	 * to the given association when being connected to the associations
	 * in the given list.
	 *
	 * @param oldConnections
	 *        The List of associations this association was connected to before.
	 * @param registered
	 *        The association to which this association has been connected.
	 */
	/*@
	  @ public behavior
	  @
	  @ pre oldConnections != null;
	  @ pre ! oldConnections.contains(null);
	  @
	  @ post ! contains(registered) ==> \result == false;
	  @ post ! (\forall Association r; r != registered;
	  @          oldConnections.contains(r) == contains(r)) ==> \result == false;
	  @*/
	public/*@ pure @*/boolean registered(List<Association<? extends TO, ? super FROM>> oldConnections,
			Association<? extends TO, ? super FROM> registered);

	/**
	 * Check whether or not the current state corresponds to disconnecting
	 * from the given association when being connected to the assocations
	 * in the given list.
	 *
	 * @param oldConnections
	 *        The List of associations this association was connected to before.
	 * @param registered
	 *        The association to which this association has been connected.
	 */
	/*@
	  @ public behavior
	  @
	  @ pre oldConnections != null;
	  @ pre ! oldConnections.contains(null);
	  @
	  @ post contains(unregistered) ==> \result == false;
	  @ post ! oldConnections.contains(unregistered) ==> \result == false;
	  @ post ! (\forall Association r; r != unregistered;
	  @          oldConnections.contains(r) == contains(r)) ==> \result == false;
	  @*/
	public/*@ pure @*/boolean unregistered(List<Association<? extends TO, ? super FROM>> oldConnections,
			Association<? extends TO, ? super FROM> unregistered);

	/**
	 * Return the objects on the other side of the binding.
	 */
	/*@
	  @ public behavior
	  @
	  @ post \result != null;
	  @ post \result.size() == getOtherAssociations().size();
	  @ post (\forall Object o; \result.contains(o);
	  @        (\exists Association r; getOtherAssociations().contains(r);
	  @           r.getObject() == o));
	  @*/
	//public /*@ pure @*/ abstract List<TO> getOtherEnds();
	public/*@ pure @*/List<TO> getOtherEnds();

	public void addOtherEndsTo(Collection<? super TO> collection);

	/**
	 * Return the association on the other side of the binding.
	 */
	/*@
	  @ public behavior
	  @
	  @ post \result != null;
	  @ post ! \result.contains(null);
	  @*/
	public/*@ pure @*/List<Association<? extends TO, ? super FROM>> getOtherAssociations();

	/**
	 * Return the object on represented by this association end.
	 */
	/*@
	  @ public behavior
	  @
	  @ post \result != null;
	  @*/
	public/*@ pure @*/FROM getObject();

	/**
	 * Replace the connection with the first association by a connection with the second association.
	 */
	/*@
	  @ public behavior
	  @
	  @ pre element != null;
	  @ pre newElement != null;
	  @
	  @ post replaced(\old(getOtherRelations()), oldAssociation, newAssociation);
	  @ post oldAssociation.unregistered(\old(other.getOtherAssociations()), this);
	  @ post newAssociation.registered(\old(oldAssociation.getOtherAssociations()),this);
	  @*/
	public void replace(Association<? extends TO, ? super FROM> element, Association<? extends TO, ? super FROM> newElement);

	/**
	 * Lock this end of the association.
	 */
	/*@
	  @ public behavior
	  @
	  @ post isLocked();
	  @*/
	public void lock();

	public void unlock();

	/**
	 * Check if this association end is locked.
	 */
	public boolean isLocked();

	public int size();

	/**
	 * Register the given association listener as an event listener to
	 * this association end.
	 * @param listener
	 *        The listener to be registered.
	 */
	/*@
	  @ public behavior
	  @
	  @ pre listener != null;
	  @
	  @ post listeners().contains(listener);
	  @*/
	public void addListener(AssociationListener<? super TO> listener);

	/**
	 * Remove the given association listener as an event listener from
	 * this association end.
	 * 
	 * @param listener
	 *        The listener to be removed.
	 */
	/*@
	  @ public behavior
	  @
	  @ pre listener != null;
	  @
	  @ post ! listeners().contains(listener);
	  @*/
	public void removeListener(AssociationListener<? super TO> listener);

	public Set<AssociationListener<? super TO>> listeners();

	/**
	 * Remove all connections. An exception is thrown if either this association, or an association to which it is connected, is locked.
	 */
	/*@
	  @ post size() == 0; 
	  @*/
	public void clear();

}