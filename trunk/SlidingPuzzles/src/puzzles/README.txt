Thomas Liu (kangchao@wpi.edu), Rui Dai (ruidai@wpi.edu) Michael Molignano (mikem@wpi.edu), Kevin Nolan (knolan@wpi.edu)

8-puzzle stats:

Misplaced:
Depth:21.2
Average Banching Factor:1.72
Expanded nodes:16032
Time: .087
Nodes/second: 83703

Manhattan:
Depth:21.2
Average Banching Factor: 1.69
Expanded nodes:1274
Time: .039
Nodes/second: 33581

Raw Distance:
Depth:21.2
Average Banching Factor:1.68
Expanded nodes:3442
Time: .097
Nodes/second: 46329


15-puzzle stats

Misplaced:
Depth: 46.4
Average branching factor: 2.171649556
Nodes expanded: 5709936.8
Time for expansions: 50.9884
Average nodes/sec: 130188.5182

Manhattan:
Depth: 62.4
Average branching factor: 2.090723417
Nodes expanded: 12203.2
Time for expansions: 0.162
Average nodes/sec: 535954.3474

Raw:
Depth:47.2
Average branching factor: 2.129012572
Nodes expanded: 6572.2
Time for expansions: 0.1774
Average nodes/sec: 509786.103

24 Puzzle Stats (Over 6 puzzles):
Average Depth: 116
Average branching factor: 2.274600
Average number of nodes expanded: 22798035
Average amount of time spent: 290.833 s
Average nodes per second: 81445

Instructions:
To compile our project, navigate to the directory and excecute "javac NPuzzle.java".
Usage:
java -Xmx2000m -jar npuzzle.jar (BOARD_SIZE) (FILENAME) [manhattan | misplaced | raw] [--fast | --best]
Defaults to fast mode if one of best and fast is not selected.
The first argument after java allocates the JVM heap space.  Give it as much as you can!  The above example gives it 2GB.
You can also use java -jar npuzzle.jar (BOARD_SIZE) --rand to generate a random board.

We solved the memory and speed problem with a few techniques:
-We removed immediate backtracking.  This means that an expanded node cannot create it's parent as one of it's children, before that node is expanded.
-