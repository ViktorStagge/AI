package reversei;

import java.util.List;

public class Evaluator {
	public static int[][] valueMatrix = new int[][]{
		{ 99, -8, 8, 6, 6, 8, -8, 99 },
		{-8, -24, -4, -3, -3, -4, -24, -8},
		{8, -6, 7, 4, 4, 7, -6, 8},
		{6, -3, 4, 0, 0, 4, -3, 6},
		{6, -3, 4, 0, 0, 4, -3, 6},
		{8, -6, 7, 4, 4, 7, -6, 8},
		{-8, -24, -4, -3, -3, -4, -24, -8},
		{ 99, -8, 8, 6, 6, 8, -8, 99 },
	};
	
	public static double eval(Board board){
		if(board.isFinished()) { return board.getWinner() * 10000000.0; }
		
		int value = 0;
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				value += board.peek(i, j)*(valueMatrix[i][j]);
			}
		}
//		System.out.println("evalued as: " + value * board.nextColor());
		return value;
	}
}
