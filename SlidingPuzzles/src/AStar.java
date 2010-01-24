import java.util.ArrayList;


public class AStar {
	Heuristic heuristic;
	ArrayList<Node> nodes;
	public AStar(Heuristic h) {
		this.heuristic = h;
		this.nodes = new ArrayList<Node>();
	}
}
