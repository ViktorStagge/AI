package reversei;

public class Move {
	protected int x;
	protected int y;
	
	public Move(String str){
		y = str.charAt(0) - (str.charAt(0)<73 ? 65:97);
		x = str.charAt(1) - 49;
	}
	
	public Move(int x, int y){
		this.x = x; this.y = y;
	}
	
	public boolean equals(Object o){
		if(o instanceof Move){
			Move other = (Move) o;
			return other.x == x && other.y == y;
		} else {
			System.out.println("class: " + o.getClass());
			return false;
		}
	}
	
	public int x(){ return x;}
	public int y(){ return y;}
	
	public String toString(){
		return "" + (char) (y + 65) + (x+1);
	}
}
