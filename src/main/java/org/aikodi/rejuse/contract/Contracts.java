package org.aikodi.rejuse.contract;

public class Contracts {

	public final static void notNull(Object o) {
		notNull(o, "");
	}
	
	public final static void check(boolean bool, String message) {
		if(! bool) {
			throw new IllegalArgumentException(message);
		}
	}
	
	public final static void notNull(Object o, String message) {
		check(o != null, message);
	}
}
