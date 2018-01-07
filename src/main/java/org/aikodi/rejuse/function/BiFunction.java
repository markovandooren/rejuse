package org.aikodi.rejuse.function;

/**
 * A functional interface for functions with two parameters
 * that can throw checked exceptions.
 * 
 * @author Marko van Dooren
 *
 * @param <I1> The type of the first parameter.
 * @param <I2> The type of the second parameter.
 * @param <O>The output type.
 * @param <E>The type of the checked exception that can be thrown.
 */
@FunctionalInterface
public interface BiFunction<I1,I2,O,E extends Exception> {

	public O apply(I1 first, I2 second) throws E;

}
