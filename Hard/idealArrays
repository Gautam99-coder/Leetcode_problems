import java.util.*;
  
class Solution {
    public int idealArrays(int n, int maxValue) {
        final int mod = 1000000007;
        int m = maxValue;
        
        // Compute an upper bound for the chain length.
        // In a chain of distinct values (with a[i] divides a[i+1]), 
        // each step (if > 1) is at least a doubling.
        // Hence, maximum length ≈ floor(log2(maxValue)) + 1.
        int maxChainLen = 0;
        int temp = m;
        while (temp > 0) {
            maxChainLen++;
            temp /= 2;
        }
        // The distinct chain length we consider cannot exceed n.
        maxChainLen = Math.min(n, maxChainLen);
        
        // f[len] will hold the count of distinct chains of length len.
        // We'll use dynamic programming to compute the number of chains.
        // dp[x] will represent the count of chains (of the current length) ending with number x.
        long[] dp = new long[m + 1];
        // Base case: for chain length 1, every number 1..m is valid.
        Arrays.fill(dp, 1);
        
        // f[1] is just m.
        long[] f = new long[maxChainLen + 1];
        f[1] = m;
        
        // Compute chains for lengths 2 through maxChainLen.
        for (int len = 2; len <= maxChainLen; len++) {
            long[] ndp = new long[m + 1];
            // For every number x that ends the current chain, try to extend it.
            for (int x = 1; x <= m; x++) {
                if (dp[x] > 0) {
                    // Each valid extension: for every multiple y of x (with y > x) within 1..m.
                    for (int y = 2 * x; y <= m; y += x) {
                        ndp[y] = (ndp[y] + dp[x]) % mod;
                    }
                }
            }
            long total = 0;
            for (int x = 1; x <= m; x++) {
                total = (total + ndp[x]) % mod;
            }
            f[len] = total;
            dp = ndp; // move to chains of length len
        }
        
        // For each chain of distinct length L, we "fill" the gaps in an array of length n.
        // The number of ways to intersperse repeated values is C(n - 1, L - 1).
        long ans = 0;
        // l denotes the length of the distinct chain.
        for (int l = 1; l <= maxChainLen; l++) {
            long comb = nCr(n - 1, l - 1, mod);
            ans = (ans + f[l] * comb) % mod;
        }
        return (int) ans;
    }
    
    // Compute combination C(n, r) modulo mod.
    // Here, r is small (r <= maxChainLen, which is O(14) at most).
    private long nCr(int n, int r, int mod) {
        long res = 1;
        for (int i = 1; i <= r; i++) {
            res = res * (n - r + i) % mod;
            res = res * modInverse(i, mod) % mod;
        }
        return res;
    }
    
    // Modular inverse using Fermat's little theorem (mod must be prime).
    private long modInverse(long a, int mod) {
        return modPow(a, mod - 2, mod);
    }
    
    // Fast exponentiation modulo mod.
    private long modPow(long a, int b, int mod) {
        long res = 1;
        a %= mod;
        while (b > 0) {
            if ((b & 1) == 1) {
                res = (res * a) % mod;
            }
            a = (a * a) % mod;
            b >>= 1;
        }
        return res;
    }
}
