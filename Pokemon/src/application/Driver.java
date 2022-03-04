/* AUTHOR: Zachary Betters
 * CREATED: 03/01/2022
 * Pokemon Battle Simulation
 * 
 *03/03/2022 Update:-Completed efficiency chart for move types
 * 				  	-Added comments
 * 				  	-Cleaned up code to be more concise
 * 				  	-Included MainMenu class for Driver to call
 */

package application;

/*** DRIVER CLASS ***/
public class Driver {
	
	/** MAIN METHOD **/
	public static void main(String[] args) {
		
		// call menu application to launch game
		MainMenu menu = new MainMenu();
		menu.mainMenu();
	}
}