package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Scanner;

/*** MAIN MENU CLASS ***/
public class MainMenu {
	
	// scanner to receive user input
	static Scanner input = new Scanner(System.in);
	static SoundCard bgmusic;
	
	/** LOAD METHOD **/
	public static void load() {

		String name1 = "null", name2 = "null";
		
		clearContent();		
		int players = selectPlayers();
		
		clearContent();
		if (players == 1) {
			name1 = inputName(1);
			name2 = "Red";
		}
		else if (players == 2) {
			name1 = inputName(1);
			name2 = inputName(2);
		}		
		
		clearContent();
		int partySize = selectPartySize();
		
		clearContent();		
		selectMusic();
		
		startGame(name1, name2, players, partySize);
	}
	/** END LOAD METHOD **/
	
	/** SELECT PLAYERS METHOD **/
	private static int selectPlayers() {
						
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
	private static String inputName(int player) {
				
		System.out.println("WHAT IS YOUR NAME, TRAINER " + player + "?");
		
		// loop until QUIT is selected
		while (true) {
			
			try { 
				String name = input.next(); 
				
				clearContent();
				System.out.println("WELCOME TO THE WORLD OF POKEMON, " + name + "!");			
				
				Sleeper.pause(2000);	
				clearContent();
				
				return name;
			}
			catch (Exception e) {
				System.out.println("ERROR: Something went wrong!");
				input.next();
			}
		}
	}
	/** END INPUT NAME METHOD **/
	
	/** SELECT PARTY SIZE METHOD **/
	private static int selectPartySize() {
				
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
		
		// arraylist to hold music String
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
			
			// add to arraylist
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
	
	/** START GAME METHOD **/
	private static void startGame(String name1, String name2, int numPlayers, int partySize) {
		
		Battle game = new Battle(name1, name2, numPlayers, partySize);
		game.start();
		
		// when battle is over, stopMusic will be called
		bgmusic.stopMusic();
		
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