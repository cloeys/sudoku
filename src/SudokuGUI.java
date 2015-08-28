import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class SudokuGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 0;
	private JTextField f[][] = new JTextField[9][9];
	private JPanel p[][] = new JPanel[3][3];
	private Sudoku s;

	public SudokuGUI(Sudoku s) {
		super("Sudoku");
		this.s = s;
		this.setResizable(false);
		this.setMinimumSize(new Dimension(400, 400));
		for (int x = 0; x <= 8; x++) {
			for (int y = 0; y <= 8; y++) {
				f[x][y] = new JTextField(1);
				f[x][y].setHorizontalAlignment(JTextField.CENTER);
				if (s.getCell(y + 1, x + 1).getValue() == 0) {
					f[x][y].setText("");
				} else {
					f[x][y].setText(Integer.toString(s.getCell(y + 1, x + 1).getValue()));
				}
				AbstractDocument d = (AbstractDocument) f[x][y].getDocument();
				d.setDocumentFilter(new DocumentFilter() {
					int max = 1;
					@Override
					public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text,
							AttributeSet attrs) throws BadLocationException {
						int documentLength = fb.getDocument().getLength();
						if (documentLength - length + text.length() <= max){
							if (text.length() == 1 && Character.isDigit(text.charAt(0)) && text.charAt(0) != '0'){
								super.replace(fb, offset, length, text.toUpperCase(), attrs);
							}
						}
					}
				});
				
				if (s.getCell(y + 1, x + 1).getGenerated()) {
					f[x][y].setEditable(false);
					f[x][y].setFont(new Font("Arial", Font.BOLD, 12));
				}
			}
		}

		for (int x = 0; x <= 2; x++) {
			for (int y = 0; y <= 2; y++) {
				p[x][y] = new JPanel(new GridLayout(3, 3));
			}
		}

		setLayout(new GridLayout(3, 3, 5, 5));

		for (int u = 0; u <= 2; u++) {
			for (int i = 0; i <= 2; i++) {
				for (int x = 0; x <= 2; x++) {
					for (int y = 0; y <= 2; y++) {
						p[u][i].add(f[y + i * 3][x + u * 3]);
					}
				}
				add(p[u][i]);
			}
		}
	}
}