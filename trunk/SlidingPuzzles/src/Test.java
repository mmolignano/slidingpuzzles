
public class Test {
	public static void main(String []args) {
		Board b = new Board(4);
		b.parseFile("C:\\Users\\mikem\\Documents\\workspace\\SlidingPuzzles\\src\\puzzles\\15-1");
		
		
		ManhattanDistance h = new ManhattanDistance();
		
		AStar alg = new AStar(h);
		int [] answer = alg.run(b);
		for(int i=0;i<answer.length; i++){
			System.out.print(answer[i] + ", ");
		}
		
	}
}
