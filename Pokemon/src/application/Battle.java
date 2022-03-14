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
	private static Moves move1, move2;
	static Pokedex pokemon1, pokemon2;	
	static BattleEngine battle;	
	
	public Battle(int numPlayers) {
		
		// prevent errors upon startup by setting numPlayers to either 1 or 2
		if (numPlayers > 2) numPlayers = 2;
		else if (numPlayers < 1) numPlayers = 1;

		this.numPlayers = numPlayers;
	}
	
	/** MAIN MENU METHOD **/
	public void start() {		
		clearContent();				
		selectPokemon();	
		selectMove();
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
		pokemon1 = Pokedex.getPokemon(getInput.get(1) - 1);	

		// play pokemon cry
		SoundCard.play("//pokedex//" + pokemon1.getName());	
		clearContent();
		
		// assign fighter to pokemon found at given index
		pokemon2 = Pokedex.getPokemon(getInput.get(2) - 1);		
		
		// play pokemon cry
		SoundCard.play("//pokedex//" + pokemon2.getName());	
		clearContent();	
		
		// initialize engine to handle pokemon battle
		battle = new BattleEngine(pokemon1, pokemon2);		
	}
	/** END SELECT POKEMON METHOD **/
		
	private void selectMove() {
		
		while (true) {
			
			clearContent();
			
			if (numPlayers == 1) {
				
				move1 = getMove(pokemon1);
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
			}					
			else if (numPlayers == 2) {
				
				move1 = getMove(pokemon1);		
				
				clearContent();
				move2 = getMove(pokemon2);	
				
				battle.move(move1, move2);
				
				if (battle.getWinner() == 1) {
					announceWinner(1, battle.getMoney());						
					return;
				}
				else if (battle.getWinner() == 2) {								
					announceWinner(2, battle.getMoney());	
					return;
				}
			}
			else { 
				System.out.println("ERROR! Internal error!"); 
				System.exit(-1); 
			}		
		}
	}
	
	private Moves getMove(Pokedex fighter) {
		
		displayHP();
		
		System.out.println("What will " + fighter.getName() + " do?\n");
		
		while (true) {
			
			int counter = 0;
			
			// display all moves				
			for (Moves m : fighter.getMoveSet())
				System.out.println("[" + ++counter + "] " + m.getName());
			System.out.println("[" + ++counter + "] RUN");
			
			try { 
				int choice = input.nextInt();
				
				// if choice is a valid move option
				if (0 < choice && choice < counter) 
					return getMove(choice, fighter.getMoveSet());
				else if (choice == counter) {
					clearContent();	
					SoundCard.play("\\in-battle\\in-battle-run");
					System.out.println("Got away safely!"); 					 
					System.exit(1);
				}
				else
					System.out.println("This is not a move"); 		
			}
			catch (InputMismatchException e) { 
				System.out.println("Input must be a number");
				input.next();
			}
		}
	}
	
	/** DISPLAY HP METHOD **/
	private void displayHP() {
		
		System.out.print("\nTRAINER 1: " + pokemon1.getName() + " HP:");
		
		for (int i = 0; i < pokemon1.getHP(); i++) {
			if (i % 60 == 0)
				System.out.println();
			
			System.out.print("*");
		}
		
		System.out.print("\nTRAINER 2: " + pokemon2.getName() + " HP:");
		
		for (int i = 0; i < pokemon2.getHP(); i++) {
			if (i % 60 == 0)
				System.out.println();
			
			System.out.print("*");
		}
		
		System.out.println("\n");
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
		SoundCard.play("//in-battle//in-battle-victory");
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