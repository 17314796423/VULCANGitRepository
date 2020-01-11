package data.structures.tree.union_find;

/**
 * p或q代表业务逻辑中的元素编号，parent[p]表示p编号指向的父编号，感觉如果业务中需要使用此结构需要将
 * 所有元素的编号以parent索引从0到parent.length-1排列好，如果设计成非数组，而是element{field... ,parent}
 * 结构应该可以做到入参的是泛型，内部可以用一个Set之类的容器将所有element存起来，避免被回收
 */
public class UnionFindQuickUnion implements UF {

    public UnionFindQuickUnion(int size){
        this.parent = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
        }
    }

    private int[] parent;

    @Override
    public int getSize() {
        return parent.length;
    }

    private int find(int p){
        if(p < 0 || p >= parent.length){
            throw new IllegalArgumentException("p is out of bound.");
        }
        for(;parent[p] != p;) {
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
        parent[pRoot] = qRoot;
    }

}
