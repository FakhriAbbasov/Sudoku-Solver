public class sudokuMain {

	public static void main(String[] args) {
		sudokuBoardView view = new sudokuBoardView();
		sudokuModel model = new sudokuModel();
		sudokuController controller = new sudokuController();
		view.initialise(controller, model);
		controller.initialise(model, view);
	}

}