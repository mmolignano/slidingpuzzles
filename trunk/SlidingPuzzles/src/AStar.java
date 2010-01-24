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
		int depthCheck = 0;
		Node leastCostNode = root;
		while(leastCostNode.getDepth() < 200) {
			leastCostNode = null;
			int leastCost = -1;
			
			for (Node n : nodes) {
				if (n.getCost() < 0) {
					int heuristicValue = heuristic.evaluate(n.getBoard());
					if (heuristicValue == 0) {
						System.out.println("Depth: " + n.getDepth());
						double total = 0;
						total = (double)children / (double)expanded;
						System.out.println(total);
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
			depthCheck ++;
			expanded++;
			nodes.addAll(leastCostNode.getChildren());
			if (depthCheck % 5000 == 0) {
				System.out.println("Expansions: " + depthCheck);
				System.out.println("Depth: " + leastCostNode.getDepth());
			} 
			if(!leastCostNode.children.isEmpty()){
				children += leastCostNode.children.size();
			}
		}
		return null;
	}
}
