import java.util.ArrayList;

public class Node {
	Board board;
	int depth;
	int cost;
	ArrayList<Node> children;
	int [] priorMoves;
	Direction dir;

	public Node(Board b, int depth, int [] priorMoves, int row, int col, Direction dir) {
		board = b;
		this.depth = depth;
		this.cost = -1;
		children = new ArrayList<Node>();
		this.priorMoves = new int[depth];
		for (int i=0; i<priorMoves.length; i++) {
			this.priorMoves[i] = priorMoves[i];
		}
		this.dir = dir;
		// The board has already changed by now!
		if(depth !=0)
			this.priorMoves[depth-1] = (this.board.getSize()*row) + col + 1;
	}

	
	
	public int getCost() {
		return cost;
	}



	public void setCost(int cost) {
		this.cost = cost;
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
			if (row != 0 && (this.dir == null || this.dir != Direction.DOWN)) {
				Board newBoard = new Board(this.board);
				newBoard.moveSpace(row, col, Direction.UP);
				children.add(new Node(newBoard, depth+1, this.priorMoves, row-1, col, Direction.UP));
			}
			if (col != 0 && (this.dir == null || this.dir != Direction.RIGHT)) {
				Board newBoard = new Board(this.board);
				newBoard.moveSpace(row, col, Direction.LEFT);
				children.add(new Node(newBoard, depth+1, this.priorMoves, row, col-1, Direction.LEFT));
			}
			if (row != board.getSize()-1 && (this.dir == null || this.dir != Direction.UP)) {
				Board newBoard = new Board(this.board);
				newBoard.moveSpace(row, col, Direction.DOWN);
				children.add(new Node(newBoard, depth+1, this.priorMoves, row+1, col, Direction.DOWN));
			}
			if (col != board.getSize()-1 && (this.dir == null || this.dir != Direction.LEFT)) {
				Board newBoard = new Board(this.board);
				newBoard.moveSpace(row, col, Direction.RIGHT);
				children.add(new Node(newBoard, depth+1, this.priorMoves, row, col+1, Direction.RIGHT));
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
