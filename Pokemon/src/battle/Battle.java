package battle;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import config.*;
import moves.Move;
import pokemon.Pokemon;
import properties.Type;

/*** BATTLE CLASS ***/
public class Battle {
	
	private static Scanner input = new Scanner(System.in);	
	private static String select = "menu" + File.separator + "select";
	private String name1, name2;
	private int numPlayers;	
	private int[] playerOneItems, playerTwoItems;
	
	private static Pokemon pokemon1, pokemon2;
	private ArrayList<Pokemon> party1, party2;
	private BattleEngine battle;
	
	/** CONSTRUCTOR **/
	public Battle(String name1, String name2, int numPlayers, 
			ArrayList<Pokemon> party1, ArrayList<Pokemon> party2) {
		
		this.name1 = name1; this.name2 = name2;
		
		// prevent errors upon startup by setting variables expected values
		if (numPlayers < 1) 
			numPlayers = 1;
		else if (2 < numPlayers) 
			numPlayers = 2;	
		
		this.numPlayers = numPlayers;
		
		// arrays to track item counts
		this.playerOneItems = new int[]{3, 2, 1};
		this.playerTwoItems = new int[]{3, 2, 1};
		
		this.party1 = party1;
		this.party2 = party2;
	}
	/** END CONSTRUCTOR **/
	
	/** MAIN MENU METHOD
	  * Begin battle sequence 
	  **/
	public void start() {
		
		pokemon1 = party1.get(0); 
		pokemon2 = party2.get(0);
						
		battle = new BattleEngine(pokemon1, pokemon2);
				
		turn();
	}
	/** END MAIN METHOD **/
		
	/** TURN METHOD
	  **/
	private void turn() {
		
		Move move1 = null, move2 = null;
		
		// loop until winner (return statement)
		while (true) {
			
			// check if a move is pending
			int delayedMove = battle.getDelayedMove(move1, move2);
			
			// no move is delayed
			if (delayedMove == 0) {
				
				if (numPlayers == 1) {			
					move1 = selectMove(pokemon1, 1);					
					move2 = battle.cpuSelectMove();	
					
					// if cpu used hyper potion
					if (move2 == null) {
						clearContent();
						SoundCard.play("battle" + File.separator + "heal");
						Sleeper.print(name2+ "'s POTION restored " + 
							pokemon2.getName() + "'s health!", 1700);
					}
				}
				else {
					move1 = selectMove(pokemon1, 1);				
					move2 = selectMove(pokemon2, 2);
				}
			}
			// a move is delayed
			else {
				// if player 1 is waiting for move, skip and get cpu or player 2 move
				if (delayedMove == 1) {
					
					if (numPlayers == 1) {
						
						move2 = battle.cpuSelectMove();	
						
						// if cpu used hyper potion
						if (move2 == null) {							
							clearContent();
							Sleeper.print(name2+ "'s POTION restored " + 
								pokemon2.getName() + "'s health!", 1700);
						}
					}
					else
						move2 = selectMove(pokemon2, 2);
				}				
				// if player 2 is waiting for move, skip and get player 1 move
				else if (delayedMove == 2)
					move1 = selectMove(pokemon1, 1);				
				// if both are waiting for move, keep existing moves
			}
			

			battle.move(move1, move2);		
			
			// if a pokemon is defeated
			if (battle.hasWinner()) {
				
				// 0 if party empty, 1 if p1 defeated, 2 if p2 defeated
				int next = chooseNextFighter();
				
				// remove move from battle
				if (next == 0) return;
				else if (next == 1) move1 = null;
				else if (next == 2) move2 = null;
			}
		}
	}
	/** END TURN METHOD **/
	
	/** SELECT MOVE METHOD
	  * Print out fighter moveset and return chosen move 
	  * @param Pokemon current fighter, int number of player
	  * @return selected move
	  **/
	private Move selectMove(Pokemon fighter, int player) {
		
		int counter = displayMove(fighter);
		
		while (true) {				
			
			try { 

				int choice = input.nextInt();				
				SoundCard.play(select);	
				
				// if choice is a valid move option
				if (0 < choice && choice < counter - 3) {
					
					// find move from fighter and return
					Move selectedMove = checkMove(choice, fighter.getMoveSet());
				
					// if selected move is valid
					if (selectedMove != null)
						return selectedMove;
				}
				// display moves info
				else if (choice == counter - 3) {
					clearContent();
					
					displayInfo(fighter);					
					displayMove(fighter);
				}
				// display items
				else if (choice == counter - 2) {
								
					clearContent();		
					
					if (displayItems(fighter, player))
						return null;
					
					displayMove(fighter);
				}
				// swap fighter
				else if (choice == counter - 1) {
													
					clearContent();
					
					Pokemon temp = swapFighter(player);
					
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
						// no move selected
						return null;
					}	
					
					displayMove(fighter);
				}
				// display items
				// run away
				else if (choice == counter) {
					
					if (numPlayers == 2) {
						Sleeper.print("You shouldn't run from a trainer battle!"); 
						System.out.print(">");
					}
					else {
						clearContent();	
						SoundCard.play("battle" + File.separator + "run");
						Sleeper.print("GOT AWAY SAFELY!", 1700);
						System.exit(0);
					}
				}
				else {
					Sleeper.print("This is not a move!"); 	
					System.out.print(">");
				}
			}
			catch (InputMismatchException e) { 
				SoundCard.play(select);	
				Sleeper.print("This is not a move!");
				System.out.print(">");
				input.next();
			}
		}
	}
	/** END SELECT MOVE METHOD **/	
	
	/** DISPLAY MOVES METHOD
	  * Print out in-battle pokemon's moveset and return index of counter
	  * @param Pokemon current fighter
	  * @return int index of counter
	  **/
	private int displayMove(Pokemon fighter) {
		
		printBattleInfo();
		
		System.out.println("\n\nWhat will " + fighter.getName() + " do?\n");
		
		int counter = 0;
		
		// find longest move name in set
		int mLength = 0;		
		for (int i = 0; i < fighter.getMoveSet().size(); i++) {			
		    if (fighter.getMoveSet().get(i).getName().length() > mLength)			   
		    	mLength = fighter.getMoveSet().get(i).getName().length();
		}		
		
		// find longest type name in set
		int tLength = 0;		
		for (int i = 0; i < fighter.getMoveSet().size(); i++) {			
			if (fighter.getMoveSet().get(i).getMove().getType().getName().length() > tLength)			   
				tLength = fighter.getMoveSet().get(i).getMove().getType().getName().length();
		}		
		
		// display all moves				
		for (Move m : fighter.getMoveSet()) {
			System.out.printf("[%d] %-" + (mLength + 1) + "s{ %-" + (tLength + 1) + "s| PP %2d/%2d }\n", 
				++counter, m.getName(), m.getMove().getType(), m.getpp(), m.getbpp());
		}
		
		System.out.println("\n[" + ++counter + "] INFO");
		System.out.println("[" + ++counter + "] BAG");
		System.out.println("[" + ++counter + "] POKEMON");
		System.out.println("\n[" + ++counter + "] RUN");
		System.out.print(">");	
			
		return counter;
	}
	/** END DISPLAY MOVES METHOD **/
	
	/** Print Battle method
	  * Print out current in-battle info 
	  **/
	private void printBattleInfo() {
		
		clearContent();
		
		Printer printHP = (name, pokemon, size) -> {
			
			String color;
			
			// display party
			System.out.print("\n" + name + "'s PARTY: ");			
			for (int i = 0; i < 6; i++) {
				if (i < size) color = Style.RED;
				else color = Style.GRAY;
				System.out.print(color + "○ " + Style.END);
			}	
			
			// display attributes
			System.out.print("\n" + Style.BOLD + pokemon.getName() + Style.END +
				((pokemon.getStatus() != null) ? " (" + pokemon.getStatus().printName() + ")" : "") + 
				" : HP " + pokemon.getHP() + "/" + pokemon.getBHP() + " | Lv " + pokemon.getLevel() + 
				" | " + ((pokemon.getTypes() == null) ? pokemon.getType().printType() : pokemon.printTypes()));	
			
			// display HP
			double remainHP = (double)pokemon.getHP() / (double)pokemon.getBHP();			
			for (int i = 0; i < pokemon.getBHP(); i++) {
				
				if (i % 50 == 0) System.out.println();
				
				if (i < pokemon.getHP()) {
					
					// change color of hp based on percentage remaining
					if (remainHP > .50) 
						color = Style.GREEN;
					else if (remainHP > .25) 
						color = Style.YELLOW;
					else 
						color = Style.RED;		
				}
				else 
					color = Style.BLACK;
					
				System.out.print(color + "." + Style.END);
			}
			System.out.println();
		};
		
		printHP.print(name1, pokemon1, party1.size());
		printHP.print(name2, pokemon2, party2.size());
		
	}
	/** END PRINT BATTLE METHOD **/
	
	/** CHECK MOVE METHOD
	  * Find move in moveset, check if has PP, and return 
	  * @param choice, ArrayList of Move moveSet
	  * @return move at given choice
	  **/
	private Move checkMove(int choice, ArrayList<Move> moveSet) {	
		
		// if move has pp
		if (moveSet.get(choice - 1).getpp() > 0) 
			return moveSet.get(choice - 1);
		else { 
			Sleeper.print("This move is out of PP and cannot be used!", 1000); 
			return null; 
		}
		
	}
	/** END CHECK MOVE METHOD **/
		
	/** GET MOVE INFO METHOD
	  * Print out description of each move in moveset 
	  * @param Pokemon current fighter
	  **/
	private void displayInfo(Pokemon fighter) {	
				
		System.out.println("MOVE DESCRIPTIONS:\n");
		
		for (Move move : fighter.getMoveSet()) {
			
			StringBuilder info = new StringBuilder(move.getMove().getInfo());
			
			// add new line after 40 characters
			int i = 0;				
			while ((i = info.indexOf(" ", i + 40)) != -1) {
				info.replace(i, i + 1, "\n\t");
			}
			
			System.out.println(move.getName().toUpperCase() + " : PP " + move.getpp() + 
					((move.getMove().getPower() == 0) ? "" : " | PWR " + move.getMove().getPower()) + 
					(move.getMove().isToSelf() ? "" : " | ACC " + move.getMove().getAccuracy()) + 
					" | TYPE " + move.getMove().getType().printType() +
					(move.getMove().getEffect() == null ? "" : " | STA " + move.getMove().getEffect().printName()));
			
			System.out.println("\t\"" + info.toString() + "\"\n");
		}
		
		System.out.println("[0] <- BACK");
		System.out.print(">");
		
		try { 
			int choice = input.nextInt();
			
			// if choice is a valid move option
			if (choice == 0) {
				SoundCard.play(select);	
			}
			else {
				SoundCard.play(select);	
				Sleeper.print("This is not a valid selection!");
				System.out.print(">");
			}
		}
		catch (InputMismatchException e) { 
			SoundCard.play(select);	
			Sleeper.print("This is not a valid selection!");
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
	private Pokemon swapFighter(int player) {
		
		ArrayList<Pokemon> pokemonParty = (player == 1 ) ? party1 : party2;
		
		// only 1 pokemon in party
		if (pokemonParty.size() == 1) {
			Sleeper.print("THERE ARE NO OTHER POKEMON IN YOUR PARTY!", 1700);			
			return null;
		}
		
		System.out.println("SELECT A POKEMON TO SWAP IN:\n");
		
		// skip first pokemon in party
		int counter = 1;
		for (Pokemon p : pokemonParty.subList(1, pokemonParty.size())) {	
			
			System.out.printf("[" + ++counter + "] " + p.getName() + 
				" : HP " + p.getHP() + "/" + p.getBHP() + " | Lv %02d | " + 
				((p.getType() == null) ? p.printTypes() : p.getType().printType()) + 
				"\n", p.getLevel());
		}
		System.out.println("\n[0] <- BACK");
		System.out.print(">");
		
		int choice = 0;
		
		while (true) {				
			try { 
				choice = input.nextInt(); 
				
				// choice must be a number from 0 to last element in list													
				if (1 < choice && choice <= counter) {
															
					Pokemon newFighter = pokemonParty.get(choice - 1);
					
					if (newFighter.getName().equals(pokemon1.getName())) {
						Sleeper.print(pokemon1.getName() + " is already in battle!");
						System.out.print(">");
					}
					else {					
						clearContent();
						
						if (player == 1) {
							Sleeper.print("(" + name1 + ") " + pokemonParty.get(0) + ", switch out! Come back!", 1700);
							// switch spots in party
							Collections.swap(party1, 0, choice - 1);
							Sleeper.print("(" + name1 + ") Go! " + newFighter.getName() + "!");
						}
						else {
							Sleeper.print("(" + name2 + ") " + pokemonParty.get(0) + ", switch out! Come back!", 1700);
							// switch spots in party
							Collections.swap(party2, 0, choice - 1);
							Sleeper.print("(" + name2 + ") Go! " + newFighter.getName() + "!");
						}
						
						SoundCard.play("pokedex" + File.separator + newFighter.getName());
						Sleeper.pause(1700);	
						
						clearContent();	
						
						return newFighter;	
					}
				}
				else if (choice == 0) {
					SoundCard.play(select);
					return null;			
				}
				else {
					SoundCard.play(select);	
					Sleeper.print("This is not a valid selection!");
					System.out.print(">");
				}
			}
			catch (Exception e) {
				SoundCard.play(select);	
				Sleeper.print("This is not a valid selection!");
				System.out.print(">");
				input.next();
			}
		}
	}
	/** END SWAP FIGHTER METHOD **/
	
	/** DISPLAY ITEMS METHOD
	  * Prompt user to select an item to heal Pokemon
	  * @param Pokemon current fighter, int player
	  * @return boolean if player selected BACK
	  **/
	private boolean displayItems(Pokemon fighter, int player) {
		
		// rolling counter to track items in player inventory
		int iCount[];		
		if (player == 1) iCount = playerOneItems;
		else iCount = playerTwoItems;
		
		System.out.println("SELECT AN ITEM TO USE ON " + fighter.getName() + ":\n");
		System.out.println("[1] POTION (x" + iCount[0] + ")\n"
				+ "[2] HYPER POTION (x" + iCount[1] + ")\n"
				+ "[3] FULL RESTORE (x" + iCount[2] + ")\n\n"
				+ "[0] <- BACK");
		System.out.print(">");
		
		// loop until BACK is selected
		int choice = 0;
		while (true) {
			
			try { 
				choice = input.nextInt(); 
				SoundCard.play(select);
				
				switch (choice) {
					case 1: 												
						if (iCount[0] != 0) {
							iCount[0] -= 1;
						
							clearContent(); 
							healFighter(20, player);
							
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
							healFighter(999, player);		
							clearContent();
							
							return true;
						}
						else {
							Sleeper.print("THERE ARE NO FULL RESTORES LEFT!"); 
							System.out.print(">");
						}
						break;
					case 0: 
						return false;
					default:
						Sleeper.print("This is not a valid selection!"); 
						System.out.print(">");
						break;
				}
				if (player == 1) 
					playerOneItems = iCount;
				else 
					playerTwoItems = iCount;
			}
			catch (Exception e) {
				SoundCard.play(select);	
				Sleeper.print("This is not a valid selection!"); 
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
		
		Pokemon fighter = (player == 1) ? pokemon1 : pokemon2;
		
		int newHP = fighter.getHP() + heal;
		
		if (newHP > fighter.getBHP()) 
			newHP = fighter.getBHP();		
		
		fighter.setHP(newHP);
		
		SoundCard.play("battle" + File.separator + "heal");
		
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
	
	/** CHOOSE NEXT FIGHTER METHOD
	  * Announce winner if party empty, get next fighter if not
	  * @return 0 if party empty, 1 if p1 defeated, 2 if p2 defeated
	  **/
	public int chooseNextFighter() {
		
		int choice;
		
		// true if player2 defeated player 1
		boolean playerTwoWinner = battle.getWinningPokemon().getName().equals(pokemon2.getName());
		if (playerTwoWinner) {
			
			// remove from player 1 party			
			for (Pokemon p : party1) {				
				if (p.getIndex() == pokemon1.getIndex()) {
					party1.remove(p);
					break;
				}
			}
			
			// if no pokemon left
			if (party1.isEmpty()) {
				announceWinner(name2, name1, battle.getMoney(0));				
				return 0;
			}
			else {			
				// reset winner
				battle.setWinningPokemon(null);
				
				// ask which pokemon to sub in and swap
				choice = listNextFighter(1);
				pokemon1 = party1.get(choice - 1);
				battle.swapPokemon(pokemon1, 0);
				
				if (party1.size() > 1)
					Collections.swap(party1, 0, choice - 1);
				
				clearContent();
				
				Sleeper.print("(" + name1 + ") Go! " + pokemon1.getName() + "!");
				SoundCard.play("pokedex" + File.separator + pokemon1.getName());
				Sleeper.pause(1700);
				
				return 1;
			}
		}
		// if player 1 defeated player 2
		else {				
			
			// remove from party 2
			for (Pokemon p : party2) {				
				if (p.getIndex() == pokemon2.getIndex()) {
					party2.remove(p);
					break;
				}
			}
			
			// if no pokemon left
			if (party2.isEmpty()) {
				announceWinner(name1, name2, battle.getMoney(1));	
				return 0;
			}
			else {			
				// reset winner
				battle.setWinningPokemon(null);
				
				if (numPlayers == 2) {
					
					// ask which pokemon to sub in and swap
					choice = listNextFighter(2);
					pokemon2 = party2.get(choice - 1);
					
					battle.swapPokemon(pokemon2, 1);
					
					if (party2.size() > 1)
						Collections.swap(party2, 0, choice - 1);
				}
				else {
					pokemon2 = cpuSelectNextPokemon();
					battle.swapPokemon(pokemon2, 1);
				}
				
				clearContent();
								
				Sleeper.print("(" + name2 + ") Go! " + pokemon2.getName() + "!");
				SoundCard.play("pokedex" + File.separator + pokemon2.getName());
				Sleeper.pause(1700);	
				
				return 2;
			}		
		}
	}	
	/** END CHOOSE NEXT POKEMON METHOD **/	
	
	/** LIST FIGHTERS METHOD
	  * Print out available pokemon to choose from party 
	  * @param player number
	  **/
	public int listNextFighter(int player) {
		
		Sleeper.print("CHOOSE A POKEMON:\n", 700);
		
		ArrayList<Pokemon> pokemonParty = (player == 1 ) ? party1 : party2;
		
		int counter = 0;
		for (Pokemon p : pokemonParty) {						
				System.out.printf("[" + ++counter + "] " + p.getName() + 
					" : HP " + p.getHP() + "/" + p.getBHP() + " | Lv %02d | " + 
					((p.getType() == null) ? p.printTypes() : p.getType().printType()) + 
					"\n", p.getLevel());	
		}
		
		System.out.print(">");
		
		int choice = 0;
		
		while (true) {				
			try { 
				choice = input.nextInt(); 
				
				// choice must be a number from 0 to last element in list
				if (0 < choice && choice <= counter) 
					return choice;									
				else {
					SoundCard.play(select);
					Sleeper.print("This is not a valid selection!"); 
					System.out.print(">");
				}
			}
			catch (Exception e) {
				SoundCard.play(select);
				Sleeper.print("This is not a valid selection!"); 
				System.out.print(">");
				input.next();
			}
		}
	}
	/** END LIST FIGHTERS METHOD **/	
	
	/** CPU SELECT NEXT BEST POKEMON METHOD 
	 * Iterates through CPU party and finds best pokemon based on type and/or level
	 * @return best Pokemon found in party
	 **/
	private Pokemon cpuSelectNextPokemon() {
		
		// list to hold all candidates based on type effectiveness
		Map<Pokemon, Integer> pokemonList = new HashMap<>();
		
		// if more than 1 pokemon in CPU party
		if (party2.size() > 1) {
			
			// loop through each pokemon in party
			for (Pokemon party : party2) {
				
				// if party is single type
				if (party.getTypes() == null) {
					
					// if target is single type
					if (pokemon1.getTypes() == null) {	
						
						// loop through each type in target pokemon
						for (Type vulnType : pokemon1.getType().getVulnerability().keySet()) {	
							
							// if type matches target's vulnerability
							if (vulnType.getName().equals(party.getType().getName()))
								pokemonList.put(party, party.getLevel());
						}
					}					
					// if target is multi type
					else {			
						
						// for each type in target
						for (Type type : pokemon1.getTypes()) {
							
							// loop through each vulnerability in type
							for (Type vuln : type.getVulnerability().keySet()) {									

								// if type matches target's vulnerability
								if (vuln.getName().equals(party.getType().getName()))
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
						for (Type type : party.getTypes()) {
							
							// loop through each vulnerability in target pokemon
							for (Type vulnType : pokemon1.getType().getVulnerability().keySet()) {	
							
								// if type matches target's vulnerability
								if (vulnType.getName().equals(type.getName()))
									pokemonList.put(party, party.getLevel());
							}
						}
						
						
					}					
					// if target is multi type
					else {			
						
						// for each type in party
						for (Type parType : party.getTypes()) {

							// for each type in target
							for (Type tarType : pokemon1.getTypes()) {
								
								// loop through each vulnerability in type
								for (Type vuln : tarType.getVulnerability().keySet()) {									
	
									// if type matches target's vulnerability
									if (vuln.getName().equals(parType.getName()))
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
			return party2.get(0);
		
		Pokemon bestPokemon;
		
		// find best pokemon based on max level
		if (!pokemonList.isEmpty()) {
			bestPokemon = Collections.max(pokemonList.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
			return bestPokemon;
		}
		else {			
			// loop through party and find highest level pokemon
			for (Pokemon p : party2) 
				pokemonList.put(p, p.getLevel());
			
			bestPokemon = Collections.max(pokemonList.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
			return bestPokemon;
		}
	}
	/** END CPU SELECT NEXT POKEMON METHOD **/
	
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
interface Printer {
	public void print(String trainer, Pokemon pokemon, int size);
}
/*** END LAMBDA INTERFACE ***/