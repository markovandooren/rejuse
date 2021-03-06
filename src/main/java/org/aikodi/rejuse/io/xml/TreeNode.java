package org.aikodi.rejuse.io.xml;

import static org.aikodi.contract.Contract.require;
import static org.aikodi.contract.Contract.requireNotNull;

import org.aikodi.rejuse.action.Nothing;
import org.aikodi.rejuse.function.BiConsumer;
import org.aikodi.rejuse.function.Consumer;
import org.aikodi.rejuse.map.StringMap;

public class TreeNode {
	
	/**
	 * A map containing the attributes and their values.
	 * The map is not null.
	 */
	private StringMap _attributes;
	
	/**
	 * The name of the node.
	 * The name is not null.
	 */
	private String _name;
	
	/**
	 * Create a new node with the given name and attributes.
	 * The name is set to the given name.
	 * The node contains all attributes of the given map.
	 * The attribute values of this node are the same as the values in the given map.
	 * 
	 * @param name The name of the node.
	 *             The name cannot be null.
	 * @param attributes The attributes of the node.
	 *                   The map cannot be null.
	 */
	public TreeNode(String name, StringMap attributes) {
		requireNotNull(name != null);
		requireNotNull(attributes != null);
		
		_attributes = attributes;
		_name = name;
	}
	
	/**
	 * Return the name of this node.
	 * 
	 * @return The name of this node.
	 *         The name is not null.
	 */
	public String name() {
		return _name;
	}
	
	/**
	 * Return the value of the attribute with the given name.
	 * 
	 * @param name The name of the attribute.
	 *             The name cannot be null.
	 *             The node must contain an attribute with the given name.
	 *             
	 * @return The value of the attribute with the given name.
	 *         The result is not null. 
	 */
	public String attribute(String name) {
		requireNotNull(name != null);
		require(_attributes.containsKey(name), "The node does not contain an attribute with name " + name);
		
		return _attributes.get(name);
	}
	
	public <T> Mapper<T> map(T value) {
		return new Mapper<>(value);
	}
	
	public class Mapper<T> {
		private T _value;
		
		public Mapper(T value) {
			requireNotNull(value);
			
			_value = value;
		}
		
		public T value() {
			return _value;
		}
		
		public AttributeMapper use(String name) {
			return new AttributeMapper(name);
		}
		
		public class AttributeMapper {
			private String _name;
			
			public AttributeMapper(String name) {
				requireNotNull(name);
				
				_name = name;
			}
			
			public Mapper<T> with(BiConsumer<T, String, Nothing> action) {
				if (contains(_name)) {
					action.accept(_value, attribute(_name));
				}
				return Mapper.this;
			}
			
			public Mapper<T> withInt(BiConsumer<T, Integer, Nothing> action) {
				if (contains(_name)) {
					action.accept(_value, parseInt(_name));
				}
				return Mapper.this;
			}
		}
	}
	
	public <E extends Exception> TreeNode with(String name, Consumer<String, E> consumer) throws E {
		if (contains(name)) {
			consumer.accept(attribute(name));
		}
		return this;
	}
	
	/**
	 * Return the integer value of the attribute with the given name.
	 * 
	 * @param name The name of the attribute.
	 *             The name cannot be null.
	 *             The node must contain an integer attribute with the given name.
	 *             
	 * @throws NumberFormatException The given string cannot be parsed as an integer
	 *                               according to {@link Integer#parseInt(String)}.
	 *                               
	 * @return The integer value of the attribute with the given name according to
	 *         {@link Integer#parseInt(String)}.
	 */
	public int parseInt(String name) {
		return Integer.parseInt(attribute(name));
	}
	
	/**
	 * Return the integer value of the attribute with the given name.
	 * 
	 * @param name The name of the attribute.
	 *             The name cannot be null.
	 *             The node must contain an integer attribute with the given name.
	 *             
	 * @throws NumberFormatException The given string cannot be parsed as an integer
	 *                               according to {@link Integer#parseInt(String)}.
	 *                               
	 * @return The integer value of the attribute with the given name according to
	 *         {@link Integer#parseInt(String)}.
	 */
	public boolean parseBoolean(String name) {
		return Boolean.parseBoolean(attribute(name));
	}
	
	/**
	 * Check if this node contains an attribute with the given name.
	 * 
	 * @param name The name of the attribute.
	 * 
	 * @return True if this node contains an attributes with the given name.
	 *         False otherwise.
	 */
	public boolean contains(String name) {
		return _attributes.containsKey(name);
	}
	
	@Override
	public String toString() {
		return name();
	}
}