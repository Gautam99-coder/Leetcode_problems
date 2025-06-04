class Solution {
    public String answerString(String word, int numFriends) {
        int n = word.length();

        // Special case: if there's only one friend, the only valid "split"
        // is the entire word itself.
        if (numFriends == 1) {
            return word;
        }

        // In any split into `numFriends` non‐empty parts, no piece can exceed:
        int k = n - numFriends + 1;

        String best = "";
        // For each starting index i, take up to k characters from there:
        for (int i = 0; i < n; i++) {
            int len = Math.min(k, n - i);
            String candidate = word.substring(i, i + len);
            if (candidate.compareTo(best) > 0) {
                best = candidate;
            }
        }
        return best;
    }
}
