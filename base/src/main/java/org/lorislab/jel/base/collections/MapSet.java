/*
 * Copyright 2014 lorislab.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lorislab.jel.base.collections;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * The map of sets.
 *
 * @param <K> the key.
 * @param <V> the value.
 *
 * @author Andrej Petras
 */
public class MapSet<K, V> {

    /**
     * The private collection map and sets.
     */
    private final Map<K, Set<V>> data = new HashMap<>();

    /**
     * Gets the entry set.
     *
     * @return the entry set.
     */
    public Set<Entry<K, Set<V>>> entrySet() {
        return data.entrySet();
    }

    /**
     * Gets the map.
     *
     * @return the map.
     */
    public Map<K, Set<V>> map() {
        return data;
    }

    /**
     * Returns {@code true} if the key contains the value.
     *
     * @param key the key.
     * @param value the value.
     * @return {@code true} if the key contains the value.
     */
    public boolean contains(K key, V value) {
        Set<V> set = data.get(key);
        if (set != null) {
            return set.contains(value);
        }
        return false;
    }

    /**
     * Returns {@code true} if the map contains the key.
     *
     * @param key the key.
     * @return {@code true} if the map contains the key.
     */
    public boolean containsKey(K key) {
        return data.containsKey(key);
    }

    /**
     * Puts the value to the key.
     *
     * @param key the key.
     * @param value the value.
     */
    public void put(K key, V value) {
        Set<V> set = data.get(key);
        if (set == null) {
            set = new HashSet<>();
            data.put(key, set);
        }
        set.add(value);
    }

    /**
     * Gets the set of values for the key.
     *
     * @param key the key.
     * @return the corresponding set of values.
     */
    public Set<V> getValues(K key) {
        return data.get(key);
    }

    /**
     * Gets the size of the map.
     *
     * @return the size of the map.
     */
    public int size() {
        return data.size();
    }

    /**
     * Gets the size of the set of the key.
     *
     * @param key the key.
     * @return the size of the set of the key.
     */
    public int size(K key) {
        Set<V> set = data.get(key);
        if (set != null) {
            return set.size();
        }
        return 0;
    }

    /**
     * Returns {@code true} if the map is empty.
     *
     * @return {@code true} if the map is empty.
     */
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * Returns {@code true} if the set of the key is empty.
     *
     * @param key the key.
     * @return {@code true} if the set of the key is empty.
     */
    public boolean isEmpty(K key) {
        Set<V> set = data.get(key);
        if (set != null) {
            return set.isEmpty();
        }
        return true;
    }

}
