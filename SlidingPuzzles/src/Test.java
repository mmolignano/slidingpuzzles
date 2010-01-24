
public class Test {
	public static void main(String []args) {
		Board b = new Board(4);
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
		b.parseFile("C:\\Users\\kangchao\\Desktop\\15.txt");
		
		
		ManhattanDistance h = new ManhattanDistance();
		
		AStar alg = new AStar(h);
		int [] answer = alg.run(b);
		for(int i=0;i<answer.length; i++){
			System.out.print(answer[i] + ", ");
		}
		
	}
}
