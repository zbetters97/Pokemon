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
	
	/** LOAD METHOD **/
	public static void load() {

		String name1 = null, name2 = null;
		
		int players = selectPlayers();
		
		clearContent();
		
		//name1 = inputName(1);		
		//name2 = (players == 1 ) ? "Red" : inputName(2);
		
		name1 = "steel";
		name2 = "red";
		int partySize = selectPartySize();
		
		selectMusic();
		
		ArrayList<Pokedex> pokemonParty1 = new ArrayList<>();
		ArrayList<Pokedex> pokemonParty2 = new ArrayList<>();
		
		pokemonParty1 = selectParty(1, partySize, name1, name2, pokemonParty1, pokemonParty2);
		pokemonParty2 = selectParty(2, partySize, name2, name1, pokemonParty2, pokemonParty1);
		
		startGame(players, name1, name2, pokemonParty1, pokemonParty2);
	}
	/** END LOAD METHOD **/
	
	/** SELECT PLAYERS METHOD **/
	private static int selectPlayers() {
						
		clearContent();
		
		System.out.println("PLEASE SELECT MODE:\n"
				+ "[1] SINGLE PLAYER\n"
				+ "[2] MULTI PLAYER\n"
				+ "[3] QUIT");
		
		// loop until QUIT is selected
		while (true) {
			
			try { 
				int choice = input.nextInt(); 
				
				if (choice == 1 || choice == 2)
					return choice;
				else if (choice == 3) { 
					System.out.println("Shutting down..."); 
					System.exit(0); 
				}	
				else 
					System.out.println("ERROR: Input must be a valid selection!"); 
			}
			catch (Exception e) {
				System.out.println("ERROR: Input must be a number!");
				input.next();
			}
		}
	}
	/** END SELECT PLAYERS METHOD **/
	
	/** INPUT NAME METHOD **/
	/*(private static String inputName(int player) {
				
		System.out.println("WHAT IS YOUR NAME, TRAINER " + player + "?");
		
		// loop until QUIT is selected
		while (true) {
			
			try { 
				String name = input.next(); 
				
				clearContent();
				System.out.println("WELCOME TO THE WORLD OF POKEMON, " + name + "!");			
				
				Sleeper.pause(1500);	
				clearContent();
				
				return name;
			}
			catch (Exception e) {
				System.out.println("ERROR: Something went wrong!");
				input.next();
			}
		}
	}*/
	/** END INPUT NAME METHOD **/
	
	/** SELECT PARTY SIZE METHOD **/
	private static int selectPartySize() {
		
		clearContent();
				
		System.out.println("PLEASE SELECT PARTY SIZE (1-6):");
		
		// loop until Quit is selected
		while (true) {
			
			try { 
				int choice = input.nextInt(); 
				
				if (1 <= choice && choice <= 6)
					return choice;
				else if (choice == 7) { 
					System.out.println("Shutting down..."); 
					System.exit(0); 
				}	
				else 
					System.out.println("ERROR: Input must be a valid selection!"); 
			}
			catch (Exception e) {
				System.out.println("ERROR: Input must be a number!");
				input.next();
			}
		}
	}
	/** END SELECT PARTY SIZE METHOD **/
	
	/** SELECT MUSIC METHOD **/
	private static void selectMusic() {
		
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
		
		System.out.println("PLEASE SELET MUSIC:\n\n[00] QUIT\n[01] NONE");
		
		for (int i = 0; i < musicList.size(); i++) 
			System.out.println("[" + musicList.get(i));
		
		while (true) {
			
			try { 				
				int choice = input.nextInt() - 1; 
				
				if (0 < choice && choice <= musicDict.size()) {
					String file = musicDict.get(choice - 1).replace(".wav", "");
					
					bgmusic = new SoundCard("\\music\\" + file);
					bgmusic.playMusic();
					return;
				}					
				else if (choice == -1) {
					clearContent();
					System.out.println("Shutting down..."); 
					System.exit(0);
				}
				else if (choice == 0)
					return;
				else
					System.out.println("ERROR: Input must be a valid selection!");
			}
			catch (Exception e) {
				System.out.println("ERROR: Input must be a number!");
				System.out.println(e);
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
				
				if (p.getType() == null) {
					System.out.printf("[" + ++counter + "] " + p.getName() + 
						"\tLVL: %02d | TYPE: " + p.getTypes() + "\n", p.getLevel());	
				}
				else {
					System.out.printf("[" + ++counter + "] " + p.getName() + 
						"\tLVL: %02d | TYPE: " + p.getType() + "\n", p.getLevel());	
				}
			}
	
			System.out.print("\n" + name1 + "'s PARTY: ");
			for (Pokedex p : party1)
				System.out.print(p.getName() + " ");
			
			System.out.print("\n" + name2 + "'s PARTY: ");			
			for (Pokedex p : party2)
				System.out.print(p.getName() + " ");
			
			System.out.println("\n\n" + name1 + ", PLEASE SELECT YOUR POKEMON PARTY:");
			
			while (true) {				
				try { 
					choice = input.nextInt(); 
					
					// choice must be a number from 0 to last element in list
					if (0 < choice && choice <= counter) {
						
						// chosen Pokemon must not have already been selected by either trainer
						if (party1.contains(Pokedex.getPokemon(choice - 1)) || 
								party2.contains(Pokedex.getPokemon(choice - 1))) 						
							System.out.println("This Pokemon has already been chosen!");
						else
							break;
					}
					else 
						System.out.println("ERROR: This is not a valid selection!");
				}
				catch (Exception e) {
					System.out.println("ERROR: Input must be a number!");
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