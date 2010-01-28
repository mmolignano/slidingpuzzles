import java.util.Collection;
import java.util.PriorityQueue;



public class AStarPriority {
	//The heuristic to use for the search.
	Heuristic heuristic;

	//A priority queue to put the expanded nodes in.
	PriorityQueue<Node> nodes;
	//The total number of nodes expanded
	int expanded = 0;
	//The total number of children in the search tree.
	int children = 0;
	
	/**
	 * Makes a new search using the given heuristic.
	 * @param h
	 */
	public AStarPriority(Heuristic h) {
		this.heuristic = h;
		this.nodes = new PriorityQueue<Node>();
	}
	
	/**
	 * Runs an A* search on the given board, with the given maximum depth, and the given heuristic multiplier.
	 * @param b The board to search for a solution.
	 * @param scale The number to multiply the solution by.
	 * @param maxDepth The maximum depth to search.
	 * @return A string representing the path from the starting state to the end state.
	 */
	public String run(Board b, int scale, int maxDepth, boolean fast) {
		Runtime runtime = Runtime.getRuntime();
		if (!b.isSolvable()) {
			System.out.println("Sorry, it looks like this puzzle is IMPOSSIBLE to solve.");
			return null;
		}

		//Initialize the search.
		Node root = new Node(b, (short) 0, null, null);
		setCost(root, scale);
		nodes.add(root);
		Node leastCostNode = root;
		long before = System.currentTimeMillis();
		
		//Main loop
		while(true) {
			//If there are no nodes or we found the solution, finish and return.
			if (nodes.size() == 0) {
				return null;
			}
			leastCostNode = nodes.remove();
			if (isFinishState(leastCostNode)) {
				return calculateStatistics(before, children, expanded, leastCostNode);
			}
					
			try {
				//Expand the lowest node.
				Collection <Node> subNodes = leastCostNode.expand((short)maxDepth);
				expanded++;
				for (Node n : subNodes) {
					setCost(n, scale);
					if (isFinishState(n)) {
						return calculateStatistics(before, children, expanded, n);
					}
				}
				
				nodes.addAll(subNodes);
				
				//Every 5000 expansions, print useful information.
				if (expanded % 5000 == 0) {
					System.out.println("Expansions: " + expanded);
					System.out.println("Nodes: " + nodes.size());
					System.out.println("Board: \n" + leastCostNode);
					System.out.println("Currently using:  " + ((runtime.totalMemory()-runtime.freeMemory()) / 1000000.0) + "MB");
					if (runtime.freeMemory() < runtime.totalMemory()/3) {
						System.out.println("Backing up the last 50% of nodes.");
						PriorityQueue<Node> newNodes = new PriorityQueue<Node>();
						double numToRemove = nodes.size() * 0.5;
						while (numToRemove > 0) {							
							newNodes.add(nodes.remove());
							numToRemove--;
						}
						if (!fast) {
							for (Node n : nodes) {
								n.backUpToParent();
								if (n.parent != null) {
									newNodes.add(n.parent);
									n.parent = null;
								}
							}
						}
						nodes = newNodes;
						runtime.gc();
						System.out.println(nodes.size() + " nodes remain.");
					}
				} 
				children += subNodes.size();
				
				
			} catch (OutOfMemoryError err) {
				System.err.println("Out of memory.");
				return null;
			}
			
		}
	}
	
	/**
	 * Calculates the statistics we need to give for our report.
	 * @param startTime The time the search started.
	 * @param children The total number of children generates.
	 * @param expanded The total number of nodes expanded.
	 * @param end The solution node.
	 * @return A string representing the statistics we need.
	 */
	private String calculateStatistics(long startTime, int children, int expanded, Node end) {
		System.out.println("Depth: " + end.getDepth());
		double total = 0;
		total = (double)children / (double)expanded;
		System.out.println("Average branching factor: " + total);
		long after = System.currentTimeMillis();
		double time = after-startTime;
		time = (double)time / 1000.0;
		System.out.println("Expanded " + expanded + 
						   " nodes in " + time + 
						   " seconds (" + ((double)expanded / time) + " nodes per second)");
		String priorMoves = "";
		while (end.parent != null) {
			priorMoves = end.lastMove() + ", "  + priorMoves; 
			end = end.parent;
		}
		return priorMoves;
	}
	
	/**
	 * Returns true if the current node is a solution node.
	 * @param n The node to test.
	 * @return True if the node is a final state, false otherwise.
	 */
	private boolean isFinishState(Node n) {
		return heuristic.evaluate(n.getBoard()) == 0;
	}
	
	/**
	 * Sets the heuristic value of the node based on the current heuristic and scale.
	 * @param n The node to assign a cost to.
	 * @param scale The scale to apply to the heuristic.
	 */
	public void setCost(Node n, int scale) {
		n.setCost((byte) (n.getDepth() + scale*heuristic.evaluate(n.getBoard())));
	}
}
