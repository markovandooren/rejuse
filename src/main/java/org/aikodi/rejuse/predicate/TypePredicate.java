package org.aikodi.rejuse.predicate;

import java.util.Collection;

import org.aikodi.rejuse.action.Nothing;

/**
 * <p>A class of predicate that check whether or not an object conforms to
 * a certain type.</p>
 *
 * <center><img src="doc-files/TypePredicate.png"/></center>
 *
 * <p>This class is typically used as follows:</p>
 * <pre><code>
 * TotalPredicate predicate = new TypePredicate(MyClass.class);
 * </code></pre>
 *
 * <p>When a <code>TypePredicate</code> is used as a constant in an interface,
 * the <code>.class</code> operator will return <code>null</code>, so in that
 * case you must pass the name of the class as a <code>String</code>:</p>
 * <pre><code>
 * TotalPredicate predicate = new TypePredicate("mypackage.MyClass");
 * </code></pre>
 *
 * @author Marko van Dooren
 * @author Jan Dockx
 */
public class TypePredicate<T> extends UniversalPredicate<T,Nothing> {

    /**
     * <p>Initialize a new TypePredicate that checks whether or not objects
     * are from a give type.</p>
     *
     * @param type The type.
     */
    /*@
      @ public behavior
      @
    @ pre type != null;
    @
    @ post getType() == type;
    @*/
    public TypePredicate(Class<T> type) {
        super(type);
    }

    /**
     * <p>Initialize a new TypePredicate based on the type with the given name.</p>
     *
     * @param name The name of the type.
     * @throws ClassNotFoundException 
     */
   /*@
     @ public behavior
     @
     @ pre name != null;
     @ pre (* <name> must be a valid classname *);
     @
     @ // The type of this TypeFilter is set to the type
     @ // with the given name.
     @ post getType() == Class.forName(name);
     @
     @ signals (LinkageError) (* something went wrong *);
     @ signals (ExceptionInInitializerError) (* something went wrong *);
     @ signals (IllegalArgumentException) (* Illegal Class Name *);
     @*/
    public TypePredicate(String name) throws ClassNotFoundException {
    	super(klazz(name));
    }

		protected static Class klazz(String name) throws ClassNotFoundException {
			return Class.forName(name);
		}

    /*@
      @ also public behavior
      @
      @ post \result == getType().isInstance(object);
      @*/
    @Override public /*@ pure @*/ boolean uncheckedEval(T object) {
        return true;
    }

    @Override
    public String toString() {
      return "instanceof "+type().getSimpleName();
    }
}