import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Board {
	int size;
	int [][] board;
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}
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
	 * Moves the blank in direction d
	 * @param row
	 * @param col
	 * @param d
	 * @return
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

	public Board(int size) {
		this.size = size;
		board = new int[size][size];
	}
	public Board(Board b) {
		this.size = b.size;
		this.board = new int[b.size][b.size];
		for (int i=0; i<b.size; i++) {
			for (int j=0; j<b.size; j++) {
				this.board[i][j] = b.getBoard()[i][j];
			}
		}
	}
	
	public void parseFile(String filename) {
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line;
			int lines = 0;
			try {
				while((line = br.readLine()) != null) {
					String[] vals = line.split(",");
					for(int i=0; i<vals.length; i++) {
						if (vals[i].equals(" "))
							vals[i] = "0";
						this.board[lines][i] = Integer.parseInt(vals[i]);
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