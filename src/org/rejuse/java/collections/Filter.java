package org.rejuse.java.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.ConcurrentModificationException;

/**
 * <p>A filter for collections. Filters remove elements that do or do not comply with 
 * some criterion.</p>
 *
 * <center>
 *   <img src="doc-files/Filter.png"/>
 * </center>
 *
 * <p>Filter can remove all elements from a collection which satisfy the criterion defined
 * by the <code><a href="Filter.html#criterion(java.lang.Object)">criterion</a></code> method.
 * In relational algebra, this is selection.</p>
 *
 * <p>Note that a filter will <b>modify the collection it operates on !</b></p>
 *
 * <p>To retain all object meeting some criterion, you can for example use the following
 * code:</p>
 *
 * <pre><code>
 * new Filter() {
 *   /oo
 *    o also public behavior
 *    o
 *    o post \result = (* preconditions for criterion *);
 *    o
 *    o public model boolean isValidElement(Object element);
 *    o/
 *
 *  /oo
 *    o public behavior
 *    o
 *    o post \result == (* your criterion *);
 *    oo/
 *   public boolean criterion(Object element) {
 *     //criterion code
 *   }
 * }.retain(collection); 
 * </code></pre>
 *
 * <p>To discard the elements, just invoke the <code><a 
 * href="Filter.html#discard(java.util.Collection)">discard</a></code> method
 * instead of the <code><a href="Filter.html#retain(java.util.Collection)">retain</a></code>
 * method.</p>
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Jan Dockx
 * @author  Marko van Dooren
 * @release $Name$
 */
public abstract class Filter<T> implements CollectionOperator {
  
 	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

 //MvDMvDMvD : change the names of the overloaded methods.
  
  /**
   * <p>The criterion to be applied to all elements of a collection.</p>
   *
   * @param  element
   *         The object the method gives a compliance verdict about.
   */
 /*@
	 @ public behavior
	 @
	 @ pre isValidElement(element);
   @*/
  public abstract /*@ pure @*/ boolean criterion(T element);
  
  /**
   * <p>Perform the filtering defined in <code>public boolean criterion(Object)</code>
   * on the given collection. The contents of <collection> is changed
   * to the filtered collection. The collection is also returned, so that further operations
   * can be applied to it inline.</p>
   *
   * @param  collection
   *         The collection to perform this filter on. This can be null,
   *         in which case nothing happens.
   */
 /*@
	 @ public behavior
	 @
   @ pre (\forall Object o; collection.contains(o); isValidElement(o));
	 @
   @ // All elements that do not meet the criterion are removed from the collection.
   @ post collection != null ==> (\forall Object o; (o!= null) && (! criterion(o)); ! \result.contains(o));
   @ // The given collection is changed and returned.
   @ post \result == collection;
	 @
	 @ signals (ConcurrentModificationException) (* The collection was modified while filtering *);
   @*/
  public final <C extends Collection<T>> C retain(C collection) throws ConcurrentModificationException {
    if (collection != null) {
      Iterator<T> iter = collection.iterator();
      while (iter.hasNext()) {
        if (! criterion(iter.next())) {
          iter.remove();
        }
      }
    }
    return collection;
  }

  //MvDMvDMvD : small performance penalty when not working with a static
  //            type of Collection. Copy & paste the code ?
  //            Actually a precompiler or smth could do lots of these
  //            optimizations. The code works with a standard jdk, but
  //            a precompiler could inline the code to prevent
  //              A) loading the anonymous inner class
  //              B) creating an object
  //              C) an extra method call
  //            We would get the elegance of jutil.org and the speed of
  //            standard operations.
  
//   /**
//    * @see retain(java.util.Collection).
// 	 *
//    * This method is supplied to do casting to the actual collection type of the result for you.
//    */
//   public final Set retainSet(Set collection) {
//     return (Set)retain((Collection)collection);
//   }
//   
//   /**
//    * @see retain(Collection).
//    * This method is supplied to do casting to the actual collection type of the result for you.
//    */
//   public final SortedSet retainSortedSet(SortedSet collection) {
//     return (SortedSet)retain((Collection)collection);
//   }
//   
//   /**
//    * @see retain(Collection).
//    * This method is supplied to do casting to the actual collection type of the result for you.
//    */
//   public final List retainList(List collection) {
//     return (List)retain((Collection)collection);
//   }

  /**
   * <p>Perform the filtering defined by the negation of
   * <code>public boolean criterion(Object)</code>
   * on <collection>. The contents of <collection> is changed
   * to the filtered collection. The collection is also returned, so that further operations
   * can be applied to it inline.</p>
   *
   * @param  collection
   *         The collection to perform this filter on. This can be null, in which case
   *         nothing happens.
   */
 /*@
	 @ public behavior
	 @
   @ pre (\forall Object o; collection.contains(o); isValidElement(o));
	 @
   @ // All elements that meet the criterion are removed from the collection
   @ post collection != null ==> (\forall Object o; (o!= null) && (criterion(o)); ! \result.contains(o));
   @ // <collection> is changed and returned.
   @ post \result == collection;
	 @
	 @ signals (ConcurrentModificationException) (* The collection was modified while filtering *);
   @*/
  public final <C extends Collection<T>>C discard(C collection) throws ConcurrentModificationException {
    if (collection != null) {
      Iterator<T> iter = collection.iterator();
      while (iter.hasNext()) {
        if (criterion(iter.next())) {
          iter.remove();
        }
      }
    }
    return collection;
  }
  
}