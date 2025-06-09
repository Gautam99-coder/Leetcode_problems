class Solution {
    public int findKthNumber(int n, int k) {
        int current = 1;
        k--; // current (which is 1) is the first number in lex order
        
        while (k > 0) {
            long steps = calculateSteps(n, current, current + 1);
            // If the entire subtree rooted at 'current' has fewer or equal elements than k,
            // skip this whole subtree.
            if (steps <= k) {
                current++;
                k -= steps;
            } else {
                // Otherwise, go deeper into the tree (i.e., append a 0)
                current *= 10;
                k--; // count the move to the next level as one step
            }
        }
        
        return current;
    }
    
    // This helper function calculates the number of steps between prefix 'n1' and 'n2'
    // within the bound n. Conceptually, it counts the numbers with prefix 'n1' (i.e. in the subtree).
    private long calculateSteps(int n, long n1, long n2) {
        long steps = 0;
        while (n1 <= n) {
            // The range we can count at the current level is from n1 to either n2 or n+1,
            // whichever is smaller.
            steps += Math.min((long)n + 1, n2) - n1;
            n1 *= 10;
            n2 *= 10;
        }
        return steps;
    }
}
