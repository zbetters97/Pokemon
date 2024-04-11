package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.lang.Math;

import battle.Battle;
import pokemon.Pokedex;

/*** MAIN MENU CLASS ***/
public class MainMenu {
	
	// scanner to receive user input
	static Sleeper sleeper;	
	static Scanner input = new Scanner(System.in);
	static String select = "menu" + File.separator + "select";
	static String file, name1, name2;
	static SoundCard menuMusic, bgmusic;
	static int defaultLevel, players, partySize;
	static boolean cpuSelect;
	static ArrayList<Pokedex> party1, party2;

	/** LOAD METHOD
	  * Method called from Driver 
	  **/
	public static void load() {
		
		// arraylists to hold Pokemon party for both players
		party1 = new ArrayList<>(); party2 = new ArrayList<>();
		
		// setting defaults
		defaultLevel = -1;
		file =  "13battle-pc-normal";
		cpuSelect = false;
		
		// menuMusic = new SoundCard("menu" + File.separator + "intro-rb");
		menuMusic = new SoundCard("menu" + File.separator + "intro-pc");		
		menuMusic.playMusic();

		clearContent();	
		
		// players can only be set to 1 or 2
		players = mainMenu();
		
		// debug mode
		if (players == 5) {
			players = 1;
			name1 = "P1";
			name2 = "P2";
			defaultLevel = 50;
			SoundCard.setActive(false);
		}
		else {	
			name1 = inputName(1);
			name2 = (players == 1 ) ? "Red" : inputName(2);
		}
		
		selectPartySize();		
		selectParty();
		
		if (partySize > 1) {				
			party1 = selectStarter(1);
			
			if (players == 2) party2 = selectStarter(2);
			else cpuSelectStarter();
		}
		
		menuMusic.stopMusic();
		
		if (file != null) {
			bgmusic = new SoundCard("music" + File.separator + file);
			bgmusic.playMusic();
		}
		
		startGame();
	}
	/** END LOAD METHOD **/
	
	/** SELECT PLAYERS METHOD
	  * Prompt player to select a setting from the main menu
	  * @return choice
	  **/
	private static int mainMenu() {
								
		System.out.println("PLEASE SELECT MODE:\n\n"
				+ "[1] ONE PLAYER\n"
				+ "[2] TWO PLAYER\n"
				+ "[3] SETTINGS\n"
				+ "[4] POWER OFF");
		System.out.print(">");
		
		// loop until QUIT is selected
		while (true) {
			
			try { 
				int choice = input.nextInt(); 				
				SoundCard.play(select);
				
				switch (choice) {
					case 1: clearContent(); return choice;
					case 2: clearContent(); return choice;
					case 3: 
						clearContent(); 					
						settingsMenu();			
						System.out.println("PLEASE SELECT MODE:\n\n"
								+ "[1] ONE PLAYER\n"
								+ "[2] TWO PLAYER\n"
								+ "[3] SETTINGS\n"
								+ "[4] POWER OFF");
						System.out.print(">");
						break;
					case 4: 
						clearContent();
						Sleeper.setSpeed(60);
						Sleeper.print("SHUTTING DOWN...", 1700); 
						System.exit(0); 
					// debug mode
					case 5:
						clearContent();
						return 5;
					default: 
						Sleeper.print("ERROR: Input must be a valid selection!"); 
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
	/** END SELECT PLAYERS METHOD **/
	
	/** SETTINGS METHOD
	  * Settings main menu 
	  **/
	private static void settingsMenu() {
		
		System.out.println("PLEASE SELECT AN OPTION:\n\n"
				+ "[1] LEVELS\n"
				+ "[2] CPU PARTY\n"
				+ "[3] BATTLE MUSIC\n"
				+ "[4] SOUNDS\n"
				+ "[5] TEXT SPEED\n\n"
				+ "[0] <- BACK");
		System.out.print(">");
		
		// loop until BACK is selected
		while (true) {
			
			try { 
				int choice = input.nextInt(); 
				SoundCard.play(select);
				
				switch (choice) {
					case 1: 
						clearContent(); 
						levelSetting(); 
						clearContent();						
						System.out.println("PLEASE SELECT AN OPTION:\n\n"
								+ "[1] LEVELS\n"
								+ "[2] CPU PARTY\n"
								+ "[3] BATTLE MUSIC\n"
								+ "[4] SOUNDS\n"
								+ "[5] TEXT SPEED\n\n"
								+ "[0] <- BACK");
						System.out.print(">");
						break;						
					case 2: 
						clearContent(); 
						partySetting(); 
						clearContent();
						System.out.println("PLEASE SELECT AN OPTION:\n\n"
								+ "[1] LEVELS\n"
								+ "[2] CPU PARTY\n"
								+ "[3] BATTLE MUSIC\n"
								+ "[4] SOUNDS\n"
								+ "[5] TEXT SPEED\n\n"
								+ "[0] <- BACK");
						System.out.print(">");
						break;
					case 3: 
						clearContent(); 
						musicSetting(); 
						clearContent();
						System.out.println("PLEASE SELECT AN OPTION:\n\n"
								+ "[1] LEVELS\n"
								+ "[2] CPU PARTY\n"
								+ "[3] BATTLE MUSIC\n"
								+ "[4] SOUNDS\n"
								+ "[5] TEXT SPEED\n\n"
								+ "[0] <- BACK");
						System.out.print(">");
						break;
					case 4: 
						clearContent();
						soundSetting(); 
						clearContent();
						System.out.println("PLEASE SELECT AN OPTION:\n\n"
								+ "[1] LEVELS\n"
								+ "[2] CPU PARTY\n"
								+ "[3] BATTLE MUSIC\n"
								+ "[4] SOUNDS\n"
								+ "[5] TEXT SPEED\n\n"
								+ "[0] <- BACK");
						System.out.print(">");
						break;
					case 5:
						clearContent();
						textSetting();
						clearContent();
						System.out.println("PLEASE SELECT AN OPTION:\n\n"
								+ "[1] LEVELS\n"
								+ "[2] CPU PARTY\n"
								+ "[3] BATTLE MUSIC\n"
								+ "[4] SOUNDS\n"
								+ "[5] TEXT SPEED\n\n"
								+ "[0] <- BACK");
						System.out.print(">");
						break;
					case 0: clearContent(); return;
					default:
						Sleeper.print("ERROR: Input must be a valid selection!"); 
						System.out.print(">");
						break;
				}
			}
			catch (Exception e) {
				System.out.println(e);
				Sleeper.print("ERROR: Input must be a number!");
				System.out.print(">");
				input.next();
			}
		}	
	}
	/** END SETTINGS METHOD **/
	
	/** LEVEL SETTING METHOD
	  * Prompt player to select a default level for battle 
	  **/
	private static void levelSetting() {
					
		System.out.println("DEFAULT LEVELS:\n\n"
				+ "[1] 10\n"
				+ "[2] 25\n"
				+ "[3] 50\n"
				+ "[4] 100\n"
				+ "[5] DISABLED\n\n"
				+ "[0] <- BACK");
		System.out.print(">");
		
		while (true) {
			
			try { 
				int choice = input.nextInt(); 
				SoundCard.play(select);
				
				switch (choice) {
				case 1:
					defaultLevel = 10;
					Sleeper.print("DEFAULT LEVEL SET TO " + defaultLevel, 1200); 
					return;
				case 2:
					defaultLevel = 25;
					Sleeper.print("DEFAULT LEVEL SET TO " + defaultLevel, 1200);
					return;
				case 3:
					defaultLevel = 50;
					Sleeper.print("DEFAULT LEVEL SET TO " + defaultLevel, 1200);
					return;
				case 4:
					defaultLevel = 100;
					Sleeper.print("DEFAULT LEVEL SET TO " + defaultLevel, 1200);
					clearContent();
					return;
				case 5:
					defaultLevel = -1;
					Sleeper.print("DEFAULT LEVEL DISABLED", 1200);
					return;
				case 0:
					return;
				default:
					Sleeper.print("ERROR: Input must be a valid selection!"); 
					System.out.print(">");
					break;
				}
			}
			catch (Exception e) {
				Sleeper.print("ERROR: Input must be a number!");
				System.out.print(">");
				input.next();
			}
		}	
	}
	/** END LEVEL SETTING METHOD **/
	
	private static void partySetting() {
		
		System.out.println("CPU PARTY SELECTION:\n\n"
				+ "[1] MANUAL\n"
				+ "[2] RANDOM\n\n"
				+ "[0] <- BACK");
		System.out.print(">");
		
		while (true) {
			
			try { 
				int choice = input.nextInt(); 
				SoundCard.play(select);
				
				switch (choice) {
					case 1:
						cpuSelect = false;
						Sleeper.print("CPU PARTY SELECTION SET TO MANUAL", 1200); 
						return;
					case 2:
						cpuSelect = true;
						Sleeper.print("CPU PARTY SELECTION SET TO RANDOM", 1200); 
						return;
					case 0:
						return;
					default:
						Sleeper.print("ERROR: Input must be a valid selection!"); 
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
	
	/** SELECT MUSIC METHOD
	  * Prompt player to select a new song to play during battle 
	  **/
	private static void musicSetting() {
		
		// array list to hold music String
		ArrayList<String> musicList = new ArrayList<>();
		
		// hashmap to hold music file and corresponding index
		LinkedHashMap<Integer, String> musicDict = new LinkedHashMap<>();
		
		// get all songs from music folder
		String musicPath = new File("").getAbsolutePath() + File.separator + "lib" + 
				File.separator + "sounds" + File.separator + "music";
		File musicDirectory = new File(musicPath);
		
		// store all music files into array
		File musicFiles[] = musicDirectory.listFiles();
		
		// for each song in directory		
		for (int i = 0; i < musicFiles.length; i++) {			
			
			// add song name to list
			String music = musicFiles[i].getName();
			musicDict.put(i, music);
			
			// format music
			String song = musicFiles[i].getName()
					.replace("battle-", "")
					.replace(".wav", "")
					.replace("-", ": ")
					.replace("_", ", ")
					.toUpperCase();
			
			// add closing bracket and space after song index #
			StringBuilder formattedSong = new StringBuilder(song);
			formattedSong.insert(2, ']'); formattedSong.insert(3, ' ');	
			
			// add to array list
			musicList.add(formattedSong.toString());
			Collections.sort(musicList);
		}		
		
		System.out.println("BATTLE MUSIC:\n");
		
		for (int i = 0; i < musicList.size(); i++) 
			System.out.println("[" + musicList.get(i));	
		
		System.out.println("[" + (musicList.size() + 1) + "] NONE");
		System.out.println("\n[0] <- BACK");			
		System.out.print(">");
		
		while (true) {
			
			try { 				
				int choice = input.nextInt(); 
				SoundCard.play(select);
				
				if (0 < choice && choice <= musicDict.size()) {					
					file = musicDict.get(choice - 1).replace(".wav", "");
					Sleeper.print("NEW MUSIC SELECTED", 1200);
					return;
				}					
				else if (choice == musicDict.size() + 1) {
					file = null;
					Sleeper.print("MUSIC TURNED OFF", 1200);
					return;
				}
				else if (choice == 0)
					return;
				else {
					Sleeper.print("ERROR: Input must be a valid selection!");
					System.out.print(">");
				}
			}
			catch (Exception e) {
				Sleeper.print("ERROR: Input must be a valid selection!");
				System.out.print(">");
				input.next();
			}
		}
	}
	/** END SELECT MUSIC METHOD **/
	
	/** SOUND SETTING METHOD
	  * Prompt player to turn on or off sound effects 
	  **/
	private static void soundSetting() {
				
		System.out.println("SOUND EFFECTS:\n\n"
				+ "[1] ON\n"
				+ "[2] OFF\n\n"
				+ "[0] <- BACK");
		System.out.print(">");
		
		while (true) {
			
			try { 
				int choice = input.nextInt(); 
				SoundCard.play(select);
				
				switch (choice) {
					case 1:
						SoundCard.setActive(true);
						Sleeper.print("SOUND EFFECTS TURNED ON", 1200); 
						return;
					case 2:
						SoundCard.setActive(false);
						Sleeper.print("SOUND EFFECTS TURNED OFF", 1200); 
						return;
					case 0:
						return;
					default:
						Sleeper.print("ERROR: Input must be a valid selection!"); 
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
	/** END SOUND SETTINGS **/
	
	/** TEXT SPEED METHOD
	  * Prompt player to select a new text speed for Sleeper 
	  **/
	private static void textSetting() {
				
		System.out.println("TEXT SPEED:\n\n"
				+ "[1] SLOW\n"
				+ "[2] MEDIUM\n"
				+ "[3] FAST\n\n"
				+ "[0] <- BACK");
		System.out.print(">");
		
		while (true) {
			
			try { 
				int choice = input.nextInt(); 
				SoundCard.play(select);
				
				switch (choice) {
					case 1:
						Sleeper.setSpeed(45);
						Sleeper.print("TEXT SPEED SET TO SLOW", 1200); 
						return;
					case 2:
						Sleeper.setSpeed(30);
						Sleeper.print("TEXT SPEED SET TO MEDIUM", 1200); 
						return;
					case 3:
						Sleeper.setSpeed(15);
						Sleeper.print("TEXT SPEED SET TO FAST", 1200); 
						return;
					case 0:
						return;
					default:
						Sleeper.print("ERROR: Input must be a valid selection!"); 
						System.out.print(">");
						break;
				}
			}
			catch (Exception e) {
				Sleeper.print("ERROR: Input must be a number!");
				System.out.print(">");
				input.next();
			}
		}	
	}
	/** END TEXT SPEED METHOD **/
		
	/** INPUT NAME METHOD
	  * Prompt player to input their name 
	  * @param Integer player number
	  * @return String player name
	  **/
	private static String inputName(int player) {
				
		Sleeper.print("WHAT IS YOUR NAME, TRAINER " + player + "?");
		System.out.print(">");
		
		// loop until QUIT is selected
		while (true) {
			
			try { 
				String name = input.next(); 
				SoundCard.play(select);
				
				clearContent();
				Sleeper.print("WELCOME TO THE WORLD OF POKEMON, " + name + "!", 1200);
				clearContent();
				
				return name;
			}
			catch (Exception e) {
				Sleeper.print("ERROR: Something went wrong!");
				System.out.print(">");
				input.next();
			}
		}
	}
	/** END INPUT NAME METHOD **/
	
	/** SELECT PARTY SIZE METHOD
	  * Prompt player to input size of pokemon party (1-6)
	  **/
	private static void selectPartySize() {
					
		Sleeper.print("PLEASE SELECT PARTY SIZE (1-6):");
		System.out.print(">");
		
		// loop until Quit is selected
		while (true) {
			
			try { 
				int choice = input.nextInt(); 
				SoundCard.play(select);
				
				if (1 <= choice && choice <= 6) {
					partySize = choice;
					clearContent();	
					return;
				}
				else if (choice == 7) { 
					Sleeper.print("Shutting down..."); 
					System.exit(0); 
				}	
				else 
					Sleeper.print("ERROR: Input must be a valid selection!"); 
			}
			catch (Exception e) {
				Sleeper.print("ERROR: Input must be a number!");
				input.next();
			}
		}
	}
	/** END SELECT PARTY SIZE METHOD **/
	
	/** SELECT PARTY METHOD
	  * Prompt player to select pokemon from available list
	  **/
	private static void selectParty() {
		
		int turn = 1, choice = 0;	
		
		while (turn < (partySize * 2) + 1) {
			
			int counter = displayParty();
			
			if (!cpuSelect || players == 2) {
				System.out.println("\n\n" + ((turn % 2 != 0) ? name1 : name2) + 
						", PLEASE SELECT YOUR POKEMON PARTY:");
				System.out.print(">");
			}
			else if (cpuSelect && turn % 2 != 0) {
				System.out.println("\n\n" + name1 + ", PLEASE SELECT YOUR POKEMON PARTY:");
				System.out.print(">");
			}
			if (cpuSelect && turn % 2 == 0 && players == 1) {
				System.out.println("\n\n" + name2 + ", PLEASE SELECT YOUR POKEMON PARTY:");
				System.out.print("");
				Sleeper.pause(1700);
				choice = cpuSelectParty(counter);
				clearContent();
			}				
			else {	
				while (true) {		
		
					try { 
						choice = input.nextInt(); 
						
						// choice must be a number from 0 to last element in list
						if (0 < choice && choice <= counter) {
							
							// chosen Pokemon must not have already been selected by either trainer
							if (party1.contains(Pokedex.getPokemon(choice - 1)) || 
									party2.contains(Pokedex.getPokemon(choice - 1))) {					
								Sleeper.print("This Pokemon has already been chosen!");
								System.out.print(">");
							}
							else
								break;
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
			
			assignParty(choice, turn);			
			turn++; clearContent();
		}
	}
	/** END SELECT PARTY METHOD **/
	
	/** CPU SELECT PARTY METHOD
	  * Randomly select a pokemon from the available lsit
	  * @param Integer counter
	  * @return Integer chosen pokemon index
	  **/
	private static int cpuSelectParty(int counter) {
		
		int choice = 1 + (int)(Math.random() * ((counter - 1) + 1));
		
		while (true) {
			
			if (party1.contains(Pokedex.getPokemon(choice - 1)) || 
					party2.contains(Pokedex.getPokemon(choice - 1))) {	
				
				choice = 1 + (int)(Math.random() * ((counter - 1) + 1));
			}
			else
				return choice;
		}
	}
	/** END CPU SELECT PARTY METHOD **/
	
	/** ASSIGN PARTY METHOD
	  * Find pokemon in Pokedex at index and assign to party1 or party2
	  * @param Integer choice, Integer counter
	  **/
	private static void assignParty(int choice, int turn) {
		
		// assign fighter to party found at given index
		Pokedex selectedPokemon = Pokedex.getPokemon(choice - 1);
		
		// play pokemon cry
		SoundCard.play("pokedex" + File.separator + selectedPokemon.getName());
		
		if (turn % 2 != 0) party1.add(selectedPokemon);
		else party2.add(selectedPokemon);
	}
	
	/** DISPLAY POKEMON METHOD
	  * Display all unchosen pokemon for player to select for battle 
	  * @return counter
	  **/
	private static int displayParty() {
		
		System.out.println("--- AVAILABLE POKEMON FOR BATTLE ---\n");
		int counter = 0;		
		for (Pokedex p : Pokedex.getPokedex()) {	
			
			p.setLevel(defaultLevel);
			
			// don't display Pokemon who are already chosen					
			if (party1.contains(Pokedex.getPokemon(counter)) || 
					party2.contains(Pokedex.getPokemon(counter))) {
				counter++;
				continue;
			}
			
			// if pokemon has multiple types
			System.out.printf("[%02d] " + p.getName() + "\tLVL: %02d | TYPE: " + 
					((p.getTypes() == null) ? p.getType() : p.printTypes()) + 
					"\n", ++counter, p.getLevel());	
		}
		
		System.out.print("\n" + name1 + "'s PARTY: ");
		for (Pokedex p : party1) {
			System.out.print(p.getName() + "(" + 
				((p.getTypes() == null) ? p.getType().toString().charAt(0) : 
					p.printTypesShort()) + ") ");
		}
		
		System.out.print("\n" + name2 + "'s PARTY: ");			
		for (Pokedex p : party2) {
			System.out.print(p.getName() + "(" + 
				((p.getTypes() == null) ? p.getType().toString().charAt(0) : 
					p.printTypesShort()) + ") ");
		}
		
		return counter;
	}
	/** END DISPLAY POKEMON METHOD **/
	
	/** SELECT STARTER METHOD
	  * Prompt player to select starting fighter from party 
	  * @param Integer player number
	  * @return ArrayList<Pokedex> swapped party
	  **/
	private static ArrayList<Pokedex> selectStarter(int player) {

		// assign temp party to player 1 or player 2 party
		ArrayList<Pokedex> party = (player == 1) ? party1 : party2;
		
		System.out.print(name1 + "'s PARTY: ");
		for (Pokedex p : party1)
			System.out.print(p.getName() + " ");
		
		System.out.print("\n" + name2 + "'s PARTY: ");		
		for (Pokedex p : party2)
			System.out.print(p.getName() + " ");
		
		System.out.println("\n\n" + ((player == 1) ? name1 : name2) + 
				", PLEASE SELECT YOUR STARTING FIGHTER:\n");	
		
		int counter = 1;
		for (Pokedex pokemon : party) { 
			System.out.print("[" + counter + "] " + pokemon.getName() + "\n");
			counter++;
		}	
		System.out.print(">");
		
		while (true) {				
				
			try { 
				
				int choice = input.nextInt(); 
				
				// if choice is within party size
				if (1 <= choice && choice < counter) {
					
					if (player == 1) {
						
						// swap choice with pokemon at first slot
						Collections.swap(party, 0, choice - 1);
						
						clearContent();
						return party;
					}
					else {
						
						// swap choice with pokemon at first slot
						Collections.swap(party, 0, choice - 1);
						
						clearContent();
						return party;
					}
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
	/** END SELECT STARTER METHOD **/
	
	/** CPU SELECT STARTER METHOD
	 * Find best Pokemon in party and swap with first slot
	 **/
	private static void cpuSelectStarter() {
		
		// find best pokemon in cpu party based on Level, then HP, then Defense
		Pokedex bestFighter = Collections.max(party2, Comparator.comparingInt(Pokedex::getLevel)
				.thenComparing(Pokedex::getHP).thenComparing(Pokedex::getDefense));

		// find index of best pokemon
		int choice = party2.indexOf(bestFighter);	
		
		// swap with first slot
		Collections.swap(party2, 0, choice);	
	}
	/** END CPU SELECT STARTER METHOD **/
	
	/** START GAME METHOD
	  * Call start() from Battle class to begin trainer battle 
	  **/
	private static void startGame() {
		
		Battle game = new Battle(name1, name2, players, party1, party2);
		game.start();
		
		// when battle is over, stopMusic will be called
		if (!(bgmusic == null))	bgmusic.stopMusic();	
		
		// waits until user input to finish
		try { System.in.read(); } 
		catch (IOException e) { }
	}
	/** END START GAME METHOD **/
		
	/** CLEAR SCREEN METHOD **/	
	private static void clearContent() {		
		System.out.println(new String(new char[60]).replace("\0", "\r\n"));
	}
	/** END CLEAR SCREEN METHOD **/
}
/*** END MAIN MENU CLASS ***/