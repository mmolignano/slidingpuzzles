import java.util.Collection;
import java.util.PriorityQueue;


public class AStarPriority {
	Heuristic heuristic;
	PriorityQueue<Node> nodes;
	int expanded = 0;
	int children = 0;
	
	public AStarPriority(Heuristic h) {
		this.heuristic = h;
		this.nodes = new PriorityQueue<Node>();
	}
	
	public byte[] run(Board b, int scale) {
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
		byte [] empty = new byte [0];
		Node root = new Node(b, (short) 0, empty, row, col, null);
		setCost(root, scale);
		nodes.add(root);
		Node leastCostNode = root;
		long before = System.currentTimeMillis();
		
		while(true) {
			leastCostNode = nodes.remove();
			if (isFinishState(leastCostNode)) {
				return calculateStatistics(before, children, expanded, leastCostNode);
			}
					
			try {
				Collection <Node> subNodes = leastCostNode.expand();
				expanded++;
				for (Node n : subNodes) {
					setCost(n, scale);
					if (isFinishState(n)) {
						return calculateStatistics(before, children, expanded, n);
					}
				}
				
				nodes.addAll(subNodes);
				
				if (expanded % 5000 == 0) {
					System.out.println("Expansions: " + expanded);
					System.out.println("Nodes: " + nodes.size());
					System.out.println("Board: \n" + leastCostNode);
					System.out.println("Currently using:  " + ((runtime.totalMemory()-runtime.freeMemory()) / 1000000.0) + "MB");
					if (runtime.freeMemory() < runtime.maxMemory()/4) {
						System.out.println("Killing half of the " + nodes.size() + " nodes.");
						PriorityQueue<Node> newNodes = new PriorityQueue<Node>();
						int numToRemove = nodes.size() / 2;
						while (numToRemove > 0) {
							newNodes.add(nodes.remove());
							numToRemove--;
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
	
	private byte[] calculateStatistics(long startTime, int children, int expanded, Node end) {
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
		return end.priorMoves;
	}
	
	private boolean isFinishState(Node n) {
		return heuristic.evaluate(n.getBoard()) == 0;
	}
	
	public void setCost(Node n, int scale) {
		n.setCost((byte) (n.getDepth() + scale*heuristic.evaluate(n.getBoard())));
	}
}
