------------------
CS4341 - Project 1
------------------
Thomas Liu (kangchao@wpi.edu)
Rui Dai (ruidai@wpi.edu) 
Michael Molignano (mikem@wpi.edu)
Kevin Nolan (knolan@wpi.edu)

--------------------------------
8-puzzle stats (over 5 puzzles):
--------------------------------
Misplaced:
Average Depth: 21.2
Average branching factor: 1.72
Average number of nodes expanded: 16032
Average amount of time spent: .087s
Average nodes per second: 83703

Manhattan:
Average Depth: 21.2
Average branching factor: 1.69
Average number of nodes expanded: 1274
Average amount of time spent: .039s
Average nodes per second: 33581

Raw Distance:
Average Depth: 21.2
Average branching factor: 1.68
Average number of nodes expanded: 3442
Average amount of time spent: .097s
Average nodes per second: 46329

---------------------------------
15-puzzle stats (over 5 puzzles):
---------------------------------
Misplaced:
Average Depth: 46.4
Average branching factor: 2.171649556
Average number of nodes expanded: 5709936.8
Average amount of time spent: 50.9884s
Average nodes per second: 130188.5182

Manhattan:
Average Depth: 62.4
Average branching factor: 2.090723417
Average number of nodes expanded: 12203.2
Average amount of time spent: 0.162s
Average nodes per second: 535954.3474

Raw:
Average Depth: 47.2
Average branching factor: 2.129012572
Average number of nodes expanded: 6572.2
Average amount of time spent: 0.1774s
Average nodes per second: 509786.103

---------------------------------
24 Puzzle Stats (Over 6 puzzles):
---------------------------------
Average Depth: 116
Average branching factor: 2.274600
Average number of nodes expanded: 22798035
Average amount of time spent: 290.833s
Average nodes per second: 81445

Instructions:
--------
BUILDING
--------
To compile our project, navigate to the directory and execute "javac NPuzzle.java".

--------
USAGE
--------
java -Xmx1000m NPuzzle (BOARD_SIZE) (FILENAME) [manhattan | misplaced | raw] [--fast | --best]
- Defaults to fast mode if one of best and fast is not selected.
- Defaults to manhattan if heuristic is unspecified.
- The first argument after java allocates the JVM heap space.  Give it as much as you can!  The above example gives it 1GB, which we've found works well.  
- You can also use java -NPuzzle (BOARD_SIZE) --rand to generate a random board.
EXAMPLE:
java -Xmx1000m NPuzzle 4 15-1 manhattan --best
- Gives the JVM 1000m of heap space.
- Runs the program on a 4x4 puzzles
- Reads input from file 15-1
- Uses the manhattan heuristic
- Finds an optimal solution

Every 5000 expansions the program prints out how much memory it's currently using, how many nodes are in memory and how many expansions it's done.
It also prints out the current node depth and boad that it's looking at.

We solved the memory and speed problem with a few techniques:
- We removed immediate backtracking.  This means that an expanded node cannot create its parent as one of its children, before that node is expanded.
- When searching for optimal paths, we have implemented a modified SMA*.  When running out of memory, we back up 50% of the nodes to their parents.
- When searching for non-optimal paths as fast as possible, we simply drop the highest 50% of the nodes.

We've included the puzzles we ran tests on in the puzzles folder.

------------
EXTRA CREDIT
------------
Our program prints IMPOSSIBLE if the puzzle is impossible.
Our program solves 15 puzzles optimally (use --best) pretty quickly.
Our program solves 24 puzzles in a small number of moves which may or may not be optimal (use --fast).  (Usually takes <15 minutes)
Our program probably solves 24 puzzles optimally (use --best) but it takes a while.

Source of determining the solvability of a puzzle: http://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html