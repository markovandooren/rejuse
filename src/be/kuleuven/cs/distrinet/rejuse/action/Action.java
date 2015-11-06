package be.kuleuven.cs.distrinet.rejuse.action;

import java.util.Collection;

/**
 * An class for actions.
 * 
 * @author Marko van Dooren
 *
 * @param <T> The type of the element on which the action operates
 * @param <E> The type of exceptions that can be thrown by the action
 */
public abstract class Action<T, E extends Exception> {

	/**
	 * Create a new action that operates on items of the 
	 * given type.
	 * 
	 * @param type A {@link Class} object that represents the type
	 *             of objects on which the new action can operate.
	 */
 /*@
   @ public behavior
   @
   @ pre type != null;
   @
   @ post type() == type;
   @*/
	public Action(Class<T> type) {
		if(type == null) {
			throw new IllegalArgumentException("The type of objects to which an action can be applied cannot be null.");
		}
		_type = type;
	}
	
	/**
	 * Perform the action.
	 * 
	 * @param object The object on which the action must be performed.
	 * @throws E
	 */
	protected abstract void doPerform(T object) throws E;
	
	/**
	 * Perform the action on the given object. First,
	 * the type of the object is checked. If it
	 * is of type T, the action is applied. Otherwise,
	 * nothing is done.
	 * 
	 * @param object The object to which the action should
	 *               be applied.
	 * @throws E
	 */
	public void perform(Object object) throws E {
		if(type().isInstance(object)) {
			doPerform((T) object);
		}
	}
		
	
	/**
	 * Apply the action to the given collection. No
	 * type check is performed. If you don't know
	 * for sure that the collection contains only
	 * objects of type T, use {@link #applySafely(Collection)}
	 * instead.
	 * 
	 * @param collection The collection containing the objects to 
	 * 								   which this action must be applied.
	 * @throws E
	 */
	public void apply(Collection<T> collection) throws E {
		for(T t: collection) {
			doPerform(t);
		}
	}
	
	public void applySafely(Collection<?> collection) throws E {
		for(Object o: collection) {
			perform(o);
		}
	}
	
	/**
	 * Return a class object that represents the type of
	 * objects on which this action can operate.
	 */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
	public Class<T> type() {
		return _type;
	}
	
	private Class<T> _type;
	
	public boolean mustProceed() {
		return true;
	}
}
