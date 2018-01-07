package org.aikodi.rejuse.java.collections;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BinaryOperator;

import com.google.common.collect.ImmutableSet;
import static org.aikodi.contract.Contract.requireNotNull;

/**
 * Methods for sets.
 * 
 * @author Marko van Dooren
 */
public class Sets {

	
	public static <T> BinaryOperator<Set<T>> union() {
		return (l,r) -> ImmutableSet.<T>builder().addAll(l).addAll(r).build();
	}
	
	/**
	 * Return an empty set.
	 * 
	 * @return A non-null empty set.
	 */
	public static <T> Set<T> empty() {
		return new HashSet<>();
	}
	
	/**
	 * Clone the given set. This is a shallow clone.
	 * 
	 * @param original The set to clone.
	 *                 The set cannot be null.
	 *                 
	 * @return A non-null set containing the same elements as the
	 *         given set.
	 */
	public static <T> Set<T> clone(Set<T> original) {
		requireNotNull(original);
		
		return new HashSet<>(original);
	}
	
	/**
	 * Return a set containing the given values.
	 * 
	 * @param values The values to be put in the set.
	 *               The list of values cannot contain null.
	 *               
	 * @return A non-null set containing all given values.
	 */
	public static <T> Set<T> of(T ... values) {
		Set<T> result = new HashSet<>();
		for(T value: values) {
			requireNotNull(value);
			result.add(value);
		}
		return result;
	}
}
