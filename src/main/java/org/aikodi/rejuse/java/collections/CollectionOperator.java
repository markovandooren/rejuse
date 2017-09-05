package org.aikodi.rejuse.java.collections;

/**
 * <p>CollectionOperator is the toplevel interface for collection operators.</p>
 *
 * <p>To be able to prove the correctness of a subclass, a model method is provided
 * which acts like an abstract precondition for methods that operate on the elements of
 * collections.</p>
 *
 * <p>Collection operators are typically used as anonymous inner classes, in which case
 * some assertions are known to be true for the collection. It would be inconvenient if those
 * assertions would have to be repeated in the isValidElement method. If the isValidElement method
 * has access to the collection on which the operator is used, the assertions don't have to be
 * repeated if the object of the anonymous inner class cannot escape from the method it is defined in, 
 * and if the anonymous inner class itself does not modify the collection. In this case, the
 * postcondition must simply say that the given parameter must be in that collection.
 * If it could be used outside the scope of the method, the assertions which the anonymous inner class
 * counts on, could be violated.</p>
 * <p>The method is typically used as follows for anonymous inner classes:</p>
 *
 *<pre><code>
 * /oo
 *   o also public behavior
 *   o
 *   o post \result == local_collection_variable.contains(element);
 *   o
 *   o public model boolean isValidElement(Object element);
 *   oo/
 *</code></pre>
 * <p>This way, the assertions for <code>local_collection_variable</code> are implicitly "copied" to the 
 * precondition for the methods which operate on the elements.</p>
 * <p>If the class is used in a general context, the limits on the elements have to be written explicitly:</p>
 * <pre><code>
 * /oo
 *   o also public behavior
 *   o
 *   o post \result == (element instanceof MyType) &&
 *   o                 (element != null) &&
 *   o                   (* some other condition *);
 *   o
 *   o public model boolean isValidElement(Object element);
 *   oo/
 * </code></pre>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public interface CollectionOperator {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
   * <p>This model method is supplied to make proving the correctness of a subclass
   * easier. It offers a <em>tunnel</em> for information the caller of
	 * methods that operate on the collection.
	 *
   * <p>Most often, this information concerns the type of the elements and them
   * not being null.</p>
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
   @ post (\forall Object o1;;
   @        (\forall Object o2; (o2 != null) && o2.equals(o1);
   @             isValidElement(o1) == isValidElement(o2)));
	 @*/
  /*@ public model pure boolean isValidElement(Object element); @*/

}
