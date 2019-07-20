package data.structures.tree.red_black_tree;

import data.structures.set.FileOperation;

import java.util.ArrayList;

/**
 * 1.每个节点是红色或黑色
 * 2.根节点是黑色
 * 3.每个叶子节点(最后的空节点)是黑色
 * 4.如果一个节点是红色，其孩子节点必定为黑色
 * 5.从任意节点到其所有的叶子节点中途经过的黑色节点个数一样
 *
 * 红黑树实现了黑节点的绝对平衡，而非标准意义的平衡
 * 单个操作的最坏的时间复杂度是2logN，即所有节点均为"3-"节点
 * 该实现为红色节点左倾的红黑树,即不存在任何一个节点的右节点是红色节点
 *
 * @author VULCAN
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class RBTree<K extends Comparable<K>, V> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    @SuppressWarnings("WeakerAccess")
    private class Node {
        public K key;
        public V value;
        public Node left, right;
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
            return this.key + ":" + this.value + "  " + (this.color ? "RED" : "BLACK");
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
        if (node == null) {
            return BLACK;
        }
        return node.color;
    }

    private Node leftRotate(Node node) {
        Node x = node.right;
        node.right = x.left;
        x.left = node;
        x.color = node.color;
        node.color = RED;
        return x;
    }

    private Node rightRotate(Node node) {
        Node x = node.left;
        node.left = x.right;
        x.right = node;
        x.color = node.color;
        node.color = RED;
        return x;
    }

    private void flipColor(Node node) {
        node.color = !node.color;
        node.left.color = !node.left.color;
        node.right.color = !node.right.color;
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
        if (isRed(node.right) && !isRed(node.left)) {
            node = leftRotate(node);
        } else if (isRed(node.left) && isRed(node.left.left)) {
            node = rightRotate(node);
            flipColor(node);
        } else if (isRed(node.right) && isRed(node.left)) {
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

    private Node maximum(Node node) {
        if (node.right != null) {
            return maximum(node.right);
        }
        return node;
    }

    public void deleteMin() {
        if (root != null) {
            root = deleteMin(root);
            if (root != null) {
                root.color = BLACK;
            }
        }
    }

    private Node deleteMin(Node h) {
        if (h.left == null) {
            size--;
            return null;
        }
        if (!isRed(h.left) && !isRed(h.left.left)) {
            h = moveRedLeft(h);
        }
        h.left = deleteMin(h.left);
        return fixUp(h);
    }

    public void deleteMax() {
        if (root != null) {
            root = deleteMax(root);
            if (root != null) {
                root.color = BLACK;
            }
        }
    }

    private Node deleteMax(Node h) {
        if (isRed(h.left)) {
            h = rightRotate(h);
        } else if (h.right == null) {
            size--;
            return null;
        } else if (!isRed(h.right) && !isRed(h.right.left)) {
            h = moveRedRight(h);
        }
        h.right = deleteMax(h.right);
        return fixUp(h);
    }

    private Node moveRedLeft(Node h) {
        flipColor(h);
        //这种情况对于之后的fixUp如果不进行预处理是有问题的
        if (isRed(h.right.left)) {
            h.right = rightRotate(h.right);
            h = leftRotate(h);
            flipColor(h);
        }
        return h;
    }

    private Node moveRedRight(Node h) {
        flipColor(h);
        //这种情况对于之后的fixUp如果不进行预处理貌似是没有问题的
//        if (isRed(h.left.left)){
//            h = rightRotate(h);
//            flipColor(h);
//        }
        return h;
    }

    private Node fixUp(Node h) {
        if (isRed(h.right)) {
            h = leftRotate(h);
        }
        if (isRed(h.left) && isRed(h.left.left)) {
            h = rightRotate(h);
        }
        if (isRed(h.left) && isRed(h.right)) {
            flipColor(h);
        }
        return h;
    }

    public V remove(K key) {
        Node ret = getNode(root, key);
        if (ret != null) {
            root = remove(root, key);
            if (root != null) {
                root.color = BLACK;
            }
            return ret.value;
        }
        return null;
    }

    /**
     * 不论颜色，任意节点的子节点不可能存在单边黑色，因为要满足黑平衡
     * 1.删除元素为叶子节点，且红色直接删除
     * 2.删除元素为非叶子节点，且为黑色，同时该节点只有红色左节点，则红色赋值给该节点后删除红色节点
     * 3.删除元素为非叶子节点，且两边都有子节点，则删除后继节点，后继节点为红色直接删除
     *
     * @param node
     * @param key
     * @return
     */
    private Node remove(Node node, K key) {
        if (key.compareTo(node.key) < 0) {
            if (!isRed(node.left) && !isRed(node.left.left)) {
                node = moveRedLeft(node);
            }
            node.left = remove(node.left, key);
        } else {
            if (isRed(node.left)) {
                node = rightRotate(node);
            } else if (/*key.compareTo(node.key) == 0 &&*/ (node.right == null)) {          //第一个条件判断貌似多余的，如果右子树为空应该不可能出现key值比较大于0的情况，因为一旦大于0就代表需要往右子树找，但是往右子树找的前提是右子树存在结果，如果右子树为空就存在逻辑冲突
                size--;
                return null;
            } else if (!isRed(node.right) && !isRed(node.right.left)) {
                node = moveRedRight(node);
            }
            if (key.compareTo(node.key) == 0) {
                Node minimum = minimum(node.right);
                node.key = minimum.key;
                node.value = minimum.value;
                node.right = deleteMin(node.right);
            } else {
                node.right = remove(node.right, key);
            }
        }
        return fixUp(node);
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
        if (node == null) {
            return true;
        }
        if ((node.color == RED && (isRed(node.left) || isRed(node.right))) || isRed(node.right)) {
            return false;
        }
        return isRB(node.left) && isRB(node.right);
    }

    public boolean isRB() {
        if (root != null) {
            if (root.color != BLACK) {
                return false;
            }
            return isRB(root);
        }
        return true;
    }

    private Node delete(Node h, K key) {
        int cmp = key.compareTo(h.key);
        if (cmp < 0) {
            if (!isRed(h.left) && !isRed(h.left.left)) {
                h = moveRedLeft(h);
            }
            h.left = delete(h.left, key);
        } else if (cmp > 0) {
            if (isRed(h.left)) {
                h = rightRotate(h);
            } else if ((!isRed(h.right) && !isRed(h.right.left))) {
                h = moveRedRight(h);
            }
            h.right = delete(h.right, key);
        } else {
            if (isRed(h.left)) {
                h = rightRotate(h);
            } else if (h.right == null) {
                size--;
                return null;
            } else if ((!isRed(h.right) && !isRed(h.right.left))) {
//                if(!isRed(h) && h != root){                   //只有一种情况，h为root节点时h才会是黑色，其余都是红色
//                    System.out.println(111);
//                }
                h = moveRedRight(h);
            }
            if(key.compareTo(h.key) == 0){
                Node minimum = minimum(h.right);
                h.key = minimum.key;
                h.value = minimum.value;
                h.right = deleteMin(h.right);
            } else {
                h.right = delete(h.right, key);
            }
        }
        return fixUp(h);
    }

    public static void main(String[] args) {

        System.out.println("The Decameron Giovanni Boccaccio");

        ArrayList<String> words = new ArrayList<>();
        if (FileOperation.readFile("The-Decameron-Giovanni-Boccaccio.txt", words)) {
            System.out.println("Total words: " + words.size());

            RBTree<String, Integer> map = new RBTree<>();
            for (String word : words) {
                if (map.contains(word)) {
                    map.set(word, map.get(word) + 1);
                } else {
                    map.put(word, 1);
                    if (!map.isRB()) {
                        throw new RuntimeException("Error");
                    }

                }
            }

            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));
            System.out.println("isRBTree: " + map.isRB());
            RBTree.Node minimum = map.minimum(map.root);
            System.out.println("min:" + minimum.key);
            map.deleteMin();
            System.out.println("min:" + map.minimum(map.root).key);
            System.out.println("isRBTree: " + map.isRB());
            RBTree.Node maximum = map.maximum(map.root);
            System.out.println("max:" + maximum.key);
            map.deleteMax();
            System.out.println("max:" + map.maximum(map.root).key);
            System.out.println("isRBTree: " + map.isRB());
//            int i1 = data.structures.map.size / 2;
//            for (int i = 0; i < i1; i++) {
//                data.structures.map.deleteMin();
//                if (!data.structures.map.isRB()) {
//                    throw new RuntimeException("error");
//                }
//                data.structures.map.deleteMax();
//                if (!data.structures.map.isRB()) {
//                    throw new RuntimeException("error");
//                }
//            }
//            System.out.println(data.structures.map.size);
            for (String word : words) {
                map.remove(word);
                if (!map.isRB()) {
                    throw new RuntimeException("error");
                }
            }
            System.out.println("size : " + map.getSize());
        }


        RBTree<Integer, Object> map = new RBTree<>();
        for (int i = 0; i < 40; i++) {
            map.put(i, null);
        }
        for (int i = 0; i < 20; i++) {
            map.deleteMin();
        }
        System.out.println(map.isRB());
        for (int i = 0; i < 20; i++) {
            map.deleteMax();
        }
        System.out.println(map.isRB());
        System.out.println(map.size);
    }

}
