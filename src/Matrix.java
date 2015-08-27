import java.util.List;

public class Matrix {
	
	private Cell[][] all;
	private int rows;
	private int cols;
	
	public Matrix (int rows, int cols) {
		if (rows <= 0 || cols <= 0) {
			throw new IllegalArgumentException();
		}
		setRows(rows);
		setCols(cols);
		all = new Cell[rows][cols];
	}
	
	public Matrix(int rows, int cols, List<Cell> values) {
		this(rows, cols);
		setValues(values);
	}
	
	public void setValues(List<Cell> values) {
		if (values == null || values.size() != getCols() * getRows()) {
			throw new IllegalArgumentException();
		}
		int currentRow = 1;
		int currentCol = 1;
		for (int i = 1; i <= getRows()*getCols(); i++) {
			setCell(currentRow, currentCol, values.get(i-1));
			if (currentCol % getCols() == 0) {
				currentRow++;
				currentCol = 1;
			} else {
				currentCol++;
			}
		}
	}
	
	private void setRows(int rows) {
		this.rows = rows;
	}
	
	private void setCols(int cols) {
		this.cols = cols;
	}
	
	public int getRows() {
		return this.rows;
	}
	
	public int getCols() {
		return this.cols;
	}
	
	public Cell getCell(int row, int col) {
		if (row <= 0 || row > getRows() || col <= 0 || col > getCols()) {
			throw new IllegalArgumentException();
		}
		return all[row-1][col-1];
	}
	
	public void setCell(int row, int col, Cell value) {
		if (row <= 0 || row > getRows() || col <= 0 || col > getCols()) {
			throw new IllegalArgumentException();
		}
		all[row-1][col-1] = value;
	}
	
	public Cell[] getRow(int row) {
		if (row <= 0 || row > getRows()) {
			throw new IllegalArgumentException();
		}
		return all[row-1];
	}
	
	public Cell[] getCol(int col) {
		if (col <= 0 || col > getRows()) {
			throw new IllegalArgumentException();
		}
		Cell[] output = new Cell[getCols()];
		for(int i = 0; i < getCols(); i++) {
			output[i] = all[i][col-1];
		}
		return output;
	}
	
	@Override
	public String toString() {
		String output = "";
		
		for (int i = 1; i <= getRows(); i++) {
			for (int j = 1; j <= getCols(); j++) {
				output += getCell(i,j).getValue() + " ";
				if (j == getCols() && !(i == getRows())) {
					output += "\n";
				}
			}
		}
		
		return output;
	}

}
