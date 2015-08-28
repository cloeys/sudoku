import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		//generating a new sudoku		
		Sudoku s = new Sudoku();
		boolean solveable = false;
		while (!solveable) { //brute force to give the user a solveable sudoku
			s = new Sudoku();
			solveable = s.solveable();
		}
		
		System.out.println(s); //
		System.out.println(s.getFilledIn());

		SudokuGUI gui = new SudokuGUI(s);
		
		gui.setVisible(true);
		
	}

}
