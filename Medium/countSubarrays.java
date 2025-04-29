class Solution {
    public long countSubarrays(int[] nums, int k) {
        int n = nums.length;
        
        // Compute the global maximum M.
        int M = 0;
        for (int num : nums) {
            M = Math.max(M, num);
        }
        
        // Total number of subarrays.
        long total = (long) n * (n + 1) / 2;
        // Count the subarrays that have at most (k-1) occurrences of M.
        long atMost = countAtMost(nums, M, k - 1);
        // The answer is total subarrays minus those with fewer than k occurrences.
        return total - atMost;
    }
    
    // Helper to count subarrays with at most x occurrences of M.
    private long countAtMost(int[] nums, int M, int x) {
        long count = 0;
        int left = 0;
        int freq = 0; // count of M in the current window
        
        for (int right = 0; right < nums.length; right++) {
            // Increase frequency if we see M.
            if (nums[right] == M) {
                freq++;
            }
            // Shrink the window until it contains at most x occurrences
            while (freq > x && left <= right) {
                if (nums[left] == M) {
                    freq--;
                }
                left++;
            }
            // All subarrays ending at right with start index from left to right are valid.
            count += (right - left + 1);
        }
        return count;
    }
}
