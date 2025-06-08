class Solution {
    public List<Integer> lexicalOrder(int n) {
        List<Integer> ans = new ArrayList<>(n);
        int curr = 1;
        
        for (int i = 0; i < n; i++) {
            ans.add(curr);
            
            // 1) Try diving deeper (append a '0' digit).
            if (curr * 10 <= n) {
                curr *= 10;
            }
            else {
                // 2) If we can't go deeper, try stepping to the next sibling.
                if (curr % 10 != 9 && curr + 1 <= n) {
                    curr++;
                }
                else {
                    // 3) Otherwise, climb up until we can move right.
                    while ( (curr / 10) > 0 && (curr % 10 == 9 || curr + 1 > n) ) {
                        curr /= 10;
                    }
                    curr++;
                }
            }
        }
        
        return ans;
    }
}
