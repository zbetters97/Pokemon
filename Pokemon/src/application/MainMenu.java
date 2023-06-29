package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

/*** MAIN MENU CLASS ***/
public class MainMenu {
	
	// scanner to receive user input
	static Scanner input = new Scanner(System.in);
	static SoundCard bgmusic;
	
	/** LOAD METHOD **/
	public static void load() {

		clearContent();		
		int players = selectPlayers();
		
		clearContent();
		int partySize = selectPartySize();
		
		clearContent();		
		selectMusic();
		
		startGame(players, partySize);
	}
	/** END LOAD METHOD **/
	
	/** SELECT PLAYERS METHOD **/
	private static int selectPlayers() {
				
		System.out.println("PLEASE SELECT MODE:\n"
				+ "[1] ONE PLAYER\n"
				+ "[2] TWO PLAYER\n"
				+ "[3] QUIT");
		
		// loop until Quit is selected
		while (true) {
			
			try { 
				int choice = input.nextInt(); 
				
				if (choice == 1 || choice == 2)
					return choice;
				else if (choice == 3) { 
					System.out.println("Turning off..."); 
					System.exit(0); 
				}	
				else 
					System.out.println("ERROR! Input must be a valid selection!"); 
			}
			catch (Exception e) {
				System.out.println("ERROR! Input must be a number!");
				input.next();
			}
		}
	}
	/** END SELECT PLAYERS METHOD **/
	
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
					System.out.println("Turning off..."); 
					System.exit(0); 
				}	
				else 
					System.out.println("ERROR! Input must be a valid selection!"); 
			}
			catch (Exception e) {
				System.out.println("ERROR! Input must be a number!");
				input.next();
			}
		}
	}
	/** END SELECT PARTY SIZE METHOD **/
	
	/** SELECT MUSIC METHOD **/
	private static void selectMusic() {
		
		// arraylist to hold music String
		ArrayList<String> musicList = new ArrayList<>();
		
		// hashmap to hold music file and corrosponding index
		LinkedHashMap<Integer, String> musicDict = new LinkedHashMap<>();
		
		// get all songs from music folder
		String path = new File("").getAbsolutePath() + "//lib//sounds//music";
		File directoryPath = new File(path);
		
		// store all music files into array
		File filesList[] = directoryPath.listFiles();
		
		for (int i = 0; i < filesList.length; i++) {			
			
			String music = filesList[i].getName();
			musicDict.put(i, music);
			
			// format music
			String song = filesList[i].getName()
					.replace("battle-", "")
					.replace(".wav", "")
					.replace("-", ": ")
					.replace("_", ", ")
					.toUpperCase();
			
			// add to arraylist
			musicList.add(song);			
		}		
		
		System.out.println("PLEASE SELECT MUSIC:\n\n[0] QUIT\n[1] NONE");
		for (int i = 0; i < musicList.size(); i++) {
			int c = i + 2;
			System.out.println("[" + c + "] " + musicList.get(i));
		}
		
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
					System.out.println("Turning off..."); 
					System.exit(0);
				}
				else if (choice == 0) {
					return;
				}
				else
					System.out.println("ERROR! Input must be a valid selection!");
			}
			catch (Exception e) {
				System.out.println("ERROR! Input must be a number!");
				System.out.println(e);
				input.next();
			}
		}
	}
	/** END SELECT MUSIC METHOD **/
	
	/** START GAME METHOD **/
	private static void startGame(int players, int partySize) {
		
		Battle game = new Battle(players, partySize);
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