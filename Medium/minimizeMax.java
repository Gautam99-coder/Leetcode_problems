class Solution {
    public int minimizeMax(int[] nums, int p) {
        int n = nums.length;
        if (p == 0) return 0;
        
        // 1) sort the array so that we can pair “close” elements adjacently
        Arrays.sort(nums);
        
        // 2) binary‐search the smallest maximum allowed difference D
        int lo = 0, hi = nums[n-1] - nums[0];
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (canForm(nums, p, mid)) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }
    
    // Greedy check: can we form at least p disjoint pairs
    // each with diff <= maxDiff?
    private boolean canForm(int[] nums, int p, int maxDiff) {
        int cnt = 0;
        int i = 0, n = nums.length;
        while (i + 1 < n) {
            // if this adjacent pair is OK, take it and skip both
            if (nums[i+1] - nums[i] <= maxDiff) {
                cnt++;
                i += 2;
                if (cnt >= p) return true;
            } else {
                // otherwise skip only the left one and try from the next
                i++;
            }
        }
        return false;
    }
}
