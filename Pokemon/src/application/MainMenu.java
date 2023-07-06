package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Scanner;

import pokemon.Pokedex;

/*** MAIN MENU CLASS ***/
public class MainMenu {
	
	// scanner to receive user input
	static Scanner input = new Scanner(System.in);
	static SoundCard bgmusic;
	static Sleeper sleeper;
	
	/** LOAD METHOD **/
	public static void load() {

		String name1 = null, name2 = null;
		
		int players = selectPlayers();
		
		clearContent();
		
		name1 = inputName(1);		
		name2 = (players == 1 ) ? "Red" : inputName(2);
		
		int partySize = selectPartySize();
		
		String file = selectMusic();
		
		ArrayList<Pokedex> pokemonParty1 = new ArrayList<>();
		ArrayList<Pokedex> pokemonParty2 = new ArrayList<>();
		
		pokemonParty1 = selectParty(1, partySize, name1, name2, pokemonParty1, pokemonParty2);
		pokemonParty2 = selectParty(2, partySize, name2, name1, pokemonParty2, pokemonParty1);

		if (file != "") {
			bgmusic = new SoundCard("\\music\\" + file);
			bgmusic.playMusic();
		}
		
		startGame(players, name1, name2, pokemonParty1, pokemonParty2);
	}
	/** END LOAD METHOD **/
	
	
	
	/** SELECT PLAYERS METHOD **/
	private static int selectPlayers() {
						
		clearContent();			
		System.out.println("PLEASE SELECT MODE:\n"
				+ "[1] ONE PLAYER\n"
				+ "[2] TWO PLAYER\n"
				+ "[3] SETTINGS\n"
				+ "[4] QUIT");
		System.out.print(">");
		
		// loop until QUIT is selected
		while (true) {
			
			try { 
				int choice = input.nextInt(); 
				
				if (choice == 1 || choice == 2)
					return choice;
				else if (choice == 3) {
					settingsMenu();
					
					clearContent();					
					System.out.println("PLEASE SELECT MODE:\n"
							+ "[1] ONE PLAYER\n"
							+ "[2] TWO PLAYER\n"
							+ "[3] SETTINGS\n"
							+ "[4] QUIT");
					System.out.print(">");
				}
				else if (choice == 4) { 
					Sleeper.print("Shutting down..."); 
					System.exit(0); 
				}	
				else {
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
	
	/** SETTINGS METHOD **/
	private static void settingsMenu() {
		
		clearContent();				
		System.out.println("PLEASE SELECT AN OPTION:\n"
				+ "[1] TEXT SPEED\n"
				+ "[2] SOUNDS\n"
				+ "[3] BACK");
		System.out.print(">");
		
		// loop until BACK is selected
		while (true) {
			
			try { 
				int choice = input.nextInt(); 
				
				if (choice == 1) {
					textSpeedMenu();
					return;
				}
				else if (choice == 2) {
					soundMenu();
					return;
				}
				else if (choice == 3) { 
					clearContent();
					return;
				}	
				else {
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
	/** END SETTINGS METHOD **/
	
	private static void textSpeedMenu() {
		
		clearContent();				
		System.out.println("TEXT SPEED:\n"
				+ "[1] SLOW\n"
				+ "[2] MEDIUM\n"
				+ "[3] FAST");
		System.out.print(">");
		
		while (true) {
			
			try { 
				int choice = input.nextInt(); 
				
				if (choice == 1) {
					Sleeper.setSpeed(50);
					Sleeper.print("TEXT SPEED SET TO SLOW"); 
					Sleeper.pause(1200);
					return;
				}
				else if (choice == 2) {	
					Sleeper.setSpeed(35);
					Sleeper.print("TEXT SPEED SET TO MEDIUM"); 
					Sleeper.pause(1200);
					return;
				}
				else if (choice == 3) {
					Sleeper.setSpeed(20);
					Sleeper.print("TEXT SPEED SET TO FAST"); 
					Sleeper.pause(1200);					
					return;
				}	
				else {
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
	
	private static void soundMenu() {
		
		clearContent();				
		System.out.println("BATTLE SOUNDS:\n"
				+ "[1] ON\n"
				+ "[2] OFF");
		System.out.print(">");
		
		// loop until BACK is selected
		while (true) {
			
			try { 
				int choice = input.nextInt(); 
				
				if (choice == 1) {
					SoundCard.setActive(true);
					Sleeper.print("BATTLE SOUNDS ARE NOW ON"); 
					Sleeper.pause(1200);	
					return;
				}
				else if (choice == 2) {	
					SoundCard.setActive(false);
					Sleeper.print("BATTLE SOUNDS ARE NOW OFF"); 
					Sleeper.pause(1200);	
					return;
				}
				else {
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
	
	/** INPUT NAME METHOD **/
	private static String inputName(int player) {
				
		Sleeper.print("WHAT IS YOUR NAME, TRAINER " + player + "?");
		System.out.print(">");
		
		// loop until QUIT is selected
		while (true) {
			
			try { 
				String name = input.next(); 
				
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
	
	/** SELECT PARTY SIZE METHOD **/
	private static int selectPartySize() {
		
		clearContent();
				
		Sleeper.print("PLEASE SELECT PARTY SIZE (1-6):");
		System.out.print(">");
		
		// loop until Quit is selected
		while (true) {
			
			try { 
				int choice = input.nextInt(); 
				
				if (1 <= choice && choice <= 6)
					return choice;
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
	
	/** SELECT MUSIC METHOD **/
	private static String selectMusic() {
		
		clearContent();	
		
		// array list to hold music String
		ArrayList<String> musicList = new ArrayList<>();
		
		// hashmap to hold music file and corresponding index
		LinkedHashMap<Integer, String> musicDict = new LinkedHashMap<>();
		
		// get all songs from music folder
		String path = new File("").getAbsolutePath() + "//lib//sounds//music";
		File directoryPath = new File(path);
		
		// store all music files into array
		File filesList[] = directoryPath.listFiles();
		
		// for each song in directory		
		for (int i = 0; i < filesList.length; i++) {			
			
			// add song names to list
			String music = filesList[i].getName();
			musicDict.put(i, music);
			
			// format music
			String song = filesList[i].getName()
					.replace("battle-", "")
					.replace(".wav", "")
					.replace("-", ": ")
					.replace("_", ", ")
					.toUpperCase();
			
			// add space after song index
			StringBuilder nSong = new StringBuilder(song);
			nSong.insert(2, ']'); nSong.insert(3, ' ');	
			
			// add to array list
			musicList.add(nSong.toString());
			Collections.sort(musicList);
		}		
		
		Sleeper.print("PLEASE SELET MUSIC:", 700);
		System.out.println("\n[00] QUIT\n[01] NONE");
		
		for (int i = 0; i < musicList.size(); i++) 
			System.out.println("[" + musicList.get(i));		
		
		System.out.print(">");
		
		while (true) {
			
			try { 				
				int choice = input.nextInt() - 1; 
				
				if (0 < choice && choice <= musicDict.size()) {
					String file = musicDict.get(choice - 1).replace(".wav", "");
					return file;
				}					
				else if (choice == -1) {
					clearContent();
					Sleeper.print("Shutting down..."); 
					System.exit(0);
				}
				else if (choice == 0)
					return "";
				else {
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
	/** END SELECT MUSIC METHOD **/
	
	/** SELECT PARTY METHOD **/
	private static ArrayList<Pokedex> selectParty(int player, int partySize, String name1, String name2, 
			ArrayList<Pokedex> party1, 	ArrayList<Pokedex> party2) {
		
		clearContent();	
		
		int c = 0, choice = 0;
		
		while (c < partySize) {			
		
			int counter = 0;
			
			for (Pokedex p : Pokedex.getPokedex()) {
				
				// don't display Pokemon who are already chosen					
				if (party1.contains(Pokedex.getPokemon(counter)) || 
						party2.contains(Pokedex.getPokemon(counter))) {
					counter++;
					continue;
				}
				
				// if pokemon has multiple types
				if (p.getType() == null) {
					System.out.printf("[%02d] " + p.getName() + "\tLVL: %02d | TYPE: " + 
							p.printTypes() + "\n", ++counter, p.getLevel());	
				}
				else {
					System.out.printf("[%02d] " + p.getName() + "\tLVL: %02d | TYPE: " + 
							p.getType() + "\n", ++counter, p.getLevel());	
				}
			}
	
			System.out.print("\n" + name1 + "'s PARTY: ");
			for (Pokedex p : party1)
				System.out.print(p.getName() + " ");
			
			System.out.print("\n" + name2 + "'s PARTY: ");			
			for (Pokedex p : party2)
				System.out.print(p.getName() + " ");
			
			System.out.println("\n\n" + name1 + ", PLEASE SELECT YOUR POKEMON PARTY:");
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
			party1.add(selectedPokemon);
			
			// play pokemon cry
			SoundCard.play("//pokedex//" + selectedPokemon.getName());
			
			clearContent();
			c++;
		}
		
		return party1;
	}
	/** END SELECT PARTY METHOD **/
	
	/** START GAME METHOD **/
	private static void startGame(int numPlayers, String name1, String name2,
			ArrayList<Pokedex> pokemonParty1, ArrayList<Pokedex> pokemonParty2) {
		
		Battle game = new Battle(name1, name2, numPlayers, pokemonParty1, pokemonParty2);
		game.start();
		
		// when battle is over, stopMusic will be called
		if (!(bgmusic == null)) {
			bgmusic.stopMusic();	
		}
		
		// waits until user input to finish
		try { System.in.read(); } 
		catch (IOException e) { e.printStackTrace(); }
	}
	/** END START GAME METHOD **/
		
	/** CLEAR SCREEN METHOD **/	
	private static void clearContent() {		
		System.out.println(new String(new char[70]).replace("\0", "\r\n"));
	}
	/** END CLEAR SCREEN METHOD **/
}
/*** END MAIN MENU CLASS ***/