
public class MisplacedHeuristic implements Heuristic {

	@Override
	public int evaluate(Board b) {
		int counter = 0;
		for(int i=0; i<b.getSize(); i++) {
			for (int j=0; j<b.getSize(); j++) {
				if (b.getBoard()[i][j] == (b.getSize()*i) + j) {
					counter++;
				}
			}
		}
		return counter;
	}

}
