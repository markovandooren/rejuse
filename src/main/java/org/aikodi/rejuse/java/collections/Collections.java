package org.aikodi.rejuse.java.collections;
import java.util.Collection;
import java.util.Map;
/**
 * <p>A utility class for perform operations on collections similar
 * to java.util.Collections.</p>
 *
 * @author  Marko van Dooren
 */
public abstract class Collections {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

 /*@ 
   @ public invariant_redundantly
   @                  (\forall Collection c; c != null;
   @                    (\forall Object o1; c.contains(o1);
   @                      (\exists Object o2; containsExplicitly(c, o2);
   @                        ((o1 == null) && (o2 == null)) ||
   @                        ((o1 != null) && (o1.equals(o2))))));
   @
   @ public invariant_redundantly
   @                  (\forall Collection c; c!= null; 
   @                    (\forall Object o2; containsExplicitly(c, o2);
   @                      c.contains(o2)));
   @*/
  
  /**
   * Check whether or not the given collection contains the given element
   * when using == for comparison instead of equals().
   *
   * @param collection
   *        The collection
   * @param element
   *        The element
   */
 /*@
   @ public behavior
   @
   @ pre collection != null;
   @
   @ post \result == (\exists int i; i >= 0 && i < collection.toArray().length;
   @                     collection.toArray()[i] == element);
   @*/
  public static /*@ pure @*/ boolean containsExplicitly(Collection<?> collection, Object element) {
    Object[] array = collection.toArray();
    boolean found = false;
    for (int i=0 ; i < array.length && (! found); i++) {
      if(array[i] == element) {
        found = true;
      }
    }
    return found; 
  }

  /**
   * Return the number of times <element> has been added to <collection>.
   *
   * @param element
   *        The element of which the number of occurrences is requested.
   * @param collection
   *        The collection in which the number of occurrences has to be counted.
   */
 /*@
   @ public behavior
   @
   @ pre collection != null;
   @
   @ post \result == (\num_of int i; i >= 0 && i < collection.toArray().length;
   @                    collection.toArray()[i] == element);
   @*/
  public static /*@ pure @*/ long nbExplicitOccurrences(Object element, Collection<?> collection) {
    if(collection == null) {
      return 0;
    }
    return collection.stream().filter(o -> o == element).count();
  }
    
  /**
   * Check whether the two given collections contain the same elements.
   * Test are done with ==
   *
   * @param first
   *        The first collection
   * @param second
   *        The second collection
   */
 /*@
   @ public behavior
   @
   @ // True if both collections are null
   @ post ((first == null) && (second == null)) ==> \result == true;
   @
   @ // False if only one of both is null.
   @ post ((first == null) || (second == null)) &&
   @      !((first == null) && (second == null)) ==> \result ==false;
   @
   @ // If both collections are non-null, true if both collections
   @ // have the same size and contains the exact same elements.
   @ post ((first != null) && (second != null)) ==>
   @        \result == (first.size() == second.size()) &&
   @        (\forall Object o; containsExplicitly(first, o);
   @          nbExplicitOccurrences(o, first) == nbExplicitOccurrences(o, second));
   @*/
  public static /*@ pure @*/ boolean identical(final Collection<?> first, final Collection<?> second) {
    //MvDMvDMvD : add testcase for {A,B,B} and {A,A,B}
    if ((first == null) && (second == null)) {
      return true;
    }
    if ((first == null) || (second == null)) {
      return false;
    }
    if (first.size() != second.size()) {
      return false;
    }
    return first.stream().allMatch(element -> nbExplicitOccurrences(element, first) == nbExplicitOccurrences(element, second)); 
  }
  
}