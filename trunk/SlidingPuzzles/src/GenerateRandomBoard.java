import java.util.ArrayList;
import java.util.Random;


public class GenerateRandomBoard {
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
	public static void main(String [] args){
		System.out.println(generateBoard(6));
	}
	
}
