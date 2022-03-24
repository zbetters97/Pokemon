/* AUTHOR: Zachary Betters
 * CREATED: 03/01/2022
 * Pokemon Battle Simulation
 *
 * --- UPDATES ---
 * 
 * 03/03/2022:
 * 		-Completed efficiency chart for move types
 * 		-Added comments
 * 		-Cleaned up code to be more concise
 * 		-Included MainMenu class for Driver to call
 * 03/05/2022:
 * 		-Added more comments
 * 		-Created interface for Pokedex class
 * 03/06/2022:
 * 		-Added in battle sound effects
 * 		-Added pokemon cries
 * 		-Added battle music
 * 03/07/2022:
 * 		-Added Pikachu cry
 * 		-Moved BattleEngine to battle package		
 * 		-Cleaned up code in BattleEngine
 * 		-Edited BattleEngine to swap Pokemon turn
 * 03/08/2022:
 * 		-Added two player mode (early version)
 * 		-Added basic AI to CPU on One Player mode
 * 		-Modified UI formatting
 * 03/09/2022:
 * 		-Modified process of creating a new Pokemon instance		
 * 		-Added moveset for each existing Pokemon
 * 		-Improved CPU AI for One Player mode
 * 		-Cleaned up coding to be more concise
 * 		-Added lambda expression to Battle class for efficiency
 * 03/10/2022:
 * 		-Added exception handlers for user input
 * 		-Simplified class names
 * 		-Added multi-type feature for Pokemon
 * 		-Modified all Pokemon attributes assignment to be more accurate
 * 		-Added Ivysaur, Venasaur, Charmeleon, Charizard, and Raichu
 * 		-Removed Geodude
 * 		-Added more moves
 * 		-Cleaned up UI elements
 * 		-Added sounds for all new moves/Pokemon
 * 03/12/2022:
 * 		-Modified sleep timer to be same duration as source file
 * 03/13/2022:
 * 		-Cleaned up coding to be more concise
 * 		-Added SoundCard class to handle audio files
 * 		-Added Sleeper class to handle wait times
 * 		-Added money earned
 * 		-Added victory music
 * 		-Added more comments
 * 		-Added option select battle music before start
 * 		-Modified health bar UI
 * 03/14/2022:
 * 		-Added more music options
 * 		-Modified damage formula
 * 		-Modified exp formula
 * 		-Cleaned up coding to be more concise
 * 03/15/2022:
 * 		-Patched misc errors
 * 		-Added Abra, Kadabra, Alakazam and Geodude (with corresponding moves)
 * 		-Added code to prepare for custom name input
 * 		-Added move type parameter (Physical, Special, Status) to move constructor
 * 		-Modified damage formula to utilize the move type variable
 * 		-Added option to select no music
 * 		-Added base HP stat to Pokedex for tracking total HP
 * 03/16/2022:
 * 		-Added Gastly, Haunter, and Gengar
 * 03/19/2022:
 * 		-Added status effects (paralyze, poison, confuse, burn, freeze)
 * 		-Added limited functionality to paralyze, poison, and confuse
 * 		-Added Thunder Wave, Poison Powder, Confuse Ray, and Hypnosis
 * 		-Cleaned up getEffect method
 * 		-Added all status sound effects
 * 		-Added limited functionality to burn, frozen, and sleep
 * 3/20/2022:
 * 		-Improved SoundCard method for status effects
 * 		-Cleaned up coding to be more concise
 * 		-Added chance of status effect in related moves (Ember, Thunderbolt...)
 * 		-Added Machop, Machamp, and Machoke, and related moves
 * 		-Cleaned up status effect coding
 * 3/21/2022:
 * 		-Added limited functionality to attribute change moves
 * 		-Added Calm Mind (Alakazam)
 * 3/22/2022:
 * 		-Added Pokemon Colosseum battle music
 * 		-Fixed bug where battle continues despite fainted Pokemon
 * 		-Added more status moves (Growl, Tail Whip, Play Nice, Kinesis)
 * 		-Fixed stat modifier logic to be more precise
 * 		-Added Graveler and Geodude
 * 		-Added Defense Curl, Earthquake, and Heavy Slam
 * 3/23/2022:
 * 		-Added Super Smash Bros. music
 * 		-Overhauled BattleEngine class
 * 		-Fixed isAlive checkers at the end of every turn
 * 		-Modified SoundCard class to sleep for full duration
 * 		-Implemented Array variable in BattleEngine class to track Pokemon in battle
 * 3/24/2022:
 * 		-Cleaned up BattleEngine class to be more concise
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