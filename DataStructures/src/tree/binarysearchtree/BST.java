package tree.binarysearchtree;

import array.Array;
import queue.LoopQueue;
import queue.Queue;

public class BST<E extends Comparable<E>> {

    private class Node {
        public E e;
        public Node left;
        public Node right;

        public Node(E e) {
            this.e = e;
            this.left = null;
            this.right = null;
        }

        @Override
        public String toString() {
            return this.e.toString();
        }
    }

    private Node root;
    private int size;

    public BST() {
        this.root = null;
        this.size = 0;
    }

    public int getSize() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void add(E e) {
        root = add(e, root);
    }

    private Node add(E e, Node node) {
        if (node != null) {
            if (e.compareTo(node.e) < 0)
                node.left = add(e, node.left);
            else if (e.compareTo(node.e) > 0)
                node.right = add(e, node.right);
            return node;
        }
        size++;
        return new Node(e);
    }

    public boolean contains(E e) {
        return contains(e, root);
    }

    private boolean contains(E e, Node node) {
        if (node != null) {
            if (e.compareTo(node.e) < 0)
                return contains(e, node.left);
            else if (e.compareTo(node.e) > 0)
                return contains(e, node.right);
            return true;
        }
        return false;
    }

    public void prevTraverse() {
        prevOrder(root);
    }

    private void prevOrder(Node node) {
        if (node != null) {
            System.out.println(node.e);
            prevOrder(node.left);
            prevOrder(node.right);
        }
    }

    public void midTraverse() {
        midTraverse(root);
    }

    private void midTraverse(Node node) {
        if (node != null) {
            midTraverse(node.left);
            System.out.println(node.e);
            midTraverse(node.right);
        }
    }

    public void postTraverse() {
        postTraverse(root);
    }

    private void postTraverse(Node node) {
        if (node != null) {
            postTraverse(node.left);
            postTraverse(node.right);
            System.out.println(node.e);
        }
    }

    public void levelTraverse() {
        if (size != 0) {
            Queue<Node> queue = new LoopQueue<>();
            queue.enqueue(root);
            for (; !queue.isEmpty(); ) {
                Node cur = queue.dequeue();
                System.out.println(cur.e);
                if (cur.left != null) {
                    queue.enqueue(cur.left);
                }
                if (cur.right != null) {
                    queue.enqueue(cur.right);
                }
            }
        }
    }

    public E floor(E e) {
        Array<Object> arr = new Array<>(2);
        arr.add(0, false);
        arr.add(1, new Node(null));
        floor(e, root, arr);
        return ((Node) arr.get(1)).e;
    }

    private void floor(E e, Node node, Array<Object> arr) {
        if (node != null) {
            floor(e, node.left, arr);
            if ((boolean) arr.get(0))
                return;
            if (e.compareTo(node.e) <= 0) {
                arr.set(0, true);
                return;
            } else
                arr.set(1, node);
            floor(e, node.right, arr);
        }
    }


    public E ceil(E e) {
        Array<Object> arr = new Array<>(2);
        arr.add(0, false);
        arr.add(1, new Node(null));
        ceil(e, root, arr);
        return ((Node) arr.get(1)).e;
    }

    private void ceil(E e, Node node, Array<Object> arr) {
        if (node != null) {
            ceil(e, node.left, arr);
            if ((boolean) arr.get(0))
                return;
            if (e.compareTo(node.e) < 0) {
                arr.set(1, node);
                arr.set(0, true);
                return;
            }
            ceil(e, node.right, arr);
        }
    }

    public int rank(E e) {
        if (isEmpty())
            throw new IllegalArgumentException("BST is empty");
        Array<Object> arr = new Array<>(2);
        arr.add(0, false);
        arr.add(1, 1);
        rank(e, root, arr);
        return (int) arr.get(1);
    }

    private void rank(E e, Node node, Array<Object> arr) {
        if (node != null) {
            rank(e, node.left, arr);
            if ((boolean) arr.get(0))
                return;
            if (e.compareTo(node.e) == 0) {
                arr.set(0, true);
                return;
            }
            if (e.compareTo(node.e) < 0 || (int) arr.get(1) == size)
                throw new IllegalArgumentException("element is not exist!");
            arr.set(1, (int) arr.get(1) + 1);
            rank(e, node.right, arr);
        }
    }

    public E select(int rank) {
        if (isEmpty())
            throw new IllegalArgumentException("BST is empty");
        if (rank > size || rank < 1)
            throw new IllegalArgumentException("rank range must between 1 and " + size);
        Array<Object> arr = new Array<>(3);
        arr.add(0, false);
        arr.add(1, 1);
        select(rank, root, arr);
        return ((Node) arr.get(2)).e;
    }

    private void select(int rank, Node node, Array<Object> arr) {
        if (node != null) {
            select(rank, node.left, arr);
            if ((boolean) arr.get(0))
                return;
            if (rank == (int) arr.get(1)) {
                arr.set(0, true);
                arr.add(2, node);
                return;
            }
            arr.set(1, (int) arr.get(1) + 1);
            select(rank, node.right, arr);
        }
    }

    public E minimum() {
        if (size == 0)
            throw new IllegalArgumentException("BST is empty");
        return minimum(root);
    }

    private E minimum(Node node) {
        if (node.left != null)
            return minimum(node.left);
        return node.e;
    }

    public E maximum() {
        if (size == 0)
            throw new IllegalArgumentException("BST is empty");
        return maximum(root);
    }

    private E maximum(Node node) {
        if (node.right != null)
            return maximum(node.right);
        return node.e;
    }

    public E removeMax() {
        if (size == 0)
            throw new IllegalArgumentException("BST is empty");
        return removeMax(root, null);
    }

    private E removeMax(Node node, Node prev) {
        if (node.right != null)
            return removeMax(node.right, node);
        size--;
        Node newNode = node.left;
        node.left = null;
        if (prev == null) {
            root = newNode;
            return node.e;
        }
        prev.right = newNode;
        return node.e;
    }

    /*private E removeMax(Node node, Node prev) {
        if (node.right != null)
            return removeMax(node.right, node);
        size -= getNodeCount(node);
        if (prev == null) {
            root = null;
            return node.e;
        }
        prev.right = null;
        return node.e;
    }*/

    public E removeMin() {
        if (size == 0)
            throw new IllegalArgumentException("BST is empty");
        return removeMin(root, null);
    }

    private E removeMin(Node node, Node prev) {
        if (node.left != null)
            return removeMin(node.left, node);
        size--;
        Node newNode = node.right;
        node.right = null;
        if (prev == null) {
            root = newNode;
            return node.e;
        }
        prev.left = newNode;
        return node.e;
    }

    /*private E removeMin(Node node, Node prev) {
        if (node.left != null)
            return removeMin(node.left, node);
        size -= getNodeCount(node);
        if (prev == null) {
            root = null;
            return node.e;
        }
        prev.left = null;
        return node.e;
    }*/

    public E removeMin2() {
        E ret = minimum();
        root = removeMin2(root);
        return ret;
    }

    private Node removeMin2(Node node) {
        if (node.left == null) {
            Node ret = node.right;
            node.right = null;
            size--;
            return ret;
        }
        node.left = removeMin2(node.left);
        return node;
    }

    public E removeMax2() {
        E ret = maximum();
        root = removeMax2(root);
        return ret;
    }

    private Node removeMax2(Node node) {
        if (node.right == null) {
            Node ret = node.left;
            node.left = null;
            size--;
            return ret;
        }
        node.right = removeMax2(node.right);
        return node;
    }

    public void remove(E e) {
        root = remove(root, e);
    }

    private Node remove(Node node, E e) {
        if (node == null)
            return null;
        if (e.compareTo(node.e) == 0) {
            Node ret;
            if (node.left != null && node.right == null) {
                ret = node.left;
                node.left = null;
                size--;
                return ret;
            } else if (node.left == null && node.right != null) {
                ret = node.right;
                node.right = null;
                size--;
                return ret;
            } else if (node.left != null && node != null) {
                E min = minimum(node.right);
                node.right = removeMin2(node.right);
                node.e = min;
                return node;
            } else {
                size--;
                return null;
            }
        } else if (e.compareTo(node.e) < 0)
            node.left = remove(node.left, e);
        else
            node.right = remove(node.right, e);
        return node;
    }

    private int getNodeCount(Node node) {
        int count = 0;
        count = getNodeCount(node, count);
        return count;
    }

    private int getNodeCount(Node node, int count) {
        if (node != null) {
            count++;
            count = getNodeCount(node.left, count);
            count = getNodeCount(node.right, count);
        }
        return count;
    }

    private String generateNodeString(Node node, int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(generateDepthString(depth));
        sb.append(node == null ? null : node.e);
        return sb.toString();
    }

    private String generateDepthString(int depth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append("--");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        generateBSTString(root, 0, sb);
        return sb.toString();
    }

    private void generateBSTString(Node node, int depth, StringBuilder result) {
        System.out.println(generateNodeString(node, depth));
        if (node != null) {
            generateBSTString(node.left, depth + 1, result);
            generateBSTString(node.right, depth + 1, result);
        }
    }

    public static void main(String[] args) {
        //int[] arr = new int[]{15, 87, 34, 99, 11, 56, 71, 20};
        int[] arr = new int[]{21, 9, 10, 41, 23,52,78,91,-5,-100};
        //int[] arr = new int[]{};
        BST<Integer> integerBST = new BST<>();
        for (int i = 0; i < arr.length; i++) {
            integerBST.add(arr[i]);
        }
        /*System.out.println("prev:");
        integerBST.prevTraverse();*/
        System.out.println("mid:");
        integerBST.midTraverse();
        int num = 9;
        System.out.println(num + "的floor是" + integerBST.floor(num));
        System.out.println(num + "的ceil是" + integerBST.ceil(num));
        System.out.println(num + "的rank是" + integerBST.rank(num));
        System.out.println(num + "的select是" + integerBST.select(num));
        /*System.out.println("post:");
        integerBST.postTraverse();
        System.out.println("level:");
        integerBST.levelTraverse();*/
        /*System.out.println("minimum:" + integerBST.minimum());
        System.out.println("maximum:" + integerBST.maximum());
        integerBST.removeMin2();
        System.out.println(integerBST);
        integerBST.removeMax2();
        System.out.println(integerBST);
        System.out.println("size:" + integerBST.size);
        integerBST.remove(34);
        integerBST.prevTraverse();*/
    }

}