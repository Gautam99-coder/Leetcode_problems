import java.util.*;

class Solution {
    // Helper class to store BFS results (parities and counts)
    private static class BfsResult {
        int[] parities; // Parity of distance from root (node 0)
        int countEven;  // Number of nodes with even parity
        int countOdd;   // Number of nodes with odd parity

        BfsResult(int[] parities, int countEven, int countOdd) {
            this.parities = parities;
            this.countEven = countEven;
            this.countOdd = countOdd;
        }
    }

    // Performs BFS to calculate parities and counts for a given tree
    private BfsResult getParitiesAndCounts(int numNodes, List<List<Integer>> adj) {
        int[] parities = new int[numNodes];
        Arrays.fill(parities, -1); // -1 indicates unvisited
        int countEven = 0;
        int countOdd = 0;

        Queue<Integer> queue = new LinkedList<>();

        // Assuming the tree is connected and nodes are 0-indexed.
        // Start BFS from node 0. Node 0 has distance 0 from itself (even parity).
        if (numNodes > 0) { // Handle case of empty graph if necessary, though constraints say numNodes >= 2
            parities[0] = 0;
            countEven = 1;
            queue.offer(0);
        }


        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v : adj.get(u)) {
                if (parities[v] == -1) { // If neighbor v is unvisited
                    parities[v] = (parities[u] + 1) % 2; // Parity of v is opposite of u
                    if (parities[v] == 0) {
                        countEven++;
                    } else {
                        countOdd++;
                    }
                    queue.offer(v);
                }
            }
        }
        return new BfsResult(parities, countEven, countOdd);
    }

    public int[] maxTargetNodes(int[][] edges1, int[][] edges2) {
        int n = edges1.length + 1; // Number of nodes in the first tree
        int m = edges2.length + 1; // Number of nodes in the second tree

        // Build adjacency list for the first tree
        List<List<Integer>> adj1 = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj1.add(new ArrayList<>());
        }
        for (int[] edge : edges1) {
            adj1.get(edge[0]).add(edge[1]);
            adj1.get(edge[1]).add(edge[0]);
        }

        // Build adjacency list for the second tree
        List<List<Integer>> adj2 = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            adj2.add(new ArrayList<>());
        }
        for (int[] edge : edges2) {
            adj2.get(edge[0]).add(edge[1]);
            adj2.get(edge[1]).add(edge[0]);
        }

        // Get parities and counts for the first tree
        BfsResult result1 = getParitiesAndCounts(n, adj1);
        int[] parities1 = result1.parities;
        int n1Even = result1.countEven;
        int n1Odd = result1.countOdd;

        // Get parities and counts for the second tree
        BfsResult result2 = getParitiesAndCounts(m, adj2);
        int n2Even = result2.countEven;
        int n2Odd = result2.countOdd;
        
        // Determine maximum targets obtainable from the second tree.
        // As n, m >= 2, we can always choose connection points x (in T1) and y (in T2)
        // such that we get either n2Even or n2Odd targets from T2, whichever is larger.
        int targetsFromT2 = Math.max(n2Even, n2Odd);

        int[] answer = new int[n];
        for (int i = 0; i < n; i++) { // For each node i in the first tree (acting as source s)
            int targetsFromT1;
            // If parity of node i (P1[i]) is 0, target nodes in T1 are those with P1[j]=0 (count n1Even)
            // If parity of node i (P1[i]) is 1, target nodes in T1 are those with P1[j]=1 (count n1Odd)
            if (parities1[i] == 0) {
                targetsFromT1 = n1Even;
            } else {
                targetsFromT1 = n1Odd;
            }
            answer[i] = targetsFromT1 + targetsFromT2;
        }

        return answer;
    }
}
