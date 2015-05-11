package be.kuleuven.cs.distrinet.rejuse.function;

public interface BiFunction<I1,I2,O,E extends Exception> {

	public O apply(I1 first, I2 second) throws E;

}
