/**
 * Manhattan distance heuristic that implements Heuristic
 * @author Thomas Liu (kangchao@wpi.edu), Rui Dai (ruidai@wpi.edu) Michael Molignano (mikem@wpi.edu), Kevin Nolan (knolan@wpi.edu)
 *
 */
public class ManhattanDistance implements Heuristic {

	@Override
	public int evaluate(Board b) {
		int value = 0;
		for(int i=0; i<b.getSize(); i++) {
			for (int j=0; j<b.getSize(); j++) {
				int curr = b.getBoard()[i][j];
				if (curr == 0)
					continue;
				int row = (curr-1) / b.getSize();
				int col = (curr-1) % b.getSize();
				value += Math.abs(row-i) + Math.abs(col-j);
			}
		}
		return value;
	}

}
