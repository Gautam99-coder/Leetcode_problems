class Solution {
    public long goodTriplets(int[] nums1, int[] nums2) {
        int n = nums1.length;
        // Create a mapping from value to its index in nums2
        int[] pos = new int[n];
        for (int i = 0; i < n; i++) {
            pos[nums2[i]] = i;
        }
        
        // Build the transformed array A where A[i] is the position 
        // of nums1[i] in nums2. 
        int[] A = new int[n];
        for (int i = 0; i < n; i++) {
            A[i] = pos[nums1[i]];
        }
        
        // left[i] holds the count of indices j (j < i) with A[j] < A[i]
        long[] left = new long[n];
        BIT bitLeft = new BIT(n);
        for (int i = 0; i < n; i++) {
            int posVal = A[i];
            // Query BIT for count of numbers less than posVal
            left[i] = (posVal > 0) ? bitLeft.query(posVal - 1) : 0;
            // Update BIT with the current value
            bitLeft.update(posVal, 1);
        }
        
        // right[i] holds the count of indices k (k > i) with A[k] > A[i]
        long[] right = new long[n];
        BIT bitRight = new BIT(n);
        for (int i = n - 1; i >= 0; i--) {
            int posVal = A[i];
            // BIT.query(n - 1) gives total counts in the BIT. 
            // BIT.query(posVal) gives count of numbers <= A[i], so the difference gives count of numbers > A[i].
            right[i] = bitRight.query(n - 1) - bitRight.query(posVal);
            // Update BIT with the current element
            bitRight.update(posVal, 1);
        }
        
        // Sum up the number of good triplets
        long result = 0;
        for (int i = 0; i < n; i++) {
            result += left[i] * right[i];
        }
        return result;
    }
    
    // Internal Binary Indexed Tree (Fenwick Tree) class.
    class BIT {
        int[] tree;
        int size;
        
        public BIT(int n) {
            this.size = n;
            tree = new int[n + 1]; // 1-indexed array
        }
        
        // Add 'value' to element at index 'index' (0-indexed)
        public void update(int index, int value) {
            index++; // Convert to 1-indexed for the BIT operations
            while (index <= size) {
                tree[index] += value;
                index += index & -index;
            }
        }
        
        // Query the cumulative frequency sum from index 0 to 'index' (0-indexed)
        public int query(int index) {
            int sum = 0;
            index++; // Convert to 1-indexed
            while (index > 0) {
                sum += tree[index];
                index -= index & -index;
            }
            return sum;
        }
    }
}
