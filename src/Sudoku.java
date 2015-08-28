import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;

public class Sudoku {

	private Matrix matrix;
	private final int DIFF_LEVEL = 33;  //number of revealed tiles which "indicate" difficulty (not really but well...)
										//increase for easier sudokus, but slower generation
										//33 is considered newspaper level (= medium)

	public Sudoku(Sudoku s) {
		this.matrix = new Matrix(9,9);
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= 9; j++) {
				this.matrix.setCell(i, j, s.getCell(i, j));
			}
		}
	}
	
	public Sudoku() {
		matrix = new Matrix(9, 9);
		int curRow = 1, curCol = 1;
		for (int i = 1; i <= 81; i++) { // initialize sudoku with all 0's (==
										// empty cell)
			matrix.setCell(curRow, curCol, new Cell(0));
			if (i % 9 == 0) {
				curRow++;
				curCol = 1;
			} else {
				curCol++;
			}
		}
		Random rn = new Random();
		for (int i = 1; i <= DIFF_LEVEL; i++) {
			int randVal = rn.nextInt(9) + 1;
			int randRow = rn.nextInt(9) + 1;
			int randCol = rn.nextInt(9) + 1;

			// System.out.println("Trying " + randVal + " on (" + randRow + ", "
			// + randCol + "), iteration: " + i);
			if (!setCell(randRow, randCol, randVal, true)) {
				i--;
			}
		}		
	}

	private Cell getCell(int row, int col) {
		return getMatrix().getCell(row, col);
	}

	private void clearCell(int row, int col) {
		getMatrix().setCell(row, col, new Cell(0));
	}
	
	public boolean solveable() throws InterruptedException { 
		Deque<Position> stack = new ArrayDeque<Position>();
		Sudoku help = new Sudoku(this);
		int curVal = 1;
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= 9; j++) {
				if (curVal == 10) {
					if (!stack.isEmpty()) {
						Position p = stack.pop();
						help.clearCell(i,j);
						i = p.getRow();
						j = p.getCol();
						//System.out.println("Backtracking to " + p); //notify when backtracking
						curVal = help.getCell(i,j).getValue()+1;
					} else {
						//throw new IllegalArgumentException("unsolvable");
						return false;
					}
				} else {
					//Thread.sleep(300); //testing purposes, see the magic happen slowly
					if (!help.getCell(i, j).getGenerated()) {
						//System.out.println("Trying value " + curVal + " on cell " + new Position(i, j));
						if (help.setCell(i, j, curVal, false)) {
							stack.push(new Position(i, j));
							//System.out.println(help); //check how our sudoku looks like partially solved
							curVal = 1;
						} else {
							curVal++;
							if (j == 1 && i > 1) {
								j = 9;
								i--;
							} else {
								j--;
							}
						}
					}
				}
			}
		}
		//System.out.println(help); //print the solution
		return true;
	}

	public Matrix getMatrix() {
		return this.matrix;
	}

	public boolean setCell(int row, int col, int value, boolean generated) {
		boolean res = false;
		if (isValidValue(value) && isValidRow(row) && isValidCol(col)) {
			if (!getMatrix().getCell(row, col).getGenerated()) {
				if (isValidInput(row, col, value)) {
					getMatrix().setCell(row, col, new Cell(value, generated));
					res = true;
				}
			}
		}
		return res;
	}

	private boolean isValidInput(int row, int col, int value) {
		if (!(isValidValue(value) && isValidRow(row) && isValidCol(col))) {
			return false;
		}
		return checkRow(value, row) && checkCol(value, col) && checkArea(value, row, col);
	}

	private boolean checkRow(int value, int row) {
		boolean res = true;
		for (Cell c : getMatrix().getRow(row)) {
			if (c.getValue() == value) {
				res = false;
				break;
			}
		}
		return res;
	}

	private boolean checkCol(int value, int col) {
		boolean res = true;
		for (Cell c : getMatrix().getCol(col)) {
			if (c.getValue() == value) {
				res = false;
				break;
			}
		}
		return res;
	}

	public boolean checkArea(int value, int row, int col) {
		boolean res = true;
		List<Position> validPositions = new ArrayList<Position>();
		if (row >= 1 && row <= 3) {
			if (col >= 1 && col <= 3) {
				for (int i = 1; i <= 3; i++) {
					for (int j = 1; j <= 3; j++) {
						validPositions.add(new Position(i, j));
					}
				}
			}
			if (col >= 4 && col <= 6) {
				for (int i = 1; i <= 3; i++) {
					for (int j = 4; j <= 6; j++) {
						validPositions.add(new Position(i, j));
					}
				}
			}
			if (col >= 7 && col <= 9) {
				for (int i = 1; i <= 3; i++) {
					for (int j = 7; j <= 9; j++) {
						validPositions.add(new Position(i, j));
					}
				}
			}
		}
		if (row >= 4 && row <= 6) {
			if (col >= 1 && col <= 3) {
				for (int i = 4; i <= 6; i++) {
					for (int j = 1; j <= 3; j++) {
						validPositions.add(new Position(i, j));
					}
				}
			}
			if (col >= 4 && col <= 6) {
				for (int i = 4; i <= 6; i++) {
					for (int j = 4; j <= 6; j++) {
						validPositions.add(new Position(i, j));
					}
				}
			}
			if (col >= 7 && col <= 9) {
				for (int i = 4; i <= 6; i++) {
					for (int j = 7; j <= 9; j++) {
						validPositions.add(new Position(i, j));
					}
				}
			}
		}
		if (row >= 7 && row <= 9) {
			if (col >= 1 && col <= 3) {
				for (int i = 7; i <= 9; i++) {
					for (int j = 1; j <= 3; j++) {
						validPositions.add(new Position(i, j));
					}
				}
			}
			if (col >= 4 && col <= 6) {
				for (int i = 7; i <= 9; i++) {
					for (int j = 4; j <= 6; j++) {
						validPositions.add(new Position(i, j));
					}
				}
			}
			if (col >= 7 && col <= 9) {
				for (int i = 7; i <= 9; i++) {
					for (int j = 7; j <= 9; j++) {
						validPositions.add(new Position(i, j));
					}
				}
			}
		}

		for (Position p : validPositions) {
			if (getMatrix().getCell(p.getRow(), p.getCol()).getValue() == value) {
				res = false;
				break;
			}
		}

		return res;
	}

	private boolean isValidValue(int value) {
		return (value > 0 && value <= 9);
	}

	private boolean isValidRow(int row) {
		return (row > 0 && row <= 9);
	}

	private boolean isValidCol(int col) {
		return (col > 0 && col <= 9);
	}

	@Override
	public String toString() {
		String output = " -----------------------\n";
		int placepipe = 0;
		int pipesplaced = 0;
		
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= 9; j++) {
				if ( j == 1) {
					output += "|";
				}
				output += " " + getCell(i,j).getValue();
				if (placepipe == 2 && pipesplaced != 2) {
					output += " |";
					placepipe = 0;
					pipesplaced++;
				} else {
					//output += "\n";
					placepipe=placepipe+1 % 3;
					pipesplaced = 0;
				}
				if (j == 9) {
					output += "\n";
				}
				if (i % 3 == 0 && j == 9) {
					output += " -----------------------\n";
				}
			}
		}
		return output;
	}
	


}
