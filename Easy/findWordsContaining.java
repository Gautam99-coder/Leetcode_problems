import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> findWordsContaining(String[] words, char x) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            // Using indexOf to check for the presence of character x in the word.
            if (words[i].indexOf(x) != -1) {
                indices.add(i);
            }
        }
        return indices;
    }
}
