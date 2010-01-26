import java.util.ArrayList;
import java.util.Random;


public class GenerateRandomBoard {
	public static Board generateBoard(int size) {
		
		Board b = new Board(size);
		do {
			int[][] board = new int[size][size];
			ArrayList<Integer> nums = generateRandomSequence(size * size);
			for (int i=0; i<size; i++) {
				for (int j=0; j<size; j++) {
					board[i][j] = nums.remove(0);
				}
			}
			b.setBoard(board);
		} while(!b.isSolvable());
		return b;
	}
	private static ArrayList<Integer> generateRandomSequence(int range) {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		Random r = new Random();
		if (range == 1) {
			arr.add(0);
			return arr;
		} else {
			int rand = r.nextInt(range);
			arr = generateRandomSequence(range-1);
			for(int i=0; i<arr.size(); i++) {
				if(arr.get(i) >= rand) {
					arr.set(i, arr.get(i)+1);
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
