class Solution {
    private static final long MOD = 1000000007;

    public int countGoodNumbers(long n) {
        long evenCount = (n + 1) / 2;
        long oddCount = n / 2;
        
        long evenWays = powMod(5, evenCount, MOD);
        long oddWays = powMod(4, oddCount, MOD);
        
        return (int) ((evenWays * oddWays) % MOD);
    }
    
    private long powMod(long base, long exponent, long mod) {
        long result = 1;
        base %= mod;
        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = (result * base) % mod;
            }
            base = (base * base) % mod;
            exponent >>= 1;
        }
        return result;
    }
}
//
