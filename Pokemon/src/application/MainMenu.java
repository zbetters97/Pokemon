package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Scanner;

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
	static ArrayList<Pokedex> party1, party2;

	/** LOAD METHOD
	  * Method called from Driver 
	  **/
	public static void load() {
		
		// arraylists to hold Pokemon party for both players
		party1 = new ArrayList<>(); party2 = new ArrayList<>();
		
		// default level -1 if not set
		defaultLevel = -1;
		file =  "15battle-ssbm-stadium";
		
		// menuMusic = new SoundCard("menu" + File.separator + "intro-rb");
		menuMusic = new SoundCard("menu" + File.separator + "intro-pc");		
		menuMusic.playMusic();

		clearContent();	
		
		// players can only be set to 1 or 2
		players = mainMenu();
		
		name1 = inputName(1);
		name2 = (players == 1 ) ? "Red" : inputName(2);
		
		selectPartySize();		
		selectParty();
		
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
				+ "[1] MUSIC\n"
				+ "[2] TEXT SPEED\n"
				+ "[3] SOUNDS\n"
				+ "[4] LEVELS\n"
				+ "[5] <- BACK");
		System.out.print(">");
		
		// loop until BACK is selected
		while (true) {
			
			try { 
				int choice = input.nextInt(); 
				SoundCard.play(select);
				
				switch (choice) {
					case 1: 
						clearContent(); 
						musicSetting(); 
						clearContent();						
						System.out.println("PLEASE SELECT AN OPTION:\n\n"
								+ "[1] MUSIC\n"
								+ "[2] TEXT SPEED\n"
								+ "[3] SOUNDS\n"
								+ "[4] LEVELS\n"
								+ "[5] <- BACK");
						System.out.print(">");
						break;						
					case 2: 
						clearContent(); 
						textSetting(); 
						clearContent();
						System.out.println("PLEASE SELECT AN OPTION:\n\n"
								+ "[1] MUSIC\n"
								+ "[2] TEXT SPEED\n"
								+ "[3] SOUNDS\n"
								+ "[4] LEVELS\n"
								+ "[5] <- BACK");
						System.out.print(">");
						break;
					case 3: 
						clearContent(); 
						soundSetting(); 
						clearContent();
						System.out.println("PLEASE SELECT AN OPTION:\n\n"
								+ "[1] MUSIC\n"
								+ "[2] TEXT SPEED\n"
								+ "[3] SOUNDS\n"
								+ "[4] LEVELS\n"
								+ "[5] <- BACK");
						System.out.print(">");
						break;
					case 4: 
						clearContent();
						levelSetting(); 
						clearContent();
						System.out.println("PLEASE SELECT AN OPTION:\n\n"
								+ "[1] MUSIC\n"
								+ "[2] TEXT SPEED\n"
								+ "[3] SOUNDS\n"
								+ "[4] LEVELS\n"
								+ "[5] <- BACK");
						System.out.print(">");
						break;
					case 5: clearContent(); return;
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
	/** END SETTINGS METHOD **/
	
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
		
		Sleeper.print("PLEASE SELECT MUSIC:\n", 700);		
		
		System.out.println("[00] NONE");
		
		for (int i = 0; i < musicList.size(); i++) 
			System.out.println("[" + musicList.get(i));	
		
		System.out.println("[" + (musicList.size() + 1) + "] <- BACK");		
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
				else if (choice == 0) {
					file = null;
					Sleeper.print("MUSIC TURNED OFF", 1200);
					return;
				}
				else if (choice == musicDict.size() + 1)
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
	
	/** TEXT SPEED METHOD
	  * Prompt player to select a new text speed for Sleeper 
	  **/
	private static void textSetting() {
				
		System.out.println("TEXT SPEED:\n\n"
				+ "[1] SLOW\n"
				+ "[2] MEDIUM\n"
				+ "[3] FAST");
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
	
	/** SOUND SETTING METHOD
	  * Prompt player to turn on or off sound effects 
	  **/
	private static void soundSetting() {
				
		System.out.println("SOUND EFFECTS:\n\n"
				+ "[1] ON\n"
				+ "[2] OFF");
		System.out.print(">");
		
		// loop until BACK is selected
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
	
	/** LEVEL SETTING METHOD
	  * Prompt player to select a default level for battle 
	  **/
	private static void levelSetting() {
					
		System.out.println("DEFAULT LEVELS:\n\n"
				+ "[1] 10\n"
				+ "[2] 25\n"
				+ "[3] 50\n"
				+ "[4] 100\n"
				+ "[5] DISABLED\n"
				+ "[6] <- BACK");
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
				case 6:
					clearContent();
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
				Sleeper.print("WELCOME TO THE WORLD OF POKEMON, " + name + "!", 1500);
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
	  * call displayPokemon() and prompt player to select pokemon for battle
	  **/
	private static void selectParty() {
		
		int c = 0, choice = 0;	
		
		while (c < partySize * 2) {
								
			int counter = displayPokemon();
			
			System.out.println("\n\n" + ((c % 2 == 0) ? name1 : name2) + 
					", PLEASE SELECT YOUR POKEMON PARTY:");
			System.out.print(">");
			
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
			
			// assign fighter to party found at given index
			Pokedex selectedPokemon = Pokedex.getPokemon(choice - 1);
			
			// play pokemon cry
			SoundCard.play("pokedex" + File.separator + selectedPokemon.getName());
			
			if (c % 2 == 0) 
				party1.add(selectedPokemon);
			else 
				party2.add(selectedPokemon);
		
			c++; clearContent();
		}
	}
	/** END SELECT PARTY METHOD **/
	
	/** DISPLAY POKEMON METHOD
	  * Display all unchosen pokemon for player to select for battle 
	  * @return counter
	  **/
	private static int displayPokemon() {
		
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
		for (Pokedex p : party1) System.out.print(p.getName() + " ");
		
		System.out.print("\n" + name2 + "'s PARTY: ");			
		for (Pokedex p : party2) System.out.print(p.getName() + " ");
		
		return counter;
	}
	/** END DISPLAY POKEMON METHOD **/
	
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