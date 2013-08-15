package be.kuleuven.cs.distrinet.rejuse.function;

public interface Function<I,O,E extends Exception> {

	
	public O apply(I argument) throws E;
}
