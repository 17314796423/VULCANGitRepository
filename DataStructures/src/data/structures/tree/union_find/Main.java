package data.structures.tree.union_find;

import java.util.Random;

public class Main {

    private static double testUF(UF uf, int m){

        int size = uf.getSize();
        Random random = new Random();

        long startTime = System.nanoTime();


        for(int i = 0 ; i < m ; i ++){
            int a = random.nextInt(size);
            int b = random.nextInt(size);
            uf.unionElements(a, b);
        }

        for(int i = 0 ; i < m ; i ++){
            int a = random.nextInt(size);
            int b = random.nextInt(size);
            uf.isConnected(a, b);
        }

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000000000.0;
    }

    /**
     * m不是特别大的时候， UnionFindQuickUnion的树高度不高，find操作没有太多时间性能消耗，但是m
     * 值特别大的情况下，树的高度会非常高（如果偏极端情况会让树退化至接近链表），这会使find操作
     * 时间性能消耗极大；另外在UnionFindQuickFind的union操作中，由于Java的JVM对于for循环数组的连
     * 续空间优化做的比较好，速度会非常快，况且它的isConnected和union操作调用的find是O(1)复杂度，
     * 只有union有一次数组遍历操作；而UnionFindQuickUnion中的find操作中，是不断的进行内存空间的随
     * 机访问，时间消耗是相对较慢的，况且在UnionFindQuickUnion中无论是isConnected还是union操作都
     * 会调用两次find
     */
    public static void main(String[] args) {

        // UnionFind1 慢于 UnionFind2
//        int size = 100000;
//        int m = 10000;

        // UnionFind2 慢于 UnionFind1, 但UnionFind3最快
//        int size = 100000;
//        int m = 100000;

        // 理论上 UnionFind3慢于UnionFind4
        int size = 10000000;
        int m = 10000000;

        /*UnionFindQuickFind uf1 = new UnionFindQuickFind(size);
        System.out.println("UnionFind1 : " + testUF(uf1, m) + " s");

        UnionFindQuickUnion uf2 = new UnionFindQuickUnion(size);
        System.out.println("UnionFind2 : " + testUF(uf2, m) + " s");*/

        UnionFindSizeOptimized uf3 = new UnionFindSizeOptimized(size);
        System.out.println("UnionFind3 : " + testUF(uf3, m) + " s");

        UnionFindRankOptimized uf4 = new UnionFindRankOptimized(size);
        System.out.println("UnionFind4 : " + testUF(uf4, m) + " s");

        UnionFindPathCompressed uf5 = new UnionFindPathCompressed(size);
        System.out.println("UnionFind5 : " + testUF(uf5, m) + " s");

        UnionFindPathCompressed2 uf6 = new UnionFindPathCompressed2(size);
        System.out.println("UnionFind6 : " + testUF(uf6, m) + " s");
    }
}
