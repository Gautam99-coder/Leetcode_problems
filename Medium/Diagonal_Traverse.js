class Solution {
    public int[] findDiagonalOrder(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int[] result = new int[m * n];
        int row = 0, col = 0, dir = 1; // 1: up-right, -1: down-left
        
        for (int i = 0; i < m * n; i++) {
            result[i] = mat[row][col];
            
            if (dir == 1) { // moving up-right
                if (col == n - 1) { // hit right boundary
                    row++;
                    dir = -1;
                } else if (row == 0) { // hit top boundary
                    col++;
                    dir = -1;
                } else { // normal move
                    row--;
                    col++;
                }
            } else { // dir == -1, moving down-left
                if (row == m - 1) { // hit bottom boundary
                    col++;
                    dir = 1;
                } else if (col == 0) { // hit left boundary
                    row++;
                    dir = 1;
                } else { // normal move
                    row++;
                    col--;
                }
            }
        }
        
        return result;
    }
}
