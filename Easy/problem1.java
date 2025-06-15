class Solution {
    public int maxDiff(int num) {
        String s = String.valueOf(num);
        return getMax(s) - getMin(s);
    }
    
    // Replace first non-'9' digit with '9'
    private int getMax(String s) {
        char[] a = s.toCharArray();
        char target = 0;
        for (char c : a) {
            if (c != '9') {
                target = c;
                break;
            }
        }
        if (target == 0) return Integer.parseInt(s);  // all 9s
        for (int i = 0; i < a.length; i++) {
            if (a[i] == target) a[i] = '9';
        }
        return Integer.parseInt(new String(a));
    }
    
    // Minimize without leading zeros
    private int getMin(String s) {
        char[] a = s.toCharArray();
        // if first digit isn't '1', replace it everywhere with '1'
        if (a[0] != '1') {
            char target = a[0];
            for (int i = 0; i < a.length; i++) {
                if (a[i] == target) a[i] = '1';
            }
        } else {
            // otherwise, find first digit > '1' and replace it everywhere with '0'
            char target = 0;
            for (int i = 1; i < a.length; i++) {
                if (a[i] > '1') {
                    target = a[i];
                    break;
                }
            }
            if (target != 0) {
                for (int i = 1; i < a.length; i++) {
                    if (a[i] == target) a[i] = '0';
                }
            }
        }
        return Integer.parseInt(new String(a));
    }
}
