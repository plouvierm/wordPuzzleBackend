import java.util.ArrayList;

public class WordPuzzle {
	private ArrayList<Letter> letters;
	private int numberOfRows;
	private String[] searchDirections;
	
	public WordPuzzle (String puzzle, int numberOfRows) {
		this.searchDirections = new String[] {"LEFT","UP","RIGHT","DOWN"};
		
		this.letters = new ArrayList<Letter>();
		this.numberOfRows = numberOfRows;
		int rowCounter = 0;
		int columnCounter = 0;
		for(int puzzleIndex = 0, n = puzzle.length() ; puzzleIndex < n ; puzzleIndex++) {
			if (columnCounter == this.numberOfRows) {
				columnCounter = 0;
				++rowCounter;
			}
			this.letters.add(puzzleIndex, new Letter(puzzle.charAt(puzzleIndex), rowCounter, columnCounter));
			++columnCounter;
		}
	}
	
	public ArrayList<Letter> searchWord (String word) {
		char firstLetterOfWord = word.charAt(0);
		for(int puzzleIndex = 0, n = this.letters.size() ; puzzleIndex < n ; puzzleIndex++) {
			if(this.letters.get(puzzleIndex).getLetterValue() == firstLetterOfWord) {
				// Look now for next chars
				ArrayList<Letter> previousLetters = new ArrayList<Letter>();
				previousLetters.add(this.letters.get(puzzleIndex));
				ArrayList<Letter> foundWord = this.findLetterAroundLetters(word, 1, previousLetters);
				if (foundWord != null) {
					return foundWord;
				}
			}
		}
		return null;
	}
	
	public ArrayList<Letter> findLetterAroundLetters (String word, int charIndex, ArrayList<Letter> previousLetters) {
		char charToFind = word.charAt(charIndex);
		String searchDirection = "ANY";
		if (charIndex > 1) {
			if (previousLetters.get(charIndex-1).getPositionRow() == previousLetters.get(charIndex-2).getPositionRow()) {
				if (previousLetters.get(charIndex-1).getPositionColumn() > previousLetters.get(charIndex-2).getPositionColumn()) {
					// direction is down
					searchDirection = "RIGHT";
				} else {
					//direction is up
					searchDirection = "LEFT";
				}
			} else {
				if (previousLetters.get(charIndex-1).getPositionRow() > previousLetters.get(charIndex-2).getPositionRow()) {
					// direction is right
					searchDirection = "DOWN";
				} else {
					//direction is left
					searchDirection = "UP";
				}
			}
				
		}
		// Look up, left, right, down
		// Check if searchDirection is ANY of predefined searchDirection
		Letter previousLetter = previousLetters.get(charIndex-1);
		for(int i = 0; i < this.searchDirections.length; i++) {
			Letter foundLetter = null;
			ArrayList<Letter> clonePreviousLetters = (ArrayList<Letter>) previousLetters.clone();
			if (searchDirection == "ANY" || searchDirection == this.searchDirections[i] ) {
				foundLetter = this.lookupLetter(charToFind, this.searchDirections[i], previousLetter);
			}
			if (foundLetter != null) {
				clonePreviousLetters.add(foundLetter);
				if (charIndex == (word.length()-1)) {
					return clonePreviousLetters;
				}
				ArrayList<Letter> result = this.findLetterAroundLetters(word, charIndex + 1, clonePreviousLetters);
				if (result != null) {
					return result;
				}
			}
		}
		return null;
	}
	
	public Letter lookupLetter(char charToFind, String direction, Letter previousLetter) {
		Letter letterToReturn = null;
		
		switch (direction) {
		case "RIGHT":
			if (previousLetter.getPositionRow() != (this.numberOfRows-1)) {
				letterToReturn = this.verifyLetterAtPosition(charToFind, previousLetter.getPositionRow(), previousLetter.getPositionColumn()+1);
			}
			break;
		case "LEFT":
			if (previousLetter.getPositionColumn() != 0) {
				letterToReturn = this.verifyLetterAtPosition(charToFind, previousLetter.getPositionRow(), previousLetter.getPositionColumn()-1);
			}
			break;
		case "UP":
			if (previousLetter.getPositionRow() != 0) {
				letterToReturn = this.verifyLetterAtPosition(charToFind, previousLetter.getPositionRow()-1, previousLetter.getPositionColumn());
			}
			break;
		case "DOWN":
			if (previousLetter.getPositionColumn() != (this.numberOfRows-1)) {
				letterToReturn = this.verifyLetterAtPosition(charToFind, previousLetter.getPositionRow()+1, previousLetter.getPositionColumn());
			}
			break;
		}
		return letterToReturn;
		
	}
	
	public Letter verifyLetterAtPosition(char charToFind, int x, int y) {
		for(int puzzleIndex = 0, n = this.letters.size() ; puzzleIndex < n ; puzzleIndex++) {
			if (this.letters.get(puzzleIndex).getLetterValue() == charToFind && this.letters.get(puzzleIndex).getPositionRow() == x && this.letters.get(puzzleIndex).getPositionColumn() == y) {
				return this.letters.get(puzzleIndex);
			}
		}
		return null;
	}

	public ArrayList<Letter> getLetters() {
		return letters;
	}

	public void setLetters(ArrayList<Letter> letters) {
		this.letters = letters;
	}
	
	public int getNumberOfRows() {
		return this.numberOfRows;
	}
}
