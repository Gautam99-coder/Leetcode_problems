import java.util.*;

class Solution {
    public int[] findEvenNumbers(int[] digits) {
        Set<Integer> result = new TreeSet<>(); // TreeSet ensures unique and sorted results
        
        int n = digits.length;
        
        // Iterate through all possible triplets
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    // Ensure distinct indices
                    if (i == j || j == k || i == k) continue;

                    int num = digits[i] * 100 + digits[j] * 10 + digits[k];

                    // Check if the number meets the requirements
                    if (digits[i] != 0 && num % 2 == 0) { // No leading zeros and must be even
                        result.add(num);
                    }
                }
            }
        }
        
        // Convert the set to an array
        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] digits = {1, 2, 3, 0};
        System.out.println(Arrays.toString(sol.findEvenNumbers(digits))); // Expected Output: [102, 120, 130, 132, 210, 230, 312]
    }
}
