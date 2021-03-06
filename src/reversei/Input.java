package reversei;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Input {
	private Scanner scan;
	
	public Input(){
		scan = new Scanner(System.in);
	}
	
	public Move readMove(){
		System.out.println("Enter move:");
		String line = scan.next();
		Pattern p = Pattern.compile("^([a-h]|[A-H])[1-8]$");
		Matcher m = p.matcher(line);
		
		while(!m.matches()){
			System.out.println("Please enter on form: \"d3\", \"A2\".");
			line = scan.next();
			m = p.matcher(line);
		}
		return new Move(line);
	}
	
	public int readMaxTime(){
		int maxTime = -1;
		while(maxTime <= 0){
			System.out.println("Enter maximum time [ms]:");
			String line = scan.next();
			try{
				maxTime = Integer.valueOf(line);
			} catch (NumberFormatException e){
				System.out.println("Wrong Format. (Use \"500\", \"2000\"");
			}
		}
		return maxTime;
	}
}
