package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/*** MAIN MENU CLASS ***/
public class MainMenu {
	
	// scanner to receive user input
	static Scanner input = new Scanner(System.in);
	static SoundCard bgmusic;
	
	/** LOAD METHOD **/
	public static void load() {

		clearContent();		
		int players = selectMode();
		
		clearContent();		
		selectMusic();
		
		startGame(players);
	}
	/** END LOAD METHOD **/
	
	/** SELECT MODE METHOD **/
	private static int selectMode() {
				
		System.out.println("PLEASE SELECT MODE:\n"
				+ "[1] ONE PLAYER\n"
				+ "[2] TWO PLAYER\n"
				+ "[3] QUIT");
		
		// loop until Quit is selected
		while (true) {
			
			try { 
				int choice = input.nextInt(); 
				
				if (choice == 1)
					return choice;
				else if (choice == 2)
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
	/** END SELECT MODE METHOD **/
	
	/** SELECT MUSIC METHOD **/
	private static void selectMusic() {
						
		String btltrainerjohto = "//music//battle-trainer-johto";
		String btltrainerkanto = "//music//battle-trainer-kanto";		
		String btlelitefour = "//music//battle-elite-four";
		String btlred = "//music//battle-red";
		
		ArrayList<String> musicList = new ArrayList<>();
		
		musicList.addAll(Arrays.asList(
				btltrainerjohto, btltrainerkanto, 
				btlelitefour, btlred)
		);

		bgmusic = null;	
		
		System.out.println("PLEASE SELECT MUSIC:\n"
				+ "[1] TRAINER - JOHTO\n"
				+ "[2] TRAINER - KANTO\n"
				+ "[3] ELITE FOUR\n"
				+ "[4] RED\n"
				+ "[5] QUIT");
		
		while (true) {
			
			try { 				
				int choice = input.nextInt(); 
				
				if (0 < choice && choice <= musicList.size()) {
					bgmusic = new SoundCard(musicList.get(choice));
					bgmusic.playMusic();
					return;
				}					
				else if (choice == 5) {
					clearContent();
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
	/** END SELECT MUSIC METHOD **/
	
	private static void startGame(int players) {
		
		Battle game = new Battle(players);
		game.start();

		bgmusic.stopMusic();
		
		try { System.in.read(); } 
		catch (IOException e) { e.printStackTrace(); }
	}
	
	/** CLEAR SCREEN METHOD **/	
	private static void clearContent() {		
		System.out.println(new String(new char[70]).replace("\0", "\r\n"));
	}
	/** END CLEAR SCREEN METHOD **/
}
/*** END MAIN MENU CLASS ***/