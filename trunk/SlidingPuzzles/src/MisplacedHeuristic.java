/**
 * Misplaced heuristic that implements Heuristic 
 * Definition: Misplaced counts the total tiles not in the correct position.
 * @author Thomas Liu (kangchao@wpi.edu), Rui Dai (ruidai@wpi.edu) Michael Molignano (mikem@wpi.edu), Kevin Nolan (knolan@wpi.edu)
 *
 */
public class MisplacedHeuristic implements Heuristic {

	@Override
	public int evaluate(Board b) {
		int counter = 0;
		for(int i=0; i<b.getSize(); i++) {
			for (int j=0; j<b.getSize(); j++) {
				if (b.getBoard()[i][j] != ((b.getSize()*i) + 1) + j && b.getBoard()[i][j] != 0) {
					counter++;
				}
			}
		}
		return counter;
	}

}
