class Solution {
    public int maximumDifference(int[] nums) {
        int min_so_far = nums[0];
        int max_diff = -1;
        for (int j = 1; j < nums.length; j++) {
            if (nums[j] > min_so_far) {
                max_diff = Math.max(max_diff, nums[j] - min_so_far);
            }
            min_so_far = Math.min(min_so_far, nums[j]);
        }
        return max_diff;
    }
}
