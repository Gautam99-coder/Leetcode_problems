class Solution {
    public long minimumCost(String source, String target, char[] original, char[] changed, int[] cost) {
        final long INF = (long) 1e15;
        int A = 26;
        
        long[][] dist = new long[A][A];
        
        // init
        for (int i = 0; i < A; i++) {
            for (int j = 0; j < A; j++) {
                dist[i][j] = (i == j) ? 0 : INF;
            }
        }
        
        // edges
        for (int i = 0; i < cost.length; i++) {
            int u = original[i] - 'a';
            int v = changed[i] - 'a';
            dist[u][v] = Math.min(dist[u][v], cost[i]);
        }
        
        // Floyd–Warshall
        for (int k = 0; k < A; k++) {
            for (int i = 0; i < A; i++) {
                for (int j = 0; j < A; j++) {
                    if (dist[i][k] < INF && dist[k][j] < INF) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
        }
        
        long ans = 0;
        
        for (int i = 0; i < source.length(); i++) {
            int s = source.charAt(i) - 'a';
            int t = target.charAt(i) - 'a';
            
            if (dist[s][t] == INF) return -1;
            ans += dist[s][t];
        }
        
        return ans;
    }
}
