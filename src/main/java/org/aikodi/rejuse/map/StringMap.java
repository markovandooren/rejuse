package org.aikodi.rejuse.map;

import java.util.HashMap;
import java.util.Map;

import org.aikodi.contract.Contract;

/**
 * A class of immutable string maps.
 *
 * @author Marko van Dooren
 */
public class StringMap {

    private Map<String, String> map;

    /**
     * Create a new string map with the content of the given map.
     *
     * @param map A map containing only strings as key and values
     *            and does not have null keys.
     *            The map cannot be null.
     */
    public StringMap(Map<String, String> map) {
        Contract.requireNotNull(map);
        Contract.require(!map.containsKey(null), "Null is not a valid key.");

        this.map = new HashMap<>(map);
    }

    /**
     * Return the value of the key with the given name as a string.
     *
     * @param attributeName The name of the key.
     *                      The name cannot be null.
     * @return The value of the key with the given name.
     * @throws IllegalArgumentException This map does not contain the given key.
     */
    public String get(String key) {
        Contract.require(map.containsKey(key), "Unknown key: " + key);
        return map.get(key);
    }

    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    /**
     * Return the value of the attribute with the given name as an int.
     *
     * @param key The name of the attribute.
     *            The name cannot be null.
     * @return The value of the attribute with the given name.
     * @throws IllegalArgumentException This attribute map does not contain
     *                                  an attribute with the given name.
     * @throws IllegalArgumentException This attribute map contains
     *                                  an attribute with the given name,
     *                                  but it cannot be parsed as an int.
     */
    public int getInt(String key) {
        Contract.requireNotNull(key);

        return Integer.parseInt(get(key));
    }

    /**
     * Return a builder for a string map.
     *
     * @return A non-null builder for a string map.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * A builder for string maps.
     *
     * @author Marko van Dooren
     */
    public static class Builder {

        private Map<String, String> map = new HashMap<>();

        private Builder() {
        }

        /**
         * Return an object to set the value for the given key.
         *
         * @param key The key to be registered in the string map.
         * @return A non-null object that set the value for the given key.
         */
        public ValueSelector with(String key) {
            return new ValueSelector(key);
        }

        /**
         * A class for setting the value of a given key.
         *
         * @author Marko van Dooren
         */
        public class ValueSelector {

            private String key;

            private ValueSelector(String key) {
                Contract.requireNotNull(key);
                this.key = key;
            }

            /**
             * Set the value of the key with which this value selector
             * was created.
             *
             * @param value The value to be set.
             * @return A builder for a map that has the given value registered
             * for the key with which this value selector was created.
             */
            public Builder as(String value) {
                map.put(key, value);
                return Builder.this;
            }
        }

        /**
         * Build the map.
         *
         * @return A non-null string map containing all key-value pairs that were
         * added via this builder. No other key-value pairs are in the result.
         */
        public StringMap build() {
            return new StringMap(map);
        }
    }

}
