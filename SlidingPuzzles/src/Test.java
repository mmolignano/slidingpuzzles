import java.util.ArrayList;


public class Test {
	public static void main(String []args) {
		try {
			ArrayList<Integer> arr = new ArrayList<Integer>();
			System.out.println("Claiming heap space by filling it with trash.");
			while(true) {
				arr.add(new Integer(Integer.MAX_VALUE));
			}
		} catch (OutOfMemoryError err) {
			System.out.println("Ok, let's go.");
		}
		Board b = new Board((byte)3);
		b.parseFile("C:\\Users\\thomas\\workspace\\Coolthings\\src\\puzzles\\8-1");
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
