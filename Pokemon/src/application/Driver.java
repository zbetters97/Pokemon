/* AUTHOR: Zachary Betters
 * CREATED: 03/01/2022
 * Pokemon Battle Simulation
 * 
 * 03/03/2022 Update:
 * 		-Completed efficiency chart for move types
 * 		-Added comments
 * 		-Cleaned up code to be more concise
 * 		-Included MainMenu class for Driver to call
 * 03/05/2022 Update:
 * 		-Added more comments
 * 		-Created interface for Pokedex class
 * 03/06/2022 Update:
 * 		-Added in battle sound effects
 * 		-Added pokemon cries
 * 		-Added battle music
 * 03/07/2022 Update:
 * 		-Added Pikachu cry
 * 		-Moved BattleEngine to battle package		
 * 		-Cleaned up code in BattleEngine
 * 		-Edited BattleEngine to swap Pokemon turn
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
	/** END MAIN METHOD **/
}
/*** END DRIVER CLASS ***/