package be.kuleuven.cs.distrinet.rejuse.action;

/**
 * A class of exceptions that cannot be thrown.
 * 
 * The purpose of this class is to serve as a generic parameter
 * for {@link Action} classes. Note that even when {@link Nothing}
 * is used as a parameter, any unchecked throwable can be thrown
 * by the action. However, by using Nothing, you indicate that
 * no unchecked exception will be thrown either. You cannot
 * prevent errors as they can be thrown at any time by the
 * virtual machine.
 * 
 * @author Marko van Dooren
 */
public final class Nothing extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 278570927015695612L;

	/**
	 * Nobody can create nothing!
	 */
	private Nothing(){}
}
