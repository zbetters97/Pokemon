package battle;

import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import moves.Moves;
import pokemon.Pokedex;
import application.Sleeper;
import application.SoundCard;

/*** BATTLE CLASS ***/
public class Battle {
	
	// create static scanner to receive user input	
	private static Scanner input = new Scanner(System.in);	
	private String name1, name2;
	private int numPlayers;
	private static Pokedex pokemon1, pokemon2;
	private ArrayList<Pokedex> pokemonParty1, pokemonParty2;
	private BattleEngine battle;
	
	/** CONSTRUCTOR **/
	public Battle(String name1, String name2, int numPlayers, 
			ArrayList<Pokedex> pokemonParty1, ArrayList<Pokedex> pokemonParty2) {
		
		this.name1 = name1; this.name2 = name2;
		
		// prevent errors upon startup by setting variables expected values
		if (numPlayers < 1) numPlayers = 1;
		else if (2 < numPlayers) numPlayers = 2;		
		this.numPlayers = numPlayers;
				
		this.pokemonParty1 = pokemonParty1;
		this.pokemonParty2 = pokemonParty2;
	}
	/** END CONSTRUCTOR **/
	
	/** MAIN MENU METHOD **/
	public void start() {
		
		clearContent();	
		
		pokemon1 = pokemonParty1.get(0); 
		pokemon2 = pokemonParty2.get(0);
		
		battle = new BattleEngine(pokemon1, pokemon2);
				
		selectMove();
	}
	/** END MAIN METHOD **/
		
	/** SELECT MOVE METHOD **/
	private void selectMove() {
		
		Moves move1, move2;
		
		while (true) {
			
			clearContent();							
			displayParty(); 
			displayHP();
			
			if (numPlayers == 1) {
				move1 = displayMoves(pokemon1);				
				move2 = battle.cpuSelectMove();		
			}
			else {
				move1 = displayMoves(pokemon1);
				
				clearContent();
				displayParty(); displayHP();
				
				move2 = displayMoves(pokemon2);
			}
						
			clearContent();			
			battle.move(move1, move2);				
			clearContent();
			
			// if a pokemon is defeated
			if (battle.hasWinner()) {
				
				// if no pokemon left in party
				if (chooseNextFighter())
					return;
			}
		}
	}
	/** END SELECT MOVE METHOD **/
	
	/** DISPLAY PARTY METHOD **/
	private void displayParty() {
		
		System.out.print(name1 + "'s PARTY: ");
		for (Pokedex p : pokemonParty1)
			System.out.print(p.getName() + " ");
		
		System.out.print("\n" + name2 + "'s PARTY: ");
		
		for (Pokedex p : pokemonParty2)
			System.out.print(p.getName() + " ");
	}
	/** END DISPLAY PARTY METHOD **/
	
	/** DISPLAY HP METHOD **/
	private void displayHP() {
						
		// if pokemon has multiple types
		if (pokemon1.getType() == null) {
			
			System.out.print("\n\n(" + name1 + ")\n" + pokemon1.getName() + 
					": HP " + pokemon1.getHP() + "/" + pokemon1.getBHP() + 
					" | Lv " + pokemon1.getLevel() + " | " + pokemon1.printTypes());
		}
		else {
			System.out.print("\n\n(" + name1 + ")\n" + pokemon1.getName() + 
					" : HP " + pokemon1.getHP() + "/" + pokemon1.getBHP() + 
					" | Lv " + pokemon1.getLevel() + " | " + pokemon1.getType());	
		}
		
		if (pokemon1.getStatus() != null)
			System.out.println(pokemon1.getStatus().getName());
		
		for (int i = 0; i < pokemon1.getHP(); i++) {
			if (i % 50 == 0)
				System.out.println();
			
			System.out.print(".");
		}
		
		// if pokemon has mutliple types
		if (pokemon2.getType() == null) {
			
			System.out.print("\n\n(" + name2 + ")\n" + pokemon2.getName() + 
					": HP " + pokemon2.getHP() + "/" + pokemon2.getBHP() + 
					" | Lv " + pokemon2.getLevel() + " | " + pokemon2.printTypes());
		}
		else {
			System.out.print("\n\n(" + name2 + ")\n" + pokemon2.getName() + 
					" : HP " + pokemon2.getHP() + "/" + pokemon2.getBHP() + 
					" | Lv " + pokemon2.getLevel() + " | " + pokemon2.getType());	
		}
		
		if (pokemon2.getStatus() != null)
			System.out.println(" (" + pokemon2.getStatus().getName() + ")");
		
		for (int i = 0; i < pokemon2.getHP(); i++) {
			
			// add new line for every 50 health points
			if (i % 50 == 0) System.out.println();			
			System.out.print(".");
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
			System.out.print(">");
			
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
					SoundCard.play("battle" + File.separator + "run");
					Sleeper.print("Got away safely!"); 					 
					System.exit(1);
				}
				else {
					Sleeper.print("This is not a move!"); 	
					System.out.print(">");
				}
			}
			catch (InputMismatchException e) { 
				Sleeper.print("ERROR: Input must be a number!");
				System.out.print(">");
				input.next();
			}
		}
	}
	/** END DISPLAY MOVE METHOD **/
		
	/** GET MOVE INFO METHOD **/
	private void displayInfo(Pokedex fighter) {	
				
		for (Moves m : fighter.getMoveSet()) {
			
			StringBuilder info = new StringBuilder(m.getInfo());
			
			// add new line after 40 characters
			int i = 0;				
			while ( (i = info.indexOf(" ", i + 40)) != -1) {
				info.replace(i, i + 1, "\n");
			}
			
			System.out.println(m.getName() + ": " + info.toString() + "\n");
		}
		
		System.out.println("[0] BACK");
		System.out.print(">");
		
		try { 
			int choice = input.nextInt();
			
			// if choice is a valid move option
			if (choice == 0) {
				clearContent();	
				displayHP();
				System.out.println("What will " + fighter.getName() + " do?\n");
				return;
			}
			else {
				Sleeper.print("ERROR: This is not a valid selection!");
				System.out.print(">");
			}
		}
		catch (InputMismatchException e) { 
			Sleeper.print("Input must be a number!");
			System.out.print(">");
			input.next();
		}		
	}
	/** END GET MOVE INFO METHOD **/
	
	/** CHOOSE NEXT POKEMON METHOD **/
	public boolean chooseNextFighter() {
		
		boolean playerTwoWinner = battle.getWinningPokemon().getName().equals(pokemon2.getName());
		int choice;
		
		// if player 2 defeated player 1
		if (playerTwoWinner) {
			
			// remove from player 1 party			
			for (Pokedex p : pokemonParty1) {
				
				if (p.getIndex() == pokemon1.getIndex()) {
					pokemonParty1.remove(p);
					break;
				}
			}
			
			// if no pokemon left
			if (pokemonParty1.isEmpty()) {
				announceWinner(name2, name1, battle.getMoney(0));
				
				return true;
			}
			else {			
				// reset winner
				battle.setWinningPokemon(null);
				
				// ask which pokemon to sub in and swap
				choice = listNextFighter(1);
				pokemon1 = pokemonParty1.get(choice - 1);
				battle.swapPokemon(pokemon1, 0);
				
				clearContent();
				
				Sleeper.print(name1 + ": GO, " + pokemon1.getName() + "!");
				SoundCard.play("pokedex" + File.separator + pokemon1.getName());
				Sleeper.pause(1700);	
				clearContent();
				
				return false;
			}
		}
		// if player 1 defeated player 2
		else {		
			
			// remove from party 2
			for (Pokedex p : pokemonParty2) {
				
				if (p.getIndex() == pokemon2.getIndex()) {
					pokemonParty2.remove(p);
					break;
				}
			}
			
			// if no pokemon left
			if (pokemonParty2.isEmpty()) {
				announceWinner(name1, name2, battle.getMoney(1));	
				return true;
			}
			else {			
				// reset winner
				battle.setWinningPokemon(null);
					
				// ask which pokemon to sub in and swap (1 for cpu so choice would = 0)
				choice = (numPlayers == 2 ) ? listNextFighter(2) : 1;				
				pokemon2 = pokemonParty2.get(choice - 1);
				battle.swapPokemon(pokemon2, 1);
				
				clearContent();
				
				Sleeper.print(name2 + ": GO, " + pokemon2.getName() + "!");
				SoundCard.play("pokedex" + File.separator + pokemon2.getName());
				Sleeper.pause(1700);	
				clearContent();
				
				return false;
			}		
		}
	}	
	/** END CHOOSE NEXT POKEMON METHOD **/	
	
	/** LIST FIGHTERS METHOD **/
	public int listNextFighter(int player) {
		
		Sleeper.print("CHOOSE A POKEMON.", 700);
		
		ArrayList<Pokedex> pokemonParty = (player == 1 ) ? pokemonParty1 : pokemonParty2;
		
		int counter = 0;
		for (Pokedex p : pokemonParty) {
			if (p.getType() == null) {							
				System.out.printf("[" + ++counter + "] " + p.getName() + 
					"\tLVL: %02d | TYPE: " + p.getTypes() + "\n", p.getLevel());	
			}
			else {
				System.out.printf("[" + ++counter + "] " + p.getName() + 
					"\tLVL: %02d | TYPE: " + p.getType() + "\n", p.getLevel());
			}
		}
		
		System.out.print(">");
		
		int choice = 0;
		
		while (true) {				
			try { 
				choice = input.nextInt(); 
				
				// choice must be a number from 0 to last element in list
				if (0 < choice && choice <= counter) {
					return choice;									
				}
				else {
					Sleeper.print("ERROR: This is not a valid selection!");
					System.out.print(">");
				}
			}
			catch (Exception e) {
				Sleeper.print("ERROR: Input must be a number!");
				System.out.print(">");
				input.next();
			}
		}
	}
	/** END LIST FIGHTERS METHOD **/	

	/** GET MOVE METHOD **/
	private static Moves getMove(int choice, ArrayList<Moves> moveSet) {		
		// if move has pp
		if (moveSet.get(choice - 1).getpp() > 0) 
			return moveSet.get(choice - 1);
		else { 
			Sleeper.print("This move is out of PP and cannot be used!"); 
			System.out.print(">");
			return null; 
		}
		
	}
	/** END GET MOVE METHOD **/
	
	/** ANNOUNCE WINNER METHOD **/
	private void announceWinner(String winner, String loser, int money) {
		
		SoundCard.setActive(true);		
		SoundCard.play("battle" + File.separator + "victory");
		
		Sleeper.print("Player defeated, " + loser + "!");
		Sleeper.print(winner + " got $" + money + " for winning!");
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