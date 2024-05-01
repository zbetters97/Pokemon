package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.lang.Math;

import battle.Battle;
import config.*;
import pokemon.Pokemon;

/*** MAIN MENU CLASS ***/
public class MainMenu {
	
	static Sleeper sleeper;	
	static Scanner input = new Scanner(System.in);
	static String select = "menu" + File.separator + "select";
	static String file, name1, name2;
	static SoundCard menuMusic, bgmusic;
	
	static LinkedHashMap<Integer, String> musicDict;
	static List<String> musicList;
	
	static int defaultLevel, players, partySize;
	static int cpuSelect;
	static boolean battleShift, trainerRed;
	static List<Pokemon> party1, party2;

	/** LOAD METHOD
	  * Method called from Driver 
	  **/
	public static void load() {
		
		// arraylists to hold Pokemon party for both players
		party1 = new ArrayList<>(); party2 = new ArrayList<>();
		
		// setting defaults
		defaultLevel = -1;
		file = "02battle-rs-trainer";
		cpuSelect = 0;
		battleShift = true;
		
		// pull music from folder and assign to lists		
		musicDict = new LinkedHashMap<>();
		musicList = new ArrayList<>();
		grabMusic();
		
		//menuMusic = new SoundCard("menu" + File.separator + "intro-rb");
		menuMusic = new SoundCard("menu" + File.separator + "intro-rs");
		//menuMusic = new SoundCard("menu" + File.separator + "intro-pc");
		menuMusic.playMusic();	
		
		// players can only be set to 1 or 2
		players = mainMenu();
		
		// debug mode
		if (players == 5) {
			players = 1;
			name1 = "P1";
			name2 = "P2";
			battleShift = true;
			//defaultLevel = 50;
			partySize = 1;
			SoundCard.setActive(false);
		}
		// secret Trainer Red mode
		else if (players == 9) {
			trainerRed = true;
			players = 1;
			name1 = inputName(1);
			name2 = "Red";
			defaultLevel = 85;
			partySize = 3;
			file = "11battle-hs-red";
		}
		else {	
			name1 = inputName(1);
			name2 = (players == 1 ) ? "Red" : inputName(2);
			selectPartySize();	
		}
		
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
	
	/** PRINT LOGO & OAK METHODS **/
	private static void printLogo() {
		clearContent();	
		System.out.println(Style.YELLOW
				+ "                                  ,'\\\r\n"
				+ "    _.----.        ____         ,'  _\\   ___    ___     ____\r\n"
				+ "_,-'       `.     |    |  /`.   \\,-'    |   \\  /   |   |    \\  |`.\r\n"
				+ "\\      __    \\    '-.  | /   `.  ___    |    \\/    |   '-.   \\ |  |\r\n"
				+ " \\.    \\ \\   |  __  |  |/    ,','_  `.  |          | __  |    \\|  |\r\n"
				+ "   \\    \\/   /,' _`.|      ,' / / / /   |          ,' _`.|     |  |\r\n"
				+ "    \\     ,-'/  /   \\    ,'   | \\/ / ,`.|         /  /   \\  |     |\r\n"
				+ "     \\    \\ |   \\_/  |   `-.  \\    `'  /|  |    ||   \\_/  | |\\    |\r\n"
				+ "      \\    \\ \\      /       `-.`.___,-' |  |\\  /| \\      /  | |   |\r\n"
				+ "       \\    \\ `.__,'|  |`-._    `|      |__| \\/ |  `.__,'|  | |   |\r\n"
				+ "        \\_.-'       |__|    `-._ |              '-.|     '-.| |   |\r\n"
				+ "                                `'                            '-._|\n\n" + 
				Style.END);
	}
	private static void printOak() {
		clearContent();	
		System.out.println(Style.CYAN 
				+ "⠀⠀⠀⠀⠀⠀⣀⡀⠀⠀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
				+ "⠀⠀⠀⠀⠀⠘⠯⠙⠛⠛⠉⠛⠛⠛⠲⠤⠤⡀⠀⠀⠀⠀⠀⠀\r\n"
				+ "⠀⠀⠀⠀⢲⣖⠤⠀⣄⣀⠤⠀⠀⠀⠀⠀⠀⠓⡄⠀⠀⠀⠀⠀\r\n"
				+ "⠀⠀⠀⠀⠰⢮⣀⣯⠉⠉⠹⢦⡼⠉⠉⠹⢇⠲⡇⠀⠀⠀⠀⠀\r\n"
				+ "⠀⠀⠀⠀⠀⠀⠿⢷⣥⣄⣦⢠⡄⡀⣠⣤⣾⠾⡃⠀⠀⠀⠀⠀\r\n"
				+ "⠀⠀⠀⠀⠀⢸⠀⡆⢹⣍⣿⠈⠙⣿⣉⠏⢹⠀⡇⠀⠀⠀⠀⠀\r\n"
				+ "⠀⠀⠀⠀⠀⠀⠙⢿⡇⢀⣛⣃⣀⠀⡄⢀⡸⠋⠀⠀⠀⠀⠀⠀\r\n"
				+ "⠀⠀⠀⠀⠀⠀⠀⢀⣵⣶⣿⠉⠉⣷⣿⣮⡀⠀⠀⠀⠀⠀⠀⠀\r\n"
				+ "⠀⠀⠀⠀⣠⣦⣟⠋⠀⣤⣿⣿⣉⣉⡠⠇⠈⣦⢄⣀⠀⠀⠀⠀\r\n"
				+ "⠀⠀⠀⠀⣯⣥⡉⢓⢢⡞⠛⢩⣬⣭⣥⣦⣤⠿⠀⠀⢴⠀⠀⠀\r\n"
				+ "⠀⠀⢀⠟⢹⣿⠿⡿⠏⢉⣿⣿⣯⡗⣿⡏⠉⠶⡀⢀⠰⡇⠀⠀\r\n"
				+ "⠀⢠⡼⠤⠼⣿⠦⠃⠘⠛⠛⣻⣿⣿⣽⡇⠀⠶⠀⢸⠀⠓⡄⠀\r\n"
				+ "⠠⡜⠁⠀⠀⢹⣄⠀⠀⠐⣶⣿⣿⡿⣿⡇⠰⠀⠀⢸⠀⠀⡇⠀\r\n"
				+ "⠘⢇⡀⠀⠀⠈⣿⣿⣶⣶⠉⠉⠁⠀⣷⠸⠁⠀⠀⢸⠀⠀⢱⡆\r\n"
				+ "⠀⠀⠙⠛⡟⠛⠷⠟⢻⣧⣤⣀⣠⣤⡟⠀⠀⠀⠀⢸⡁⢠⡜⠃\r\n"
				+ "⠀⠀⠀⡀⢃⣀⠀⠀⢸⣿⣿⣉⣹⣿⣧⡀⠀⣀⠒⠚⣦⠏⠁⠀\r\n"
				+ "⠀⠀⠀⡿⠀⠀⣿⠀⢸⣿⣾⣿⢧⣻⣼⡇⠀⣿⠀⠀⣽⠀⠀⠀\r\n"
				+ "⠀⠀⠀⣟⠀⠀⣿⠀⢸⣯⢿⡟⢻⡷⣾⡇⠀⣿⠀⠀⢾⠀⠀⠀\r\n"
				+ "⠀⠀⠀⣿⠱⠤⠿⠀⣸⣿⠿⡇⢸⣷⣻⡇⠀⠿⠤⠌⣿⠀⠀⠀\r\n"
				+ "⠀⠀⠀⠀⢳⣦⣤⣼⣿⠁⣴⠃⠘⣷⣿⣧⣤⣤⣴⡞⠉⠀⠀⠀\r\n"
				+ "⠀⠀⠀⠀⢸⣿⣽⣏⡁⠀⣿⠀⠀⣿⡷⠿⣍⣉⣹⡇⠀⠀⠀⠀\r\n"
				+ "⠀⠀⠀⠀⣿⡿⠿⢿⡿⠿⣿⠇⠠⣟⣻⣿⡿⠿⢿⣿⠀⠀⠀⠀\r\n"
				+ "⠀⠀⠀⠀⣏⣡⣤⣜⣣⣤⠛⠀⠀⠛⣿⣋⣡⣤⠘⢻⠀⠀⠀⠀\r\n"
				+ "⠀⠀⠀⠀⠈⠉⠉⠉⠁⠀⠀⠀⠀⠀⠀⠉⠉⠉⠉⠁⠀⠀⠀⠀\n" 
				+ Style.END);
	}
	/** END PRINT LOGO & OAK METHODS **/
	
	/** GRAB MUSIC METHOD 
	 * Store all music files from lib\sounds\music into two lists
	 **/
	private static void grabMusic() {
		
		// get all songs from music folder
		String musicPath = new File("").getAbsolutePath() + File.separator + "lib" + 
				File.separator + "sounds" + File.separator + "music";
		File musicDirectory = new File(musicPath);
		
		// store all music files into array
		File musicFiles[] = musicDirectory.listFiles();
		
		// for each song in directory		
		for (int i = 0; i < musicFiles.length; i++) {			
			
			// add song name to list
			musicDict.put(i, musicFiles[i].getName());
			
			// format music
			String song = musicFiles[i].getName()
					.replace("battle-", "")
					.replace(".wav", "")
					.replace("-", ": ")
					.replace("_", ", ")
					.replace("&", " ")
					.toUpperCase();
			
			// add closing bracket and space after song index #
			StringBuilder formattedSong = new StringBuilder(song);
			formattedSong.insert(2, ']'); formattedSong.insert(3, ' ');	
			
			// add to array list and reverse sort
			musicList.add(formattedSong.toString());
			Collections.sort(musicList, Collections.reverseOrder());
		}		
	}
	/** END GRAB MUSIC METHOD **/
	
	/** SELECT PLAYERS METHOD
	  * Prompt player to select a setting from the main menu
	  * @return 1 for 1 Player, 2 for 2 Player, 5 for Debug mode
	  **/
	private static int mainMenu() {
		
		displayMain();
		
		// loop until QUIT is selected
		while (true) {
			
			try { 
				int choice = input.nextInt(); 				
				SoundCard.play(select);
				
				switch (choice) {
					case 1: return choice;
					case 2: return choice;
					case 3: 					
						settingsMenu();	
						displayMain();
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
					case 9:
						clearContent();
						return 9;
					default: 
						Sleeper.print("Input must be a menu option!"); 
						System.out.print(">");
				}
			}
			catch (Exception e) {
				SoundCard.play(select);
				Sleeper.print("Input must be a menu option!");
				System.out.print(">");
				input.next();
			}
		}
	}
	/** END SELECT PLAYERS METHOD **/
	
	/** DISPLAY MAIN MENU METHOD **/
	private static void displayMain() {
		printLogo();		
		System.out.println("--MAIN MENU--\n\n"
				+ "[1] ONE PLAYER\n"
				+ "[2] TWO PLAYER\n"
				+ "[3] SETTINGS\n"
				+ "[4] POWER OFF");
		System.out.print(">");
	}
	/** END DISPLAY MAIN MENU METHOD **/
	
	/** SETTINGS METHOD
	  * Settings main menu 
	  **/
	private static void settingsMenu() {
		
		displaySettings();
		
		// loop until BACK is selected
		while (true) {
			
			try { 
				int choice = input.nextInt(); 
				SoundCard.play(select);
				
				switch (choice) {
					case 1: levelSetting(); displaySettings(); break;						
					case 2: partySetting(); displaySettings(); break;
					case 3: musicSetting(); displaySettings(); break;					
					case 4: textSetting(); displaySettings(); break;
					case 5: soundSetting(); displaySettings(); break;
					case 6: battleSetting(); displaySettings(); break;
					case 0: return;
					default:
						Sleeper.print("Input must be a menu option!"); 
						System.out.print(">");
						break;
				}
			}
			catch (Exception e) {
				SoundCard.play(select);
				Sleeper.print("Input must be a menu option!");
				System.out.print(">");
				input.next();
			}
		}	
	}
	/** END SETTINGS METHOD **/
	
	/** DISPLAY SETTINGS MENU METHOD **/
	private static void displaySettings() {
		printLogo();
		System.out.println("--SETTINGS--\n\n"
				+ "[1] DEFAULT LEVELS\n"
				+ "[2] CPU SELECT\n"
				+ "[3] BATTLE MUSIC\n"
				+ "[4] TEXT SPEED\n"
				+ "[5] SOUND EFFECTS\n" 
				+ "[6] BATTLE STYLE\n\n"
				+ "[0] <- BACK");
		System.out.print(">");
	}
	/** END DISPLAY SETTINGS MENU METHOD **/
	
	/** LEVEL SETTING METHOD
	  * Prompt player to select a default level for battle 
	  **/
	private static void levelSetting() {
		
		printLogo(); 					
		System.out.println("--DEFAULT LEVELS--\n\n"
				+ "[1] 10\n"
				+ "[2] 25\n"
				+ "[3] 50\n"
				+ "[4] 100\n"
				+ "[5] CUSTOM\n"
				+ "[6] DISABLED\n\n"
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
						Sleeper.print("Please enter a default level (1-100):");
						System.out.print(">");
						
						while (true) {
							
							try { 
								choice = input.nextInt(); 
								SoundCard.play(select);
								
								if (1 <= choice && choice <= 100) {
									defaultLevel = choice;
									Sleeper.print("DEFAULT LEVEL SET TO " + defaultLevel, 1200);
									return;	
								}
								else {
									Sleeper.print("Input must be a number between 1 and 100!");
									System.out.print(">");
								}								
							}						
							catch (Exception e) {
								SoundCard.play(select);
								Sleeper.print("Input must be a number between 1 and 100!");
								System.out.print(">");
								input.next();
							}
						}
					case 6:
						defaultLevel = -1;
						Sleeper.print("DEFAULT LEVEL DISABLED", 1200);
						return;
					case 0:
						return;
					default:
						Sleeper.print("Input must be a menu option!"); 
						System.out.print(">");
						break;
				}
			}
			catch (Exception e) {
				SoundCard.play(select);
				Sleeper.print("Input must be a menu option!");
				System.out.print(">");
				input.next();
			}
		}	
	}
	/** END LEVEL SETTING METHOD **/
	
	/** PARTY SETTING METHOD 
	  *	Prompt player to select which method the cpu will select party
	  * 0 for manual, -1 for random, 1 for difficult
	  **/
	private static void partySetting() {
		
		printLogo();
		System.out.println("--CPU PARTY SELECTION--\n\n"
				+ "[1] MANUAL\n"
				+ "[2] RANDOM\n"
				+ "[3] DIFFICULT\n\n"
				+ "[0] <- BACK");
		System.out.print(">");
		
		while (true) {
			
			try { 
				int choice = input.nextInt(); 
				SoundCard.play(select);
			
				switch (choice) {
					case 1:
						cpuSelect = 0;
						Sleeper.print("CPU PARTY SELECTION SET TO MANUAL", 1200); 
						return;
					case 2:
						cpuSelect = -1;
						Sleeper.print("CPU PARTY SELECTION SET TO RANDOM", 1200); 
						return;
					case 3:
						cpuSelect = 1;
						Sleeper.print("CPU PARTY SELECTION SET TO DIFFICULT", 1200); 
						return;
					case 0:
						return;
					default:
						Sleeper.print("Input must be a menu option!"); 
						System.out.print(">");
				}
			}
			catch (Exception e) {
				SoundCard.play(select);
				Sleeper.print("Input must be a menu option!!");
				System.out.print(">");
				input.next();
			}
		}	
	}
	/** END PARTY SETTING METHOD **/
	
	/** SELECT MUSIC METHOD
	  * Prompt player to select a new song to play during battle 
	  **/
	private static void musicSetting() {
		
		printLogo();
		
		System.out.println("--BATTLE MUSIC--\n");
		
		// print music in descending order
		for (int i = 0; i < musicList.size(); i++) 
			System.out.println("[" + musicList.get(i));	
		
		System.out.println(
				"[01] NONE\n\n" + 
				"[0] <- BACK");		
		System.out.print(">");
		
		while (true) {
			
			try { 				
				int choice = input.nextInt(); 
				SoundCard.play(select);
				
				if (1 < choice && choice <= musicDict.size() + 1) {					
					file = musicDict.get(choice - 2).replace(".wav", "");
					Sleeper.print("NEW MUSIC SELECTED", 1200);					
					return;
				}					
				else if (choice == 1) {
					file = null;
					Sleeper.print("MUSIC TURNED OFF", 1200);
					return;
				}
				else if (choice == 0)
					return;
				else {
					Sleeper.print("Input must be a menu option!");
					System.out.print(">");
				}
			}
			catch (Exception e) {
				SoundCard.play(select);
				Sleeper.print("Input must be a menu option!");
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
				
		printLogo();
		System.out.println("--TEXT SPEED--\n\n"
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
						Sleeper.print("Input must be a menu option!");
						System.out.print(">");
						break;
				}
			}
			catch (Exception e) {
				SoundCard.play(select);
				Sleeper.print("Input must be a menu option!");
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
				
		printLogo();
		System.out.println("--SOUND EFFECTS--\n\n"
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
						Sleeper.print("Input must be a menu option!");
						System.out.print(">");
				}
			}
			catch (Exception e) {
				SoundCard.play(select);
				Sleeper.print("Input must be a menu option!");
				System.out.print(">");
				input.next();
			}
		}	
	}
	/** END SOUND SETTINGS **/
	
	/** BATTLE SETTING METHOD
	  * Prompt player to select shift or set for battle style
	  **/
	private static void battleSetting() {
				
		printLogo();
		System.out.println("--BATTLE STYLE--\n\n"
				+ "[1] SHIFT\n"
				+ "[2] SET\n\n"
				+ "[0] <- BACK");
		System.out.print(">");
		
		while (true) {
			
			try { 
				int choice = input.nextInt(); 
				SoundCard.play(select);
				
				switch (choice) {
					case 1:
						battleShift = true;
						Sleeper.print("BATTLE STYLE SET TO SHIFT", 1200); 
						return;
					case 2:
						battleShift = false;
						Sleeper.print("BATTLE STYLE SET TO SET", 1200); 
						return;
					case 0:
						return;
					default:
						Sleeper.print("Input must be a menu option!");
						System.out.print(">");
						break;
				}
			}
			catch (Exception e) {
				SoundCard.play(select);
				Sleeper.print("Input must be a menu option!");
				System.out.print(">");
				input.next();
			}
		}	
	}
	/** END BATTLE STYLE METHOD **/
		
	/** INPUT NAME METHOD
	  * Prompt player to input their name 
	  * @param Integer player number
	  * @return String player name
	  **/
	private static String inputName(int player) {
				
		printOak();		
		Sleeper.print("(PR. OAK) What's your name, trainer " + player + "?");
		System.out.print(">");
		
		// loop until QUIT is selected
		while (true) {
			
			try { 
				String name = input.next(); 
				SoundCard.play(select);
				
				String greeting;
				
				// hidden greetings based on player name
				if (name.equals("Ash"))
					greeting = "(PR. OAK) Ash! Good to see you again :)";
				else if (name.equals("Oak"))
					greeting = "(PR. OAK) Would you happen to also be a professor?";
				else if (name.equals("Zachary")) 
					greeting = "(PR. OAK) Woah! I am honored to be in the presense of my creator!";
				else if (name.equals("Jenna"))
					greeting = "(PR. OAK) What a lovely sounding name!";
				else 
					greeting = "(PR. OAK) Welcome to the world of POKEMON, " + name + "!";									
				
				printOak();
				Sleeper.print(greeting, 1200);
				
				return name;
			}
			catch (Exception e) {
				SoundCard.play(select);
				Sleeper.print("Something went wrong! Please try again!");
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
					
		printOak();
		Sleeper.print("(PR. OAK) What will be the size of your POKEMON party? (1-6)");
		System.out.print(">");
		
		// loop until Quit is selected
		while (true) {
			
			try { 
				int choice = input.nextInt(); 
				SoundCard.play(select);
				
				if (1 <= choice && choice <= 6) {
					partySize = choice;
					return;
				}
				else {
					Sleeper.print("Input must be between 1-6!"); 
					System.out.print(">");
				}
			}
			catch (Exception e) {
				SoundCard.play(select);
				Sleeper.print("Input must be a valid number!");
				System.out.print(">");
				input.next();
			}
		}
	}
	/** END SELECT PARTY SIZE METHOD **/
	
	/** SELECT PARTY METHOD
	  * Prompt player to select pokemon from available list
	  **/
	private static void selectParty() {

		if (trainerRed) {
			Pokemon p1 = Pokemon.getPokemon(2);
			Pokemon p2 = Pokemon.getPokemon(5);
			Pokemon p3 = Pokemon.getPokemon(8);
			Pokemon p4 = Pokemon.getPokemon(9);
			Pokemon p5 = Pokemon.getPokemon(25);
			Pokemon p6 = Pokemon.getPokemon(26);
			party2.addAll(Arrays.asList(p1, p2, p3, p4, p5, p6));
		}
		
		int turn = 1, choice = 0;	
		
		while (turn <= (partySize * 2)) {
			
			clearContent();
			
			int counter = displayParty();
			
			if (trainerRed) {
				System.out.println("\n\n(PR. OAK) " + name1 + ", Please choose your POKEMON:");
				System.out.print(">");
			}			
			// if cpu is not selecting
			else if (cpuSelect == 0 || players == 2) {
				System.out.println("\n\n(PR. OAK) " + ((turn % 2 != 0) ? name1 : name2) + 
						", Please choose your POKEMON:");
				System.out.print(">");
			}
			// if player 1 is selecting
			else if (cpuSelect != 0 && turn % 2 != 0) {
				System.out.println("\n\n(PR. OAK) " + name1 + ", Please choose your POKEMON:");
				System.out.print(">");
			}
			
			// if cpu is selecting
			if (cpuSelect != 0 && turn % 2 == 0 && players == 1) {
				System.out.println("\n\n(PR. OAK) " + name2 + ", Please choose your POKEMON:");
				System.out.print("");
				
				Sleeper.pause(1700);
				choice = cpuSelectParty(counter);
			}	
			// cpu did not select
			else {	
				while (true) {		
		
					try { 
						choice = input.nextInt(); 
						
						if (0 < choice && choice <= counter) {
							
							if (!isValidChoice(choice)) {				
								Sleeper.print("This Pokemon has already been chosen!");
								System.out.print(">");
							}
							else
								break;
						}
						else {
							Sleeper.print("Input must be a valid selection!");
							System.out.print(">");
						}
					}
					catch (Exception e) {
						SoundCard.play(select);
						Sleeper.print("Input must be a valid selection!");
						System.out.print(">");
						input.next();
					}
				}	
			}
			
			assignParty(choice, turn);			
			turn++;
		}
	}
	/** END SELECT PARTY METHOD **/
	
	/** DISPLAY POKEMON METHOD
	  * Display all unchosen pokemon for player to select for battle 
	  * @return counter
	  **/
	private static int displayParty() {
		
		System.out.println("--- AVAILABLE POKEMON FOR BATTLE ---\n");
		
		int counter = 0;		
		for (Pokemon p : Pokemon.getPokedex()) {	
			
			p.setLevel(defaultLevel);
			
			// skip Pokemon who were already chosen
			if (party1.contains(Pokemon.getPokemon(counter)) || 
					party2.contains(Pokemon.getPokemon(counter))) {
				counter++;
				continue;
			}
			
			// if pokemon has multiple types
			System.out.printf("[%02d] " + p.getName() + "\tLv%02d [" + 
					((p.getTypes() == null) ? p.getType().printType() : p.printTypes()) + 
					"]\n", ++counter, p.getLevel());	
		}
		
		printParty();
		
		return counter;
	}
	/** END DISPLAY POKEMON METHOD **/
	
	/** IS VALID CHOICE METHOD 
	 * Checks if selected Pokemon already belongs in a party
	 * @param Integer choice
	 * @return True if not found in either party, False if found
	 **/
	private static boolean isValidChoice(int choice) {
		
		// chosen Pokemon must not have already been selected by either trainer
		if (party1.contains(Pokemon.getPokemon(choice - 1)) || 
				party2.contains(Pokemon.getPokemon(choice - 1))) 			
			return false;		
		else
			return true;
	}
	/** END IS VALID CHOICE METHOD **/
	
	/** CPU SELECT PARTY METHOD
	  * Randomly select a pokemon from the available list
	  * @param Integer counter
	  * @return Integer chosen pokemon index
	  **/
	private static int cpuSelectParty(int counter) {
		
		// cpu selects party at random
		if (cpuSelect == -1) {
			int choice = 1 + (int)(Math.random() * counter);
			
			// loop until random number is valid
			while (true) {
				
				if (!isValidChoice(choice))					
					choice = 1 + (int)(Math.random() * counter);				
				else
					return choice;
			}
		}
		// cpu selects party strategically
		else {			
			
			// make copy of pokedex
			List<Pokemon> bestList = new ArrayList<>(Pokemon.getPokedex());

			
			//sort copy list first by level, then hp, then attack, and send highest values to top (0)
			Collections.sort(bestList, Comparator.comparing(Pokemon::getLevel)
		            .thenComparing(Pokemon::getHP)
		            .thenComparing(Pokemon::getAttack).reversed());
			
			int i = 0;
			while (true) {
												
				// get best pokemon
				Pokemon choice = bestList.get(i);
				
				// if pokemon already chosen, go to next and check again
				if (party1.contains(choice) || party2.contains(choice))
					i++;
				else {
					// return 1 + index of selected pokemon in Pokedex
					return Pokemon.getPokedex().indexOf(choice) + 1;
				}
			}
		}
		
	}
	/** END CPU SELECT PARTY METHOD **/
	
	/** ASSIGN PARTY METHOD
	  * Find pokemon in Pokedex at index and assign to party1 or party2
	  * @param Integer choice, Integer counter
	  **/
	private static void assignParty(int choice, int turn) {
		
		// assign fighter to party found at given index
		Pokemon selectedPokemon = Pokemon.getPokemon(choice - 1);
		
		// play pokemon cry
		SoundCard.play("pokedex" + File.separator + selectedPokemon.getName());
		
		if (trainerRed)
			party1.add(selectedPokemon);
		else if (turn % 2 == 0) 
			party2.add(selectedPokemon);
		else 
			party1.add(selectedPokemon);
	}
	/** END ASSIGN PARTY METHOD **/
	
	/** PRINT PARTY METHOD**/
	private static void printParty() {
		
		int n = 0;
		System.out.print("\n" + name1 + "'s PARTY:\t");
		for (Pokemon p : party1) {
			if (n == 3) { 
				System.out.println(); 
				System.out.print("\t\t"); 
			}
			System.out.print(p.getName() + "(" + 
				((p.getTypes() == null) ? p.getType().toString().charAt(0) + "" + 
					p.getType().toString().toLowerCase().charAt(1) : 
					p.printTypesShort()) + ") ");
			n++;
		}
		
		n = 0;
		System.out.print("\n" + name2 + "'s PARTY:\t");	
		for (Pokemon p : party2) {
			if (n == 3) { 
				System.out.println(); 
				System.out.print("\t\t"); 
			}
			System.out.print(p.getName() + "(" + 
				((p.getTypes() == null) ? p.getType().toString().charAt(0) + "" +
					p.getType().toString().toLowerCase().charAt(1) : 
					p.printTypesShort()) + ") ");
			n++;
		}
	}
	/** END PRINT PARTY METHOD **/
	
	/** SELECT STARTER METHOD
	  * Prompt player to select starting fighter from party 
	  * @param Integer player number
	  * @return ArrayList swapped party
	  **/
	private static List<Pokemon> selectStarter(int player) {

		// assign temp party to player 1 or player 2 party
		List<Pokemon> party = (player == 1) ? party1 : party2;
				
		printOak();
		printParty();		
		System.out.println("\n\n(PR. OAK) " + ((player == 1) ? name1 : name2) + 
				", Please select your starting fighter:\n");	
		
		int counter = 1;
		for (Pokemon pokemon : party) { 
			System.out.print("[" + counter + "] " + pokemon.getName() + "(" + 
					((pokemon.getTypes() == null) ? pokemon.getType().toString().charAt(0) + "" + 
					pokemon.getType().toString().toLowerCase().charAt(1) : 
					pokemon.printTypesShort()) + ")\n");
			counter++;
		}	
		System.out.print(">");
		
		while (true) {				
				
			try { 				
				int choice = input.nextInt(); 
				SoundCard.play(select);
				
				// if choice is within party size
				if (1 <= choice && choice < counter) {
							
					// swap choice with pokemon at first slot
					Collections.swap(party, 0, choice - 1);
					return party;
				}
				else {
					Sleeper.print("Input must be a valid selection!");
					System.out.print(">");
				}
			}
			catch (Exception e) {
				SoundCard.play(select);
				Sleeper.print("Input must be a valid selection!");
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
		
		// find best pokemon in cpu party based on level, then HP, then attack
		Pokemon bestFighter = Collections.max(party2, Comparator.comparingInt(Pokemon::getLevel)
				.thenComparing(Pokemon::getHP).thenComparing(Pokemon::getAttack));

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
		
		Battle game = new Battle(name1, name2, players, party1, party2, battleShift);
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