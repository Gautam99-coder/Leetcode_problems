class Solution {
    public int numberOfArrays(int[] differences, int lower, int upper) {
        long prefix = 0;
        long minPrefix = 0, maxPrefix = 0;
        
        // Compute the prefix sums while tracking the minimum and maximum values.
        for (int diff : differences) {
            prefix += diff;
            minPrefix = Math.min(minPrefix, prefix);
            maxPrefix = Math.max(maxPrefix, prefix);
        }
        
        // The initial value x must satisfy:
        // x >= lower - minPrefix and x <= upper - maxPrefix
        long lo = lower - minPrefix;
        long hi = upper - maxPrefix;
        
        // If the interval is invalid, return 0. Otherwise, count integers in [lo, hi].
        if (lo > hi) {
            return 0;
        }
        
        return (int)(hi - lo + 1);
    }
}
