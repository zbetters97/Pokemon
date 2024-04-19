/* AUTHOR: Zachary Betters
 * CREATED: 03/01/2022
 * Pokemon Battle Simulation
 *
 * --- RELEASE UPDATES ---
 * 
 * 3/03/2022:
 * 		-Completed efficiency chart for move types
 * 		-Added comments
 * 		-Cleaned up code to be more concise
 * 		-Included MainMenu class for Driver to call
 * 3/05/2022:
 * 		-Added more comments
 * 		-Created interface for Pokedex class
 * 3/06/2022:
 * 		-Added in battle sound effects
 * 		-Added Pokemon cries
 * 		-Added battle music
 * 3/07/2022:
 * 		-Added Pikachu cry
 * 		-Moved BattleEngine to battle package		
 * 		-Cleaned up code in BattleEngine
 * 		-Edited BattleEngine to swap Pokemon turn
 * 3/08/2022:
 * 		-Added two player mode (early version)
 * 		-Added basic AI to CPU on One Player mode
 * 		-Modified UI formatting
 * 3/09/2022:
 * 		-Modified process of creating a new Pokemon instance		
 * 		-Added moveset for each existing Pokemon
 * 		-Improved CPU AI for One Player mode
 * 		-Cleaned up coding to be more concise
 * 		-Added lambda expression to Battle class for efficiency
 * 3/10/2022:
 * 		-Added exception handlers for user input
 * 		-Simplified class names
 * 		-Added multi-type feature for Pokemon
 * 		-Modified all Pokemon attributes assignment to be more accurate
 * 		-Added Ivysaur, Venasaur, Charmeleon, Charizard, and Raichu
 * 		-Removed Geodude
 * 		-Added more moves
 * 		-Cleaned up UI elements
 * 		-Added sounds for all new moves/Pokemon
 * 3/12/2022:
 * 		-Modified sleep timer to be same duration as source file
 * 3/13/2022:
 * 		-Cleaned up coding to be more concise
 * 		-Added SoundCard class to handle audio files
 * 		-Added Sleeper class to handle wait times
 * 		-Added money earned
 * 		-Added victory music
 * 		-Added more comments
 * 		-Added option select battle music before start
 * 		-Modified health bar UI
 * 3/14/2022:
 * 		-Added more music options
 * 		-Modified damage formula
 * 		-Modified exp formula
 * 		-Cleaned up coding to be more concise
 * 3/15/2022:
 * 		-Patched misc errors
 * 		-Added Abra, Kadabra, Alakazam and Geodude (with corresponding moves)
 * 		-Added code to prepare for custom name input
 * 		-Added move type parameter (Physical, Special, Status) to move constructor
 * 		-Modified damage formula to utilize the move type variable
 * 		-Added option to select no music
 * 		-Added base HP stat to Pokedex for tracking total HP
 * 3/16/2022:
 * 		-Added Gastly, Haunter, and Gengar
 * 3/19/2022:
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
 * 3/25/2022:
 * 		-Modified background music coding to pull from music folder
 * 		-Modified Sleeper and SoundCard class to more accurately hold for sound file
 * 5/7/2022:
 * 		-Modified deal damage logic to be more accurate
 * 		-Changed type of Rock Throw from Ground to Rock
 * 		-Weakened super effective damage from 2.0 to 1.5
 * 		-Added STAB multiplier for damage formula
 * 6/28/2023:
 * 		-Fixed bug in music selection menu	
 * 		-Sorted moves list
 * 		-Added moves descriptions and option to view in battle
 * 		-Added limited functionality for Pokemon party battles (1-6)
 * 		-Added Pokedex index value to each Pokemon
 * 6/29/2023:
 * 		-Fixed bug that gave every Pokemon 4 moves 	
 * 		-Fixed bug where super effective moves were not applying
 * 		-Fixed bug where Pokemon swap without fainting
 * 6/29/2023 (cont.):
 * 		-Added Type description in select Pokemon menu
 * 		-Organized the music list
 * 		-Fixed bug where Pokemon don't swap during two player mode
 * 		-Fixed bug where incorrect winner is announced
 * 6/29/2023 (cont.):
 * 		-Fixed bug where select move isn't an option for multi player
 * 		-Cleaned up menu wording and error messages
 * 		-Added name your player functionality
 * 		-Modified IVs to randomly generate per stat
 * 6/30/2023:
 * 		-Added Horsea, Seadra, Kingdra, Treecko, Grovyle, Skeptile, Torchic, 
 * 			Combuskin, Blaziken, Mudkip, Marshtomp, and Swampert
 * 		-Added Agility, Bubble, Twister, Dragon Pulse, Pound, Leaf Blade,
 * 			Leaf Storm, X-Scissor, Double Kick, Fire Punch, Blaze Kick,
 * 			Sky Uppercut, Mudslap, Mudshot, Surf, Mudbomb, and Muddy Water
 * 		-Added select Pokemon to swap out functionality
 * 		-Removed error output if sound file is not found
 * 		-Modified the winning EXP formula to be more accurate
 * 6/30/2023 (cont.):
 * 		-Fixed bug in Pokemon swap out functionality
 * 		-Added level info in displayHP method and cleaned up formatting
 * 		-Cleaned up coding
 * 		-Condensed chunks of coding
 * 7/5/2023
 * 		-Fixed bug in selectParty method
 * 		-Fixed bug in CPU select move functionality
 * 		-Fixed bug in remove from party functionality
 * 		-Cleaned up coding
 * 		-Condensed chunks of coding
 * 7/5/2023 (cont.):
 * 		-Fixed typo for Sceptile
 * 		-Minor code cleanup
 * 		-Changed formatting for Pokemon selection and in-battle info
 * 		-Changed formatting for multi-type Pokemon
 * 		-Changed wording for two player menu
 * 		-Fixed damage formula logic error
 * 7/6/2023:
 * 		-Changed text output to mimic traditional Pokemon
 * 		-Moved music to now play when battle starts
 * 		-Removed type Dragon from Charizard
 * 		-Fixed damage formula logic error
 * 		-Added Settings menu to change text speed and sounds
 * 		-Added player input indicator
 * 7/6/2023 (cont.):
 * 		-Moved "effective" sound to play before text
 * 		-Fixed formatting error in displayMove method
 * 		-Changed wording in welcome and settings menu
 * 7/7/2023:
 *  	-Increased text speeds
 * 		-Added delay after effectiveness text read
 * 		-Fixed error in trainer defeated text 	
 * 		-Overhauled type effectiveness method to be more accurate
 * 		-Added sound files for the following Pokemon: Horsea, Seadra, 
 * 			Kingdra, Treecko, Grovyle, Skeptile, Torchic, Combuskin, 
 * 			Blaziken, Mudkip, Marshtomp, and Swampert
 * 		-Added sound files for the following moves: Agility, Bubble, 
 * 			Twister, Dragon Pulse, Pound, Leaf Blade, Leaf Storm, 
 * 			X-Scissor, Double Kick, Fire Punch, Blaze Kick, Sky Uppercut, 
 * 			Mudslap, Mudshot, Surf, Mudbomb, and Muddy Water
 * 7/7/2023 (cont.):
 * 		-Patched broken audio files
 * 		-Modified audio levels in most song files
 * 		-Changed numbering for all song files
 * 		-Decreased wait time after in battle text
 * 		-Added menu beep noise
 * 		-Moved select music to settings menu
 * 		-Cleaned up coding
 * 		-Moved Battle class to battle package
 * 7/7/2023 (cont.):
 * 		-Corrected attributes for several moves
 * 		-Code cleanup
 * 7/10/2023:
 * 		-Fixed default music bug
 * 		-Fixed EXP earned bug		
 * 		-Simplified EXP formula
 * 		-Streamlined sound card to be compatible with MacOS
 * 		-Reorganized files and folders
 * 		-Added Absorb and added to Treeko's move set
 * 7/10/2023 (cont.):
 * 		-Added fainting cry for all existing Pokemon
 * 7/11/2023:
 * 		-Forced victory music playback when no sound effects		
 * 		-Added menu music
 * 		-Added option to turn off music * 		
 * 		-Added the move Giga Drain
 * 		-Added Agility and Giga Drain functionality
 * 		-Cleaned up code to be more concise
 * 		-Fixed formatting of status condition
 * 7/12/2023:
 * 		-Condensed coding
 * 		-Fixed Venusaur faint sound		
 * 		-Added new music
 * 		-Added ability to set default levels
 * 		-Changed move info layout to add new line after 40 characters 	
 * 		-Changed party selection to rotate between players
 * 		-Reformatted in battle status info	
 * 		-Added the following Pokemon: Raikou, Entei, Suicune
 * 		-Added the following moves: Crunch, Thunder, Thunder Fang, 
 * 			Extrasensory, Ice Fang, Aurora Beam
 * 7/14/2023:
 * 		-Condensed coding
 * 		-Reformatted moves info and fighter info
 * 		-Fixed frozen status effect
 * 		-Added more detailed comments
 * 7/17/2023:
 * 		-Cleaned up coding
 * 		-Added more detailed comments
 * 7/20/2023:
 * 		-Added CPU AI to select next best Pokemon in party
 * 		-Reduced clear content from 70 lines to 60
 * 3/29/2024:
 * 		-Cleaned up Settings menu functionality
 * 		-Fixed bug in CPU select next Pokemon logic
 * 		-Reworded Default Level Settings
 * 4/2/2024:
 * 		-Added turn waiting functionality (ex: Solar Beam, Dig, Fly)
 * 		-Added the moves Dig and Fly
 * 		-Added 33% chance CPU does not select most powerful move
 * 		-Implemented early stages of swap fighter functionality
 * 4/3/2024:
 * 		-Modified swap fighter functionality
 * 		-Added potions to menu options
 * 		-Reworded menu options
 * 		-Cleaned up formatting
 * 4/4/2024:
 * 		-Added CPU Super / Hyper potion functionality
 * 		-Added menu select sound effect to other menu options
 * 		-Changed "BACK" to "<- BACK" in menu options
 * 		-Reworded QUIT menu option to POWER OFF
 * 		-Fixed Dig's type engine from flying to ground
 * 		-Cleaned up comments and formatting
 * 4/5/2024:
 * 		-Added select starting fighter functionality
 * 		-Added CPU random party select functionality
 * 		-Added the following Pokemon: Kyogre, Groudon, and Rayquaza
 * 		-Added Blizzard and Extreme Speed
 * 		-Added new song: PC Multi-player Battle
 * 		-Modified menu wording / formatting
 * 		-Fixed broken move sound effect files 		
 * 		-Fixed bug in party select menu
 * 4/8/2024:
 * 		-Fixed bug in attribute-change moves (Muddy Water, Mud Bomb, etc)
 * 		-Cleaned up attribute and status move logic
 * 		-Cleaned up coding
 * 4/9/2024:
 * 		-Added debug mode option in Main Menu
 * 		-Changed BACK option to always be 0
 * 		-Fixed bug in move wait logic
 * 		-Cleaned up coding
 * 4/10/2024:
 * 		-Changed swap functionality to not display current fighter
 * 		-Cleaned up displayMoves formatting
 * 		-Fixed bug in swap fighter logic
 * 4/11/2024:
 * 		-Added new Move class that creates unique objects out of Moves Enums
 * 		-Fixed move PP logic error
 * 		-Renamed Moves class to MoveEngine and cleaned coding
 * 		-Modified Move class so PP is found in MovesEngine
 * 		-Added Pokemon logo to Main Menu
 * 		-Added Professor Oak ascii art to battle setup menu
 * 		-Added hidden phrases at name select screen
 * 4/12/2024:
 * 		-Modified displayInfo to not print accuracy if move is to self
 * 		-Reformatted printBattleInfo to be more faithful to the series		
 * 		-Added delay in trainer print lines
 * 		-Added color to Pokemon and Oak print
 * 		-Added color to Pokemon and Move types
 * 		-Added logic to update color of HP based on percentage remaining
 * 		-Fixed bug in displayMove formatting
 * 4/13/2024:
 * 		-Added faint sounds for Raikou, Entei, Suicune, Kyogre, 
 * 			Grouodon, and Rayquaza
 * 		-Added potion heal sound effect
 * 		-Added R/S Elite Four battle music
 * 		-Added R/S Victory Road menu music
 * 		-Reworded menu options
 * 		-Cleaned up coding
 * 4/14/2024:
 * 		-Added Nature class (non-functional)
 * 4/15/2024:
 * 		-Added functioning Nature attributes
 * 		-Renamed Pokedex class to Pokemon
 * 		-Renamed MoveEngine to Move
 * 		-Renamed TypeEngine class to Type and condensed coding
 * 		-Renamed StatusEngine class to Status and condensed coding
 * 		-Moved Nature, Status, and Type class to new properties package
 * 		-Moved Sleeper, SoundCard, and Style to new config package
 * 		-Added full restore to CPU item list
 * 		-Added CPU smart party select
 * 		-Patched Suicune's battle cry
 * 		-Fixed Electric text color bug
 * 		-Fixed bug in check for resistance method
 * 4/16/2024:
 * 		-Sorted battle music in descending order
 * 		-Fixed Giga Drain and Absorb logic
 * 		-Added Critical Hit modifiers
 * 		-Changed critical hit base chance from 1/255 to 2/25 (or 4/25)
 * 		-Reworded menu options
 * 		-Cleaned up coding
 * 4/17/2024:
 * 		-Added recoil damage functionality
 * 		-Added Seismic Toss functionality
 * 		-Added default level custom option
 * 		-Added 50/50 chance of who goes first if speeds are equal
 * 		-Changed Moves getName() to return toUpperCase()
 * 		-Cleaned up coding
 * 4/18/2024:
 * 		-Added Battle Shift mode
 * 		-Added detailed Pokemon stats menu
 * 		-Added paralyze effect for Dragon Breath
 * 		-Added color to Move types
 * 		-Fixed bug in delay move logic
 * 		-Fixed bug with move Poison Powder
 * 		-Cleaned up coding
 * 4/19/2024:
 * 		-Added hidden Trainer Red mode
 * 		-Added the following Pokemon: Snorlax and Lapras
 * 		-Added the following moves: Body Slam, Yawn, Ice Beam, Sheer Cold
 * 		-Removed CPU battle shift logic
 * 		-Reorganized Pokemon mapped moves
 * 		-Changed Sleep wait time to 1-5 turns
 * 		-Fixed bug in CPU swap fighter logic
 * 		-Cleaned up battle shift coding
 * 4/20/2024:
 * 		-Added Struggle
 */	

package application;

import java.util.Scanner;

/*** DRIVER CLASS ***/
public class Driver {
	
	static Scanner input = new Scanner(System.in);
	
	/** MAIN METHOD 
	 * Driver method to call MainMenu class
	 **/
	public static void main(String[] args) {
		MainMenu.load();
	}
	/** END MAIN METHOD **/
}
/*** END DRIVER CLASS ***/