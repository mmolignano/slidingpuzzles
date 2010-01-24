
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
	
	public void parseFile(String filename){
		
	}
}
