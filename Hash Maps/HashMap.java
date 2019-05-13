import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
/**
 * Your implementation of HashMap.
 * 
 * @author QUYEN TRAN
 * @version 1.0
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {

    // Do not make any new instance variables.
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    @SuppressWarnings("unchecked")
    public HashMap(int initialCapacity) {
        table = (MapEntry<K, V>[]) new MapEntry[initialCapacity];
    }

    /**
     * Adds the given key-value pair to the HashMap.
     * If an entry in the HashMap already has this key, replace the entry's
     * value with the new one passed in.
     *
     * In the case of a collision, use quadratic probing as your resolution
     * strategy.
     *
     * Check to see if the backing array needs to be regrown BEFORE adding. For
     * example, if my HashMap has a backing array of length 5, and 3 elements in
     * it, I should regrow at the start of the next add operation (even if it
     * is a key that is already in the hash map). This means you must account
     * for the data pending insertion when calculating the load factor.
     *
     * When regrowing, increase the length of the backing table by
     * 2 * old length + 1. Use the resizeBackingTable method.
     *
     * If an available position is not found within the backing table after
     * probing table.length times, regrow the backing array
     * and begin probing again
     * starting at the initial hashed index.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key key to add into the HashMap
     * @param value value to add into the HashMap
     * @throws IllegalArgumentException if key or value is null
     * @return null if the key was not already in the map.  If it was in the
     * map, return the old value associated with it
     */
    @SuppressWarnings("unchecked")
    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Either the key or "
                    + "value cant be null");
        }
        V removed = null;
        MapEntry<K, V> entry = new MapEntry<>(key, value);
        int hashCode = Math.abs(key.hashCode());
        double loadFactor = (size + 1) / (double) table.length;
        if (loadFactor >= MAX_LOAD_FACTOR) {
            resizeBackingTable(2 * table.length + 1);
        }
        int markDeleted = -1;
        while (markDeleted == -1) {
            for (int h = 0; h < table.length; h++) {
                int index = (hashCode + h * h) % table.length;
                if (table[index] == null) {
                    if (markDeleted == -1) {
                        table[index] = entry;
                        size++;
                        return null;
                    } else {
                        table[markDeleted] = entry;
                        size++;
                        return null;
                    }
                }
                if (table[index].getKey().equals(key)
                        && !table[index].isRemoved()) {
                    removed = table[index].getValue();
                    table[index] = entry;
                    return removed;
                }
                if (table[index].isRemoved() && markDeleted == -1) {
                    markDeleted = index;
                }
            }
            if (markDeleted != -1) {
                table[markDeleted] = entry;
                size++;
                return null;
            } else {
                resizeBackingTable(2 * table.length + 1);
            }
        }
        return removed;
    }

    /**
     * Removes the entry with a matching key from the HashMap.
     *
     * @param key the key to remove
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key does not exist
     * @return the value previously associated with the key
     */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cant be null");
        }
        V value = null;
        int hashCode = Math.abs(key.hashCode());
        int index = hashCode % table.length;
        int h = 1;
        while (table[index] != null && h < table.length) {
            if (table[index].getKey().equals(key)
                    && !table[index].isRemoved()) {
                value = table[index].getValue();
                table[index].setRemoved(true);
                size--;
                return value;
            }
            index = (hashCode + h * h) % table.length;
            h++; 
        }

        throw new java.util.NoSuchElementException("The key doen not exist");
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key is not in the map
     * @return the value associated with the given key
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key cant be null");
        }
        V value = null;
        int hashCode = Math.abs(key.hashCode());
        int index = hashCode % table.length;
        int h = 1;
        while (table[index] != null && h < table.length) {
            if (table[index].getKey().equals(key)
                    && !table[index].isRemoved()) {
                value = table[index].getValue();
                return value;
            }
            index = (hashCode + h * h) % table.length;
            h++;
        }
        throw new java.util.NoSuchElementException("The key does not exist");
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @return whether or not the key is in the map
     */
    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cant be null");
        }
        int hashCode = Math.abs(key.hashCode());
        int index = hashCode % table.length;
        int h = 1;
        while (table[index] != null && h < table.length) {
            if (table[index].getKey().equals(key)
                    && !table[index].isRemoved()) {
                return true;
            }
            index = (hashCode + h * h) % table.length;
            h++;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        table = (MapEntry<K, V>[]) new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     * Use {@code java.util.HashSet}.
     *
     * @return set of keys in this map
     */
    @Override
    public Set<K> keySet() {
        Set<K> newSet = new HashSet<K>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                newSet.add(table[i].getKey());
            }
        }
        return newSet;
    }

    /**
     * Returns a List view of the values contained in this map.
     * beginning with the first index of the backing array.
     * Use any class that implements the List interface
     * This includes {@code java.util.ArrayList} and
     * {@code java.util.LinkedList}.
     *
     * @return list of values in this map
     */
    @Override
    public List<V> values() {
        List<V> newList = new ArrayList<V>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                newList.add(table[i].getValue());
            }
        }
        return newList;
    }

    /**
     * Resize the backing table to {@code length}.
     *
     * After resizing, the table's load factor is permitted to exceed
     * MAX_LOAD_FACTOR. No adjustment to the backing table's length is necessary
     * should this occurr.
     *
     * Remember that you cannot just simply copy the entries over to the new
     * array.
     *
     * @param length new length of the backing table
     * @throws IllegalArgumentException if length is non-positive or less than
     * the number of items in the hash map.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void resizeBackingTable(int length) {
        if (length <= 0 || length < size) {
            throw new IllegalArgumentException("The length of table cant be "
                    + "non-positive or less than the number of items "
                    + "in the hash map ");
        }
        MapEntry<K, V>[] newTable = new MapEntry[length];
        MapEntry<K, V>[] oldTable = table;
        size = 0;
        table = newTable;
        for (int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] != null && !oldTable[i].isRemoved()) {
                MapEntry<K, V> entry = table[i];
                put(oldTable[i].getKey(), oldTable[i].getValue());
            }
        }
    }
    
    @Override
    public MapEntry<K, V>[] getTable() {
        // DO NOT EDIT THIS METHOD!
        return table;
    }

}
