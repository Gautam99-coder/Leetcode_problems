import java.util.List;

class Solution {
    public long countInterestingSubarrays(List<Integer> nums, int modulo, int k) {
        long ans = 0;
        // Frequency array for prefix mod values.
        // We work with modulo "modulo", so possible remainders are 0 ... modulo-1.
        long[] freq = new long[modulo];
        
        // Initially, the prefix "count" is 0, so remainder is 0.
        freq[0] = 1;
        int prefix = 0;
        
        // Iterate over the numbers.
        for (int num : nums) {
            // Increase prefix count if the element meets the condition.
            if (num % modulo == k) {
                prefix++;
            }
            
            // Compute the current prefix remainder.
            int rem = prefix % modulo;
            // Determine the necessary prefix remainder from an earlier index.
            int needed = (rem - k + modulo) % modulo;
            // Add the number of matching prefixes to the answer.
            ans += freq[needed];
            // Update the frequency of the current prefix remainder.
            freq[rem]++;
        }
        
        return ans;
    }
}
