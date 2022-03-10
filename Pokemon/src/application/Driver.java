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
 * 		-Added basic AI to CPU on One Player mode
 * 		-Modified UI formatting
 * 03/09/2022 Update:
 * 		-Modified process of creating a new Pokemon instance		
 * 		-Added moveset for each existing Pokemon
 * 		-Improved CPU AI for One Player mode
 * 		-Cleaned up coding to be more concise
 * 		-Added lambda expression to Battle class for efficiency
 * 03/10/2022 Update:
 * 		-Added exception handlers for user input
 * 		-Simplified class names
 * 		-Added multi-type feature for Pokemon
 * 		-Modified all Pokemon attributes assignment to be more accurate
 * 		-Added Ivysaur, Venasaur, Charmeleon, Charizard, and Raichu
 * 		-Removed Geodude
 * 		-Added more moves
 * 		-Cleaned up UI elements
 * 		-Added sounds for all new moves/Pokemon
 */

package application;

import java.util.Scanner;

/*** APPLICATION CLASS ***/
public class Driver {
	
	static Scanner input = new Scanner(System.in);
	
	/** MAIN METHOD **/
	public static void main(String[] args) {
		
		MainMenu.load();
	}
	/** END MAIN METHOD **/
}
/*** END DRIVER CLASS ***/