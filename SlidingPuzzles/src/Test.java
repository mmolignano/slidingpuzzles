
public class Test {
	public static void main(String []args) {
		Board b = new Board((byte)5);
		b.parseFile("/home/tliu/devel/eclipse/SlidingPuzzles/src/puzzles/24");
		Runtime r = Runtime.getRuntime();
		System.out.println("Max memory allotted: " + (r.totalMemory() / 1000000.0) + "MB");
		
		Heuristic h = new ManhattanDistance();
		
		AStarPriority alg = new AStarPriority(h);
		byte [] answer = alg.run(b,1);
		
		if (answer != null) {
			for(int i=0;i<answer.length; i++){
				System.out.print(answer[i] + ", ");
			}
		}
		
	}
}
