public class ValidSudoku {

	public boolean isValidSudoku(char[][] board) {
		int n = board.length;
		
		Map<Character, Character> cmap = new HashMap<>();
		Map<Character, Character> rmap = new HashMap<>();
		boolean dup = false;
		for (int i = 0; i < n ; ++i) {
			for (int j = 0; j < n ; ++j) {
				if (board[j][i] != '.') {
					if (!cmap.containsKey(board[j][i])) {
						cmap.put(board[j][i], board[j][i]);
					} else {
						dup = true;
						break;
					}
				}
				if (board[i][j] != '.') {
					if (!rmap.containsKey(board[i][j])) {
						rmap.put(board[i][j], board[i][j]);
					} else {
						dup = true;
						break;
					}
				}
				
			}
			cmap.clear();
			rmap.clear();
			if (dup)
				break;
			
			
		}
		if (dup)
			return !dup;
		cmap.clear();
		int i = 0, j = i;
		while (i < n ) {

			while (j < n ) {

				int m = i;
				while (m < i + 3) {
					int k = 0;
					while (k < 3) {
//					if ( i  == j) {
						if (board[m][k + j] != '.') {
							if (!cmap.containsKey(board[m][k + j])) {
								cmap.put(board[m][k + j], board[m][k + j]);
							} else {
								dup = true;
								break;
							}
						}
						++k;
					}
					++m;
				}
				cmap.clear();
				if (dup)
					break;
				j = j + 3;
			}
			if (dup)
				break;
			j = 0;
			i = i + 3;
		}

		return !dup;
		
        
    }
}