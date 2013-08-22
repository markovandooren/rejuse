package be.kuleuven.cs.distrinet.rejuse.predicate;

import java.util.Collection;
import java.util.List;

import be.kuleuven.cs.distrinet.rejuse.action.Nothing;
import be.kuleuven.cs.distrinet.rejuse.java.collections.CollectionOperator;

/*@ model import org.jutil.java.collections.Collections; @*/

/**
 * <p>A class of predicates. A predicate is a function that evaluate to <code>true</code> or
 * <code>false</code> for an object.</p>
 *
 * <p>The Predicate class is an implementation of the <em>Strategy</em>
 * pattern. You can write code that works with predicates (e.g. filters,
 * quantifiers,...) in general, and the user of that code can write the specific
 * predicate.</p>
 *
 * <p>Typically, this class will be used as an anonymous inner class. For predicates
 * that do not verify the type of the argument, use {@link AbstractPredicate}. For 
 * predicates that do verify the type of the argument, use {@link UniversalPredicate}.</p>
 * 
 * 
 * <p><code>AbstractPredicate</code> implements all methods of this interface except for
 * <a href="Predicate.html#eval(Object)"><code>eval()</code></a>.</p>
 *
 * <p>If your predicate does not throw an exception, you should use
 * <a href="TotalPredicate.html"><code>TotalPredicate</code></a>, so users
 * of your predicate won't have to write try-catch constructions when no
 * exceptions can be thrown.</p>
 *
 * <p>In case your predicate doesn't wrap another one, use <a href="PrimitivePredicate.html"><code>PrimitivePredicate</code></a>. If it also doesn't throw
 * exceptions, extend <a href="PrimitiveTotalPredicate.html"><code>PrimitiveTotalPredicate</code></a>.</p>
 */
public interface Predicate<T, E extends Exception> extends CollectionOperator {

	/**
	 * Evaluate this Predicate for the given object.
	 *
	 * @param object The object for which this Predicate must be evaluated. In general,
	 *               because collections can contain null, this might be null.
	 */
	/*@
      @ public behavior
      @
      @ post \result == true | \result == false;
    @
    @ signals (Exception) ! isValidElement(object);
      @*/
	public /*@ pure @*/ abstract boolean eval(T object) throws E;

	/**
	 * Check wether or not the given collection contains an object for which
	 * this predicate evaluates to <code>true</code>.
	 *
	 * @param collection The collection which has to be searched for an object
	 *                   for which this Predicate evaluates to <code>true</code>.
	 */
	/*@
    @ public behavior
    @
    @ post collection != null ==> \result == (\exists Object o; Collections.containsExplicitly(collection, o); eval(o) == true);
    @ post collection == null ==> \result == false;
    @
    @ signals (ConcurrentModificationException)
    @         (* The collection was modified while accumulating *);
      @ signals (Exception) (collection != null) &&
    @                     (\exists Object o;
      @                        (collection != null) &&
      @                        Collections.containsExplicitly(collection, o);
      @                          ! isValidElement(o));
    @*/
	public /*@ pure @*/ boolean exists(Collection<T> collection) throws E;

	/**
	 * Check wether or not this Predicate evaluates to <code>true</code>
	 * for all object in the given collection.
	 *
	 * @param collection The collection of which one wants to know if all object
	 *                   evaluate to <code>true</code> for this Predicate.
	 */
	/*@
    @ public behavior
    @
    @ post collection != null ==> \result == (\forall Object o; Collections.containsExplicitly(collection, o); eval(o) == true);
    @ post collection == null ==> \result == true;
    @
    @ signals (ConcurrentModificationException)
    @         (* The collection was modified while accumulating *);
      @ signals (Exception) (collection != null) &&
    @                     (\exists Object o;
      @                        (collection != null) &&
      @                        Collections.containsExplicitly(collection, o);
      @                          ! isValidElement(o));
    @*/
	public /*@ pure @*/ boolean forAll(Collection<T> collection) throws E;

	/**
	 * Count the number of object in the given collection for which this
	 * Predicate evaluates to <code>true</code>.
	 *
	 * @param collection The collection for which the number of object evaluating to
	 *                   <code>true</code> for this Predicate must be counted.
	 */
	/*@
    @ public behavior
    @
    @ post collection != null ==> \result == (\num_of Object o;
    @                                          Collections.containsExplicitly(collection,o);
    @                                          eval(o) == true);
    @ post collection == null ==> \result == 0;
    @
    @ signals (ConcurrentModificationException)
    @         (* The collection was modified while accumulating *);
      @ signals (Exception) (collection != null) &&
    @                     (\exists Object o;
      @                        (collection != null) &&
      @                        Collections.containsExplicitly(collection, o);
      @                          ! isValidElement(o));
    @*/
	public /*@ pure @*/ int count(Collection<T> collection) throws E;

	/**
	 * <p>Remove all objects for which this Predicate evaluates to <code>false</code>
	 * from the given collection.</p>
	 *
	 * @param collection The collection to be filtered.
	 */
 /*@
   @ public behavior
   @
   @ // All object that evaluate to false have been removed.
   @ post collection != null ==> (\forall Object o; Collections.containsExplicitly(collection, o); eval(o) == true);
   @ // No new objects have been put in the collection, and object that evaluate to true
   @ // remain untouched.
   @ post collection != null ==> (\forall Object o; \old(Collections.containsExplicitly(collection, o));
   @                               (eval(o) == true) ==>
   @                               (
   @                                Collections.nbExplicitOccurrences(o, collection) ==
   @                                \old(Collections.nbExplicitOccurrences(o, collection))
   @                               )
   @                             );
   @
   @ signals (ConcurrentModificationException)
   @         (* The collection was modified while accumulating *);
   @ // If a Exception occurs, nothing will happen.
   @ signals (Exception) (collection != null) &&
   @                     (\exists Object o;
   @                        (collection != null) &&
   @                        Collections.containsExplicitly(collection, o);
   @                          ! isValidElement(o));
   @*/
	public <X extends T> void filter(Collection<X> collection) throws E;


	/**
	 * Return the subpredicates of this Predicate.
	 */
	/*@
      @ public behavior
      @
      @ post \result != null;
      @ post ! \result.contains(null);
    @ // There may be no loops
      @ post ! \result.contains(this);
      @*/
	//    public /*@ pure @*/ List<Predicate<T>> getSubPredicates();

	/**
	 * Check whether or not this Predicate equals another object
	 *
	 * @param other The object to compare this Predicate with.
	 */
	/*@
      @ also public behavior
      @
      @ post \result == (other instanceof Predicate) &&
    @                 (\forall Object o; ;
      @                   ((Predicate)other).isValidElement(o) == isValidElement(o) &&
      @                   ((Predicate)other).isValidElement(o) ==> ((Predicate)other).eval(o) == eval(o));
      @*/
	public /*@ pure @*/ boolean equals(Object other);
	
	public Predicate<T,E> and(final Predicate<? super T, ? extends E> other);
	
	public Predicate<T,E> or(final Predicate<? super T, ? extends E> other);
	
	public Predicate<T,E> negation();
	
	public Predicate<T,E> implies(final Predicate<? super T, ? extends E> other);
	
	public Predicate<T,E> xor(final Predicate<? super T, ? extends E> other);
	
	public Predicate<T,Nothing> guard(final boolean value);

	public <X extends T>  List<X> filteredList(Collection<X> collection) throws E;
	
	public <X extends T> UniversalPredicate<X,E> makeUniversal(Class<X> type);
}
