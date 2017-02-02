package reversei;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main {
	private static int maxDepth = 8;
	
	public static final void main(String[] args){
		Input input = new Input();
		Board board = new Board();
		int parity = -1;
		
		while(true){
			board.display();
			Move m = input.readMove();
			while(board.canPlay(m.x(), m.y(), parity) <= 0){
				System.out.println("Silly, you can't make that move..");
				m = input.readMove();
			}
			board.play(m.x(), m.y(), parity);
			parity = -parity;
			
			board.display();
			Move move = minmaxDecision(board);
			if(move != null){
				board.play(move.x(), move.y(), parity);
				parity = -parity;
				System.out.println("Computer says: " + (char)( move.y() + 97) + (move.x()+1));
			}
		}
	}
	
	public static Move minmaxDecision(Board board){
		long ts = System.currentTimeMillis();
		List<Move> moves = board.successors();
		Move nextMove = moves.size() > 0 ? moves.get(0) : null;
		double v = -10000000.0;
		for(Move m : moves){
			Board b = board.copy(m);
			double min = minValue(b, -10000000.0, 10000000.0, 0);
			//System.out.println("move value: skill=" + min + ", x=" + m.x() + ", y=" + m.y());
			if(min > v) {
				v = min;
				nextMove = m;
				// System.out.println("changing move to: x=" + m.x() + ", y=" + m.y());
			}
		}
		System.out.println("Move made: skill=" + v + ", time=" + (System.currentTimeMillis()-ts)/1000.0);
		return nextMove;
	}
	
	public static double maxValue(Board board, double alpha, double beta, int depth){
		List<Move> moves = board.successors();
		double v = -10000000.0;
		
		if(moves.size() == 0 || depth >= maxDepth) return Evaluator.eval(board);
		for(Move m  : moves){
			Board b = board.copy(m);
			double min = minValue(b, alpha, beta, depth+1);
			if(min > v) v = min;
			if(v > beta) return v;
			if(v > alpha) alpha = v;
		}
		return v;
	}
	
	public static double minValue(Board board, double alpha, double beta, int depth){
		List<Move> moves = board.successors();
		double v = Double.MAX_VALUE;
		
		if(moves.size() == 0 || depth >= maxDepth) return Evaluator.eval(board);
		for(Move m : moves){
			Board b = board.copy(m);
			double max = maxValue(b, alpha, beta, depth+1);
			if(max < v) v = max;
			if(v < alpha) return v;
			if(v < beta) beta = v;
		}
		return v;
	}
}
