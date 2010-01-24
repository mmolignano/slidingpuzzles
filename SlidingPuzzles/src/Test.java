
public class Test {
	public static void main(String []args) {
		Board b = new Board(3);
//		int [][] board = new int[3][3];
//		board[0][0] = 7;
//		board[0][1] = 2;
//		board[0][2] = 4;
//		board[1][0] = 5;
//		board[1][1] = 0;
//		board[1][2] = 6;
//		board[2][0] = 8;
//		board[2][1] = 3;
//		board[2][2] = 1;
//		b.setBoard(board);
		b.parseFile("/tmp/puzzle");
		
		Node n = new Node(b, 0);
		System.out.println(n);
		
		n.expand();
		for(Node node:n.children) {
			System.out.println(node);
		}
		
		MisplacedHeuristic h = new MisplacedHeuristic();
		RawDistance h2 = new RawDistance();
		System.out.println(h2.evaluate(b));
		
		
	}
}
