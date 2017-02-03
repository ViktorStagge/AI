package reversei;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main {
	private static int maxDepth = 7;
	
	public static final void main(String[] args){
		Input input = new Input();
		Board board = new Board();
		
		while(!board.isFinished()){
			board.display();
			if(board.nextColor() < 0){
				Move m = input.readMove();
				while(board.canPlay(m.x(), m.y()) <= 0){
					System.out.println("Silly, you can't make that move..");
					m = input.readMove();
				}
				board.play(m.x(), m.y());
			} else {
				Move move = minmaxDecision(board);
				if(move != null){
					board.play(move.x(), move.y());
					System.out.println("Computer says: " + (char)( move.y() + 97) + (move.x()+1));
				}
			}
		}
		board.display();
		System.out.println("'tis has been decided: " + (board.getWinner() < 0 ? "You WIN!" : "You lose, ha ha haa"));
	}
	
	public static Move minmaxDecision(Board board){
		long ts = System.currentTimeMillis();
		List<Move> moves = board.successors();
		moves.sort(new MoveComparator());
		Move nextMove = moves.size() > 0 ? moves.get(0) : null;
		double v = -10000000.0;
		for(Move m : moves){
			Board b = new Board(board, m);
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
		moves.sort(new MoveComparator());
		double v = -10000000.0;
		
		if(moves.size() == 0 || depth >= maxDepth) return Evaluator.eval(board);
		for(Move m  : moves){
			Board b = new Board(board, m);
			double min = minValue(b, alpha, beta, depth+1);
			if(min > v) v = min;
			if(v > beta) return v;
			if(v > alpha) alpha = v;
		}
		return v;
	}
	
	public static double minValue(Board board, double alpha, double beta, int depth){
		List<Move> moves = board.successors();
		moves.sort(new MoveComparator());
		double v = Double.MAX_VALUE;
		
		if(moves.size() == 0 || depth >= maxDepth) return Evaluator.eval(board);
		for(Move m : moves){
			Board b = new Board(board, m);
			double max = maxValue(b, alpha, beta, depth+1);
			if(max < v) v = max;
			if(v < alpha) return v;
			if(v < beta) beta = v;
		}
		return v;
	}
}
