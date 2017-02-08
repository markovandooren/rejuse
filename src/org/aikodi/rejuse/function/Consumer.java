package org.aikodi.rejuse.function;

import org.aikodi.rejuse.action.Nothing;

/**
 * An interface for consumers that can throw exceptions.
 * Use {@link Nothing} as the type parameter if the consumer cannot
 * throw a checked exception.
 * 
 * @author Marko van Dooren
 *
 * @param <I> The type of the elements consumed by this consumer.
 * @param <E> The type of the exceptions that can be throw by this consumer.
 */
public interface Consumer<I, E extends Exception> {
	
	/**
	 * Consume the given object.
	 * 
	 * @param object The object to be consumed.
	 * @throws E Something went wrong.
	 */
	public void accept(I object) throws E;

}
