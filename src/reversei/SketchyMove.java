package reversei;

public class SketchyMove extends Move{

	public SketchyMove(int x, int y) {
		super(x, y);
	}
	public SketchyMove(String move){
		 super(move);
	}
	 
	public void changeTo(int x, int y){
		super.x = x; super.y= y;
	}
}
