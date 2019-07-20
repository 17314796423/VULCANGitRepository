package data.structures.tree.union_find;

/**
 * 此类的rank不表示深度，只是一个参考值，作为排名，该类的时间复杂度在O(log*n) 如果n<=1，则为0，否则为1+log*(logn)
 */
public class UnionFindPathCompressed implements UF {

    public UnionFindPathCompressed(int size){
        this.parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    private int[] parent;
    private int[] rank;

    @Override
    public int getSize() {
        return parent.length;
    }

    private int find(int p){
        if(p < 0 || p >= parent.length){
            throw new IllegalArgumentException("p is out of bound.");
        }
        for(;parent[p] != p;) {
            //path compress
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }

    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    @Override
    public void unionElements(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if(pRoot == qRoot) {
            return;
        }
        if(rank[pRoot] < rank[qRoot]) {
            parent[pRoot] = qRoot;
        }else if(rank[pRoot] > rank[qRoot]){
            parent[qRoot] = pRoot;
        }else{
            parent[pRoot] = qRoot;
            rank[qRoot] += 1;
        }
    }

}
