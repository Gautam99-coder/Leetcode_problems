class Solution {
    public long (int[] nums, long k) {
        long count = 0;
        long sum = 0; // To store the sum of elements in the current window
        int start = 0;

        for (int end = 0; end < nums.length; end++) {
            // Add the current element to the window sum
            sum += nums[end];

            // Check if the score exceeds k, and adjust `start` if necessary
            while (sum * (end - start + 1) >= k) {
                sum -= nums[start];
                start++;
            }

            // Count all subarrays ending at `end` that satisfy the condition
            count += (end - start + 1);
        }

        return count;
    }
}
