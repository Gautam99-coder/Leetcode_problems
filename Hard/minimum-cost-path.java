import java.util.*;

class Solution {
    public int minCost(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        final int INF = 1_000_000_000;

        int mn = m * n;
        int[] val = new int[mn];
        int[] xi = new int[mn], yj = new int[mn];

        int id = 0;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                val[id] = grid[i][j];
                xi[id] = i;
                yj[id] = j;
                id++;
            }

        Integer[] order = new Integer[mn];
        for (int i = 0; i < mn; i++) order[i] = i;
        Arrays.sort(order, Comparator.comparingInt(a -> val[a]));

        // groupStart[p] = first index of the block of equal values that includes p
        int[] groupStart = new int[mn];
        int start = 0;
        for (int p = 0; p < mn; p++) {
            if (p == 0) {
                start = 0;
            } else {
                if (val[order[p]] != val[order[p - 1]]) start = p;
            }
            groupStart[p] = start;
        }

        int[][] dpPrev = new int[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                dpPrev[i][j] = INF;

        dpPrev[0][0] = 0;

        // dp with no teleport (t = 0)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) continue;
                int best = INF;
                if (i > 0) best = Math.min(best, dpPrev[i - 1][j] + grid[i][j]);
                if (j > 0) best = Math.min(best, dpPrev[i][j - 1] + grid[i][j]);
                dpPrev[i][j] = best;
            }
        }

        // iterate using up to k teleports
        for (int t = 1; t <= k; t++) {
            int[] prevVals = new int[mn];
            for (int p = 0; p < mn; p++) {
                int o = order[p];
                prevVals[p] = dpPrev[xi[o]][yj[o]];
            }

            // suffixMin[p] = min(prevVals[p..mn-1])
            int[] suffixMin = new int[mn];
            int curMin = INF;
            for (int p = mn - 1; p >= 0; p--) {
                curMin = Math.min(curMin, prevVals[p]);
                suffixMin[p] = curMin;
            }

            int[][] base = new int[m][n];
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    base[i][j] = INF;

            // For each cell (i,j) at position p in sorted order,
            // best teleport-to cost is suffixMin[groupStart[p]] (includes entire equal-value block)
            for (int p = 0; p < mn; p++) {
                int o = order[p];
                int i = xi[o], j = yj[o];
                int teleportBest = suffixMin[groupStart[p]];
                base[i][j] = Math.min(dpPrev[i][j], teleportBest);
            }

            int[][] dpCurr = new int[m][n];
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    dpCurr[i][j] = INF;

            // propagate right/down from base
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    dpCurr[i][j] = base[i][j];
                    if (i > 0)
                        dpCurr[i][j] = Math.min(dpCurr[i][j], dpCurr[i - 1][j] + grid[i][j]);
                    if (j > 0)
                        dpCurr[i][j] = Math.min(dpCurr[i][j], dpCurr[i][j - 1] + grid[i][j]);
                }
            }

            dpPrev = dpCurr;
        }

        return dpPrev[m - 1][n - 1];
    }
}
