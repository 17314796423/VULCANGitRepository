package data.structures.tree.segment_tree;

import data.structures.queue.LoopQueue;
import data.structures.queue.Queue;

public class SegmentTreeWithNode<E> {

    private class Node<E>{

        E value;
        Node<E> leftChild;
        Node<E> rightChild;

        public Node(E value){
            this.value = value;
            this.leftChild = null;
            this.rightChild = null;
        }

        public Node(E value, Node leftChild, Node rightChild){
            this.value = value;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }

        public Node(){
            this.value = null;
            this.leftChild = null;
            this.rightChild = null;
        }

        @Override
        public String toString(){
            return this.value.toString();
        }

    }

    public interface Merger<E>{
        E merge(E leftChild, E rightChild);
    }

    private E[] data;
    private Node<E> root;
    private Merger<E> merger;
    private int treeSize;

    public SegmentTreeWithNode(E[] arr, Merger<E> merger){
        if(merger == null)
            throw new IllegalArgumentException("Arguments are illegal.");
        this.data = (E[]) new Object[arr.length];
        for (int i = 0; i < arr.length; i++)
            data[i] = arr[i];
        this.merger = merger;
        this.root = new Node<E>();
        treeSize = 1;
        buildSegmentTree(root, 0, data.length - 1);
    }

    private void buildSegmentTree(Node<E> node, int begin, int end){
        if(begin == end) {
            node.value = data[begin];
            return;
        }
        int mid = begin + (end - begin) / 2;
        node.leftChild = new Node<E>();
        node.rightChild = new Node<E>();
        treeSize += 2;
        buildSegmentTree(node.leftChild, begin, mid);
        buildSegmentTree(node.rightChild, mid + 1, end);
        node.value = merger.merge(node.leftChild.value, node.rightChild.value);
    }

    public int getSize(){
        return data.length;
    }

    public E get(int index){
        if(index < 0 || index >= data.length)
            throw new IllegalArgumentException("Index is illegal.");
        return data[index];
    }

    public boolean isEmpty(){
        return data.length == 0;
    }

    public E query(int queryL, int queryR){
        if(queryL < 0 || queryL >= data.length || queryR < 0 || queryR >= data.length || queryL > queryR)
            throw new IllegalArgumentException("index is illegal.");
        return query(root, 0, data.length - 1, queryL, queryR);
    }

    private E query(Node<E> node, int begin, int end, int queryL, int queryR){
        if(begin == queryL && end == queryR)
            return node.value;
        int mid = begin + (end - begin) / 2;
        if(queryL > mid)
            return query(node.rightChild, mid + 1, end, queryL, queryR);
        if(queryR < mid + 1)
            return query(node.leftChild, begin, mid, queryL, queryR);
        return merger.merge(
                query(node.leftChild, begin, mid, queryL, mid),
                query(node.rightChild, mid + 1, end, mid + 1, queryR)
        );
    }

    public void update(int index, E val){
        if(index < 0 || index >= data.length)
            throw new IllegalArgumentException("index is illegal.");
        this.data[index] = val;
        update(root, 0, data.length - 1, index, val);
    }

    private void update(Node<E> node, int begin, int end, int index, E val){
        if(begin == end) {
            node.value = val;
            return;
        }
        int mid = begin + (end - begin) / 2;
        if(index <= mid)
            update(node.leftChild, begin, mid, index, val);
        else
            update(node.rightChild, mid + 1, end, index, val);
        node.value = merger.merge(node.leftChild.value, node.rightChild.value);
    }

    public void updateRange(int queryL, int queryR, E[] vals){
        if(queryL < 0 || queryL >= data.length || queryR < 0 || queryR >= data.length || queryL > queryR || vals.length != queryR - queryL + 1)
            throw new IllegalArgumentException("index is illegal.");
        updateRange(root, 0, data.length - 1, queryL, queryR, vals);
    }

    private void updateRange(Node<E> node, int begin, int end, int queryL, int queryR, E[]vals){
        if(begin == end){
            for (int i = queryL, j = 0; i <= queryR; i++, j++)
                if(i == begin) {
                    node.value = vals[j];
                    return;
                }
        }
        int mid = begin + (end - begin) / 2;
        if(queryL > mid)
            updateRange(node.rightChild, mid + 1, end, queryL, queryR, vals);
        else if(queryR < mid + 1)
            updateRange(node.leftChild, begin, mid, queryL, queryR, vals);
        else{
            updateRange(node.leftChild, begin, mid, queryL, queryR, vals);
            updateRange(node.rightChild, mid + 1, end, queryL, queryR, vals);
        }
        node.value = merger.merge(node.leftChild.value, node.rightChild.value);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("SegmentTree: [\n");
        Queue<Node<E>> queue = new LoopQueue<>();
        queue.enqueue(root);
        for (int n = 1, i = 0; !queue.isEmpty(); i++) {
            Node<E> node = queue.dequeue();
            if (node.value != null)
                res.append(String.format("%6s", node.value));
            else
                res.append(String.format("%6s", "null"));
            if(node.leftChild != null)
                queue.enqueue(node.leftChild);
            if (node.rightChild != null)
                queue.enqueue(node.rightChild);
            if(i + 1 == n){
                n = 2 * n + 1;
                res.append("\n");
                continue;
            }
            if(i != treeSize - 1)
                res.append(",");
            else
                res.append("\n");
        }
        res.append("]");
        return res.toString();
    }

}
