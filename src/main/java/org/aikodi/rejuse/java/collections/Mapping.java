package org.aikodi.rejuse.java.collections;


import java.util.*;


/**
 * <p>A mapping of collections.</p>
 *
 * <center>
 *   <img src="doc-files/Mapping.png"/>
 * </center>
 *
 * <p>Mappings exchange an element for another element,
 * calculated based on the original element. In relational algebra, this is projection.</p>
 *
 * <p>That mapping is defined in the <code><a href="Mapping.html#mapping(java.lang.Object)">
 * mapping(Object)</a></code> method. The mapping of the collection happens through the
 * invocation of the <code><a href="Mapping.html#applyTo(java.util.Collection)">
 * applyTo(Collection)</a></code> method, or the <code>
 * <a href="Mapping.html#applyFromTo(java.util.Collection, java.util.Collection)">
 * applyTo(Collection, Collection)</a></code> method which maps the first
 * collection into the second.</p>
 *
 * <p>The code it typically used as follows:</p>
 * <pre><code>
 * new Mapping() {
 *   /oo
 *     o also public behavior
 *     o
 *     o post (* postconditions *);
 *     o/
 *   public Object mapping(Object element) {
 *     // mapping code
 *   }
 * }.applyTo(collection);
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
public abstract class Mapping<FROM,TO> implements CollectionOperator {

  //MvDMvDMvD : make separate methods for lists and ordered sets,...
    
	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /**
   * The mapping to be applied to all elements of a collection.
   *
   * @param  element
   *         The object the mapping should replace.
   */
 /*@
   @ public behavior
	 @
   @ pre isValidElement(element);
	 @
   @ post (\forall Object o1;;
   @        (\forall Object o2; (o2 != null) && o2.equals(o1);
   @             mapping(o1).equals(mapping(o2))));
   @*/
  public abstract /*@ pure @*/ TO mapping(FROM element);
  
  /**
   * <p>Perform the mapping defined in <code>public Object mapping(Object)</code>
   * on <collection>. The contents of <collection> is changed
   * to the mapping. The collection is also returned, so that further operations
   * can be applied to it inline.</p>
   * <p>If the collection is a sorted set, make sure that the new elements are also
   * Comparable, or that the Comparator is changed accordingly.</p>
   * <p>If the collection is a List, the order of the elements is preserved. The mapped
   * element has the same position as the object it was mapped from.
   *
   * @param  collection
   *         The collection to perform this mapping on. This can be null, in which case
   *         nothing happens.
   */
 /*@
   @ public behavior
   @
   @ pre (\forall Object o; collection.contains(o); isValidElement(o));
	 @
   @ // The elements of the given set are replaced by their mapping under
   @ // <code>public Object mapping(Object)</code>.
   @ post (\forall Object o; collection.contains(o); 
   @         \result.contains(mapping(o))) &&
   @      (\forall Object o; \result.contains(o);
   @         (\exists Object o2; collection.contains(o2);
   @            mapping(o2).equals(o)));
   @ post collection.size() == \old(collection.size());   
   @ // The given collection is changed and returned
   @ post \result == collection;
	 @
	 @ signals (ConcurrentModificationException) (* The collection was modified while mapping *);
   @*/
  public final /*@ pure @*/ Collection<TO> applyTo(Collection<FROM> collection) throws ConcurrentModificationException {
    if (collection != null) {
      ArrayList<TO> acc = new ArrayList<TO>();
      Iterator<FROM> iter = collection.iterator(); // iterate over a safe set
      while (iter.hasNext()) {
        acc.add(mapping(iter.next()));
      }
      return acc;
    } else {
    	return null;
    }
  }

//   /**
//    * <p>Perform the mapping defined in <code>public Object mapping(Object)</code>
//    * on <set>. The contents of <set> is changed
//    * to the mapping. The set is also returned, so that further operations
//    * can be applied to it inline.</p>
//    * <p>If the set is a sorted set, make sure that the new elements are also
//    * Comparable, or that the Comparator is changed accordingly.</p>
//    *
//    * @param  set
//    *         The set to perform this mapping on. This can be null, in which case
//    *         nothing happens.
//    */
//  /*@
//    @ // The given set may not contain null references
//    @ pre ! set.contains(null);
//    @
//    @ // The elements of the given set are replaced by their mapping under
//    @ // <code>public Object mapping(Object)</code>.
//    @ post (\forall Object o; set.contains(o); \result.contains(mapping(o))) &&
//    @      (\forall Object o; set.contains(o); (o != mapping(o)) ==> (! \result.contains(o)));
//    @ // The given set is changed and returned
//    @ post \result == set;
//    @*/
//   public final Set applyTo(Set set) {
//     if (set != null) {
//       Set acc = new HashSet();
//       Iterator iter = set.iterator(); // iterate over a safe set
//       while (iter.hasNext()) {
//         acc.add(mapping(iter.next()));
//       }
//       set.clear();
//       set.addAll(acc);
//     }
//     return set;
//   }
  
  /**
   * <p>Perform the mapping defined in <code>public Object mapping(Object)</code>
   * on all elements in <fromCollection> and put them in <toCollection>. 
   * The contents of <fromCollection> is not changed. 
   * <toCollection> is also returned, so that further operations
   * can be applied to it inline.</p>
   * <p>If <fromCollection> is a sorted set, make sure that the new elements are also
   * Comparable, or that the Comparator is changed accordingly.</p>
   * <p>If <fromCollection> is a List, the order of the elements is preserved. The mapped
   * element has the same position in <toCollection> as the object it was mapped from
   * in <fromCollection>.
   *
   * @param  fromCollection
   *         The collection which contains to elements to be mapped into the other
   *         collection. This can be null, in which case nothing happens.
   * @param  toCollection
   *         The collection into which the elements of <fromCollection> are mapped.
   *         This can be null, in which case nothing happens.
   */
 /*@
	 @ public behavior
	 @
   @ pre (\forall Object o; fromCollection.contains(o); isValidElement(o));
   @
   @ // The elements <fromCollection> are mapped into <toCollection> according to the 
   @ // mapping under <code>public Object mapping(Object)</code>.
   @ post (\forall Object o; fromCollection.contains(o);
   @         \result.contains(mapping(o))) &&
   @      (\forall Object o; toCollection.contains(o); 
   @          (\exists Object o2; fromCollection.contains(o2);
   @             o.equals(mapping(o2))));
   @ // <toCollection> is changed and returned
   @ post \result == toCollection;
	 @
	 @ signals (ConcurrentModificationException) (* The collection was modified while mapping *);
   @*/
  public final /*@ pure @*/ Collection<TO> applyFromTo(Collection<FROM> fromCollection, Collection<TO> toCollection) throws ConcurrentModificationException {
    if ((fromCollection != null) && (toCollection != null)) {
      toCollection.clear();
      Iterator<FROM> iter = fromCollection.iterator();
      while (iter.hasNext()) {
        toCollection.add(mapping(iter.next()));
      }
    }
    return toCollection;
  }
//   /**
//    * <p>Perform the mapping defined in <code>public Object mapping(Object)</code>
//    * on all elements in <fromSet> and put them in <toSet>. The contents of <fromSet>
//    * is not changed. <toSet> is also returned, so that further operations
//    * can be applied to it inline.</p>
//    * <p>If the set is a sorted set, make sure that the new elements are also
//    * Comparable, or that the Comparator is changed accordingly.</p>
//    *
//    * @param  fromSet
//    *         The set which contains to elements to be mapped into the other set
//    *         This can be null, in which case nothing happens.
//    * @param  toSet
//    *         The set into which the elements of <fromSet> are mapped.
//    *         This can be null, in which case nothing happens.
//    */
//  /*@
//    @ // <fromSet> may not contain null references
//    @ pre ! fromSet.contains(null);
//    @
//    @ // The elements <fromSet> are mapped into <toSet> according to the mapping under
//    @ // <code>public Object mapping(Object)</code>.
//    @ post (\forall Object o; fromSet.contains(o); \result.contains(mapping(o))) &&
//    @      (\forall Object o; toSet.contains(o); 
//    @          (\exists Object o2; fromSet.contains(o2); o == mapping(o2) ));
//    @ // <toSet> is changed and returned
//    @ post \result == toSet;
//    @*/
//   public final Set applyFromTo(Set fromSet, Set toSet) {
//     if ((fromSet != null) && (toSet != null)) {
//       toSet.clear();
//       Iterator iter = fromSet.iterator();
//       while (iter.hasNext()) {
//         toSet.add(mapping(iter.next()));
//       }
//     }
//     return toSet;
//   }
}

