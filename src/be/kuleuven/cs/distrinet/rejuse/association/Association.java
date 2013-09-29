package be.kuleuven.cs.distrinet.rejuse.association;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>A class of objects that can be used to set up bi-directional association between objects.</p>
 *
 * <center><img src="doc-files/Relation.png"/></center>
 *
 * <p>This class provides the general interface that is needed in order to create different types 
 * of bindings that can be created by taking two arbitrary multiplicities.</p>
 *
 * <p>Note: The collection of event listeners is initialized lazily, are removed when no event listeners
 * are registered anymore.</p>
 * 
 * @author  Marko van Dooren
 */
public abstract class Association<FROM,TO> implements IAssociation<FROM, TO> {
  
  /**
   * Initialize a new association for the given object.
   * The new association will be unconnected.
   *
   * @param object
   *        The object at this side of the binding.
   */
 /*@
   @ public behavior
   @
   @ pre object != null;
   @
   @ post getObject() == object;
   @*/
  public Association(FROM object) {
    _object = object;
  }

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
//  @Override
	public /*@ pure @*/ boolean contains(Association<? extends TO,? super FROM> association) {
    Collection<Association<? extends TO, ? super FROM>> internalAssociations = internalAssociations();
		return internalAssociations == null ? false : internalAssociations.contains(association);
  }

 /*@
   @ also public behavior
   @
   @ post \result == (other == this);
   @*/
  @Override
	public /*@ pure @*/ boolean equals(Object other) {
    return other == this;
  }

  /**
   * Add the given association as a participant in this
   * binding.
   * 
   * If the new object is not null and replaces another one, {@link #fireElementReplaced(Object, Object)}
   * is called. If the new object is null and replaces another one, {@link #fireElementRemoved(Object)}
   * is called. If the new object is not null and does not replace another one, {@link #fireElementAdded(Object)}
   * is called.
   */
 /*@
   @ protected behavior
   @
   @ pre isValidElement(other);
   @
   @ post registered(\old(getOtherAssociations()), other);
   @*/
  protected abstract void register(Association<? extends TO,? super FROM> other);
  
  
  protected void checkLock() {
  	checkLock(this);
  }
  
  /**
   * Throw an exception if this association end is locked.
   */
	protected void checkLock(Association<?,?> association) {
    if(association != null && association.isLocked()) {
  		throw new LockException("Trying to modify locked reference. Locked object: "+association.getObject().getClass().getName());
  	}
	}

  /**
   * Remove the given association end as a participant in this
   * association.
   */
 /*@
   @ protected behavior
   @
   @ pre contains(other);
   @
   @ post unregistered(\old(getOtherAssociations()), other);
   @*/
  protected abstract void unregister(Association<? extends TO,? super FROM> other);

  /**
   * Check whether or not the given association may be connected to
   * this association.
   */
 /*@
   @ protected behavior
   @
   @ post \result == true | \result == false;
   @*/
  protected /*@ pure @*/ abstract boolean isValidElement(Association<? extends TO,? super FROM> association);

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
//  @Override
	public /*@ pure @*/ abstract boolean registered(List<Association<? extends TO,? super FROM>> oldConnections, Association<? extends TO,? super FROM> registered);

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
//  @Override
	public /*@ pure @*/ abstract boolean unregistered(List<Association<? extends TO,? super FROM>> oldConnections, Association<? extends TO,? super FROM> unregistered);


	
//  @Override
	public abstract void addOtherEndsTo(Collection<? super TO> collection);

//	public abstract List<TO> view();
	
  /**
   * Return the association on the other side of the binding.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @ post ! \result.contains(null);
   @*/
//  @Override
	public /*@ pure @*/ abstract List<Association<? extends TO,? super FROM>> getOtherAssociations();
	
	protected /*@ pure @*/ abstract Collection<Association<? extends TO,? super FROM>> internalAssociations();

  /**
   * Return the object on represented by this association end.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
//  @Override
	public /*@ pure @*/ FROM getObject() {
    return _object; 
  }
  
  /**
   * The object on this side of the binding.
   */
  private FROM _object;

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
//	@Override
	public abstract void replace(Association<? extends TO,? super FROM> element, Association<? extends TO,? super FROM> newElement);
	
  /*
   * True if this association end is locked.
   * False if this association end is not locked.
   */
  private boolean _locked = false;

  /**
   * Lock this end of the association.
   */
 /*@
   @ public behavior
   @
   @ post isLocked();
   @*/
//  @Override
	public void lock() {
  	_locked=true;
  }

//  @Override
	public void unlock() {
  	_locked=false;
  }

  /**
   * Check if this association end is locked.
   */
//  @Override
	public boolean isLocked() {
  	return _locked;
  }
  
//  @Override
	public abstract int size();
  
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
//  @Override
	public void addListener(AssociationListener<? super TO> listener) {
  	if(listener == null) {
  		throw new IllegalArgumentException("An association listener cannot be null.");
  	}
  	if(_listeners == null) {
  		_listeners = new HashSet<AssociationListener<? super TO>>();
  	}
  	_listeners.add(listener);
  }
  
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
//  @Override
	public void removeListener(AssociationListener<? super TO> listener) {
  	if(_listeners != null) {
  		_listeners.remove(listener);
  		// clean up if there are no listeners anymore.
  		if(_listeners.isEmpty()) {
  			_listeners = null;
  		}
  	}
  }
  
//  @Override
	public Set<AssociationListener<? super TO>> listeners() {
  	return new HashSet<AssociationListener<? super TO>>(_listeners);
  }
  
  private Set<AssociationListener<? super TO>> _listeners;
  
  public abstract void flushCache();
  
  /**
   * If events are enabled, send "element added" events to all listeners.
   */
  protected void fireElementAdded(TO addedElement) {
  	flushCache();
  	if(! _eventsBlocked && _listeners != null) {
  		for(AssociationListener<? super TO> listener: _listeners) {
  			listener.notifyElementAdded(addedElement);
  		}
  	}
  }

  /**
   * If events are enabled, send "element removed" events to all listeners.
   */
  protected void fireElementRemoved(TO removedElement) {
  	flushCache();
  	if(! _eventsBlocked && _listeners != null) {
  		for(AssociationListener<? super TO> listener: _listeners) {
  			listener.notifyElementRemoved(removedElement);
  		}
  	}
  }

  /**
   * If events are enabled, send "element replaced" events to all listeners.
   */
  protected void fireElementReplaced(TO oldElement, TO newElement) {
  	flushCache();
  	if(! _eventsBlocked && _listeners != null) {
  		for(AssociationListener<? super TO> listener: _listeners) {
  			listener.notifyElementReplaced(oldElement, newElement);
  		}
  	}
  }
  
  private boolean _eventsBlocked;
  
  /**
   * Disable sending of events. This can be used to prevent register and unregister methods
   * from sending events when a replace is done.
   * @return
   */
 /*@
   @ behavior
   @
   @ post eventsBlocked() == true;
   @*/
  protected void disableEvents() {
  	_eventsBlocked = true;
  }
  
  /**
   * Enable sending of events.
   */
 /*@
   @ behavior
   @
   @ post eventsBlocked() == false;
   @*/
  protected void enableEvents() {
  	_eventsBlocked = false;
  }
  
  /**
   * Check whether or not events are blocked.
   * @return
   */
  protected boolean eventsBlocked() {
  	return _eventsBlocked;
  }

  /**
   * Remove all connections. An exception is thrown if either this association, or an association to which it is connected, is locked.
   */
 /*@
   @ post size() == 0; 
   @*/
//  @Override
	public abstract void clear();

	public void enableCache() {
		_isCaching = true;
	}
	
	public void disableCache() {
		_isCaching = false;
	}
	
	public boolean isCaching() {
		return _isCaching;
	}
	
	private boolean _isCaching;
	
	public boolean containsObject(TO to) {
		if(to != null) {
			for(TO connected: getOtherEnds()) {
				if(connected.equals(to)) {
					return true;
				}
			}
		}
		return false;
	}
}
