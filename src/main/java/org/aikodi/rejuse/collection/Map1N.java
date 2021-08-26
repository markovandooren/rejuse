package org.aikodi.rejuse.collection;

import com.google.common.collect.ImmutableSet;
import org.aikodi.contract.Contract;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A class that maps keys of type FROM to sets of objects of type TO
 *
 * @param <FROM> The type of the keys.
 * @param <TO> The type of the values.
 */
public class Map1N<FROM, TO> {

    /**
     * Add the given key-value pair to the map.
     *
     * If there was already an association between the given key
     * and value, then nothing happens.
     *
     * @param key The key. Cannot be null.
     * @param value The value that must be associated with the key.
     */
    public void add(FROM key, TO value) {
        Contract.requireNotNull(key);
        Set<TO> set = _map.computeIfAbsent(key, k -> new HashSet<>());
        set.add(value);
    }

    /**
     * Add all associations from the given map to this map.
     *
     * @param map The map from which to add the associations. Cannot be null.
     */
    public void addAll(Map1N<? extends FROM, ? extends TO> map) {
        Contract.requireNotNull(map);
        for (Map.Entry<? extends FROM, ? extends Set<? extends TO>> entry: map._map.entrySet()) {
            for (TO associated: entry.getValue()) {
                add(entry.getKey(), associated);
            }
        }
    }

    /**
     * Return the values associated with the given key.
     *
     * @param key The key of which the associated objects are requested. Cannot be null.
     * @return The set of values associated with the given key. The
     * result is never null, but it may be empty.
     */
    public Set<TO> get(FROM key) {
        Contract.requireNotNull(key);
        return ImmutableSet.copyOf(_map.getOrDefault(key, ImmutableSet.of()));
    }

    /**
     * Return the keys in the map.
     *
     * @return The result is not null and contains all keys for which at least one association is
     * recorded in this map. The result does not contain other elements.
     */
    public Set<FROM> keys() {
        return _map.keySet();
    }

    /**
     * Return the size of the map.
     *
     * @return The number of keys that have a values associated with them.
     */
    public int size() {
        return _map.size();
    }

    /**
     * The map that actually stores the associations.
     */
    private final Map<FROM, Set<TO>> _map = new HashMap<>();
}
