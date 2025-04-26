class Solution {
    public long countSubarrays(int[] nums, int minK, int maxK) {
        long ans = 0;
        int lastInvalid = -1; // last index of an element outside [minK, maxK]
        int lastMin = -1;     // last index where nums[i] == minK
        int lastMax = -1;     // last index where nums[i] == maxK
        
        for (int i = 0; i < nums.length; i++) {
            // If the number at i is outside [minK, maxK], it breaks any valid subarray
            if (nums[i] < minK || nums[i] > maxK) {
                lastInvalid = i;
            }
            if (nums[i] == minK) {
                lastMin = i;
            }
            if (nums[i] == maxK) {
                lastMax = i;
            }
            // The valid subarray must start after lastInvalid and must include both minK and maxK.
            int validStart = Math.min(lastMin, lastMax);
            // Only count if we have encountered both minK and maxK and the valid window doesn't cross an invalid element.
            if (validStart > lastInvalid) {
                ans += (validStart - lastInvalid);
            }
        }
        
        return ans;
    }
}
