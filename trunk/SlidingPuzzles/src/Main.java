import java.util.ArrayList;


public class Main {
	public static void main(String []args) {
		Board b = new Board((byte)5);
		
		if (b.size > 4) {
			try {
				ArrayList<Integer> arr = new ArrayList<Integer>();
				System.out.println("Claiming heap space by filling it with trash.");
				while(true) {
					arr.add(new Integer(Integer.MAX_VALUE));
				}
			} catch (OutOfMemoryError err) {
				System.out.println("Ok, let's go.");
			}
		}
		
		b.parseFile("R:\\workspace\\Coolthings\\src\\puzzles\\24-2");
		Runtime r = Runtime.getRuntime();
		System.out.println("Max memory allotted: " + (r.totalMemory() / 1000000.0) + "MB");
		
		Heuristic h = new ManhattanDistance();
		
		AStarPriority alg = new AStarPriority(h);
		String answer = alg.run(b,1, 200);
		
		if (answer != null) {
			System.out.println("Answer: " + answer);
		}
		
	}
}
