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
	private int[] playerOneItems, playerTwoItems;
	
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
		
		// arrays to track item counts
		this.playerOneItems = new int[]{3, 2, 1};
		this.playerTwoItems = new int[]{3, 2, 1};
		
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
			displayParty(); displayHP();
			
			if (numPlayers == 1) {			
				move1 = displayMoves(pokemon1, 1);	
				move2 = battle.cpuSelectMove();	
			}
			else {
				move1 = displayMoves(pokemon1, 1);
				
				clearContent();
				displayParty(); displayHP();
				
				move2 = displayMoves(pokemon2, 2);
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
			
			// check if a move is pending
			int delayedMove = battle.hasDelayedMove(move1, move2);			
			if (delayedMove != 0) {
				
				displayParty(); displayHP();
				
				// if player 1 is waiting for move, skip and assign move to cpu or player 2
				if (delayedMove == 1) {
					
					if (numPlayers == 1)
						move2 = battle.cpuSelectMove();	
					else
						move2 = displayMoves(pokemon2, 2);
				}				
				// if player 2 is waiting for move, skip and assign move to player 1
				else if (delayedMove == 2)
					move1 = displayMoves(pokemon1, 1);
				
				//if both are waiting for move, keep existing moves
				
				clearContent();
				battle.move(move1, move2);
				clearContent();
			}
			
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
	private Moves displayMoves(Pokedex fighter, int player) {
		
		while (true) {		
			
			System.out.println("What will " + fighter.getName() + " do?\n");
			
			int counter = 0;
			
			// display all moves				
			for (Moves m : fighter.getMoveSet()) {
				
				System.out.println("[" + ++counter + "] " + m.getName() + " : PP " + m.getpp() + 
						((m.getPower() == 0) ? "" : " | PWR " + m.getPower()) + 
						" | ACC " + m.getAccuracy() + " | TYPE " + m.getType());
								
			}
			System.out.println("[" + ++counter + "] MOVES INFO");
			System.out.println("[" + ++counter + "] SWAP POKEMON");
			System.out.println("[" + ++counter + "] USE AN ITEM");
			System.out.println("[" + ++counter + "] RUN");
			System.out.print(">");			
			
			try { 
				int choice = input.nextInt();
				
				// if choice is a valid move option
				if (0 < choice && choice < counter - 3) {
					
					Moves selectedMove = checkMove(choice, fighter.getMoveSet());
					
					if (selectedMove != null)
						return selectedMove;
				}
				else if (choice == counter - 3) {
					clearContent();
					displayInfo(fighter);
				}
				else if (choice == counter - 2) {
					
					clearContent();
					
					Pokedex temp = swapFighter(player);
					
					// if player didn't select the BACK option
					if (temp != null) {
						if (player == 1) {
							pokemon1 = temp;
							battle.swapPokemon(pokemon1, 0);
						}
						else if (player == 2) {
							pokemon2 = temp;
							battle.swapPokemon(pokemon2, 1);
						}						
						return null;
					}					
				}
				else if (choice == counter - 1) {
					
					clearContent();		
					
					if (displayItems(fighter, player))
						return null;
				}
				else if (choice == counter) {
					clearContent();	
					SoundCard.play("battle" + File.separator + "run");
					Sleeper.print("Got away safely!", 1200);
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

	/** SWAP FIGHTER METHOD
	  * Prompt user to choose another Pokemon in party to swap in
	  * @param int current player
	  * @return Pokedex new fighter
	  **/
	private Pokedex swapFighter(int player) {
		
		Sleeper.print("SELECT A POKEMON TO SWAP IN:", 700);
		
		ArrayList<Pokedex> pokemonParty = (player == 1 ) ? pokemonParty1 : pokemonParty2;
		
		int counter = 0;
		for (Pokedex p : pokemonParty) {	
			System.out.printf("[" + ++counter + "] " + p.getName() + 
				" : HP " + p.getHP() + "/" + p.getBHP() + " | Lv %02d | " + 
				((p.getType() == null) ? p.printTypes() : p.getType()) + 
				"\n", p.getLevel());
		}
		System.out.println("[" + ++counter + "] BACK");
		System.out.print(">");
		
		int choice = 0;
		
		while (true) {				
			try { 
				choice = input.nextInt(); 
				
				// choice must be a number from 0 to last element in list													
				if (0 < choice && choice < counter) {
															
					Pokedex newFighter = pokemonParty.get(choice - 1);
					
					if (newFighter.getName().equals(pokemon1.getName())) {
						Sleeper.print(pokemon1.getName() + " is already in battle!");
						System.out.print(">");
					}
					else {					
						clearContent();
						
						if (player == 1)
							Sleeper.print(name1 + ": GO, " + newFighter.getName() + "!");
						else
							Sleeper.print(name2 + ": GO, " + newFighter.getName() + "!");
						
						SoundCard.play("pokedex" + File.separator + newFighter.getName());
						Sleeper.pause(1700);	
						
						clearContent();						
						
						return newFighter;	
					}
				}
				else if (choice == counter) {
					clearContent();
					displayHP();
					return null;			
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
	/** END SWAP FIGHTER METHOD **/
	
	/** DISPLAY ITEMS METHOD
	  * Prompt user to select an item to heal Pokemon
	  * @param Pokedex current fighter, int player
	  * @return boolean if player selected BACK
	  **/
	private boolean displayItems(Pokedex fighter, int player) {
		
		// rolling counter to track items in player inventory
		int iCount[];		
		if (player == 1) iCount = playerOneItems;
		else iCount = playerTwoItems;
		
		Sleeper.print("PLEASE SELECT AN ITEM TO USE ON " + fighter.getName() + ":", 700);
		System.out.println("[1] POTION (x" + iCount[0] + ")\n"
				+ "[2] HYPER POTION (x" + iCount[1] + ")\n"
				+ "[3] FULL RESTORE (x" + iCount[2] + ")\n"
				+ "[4] BACK");
		System.out.print(">");
		
		// loop until BACK is selected
		int choice = 0;
		while (true) {
			
			try { 
				choice = input.nextInt(); 
				
				switch (choice) {
					case 1: 
						if (iCount[0] != 0) {
							iCount[0] -= 1;
							
							clearContent(); 
							healFighter(20, player);						
							clearContent();
							
							return true;
						}
						else {
							Sleeper.print("THERE ARE NO POTIONS LEFT!"); 
							System.out.print(">");
						}
						break;						
					case 2: 
						if (iCount[1] != 0) {
							iCount[1] -= 1;
							
							clearContent(); 
							healFighter(120, player);						
							clearContent();	
							
							return true;
						}
						else {
							Sleeper.print("THERE ARE NO HYPER POTIONS LEFT!"); 
							System.out.print(">");
						}
						break;
					case 3: 
						if (iCount[2] != 0) {
							iCount[2] -= 1;
							
							clearContent();
							healFighter(1000, player);		
							clearContent();
							
							return true;
						}
						else {
							Sleeper.print("THERE ARE NO FULL RESTORES LEFT!"); 
							System.out.print(">");
						}
						break;
					case 4: 
						clearContent(); 
						displayHP();
						return false;
					default:
						Sleeper.print("ERROR: Input must be a valid selection!"); 
						System.out.print(">");
						break;
				}
				if (player == 1) playerOneItems = iCount;
				else playerTwoItems = iCount;
			}
			catch (Exception e) {
				System.out.println(e);
				Sleeper.print("ERROR: Input must be a number!");
				System.out.print(">");
				input.next();
			}
		}	
	}
	/** END DISPLAY ITEMS METHOD **/
	
	/** HEAL FIGHTER METHOD
	  * Applies potion healing to Pokemon HP
	  * @param int hp to heal, int player
	  **/
	private void healFighter(int heal, int player) {
		
		Pokedex fighter = (player == 1) ? pokemon1 : pokemon2;
		
		int newHP = fighter.getHP() + heal;
		
		if (newHP > fighter.getBHP()) 
			newHP = fighter.getBHP();		
		
		fighter.setHP(newHP);
		
		switch (heal) {
			case 20:
				Sleeper.print(((player == 1) ? name1 : name2) + "'s POTION restored " + 
					fighter.getName() + "'s health!", 1700);
				break;
			case 120:
				Sleeper.print(((player == 1) ? name1 : name2) + "'s HYPER POTION restored " + 
						fighter.getName() + "'s health!", 1700);
				break;
			case 1000:
				Sleeper.print(((player == 1) ? name1 : name2) + "'s FULL RESTORE restored " + 
					fighter.getName() + "'s health!", 1700);
				break;
			default:
				break;
		}
	}
	/** END HEAL FIGHTER METHOD **/
	
	/** CHOOSE NEXT POKEMON METHOD
	  * Prompt user to choose next Pokemon in party 
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
				
				if (numPlayers == 2) {
					
					// ask which pokemon to sub in and swap
					choice = listNextFighter(2);
					pokemon2 = pokemonParty2.get(choice - 1);
				}
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
					
					// if target is single type
					if (pokemon1.getTypes() == null) {	
						
						// loop through each type in target pokemon
						for (TypeEngine vulnType : pokemon1.getType().getVulnerability()) {	
							
							// if type matches target's vulnerability
							if (vulnType.toString().equals(party.getType().toString()))
								pokemonList.put(party, party.getLevel());
						}
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
				// if party is multi type
				else { 
										
					// if target is single type
					if (pokemon1.getTypes() == null) {	
						
						// for each type in party
						for (TypeEngine type : party.getTypes()) {
							
							// loop through each vulnerability in target pokemon
							for (TypeEngine vulnType : pokemon1.getType().getVulnerability()) {	
							
								// if type matches target's vulnerability
								if (vulnType.toString().equals(type.toString()))
									pokemonList.put(party, party.getLevel());
							}
						}
						
						
					}					
					// if target is multi type
					else {			
						
						// for each type in party
						for (TypeEngine parType : party.getTypes()) {

							// for each type in target
							for (TypeEngine tarType : pokemon1.getTypes()) {
								
								// loop through each vulnerability in type
								for (TypeEngine vuln : tarType.getVulnerability()) {									
	
									// if type matches target's vulnerability
									if (vuln.toString().equals(parType.toString()))
										pokemonList.put(party, party.getLevel());
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