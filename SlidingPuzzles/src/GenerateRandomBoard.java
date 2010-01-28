import java.util.ArrayList;
import java.util.Random;

/**
 * This class helps generate a random board for our program to solve
 * @author Thomas Liu (kangchao@wpi.edu), Rui Dai (ruidai@wpi.edu) Michael Molignano (mikem@wpi.edu), Kevin Nolan (knolan@wpi.edu)
 *
 */
public class GenerateRandomBoard {
	/**
	 * A static function to generate a random board of a given size
	 * @param size the size of the board
	 * @return a random Board of the given size
	 */
	public static Board generateBoard(int size) {
		
		Board b = new Board((byte)size);
		do {
			byte[][] board = new byte[size][size];
			ArrayList<Byte> nums = generateRandomSequence(size * size);
			for (int i=0; i<size; i++) {
				for (int j=0; j<size; j++) {
					board[i][j] = nums.remove(0);
				}
			}
			b.setBoard(board);
		} while(!b.isSolvable());
		return b;
	}
	
	/**
	 * Helper function to generate a random sequence
	 * @param range a given range
	 * @return an array list of a random sequence
	 */
	private static ArrayList<Byte> generateRandomSequence(int range) {
		ArrayList<Byte> arr = new ArrayList<Byte>();
		Random r = new Random();
		if (range == 1) {
			arr.add((byte)0);
			return arr;
		} else {
			byte rand = (byte)r.nextInt(range);
			arr = generateRandomSequence(range-1);
			for(int i=0; i<arr.size(); i++) {
				if(arr.get(i) >= rand) {
					arr.set(i, (byte) (arr.get(i)+1));
				}
			}
			arr.add(rand);
		}
		return arr;
	}
	
}
