import java.util.Arrays;

class Solution {
    /**
     * Finds the meeting node that minimizes the maximum distance from node1 and node2.
     *
     * @param edges An array representing the directed graph. edges[i] is the node
     * that node i points to, or -1 if no outgoing edge.
     * @param node1 The first starting node.
     * @param node2 The second starting node.
     * @return The index of the meeting node, or -1 if no such node exists.
     */
    public int closestMeetingNode(int[] edges, int node1, int node2) {
        int n = edges.length;

        // Calculate distances from node1 to all reachable nodes
        int[] dist1 = new int[n];
        Arrays.fill(dist1, -1); // Initialize distances as unreachable
        calculateDistances(node1, edges, dist1);

        // Calculate distances from node2 to all reachable nodes
        int[] dist2 = new int[n];
        Arrays.fill(dist2, -1); // Initialize distances as unreachable
        calculateDistances(node2, edges, dist2);

        int minMaxDist = Integer.MAX_VALUE;
        int resultNode = -1;

        // Iterate through all nodes to find the meeting node
        for (int i = 0; i < n; i++) {
            // Check if node i is reachable from both node1 and node2
            if (dist1[i] != -1 && dist2[i] != -1) {
                int currentMaxDistFromStarts = Math.max(dist1[i], dist2[i]);

                if (currentMaxDistFromStarts < minMaxDist) {
                    minMaxDist = currentMaxDistFromStarts;
                    resultNode = i;
                } else if (currentMaxDistFromStarts == minMaxDist) {
                    // If the maximum distance is the same, we prefer the node with the smaller index.
                    // Since 'i' iterates from 0 to n-1, if 'resultNode' is already set
                    // for this 'minMaxDist', it means it was set by an index smaller than or equal to 'i'.
                    // Therefore, 'resultNode' will naturally hold the smallest index if it's simply
                    // updated when a strictly smaller 'minMaxDist' is found.
                    // No explicit Math.min(resultNode, i) is needed here due to the loop order.
                    // If resultNode was -1, this means this is the first candidate, which is handled by the previous if.
                    // This condition could also be written as resultNode = Math.min(resultNode, i) if we want to be very explicit,
                    // but the current structure handles it correctly.
                    // Example: if resultNode = 2 (from i=2) with minMaxDist=5.
                    // If current i=3 also has max_dist=5, resultNode remains 2.
                    // If resultNode was set because i=2 has max_dist=5. If i=1 also has max_dist=5, this is not possible
                    // as i=1 would have been processed before i=2.
                }
            }
        }
        return resultNode;
    }

    /**
     * Helper function to calculate distances from a startNode along the paths
     * in the graph. Each node has at most one outgoing edge.
     *
     * @param startNode The node to start traversal from.
     * @param edges     The graph representation.
     * @param distances An array to store the calculated distances. It's pre-filled
     * with -1 (unreachable) and updated by this function.
     */
    private void calculateDistances(int startNode, int[] edges, int[] distances) {
        int currentNode = startNode;
        int currentDist = 0;
        // Traverse the path from startNode
        // The condition distances[currentNode] == -1 handles cycles:
        // if we revisit a node that's already been assigned a distance in this traversal,
        // it means we've entered a cycle along this path, and we stop.
        while (currentNode != -1 && distances[currentNode] == -1) {
            distances[currentNode] = currentDist;
            currentNode = edges[currentNode];
            currentDist++;
        }
    }
}
