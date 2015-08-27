import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		Matrix m = new Matrix(3,3);
		m.setCell(1, 1, new Cell(1));
		m.setCell(1, 2, new Cell(2));
		m.setCell(1, 3, new Cell(3));
		m.setCell(2, 1, new Cell(4));
		m.setCell(2, 2, new Cell(5));
		m.setCell(2, 3, new Cell(6));
		m.setCell(3, 1, new Cell(7));
		m.setCell(3, 2, new Cell(8));
		m.setCell(3, 3, new Cell(9));
		
		List<Cell> values = new ArrayList<Cell>();
		for (int i = 1; i <= 16; i++) {
			values.add(new Cell(i%10));
		}
		Matrix l = new Matrix(4,4,values);

		System.out.println(m);
		System.out.println("-----");
		System.out.println(l);
		System.out.println("-----");
		
		Sudoku s = new Sudoku();
		boolean solveable = false;
		
		while (!solveable) {
			s = new Sudoku();
			solveable = s.solveable();
		}
		System.out.println(s);

		
		
	}

}
