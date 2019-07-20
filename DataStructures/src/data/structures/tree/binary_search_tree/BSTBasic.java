package data.structures.tree.binary_search_tree;

public class BSTBasic<E extends Comparable<E>> {

    private class Node {
        public E e;
        public Node left, right;

        public Node(E e) {
            this.e = e;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int size;

    public BSTBasic(){
        root = null;
        size = 0;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    // 向二分搜索树中添加新的元素e
    public void add(E e){

        if(root == null){
            root = new Node(e);
            size ++;
        }
        else
            add(root, e);
    }

    // 向以node为根的二分搜索树中插入元素e，递归算法
    private void add(Node node, E e){
        if(e.equals(node.e))
            return;
        else if(e.compareTo(node.e) < 0){
            if (node.left != null) {
                add(node.left, e);
                return;
            }
            node.left = new Node(e);
        }
        else if(e.compareTo(node.e) > 0){
            if (node.right != null) {
                add(node.right, e);
                return;
            }
            node.right = new Node(e);
        }
        size ++;
        return;
    }
}
