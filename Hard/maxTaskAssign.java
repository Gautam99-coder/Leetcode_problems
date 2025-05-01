import java.util.*;

class Solution {
    public int maxTaskAssign(int[] tasks, int[] workers, int pills, int strength) {
        // sort tasks and workers in ascending order
        Arrays.sort(tasks);
        Arrays.sort(workers);
        
        int lo = 0, hi = Math.min(tasks.length, workers.length);
        // binary search for the maximum number of tasks (candidate k)
        while (lo < hi) {
            int mid = (lo + hi + 1) / 2;
            if (canAssign(mid, tasks, workers, pills, strength)) {
                lo = mid;
            } else {
                hi = mid - 1;
            }
        }
        return lo;
    }
    
    // Check if it is possible to assign k tasks (optimally chosen as the k easiest tasks)
    private boolean canAssign(int k, int[] tasks, int[] workers, int pills, int strength) {
        // We use the k easiest tasks – i.e., tasks[0..k-1] (since tasks is sorted in ascending order)
        // We will process these candidate tasks from hardest to easiest (i.e. descending order).
        
        // Make a multiset for the available workers.
        TreeMap<Integer, Integer> available = new TreeMap<>();
        for (int w : workers) {
            available.put(w, available.getOrDefault(w, 0) + 1);
        }
        
        // Process candidate tasks from index k-1 down to 0.
        for (int i = k - 1; i >= 0; i--) {
            int req = tasks[i];  // current task requirement
            // Try to assign a worker without a pill: find the smallest worker with strength >= req.
            Map.Entry<Integer, Integer> entry = available.ceilingEntry(req);
            if (entry != null) {
                removeOne(available, entry.getKey());
            } else {
                // No worker can cover the task outright.
                // Use a pill if possible: find the smallest worker with strength >= (req - strength)
                if (pills <= 0) return false;
                int needed = req - strength;
                Map.Entry<Integer, Integer> pillEntry = available.ceilingEntry(needed);
                if (pillEntry == null) return false;
                // Even with pill, verify (should always hold if found in tree)
                if (pillEntry.getKey() + strength < req) return false;
                pills--;
                removeOne(available, pillEntry.getKey());
            }
        }
        return true;
    }
    
    // Helper method to remove one occurrence of key from the TreeMap (multiset)
    private void removeOne(TreeMap<Integer, Integer> map, int key) {
        int cnt = map.get(key);
        if (cnt == 1) {
            map.remove(key);
        } else {
            map.put(key, cnt - 1);
        }
    }
}
