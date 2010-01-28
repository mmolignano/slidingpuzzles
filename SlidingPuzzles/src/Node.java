import java.util.ArrayList;
import java.util.Collection;

public class Node implements Comparable<Node>{
	Board board;
	short depth;
	short cost;
	Direction dir;
	Node parent;


	public Node(Board b, short depth, byte row, byte col, Direction dir, Node parent) {
		board = b;
		this.depth = depth;
		this.cost = -1;

		this.parent = parent;

		this.dir = dir;

	}

	
	
	public short getCost() {
		return cost;
	}



	public void setCost(short cost) {
		this.cost = cost;
	}


	public Collection<Node> expand(short maxDepth) {
		byte row = 0;
		byte col = 0;
		ArrayList<Node> children = new ArrayList<Node>();
		if (this.depth < maxDepth) {
			if (children.size() == 0) {
				for(byte i=0; i<board.getSize(); i++) {
					for(byte j=0; j<board.getSize(); j++) {
						if (board.getBoard()[i][j] == 0) {
							row = i;
							col = j;
						}
					}
				}
				if (row != 0 && (this.dir == null || this.dir != Direction.DOWN)) {
					Board newBoard = new Board(this.board);
					newBoard.moveSpace(row, col, Direction.UP);
					children.add(new Node(newBoard, (short) (depth+1), (byte)(row-1), col, Direction.UP, this));
				}
				if (col != 0 && (this.dir == null || this.dir != Direction.RIGHT)) {
					Board newBoard = new Board(this.board);
					newBoard.moveSpace(row, col, Direction.LEFT);
					children.add(new Node(newBoard, (short) (depth+1), row, (byte)(col-1), Direction.LEFT, this));
				}
				if (row != board.getSize()-1 && (this.dir == null || this.dir != Direction.UP)) {
					Board newBoard = new Board(this.board);
					newBoard.moveSpace(row, col, Direction.DOWN);
					children.add(new Node(newBoard, (short) (depth+1), (byte)(row+1), col, Direction.DOWN, this));
				}
				if (col != board.getSize()-1 && (this.dir == null || this.dir != Direction.LEFT)) {
					Board newBoard = new Board(this.board);
					newBoard.moveSpace(row, col, Direction.RIGHT);
					children.add(new Node(newBoard, (short) (depth+1),  row, (byte)(col+1), Direction.RIGHT, this));
				}
			}
		}
		return children;
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

	public void setDepth(short depth) {
		this.depth = depth;
	}

	public void backUpToParent() {
		if (this.parent == null) {
			return;
		}
		if (this.parent.getCost() > this.cost) {
			this.parent.setCost(this.cost);
		}
		this.parent = null;
	}

	public short lastMove() {
		int row = 0;
		int col = 0;
		for(byte i=0; i<board.getSize(); i++) {
			for(byte j=0; j<board.getSize(); j++) {
				if (board.getBoard()[i][j] == 0) {
					row = i;
					col = j;
				}
			}
		}
		return (short) ((this.board.getSize()*row) + col + 1);
	}
	
	@Override
	public int compareTo(Node other) {
		if (this.cost < other.getCost())
			return -1;
		if (this.cost > other.getCost())
			return 1;
		return 0;
	}

}
