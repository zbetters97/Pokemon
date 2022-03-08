package application;

import java.io.File;
import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import battle.TwoPlayerBattleEngine;
import moves.MoveEngine;
import pokemon.Pokedex;


/*** MAIN MENU CLASS ***/
public class TwoPlayer {
	
	// create static scanner to receive user input
	static Scanner input = new Scanner(System.in);	
	static Pokedex trainer1, trainer2;	
	static TwoPlayerBattleEngine battle;
	
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
						
		System.out.println("PLAYER 1, PLEASE SELECT YOUR POKEMON:");		
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
		trainer1 = pokedex.get(choice - 1);
		
		// play pokemon cry
		soundCard("\\pokedex\\" + trainer1.getName());		
		
		trainer1.addMove(MoveEngine.TACKLE);
		trainer1.addMove(MoveEngine.QUICKATTACK);
		trainer1.addMove(MoveEngine.THUNDERSHOCK);
		
		System.out.println("PLAYER 2, PLEASE SELECT YOUR OPPONENT'S POKEMON:");			
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
		trainer2 = pokedex.get(choice - 1);
		
		// play pokemon battle cry
		soundCard("\\pokedex\\" + trainer2.getName());
		
		trainer2.addMove(MoveEngine.SCRATCH);
		trainer2.addMove(MoveEngine.QUICKATTACK);
		trainer2.addMove(MoveEngine.WATERGUN);
		
		// initialize engine to handle pokemon battle, pass in chosen pokemon
		battle = new TwoPlayerBattleEngine();
		
		clearContent();	
		selectOption();
	}
	/** END SELECT POKEMON METHOD **/
	
	/** SELECT AN OPTION METHOD **/
	public static void selectOption() {
		
		// tracks which trainer is selecting the move
		int turn = 0;

		MoveEngine move1 = null;
		MoveEngine move2 = null;
		
		// loop until manual exit
		while (true) { 
		
			/*int trainer1Speed = trainer1.getSpeed(); int trainer2Speed = trainer2.getSpeed();
			boolean trainer1Go = (trainer1Speed >= trainer2Speed);
						
			if (turn == 1 && trainer1Go) trainer1Go = false;
			else if (turn == 1 && !trainer1Go) trainer1Go = true;
			else if (turn == 2) turn = 0;*/
									
			displayHP();
			System.out.println("[1] SELECT MOVE\n[2] LIST MOVES\n[3] RUN");
						
			int choice = 0;
			
			try { choice = input.nextInt(); }
			catch (Exception e) {
				System.out.println(e.getMessage());
				break;
			}			
			
			// switch-case on given input
			switch (choice) {
			
				// call method to display moves
				case 1: 
					clearContent(); 
					
					if (turn == 0) {
						move1 = selectMove(trainer1);
						turn++;
					}
					else if (turn == 1){
						move2 = selectMove(trainer2);
						
						battle.move(trainer1, move1, trainer2, move2);
						turn = 0;
						
						clearContent();
					}
					
					break;
					
				// call method from object to list move set
				case 2: 
					clearContent(); 
					if (turn == 0) trainer1.listMoves(); 
					else trainer2.listMoves();
					continue;
				
				// end program
				case 3: 
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
		System.out.println("\n\nTRAINER 1: ---" + trainer1.getName() + " HP: " +trainer1.getHP() + "---" +
				"\nTRAINER 2: ---" + trainer2.getName() + " HP: " + trainer2.getHP() + "---\n");
	}
	/** END DISPLAY HP METHOD **/
	
	/** SELECT A MOVE METHOD **/
	public static MoveEngine selectMove(Pokedex fighter) {
		
		// store current fighter's move set
		ArrayList<MoveEngine> moveSet = fighter.getMoveSet();
		
		System.out.println("What move will " + fighter.getName() + " do?\n");
		
		// display all moves
		int choice = 0, counter = 1;		
		for (MoveEngine m : moveSet) {
			System.out.println("[" + counter + "] " + m.getName());
			counter++;
		}
		System.out.println("[" + counter + "] <-BACK");
						
		try { choice = input.nextInt(); }
		catch (Exception e) { System.out.println(e.getMessage()); }
		
		// if choice is a valid move option
		if (0 < choice && choice < counter) {
			clearContent();
			
			// if move is out of pp
			if (moveSet.get(choice - 1).getpp() <= 0) {
				System.out.println("This move is out of PP and cannot be used!");
				selectMove(fighter);
			}
			else {
				// call battle engine to use the move, pass the move at specified index
				return moveSet.get(choice - 1);	
			}
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
		return null;
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