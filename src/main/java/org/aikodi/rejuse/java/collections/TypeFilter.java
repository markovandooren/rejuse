package org.aikodi.rejuse.java.collections;

/**
 * <p>A class of filters that use the type of an object as criterion.</p>
 *
 * <center>
 *  <img src="doc-files/TypeFilter.png"/>
 * </center>
 *
 * <p>This filter uses the type of elements of the filtered collection as criterium.
 * The type to use in the criterium is given as an argument during construction.
 * Subclasses exist how to deal with the type.</p>
 *
 * <p>The type can be passed to the <code>TypeFilter</code> with a <code>Class</code> object, or with a
 * <code>String</code> that equals its name. The latter is necessary when defining <code>Filter</code>
 * constants in an interface.</p>
 *
 * <p>For using a <code>TypeFilter</code>, no class has to be created. Just create a new <code>TypeFilter</code>
 * with the requested type as an argument.</p>
 *
 * <pre><code>
 * new TypeFilter(myPackage.MyClass.class).retain(collection);
 * </code></pre>
 * <pre><code>
 * new TypeFilter("myPackage.MyClass").discard(collection);
 * </code></pre>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Jan Dockx
 * @author  Marko van Dooren
 * @release $Name$
 */
public class TypeFilter<T> implements Filter<Object> {
  
	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /**
   * <p>A new Type Filter that filters objects based on type <type> from the
   * filtered collections.</p>
   *
   * @param type
   *        The type to be filtered.
   */
 /*@
	 @ public behavior
	 @
   @ // The given type may not be null.
   @ pre type != null;
   @
   @ // The type of this TypeFilter is set to the given type.
   @ post getType() == type;
   @*/
  public TypeFilter(Class<T> type) {
    _type = type;
  }
  
  /**
   * Return the type of this TypeFilter.
   */
 /*@
   @ public behavior
   @
   @ post \result != null;
   @*/
  public final /*@ pure @*/ Class<T> getType() {
    return _type;
  }
  
	/**
	 * The type to be used in the filter.
	 */
 /*@
   @ private invariant _type != null;
   @*/
  private Class<T> _type;

	/**
	 * See superclass
	 */
 /*@
	 @ also public behavior
	 @
   @ // <element> is of the desired type.
   @ post \result == getType().isInstance(element);
   @*/
  public final /*@ pure @*/ boolean criterion(Object element) {
    return getType().isInstance(element);
  }
}

