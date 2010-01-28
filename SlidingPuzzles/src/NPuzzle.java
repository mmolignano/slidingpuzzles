import java.util.ArrayList;


public class NPuzzle {
	public static void main(String [] args) {
		// Parse input arguments
		if (args.length == 0 || (args.length!= 2 && args.length != 3 && args.length != 4) || args[0].equals("--help")) {
			System.out.println("Usage:");
			System.out.println("java -Xmx2000m NPuzzle (BOARD_SIZE) (FILENAME) [manhattan | misplaced | raw] [--fast | --best]");
			System.out.println("Defaults to fast mode if one of best and fast is not selected.");
			System.out.println("The first argument after java allocates the JVM heap space.  Give it as much as you can!  The above example gives it 2GB.");
			System.out.println("\nYou can also use java NPuzzle (BOARD_SIZE) --rand to generate a random board.");
			return;
		}
		
		int size = Integer.parseInt(args[0]);
		
		if (args.length == 2 && args[1].equals("--rand")) {
			System.out.println(GenerateRandomBoard.generateBoard(size));
			return;
		}
		
		String filename = args[1];
		boolean fast = true;
		Heuristic h = new ManhattanDistance();
		if (args[2].equals("misplaced")) {
			h = new MisplacedHeuristic();
			System.out.println("Using misplaced heuristic.");
		} else if (args[2].equals("raw")) {
			h = new RawDistance();
			System.out.println("Using raw distance heuristic.");
		} else {
			System.out.println("Using manhattan distance heuristic.");
		}
		
		if (args[3].equals("--best")) {
			fast = false;
		}

		Board b = new Board((byte)size);
		
		/**
		 * The JVM has a heap size by default and a max heap size.
		 * This forces the JVM to allocate the most heap space that it can.
		 */
		if (b.size > 3) {
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
		
		
		AStarPriority alg = new AStarPriority(h);
		
		/*
		 * If they want to go fast, run the 15 puzzle at 3x
		 * We run the 24 puzzle at 1x with node chopping.
		 */
		int scale = 1;
		if(fast) {
			if (size == 4) {
				scale = 3;
			} else if (size == 5) {
				scale = 1;
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
			if (fast)
				maxDepth = 240;
		} else if (size == 5) {
			maxDepth = 200;
			if (fast)
				maxDepth = 1600;
		}
		

		String answer = alg.run(b, scale, maxDepth, fast);
		
		if (answer != null) {
			System.out.println(answer.substring(0,answer.length()-2));
		} else {
			System.out.println("It seems there was no solution within a depth of " + maxDepth);
		}

	}
}
