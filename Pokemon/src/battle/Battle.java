package battle;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import moves.Moves;
import pokemon.Pokedex;
import types.TypeEngine;
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
	
	/** MAIN MENU METHOD
	  * Begin battle sequence 
	  **/
	public void start() {
		
		clearContent();	
		
		pokemon1 = pokemonParty1.get(0); 
		pokemon2 = pokemonParty2.get(0);
		
		battle = new BattleEngine(pokemon1, pokemon2);
				
		turn();
	}
	/** END MAIN METHOD **/
		
	/** TURN METHOD
	  * Call displayParty(), displayHP(), and displayMoves() 
	  **/
	private void turn() {
		
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
	/** END TURN METHOD **/
	
	/** DISPLAY PARTY METHOD
	  * Print out pokemon party for both trainers 
	  **/
	private void displayParty() {
		
		System.out.print(name1 + "'s PARTY: ");
		for (Pokedex p : pokemonParty1)
			System.out.print(p.getName() + " ");
		
		System.out.print("\n" + name2 + "'s PARTY: ");
		
		for (Pokedex p : pokemonParty2)
			System.out.print(p.getName() + " ");
	}
	/** END DISPLAY PARTY METHOD **/
	
	/** DISPLAY HP METHOD
	  * Print out current in-battle pokemon info 
	  **/
	private void displayHP() {
								
		// if pokemon has multiple types
		System.out.print("\n\n(" + name1 + ")\n" + pokemon1.getName() + 
				((pokemon1.getStatus() != null) ? " (" + pokemon1.getStatus().getName() + ")" : "") + 
				" : HP " + pokemon1.getHP() + "/" + pokemon1.getBHP() + 
			" | Lv " + pokemon1.getLevel() + " | " + 
			((pokemon1.getTypes() == null) ? pokemon1.getType() : pokemon1.printTypes()));	
		
		for (int i = 0; i < pokemon1.getHP(); i++) {
			if (i % 50 == 0) System.out.println();		
			System.out.print(".");
		}
		
		// if pokemon has mutliple types
		System.out.print("\n\n(" + name2 + ")\n" + pokemon2.getName() + 
				((pokemon2.getStatus() != null) ? " (" + pokemon2.getStatus().getName() + ")" : "") + 
				" : HP " + pokemon2.getHP() + "/" + pokemon2.getBHP() + 
			" | Lv " + pokemon2.getLevel() + " | " + 
			((pokemon2.getTypes() == null) ? pokemon2.getType() : pokemon2.printTypes()));	
		
		for (int i = 0; i < pokemon2.getHP(); i++) {
			if (i % 50 == 0) System.out.println();			
			System.out.print(".");
		}
		System.out.println("\n");
	}
	/** END DISPLAY HP METHOD **/
	
	/** DISPLAY MOVES METHOD
	  * Print out in-battle pokemon's moveset and return chosen move 
	  * @param Pokedex current fighter
	  * @return selected move
	  **/
	private Moves displayMoves(Pokedex fighter) {
		
		while (true) {		
			
			System.out.println("What will " + fighter.getName() + " do?\n");
			
			int counter = 0;
			
			// display all moves				
			for (Moves m : fighter.getMoveSet()) {
				System.out.println("[" + ++counter + "] " + m.getName() + 
						" : PP " + m.getpp() + " | PWR " + m.getPower() + 
						" | ACC " + m.getAccuracy() + " | TYPE " + m.getType());
			}
			System.out.println("[" + ++counter + "] INFO");
			System.out.println("[" + ++counter + "] RUN");
			System.out.print(">");			
			
			try { 
				int choice = input.nextInt();
				
				// if choice is a valid move option
				if (0 < choice && choice < counter - 1) {
					
					Moves selectedMove = checkMove(choice, fighter.getMoveSet());
					
					if (selectedMove != null)
						return selectedMove;
				}
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
		
	/** GET MOVE INFO METHOD
	  * Print out description of each move in moveset 
	  * @param Pokedex current fighter
	  **/
	private void displayInfo(Pokedex fighter) {	
				
		for (Moves m : fighter.getMoveSet()) {
			
			StringBuilder info = new StringBuilder(m.getInfo());
			
			// add new line after 40 characters
			int i = 0;				
			while ((i = info.indexOf(" ", i + 40)) != -1) {
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
	
	/** CHOOSE NEXT POKEMON METHOD
	  * Prompt user to choose next pokemon in party 
	  **/
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
				
				if (numPlayers == 2)
					pokemon2 = pokemonParty2.get(listNextFighter(2));
				else 
					pokemon2 = cpuSelectNextPokemon();
				
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
	
	/** CPU SELECT NEXT BEST POKEMON METHOD 
	 * Iterates through CPU party and finds best pokemon based on type and/or level
	 * @return best Pokemon found in party
	 **/
	private Pokedex cpuSelectNextPokemon() {
		
		// list to hold all candidates based on type effectiveness
		Map<Pokedex, Integer> pokemonList = new HashMap<>();
		
		// if more than 1 pokemon in CPU party
		if (pokemonParty2.size() > 1) {
			
			// loop through each pokemon in party
			for (Pokedex party : pokemonParty2) {
				
				// if party is single type
				if (party.getTypes() == null) {
					
					// loop through each type in target pokemon
					for (TypeEngine vulnType : pokemon1.getType().getVulnerability()) {				
					
						// if target is single type
						if (pokemon1.getTypes() == null) {	
							
							// if type matches target's vulnerability
							if (vulnType.toString().equals(party.getType().toString()))
								pokemonList.put(party, party.getLevel());
						}						
						// if target is multi type
						else {			
							
							// for each type in target
							for (TypeEngine type : pokemon1.getTypes()) {
								
								// loop through each vulnerability in type
								for (TypeEngine vuln : type.getVulnerability()) {									
	
									// if type matches target's vulnerability
									if (vuln.toString().equals(party.getType().toString()))
										pokemonList.put(party, party.getLevel());
								}
							}						
						}
					}
				}				
				// if party is multi type
				else { 
										
					// loop through each party type
					for (TypeEngine partyType : party.getTypes()) {
						
						// loop through each vulnerability in target type
						for (TypeEngine vulnType : pokemon1.getType().getVulnerability()) {			
							
							// if target is single type
							if (pokemon1.getTypes() == null) {
								
								// if type matches target's vulnerability
								if (vulnType.toString().equals(partyType.toString()))
									pokemonList.put(party, party.getLevel());
							}												
							// if target is multi type
							else {
								
								// for each type in target
								for (TypeEngine type : pokemon1.getTypes()) {
									
									// loop through each vulnerability in type
									for (TypeEngine vuln : type.getVulnerability()) {									
	
										// if type matches target's vulnerability
										if (vuln.toString().equals(partyType.toString()))
											pokemonList.put(party, party.getLevel());
									}		
								}
							}
						}
					}
				}
			}						
		}
		// if 1 pokemon remaining in party
		else 
			return pokemonParty2.get(0);
		
		// find best pokemon candidate based on max level
		if (!pokemonList.isEmpty()) 
			return Collections.max(pokemonList.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey(); 	
		else {			
			// loop through party and find highest level pokemon
			for (Pokedex p : pokemonParty2) 
				pokemonList.put(p, p.getLevel());
			
			return Collections.max(pokemonList.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
		}
	}
	/** END CPU SELECT NEXT POKEMON METHOD **/
	
	/** LIST FIGHTERS METHOD
	  * Print out available pokemon to choose from party 
	  * @param player number
	  **/
	public int listNextFighter(int player) {
		
		Sleeper.print("CHOOSE A POKEMON.", 700);
		
		ArrayList<Pokedex> pokemonParty = (player == 1 ) ? pokemonParty1 : pokemonParty2;
		
		int counter = 0;
		for (Pokedex p : pokemonParty) {						
				System.out.printf("[" + ++counter + "] " + p.getName() + 
					" : HP " + p.getHP() + "/" + p.getBHP() + " | Lv %02d | " + 
					((p.getType() == null) ? p.printTypes() : p.getType()) + 
					"\n", p.getLevel());	
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

	/** CHECK MOVE METHOD
	  * Find move in moveset, check if has PP, and return 
	  * @param choice, ArrayList of Moves moveSet
	  * @return move at given choice
	  **/
	private Moves checkMove(int choice, ArrayList<Moves> moveSet) {	
		
		// if move has pp
		if (moveSet.get(choice - 1).getpp() > 0) 
			return moveSet.get(choice - 1);
		else { 
			Sleeper.print("This move is out of PP and cannot be used!", 1000); 
			clearContent();
			displayHP();
			return null; 
		}
		
	}
	/** END CHECK MOVE METHOD **/
	
	/** ANNOUNCE WINNER METHOD
	  * Play victory song and print out winning trainer 
	  * @param name of winner, name of loser, money earned
	  **/
	private void announceWinner(String winner, String loser, int money) {
		
		SoundCard.setActive(true);		
		SoundCard.play("battle" + File.separator + "victory");
		
		Sleeper.print("Player defeated, " + loser + "!");
		Sleeper.print(winner + " got $" + money + " for winning!");
	}
	/** END ANNOUNCE WINNER METHOD **/

	/** CLEAR SCREEN METHOD **/	
	private static void clearContent() {		
		System.out.println(new String(new char[60]).replace("\0", "\r\n"));
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