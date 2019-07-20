package data.structures.map;

public interface Map<K, V> {

    V remove(K key);
    boolean contains(K key);
    V get(K key);
    void set(K key, V newValue);
    int getSize();
    boolean isEmpty();
    void put(K key , V value);
}
