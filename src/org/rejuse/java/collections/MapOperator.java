package org.rejuse.java.collections;

/**
 * <p>MapOperator is the toplevel interface for map operators.</p>
 *
 * <p>To be able to prove the correctness of a subclass, a model method is provided
 * which acts like an abstract precondition for methods that operate on the object-key pair of
 * maps.</p>
 *
 * <p>Map operators are typically used as anonymous inner classes, in which case
 * some assertions are known to be true for the pairs in the map. It would be inconvenient if those
 * assertions would have to be repeated in the isValidPair method. If the isValidPair method
 * has access to the map on which the operator is used, the assertions don't have to be
 * repeated if the object of the anonymous inner class cannot escape from the method it is defined in, 
 * and if the anonymous inner class itself does not modify the maps. In this case, the
 * postcondition must simply say that the given pair must be in that map.
 * If it could be used outside the scope of the method, the assertions which the anonymous inner class
 * counts on, could be violated.</p>
 * <p>The method is typically used as follows for anonymous inner classes:</p>
 *
 *<pre><code>
 * @
 * @ also public behavior
 * @
 * @ post \result == (\exists Map.Entry entry; local_map_variable.entrySet().contains(entry);
 * @                   entry.getKey().equals(key) && entry.getValue().equals(element));
 * @
 * @ public model boolean isValidPair(Object key, Object element);
 * @
 *</code></pre>
 * <p>This way, the assertions for <code>local_map_variable</code> are implicitly "copied" to the 
 * precondition for the methods which operate on the pairs.</p>
 * <p>If the class is used in a general context, the limits on the elements have to be written explicitly:</p>
 * <pre><code>
 * @
 * @ also public behavior
 * @
 * @ post \result == (element instanceof MyType) &&
 * @ poar            (key instanceof MyType) &&
 * @ post            (element != null) &&
 * @ post            (key != null) &&
 * @ post            (* some other expression using both key and element *);
 * @
 * @ public model boolean isValidPair(Object key, Object element);
 * @
 * </code></pre>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */

public interface MapOperator {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
   * <p>This model method is supplied to make proving the correctness of a subclass
   * easier. It offers a <em>tunnel</em> for information from the caller of
	 * methods that operate on the map.
	 *
   * <p>Most often, this information concerns the type of the keys and elements,
	 * and them not being null.</p>
	 *
   * <p>The algorithm does not use this method, so we will not force subclasses
   * to implement it. A strengthened specification should be given to enable
   * proving the correctness of a specific operator.</p>
	 *
	 * <p>This is actually an abstract precondition as used in Eiffel.</p>
   */
 /*@
   @ public behavior
   @
   @ // this method should be consistent with equals; subclass can say more
   @ post (\forall Object key1;;
   @        (\forall Object key2; (key2 != null) && key2.equals(key1);
	 @          (\forall Object o1;;
	 @            (\forall Object o2; (o2 != null) && o2.equals(o1);
   @              isValidPair(key1,o1) == isValidPair(key2,o2)))));
	 @
   @ public model pure boolean isValidPair(Object key, Object element);
   @*/
}

