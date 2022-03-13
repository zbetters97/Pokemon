package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import battle.BattleEngine;
import moves.Moves;
import pokemon.Pokedex;

/*** BATTLE CLASS ***/
public class Battle {
	
	// create static scanner to receive user input	
	static Scanner input = new Scanner(System.in);	
	private int numPlayers;
	static Pokedex trainer1, trainer2;	
	static BattleEngine battle;	
	
	public Battle(int numPlayers) {
		
		// prevent errors upon startup by setting numPlayers to either 1 or 2
		if (numPlayers > 2) 
			numPlayers = 2;
		else if (numPlayers < 1) 
			numPlayers = 1;

		this.numPlayers = numPlayers;
	}
	
	/** MAIN MENU METHOD **/
	public void start() {		
		clearContent();				
		selectPokemon();		
	}
	/** END MAIN METHOD **/
	
	/** SELECT POKEMON METHOD **/
	private void selectPokemon() {
		
		// lambda method to get pokemon selection
		SelectionGrabber getInput = (trainerNum) -> {
			
			System.out.println("TRAINER " + trainerNum + ", PLEASE SELECT YOUR POKEMON:");
			
			int counter = 0;
			for (Pokedex p : Pokedex.getPokedex())
				System.out.println("[" + ++counter + "] " + p.getName());
			
			while (true) {				
				try { 
					int choice = input.nextInt(); 
					
					// choice must be a number from 0 to last element in list
					if (0 < choice && choice <= counter) 
						return choice;
					else 
						System.out.println("This is not a valid selection");
				}
				catch (Exception e) {
					System.out.println("ERROR! Input must be a number!");
					input.next();
				}
			}
		};
		
		// assign fighter to pokemon found at given index
		trainer1 = Pokedex.getPokemon(getInput.get(1) - 1);	

		// play pokemon cry
		SoundCard.play("//pokedex//" + trainer1.getName());	
		clearContent();
		
		// assign fighter to pokemon found at given index
		trainer2 = Pokedex.getPokemon(getInput.get(2) - 1);		
		
		// play pokemon cry
		SoundCard.play("//pokedex//" + trainer2.getName());	
		clearContent();	
		
		// initialize engine to handle pokemon battle
		battle = new BattleEngine(trainer1, trainer2);		
		
		selectOption();
	}
	/** END SELECT POKEMON METHOD **/
	
	/** SELECT AN OPTION METHOD **/
	private void selectOption() {
		
		// lambda method to use for both trainers
		MoveGrabber selectMove = (fighter) -> {
			
			System.out.println("What will " + fighter.getName() + " do?\n");
			
			while (true) {
				
				int counter = 0;
				
				// display all moves				
				for (Moves m : fighter.getMoveSet())
					System.out.println("[" + ++counter + "] " + m.getName());
								
				try { 
					int choice = input.nextInt();
					
					// if choice is a valid move option
					if (0 < choice && choice <= counter) 
						return getMove(choice, fighter.getMoveSet());
					else 
						System.out.println("This is not a move"); 		
				}
				catch (InputMismatchException e) { 
					System.out.println("Input must be a number");
					input.next();
				}
			}
		};
		
		Moves move1 = null, move2 = null;
		
		// tracks which trainer is selecting the move
		int turn = 1;
		
		// loop until manual exit
		while (true) { 		
			
			displayHP();
			System.out.println("[1] SELECT MOVE\n[2] LIST MOVES\n[3] RUN");
			
			try { 
				int choice = input.nextInt();
				
				switch (choice) {
				
					case 1: 
						clearContent(); 
						
						// if one player mode
						if (numPlayers == 1) {
							move1 = selectMove.get(trainer1);
							move2 = battle.cpuSelectMove();
							battle.move(move1, move2);
							
							clearContent();
							
							if (battle.getWinner() == 1) {
								announceWinner(1, battle.getMoney());							
								return;
							}
							else if (battle.getWinner() == 2) {
								announceWinner(2, battle.getMoney());		
								return;
							}
							
							break;
						}					
						// if two player mode
						else if (numPlayers == 2) {
							if (turn == 1) {
								move1 = selectMove.get(trainer1);							
								turn = 2;
							}
							else if (turn == 2){
								move2 = selectMove.get(trainer2);							
								battle.move(move1, move2);																								
								turn = 1;
								
								clearContent();
							}	
							
							if (battle.getWinner() == 1) {
								announceWinner(1, battle.getMoney());						
								return;
							}
							else if (battle.getWinner() == 2) {								
								announceWinner(2, battle.getMoney());	
								return;
							}
							
							// break out of switch-case and begin while-loop again
							break;
						}
						// catch error if player mode is not 1 or 2
						else { System.out.println("Internal error!"); System.exit(1); break; }
						
					case 2: 
						clearContent(); 
						if (turn == 1) trainer1.listMoves(); 
						else if (turn == 2) trainer2.listMoves();
						else { System.out.println("Internal error!"); System.exit(1); break; }
						
						// points back to beginning of switch-case
						continue;
					
					case 3: 
						clearContent();	
						SoundCard.play("in-battle-run");
						System.out.println("Got away safely!"); 					 
						System.exit(1); 
						break;
					
					// not a valid option handler
					default: 
						clearContent();
						System.out.println("Input is not valid");
						break;
				}
			}
			catch (Exception e) {
				clearContent();
				System.out.println("Input must be a number" + 2);
				input.next();
			}		
		}
	}
	/** END SELECT AN OPTION METHOD **/

	/** DISPLAY HP METHOD **/
	private void displayHP() {
		System.out.println(
				"\nTRAINER 1: [ " + trainer1.getName() + " HP: " +trainer1.getHP() + " ]" +
				"\nTRAINER 2: [ " + trainer2.getName() + " HP: " + trainer2.getHP() + " ]\n"
		);
	}
	/** END DISPLAY HP METHOD **/
	
	/** GET MOVE METHOD **/
	private static Moves getMove(int choice, ArrayList<Moves> moveSet) {
		
		clearContent();
		
		// if move has pp
		if (moveSet.get(choice - 1).getpp() > 0) { return moveSet.get(choice - 1); }
		else { System.out.println("This move is out of PP and cannot be used!"); return null; }
		
	}
	/** END GET MOVE METHOD **/
	
	private void announceWinner(int winner, int money) {
		SoundCard.play("in-battle-victory");
		System.out.println("Player defeated, Pokemon Trainer " + winner + "!");
		System.out.println("Trainer " + winner + " got $" + money + " for winning!");
	}

	/** CLEAR SCREEN METHOD **/	
	private static void clearContent() {		
		System.out.println(new String(new char[70]).replace("\0", "\r\n"));
	}
	/** END CLEAR SCREEN METHOD **/
}
/*** END BATTLE CLASS ***/


/*** LAMBDA INTERFACE ***/
@FunctionalInterface
interface SelectionGrabber {
	public int get(int trainerNum);
}

@FunctionalInterface
interface MoveGrabber {
	public Moves get(Pokedex fighter);
}
/*** END LAMBDA INTERFACE ***/