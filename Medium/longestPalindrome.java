import java.util.HashMap;
import java.util.Map;

class Solution {
    public int longestPalindrome(String[] words) {
        Map<String, Integer> freq = new HashMap<>();
        
        // Count frequency of each word
        for (String word : words) {
            freq.put(word, freq.getOrDefault(word, 0) + 1);
        }
        
        int length = 0;
        boolean hasCenter = false;
        
        // Iterate through each unique word in the map
        for (Map.Entry<String, Integer> entry : freq.entrySet()) {
            String word = entry.getKey();
            int count = entry.getValue();
            
            // If the word is a palindromic word, e.g., "aa", "gg", etc.
            if (word.charAt(0) == word.charAt(1)) {
                // Use them in pairs, each pair adds 4 to the length:
                length += (count / 2) * 4;
                // If there is an extra word available then it can be placed in the center
                if (count % 2 == 1) {
                    hasCenter = true;
                }
            } else {
                // For non-palindromic words, only process each pair once.
                // Getting its reversed counterpart.
                String rev = "" + word.charAt(1) + word.charAt(0);
                if (freq.containsKey(rev)) {
                    // To avoid counting the pair twice, handle only if word is lexicographically smaller than its reverse
                    if (word.compareTo(rev) < 0) {
                        int pairs = Math.min(count, freq.get(rev));
                        length += pairs * 4;
                    }
                }
            }
        }
        
        // If we have at least one palindromic word left that can be placed in the center, add extra 2.
        if (hasCenter) {
            length += 2;
        }
        
        return length;
    }
}
