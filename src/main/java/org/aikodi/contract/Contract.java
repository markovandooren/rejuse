package org.aikodi.contract;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

/**
 * A class with methods for defensive programming.
 * 
 * @author Marko van Dooren
 */
public class Contract {

  /**
   * Check whether the original list would be equal to the result list if the
   * given element would be added to it.
   * 
   * @param element The element that would be added.
   * @param original The original list. Cannot be null.
   * @param result The resulting list. Cannot be null.
   * @return True if the last element of result is the given element, and all
   * other elements are the same. 
   */
  public static <T> boolean wasElementAdded(T element, List<T> original, List<T> result) {
    requireNotNull(original, result);

    int originalSize = original.size();
    return result.size() == originalSize + 1 &&
        IntStream.range(0, originalSize).allMatch(i -> result.get(i) == original.get(i)) &&
        result.get(originalSize) == element;
  }
  
  /**
   * Check whether the original list would be equal to the result list if the
   * given element would be removed from it.
   * 
   * @param element The element that would be removed.
   * @param original The original list. Cannot be null.
   * @param result The resulting list. Cannot be null.
   * @return True if result has one element less than original, and all elements
   * of result are the same as the elements at the corresponding indices in
   * the original list.
   */
  public static <T> boolean wasElementRemoved(T element, List<T> original, List<T> result) {
    requireNotNull(original, result);

    int resultSize = result.size();
    return original.size() == resultSize - 1 &&
        IntStream.range(0, resultSize).allMatch(i -> result.get(i) == original.get(i));
  }
  
  /** 
   * Check if the given object is null.
   * 
   * @param o The object to check.
   * 
   * @throws IllegalArgumentException The object is null.
   */
  public static <T> T requireNotNull(T o) {
    return requireNotNull(o, "The given object cannot be null.");
  }
  
  /** 
   * Check if the given array of objects contains a null reference.
   * 
   * @param o The array to check.
   * 
   * @throws IllegalArgumentException One of the objects in the array is null.
   */
  public static void requireNotNull(Object... o) {
    for(Object object: o) {
      requireNotNull(object,"");
    }
  }
  
  /**
   * Check if the given state is true.
   * 
   * @param state The state to check.
   * @param message The message of the exception in case the state is false.
   * 
   * @throws IllegalStateException The state is false.
   */
  public static void requireState(boolean state, String message) {
	  if (! state) {
		  throw new IllegalStateException(message);
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
  public static void require(boolean bool, String message) {
    if (message == null) {
      throw new IllegalArgumentException("The precondition message cannot be null");
    }
    if(! bool) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * Check if the given object is null.
   * @param o The object to check.
   * @param message The error message to be used in case the object is null. Cannot be null.
   * @param <T> The type of the object.
   * @return The given object. This allows a precondition to be checked inside a super constructor call.
   */
  public static <T> T requireNotNull(T o, String message) {
    require(o != null, message);
    return o;
  }
  
  /**
   * Check whether the given collection is null or contains null.
   * @param collection The collection to be checked.
   * @throws IllegalArgumentException The collection is null or the collection contains null.
   */
  public static void requireSaneCollection(Collection<?> collection) {
  	 requireNotNull(collection);
  	 require(! collection.contains(null), "A collection should not contain null.");
  }
}
