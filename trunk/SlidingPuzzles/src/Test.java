
public class Test {
	public static void main(String []args) {
		Board b = new Board(3);
		b.parseFile("R:\\workspace\\Coolthings\\src\\puzzles\\8-1");
		
		
		Heuristic h = new RawDistance();
		
		AStar alg = new AStar(h);
		int [] answer = alg.run(b);
		for(int i=0;i<answer.length; i++){
			System.out.print(answer[i] + ", ");
		}
		
	}
}
