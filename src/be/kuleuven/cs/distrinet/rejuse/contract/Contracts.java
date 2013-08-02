package be.kuleuven.cs.distrinet.rejuse.contract;

public class Contracts {

	public final static void notNull(Object o) {
		notNull(o, "");
	}
	
	public final static void notNull(Object o, String message) {
		if(o == null) {
			throw new IllegalArgumentException(message);
		}
	}
}
