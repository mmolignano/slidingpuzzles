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
				value += Math.floor(Math.sqrt(Math.pow(Math.abs(row - i), 2)  + Math.pow(Math.abs(col - j), 2)));
			}
		}
		return value;
	}
}
