package reversei;

import java.util.Comparator;

public class MoveComparator implements Comparator<Move>{
	public int compare(Move m1, Move m2){
		return Integer.compare(Evaluator.valueMatrix[m2.x][m2.y], Evaluator.valueMatrix[m1.x][m1.y]);
	}
}
