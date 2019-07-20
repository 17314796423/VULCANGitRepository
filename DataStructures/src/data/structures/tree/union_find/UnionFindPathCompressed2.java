package data.structures.tree.union_find;

/**
 * 此类的rank不表示深度，只是一个参考值，作为排名,且压缩过程将向上追溯每一个父节点的编号都指向了根节点
 */
public class UnionFindPathCompressed2 implements UF {

    public UnionFindPathCompressed2(int size){
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
        if(p != parent[p]){
            parent[p] = find(parent[p]);
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
