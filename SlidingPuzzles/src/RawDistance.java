/**
 * Raw distance heuristic that implements Heuristic
 * Definition of Raw Distance:  similar to Manhattan distance, but allows diagonal movements of tiles
 * @author Thomas Liu (kangchao@wpi.edu), Rui Dai (ruidai@wpi.edu) Michael Molignano (mikem@wpi.edu), Kevin Nolan (knolan@wpi.edu)
 * 
 *
 */
public class RawDistance implements Heuristic{


	public int evaluate(Board b) {
		int value = 0;
		for (int i = 0; i < b.getSize(); i++) {
			for (int j = 0; j < b.getSize(); j++) {
				int curr = b.getBoard()[i][j];
				if (curr == 0)
					continue;
				int row = (curr - 1) / b.getSize();
				int col = (curr - 1) % b.getSize();
				row = Math.abs(row-i);
				col = Math.abs(col-j);
				value += (row > col) ? row : col;
			}
		}
		return value;
	}
}
