import java.util.HashSet;
import java.util.Set;

public class sudokuController {
	int[][] solution = new int[9][9];
	sudokuModel model = null;
	sudokuBoardView view = null;
	boolean solvable = false;

	public void initialise(sudokuModel model, sudokuBoardView view) {
		this.model = model;
		this.view = view;
		model.clear();
	}

	public int checkValid() {
		Set<Integer> rowSet = null;
		Set<Integer> colSet = null;
		for (int i = 0; i < 9; i++) {
			rowSet = new HashSet<>();
			colSet = new HashSet<>();
			for (int j = 0; j < 9; j++) {
				int r = model.getBoardContents(i, j);
				int c = model.getBoardContents(j, i);
				if (r != 0) {
					if (rowSet.contains(r))
						return 1;
					else
						rowSet.add(r);
				}
				if (c != 0) {
					if (colSet.contains(c))
						return 2;
					else
						colSet.add(c);
				}
			}
		}
		for (int i = 0; i < 9; i = i + 3) {
			for (int j = 0; j < 9; j = j + 3) {
				if (!checkBlock(i, j))
					return 3;
			}
		}
		return 0;
	}

	public boolean checkBlock(int idxI, int idxJ) {
		Set<Integer> blockSet = new HashSet<>();
		int rows = idxI + 3;
		int cols = idxJ + 3;
		for (int i = idxI; i < rows; i++) {
			for (int j = idxJ; j < cols; j++) {
				if (model.getBoardContents(i, j) == 0)
					continue;

				if (blockSet.contains(model.getBoardContents(i, j)))
					return false;

				blockSet.add(model.getBoardContents(i, j));
			}
		}
		return true;
	}

	public boolean noempty() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (model.getBoardContents(i, j) == 0)
					return false;
			}
		}
		return true;
	}

	public void moveMade() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (view.tfs[i][j].getText().isEmpty())
					model.setBoardContents(i, j, 0);
				else
					model.setBoardContents(i, j, Integer.parseInt(view.tfs[i][j].getText()));
			}
		}
	}

	public boolean check() {
		moveMade();
		int check = checkValid();
		if (check == 0 && noempty())
			view.feedbackToPlayer("You won");
		else if (check == 1)
			view.feedbackToPlayer("Identical numbers in a row");
		else if (check == 2)
			view.feedbackToPlayer("Identical numbers in a column");
		else if (check == 3)
			view.feedbackToPlayer("Identical numbers in a block");
		else
			view.feedbackToPlayer("Current state is valid");
		if (check == 0)
			return true;
		return false;

	}

	boolean isValid(int row, int col, int value) {
		for (int i = 0; i < 9; i++) {
			if (solution[row][i] == value)
				return false;

			if (solution[i][col] == value)
				return false;

			if (solution[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == value)
				return false;
		}
		return true;
	}

	public void solveBoard() {
		solvable = false;
		if (check()) {
			update();
			if (solve()) {
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {
						view.tfs[i][j].setText(Integer.toString(solution[i][j]));
					}
				}
			} else
				view.feedbackToPlayer("No solution");
		}
	}

	boolean solve() {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {

				if (solution[row][col] == 0) {

					for (int i = 1; i <= 9; i++) {
						if (isValid(row, col, i)) {
							solution[row][col] = i;
							boolean solutionPossible = solve();

							if (solutionPossible)
								return true;
							else
								solution[row][col] = 0;
						}
					}
					return false;
				}
			}
		}
		return true;
	}

	public void hintBoard() {
		if (check()) {
			update();
			if (solve())
				hint1();
			else
				view.feedbackToPlayer("No solution");
		}
	}

	public void hint1() {
		if (check()) {
			update();
			solve();
		}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (model.getBoardContents(i, j) == 0) {
					model.setBoardContents(i, j, solution[i][j]);
					view.tfs[i][j].setText(Integer.toString(solution[i][j]));
					return;
				}
			}
		}
	}

	public void update() {
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				solution[i][j] = model.getBoardContents(i, j);
	}
}
