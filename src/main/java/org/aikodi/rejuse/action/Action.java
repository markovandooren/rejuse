package org.aikodi.rejuse.action;

/**
 * A functional interface for actions that can throw checked exceptions.
 * 
 * @author Marko van Dooren
 *
 * @param <E> The type of the checked exception that can be thrown.
 */
public interface Action<E extends Exception> {
	
	/**
	 * Apply this action
	 * 
	 * @throws E An exception occurred while executing the action.
	 */
	void apply() throws E;

}
