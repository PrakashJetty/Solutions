class Leetcode73 {
    public void setZeroes(int[][] matrix) {
        Map<Integer, Boolean> rowMap = new HashMap<>();
        Map<Integer, Boolean> colMap = new HashMap<>();
        int m = matrix.length, n = matrix[0].length;
        for (int i = 0 ; i < m ; ++i ) {
            for (int j = 0 ; j < n ; ++j ) {
                if (matrix[i][j] == 0) {
                    rowMap.put(i, true);
                    colMap.put(j, true);
                }
                    
            }
        }
        
        for (Map.Entry<Integer, Boolean> entry : rowMap.entrySet()) {
            Arrays.fill(matrix[entry.getKey()], 0);
        }
        for (Map.Entry<Integer, Boolean> entry : colMap.entrySet()) {
            for (int i = 0 ; i < m ; ++i) {
                matrix[i][entry.getKey()] = 0;
            }
        }
        
    }
}
