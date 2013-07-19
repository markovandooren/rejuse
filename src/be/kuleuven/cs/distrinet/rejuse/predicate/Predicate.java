package be.kuleuven.cs.distrinet.rejuse.predicate;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.List;

import be.kuleuven.cs.distrinet.rejuse.java.collections.CollectionOperator;

/*@ model import org.jutil.java.collections.Collections; @*/

/**
 * <p>A class of predicates that evaluate to <code>true</code> or
 * <code>false</code> for an object.</p>
 *
 * <center><img src="doc-files/Predicate.png"/></center>
 *
 * <p>The Predicate class is actually an implementation of the <em>Strategy</em>
 * pattern. You can write code that works with predicates (e.g. filters,
 * quantifiers,...) in general, and the user of that code can write the specific
 * predicate.</p>
 *
 * <p>This class evolved from the <a href="ForAll.html"><code>ForAll</code></a>,
 * <a href="Exists.html"><code>Exists</code></a>, <a href="Counter.html"><code>Counter</code></a> and
 * <a href="Filter.html"><code>Filter</code></a> classes (which will be deprecated
 * soon). This class has the advantage that the predicate itself is reusable.
 * Using the classes mentioned above, you'd have to implement a predicate multiple
 * times if you wanted to use it for more than one type of operation. With the
 * <code>Predicate</code> class, you write the predicate once, and the client
 * can do with it whatever he/she wants.
 * </p>
 *
 * <p>The <a href="ForAll.html"><code>ForAll</code></a>,
 * <a href="Exists.html"><code>Exists</code></a>,
 * <a href="Counter.html"><code>Counter</code></a> and
 * <a href="Filter.html"><code>Filter</code></a> classes are replaced by
 * instance methods of this class so they can be removed in the future.
 * The recommended use for operations that do not work on collections is to
 * use an internal iterator (like the forall,... method of this class) in the
 * objects that contain the information. This was not possible for collections
 * since we don't have access to the source code, and so these methods are
 * put in this class.</p>
 *
 * <p>Typically, this class will be used as an anonymous inner class as follows:</p>
 * <pre><code>
 * Predicate myPredicate = new AbstractPredicate() {
 *            /oo
 *             o also public behavior
 *             o
 *             o post postcondition;
 *             o/
 *            public boolean eval(Object o) throws MyException {
 *              //calculate boolean value
 *              //using the given object
 *            }
 *
 *            public int nbSubPredicates() {
 *              // ...
 *            }
 *
 *            public List getSubPredicates() {
 *              // ...
 *            }
 *          };
 * </code></pre>
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
public interface Predicate<T> extends CollectionOperator {

    /* The revision of this class */
    public static final String CVS_REVISION = "$Revision$";

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
    @public <X extends T>  List<X> filteredList(Collection<X> collection) throws ConcurrentModificationException, Exception {
    @ signals (Exception) ! isValidElement(object);
      @*/
    public /*@ pure @*/ abstract boolean eval(T object) throws Exception;

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
    public /*@ pure @*/ boolean exists(Collection<T> collection) throws ConcurrentModificationException, Exception;

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
    public /*@ pure @*/ boolean forAll(Collection<T> collection) throws ConcurrentModificationException, Exception;

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
    public /*@ pure @*/ int count(Collection<T> collection) throws ConcurrentModificationException, Exception;

    /**
     * <p>Remove all objects for which this Predicate evaluates to <code>false</code>
     * from the given collection.</p>
     *
     * <p>If you want to remove all object for which this Predicate evaluates
     * to <code>true</code>, wrap a <code>Not</code> predicate around this predicate, and
     * perform the filter using that predicate. For example:</p>
     * <pre><code>
     * new Not(myPredicate).filter(collection);
     * </code></pre>
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
    public <X extends T> void filter(Collection<X> collection) throws ConcurrentModificationException, Exception;


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

    /**
     * Return the size of this Predicate.
     */
    /*@
      @ public behavior
      @
      @ post \result == getSubPredicates().size();
      @*/
//    public /*@ pure @*/ int nbSubPredicates();
    
    public <X extends T>  List<X> filteredList(Collection<X> collection) throws Exception;
}
