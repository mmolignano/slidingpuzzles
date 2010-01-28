import java.util.ArrayList;
import java.util.Collection;

/**
 * Acts a the Node structure during our search. This Node contains the layout
 * of the board, the depth of this node, the estimated cost to the goal, the
 * direction taken to get to this position and the parent node. * 
 * 
 * @author Thomas Liu (kangchao@wpi.edu), Rui Dai (ruidai@wpi.edu) Michael Molignano (mikem@wpi.edu), Kevin Nolan (knolan@wpi.edu)
 *
 */
public class Node implements Comparable<Node>{
	Board board;
	short depth;
	short cost;
	Direction dir;
	Node parent;

	/**
	 * Constructor for the Node class
	 * 
	 * @param board		The board this node represents
	 * @param depth		The depth at this node
	 * @param dir		The direction taken to get to this node from the parent
	 * @param parent	Reference to the parent node
	 */
	public Node(Board board, short depth, Direction dir, Node parent) {
		this.board = board;
		this.depth = depth;
		this.dir = dir;
		this.parent = parent;
		
		this.cost = -1;
	}

	/**
	 * Return the cost of this Node to the goal. The cost is the depth plus
	 * the heuristic value.
	 * 
	 * @return	The cost of the Node
	 */
	public short getCost() {
		return cost;
	}

	/**
	 * Set the cost of the node to the given value
	 * 
	 * @param cost	The new cost of the node
	 */
	public void setCost(short newCost) {
		this.cost = newCost;
	}

	/**
	 * Expands this node and returns the set of its children. If the depth of
	 * this node is greater or equal to maxDepth, return an empty list.
	 * 
	 * @param maxDepth	The maximum depth allowed for the nodes
	 * 	
	 * @return	The list of children nodes created by expansion 
	 */
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
					children.add(new Node(newBoard, (short) (depth+1), Direction.UP, this));
				}
				if (col != 0 && (this.dir == null || this.dir != Direction.RIGHT)) {
					Board newBoard = new Board(this.board);
					newBoard.moveSpace(row, col, Direction.LEFT);
					children.add(new Node(newBoard, (short) (depth+1), Direction.LEFT, this));
				}
				if (row != board.getSize()-1 && (this.dir == null || this.dir != Direction.UP)) {
					Board newBoard = new Board(this.board);
					newBoard.moveSpace(row, col, Direction.DOWN);
					children.add(new Node(newBoard, (short) (depth+1), Direction.DOWN, this));
				}
				if (col != board.getSize()-1 && (this.dir == null || this.dir != Direction.LEFT)) {
					Board newBoard = new Board(this.board);
					newBoard.moveSpace(row, col, Direction.RIGHT);
					children.add(new Node(newBoard, (short) (depth+1), Direction.RIGHT, this));
				}
			}
		}
		return children;
	}
	
	/**
	 * Returns a string value of this node containing its depth and the board
	 */
	public String toString() {
		return "Depth: " + this.depth + "\n" + this.board.toString();
	}

	/**
	 * Returns the board contained in this node
	 * 
	 * @return	The board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Sets the board in the node to the given board
	 * 
	 * @param board	new board
	 */
	public void setBoard(Board newBoard) {
		this.board = newBoard;
	}

	/**
	 * Returns the depth of the node
	 * 
	 * @return depth
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * Sets the depth of the node to the new depth
	 * 
	 * @param depth	new depth
	 */
	public void setDepth(short newDepth) {
		this.depth = newDepth;
	}

	/**
	 * Sets the parent's cost to this node's cost
	 */
	public void backUpToParent() {
		if (this.parent == null) {
			return;
		}
		if (this.parent.getCost() > this.cost) {
			this.parent.setCost(this.cost);
		}
	}

	/**
	 * Returns the location of the last move
	 * 
	 * @return location of last move
	 */
	public byte lastMove() {
		byte row = 0;
		byte col = 0;
		for(byte i=0; i<board.getSize(); i++) {
			for(byte j=0; j<board.getSize(); j++) {
				if (board.getBoard()[i][j] == 0) {
					row = i;
					col = j;
				}
			}
		}
		return (byte) ((this.board.getSize()*row) + col + 1);
	}
	
	/**
	 * Returns -1 if this node's cost is less than the other node
	 * Returns 1 if this node's cost is greater than the other node
	 * Returns 0 if the costs are equal
	 */
	@Override
	public int compareTo(Node other) {
		if (this.cost < other.getCost())
			return -1;
		if (this.cost > other.getCost())
			return 1;
		return 0;
	}

}
