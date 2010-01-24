import java.util.ArrayList;

public class Node {
	Board board;
	int depth;
	ArrayList<Node> children;

	public Node(Board b, int depth) {
		board = b;
		this.depth = depth;
		children = new ArrayList<Node>();
	}

	public void expand() {
		int row = 0;
		int col = 0;
		if (children.size() == 0) {
			for(int i=0; i<board.getSize(); i++) {
				for(int j=0; j<board.getSize(); j++) {
					if (board.getBoard()[i][j] == 0) {
						row = i;
						col = j;
					}
				}
			}
			if (row != 0) {
				Board newBoard = new Board(this.board);
				newBoard.moveSpace(row, col, Direction.UP);
				children.add(new Node(newBoard, depth+1));
			}
			if (col != 0) {
				Board newBoard = new Board(this.board);
				newBoard.moveSpace(row, col, Direction.LEFT);
				children.add(new Node(newBoard, depth+1));
			}
			if (row != board.getSize()-1) {
				Board newBoard = new Board(this.board);
				newBoard.moveSpace(row, col, Direction.DOWN);
				children.add(new Node(newBoard, depth+1));
			}
			if (col != board.getSize()-1) {
				Board newBoard = new Board(this.board);
				newBoard.moveSpace(row, col, Direction.RIGHT);
				children.add(new Node(newBoard, depth+1));
			}
		}
	}
	
	public String toString() {
		return "Depth: " + this.depth + "\n" + this.board.toString();
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public ArrayList<Node> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<Node> children) {
		this.children = children;
	}

}
