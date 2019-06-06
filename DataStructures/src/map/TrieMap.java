package map;

import java.util.TreeMap;
import java.util.Map;

public class TrieMap<E> implements map.Map<String, E> {

    private Node<E> root;
    private int size;

    public TrieMap() {
        this.root = new Node();
        size = 0;
    }

    @Override
    public void put(String key, E value) {
        Node<E> cur = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (cur.next.get(c) == null)
                cur.next.put(c, new Node<>());
            cur = cur.next.get(c);
        }
        if (!cur.isWord)
            size++;
        cur.value = value;
        cur.isWord = true;
    }

    @Override
    public E remove(String key) {
        Node<E> cur = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (cur.next.get(c) == null)
                return null;
            cur = cur.next.get(c);
        }
        if (cur.isWord) {
            E ret = cur.value;
            cur.value = null;
            cur.isWord = false;
            size--;
            return ret;
        }
        return null;
    }

    @Override
    public boolean contains(String key) {
        Node<E> cur = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if(cur.next.get(c) == null)
                return false;
            cur = cur.next.get(c);
        }
        return cur.isWord;
    }

    @Override
    public E get(String key) {
        Node<E> cur = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if(cur.next.get(c) == null)
                return null;
            cur = cur.next.get(c);
        }
        return cur.value;
    }

    @Override
    public void set(String key, E newValue) {
        Node<E> cur = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (cur.next.get(c) == null)
                cur.next.put(c, new Node<>());
            cur = cur.next.get(c);
        }
        if (!cur.isWord)
            throw new IllegalArgumentException(key + "is not exist.");
        cur.value = newValue;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    private class Node<E> {

        Map<Character, Node<E>> next;
        E value;
        boolean isWord;

        public Node(E value) {
            this.next = new TreeMap<Character, Node<E>>();
            this.value = value;
            this.isWord = true;
        }

        public Node() {
            this.next = new TreeMap<Character, Node<E>>();
            this.isWord = false;
        }

    }


}
