class Solution {
    public int lengthAfterTransformations(String s, int t) {
        final int MOD = 1_000_000_007;
        // dp[i]: number of characters produced by letter corresponding to index i after current number of transformations.
        long[] dp = new long[26];
        // Base: for t = 0, every letter contributes exactly one character.
        for (int i = 0; i < 26; i++) {
            dp[i] = 1;
        }
        
        // Apply transformations one by one.
        for (int step = 1; step <= t; step++) {
            long[] newdp = new long[26];
            // Letters 'a' to 'y': each becomes the next letter.
            for (int i = 0; i < 25; i++) {
                newdp[i] = dp[i + 1] % MOD;
            }
            // Letter 'z' becomes "ab": so add results for 'a' and 'b'
            newdp[25] = (dp[0] + dp[1]) % MOD;
            dp = newdp;
        }
        
        // Sum contributions for each character in the original string.
        long result = 0;
        for (char c : s.toCharArray()) {
            result = (result + dp[c - 'a']) % MOD;
        }
        
        return (int) result;
    }
}
