class Solution {
    public int lengthAfterTransformations(String s, int t, List<Integer> nums) {
        final int MOD = 1_000_000_007;
        int n = 26;
        long[][] M = new long[n][n];

        // Build the transformation matrix M.
        // For each letter i, it will produce letters at positions (i+1) % 26, (i+2) % 26, ... , (i+nums[i]) % 26.
        for (int i = 0; i < n; i++) {
            int steps = nums.get(i);
            for (int k = 1; k <= steps; k++) {
                int j = (i + k) % n;
                M[i][j] = (M[i][j] + 1) % MOD;
            }
        }

        // Compute matrix exponentiation: M^t mod MOD.
        long[][] Mt = matrixPower(M, t, MOD);

        // Compute the frequency vector for the string s.
        long[] freq = new long[n];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }
        
        // Instead of doing full vector-matrix multiplication, note that the contribution from letter i
        // is its count multiplied by the sum of row i in Mt.
        long total = 0;
        for (int i = 0; i < n; i++) {
            long rowSum = 0;
            for (int j = 0; j < n; j++) {
                rowSum = (rowSum + Mt[i][j]) % MOD;
            }
            total = (total + freq[i] * rowSum) % MOD;
        }
        return (int) total;
    }

    // Fast exponentiation of a matrix using binary exponentiation.
    private long[][] matrixPower(long[][] base, int exp, int mod) {
        int n = base.length;
        // Initialize result as the identity matrix.
        long[][] result = new long[n][n];
        for (int i = 0; i < n; i++) {
            result[i][i] = 1;
        }
        long[][] cur = base;
        while(exp > 0) {
            if ((exp & 1) == 1) {
                result = matrixMultiply(result, cur, mod);
            }
            cur = matrixMultiply(cur, cur, mod);
            exp >>= 1;
        }
        return result;
    }
    
    // Multiply two matrices A and B modulo mod.
    private long[][] matrixMultiply(long[][] A, long[][] B, int mod) {
        int n = A.length;
        long[][] C = new long[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                long sum = 0;
                for (int k = 0; k < n; k++) {
                    sum = (sum + A[i][k] * B[k][j]) % mod;
                }
                C[i][j] = sum;
            }
        }
        return C;
    }
}
