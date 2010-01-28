/**
 * Interface to make different heuristics with.
 * @author Thomas Liu (kangchao@wpi.edu), Rui Dai (ruidai@wpi.edu) Michael Molignano (mikem@wpi.edu), Kevin Nolan (knolan@wpi.edu)
 *
 */
public interface Heuristic {
	/**
	 * Takes a board and returns the total heuristic value for a particular heuristic on that board.
	 * @param b The board to run an heuristic on.
	 * @return The total heuristic value.
	 */
	public int evaluate(Board b);
}
