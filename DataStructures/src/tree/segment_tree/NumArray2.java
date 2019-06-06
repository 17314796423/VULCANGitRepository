package tree.segment_tree;

public class NumArray2 {

    private int[] data;
    private int[] sums;

    public NumArray2(int[] nums) {
        if(nums.length > 0) {
            data = new int[nums.length];
            sums = new int[nums.length + 1];
            sums[0] = 0;
            for (int i = 0; i < nums.length; i++) {
                data[i] = nums[i];
                sums[i + 1] = data[i] + sums[i];
            }
        }
    }
    
    public int sumRange(int i, int j) {
        return sums[j + 1] - sums[i];
    }

    public void update(int index, int val) {
        data[index] = val;
        for(int i = index + 1; i < sums.length ; i ++){
            sums[i] = sums[i - 1] + data[i - 1];
        }
    }

}