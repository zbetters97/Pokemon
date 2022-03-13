package application;

import java.io.IOException;
import java.util.Scanner;

/*** MAIN MENU CLASS ***/
public class MainMenu {
	
	// scanner to receive user input
	static Scanner input = new Scanner(System.in);
	
	/** LOAD METHOD **/
	public static void load() {
		
		SoundCard bgmusic = new SoundCard("battle-red");
		bgmusic.playMusic();
		
		// loop until Quit is selected
		while (true) {
			
			System.out.println("PLEASE SELECT MODE:\n[1] ONE PLAYER\n[2] TWO PLAYER\n[3] QUIT");

			try { 
				int choice = input.nextInt(); 
				
				if (choice == 1) { 
					Battle game = new Battle(1); 
					game.start(); 
					break;
				}		
				else if (choice == 2) { 
					Battle game = new Battle(2); 
					game.start(); 
					break;
				}
				else if (choice == 3) { 
					System.out.println("Turning off..."); 
					System.exit(0); 
				}	
				else { 
					System.out.println("ERROR! Input must be a valid selection!"); 
				}	
			}
			catch (Exception e) {
				System.out.println("ERROR! Input must be a number!");
				input.next();
			}
		}
		
		bgmusic.stopMusic();
		
		try { System.in.read(); } 
		catch (IOException e) { e.printStackTrace(); }
	}
	/** END LOAD METHOD **/
}
/*** END MAIN MENU CLASS ***/