class Solution {
    public long countGood(int[] nums, int k) {
        int n = nums.length;
        // If k is 0, every subarray is good..
        if (k == 0) {
            return (long) n * (n + 1) / 2;
        }
        
        long result = 0;
        long pairs = 0;
        int left = 0;
        // Use a HashMap to record frequencies
        Map<Integer, Integer> freq = new HashMap<>();
        
        for (int right = 0; right < n; right++) {
            // When we add nums[right], new pairs = current frequency of nums[right]
            int count = freq.getOrDefault(nums[right], 0);
            pairs += count;
            freq.put(nums[right], count + 1);
            
            // When the number of pairs in [left, right] is at least k,
            // every expansion from r to the end yields a good subarray.
            while (pairs >= k) {
                result += (n - right);
                
                // Remove nums[left] from the window.
                // Before removing, nums[left] appears freq number of times.
                // Removing one occurrence reduces the pairs count by (freq - 1)
                int freqLeft = freq.get(nums[left]);
                pairs -= (freqLeft - 1);
                freq.put(nums[left], freqLeft - 1);
                left++;
            }
        }
        return result;
    }
}
