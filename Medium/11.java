import java.util.*;

class Solution {
    public int minimumPairRemoval(int[] nums) {
        if (nums.length <= 1) return 0;
        List<Long> list = new ArrayList<>();
        for (int x : nums) list.add((long)x);

        int ops = 0;
        while (!isNonDecreasing(list)) {
            int n = list.size();
            long minSum = Long.MAX_VALUE;
            int idx = -1;
            // find leftmost adjacent pair with minimum sum
            for (int i = 0; i < n - 1; i++) {
                long s = list.get(i) + list.get(i + 1);
                if (s < minSum) {
                    minSum = s;
                    idx = i;
                }
            }
            // replace pair (idx, idx+1) by their sum
            list.set(idx, minSum);
            list.remove(idx + 1);
            ops++;
        }
        return ops;
    }

    private boolean isNonDecreasing(List<Long> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) < list.get(i - 1)) return false;
        }
        return true;
    }
}
