package org.aikodi.rejuse.function;

public interface Producer<T, E extends Exception> {

	T produce() throws E;
}
