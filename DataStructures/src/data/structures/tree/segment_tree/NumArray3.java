package data.structures.tree.segment_tree;

public class NumArray3 {

    private SegmentTree<Integer> segmentTree;

    public NumArray3(int[] nums) {
        if(nums.length > 0) {
            Integer[] integers = new Integer[nums.length];
            for (int i = 0; i < nums.length; i++)
                integers[i] = nums[i];
            segmentTree = new SegmentTree<Integer>(integers, (a, b) -> a + b);
        }
    }

    public int sumRange(int i, int j) {
        return segmentTree.query(i, j);
    }

    public void update(int i, int val) {
        segmentTree.update(i, val);
    }
}
