package tree.segmenttree;

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
