class Solution {
    public int maxDifference(String s) {
        // 1) Build frequency table
        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        /// 2) Find the largest odd freq and smallest even freq (only for letters that appear)
        int maxOdd = Integer.MIN_VALUE;
        int minEven = Integer.MAX_VALUE;

        for (int f : freq) {
            if (f == 0) continue;        // ignore letters not present
            if (f % 2 == 1) {           // odd
                maxOdd = Math.max(maxOdd, f);
            } else {                    // even
                minEven = Math.min(minEven, f);
            }
        }

        // 3) By problem guarantee, there's at least one odd and one even frequency
        return maxOdd - minEven;
    }
}
