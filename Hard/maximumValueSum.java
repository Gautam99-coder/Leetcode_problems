class Solution {
    public long maximumValueSum(int[] nums, int k, int[][] edges) {
        long baseline = 0;
        int n = nums.length;
        
        // We will partition nodes by whether toggling them is beneficial.
        long sumPos = 0;
        int countPos = 0;
        int minPos = Integer.MAX_VALUE;  // smallest positive diff among those > 0
        int maxNeg = Integer.MIN_VALUE;  // maximum (i.e. least negative) diff among those < 0
        boolean hasZero = false;         // flag for diff == 0
        
        for (int x : nums) {
            baseline += x;
            // Compute diff. Note that (x XOR k) equals x + k - 2 * (x & k) so:
            int diff = (x ^ k) - x;  
            // Alternatively: int diff = k - 2 * (x & k);
            if (diff > 0) {
                sumPos += diff;
                countPos++;
                minPos = Math.min(minPos, diff);
            } else if (diff == 0) {
                hasZero = true;
            } else { // diff < 0
                maxNeg = Math.max(maxNeg, diff);
            }
        }
        
        long bestExtra = 0;
        if (countPos % 2 == 0) {
            // We can take all positive nodes.
            bestExtra = sumPos;
        } else {
            // If we have any zero diff node, we can add it to fix parity with no cost.
            if (hasZero) {
                bestExtra = sumPos;
            } else {
                // Option A: drop the smallest positive gain.
                long candidateA = (countPos > 0 ? sumPos - minPos : Long.MIN_VALUE);
                // Option B: add one negative (the one that is least harmful).
                long candidateB = (maxNeg != Integer.MIN_VALUE ? sumPos + maxNeg : Long.MIN_VALUE);
                bestExtra = Math.max(candidateA, candidateB);
            }
        }
        
        // It might be the case that using operations is worse than doing nothing.
        bestExtra = Math.max(bestExtra, 0);
        return baseline + bestExtra;
    }
}
