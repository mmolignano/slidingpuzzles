
public class Test {
	public static void main(String []args) {
		Board b = new Board(4);
		b.parseFile("C:\\Users\\thomas\\workspace\\Coolthings\\src\\puzzles\\15-1");
		
		
		Heuristic h = new RawDistance();
		
		AStar alg = new AStar(h);
		int [] answer = alg.run(b,3);
		
		if (answer != null) {
			for(int i=0;i<answer.length; i++){
				System.out.print(answer[i] + ", ");
			}
		}
		
	}
}
