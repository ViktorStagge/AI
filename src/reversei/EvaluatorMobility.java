package reversei;

public class EvaluatorMobility extends Evaluator{
	public static double eval(Board board){
		return board.successors().size();
	}
}
