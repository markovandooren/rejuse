package org.aikodi.rejuse.function;


/**
 * A functional interface for consumers with two parameters
 * that can throw checked exceptions.
 * 
 * @author Marko van Dooren
 *
 * @param <I1> The type of the first parameter.
 * @param <I2> The type of the second parameter.
 * @param <E>The type of the checked exception that can be thrown.
 */
@FunctionalInterface
public interface BiConsumer<I1, I2, E extends Exception> {

	/**
	 * Consume the parameters.
	 *  
	 * @param first The first parameter.
	 * @param second The second parameter.
	 * @throws E An exception occurred.
	 */
	public void accept(I1 first, I2 second) throws E;

}
