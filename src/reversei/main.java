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
		
		int maxTime = input.readMaxTime();	
		MinMax minmax = new MinMax(maxTime);
		
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
				Move move = minmax.decision(board);
				if(move != null){
					board.play(move.x(), move.y());
					System.out.println("Computer says: " + (char)( move.y() + 97) + (move.x()+1));
				}
			}
		}
		board.display();
		System.out.println("'tis has been decided: " + (board.getWinner() < 0 ? "You WIN!" : "You lose, ha ha haa"));
	}
}