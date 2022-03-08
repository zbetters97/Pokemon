package application;

import java.io.File;
import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import battle.OnePlayerBattleEngine;
import moves.MoveEngine;
import pokemon.Pokedex;


/*** MAIN MENU CLASS ***/
public class OnePlayer {
	
	// create static scanner to receive user input
	static Scanner input = new Scanner(System.in);	
	static Pokedex fighter, opponent;	
	static OnePlayerBattleEngine battle;
	
	/** MAIN MENU METHOD **/
	public void start() {
		
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
						
		System.out.println("PLEASE SELECT YOUR POKEMON:");		
		int choice = 0, counter = 1;
		
		// loop through list of pokemon
		for (Pokedex pokemon : pokedex) {
			System.out.println("[" + counter + "] " + pokemon.getName());
			
			// increment counter to display current ID of pokemon
			counter++;
		}			
		
		try { choice = input.nextInt(); }
		catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}
		
		// assign fighter to pokemon found at given index
		fighter = pokedex.get(choice - 1);
		
		// play pokemon cry
		soundCard("\\pokedex\\" + fighter.getName());		
		
		fighter.addMove(MoveEngine.EMBER);
		fighter.addMove(MoveEngine.SCRATCH);
		fighter.addMove(MoveEngine.THUNDERSHOCK);
		
		System.out.println("PLEASE SELECT YOUR OPPONENT'S POKEMON:");			
		counter = 1;
		
		// loop through list of pokemon
		for (Pokedex pokemon : pokedex) {
			System.out.println("[" + counter + "] " + pokemon.getName());
			
			// increment counter to display current ID of pokemon
			counter++;
		}			
		
		try { choice = input.nextInt(); }
		catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}	
		
		// assign opponent to pokemon found at given index
		opponent = pokedex.get(choice - 1);
		
		// play pokemon battle cry
		soundCard("\\pokedex\\" + opponent.getName());
		
		// initialize engine to handle pokemon battle, pass in chosen pokemon
		battle = new OnePlayerBattleEngine();
		
		clearContent();	
		selectOption();
	}
	/** END SELECT POKEMON METHOD **/
	
	/** SELECT AN OPTION METHOD **/
	public static void selectOption() {
		
		// loop until manual exit
		while (true) { 
			
			displayHP();
			
			int choice = 0;
			
			try { choice = input.nextInt(); }
			catch (Exception e) {
				System.out.println(e.getMessage());
				break;
			}
			
			// switch-case on given input
			switch (choice) {
			
				// call method to display moves
				case 1: clearContent(); selectMove(fighter, opponent); break;
					
				// call method from object to list move set
				case 2: clearContent(); fighter.listMoves(); break;
				
				// return to previous method
				case 3: clearContent(); selectPokemon(); break;
				
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
					System.out.println("You have entered an unrecognized choice.");
					break;
			}
		}
	}
	/** END SELECT AN OPTION METHOD **/

	/** DISPLAY HP METHOD **/
	public static void displayHP() {
		System.out.println("\n\n---" + fighter.getName() + " HP: " + fighter.getHP() + "---\n---" + 
				opponent.getName() + " HP: " + opponent.getHP() + "---\n");
		
		System.out.println("[1] SELECT MOVE\n[2] LIST MOVES\n[3] CHANGE POKEMON\n[4] RUN");
	}
	/** END DISPLAY HP METHOD **/
	
	/** SELECT A MOVE METHOD **/
	public static void selectMove (Pokedex fighter, Pokedex target) {
		
		// store current fighter's move set
		ArrayList<MoveEngine> moveSet = fighter.getMoveSet();
		
		System.out.println("What move will " + fighter.getName() + " do?\n");
		
		// display all moves
		int choice = 0, counter = 1;		
		for (MoveEngine move : moveSet) {
			System.out.println("[" + counter + "] " + move.getName());
			counter++;
		}
		System.out.println("[" + counter + "] <-BACK");
						
		try { choice = input.nextInt(); }
		catch (Exception e) { System.out.println(e.getMessage()); }
		
		// if choice is a valid move option
		if (0 < choice && choice < counter) {
			clearContent();
			
			// call battle engine to use the move, pass the move at specified index
			battle.move(fighter, target, moveSet.get(choice - 1));
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

	/** CLEAR SCREEN METHOD **/	
	public static void clearContent() {		
		for (int clear = 0; clear < 200; clear++) 
			System.out.println("\n") ;
	}
	/** END CLEAR SCREEN METHOD **/
}
/*** END MAIN MENU CLASS ***/