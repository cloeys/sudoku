
public class Cell {
	
	private int value;
	private boolean generated;
	
	public Cell(int value) {
		if (value < 0 || value > 9) {
			throw new IllegalArgumentException();
		}
		setValue(value);
		setGenerated(false);
	}
	
	public Cell(int value, boolean generated) {
		this(value);
		setGenerated(generated);
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public void setGenerated(boolean generated) {
		this.generated = generated;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public boolean getGenerated() {
		return this.generated;
	}
	
	@Override
	public String toString() {
		return "" + getValue() + ", generated = " + getGenerated();
	}

}
