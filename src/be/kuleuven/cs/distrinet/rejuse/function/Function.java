package be.kuleuven.cs.distrinet.rejuse.function;

/**
 * An interface for functions that can throw checked exceptions.
 * 
 * @author Marko van Dooren
 *
 * @param <I> The type of the function parameter
 * @param <O> The return type of the function.
 * @param <E> The type of the checked exceptions that can be thrown.
 */
public interface Function<I,O,E extends Exception> {

	
	public O apply(I argument) throws E;
}
