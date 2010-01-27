import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;


public class AStarPriority {
	Heuristic heuristic;
	PriorityBlockingQueue<Node> nodes;
	ArrayList<Node> closedNodes;
	int expanded = 0;
	int children = 0;
	
	public AStarPriority(Heuristic h) {
		this.heuristic = h;
		this.nodes = new PriorityBlockingQueue<Node>();
		this.closedNodes = new ArrayList<Node>();
	}
	
	public String run(Board b, int scale, int maxDepth, boolean fast) {
		Runtime runtime = Runtime.getRuntime();
		if (!b.isSolvable()) {
			System.out.println("Sorry, it looks like this puzzle is IMPOSSIBLE to solve.");
			return null;
		}
		byte row = 0;
		byte col = 0;
		for (byte i=0; i<b.getBoard().length; i++) {
			for (byte j=0; j<b.getBoard().length; j++) {
				if (b.getBoard()[i][j] == 0) {
					row = i;
					col = j;
				}
			}
		}
		Node root = new Node(b, (short) 0, row, col, null, null);
		setCost(root, scale);
		nodes.add(root);
		Node leastCostNode = root;
		long before = System.currentTimeMillis();
		
		while(true) {
			if (nodes.size() == 0) {
				return null;
			}
			leastCostNode = nodes.remove();
			if (isFinishState(leastCostNode)) {
				return calculateStatistics(before, children, expanded, leastCostNode);
			}
					
			try {
				Collection <Node> subNodes = leastCostNode.expand((short)maxDepth);
				expanded++;
				for (Node n : subNodes) {
					setCost(n, scale);
					if (isFinishState(n)) {
						return calculateStatistics(before, children, expanded, n);
					}
					
				}
				
				nodes.addAll(subNodes);
				leastCostNode.setCost(Short.MAX_VALUE);
				
				nodes.add(leastCostNode);
				if (expanded % 5000 == 0) {
					System.out.println("Expansions: " + expanded);
					System.out.println("Nodes: " + nodes.size());
					System.out.println("Board: \n" + leastCostNode);
					System.out.println("Currently using:  " + ((runtime.totalMemory()-runtime.freeMemory()) / 1000000.0) + "MB");
					if (runtime.freeMemory() < runtime.totalMemory()/4) {
						System.out.println("Backing up the last 50% of nodes.");
						PriorityBlockingQueue<Node> newNodes = new PriorityBlockingQueue<Node>();
						double numToKeep = nodes.size() * 0.5 ;
						while (numToKeep > 0) {							
							newNodes.add(nodes.remove());
							numToKeep--;
						}
						if (!fast) {
							for (Node n : nodes) {
								if (n.getCost() == Short.MAX_VALUE) {
									newNodes.add(n);
								}
								
								if (n.getCost() < n.parent.getCost()) {
									n.parent.setCost(n.getCost());
									nodes.remove(n.parent);
									newNodes.add(n.parent);
								}

							}
							nodes = newNodes;
							runtime.gc();
						}
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
	
	private boolean isFinishState(Node n) {
		return heuristic.evaluate(n.getBoard()) == 0;
	}
	
	public void setCost(Node n, int scale) {
		n.setCost((byte) (n.getDepth() + scale*heuristic.evaluate(n.getBoard())));
	}
}
