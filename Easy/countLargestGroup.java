class Solution {
    public int countLargestGroup(int n) {
        // Since n is at most 10^4, the maximum sum of digits is for 9999: 9+9+9+9 = 36.
        // We allocate an array to store frequencies for possible sums from 1 to 36.
        int[] freq = new int[37]; 
        
        // Count the frequency for each digit sum from 1 to n.
        for (int i = 1; i <= n; i++) {
            int sum = 0;
            int num = i;
            while (num > 0) {
                sum += num % 10;
                num /= 10;
            }
            freq[sum]++;
        }
        
        // Determine the maximum frequency.
        int maxFreq = 0;
        for (int f : freq) {
            if (f > maxFreq) {
                maxFreq = f;
            }
        }
        
        // Count the number of groups that have the maximum frequency.
        int count = 0;
        for (int f : freq) {
            if (f == maxFreq) {
                count++;
            }
        }
        
        return count;
    }
}
