class Solution {
    public String countAndSay(int n) {
        // Base case: the first element is "1".
        String s = "1";
        // Generate the sequence iteratively from 2 up to n
        for (int i = 2; i <= n; i++) {
            s = getNext(s);
        }
        return s;
    }
    
    // Helper method to generate the next term in the sequence
    private String getNext(String s) {
        StringBuilder sb = new StringBuilder();
        int count = 1; // Count for the current digit
        
        for (int i = 0; i < s.length(); i++) {
            // If the next character is the same, increase the count
            if (i + 1 < s.length() && s.charAt(i) == s.charAt(i + 1)) {
                count++;
            } else {
                // When the current run ends, append count and the digit
                sb.append(count);
                sb.append(s.charAt(i));
                count = 1; // Reset count for the next digit
            }
        }
        return sb.toString();
    }
}
