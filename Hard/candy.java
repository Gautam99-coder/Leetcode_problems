class Solution {
    public int candy(int[] ratings) {
        int n = ratings.length;
        int[] candies = new int[n];
        
        // Each child gets at least one candy.
        for (int i = 0; i < n; i++) {
            candies[i] = 1;
        }
        
        // Left-to-right pass to satisfy left neighbor condition.
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            }
        }
        
        // Right-to-left pass to satisfy right neighbor condition.
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
        }
        
        // Sum up all candies.
        int sum = 0;
        for (int candy : candies) {
            sum += candy;
        }
        
        return sum;
    }
}
