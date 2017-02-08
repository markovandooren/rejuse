package org.aikodi.rejuse.association;

/**
 * An interface for listening to changes in associations.
 *
 * @author Marko van Dooren
 */
public abstract class AssociationListener<T> {

	public abstract void notifyElementAdded(T element);
	
	public abstract void notifyElementRemoved(T element);
	
	public void notifyElementReplaced(T oldElement, T newElement) {
		notifyElementRemoved(oldElement);
		notifyElementAdded(newElement);
	}
}
