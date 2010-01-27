import java.util.ArrayList;


public class Main {
	public static void main(String [] args) {
		if (args.length == 0 || (args.length!= 2 && args.length != 3) || args[0].equals("--help")) {
			System.out.println("Usage:");
			System.out.println("java -Xmx2000m -jar npuzzle.jar (BOARD_SIZE) (FILENAME) [--best | --fast]");
			System.out.println("Defaults to fast mode if one of best and fast is not selected.");
			System.out.println("The first argument after java allocates the JVM heap space.  Give it as much as you can!  The above example gives it 2GB.");
			System.out.println("\nYou can also use java -jar npuzzle.jar (BOARD_SIZE) --rand to generate a random board.");
			return;
		}
		
		int size = Integer.parseInt(args[0]);
		
		if (args.length == 2 && args[1].equals("--rand")) {
			System.out.println(GenerateRandomBoard.generateBoard(size));
			return;
		}
		
		String filename = args[1];
		boolean fast = true;
		if (args[2].equals("--best")) {
			fast = false;
		}

		Board b = new Board((byte)size);
		
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
		
		b.parseFile(filename);
		Runtime r = Runtime.getRuntime();
		System.out.println("Max memory allotted: " + (r.totalMemory() / 1000000.0) + "MB");
		
		Heuristic h = new ManhattanDistance();
		
		AStarPriority alg = new AStarPriority(h);
		int scale = 1;
		if(fast) {
			if (size == 4) {
				scale = 3;
			} else if (size == 5) {
				scale = 8;
			}
		}
		/*
		 * Max number of moves in optimal solution for
		 * 8 puzzle: 31
		 * 15 puzzle: 80
		 * 24 puzzle: assumed to be 200
		 */
		int maxDepth = 31;
		if (size == 4) {
			maxDepth = 80;
		} else if (size == 5) {
			maxDepth = 200;
		}
		
		String answer = alg.run(b, scale, maxDepth);
		
		if (answer != null) {
			System.out.println(answer.substring(0,answer.length()-2));
		} else {
			System.out.println("It seems there was no solution within a depth of " + maxDepth);
		}
		
	}
}
