package reversei;

import java.util.LinkedList;
import java.util.List;

public class Board {
	private static final int EMPTY = 0;
	
	private int[][] playingBoard;
	private int nextColor;
	private List<Move> edges;
	private boolean finished = false;
	private int winner = 0;
	
	public Board(){
		playingBoard = new int[10][10];
		for(int i = 0; i < 10; i++){ playingBoard[0][i] = -1000; playingBoard[9][i] = -1000; playingBoard[i][0] = -1000; playingBoard[i][9] = -1000; }
		playingBoard[4][4] = 1; playingBoard[5][5] = 1; playingBoard[4][5] = -1; playingBoard[5][4] = -1;
		nextColor = -1;
		edges = new LinkedList<Move>();
		edges.add(new Move(2,3)); edges.add(new Move(2,4)); edges.add(new Move(2,5)); 
		edges.add(new Move(3,5)); edges.add(new Move(4,5)); edges.add(new Move(5,5));
		edges.add(new Move(5,4)); edges.add(new Move(5,3)); edges.add(new Move(5,2));
		edges.add(new Move(4,2)); edges.add(new Move(3,2)); edges.add(new Move(2,2));
	}
	
	public Board(Board o, Move m){
		playingBoard = new int[10][10];
		nextColor = o.nextColor;
		finished = o.finished;
		winner = o.winner;
		for(int i = 0; i < 10; i++){
			for(int j= 0; j < 10; j++){
				playingBoard[i][j] = o.playingBoard[i][j];
			}
		}
		edges = new LinkedList<Move>();
		edges.addAll(o.edges);
		play(m.x(), m.y());
	}
	
	public boolean play(int x, int y){
		if(canPlay(x, y, nextColor) > 0) { 
			x++; y++;
			playingBoard[x][y] = nextColor;
			
			for(int dx = -1; dx <= 1; dx++){
				for(int dy = -1; dy <= 1; dy++){
					int count = 0;
					Move m = new Move(x-1+dx, y-1+dy);
					if(playingBoard[x+dx][y+dy] == -nextColor){
						count++;
						while(playingBoard[x+(count+1)*dx][y+(count+1)*dy] == -nextColor){ count++; }
						if(playingBoard[x+(count+1)*dx][y+(count+1)*dy] == nextColor){
							for(int i = 1; i <= count; i++){
								playingBoard[x+i*dx][y+i*dy] *= -1;
							}
						}
					} else if(playingBoard[x+dx][y+dy] == EMPTY && !edges.contains(m)) {
						edges.add(new Move(x-1+dx, y-1+dy));
					}
				}
			}
			
			edges.remove(new Move(x-1,y-1));
			nextColor = getNextPlayer();
			return true;
		} else {
			System.out.println("Suggested Illegal Move");
			return false;
		}
	}
	
	public int canPlay(int x, int y){ return canPlay(x, y, nextColor); }
	
	public int canPlay(int x, int y, int color){
		int flipped = 0;
		
		if(x>=0 && x<8 && y>=0 && y<8 && playingBoard[++x][++y] == 0){
			for(int dx = -1; dx <= 1; dx++){
				for(int dy = -1; dy <= 1; dy++){
					int count = 0;
					if(playingBoard[x+dx][y+dy] == -color){
						count++;
						while(playingBoard[x+(count+1)*dx][y+(count+1)*dy] == -color){ count++; }
						if(playingBoard[x+(count+1)*dx][y+(count+1)*dy] == color)  {
							flipped += count;
						}
					}
				}
			}
		}
		return flipped;
	}
	
	public List<Move> successors(){
		LinkedList<Move> list = new LinkedList<Move>();
		for(Move m : edges){
			if(canPlay(m.x, m.y, nextColor) > 0){
				list.add(m);
			}
		}
		
		return list;
	}
	
	public int peek(int x, int y){
		return playingBoard[x+1][y+1];
	}
	
	private int getNextPlayer(){
		for(Move m : edges) {
			if(canPlay(m.x, m.y, -nextColor) > 0) return -nextColor;
		}
		for(Move m : edges) {
			if(canPlay(m.x, m.y, nextColor) < 0) finished = true;
		}
		if(edges.size() == 0) finished = true;
		if(finished) generateWinner();
		return nextColor;
	}
	
	private void generateWinner() {
		winner = 0;
		for(int i = 1; i < 9; i++){
			for(int j = 1; j < 9; j++){
				winner += playingBoard[i][j];
			}
		}
	}

	public int nextColor(){ return nextColor; }
	public boolean isFinished(){ return finished; }
	public int getWinner() { return winner; }
	
	public void display(){
		StringBuilder lineBuilder = new StringBuilder();
		lineBuilder.append("    A  B  C  D  E  F  G  H  \n");
		for(int i = 1; i < 9; i++){
			lineBuilder.append(i); lineBuilder.append(' '); lineBuilder.append('|');
			for(int j = 1; j <9; j++){
				if(playingBoard[i][j] == -1){
					lineBuilder.append(" X ");
				} else if(playingBoard[i][j] == 1){
					lineBuilder.append(" O ");
				} else {
					lineBuilder.append("   ");
				}
			}
			lineBuilder.append('|'); lineBuilder.append('\n');
		}
		System.out.println(lineBuilder.toString());
	}
}
