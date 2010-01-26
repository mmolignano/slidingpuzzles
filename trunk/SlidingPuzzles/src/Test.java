
public class Test {
	public static void main(String []args) {
		Board b = new Board((byte)4);
		b.parseFile("C:\\Users\\knolan\\Desktop\\puzzle.txt");
		
		
		Heuristic h = new ManhattanDistance();
		
		AStar alg = new AStar(h);
		byte [] answer = alg.run(b,3);
		
		if (answer != null) {
			for(int i=0;i<answer.length; i++){
				System.out.print(answer[i] + ", ");
			}
		}
		
	}
}
