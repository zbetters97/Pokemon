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
	private int partySize;
	private ArrayList<Pokedex> pokemonParty1;
	private ArrayList<Pokedex> pokemonParty2;
	private static Pokedex pokemon1, pokemon2;
	private BattleEngine battle;
	
	/** CONSTRUCTOR **/
	public Battle(int numPlayers, int partySize) {
		
		// prevent errors upon startup by setting variables expected values
		if (numPlayers < 1) numPlayers = 1;
		else if (numPlayers > 2) numPlayers = 2;		
		
		if (partySize < 1) partySize = 1;
		if (partySize > 6) partySize = 6;
		
		this.numPlayers = numPlayers;
		this.partySize = partySize;
		
		pokemonParty1 = new ArrayList<Pokedex>();
		pokemonParty2 = new ArrayList<Pokedex>();
	}
	/** END CONSTRUCTOR **/
	
	/** MAIN MENU METHOD **/
	public void start() {		
		clearContent();	
		
		//selectPokemon();
		
		selectParty(1);
		selectParty(2);
		
		pokemon1 = pokemonParty1.get(0);
		pokemon2 = pokemonParty2.get(0);
		
		battle = new BattleEngine(pokemon1, pokemon2);
				
		selectMove();
	}
	/** END MAIN METHOD **/
	
	/** SELECT PARTY METHOD **/
	private void selectParty(int player) {
				
		int party = 0;		
		
		while (party < partySize) {
		
			/** LAMBDA METHOD TO GET POKEMON SELECTION **/
			SelectionGrabber getInput = (trainerNum) -> {				
				
				int counter = 0;
				
				for (Pokedex p : Pokedex.getPokedex()) {
					
					// don't display Pokemon who are already chosen					
					if (pokemonParty1.contains(Pokedex.getPokemon(counter)) || 
							pokemonParty2.contains(Pokedex.getPokemon(counter))) {
						counter++;
						continue;
					}			
					
					if (p.getType() == null) {
						System.out.printf("[" + ++counter + "] " + p.getName() + 
							"\tLVL: %02d | TYPE: " + p.getTypes() + "\n", p.getLevel());	
					}
					else {
						System.out.printf("[" + ++counter + "] " + p.getName() + 
							"\tLVL: %02d | TYPE: " + p.getType() + "\n", p.getLevel());	
					}
				}			
				
				while (true) {				
					try { 
						int choice = input.nextInt(); 
						
						// choice must be a number from 0 to last element in list
						if (0 < choice && choice <= counter) {
							
							// chosen Pokemon must not have already been selected by either trainer
							if (pokemonParty1.contains(Pokedex.getPokemon(choice - 1)) || 
									pokemonParty2.contains(Pokedex.getPokemon(choice - 1))) 						
								System.out.println("This Pokemon has already been chosen!");							
							else						
								return choice;
						}
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
			
			System.out.println("TRAINER " + player + ", PLEASE SELECT YOUR POKEMON PARTY:\n");
			
			// assign fighter to pokemon found at given index
			if (player == 1) {	
				
				System.out.print("TRAINER 1 PARTY: ");
				
				if (!pokemonParty1.isEmpty()) {
					for (Pokedex p : pokemonParty1)
						System.out.print(p.getName() + " ");
				}
				System.out.print("\nTRAINER 2 PARTY:\n\n");
				
				pokemonParty1.add(Pokedex.getPokemon(getInput.get(1) - 1));
								
				// play pokemon cry
				SoundCard.play("//pokedex//" + pokemonParty1.get(party).getName());	
				clearContent();
				party++;
			}				
			else if (player == 2) {
				
				if (!pokemonParty1.isEmpty()) {
					System.out.print("TRAINER 1 PARTY: ");
					for (Pokedex p : pokemonParty1)
						System.out.print(p.getName() + " ");
				}
				
				if (!pokemonParty2.isEmpty()) {
					System.out.print("\nTRAINER 2 PARTY: ");
					for (Pokedex p : pokemonParty2)
						System.out.print(p.getName() + " ");
				}
				else
					System.out.print("\nTRAINER 2 PARTY: ");
				
				System.out.print("\n\n");
				
				pokemonParty2.add(Pokedex.getPokemon(getInput.get(1) - 1));
				
				// play pokemon cry
				SoundCard.play("//pokedex//" + pokemonParty2.get(party).getName());	
				clearContent();
				party++;
			}
		}
	}
	/** END SELECT PARTY METHOD **/
		
	/** SELECT MOVE METHOD **/
	private void selectMove() {
		
		Moves move1, move2;
		
		while (true) {
			
			clearContent();
							
			displayParty(); displayHP();
			move1 = displayMoves(pokemon1);
			move2 = battle.cpuSelectMove();				
			clearContent();
			
			battle.move(move1, move2);				
			clearContent();
			
			if (battle.hasWinner()) {
					
				// if player 2 defeated player 1
				if (battle.getWinningPokemon().getName().equals(pokemon2.getName())) {
					
					// remove from party 1
					pokemonParty1.remove(0);
					
					// if no pokemon left
					if (pokemonParty1.isEmpty()) {
						announceWinner("2", "1", battle.getMoney(1));	
						return;
					}
					else {			
						// reset winner
						battle.setWinningPokemon(null);
						
						// get next pokemon in party and swap out battle
						pokemon1 = pokemonParty1.get(0);
						battle.swapPokemon(pokemon1, 0);
						
						System.out.println("TRAINER 1: GO, " + pokemon1.getName() + "!");
						SoundCard.play("//pokedex//" + pokemon1.getName());
						Sleeper.pause(1700);	
						clearContent();
					}
				}
				// if player 1 defeated player 2
				else {				
					// remove from party 2
					pokemonParty2.remove(0);
					
					// if no pokemon left
					if (pokemonParty2.isEmpty()) {
						announceWinner("1", "2", battle.getMoney(0));	
						return;
					}
					else {			
						// reset winner
						battle.setWinningPokemon(null);
						
						// get next pokemon in party and swap out battle
						pokemon2 = pokemonParty2.get(0);
						battle.swapPokemon(pokemon2, 1);
						
						System.out.println("TRAINER 2: GO, " + pokemon2.getName() + "!");
						SoundCard.play("//pokedex//" + pokemon2.getName());
						Sleeper.pause(1700);	
						clearContent();
					}					
				}
			}
		}
	}
	/** END SELECT MOVE METHOD **/
	
	private void displayParty() {
		
		System.out.print("TRAINER 1 PARTY: ");
		for (Pokedex p : pokemonParty1)
			System.out.print(p.getName() + " ");
		
		System.out.print("\nTRAINER 2 PARTY: ");
		
		for (Pokedex p : pokemonParty2)
			System.out.print(p.getName() + " ");
	}
	
	/** DISPLAY HP METHOD **/
	private void displayHP() {
						
		System.out.print("\n\nTRAINER 1: " + pokemon1.getName() + 
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
			System.out.println("[" + ++counter + "] INFO");
			System.out.println("[" + ++counter + "] RUN");
			
			try { 
				int choice = input.nextInt();
				
				// if choice is a valid move option
				if (0 < choice && choice < counter - 1)
					return getMove(choice, fighter.getMoveSet());				
				else if (choice == counter - 1) {
					clearContent();
					displayInfo(fighter);		
				}
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
		
	/** GET MOVE INFO METHOD **/
	private void displayInfo(Pokedex fighter) {	
				
		for (Moves m : fighter.getMoveSet())
			System.out.println(m.getName() + " : " + m.getInfo() + "");
		
		System.out.println("\n[0] BACK");
		
		try { 
			int choice = input.nextInt();
			
			// if choice is a valid move option
			if (choice == 0) {
				clearContent();	
				displayHP();
				System.out.println("What will " + fighter.getName() + " do?\n");
				return;
			}
			else
				System.out.println("This is not a valid selection");
		}
		catch (InputMismatchException e) { 
			System.out.println("Input must be a number");
			input.next();
		}		
	}
	/** END GET MOVE INFO METHOD **/	

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