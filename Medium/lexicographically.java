class Solution {
    // A helper class to store a character and its index.
    class Node {
        char c;
        int idx;
        public Node(char c, int idx) {
            this.c = c;
            this.idx = idx;
        }
    }
    
    Node[] seg;
    char[] arr;
    
    // Combine two nodes according to our rules:
    // choose the one with the smaller character; if equal, choose the one with the larger index.
    private Node combine(Node left, Node right) {
        if (left.c < right.c)
            return left;
        else if (left.c > right.c)
            return right;
        else // equal letter: choose with max index (rightmost occurrence)
            return (left.idx > right.idx ? left : right);
    }
    
    // Build the segment tree for the range [l, r]
    private void build(int pos, int l, int r) {
        if (l == r) {
            seg[pos] = new Node(arr[l], l);
            return;
        }
        int mid = (l + r) / 2;
        build(pos * 2, l, mid);
        build(pos * 2 + 1, mid + 1, r);
        seg[pos] = combine(seg[pos * 2], seg[pos * 2 + 1]);
    }
    
    // Update the value at index 'index' to newVal and update the segment tree accordingly.
    private void update(int pos, int l, int r, int index, char newVal) {
        if (l == r) {
            seg[pos] = new Node(newVal, l);
            return;
        }
        int mid = (l + r) / 2;
        if (index <= mid)
            update(pos * 2, l, mid, index, newVal);
        else
            update(pos * 2 + 1, mid + 1, r, index, newVal);
        seg[pos] = combine(seg[pos * 2], seg[pos * 2 + 1]);
    }
    
    // Query the segment tree for the combined node in the range [ql, qr]
    private Node query(int pos, int l, int r, int ql, int qr) {
        if (ql > r || qr < l)
            return new Node('{', -1); // Sentinel: '{' is after 'z'
        if (ql <= l && r <= qr)
            return seg[pos];
        int mid = (l + r) / 2;
        Node left = query(pos * 2, l, mid, ql, qr);
        Node right = query(pos * 2 + 1, mid + 1, r, ql, qr);
        return combine(left, right);
    }
    
    public String clearStars(String s) {
        int n = s.length();
        // Convert s to a mutable char array.
        arr = s.toCharArray();
        // We treat stars as having a “high” value so that they are never chosen 
        // as a candidate letter to remove.
        for (int i = 0; i < n; i++) {
            if (arr[i] == '*') {
                arr[i] = '{';
            }
        }
        
        // Build the segment tree.
        seg = new Node[4 * n];
        build(1, 0, n - 1);
        
        // Get in order all star positions as they appear in the original string.
        // (We refer to s.charAt(i) even though arr has been modified.)
        java.util.ArrayList<Integer> stars = new java.util.ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '*')
                stars.add(i);
        }
        
        // For each star (processed in order) perform the deletion:
        // Query the range [0, starIndex-1] for the smallest letter (tie-breaker: rightmost),
        // then mark that letter and the star as removed (by updating them to '{').
        for (int starIdx : stars) {
            if (starIdx > 0) {
                Node candidate = query(1, 0, n - 1, 0, starIdx - 1);
                // According to the problem’s guarantee, candidate.c will not be the sentinel.
                if (candidate.c != '{') {
                    update(1, 0, n - 1, candidate.idx, '{');
                    arr[candidate.idx] = '{';
                }
            }
            update(1, 0, n - 1, starIdx, '{');
            arr[starIdx] = '{';
        }
        
        // Rebuild the final string from positions that still hold letters (i.e. 'a'-'z').
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (arr[i] >= 'a' && arr[i] <= 'z')
                sb.append(arr[i]);
        }
        return sb.toString();
    }
}
