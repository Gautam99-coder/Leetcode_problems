class Solution {
    public int countSymmetricIntegers(int low, int high) {
        int count = 0;
        for (int num = low; num <= high; num++) {
            if (isSymmetric(num)) {
                count++;
            }
        }
        return count;
    }
    
    private boolean isSymmetric(int num) {
        String s = Integer.toString(num);
        int length = s.length();
        if (length % 2 != 0) {
            return false;
        }
        int half = length / 2;
        int sumFirst = 0;
        int sumSecond = 0;
        for (int i = 0; i < half; i++) {
            sumFirst += s.charAt(i) - '0';
        }
        for (int i = half; i < length; i++) {
            sumSecond += s.charAt(i) - '0';
        }
        return sumFirst == sumSecond;
    }
}
