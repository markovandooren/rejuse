package org.aikodi.rejuse.io.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * A map of attributes to their values.
 */
public class AttributeMap {

   /**
    * A map containing the key-value pairs.
    * The map is not null.
    */
   private Map<String, String> map;

   /**
    *
    * @param map A map containing the attributes as keys
    *            and their values as values.
    *            The map cannot be null.
    *            The map cannot contain null as a key.
    *            The map cannot contain null as a value.
    */
   public AttributeMap(Map<String, String> map) {
      if (map == null || map.containsKey(null) || map.containsValue(null)) {
         throw new IllegalArgumentException();
      }
      this.map = new HashMap<>(map);
   }

   /**
    * Return the value of the attribute with the given name as a string.
    *
    * @param attributeName The name of the attribute.
    *                      The name cannot be null.
    *
    * @return The value of the attribute with the given name.
    *
    * @throws IllegalArgumentException This attribute map does not contain
    *                                  an attribute with the given name.
    */
   public String get(String attributeName) {
      checkAttributeName(attributeName);
      return map.get(attributeName);
   }

   /**
    * Check if this attribute map contains an attribute with the given name.
    * If this map contains the attribute, nothing happens.
    * Otherwise, an IllegalArgumentException is thrown.
    *
    * @param attributeName The name of the attribute.
    *
    * @throws IllegalArgumentException This attribute map does not contain
    *                                  an attribute with the given name.
    */
   private void checkAttributeName(String attributeName) {
      if (!map.containsKey(attributeName)) {
         throw new IllegalArgumentException();
      }
   }

   /**
    * Return the value of the attribute with the given name as an int.
    *
    * @param attributeName The name of the attribute.
    *                      The name cannot be null.
    *
    * @return The value of the attribute with the given name.
    *
    * @throws IllegalArgumentException This attribute map does not contain
    *                                  an attribute with the given name.
    * @throws IllegalArgumentException This attribute map contains
    *                                  an attribute with the given name,
    *                                  but it cannot be parsed as an int.
    */
   public int getInt(String attributeName) {
      return Integer.parseInt(get(attributeName));
   }
}
