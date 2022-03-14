package application;

/*** SLEEPER CLASS ***/
public class Sleeper {
	
	/** SLEEP METHOD **/
	public static void pause(double time) {		
		try { Thread.sleep((int) time); } 
		catch (InterruptedException e) { e.printStackTrace(); }
	}
	/** END SLEEP METHOD **/
}
/*** END SLEEPER CLASS ***/