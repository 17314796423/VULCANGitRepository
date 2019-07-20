package data.structures.tree.avl;

import data.structures.set.FileOperation;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class AVLTree<K extends Comparable<K>, V>{

    private class Node {
        public K key;
        public V value;
        public Node left;
        public Node right;
        public int height;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
            this.height = 1;
        }

        @Override
        public String toString() {
            return this.key.toString() + ":" + this.value.toString();
        }

    }

    private Node root;
    private int size;

    public AVLTree() {
        size = 0;
    }

    public boolean isBalanced(){
        return isBalanced(root);
    }

    public boolean isBalanced(Node node){
        if(node == null){
            return true;
        }
        if(Math.abs(getBalanceFactor(node)) > 1){
            return false;
        }
        return isBalanced(node.left) && isBalanced(node.right);
    }

    public boolean isBST(){
        List<K> keys = new ArrayList<>();
        inOrder(root, keys);
        for (int i = 1; i < keys.size(); i++) {
            if(keys.get(i).compareTo(keys.get(i - 1)) < 0){
                return false;
            }
        }
        return true;
    }

    private void inOrder(Node node, List<K> keys) {
        if(node != null) {
            inOrder(node.left,keys);
            keys.add(node.key);
            inOrder(node.right,keys);
        }
    }

    private int getHeight(Node node){
        if(node != null) {
            return node.height;
        }
        return 0;
    }

    private int getBalanceFactor(Node node){
        if(node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    public void add(K key, V value) {
        root = add(root, key, value);
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
            //throw new IllegalArgumentException("element is existed!");
            node.value = value;
        }
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        int balanceFactor = getBalanceFactor(node);
        //RR
        if(balanceFactor > 1){
            //LR
            if(getBalanceFactor(node.left) < 0){
                node.left = leftRotate(node.left);
            }
            return rightRotate(node);
        }
        //LL
        if(balanceFactor < -1){
            //RL
            if(getBalanceFactor(node.right) > 0){
                node.right = rightRotate(node.right);
            }
            return leftRotate(node);
        }
        return node;
    }

    // 对节点y进行向右旋转操作，返回旋转后新的根节点x
    //        y                              x
    //       / \                           /   \
    //      x   T4     向右旋转 (y)        z     y
    //     / \       - - - - - - - ->    / \   / \
    //    z   T3                       T1  T2 T3 T4
    //   / \
    // T1   T2

    private Node rightRotate(Node y){
        Node x = y.left;
        Node T3 = x.right;
        x.right = y;
        y.left = T3;
        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));
        return x;
    }

    // 对节点y进行向左旋转操作，返回旋转后新的根节点x
    //    y                             x
    //  /  \                          /   \
    // T1   x      向左旋转 (y)       y     z
    //     / \   - - - - - - - ->   / \   / \
    //   T2  z                     T1 T2 T3 T4
    //      / \
    //     T3 T4

    private Node leftRotate(Node y){
        Node x = y.right;
        Node T2 = x.left;
        x.left = y;
        y.right = T2;
        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));
        return x;
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
            node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
            int balanceFactor = getBalanceFactor(node);
            //RR
            if(balanceFactor > 1){
                //LR
                if(getBalanceFactor(node.left) < 0){
                    node.left = leftRotate(node.left);
                }
                return rightRotate(node);
            }
            //LL
            if(balanceFactor < -1){
                //RL
                if(getBalanceFactor(node.right) > 0){
                    node.right = rightRotate(node.right);
                }
                return leftRotate(node);
            }
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
            Node ret;
            if (key.compareTo(node.key) < 0) {
                node.left = remove(node.left, key);
            } else if (key.compareTo(node.key) > 0) {
                node.right = remove(node.right, key);
            } else {
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
                    //这里不可以使用removeMin，在removeMin过程中从底至上出现的第一个不平衡节点并不一定就是node.right，如果是在其中间节点出现不平衡，没有及时处理则会失去平衡，仅利用本方法的旋转对非第一个不平衡节点进行旋转操作已经无效
                    //解决方案为在removeMin中对每个节点自底向上进行自平衡，或调用remove方法对node.right进行递归删除指定的minNode对应的key
//                    node.right = remove(node.right, min.key);
                    node.right = removeMin(node.right);
//                    node.right = removeMin(node.right);
//                    min.right = remove(node.right, min.key);
//                    min.left = node.left;
//                    node.left = node.right = null;
//                    ret = min;
                }
            }
            ret = node;
            ret.height = 1 + Math.max(getHeight(ret.left), getHeight(ret.right));
            int balanceFactor = getBalanceFactor(ret);
            //RR
            if(balanceFactor > 1){
                //LR
                if(getBalanceFactor(ret.left) < 0){
                    ret.left = leftRotate(ret.left);
                }
                return rightRotate(ret);
            }
            //LL
            if(balanceFactor < -1){
                //RL
                if(getBalanceFactor(ret.right) > 0){
                    ret.right = rightRotate(ret.right);
                }
                return leftRotate(ret);
            }
            return ret;
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
        if (key.compareTo(node.key) < 0) {
            return getNode(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            return getNode(node.right, key);
        } else {
            return node;
        }
    }

    public void set(K key, V newValue) {
        Node node = getNode(root, key);
        if (node != null) {
            node.value = newValue;
            return;
        }
        throw new IllegalArgumentException(key + " doesn't exist!");
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        System.out.println("The Decameron Giovanni Boccaccio");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("The-Decameron-Giovanni-Boccaccio.txt", words)) {
            System.out.println("Total words: " + words.size());

            AVLTree<String, Integer> map = new AVLTree<>();
            for (String word : words) {
                if (map.contains(word)) {
                    map.set(word, map.get(word) + 1);
                } else {
                    map.add(word, 1);
                }
            }

            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));
            System.out.println("is BST : " + map.getClass().getMethod("isBST").invoke(map));
            System.out.println("is Balanced : " + map.getClass().getMethod("isBalanced").invoke(map));

            for(String word: words){
                map.remove(word);
                if(!map.isBST() || !map.isBalanced()){
                    throw new RuntimeException("ERROR");
                }
            }
            System.out.println("size : " + map.getSize());
        }

        System.out.println();
    }

}
