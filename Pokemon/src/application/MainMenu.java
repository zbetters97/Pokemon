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
		//selectMusic();
		
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
		
		// music files //
		String btlRStrainer = "battle-rs-trainer";
		String btlRSgym = "battle-rs-gym";
		String btlRSchampion = "battle-rs-champion";
		String btlRSlegendary = "battle-rs-legendary";
						
		String btlHStrainerjohto = "battle-hs-trainer-johto";
		String btlHStrainerkanto = "battle-hs-trainer-kanto";		
		String btlHSgymjohto = "battle-hs-gym-johto";
		String btlHSgymkanto = "battle-hs-gym-kanto";		
		String btlHSred = "battle-hs-red";
		
		String btlDPdialga = "battle-dp-dialga";
		
		// list to hold music files
		ArrayList<String> musicList = new ArrayList<>();
		
		// add all files to music list
		musicList.addAll(Arrays.asList(
				btlRStrainer, btlRSgym, btlRSchampion, btlRSlegendary,								
				btlHStrainerjohto, btlHStrainerkanto, btlHSgymjohto, btlHSgymkanto, btlHSred,				
				btlDPdialga
		));

		bgmusic = null;	
		
		System.out.println("PLEASE SELECT MUSIC:\n\n"
				+ "[0] QUIT\n\n"
				+ "[1] R/S: TRAINER\n"
				+ "[2] R/S: GYM\n"
				+ "[3] R/S: CHAMPION\n"
				+ "[4] R/S: LEGENDARY\n\n"
				+ "[5] HG/SS: TRAINER (JOHTO)\n"
				+ "[6] HG/SS: TRAINER (KANTO)\n"
				+ "[7] HG/SS: GYM (JOHTO)\n"
				+ "[8] HG/SS: GYM (KANTO)\n"
				+ "[9] HG/SS: RED\n\n"
				+ "[10] D/P: DIALGA\n"
		);
		
		while (true) {
			
			try { 				
				int choice = input.nextInt(); 
				
				if (0 < choice && choice <= musicList.size()) {
					System.out.println(musicList.size());
					bgmusic = new SoundCard("\\music\\" + musicList.get(choice - 1));
					bgmusic.playMusic();
					return;
				}					
				else if (choice == 0) {
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
	
	/** START GAME METHOD **/
	private static void startGame(int players) {
		
		Battle game = new Battle(players);
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