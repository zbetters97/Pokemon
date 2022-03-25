package application;

/*** SLEEPER CLASS ***/
public class Sleeper {
	
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
}
/*** END SLEEPER CLASS ***/