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
	private static Scanner input = new Scanner(System.in);	
	private int numPlayers;
	private static Pokedex pokemon1, pokemon2;	
	private BattleEngine battle;
	
	/** CONSTRUCTOR **/
	public Battle(int numPlayers) {
		
		// prevent errors upon startup by setting numPlayers to either 1 or 2
		if (numPlayers > 2) numPlayers = 2;
		else if (numPlayers < 1) numPlayers = 1;

		this.numPlayers = numPlayers;
	}
	/** END CONSTRUCTOR **/
	
	/** MAIN MENU METHOD **/
	public void start() {		
		clearContent();				
		selectPokemon();
		selectMove();
	}
	/** END MAIN METHOD **/
	
	/** SELECT POKEMON METHOD **/
	private void selectPokemon() {
		
		/** LAMBDA METHOD TO GET POKEMON SELECTION **/
		SelectionGrabber getInput = (trainerNum) -> {
			
			System.out.println("TRAINER " + trainerNum + ", PLEASE SELECT YOUR POKEMON:");
			
			int counter = 0;
			for (Pokedex p : Pokedex.getPokedex())
				System.out.println("[" + ++counter + "] " + p.getName() + " (LVL: " + p.getLevel() + ")");
			
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
		/** END LAMBDA METHOD **/
		
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
		
		battle = new BattleEngine(pokemon1, pokemon2);
	}
	/** END SELECT POKEMON METHOD **/
		
	/** SELECT MOVE METHOD **/
	private void selectMove() {
		
		Moves move1, move2;
		
		while (true) {
			
			clearContent();
			
			// 1 player mode
			if (numPlayers == 1) {
				
				displayHP();
				move1 = displayMoves(pokemon1);
				move2 = battle.cpuSelectMove();				
				clearContent();
				
				battle.move(move1, move2);				
				clearContent();
				
				if (battle.hasWinner()) {
					if (battle.getWinningPokemon().getName().equals(pokemon1.getName())) {
						announceWinner("1", "2", battle.getMoney(1));						
						return;
					}
					else if (battle.getWinningPokemon().getName().equals(pokemon2.getName())) {								
						announceWinner("2", "1", battle.getMoney(0));	
						return;
					}	
				}
			}				
			// 2 player mode
			else if (numPlayers == 2) {
				
				displayHP();
				move1 = displayMoves(pokemon1);					
				clearContent();
				
				displayHP();
				move2 = displayMoves(pokemon2);	
				clearContent();
				
				battle.move(move1, move2);				
				clearContent();
				
				if (battle.hasWinner()) {
					if (battle.getWinningPokemon().getName().equals(pokemon1.getName())) {
						announceWinner("1", "2", battle.getMoney(1));						
						return;
					}
					else if (battle.getWinningPokemon().getName().equals(pokemon2.getName())) {								
						announceWinner("2", "1", battle.getMoney(0));	
						return;
					}	
				}				
			}	
		}
	}
	/** END SELECT MOVE METHOD **/
	
	/** DISPLAY HP METHOD **/
	private void displayHP() {
						
		System.out.print("\nTRAINER 1: " + pokemon1.getName() + 
				" HP [" + pokemon1.getHP() + "/" + pokemon1.getBHP() + "]: ");
		
		if (pokemon1.getStatus() != null)
			System.out.println(pokemon1.getStatus().getName());
		
		for (int i = 0; i < pokemon1.getHP(); i++) {
			if (i % 50 == 0)
				System.out.println();
			
			System.out.print("*");
		}
		
		System.out.print("\nTRAINER 2: " + pokemon2.getName() + 
				" HP [" + pokemon2.getHP() + "/" + pokemon2.getBHP() + "]: ");
		
		if (pokemon2.getStatus() != null)
			System.out.println(pokemon2.getStatus().getName());
		
		for (int i = 0; i < pokemon2.getHP(); i++) {
			if (i % 50 == 0)
				System.out.println();
			
			System.out.print("*");
		}
		System.out.println("\n");
	}
	/** END DISPLAY HP METHOD **/
	
	/** DISPLAY MOVES METHOD **/
	private Moves displayMoves(Pokedex fighter) {
		
		System.out.println("What will " + fighter.getName() + " do?\n");
		
		while (true) {
			
			int counter = 0;
			
			// display all moves				
			for (Moves m : fighter.getMoveSet()) {
				System.out.println("[" + ++counter + "] " + m.getName() + 
						" (PP: " + m.getpp() + ", PWR: " + m.getPower() + ", ACC: " + m.getAccuracy() + ")");
			}			
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
	/** END DISPLAY MOVE METHOD **/
	
	/** GET MOVE METHOD **/
	private static Moves getMove(int choice, ArrayList<Moves> moveSet) {		
		// if move has pp
		if (moveSet.get(choice - 1).getpp() > 0) { return moveSet.get(choice - 1); }
		else { System.out.println("This move is out of PP and cannot be used!"); return null; }
		
	}
	/** END GET MOVE METHOD **/
	
	/** ANNOUNCE WINNER METHOD **/
	private void announceWinner(String winner, String loser, int money) {		
		SoundCard.play("//in-battle//in-battle-victory");
		System.out.println("Player defeated, Pokemon Trainer " + loser + "!");
		System.out.println("Trainer " + winner + " got $" + money + " for winning!");
	}
	/** END ANNOUNCE WINNER METHOD **/

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
/*** END LAMBDA INTERFACE ***/