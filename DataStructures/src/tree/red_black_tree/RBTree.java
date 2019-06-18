package tree.red_black_tree;

import map.Map;
import set.FileOperation;

import java.util.ArrayList;

/**
 *  1.每个节点是红色或黑色
 *  2.根节点是黑色
 *  3.每个叶子节点(最后的空节点)是黑色
 *  4.如果一个节点是红色，其孩子节点必定为黑色
 *  5.从任意节点到其所有的叶子节点中途经过的黑色节点个数一样
 *
 *  红黑树实现了黑节点的绝对平衡，而非标准意义的平衡
 *  单个操作的最坏的时间复杂度是2logN，即所有节点均为"3-"节点
 *  该实现为红色节点左倾的红黑树
 */
public class RBTree<K extends Comparable<K>, V>{

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        public K key;
        public V value;
        public Node left,right;
        public boolean color;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
            this.color = RED;
        }

        @Override
        public String toString() {
            return this.key.toString() + ":" + this.value.toString();
        }
    }

    private Node root;
    private int size;

    public RBTree() {
        size = 0;
    }

    public void put(K key, V value) {
        root = add(root, key, value);
        root.color = BLACK;
    }

    private boolean isRed(Node node) {
        if(node == null){
            return BLACK;
        }
        return node.color;
    }

    private Node leftRotate(Node node){
        Node x = node.right;
        node.right = x.left;
        x.left = node;
        x.color = node.color;
        node.color = RED;
        return x;
    }

    private Node rightRotate(Node node){
        Node x = node.left;
        node.left = x.right;
        x.right = node;
        x.color = node.color;
        node.color = RED;
        return x;
    }

    private void flipColor(Node node){
        node.color = RED;
        node.left.color = node.right.color = BLACK;
    }

    private Node add(Node node, K key, V value) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }
        if (key.compareTo(node.key) < 0) {
            node.left = add(node.left, key, value);
        } else if (key.compareTo(node.key) > 0) {
            node.right = add(node.right, key, value);
        } else {
            node.value = value;
        }
        if(isRed(node.right) && !isRed(node.left)){
            node = leftRotate(node);
        }else if(isRed(node.left) && isRed(node.left.left)){
            node = rightRotate(node);
        }
        if(isRed(node.right) && isRed(node.left)){
            flipColor(node);
        }
        return node;
    }

    private Node minimum(Node node) {
        if (node.left != null) {
            return minimum(node.left);
        }
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
            if (key.compareTo(node.key) < 0) {
                node.left = remove(node.left, key);
            } else if (key.compareTo(node.key) > 0) {
                node.right = remove(node.right, key);
            } else {
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

    public boolean contains(K key) {
        return getNode(root, key) != null;
    }

    public V get(K key) {
        Node ret = getNode(root, key);
        return ret != null ? ret.value : null;
    }

    private Node getNode(Node node, K key) {
        if (node == null) {
            return null;
        }
        if (key.equals(node.key)) {
            return node;
        } else if (key.compareTo(node.key) < 0) {
            return getNode(node.left, key);
        } else {
            return getNode(node.right, key);
        }
    }

    public void set(K key, V newValue) {
        Node node = getNode(root, key);
        if (node == null) {
            throw new IllegalArgumentException(key + " doesn't exist!");
        }
        node.value = newValue;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private boolean isRB(Node node) {
        if(node == null) {
            return true;
        }
        if(node.color == RED && (isRed(node.left) || isRed(node.right))) {
            return false;
        }
        return isRB(node.left) && isRB(node.right);
    }

    public boolean isRB(){
        if(root.color != BLACK){
            return false;
        }
        return isRB(root);
    }

    public static void main(String[] args){

        System.out.println("The Decameron Giovanni Boccaccio");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("The-Decameron-Giovanni-Boccaccio.txt", words)) {
            System.out.println("Total words: " + words.size());

            RBTree<String, Integer> map = new RBTree<>();
            for (String word : words) {
                if (map.contains(word)) {
                    map.set(word, map.get(word) + 1);
                } else {
                    map.put(word, 1);
                }
            }

            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));
            System.out.println("isRBTree: " + map.isRB());
        }
        System.out.println();
    }

}
