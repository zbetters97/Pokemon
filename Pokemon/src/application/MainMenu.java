package application;

import java.util.Scanner;

public class MainMenu {
	
	static Scanner input = new Scanner(System.in);
	
	public static void load() {
		
		System.out.println("PLEASE SELECT MODE:\n[1] ONE PLAYER MODE\n[2] TWO PLAYER MODE\n[3] QUIT");		
		
		while (true) {
	
			try { 
				int choice = input.nextInt(); 
				
				if (choice == 1) { Battle game = new Battle(1); game.start(); }		
				else if (choice == 2) { Battle game = new Battle(2); game.start(); }
				else if (choice == 3) { System.out.println("Turning off..."); System.exit(1); }	
				else { System.out.println("Input must be a valid selection!"); }	
			}
			catch (Exception e) {
				System.out.println("Input must be a number!");
				input.next();
			}
		}
	}
}