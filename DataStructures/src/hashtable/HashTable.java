package hashtable;

import map.Map;

import java.util.TreeMap;

/**
 * @author VULCAN
 */
public class HashTable<K extends Comparable<K>, V> implements Map<K, V> {

    private TreeMap<K, V>[] hashTable;

    private int size;

    private int tableSize;

    private static final int UPPER_TOL = 10;
    private static final int LOWER_TOL = 2;
    private int capacityIndex = 0;
    private static final int RESIZE_NUM = 2;
    private static final int[] CAPACITY = new int[]{53, 97, 193, 389, 769, 1543, 3079, 6151, 12289
            , 24593, 49157, 98317, 196613, 393241, 786433, 1572869, 3145739, 6291469, 12582917, 25165843,
            50331653, 100663319, 201326611, 402653189, 805306457, 1610612741};

    @SuppressWarnings("unchecked")
    public HashTable() {
        this.tableSize = CAPACITY[capacityIndex];
        this.size = 0;
        hashTable = new TreeMap[tableSize];
        for (int i = 0; i < tableSize; i++) {
            hashTable[i] = new TreeMap<>();
        }
    }

    @Override
    public V remove(K key) {
        TreeMap<K, V> treeMap = hashTable[hash(key)];
        if (treeMap.containsKey(key)) {
            size--;
            V remove = remove(key);
            if (size / tableSize <= LOWER_TOL && capacityIndex - 1 >= 0) {
                capacityIndex--;
                resize(CAPACITY[capacityIndex]);
            }
            return remove;
        }
        return null;
    }

    @Override
    public boolean contains(K key) {
        return hashTable[hash(key)].containsKey(key);
    }

    @Override
    public V get(K key) {
        return hashTable[hash(key)].get(key);
    }

    @Override
    public void set(K key, V newValue) {
        TreeMap<K, V> treeMap = hashTable[hash(key)];
        if (!treeMap.containsKey(key)) {
            throw new IllegalArgumentException(key + " doesn't exist!");
        }
        treeMap.put(key, newValue);
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public void put(K key, V value) {
        TreeMap<K, V> treeMap = hashTable[hash(key)];
        if (!treeMap.containsKey(key)) {
            size++;
        }
        treeMap.put(key, value);
        if (size / tableSize >= UPPER_TOL && capacityIndex + 1 < CAPACITY.length) {
            resize(tableSize * RESIZE_NUM);
        }
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % tableSize;
    }

    @SuppressWarnings("unchecked")
    private void resize(int newM) {
        TreeMap<K, V>[] newHashTable = new TreeMap[newM];
        for (int i = 0; i < newHashTable.length; i++) {
            newHashTable[i] = new TreeMap<>();
        }
        this.tableSize = newM;
        for (TreeMap<K, V> kvTreeMap : hashTable) {
            for (java.util.Map.Entry<K, V> entry : kvTreeMap.entrySet()) {
                newHashTable[hash(entry.getKey())].put(entry.getKey(), entry.getValue());
            }
        }
        this.hashTable = newHashTable;
    }

}
