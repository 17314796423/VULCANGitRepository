package algorithms.graph.wt.mst.prim;

import algorithms.graph.wt.Edge;
import algorithms.graph.wt.WeightedGraph;
import algorithms.sort.heap.index.BinaryMinHeap;

import java.util.Vector;

// 使用Prim算法求图的最小生成树
public class PrimMST<Weight extends Number & Comparable> {

    private WeightedGraph<Weight> G;    // 图的引用
    private BinaryMinHeap<Edge<Weight>> ipq;   // 最小索引堆, 算法辅助数据结构
    private boolean[] marked;           // 标记数组, 在算法运行过程中标记节点i是否被访问
    private Vector<Edge<Weight>> mst;   // 最小生成树所包含的所有边
    private Number mstWeight;           // 最小生成树的权值

    // 构造函数, 使用Prim算法求图的最小生成树
    public PrimMST(WeightedGraph<Weight> graph){

        // 算法初始化
        G = graph;
        ipq = new BinaryMinHeap<>(G.E());
        marked = new boolean[G.V()];
        mst = new Vector<Edge<Weight>>();

        // Lazy Prim
        visit(0);
        while( !ipq.isEmpty() ){
            // 使用最小堆找出已经访问的边中权值最小的边
            int index = ipq.extractMinIndex();
            Edge<Weight> min_item = ipq.getItem(index);
            // 否则, 这条边则应该存在在最小生成树中
            mst.add(min_item);
            // 访问和这条边连接的还没有被访问过的节点
            visit(index);
        }

        // 计算最小生成树的权值

        mstWeight = mst.elementAt(0).wt();
        for( int i = 1 ; i < mst.size() ; i ++ )
            mstWeight = mstWeight.doubleValue() + mst.elementAt(i).wt().doubleValue();
    }

    // 访问节点v
    private void visit(int v){

        assert !marked[v];
        marked[v] = true;

        // 将和节点v相连接的所有未访问的边放入最小堆中
        for( Edge<Weight> e : G.adj(v) )
            if( !marked[e.other(v)] ) {
                Edge<Weight> curr_index_item = ipq.getItem(e.other(v));
                if (curr_index_item == null) {
                    ipq.add(e.other(v), e);
                } else if(e.wt().compareTo(curr_index_item.wt()) < 0) {
                    ipq.change(e.other(v), e);
                }
            }
    }

    // 返回最小生成树的所有边
    Vector<Edge<Weight>> mstEdges(){
        return mst;
    };

    // 返回最小生成树的权值
    Number result(){
        return mstWeight;
    };
}
