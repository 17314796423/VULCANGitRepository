package tree.segment_tree;

public class SegmentTree<E> {

    private E[] data;
    private E[] segmentTree;
    private Merger<E> merger;

    public SegmentTree(E[] arr, Merger<E> merger){
        int k = 0;
        this.data = (E[]) new Object[arr.length];
        for (int i = 0; i < arr.length; i++)
            this.data[i] = arr[i];
        for(int i = 1;i <= arr.length;i = 2 * i)
            k = i;
        if(k == arr.length)
            this.segmentTree = (E[]) new Object[2 * k - 1];
        else
            this.segmentTree = (E[]) new Object[4 * k - 1];
        this.merger = merger;
        buildSegmentTree(0, 0, data.length - 1);
    }

    private void buildSegmentTree(int treeIndex, int begin, int end){
        if(begin == end){
            segmentTree[treeIndex] = data[begin];
            return;
        }
        int leftChildTreeIndex = leftChild(treeIndex);
        int rightChildTreeIndex = rightChild(treeIndex);
        int mid = begin + (end - begin) / 2;
        buildSegmentTree(leftChildTreeIndex, begin, mid);
        buildSegmentTree(rightChildTreeIndex, mid + 1, end);
        segmentTree[treeIndex] = merger.merge(segmentTree[leftChildTreeIndex], segmentTree[rightChildTreeIndex]);
    }

    public int size(){
        return data.length;
    }

    public E get(int index){
        if(index < 0 || index >= data.length)
            throw new IllegalArgumentException("Index is illegal.");
        return data[index];
    }

    // 返回完全二叉树的数组表示中，一个索引所表示的元素的左孩子节点的索引
    private int leftChild(int index){
        return 2*index + 1;
    }

    // 返回完全二叉树的数组表示中，一个索引所表示的元素的右孩子节点的索引
    private int rightChild(int index){
        return 2*index + 2;
    }

    public boolean isEmpty(){
        return data.length == 0;
    }

    public E query(int queryL, int queryR){
        if(queryL < 0 || queryL >= data.length || queryR < 0 || queryR >= data.length || queryL > queryR)
            throw new IllegalArgumentException("index is illegal.");
        return query(0, 0, data.length - 1, queryL, queryR);
    }

    private E query(int treeIndex, int begin, int end, int queryL, int queryR){
        if(begin == queryL && end == queryR)
            return segmentTree[treeIndex];
        int leftChildTreeIndex = leftChild(treeIndex);
        int rightChildTreeIndex = rightChild(treeIndex);
        int mid = begin + (end - begin) / 2;
        if(queryL > mid)
            return query(rightChildTreeIndex, mid + 1, end, queryL, queryR);
        if(queryR < mid + 1)
            return query(leftChildTreeIndex, begin, mid, queryL, queryR);
        return merger.merge(
                query(leftChildTreeIndex, begin, mid, queryL, mid),
                query(rightChildTreeIndex, mid + 1, end, mid + 1, queryR)
        );
    }

    public void updateRange(int queryL, int queryR, E[] vals){
        if(queryL < 0 || queryL >= data.length || queryR < 0 || queryR >= data.length || queryL > queryR || vals.length != queryR - queryL + 1)
            throw new IllegalArgumentException("index is illegal.");
        updateRange(0, 0, data.length - 1, queryL, queryR, vals);
    }

    private void updateRange(int treeIndex, int begin, int end, int queryL, int queryR, E[]vals){
        if(begin == end){
            for (int i = queryL, j = 0; i <= queryR; i++, j++)
                if(i == begin) {
                    segmentTree[treeIndex] = vals[j];
                    return;
                }
        }
        int leftChildTreeIndex = leftChild(treeIndex);
        int rightChildTreeIndex = rightChild(treeIndex);
        int mid = begin + (end - begin) / 2;
        if(queryL > mid)
            updateRange(rightChildTreeIndex, mid + 1, end, queryL, queryR, vals);
        else if(queryR < mid + 1)
            updateRange(leftChildTreeIndex, begin, mid, queryL, queryR, vals);
        else{
            updateRange(leftChildTreeIndex, begin, mid, queryL, queryR, vals);
            updateRange(rightChildTreeIndex, mid + 1, end, queryL, queryR, vals);
        }
        segmentTree[treeIndex] = merger.merge(segmentTree[leftChildTreeIndex], segmentTree[rightChildTreeIndex]);
    }

    public void update(int index, E val){
        if(index < 0 || index >= data.length)
            throw new IllegalArgumentException("index is illegal.");
        this.data[index] = val;
        update(0, 0, data.length - 1, index, val);
    }

    private void update(int treeIndex, int begin, int end, int index, E val){
        if(begin == end) {
            segmentTree[treeIndex] = val;
            return;
        }
        int leftChildTreeIndex = leftChild(treeIndex);
        int rightChildTreeIndex = rightChild(treeIndex);
        int mid = begin + (end - begin) / 2;
        if(index <= mid)
            update(leftChildTreeIndex, begin, mid, index, val);
        else
            update(rightChildTreeIndex, mid + 1, end, index, val);
        segmentTree[treeIndex] = merger.merge(segmentTree[leftChildTreeIndex], segmentTree[rightChildTreeIndex]);
    }

    @Override
    public String toString(){
        boolean flag = true;
        StringBuilder sb = new StringBuilder("SegmentTree: [\n");
        for (int i = 0, n = 1; i < segmentTree.length; i++) {
            if(segmentTree[i] != null)
                sb.append(String.format("%6s", segmentTree[i]));
            else
                sb.append(String.format("%6s", "null"));
            if(i + 1 == n){
                n = 2 * n + 1;
                sb.append("\n");
                continue;
            }
            if(i != segmentTree.length - 1)
                sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

}
