class Solution {
    public String smallestEquivalentString(String s1, String s2, String baseStr) {
        DSU dsu = new DSU();

        for (int i = 0; i < s1.length(); i++) {
            dsu.union(s1.charAt(i) - 'a', s2.charAt(i) - 'a');
        }

        StringBuilder result = new StringBuilder();
        for (char ch : baseStr.toCharArray()) {
            int root = dsu.find(ch - 'a');
            result.append((char) (root + 'a'));
        }

        return result.toString();
    }
}

// Disjoint Set Union with smallest lexicographical character as representative
class DSU {
    int[] parent;

    DSU() {
        parent = new int[26];
        for (int i = 0; i < 26; i++) {
            parent[i] = i;
        }
    }

    int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    void union(int x, int y) {
        int px = find(x);
        int py = find(y);
        if (px == py) return;

        // Always keep the smaller char as representative
        if (px < py) {
            parent[py] = px;
        } else {
            parent[px] = py;
        }
    }
}
