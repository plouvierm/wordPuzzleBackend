
public class Letter {

	private char letterValue;
	private int positionRow;
	private int positionColumn;
	public Letter(char letterValue, int positionRow, int positionColumn) {
		this.letterValue = letterValue;
		this.positionRow = positionRow;
		this.positionColumn = positionColumn;
	}
	
	public char getLetterValue() {
		return this.letterValue;
	}
	
	public int getPositionRow() {
		return this.positionRow;
	}
	
	public int getPositionColumn() {
		return this.positionColumn;
	}
}
