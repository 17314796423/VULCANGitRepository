package map;

import set.FileOperation;

import java.util.ArrayList;

public class BSTreeMap<K extends Comparable<K>, V> implements Map<K, V> {

    private class Node {
        public K key;
        public V value;
        public Node left;
        public Node right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }

        @Override
        public String toString() {
            return this.key.toString() + ":" + this.value.toString();
        }
    }

    private Node root;
    private int size;

    public BSTreeMap() {
        size = 0;
    }

    @Override
    public void put(K key, V value) {
        root = add(root, key, value);
    }

    private Node add(Node node, K key, V value) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }
        if (key.compareTo(node.key) < 0)
            node.left = add(node.left, key, value);
        else if (key.compareTo(node.key) > 0)
            node.right = add(node.right, key, value);
        else
            node.value = value;
        return node;
    }

    private Node minimum(Node node) {
        if (node.left != null)
            return minimum(node.left);
        return node;
    }

    private Node removeMin(Node node) {
        if (node.left != null) {
            node.left = removeMin(node.left);
            return node;
        }
        Node ret = node.right;
        node.right = null;
        size--;
        return ret;
    }

    @Override
    public V remove(K key) {
        Node ret = getNode(root, key);
        if (ret != null) {
            root = remove(root, key);
            return ret.value;
        }
        return null;
    }

    private Node remove(Node node, K key) {
        if (node != null) {
            if (key.compareTo(node.key) < 0)
                node.left = remove(node.left, key);
            else if (key.compareTo(node.key) > 0)
                node.right = remove(node.right, key);
            else {
                Node ret;
                if (node.left == null && node.right == null) {
                    size--;
                    return null;
                } else if (node.left != null && node.right == null) {
                    ret = node.left;
                    node.left = null;
                    size--;
                    return ret;
                } else if (node.left == null && node.right != null) {
                    ret = node.right;
                    node.right = null;
                    size--;
                    return ret;
                } else {
                    Node min = minimum(node.right);
                    node.key = min.key;
                    node.value = min.value;
                    node.right = removeMin(node.right);
                    return node;
                }
            }
        }
        return null;
    }

    @Override
    public boolean contains(K key) {
        return getNode(root, key) != null;
    }

    @Override
    public V get(K key) {
        Node ret = getNode(root, key);
        return ret != null ? ret.value : null;
    }

    private Node getNode(Node node, K key) {
        if (node == null)
            return null;
        if (key.compareTo(node.key) < 0)
            return getNode(node.left, key);
        else if (key.compareTo(node.key) > 0)
            return getNode(node.right, key);
        else
            return node;
    }

    @Override
    public void set(K key, V newValue) {
        Node node = getNode(root, key);
        if (node != null) {
            node.value = newValue;
            return;
        }
        throw new IllegalArgumentException(key + " doesn't exist!");
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public static void main(String[] args){

        System.out.println("Pride and Prejudice");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("pride-and-prejudice.txt", words)) {
            System.out.println("Total words: " + words.size());

            Map<String, Integer> map = new BSTreeMap<>();
            for (String word : words) {
                if (map.contains(word))
                    map.set(word, map.get(word) + 1);
                else
                    map.put(word, 1);
            }

            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));
        }

        System.out.println();
    }

}
