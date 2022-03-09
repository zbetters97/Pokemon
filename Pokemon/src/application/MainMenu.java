package application;

import java.util.Scanner;

public class MainMenu {
	
	static Scanner input = new Scanner(System.in);
	
	public static void load() {
			
		System.out.println("PLEASE SELECT MODE:\n[1] ONE PLAYER MODE\n[2] TWO PLAYER MODE\n[3] QUIT");		
		int choice = 0;
				
		try { choice = input.nextInt(); }
		catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}
		
		if (choice == 1) { 
			Battle game = new Battle(1); 
			game.start(); 
		}		
		else if (choice == 2) {
			Battle game = new Battle(2); 
			game.start(); 
		}
		else if (choice == 3) { 
			System.out.println("Turning off...");
			System.exit(1); 
		}
	}
}