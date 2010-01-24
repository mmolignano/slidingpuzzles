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

	public Board(int size) {
		this.size = size;
		board = new int[size][size];
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
