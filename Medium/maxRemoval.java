import java.util.*;

class Solution {
    public int maxRemoval(int[] nums, int[][] queries) {
        int n = nums.length;
        int m = queries.length;
        
        // Step 1. Check if even all queries cover every demand.
        int[] diffTotal = new int[n + 1];
        for (int[] q : queries) {
            int l = q[0], r = q[1];
            diffTotal[l]++;
            if (r + 1 < diffTotal.length) {
                diffTotal[r + 1]--;
            }
        }
        int cum = 0;
        for (int i = 0; i < n; i++) {
            cum += diffTotal[i];
            if (cum < nums[i]) {
                return -1; // Even all queries cannot cover index i.
            }
        }
        
        // Step 2. We now want to select a minimal subset of queries that for each index i provides at least nums[i] coverage.
        // Sort queries by their starting index.
        Arrays.sort(queries, (a, b) -> Integer.compare(a[0], b[0]));
        
        // We'll use a max-heap (priority queue) to choose, among available queries (those with start <= i),
        // the ones that extend farthest (largest r).
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        int chosen = 0; // Count of chosen queries
        int j = 0;     // Pointer for sorted queries
        
        // Use a difference array "add" to record the effect of chosen queries.
        int[] add = new int[n + 1];
        int currentCoverage = 0; // Running sum of applied coverage
        
        // Process each index from 0 to n-1:
        for (int i = 0; i < n; i++) {
            // Update current coverage using the difference array.
            currentCoverage += add[i];
            
            // Before processing index i, add all queries that start at or before i.
            while (j < m && queries[j][0] <= i) {
                // We only need the ending index r for our decision.
                maxHeap.offer(queries[j][1]);
                j++;
            }
            // Remove queries from the heap that no longer cover index i.
            while (!maxHeap.isEmpty() && maxHeap.peek() < i) {
                maxHeap.poll();
            }
            
            // Determine if index i is already sufficiently covered.
            int deficit = nums[i] - currentCoverage;
            // If there is a deficit, pick queries from our candidates.
            while (deficit > 0) {
                if (maxHeap.isEmpty()) { 
                    // Shouldn't happen because we confirmed full coverage is possible.
                    return -1;
                }
                // Choose the query that extends farthest, so it helps with future indices.
                int r = maxHeap.poll();
                chosen++;
                // Apply this query's effect: add 1 from index i through r.
                currentCoverage++; // Immediately, index i gains +1.
                add[r + 1] -= 1;   // Effect will end right after r.
                deficit--;
            }
        }
        // The maximum number of queries that can be removed is total queries minus the queries we had to choose.
        return m - chosen;
    }
}
