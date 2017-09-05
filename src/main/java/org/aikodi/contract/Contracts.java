package org.aikodi.contract;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

/**
 * A class with methods for defensive programming.
 * 
 * @author Marko van Dooren
 */
public class Contracts {

  /**
   * Check whether the original list would be equal to the result list if the
   * given element would be added to it.
   * 
   * @param element The element that would be added.
   * @param original The original list.
   * @param result The resulting list.
   * @return True if the last element of result is the given element, and all
   * other elements are the same. 
   */
  public static <T> boolean wasElementAdded(T element, List<T> original, List<T> result) {
    int originalSize = original.size();
    return original != null &&
        result != null &&
        result.size() == originalSize + 1 &&
        IntStream.range(0, originalSize).allMatch(i -> result.get(i) == original.get(i)) &&
        result.get(originalSize) == element;
  }
  
  /**
   * Check whether the original list would be equal to the result list if the
   * given element would be removed from it.
   * 
   * @param element The element that would be removed.
   * @param original The original list.
   * @param result The resulting list.
   * @return True if result has one element less than original, and all elements
   * of result are the same as the elements at the corresponding indices in
   * the original list. 
   */
  public static <T> boolean wasElementRemoved(T element, List<T> original, List<T> result) {
    int resultSize = result.size();
    return original != null &&
        result != null &&
        original.size() == resultSize - 1 &&
        IntStream.range(0, resultSize).allMatch(i -> result.get(i) == original.get(i));
  }
  
  /** 
   * Check if the given object is null.
   * 
   * @param o The object to check.
   * 
   * @throws IllegalArgumentException The object is null.
   */
  public final static void notNull(Object o) {
    notNull(o, "");
  }
  
  /** 
   * Check if the given array of objects contains a null reference.
   * 
   * @param o The array to check.
   * 
   * @throws IllegalArgumentException One of the objects in the array is null.
   */
  public final static void notNull(Object... o) {
    for(Object object: o) {
      notNull(object,"");
    }
  }
  
  /**
   * Check if the given boolean is true.
   * 
   * @param bool The boolean value to check.
   * @param message The message of the exception in case the boolean is false.
   * 
   * @throws IllegalArgumentException The boolean value is false.
   */
  public final static void check(boolean bool, String message) {
    if(! bool) {
      throw new IllegalArgumentException(message);
    }
  }
  
  public final static void notNull(Object o, String message) {
    check(o != null, message);
  }
  
  /**
   * Check whether the given collection is null or contains null.
   * @param collection The collection to be checked.
   * @throws IllegalArgumentException The collection is null or the collection contains null.
   */
  public final static void checkCollection(Collection<?> collection) {
  	notNull(collection);
  	check(! collection.contains(null), "A collection should not contain null.");
  }

}
