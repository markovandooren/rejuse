package org.rejuse.association;

/**
 * An interface for listening to changes in associations.
 *
 * @author Marko van Dooren
 */
public interface AssociationListener<T> {

	public void notifyElementAdded(T element);
	
	public void notifyElementRemoved(T element);
	
	public void notifyElementReplaced(T oldElement, T newElement);
}
