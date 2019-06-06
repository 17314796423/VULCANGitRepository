package tree.segment_tree;

public class Main {

    public static void main(String[] args) {

        Integer[] nums = {-2, 0, 3, -5, 2, -1};

        SegmentTreeWithNode<Integer> segTree = new SegmentTreeWithNode<>(nums,
                (a, b) -> a + b);
        System.out.println(segTree);

        System.out.println(segTree.query(0, 2));
        System.out.println(segTree.query(2, 5));
        System.out.println(segTree.query(0, 5));
        segTree.updateRange(1, 4, new Integer[]{1,4,7,2});
        System.out.println(segTree);

    }
}
