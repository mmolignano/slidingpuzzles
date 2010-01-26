import java.util.ArrayList;


public class AStar {
	Heuristic heuristic;
	ArrayList<Node> nodes;
	int expanded = 0;
	int children = 0;
	
	public AStar(Heuristic h) {
		this.heuristic = h;
		this.nodes = new ArrayList<Node>();
	}
	
	public int[] run(Board b, int scale) {
		
		if (!b.isSolvable()) {
			System.out.println("Sorry, it looks like this puzzle is IMPOSSIBLE to solve.");
			return null;
		}
		int row = 0;
		int col = 0;
		for (int i=0; i<b.getBoard().length; i++) {
			for (int j=0; j<b.getBoard().length; j++) {
				if (b.getBoard()[i][j] == 0) {
					row = i;
					col = j;
				}
			}
		}
		int [] empty = new int [0];
		Node root = new Node(b, 0, empty, row, col, null);
		nodes.add(root);
		Node leastCostNode = root;
		long before = System.currentTimeMillis();
		while(true) {
			leastCostNode = null;
			int leastCost = -1;
			
			for (Node n : nodes) {
				if (n.getCost() < 0) {
					int heuristicValue = heuristic.evaluate(n.getBoard());
					if (heuristicValue == 0) {
						System.out.println("Depth: " + n.getDepth());
						double total = 0;
						total = (double)children / (double)expanded;
						System.out.println("Average branching factor: " + total);
						long after = System.currentTimeMillis();
						double time = after-before;
						time = (double)time / 1000.0;
						System.out.println("Expanded " + expanded + 
										   " nodes in " + time + 
										   " seconds (" + ((double)expanded / time) + " nodes per second)");
						return n.priorMoves;
					} else {
						n.cost = n.depth + scale*heuristicValue;
					}
				}
				if (leastCostNode == null) {
					leastCostNode = n;
					leastCost = n.getCost();
					continue;
				}
				if (n.getCost() < leastCost) {
					leastCostNode = n;
					leastCost = n.getCost();
				}
			}

			ArrayList<Node> subNodes = leastCostNode.expand();
			expanded++;
			nodes.addAll(subNodes);
			nodes.remove(leastCostNode);
			if (expanded % 5000 == 0) {
				System.out.println("Expansions: " + expanded);
				System.out.println("Nodes: " + nodes.size());
				System.out.println("Board: \n" + leastCostNode); 
			} 
			if(!subNodes.isEmpty()){
				children += subNodes.size();
			}
		}
	}
}
