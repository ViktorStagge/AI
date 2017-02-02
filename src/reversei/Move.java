package reversei;

public class Move {
	private int x;
	private int y;
	
	public Move(String str){
		y = str.charAt(0) - (str.charAt(0)<73 ? 65:97);
		x = str.charAt(1) - 49;
	}
	
	public Move(int x, int y){
		this.x = x; this.y = y;
	}
	
	public int x(){ return x;}
	public int y(){ return y;}
}
