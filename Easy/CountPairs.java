class Solution {
    public int countPairs(int[] nums, int k) {
        int n = nums.length;
        int count = 0;
        
        // Iterate over all possible pairs (i, j) with i < j
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // Check both conditions:
                // 1. nums[i] == nums[j]
                // 2. (i * j) % k == 0
                if (nums[i] == nums[j] && (i * j) % k == 0) {
                    count++;
                }
            }
        }
        
        return count;
    }
}
