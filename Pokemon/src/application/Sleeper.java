package application;

/*** SLEEPER CLASS ***/
public class Sleeper {
	
	private static int speed = 20;
	
	/** SET SPEED METHOD **/
	public static void setSpeed(int sp) {
		speed = sp;
	}
	/** END SET SPEED METHOD **/
	
	/** SLEEP METHOD **/
	public static void pause(int time) {		
		try { Thread.sleep(time); } 
		catch (InterruptedException e) { e.printStackTrace(); }
	}
	/** END SLEEP METHOD **/
	
	/** SLEEP METHOD **/
	public static void pause(double time) {		
		try { Thread.sleep((long) (time * 1000)); } 
		catch (InterruptedException e) { e.printStackTrace(); }
	}
	/** END SLEEP METHOD **/
	
	/** PRINT METHOD **/
	public static void print(String s) {
		
		// print text to screen one character at a time, similar to Pokemon
		for (char c : s.toCharArray()) {
			Sleeper.pause(speed);
		    System.out.print(c);
		}
		System.out.println();
	}
	/** END PRINT METHOD **/
	
	/** PRINT METHOD **/
	public static void print(String s, int time) {
		
		// print text to screen one character at a time, similar to Pokemon
		for (char c : s.toCharArray()) {
			Sleeper.pause(speed);
		    System.out.print(c);
		}
		System.out.println();
		
		pause(time);
	}
	/** END PRINT METHOD **/
}
/*** END SLEEPER CLASS ***/