class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if (obstacleGrid.length == 1 && obstacleGrid[0].length == 1) {
            return obstacleGrid[0][0] == 1? 0 : 1;
        } 
       int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
		int[][] dp = new int[m][n];

		for (int i = m - 1; i >=0  ; --i) {
			for (int j = n - 1; j >= 0 ; --j) {
				if (i == m -1 && j == n - 1) {
					dp[m - 1][n - 1] = 1;
					continue;
				}
				if (obstacleGrid[i][j] == 0) {
					if (i + 1 < m && obstacleGrid[i + 1][j] == 0)
						dp[i][j] += dp[i + 1][j];

					if (j + 1 < n && obstacleGrid[i][j + 1] == 0) {
						dp[i][j] += dp[i][j + 1];
					}
				}
			}
		}
        return dp[0][0];
        
    }
}
