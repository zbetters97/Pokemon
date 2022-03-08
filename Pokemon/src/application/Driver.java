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
 * 03/08/2022 Update:
 * 		-Added two player mode (early version)
 * 		-Modified UI formatting
 */

package application;

import java.util.Scanner;

/*** DRIVER CLASS ***/
public class Driver {
	
	static Scanner input = new Scanner(System.in);
	
	/** MAIN METHOD **/
	public static void main(String[] args) {
		
		System.out.println("PLEASE SELECT MODE:\n[1] ONE PLAYER MODE\n[2] TWO PLAYER MODE\n[3] QUIT");		
		int choice = 0;
				
		try { choice = input.nextInt(); }
		catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}
		
		if (choice == 1) { OnePlayer game = new OnePlayer(); game.start(); }		
		else if (choice == 2) { TwoPlayer game = new TwoPlayer(); game.start(); }
		else if (choice == 3) { System.exit(1); }
	}
	/** END MAIN METHOD **/
}
/*** END DRIVER CLASS ***/