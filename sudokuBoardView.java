import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.PlainDocument;

public class sudokuBoardView {
	JFrame guiFrame = new JFrame("Sudoku");
	JLabel feedback = new JLabel();
	sudokuController controller = null;
	sudokuModel model = null;
	JTextField[][] tfs = new JTextField[9][9];
	IntFilter filter = new IntFilter();

	public void initialise(sudokuController controller, sudokuModel model) {
		this.controller = controller;
		this.model = model;
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setMinimumSize(new Dimension(500, 500));
		guiFrame.getContentPane().setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridLayout(3, 3));
		Font font = new Font("Arial", Font.BOLD, 30);
		JPanel[] jps = new JPanel[9];
		for (int i = 0; i < 9; i++) {
			JPanel blockPanel = new JPanel(new GridLayout(3, 3));
			blockPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			jps[i] = blockPanel;
		}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				JTextField tf = new JTextField();
				tf.setFont(font);
				tf.setHorizontalAlignment(SwingConstants.CENTER);
				PlainDocument doc = (PlainDocument) tf.getDocument();
				doc.setDocumentFilter(filter);
				tfs[i][j] = tf;
				jps[(i / 3) * 3 + j / 3].add(tf);
			}
		}
		for (int i = 0; i < 9; i++)
			panel.add(jps[i]);
		JPanel panel2 = new JPanel(new GridLayout(4, 1));
		JButton button1 = new JButton("Check");
		JButton button2 = new JButton("Hint");
		JButton button3 = new JButton("Solve");
		JButton button4 = new JButton("Reset");
		button1.addActionListener(e -> controller.check());
		button2.addActionListener(e -> controller.hintBoard());
		button3.addActionListener(e -> controller.solveBoard());
		button4.addActionListener(e -> reset());
		panel2.add(button1);
		panel2.add(button2);
		panel2.add(button3);
		panel2.add(button4);
		guiFrame.getContentPane().add(feedback, BorderLayout.NORTH);
		guiFrame.getContentPane().add(panel2, BorderLayout.SOUTH);
		guiFrame.getContentPane().add(panel, BorderLayout.CENTER);
		guiFrame.pack();
		guiFrame.setVisible(true);
	}

	public void reset() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				PlainDocument doc = (PlainDocument) tfs[i][j].getDocument();
				doc.setDocumentFilter(null);
				tfs[i][j].setText("");
				doc.setDocumentFilter(filter);
			}
		}
	}

	public void feedbackToPlayer(String message) {
		feedback.setText(message);
		guiFrame.repaint();
	}
}
