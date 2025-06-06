class Solution {
    public String robotWithString(String s) {
        int n = s.length();
        char[] sc = s.toCharArray();
        char[] minChar = new char[n];
        
        // Precompute the minimum character from index i to the end of s.
        minChar[n - 1] = sc[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            minChar[i] = (char) Math.min(sc[i], minChar[i + 1]);
        }
        
        StringBuilder result = new StringBuilder();
        // Use a stack to simulate the robot's string t.
        Deque<Character> stack = new ArrayDeque<>();
        
        for (int i = 0; i < n; i++) {
            // Operation 1: Remove the first character of s and append it to t.
            stack.push(sc[i]);
            
            // Operation 2: While it is safe to write from t (i.e., the character at the top is 
            // not greater than the smallest character still in s), pop it and append to result.
            while (!stack.isEmpty() && (i == n - 1 || stack.peek() <= minChar[i + 1])) {
                result.append(stack.pop());
            }
        }
        
        return result.toString();
    }
}
