class Solution {
    public int maxDifference(String s, int k) {
        int n = s.length();
        if (k > n) return -1;

        // Build prefix counts for digits '0'..'4'
        // pref[c][i]: count of char ('0'+c) in s[0..i-1]
        int[][] pref = new int[5][n+1];
        for (int i = 1; i <= n; i++) {
            int d = s.charAt(i-1) - '0';
            for (int c = 0; c < 5; c++) {
                pref[c][i] = pref[c][i-1] + (c == d ? 1 : 0);
            }
        }

        int overallMax = Integer.MIN_VALUE;
        final int INF = Integer.MAX_VALUE / 2;

        // For each ordered pair (a, b), a != b, digits 0..4
        for (int a = 0; a < 5; a++) {
            for (int b = 0; b < 5; b++) {
                if (a == b) continue;

                // We will build 4 segment trees, one for each parity state (pa, pb) in {0,1}x{0,1}.
                // Map (pa, pb) to index 0..3: idx = pa*2 + pb
                SegmentTreeMin[] segs = new SegmentTreeMin[4];
                for (int st = 0; st < 4; st++) {
                    segs[st] = new SegmentTreeMin(n+1, INF);
                }

                // Helper to get parity and D at prefix index i:
                // P_a[i] = pref[a][i] % 2, P_b[i] = pref[b][i] % 2, D[i] = pref[a][i] - pref[b][i].

                // We'll iterate t2 from 1..n. Whenever (t2 - k) >= 0, we add index i = t2 - k.
                for (int t2 = 1; t2 <= n; t2++) {
                    int iAdd = t2 - k;
                    if (iAdd >= 0) {
                        // Add prefix index iAdd into segment tree
                        int pa_i = pref[a][iAdd] & 1;
                        int pb_i = pref[b][iAdd] & 1;
                        int state = (pa_i << 1) | pb_i; // pa_i*2 + pb_i
                        int db_i = pref[a][iAdd] - pref[b][iAdd];
                        // Update at key = pref[b][iAdd], value = D[iAdd]
                        segs[state].update(pref[b][iAdd], db_i);
                    }

                    // Now consider t2 as end+1 of substring => substring end index j = t2-1
                    // Compute prefix at t2
                    int pa_t = pref[a][t2] & 1;
                    int pb_t = pref[b][t2] & 1;
                    int D_t = pref[a][t2] - pref[b][t2];

                    // Desired prefix state: P_a[i] = 1 XOR pa_t, P_b[i] = pb_t
                    int desiredPa = pa_t ^ 1;
                    int desiredPb = pb_t;
                    int stateWant = (desiredPa << 1) | desiredPb;
                    // Need pref[b][i] <= pref[b][t2] - 2
                    int limit = pref[b][t2] - 2;
                    if (limit >= 0) {
                        int minDi = segs[stateWant].queryMin(0, limit);
                        if (minDi < INF/2) {
                            int diff = D_t - minDi;
                            if (diff > overallMax) {
                                overallMax = diff;
                            }
                        }
                    }
                }
            }
        }

        return (overallMax == Integer.MIN_VALUE ? -1 : overallMax);
    }

    // Segment tree supporting point updates (taking min) and range min query over [l..r].
    static class SegmentTreeMin {
        int size;
        int[] tree;
        final int INF;
        SegmentTreeMin(int n, int inf) {
            // build size = next power of two ≥ n
            INF = inf;
            size = 1;
            while (size < n) size <<= 1;
            tree = new int[2 * size];
            Arrays.fill(tree, INF);
        }
        // point update: set tree[pos] = min(tree[pos], value)
        void update(int pos, int value) {
            int idx = pos + size;
            if (value < tree[idx]) {
                tree[idx] = value;
                idx >>= 1;
                while (idx > 0) {
                    int newVal = Math.min(tree[2*idx], tree[2*idx+1]);
                    if (newVal == tree[idx]) break;
                    tree[idx] = newVal;
                    idx >>= 1;
                }
            }
        }
        // range min query on [l..r] inclusive (0-based keys)
        int queryMin(int l, int r) {
            if (l > r) return INF;
            int res = INF;
            int left = l + size, right = r + size;
            while (left <= right) {
                if ((left & 1) == 1) {
                    res = Math.min(res, tree[left]);
                    left++;
                }
                if ((right & 1) == 0) {
                    res = Math.min(res, tree[right]);
                    right--;
                }
                left >>= 1;
                right >>= 1;
            }
            return res;
        }
    }
}
