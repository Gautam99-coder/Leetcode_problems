class Solution {
    public boolean threeConsecutiveOdds(int[] arr) {
        int count = 0;
        
        for (int num : arr) {
            if (num % 2 == 1) {  // Check if the number is odd
                count++;
                if (count == 3) return true; // Found three consecutive odds
            } else {
                count = 0; // Reset count on encountering an even number
            }
        }
        
        return false; // No three consecutive odds found
    }
}
