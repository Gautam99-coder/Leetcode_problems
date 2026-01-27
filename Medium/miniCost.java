import java.util.*;

class Solution {
    public int minCost(int n, int[][] edges) {
        List<List<int[]>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) adj.add(new ArrayList<>());

        // add original and reversed-with-cost-2*w edges
        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            adj.get(u).add(new int[] { v, w });      // normal edge
            adj.get(v).add(new int[] { u, 2 * w });  // reversed traversal from v to u
        }

        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0L;

        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
        pq.offer(new long[] { 0L, 0 }); // {distance, node}

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            long d = cur[0];
            int u = (int) cur[1];
            if (d != dist[u]) continue;
            if (u == n - 1) return (int) d; // found shortest to target

            for (int[] e : adj.get(u)) {
                int v = e[0], w = e[1];
                long nd = d + w;
                if (nd < dist[v]) {
                    dist[v] = nd;
                    pq.offer(new long[] { nd, v });
                }
            }
        }

        return -1;
    }
}
