package tree.segment_tree;

public class NumArray {

    private SegmentTree<Integer> segTree;

    public NumArray(int[] nums) {
        if(nums.length > 0) {
            Integer[] integers = new Integer[nums.length];
            for (int i = 0; i < nums.length; i++)
                integers[i] = nums[i];
            segTree = new SegmentTree<Integer>(integers, (a, b) -> a + b);
        }
    }
    
    public int sumRange(int i, int j) {
        return segTree.query(i, j);
    }

}