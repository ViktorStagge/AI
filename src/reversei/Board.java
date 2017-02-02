package reversei;

import java.util.LinkedList;
import java.util.List;

public class Board {
	private int[][] playingBoard;
	private int nextColor;
	
	public Board(){
		playingBoard = new int[10][10];
		playingBoard[4][4] = 1; playingBoard[5][5] = 1; playingBoard[4][5] = -1; playingBoard[5][4] = -1;
		nextColor = -1;
	}
	
	public boolean play(int x, int y, int color){
		if(canPlay(x, y, color) > 0 && color == nextColor) { 
			playingBoard[x+1][y+1] = color;
			x++; y++;
			
			for(int dx = -1; dx <= 1; dx++){
				for(int dy = -1; dy <= 1; dy++){
					int count = 0;
					if(playingBoard[x+dx][y+dy] == -color){
						count++;
						while(playingBoard[x+(count+1)*dx][y+(count+1)*dy] == -color){ count++; }
						if(playingBoard[x+(count+1)*dx][y+(count+1)*dy] == color){
							for(int i = 1; i <= count; i++){
								playingBoard[x+i*dx][y+i*dy] *= -1;
							}
						}
					}
				}
			}
			
			nextColor = -nextColor;
			return true;
		} else {
			System.out.println("Suggested Illegal Move");
			return false;
		}
	}
	
	public int canPlay(int x, int y, int color){
		int flipped = 0;
		
		if(x>=0 && x<8 && y>=0 && y<8 && playingBoard[++x][++y] == 0){
			for(int dx = -1; dx <= 1; dx++){
				for(int dy = -1; dy <= 1; dy++){
					int count = 0;
					if(playingBoard[x+dx][y+dy] == -color){
						count++;
						while(playingBoard[x+(count+1)*dx][y+(count+1)*dy] == -color){
							count++;
						}
						//System.out.println("trying move: x=" + x + ", y=" + y + ", count=" + count + ", dx=" + dx + ", dy=" + dy);
						if(playingBoard[x+(count+1)*dx][y+(count+1)*dy] == color)  {
					//		System.out.println("move okay: x=" + x + ", y=" + y + ", color= " + color + ", count=" + count + ", dx=" + dx + ", dy=" + dy);
							flipped += count;
						}
						//System.out.println("tried move: x=" + x + ", y=" + y + ", count=" + count + ", dx=" + dx + ", dy=" + dy);
					}
				}
			}
		}
		return flipped;
	}
	
	public List<Move> successors(){
		LinkedList<Move> list = new LinkedList<Move>();
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(canPlay(i, j, nextColor) > 0){
					list.add(new Move(i,j)); 
//					System.out.println("move added: x=" + i + ", y=" + j);
				}
			}
		}
		return list;
	}
	
	public Board copy(Move m){
		Board copy = new Board();
		copy.nextColor = nextColor;
		for(int i = 0; i < 8; i++){
			for(int j= 0; j < 8; j++){
				copy.playingBoard[i][j] = playingBoard[i][j];
			}
		}
		copy.play(m.x(), m.y(), copy.nextColor);
		return copy;
	}
	
	public int peek(int x, int y){
		return playingBoard[x+1][y+1];
	}
	
	public int nextColor(){ return nextColor; }
	
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
