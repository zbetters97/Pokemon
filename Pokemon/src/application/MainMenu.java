package application;

import java.io.File;
import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import moves.BattleEngine;
import moves.MoveEngine;
import pokemon.Pokedex;


/*** MAIN MENU CLASS ***/
public class MainMenu {
	
	// create static scanner to recieve user input
	static Scanner input = new Scanner(System.in);	
	static Pokedex fighter, opponent;	
	static BattleEngine battle;
	
	/** MAIN MENU METHOD **/
	public void mainMenu() {
		
		/* METHOD STRUCTURE:
		 * selectPokemon ->
		 * selectOption ->
		 * engage battle (loop until quit)
		 */
		
		clearContent();
		soundCard("battle-red");	
		
		// call method to select Pokemon first
		selectPokemon();		
	}
	/** END MAIN METHOD **/
	
	/** SELECT POKEMON METHOD **/
	public static void selectPokemon() {
		
		// assign entire pokedex to variable
		List<Pokedex> pokedex = Pokedex.getPokedex();
		
		int choice, counter;
		
		System.out.println("PLEASE SELECT YOUR POKEMON:");			
		counter = 1;
		
		// loop through list of pokemon
		for (Pokedex pokemon : pokedex) {
			System.out.println("(" + counter + "): " + pokemon.getName());
			
			// increment counter to display current ID of pokemon
			counter++;
		}			
		
		try { choice = input.nextInt(); }
		catch (Exception e) {
			System.out.println("You have entered an invalid selection.\n"
					+ "This program will now end.");
			return;
		}
		
		// assign fighter to pokemon found at given index
		fighter = pokedex.get(choice - 1);
		soundCard("\\pokedex\\" + fighter.getName());		
		
		fighter.addMove(MoveEngine.EMBER);
		fighter.addMove(MoveEngine.SCRATCH);
		fighter.addMove(MoveEngine.THUNDERSHOCK);
		
		System.out.println("PLEASE SELECT YOUR OPPONENT'S POKEMON:");			
		counter = 1;
		
		// loop through list of pokemon
		for (Pokedex pokemon : pokedex) {
			System.out.println("(" + counter + "): " + pokemon.getName());
			
			// increment counter to display current ID of pokemon
			counter++;
		}			
		
		try { choice = input.nextInt(); }
		catch (Exception e) {
			System.out.println("You have entered an invalid selection.\n"
					+ "This program will now end.");
			return;
		}	
		
		// assign opponent to pokemon found at given index
		opponent = pokedex.get(choice - 1);
		soundCard("\\pokedex\\" + opponent.getName());
		
		// initialize engine to handle pokemon battle, pass in chosen pokemon
		battle = new BattleEngine(fighter, opponent);
		
		clearContent();	
		selectOption();
	}
	/** END SELECT POKEMON METHOD **/
	
	/** SELECT AN OPTION METHOD **/
	public static void selectOption() {
		
		// loop until manual exit
		while (true) { 
			
			// display HP
			System.out.println("\n\n---" + fighter.getName() + " HP: " + fighter.getHP() + "---");
			System.out.println("---" + opponent.getName() + " HP: " + opponent.getHP() + "---\n");
			
			System.out.println("(1): SELECT MOVE\n(2): LIST MOVES\n(3): CHANGE POKEMON\n(4): RUN");
			
			int choice;
			
			try { choice = input.nextInt(); }
			catch (Exception e) {
				System.out.println("You have entered an invalid selection.\n"
						+ "This program will now end.");
				break;
			}
			
			switch (choice) {
			
				// call method to display moves
				case 1: 
					clearContent();
					selectMove(fighter, opponent);
					break;
					
				// call method from object to list move set
				case 2: 
					clearContent();
					fighter.listMoves();
					break;
				
				// return to previous method
				case 3: 
					clearContent();
					selectPokemon();
					break;
				
				// end program
				case 4:
					clearContent();					
					System.out.println("Got away safely!");
					soundCard("in-battle-run");
					
					System.exit(1); 
					break;
				
				// not a valid option handler
				default: 
					clearContent();
					System.out.println("You have entered an unrecognized choice. "
							+ "Please be sure to enter a valid option");
					break;
			}
		}
	}
	/** END SELECT AN OPTION METHOD **/
	
	/** SELECT A MOVE METHOD **/
	public static void selectMove (Pokedex fighter, Pokedex target) {
		
		// store current fighter's move set
		ArrayList<MoveEngine> moveSet = fighter.getMoveSet();
		
		System.out.println("What move will " + fighter.getName() + " do?\n");
		
		// display all moves
		int counter = 1;		
		for (MoveEngine move : moveSet) {
			System.out.println("(" + counter + ") " + move.getName());
			counter++;
		}
		System.out.println("(" + counter + ") <-BACK");
				
		int choice = 0;
		
		try { choice = input.nextInt(); }
		catch (Exception e) {
			System.out.println("You have entered an invalid selection.");
		}
		
		// if choice is a valid move option
		if (0 < choice && choice < counter) {
			clearContent();
			
			// call battle engine to use the move, pass the move at specified index
			battle.move(moveSet.get(choice - 1));
		}
		
		// if choice equals option to go back
		else if (choice == counter) {
			clearContent();
		}
		
		// unrecognized choice handler
		else {
			clearContent();
			System.out.println("Invalid choice!");
		}
		
		clearContent();
	}
	/** END SELECT A MOVE METHOD **/
	
	/** CLEAR SCREEN METHOD **/	
	public static void clearContent() {		
		for (int clear = 0; clear < 200; clear++) 
			System.out.println("\n") ;
	}
	/** END CLEAR SCREEN METHOD **/

	/** SOUND CARD METHOD **/
	public static void soundCard(String arg) {
		
		try {
			// retrieve sound file based on argument given
			String path = new File("").getAbsolutePath() + "\\lib\\sounds\\" + arg + ".wav";	
	        File sound = new File(path);
	        
            AudioInputStream ais = AudioSystem.getAudioInputStream(sound);
            Clip c = AudioSystem.getClip();
            c.open(ais); 
            
            // play music using built-in player
            c.start(); 
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
	}
	/** END SOUND CARD METHOD **/
}
/*** END MAIN MENU CLASS ***/