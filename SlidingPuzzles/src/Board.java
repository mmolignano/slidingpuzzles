import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/**
 * Class to encapsulate a board for an n-puzzle. 
 * @author Thomas Liu (kangchao@wpi.edu), Rui Dai (ruidai@wpi.edu) Michael Molignano (mikem@wpi.edu), Kevin Nolan (knolan@wpi.edu)
 *
 */

public class Board {
	//The size of the board.
	byte size;
	//An array that holds the values on the board.
	byte [][] board;
	
	/**
	 * Getters and setters for the board and size.
	 */
	public byte getSize() {
		return size;
	}

	public void setSize(byte size) {
		this.size = size;
	}

	public byte[][] getBoard() {
		return board;
	}

	public void setBoard(byte[][] board) {
		this.board = board;
	}
	
	/**
	 * Returns a string representing an n-puzzle board, formatted correctly.
	 */
	public String toString() {
		String s = "";
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board.length; j++) {
				if (j==board.length-1) {
					s += board[i][j];
				} else {
					s += board[i][j] + ",";
				}
			}
			s += "\n";
		}
		return s;
	}
	
	/**
	 * Figures out if the n-puzzle board is solvable by counting inversions.
	 * @return true if solvable, false otherwise.
	 */
	public boolean isSolvable() {
		int[] arr = new int[size * size];
		int inversions = 0;
		int pos = 0;

		int row = 0;//store the position for blank

		//Get the values of the board
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {

				arr[pos] = board[i][j];
				pos++;
								
				//Marks the row of the blank, counting from bottom to top
				if (board[i][j]==0) {
					 row = size - i;
				}
			}
		}

		// count inversions
		for (int i = 0; i < arr.length; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				if ((arr[i] > arr[j]) && arr[i] * arr[j] != 0) {
					inversions++;

				}
			}
		}

		//If the board size is odd, we don't care about the blank's position.
		if (size % 2 == 1) {
			if ((inversions) % 2 == 0) {
				return true;
			}
			//If the board size is even, then add one if row is odd, or 0 if row is even.
		} else {
			if (row % 2 == 0) {
				if (inversions % 2 == 1) {
					return true;
				}
			}
			else if (row % 2 == 1) {
				if (inversions % 2 == 0) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Moves the blank in direction d
	 * @param row The row that the blank space is in.
	 * @param col The column that the blank space is in.
	 * @param d The direction to move the blank.
	 * @return True if the blank is moved, false otherwise.
	 */
	public boolean moveSpace(int row, int col, Direction d) {
		if (this.board[row][col] != 0)
			return false;
		if (d == Direction.UP) {
			this.board[row][col] = this.board[row-1][col];
			this.board[row-1][col] = 0;
		} else if (d == Direction.DOWN) {
			this.board[row][col] = this.board[row+1][col];
			this.board[row+1][col] = 0;
		} else if (d == Direction.RIGHT) {
			this.board[row][col] = this.board[row][col+1];
			this.board[row][col+1] = 0;
		} else {
			this.board[row][col] = this.board[row][col-1];
			this.board[row][col-1] = 0;
		}
		return true;
	}
	
	/**
	 * Creates a new board with a width and height of the given size.
	 * @param size The width and height of the board.
	 */
	public Board(byte size) {
		this.size = size;
		board = new byte[size][size];
	}
	
	/**
	 * Creates a new board that is identical to the given board.
	 * @param b The board to copy.
	 */
	public Board(Board b) {
		this.size = b.size;
		this.board = new byte[b.size][b.size];
		for (int i=0; i<b.size; i++) {
			for (int j=0; j<b.size; j++) {
				this.board[i][j] = b.getBoard()[i][j];
			}
		}
	}
	
	/**
	 * Takes in a file and populates the board with the values from the file.
	 * Each line in the file must be a series of numbers or blanks, separated by commas.
	 * Each line can only have as many numbers as the board length.
	 * The number of lines in the file that will be read is the size of the board.
	 * Anything written after the last line in the puzzle will be ignored.
	 * @param filename A path to a file to read in.
	 */
	public void parseFile(String filename) {
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line;
			int lines = 0;
			try {
				while((line = br.readLine()) != null && lines < this.board.length) {
					String[] vals = line.split(",");
					for(int i=0; i<vals.length; i++) {
						if (vals[i].equals(" "))
							vals[i] = "0";
						this.board[lines][i] = (byte)Integer.parseInt(vals[i]);
					}
					lines++;
				}
			} catch (NumberFormatException e) {
				System.err.println("Bad file input.");
			} catch (IOException e) {
				System.err.println("Error while reading file.");
			}
		} catch (FileNotFoundException e) {
			System.err.println("Could not open file for reading.");
			e.printStackTrace();
		}
		
	}
}
