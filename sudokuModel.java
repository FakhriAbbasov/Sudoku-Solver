public class sudokuModel {
	int[][] board = new int[9][9];

	public void clear() {
		for (int x = 0; x < 9; x++)
			for (int y = 0; y < 9; y++)
				board[x][y] = 0;
	}

	public int getBoardContents(int x, int y) {
		return board[x][y];
	}

	public void setBoardContents(int x, int y, int v) {
		board[x][y] = v;
	}
}
