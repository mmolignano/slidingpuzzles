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
	
	public int[] run(Board b) {
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
		Node root = new Node(b, 0, empty, row, col);
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
						n.cost = n.depth + 3*heuristicValue;
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
			
			nodes.remove(leastCostNode);
			leastCostNode.expand();
			expanded++;
			nodes.addAll(leastCostNode.getChildren());
			if (expanded % 5000 == 0) {
				System.out.println("Expansions: " + expanded);
				System.out.println("Board: \n" + leastCostNode); 
			} 
			if(!leastCostNode.children.isEmpty()){
				children += leastCostNode.children.size();
			}
		}
	}
}
