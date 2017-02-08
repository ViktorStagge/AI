package reversei;

import java.util.List;

public class MinMax {
	private int maxDepth = 7;
	private long maxTime = 5000;	
	private long startTime;	
	
	public MinMax(int maxTime){
		this.maxTime = maxTime;
	}
	
	public Move decision(Board board){
		startTime = System.currentTimeMillis();
		List<Move> moves = board.successors();
		moves.sort(new MoveComparator());
		Move nextMove = moves.size() > 0 ? moves.get(0) : null;
		double v = -10000000.0;
		for(Move m : moves){
			Board b = new Board(board, m);
			double min = minValue(b, -10000000.0, 10000000.0, 0);
			if(min > v) {
				v = min;
				nextMove = m;
			}
		}
		long ts = System.currentTimeMillis();
		System.out.println("Move made: skill=" + v + ", time=" + (ts-startTime)/1000.0 + ", depth=" + maxDepth);
		if(ts > startTime + maxTime) maxDepth--;
		if(ts < startTime +  maxTime*0.1) maxDepth++;

		return nextMove;
	}
	
	private double maxValue(Board board, double alpha, double beta, int depth){
		List<Move> moves = board.successors();
		moves.sort(new MoveComparator());
		double v = -10000000.0;
		
		if(moves.size() == 0 || depth >= maxDepth || System.currentTimeMillis() > startTime + maxTime) {
			return Evaluator.eval(board);
		}
		
		for(Move m  : moves){
			Board b = new Board(board, m);
			double min = minValue(b, alpha, beta, depth+1);
			if(min > v) v = min;
			if(v > beta) return v;
			if(v > alpha) alpha = v;
		}
		return v;
	}
	
	private double minValue(Board board, double alpha, double beta, int depth){
		List<Move> moves = board.successors();
		moves.sort(new MoveComparator());
		double v = Double.MAX_VALUE;
		
		if(moves.size() == 0 || depth >= maxDepth || System.currentTimeMillis() > startTime + maxTime){
			return Evaluator.eval(board);
		}
		
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
