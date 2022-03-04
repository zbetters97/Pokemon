package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import moves.MoveEngine;
import moves.BattleEngine;
import pokemon.*;

public class Driver {

	static Scanner input = new Scanner(System.in);
	
	static List<Pokedex> pokemonTeam;
	static Pokedex fighter, opponent;
	
	static BattleEngine battle;
	
	public static void main(String[] args) {
		
		selectPokemon();
		
		while (true) { 
									
			System.out.println("\n\n---" + fighter.getName() + " HP: " + fighter.getHP() + "---");
			System.out.println("---" + opponent.getName() + " HP: " + opponent.getHP() + "---\n");
			
			System.out.println("(1): SELECT MOVE\n(2): LIST MOVES\n(3): CHANGE POKEMON\n(4): RUN");
			
			int choice;
			
			try { choice = input.nextInt(); }
			catch (Exception e) {
				System.out.println("You have entered an invalid selection.\n"
						+ "This program will now end.");
				break;
			}
			
			switch (choice) {
			
				case 1: 
					clearContent();
					move(fighter, opponent);
					break;
					
				case 2: 
					clearContent();
					fighter.listMoves();
					break;
					
				case 3: 
					clearContent();
					selectPokemon();
					break;
					
				case 4:
					clearContent();
					System.out.println("Got away safely!");
					System.exit(1); 
					break;
					
				default: 
					clearContent();
					System.out.println("You have entered an unrecognized choice. "
							+ "Please be sure to enter a valid option");
					break;
			}
		}		
	}
	
	public static void selectPokemon() {
		
		pokemonTeam = Pokedex.getPokedex();
		
		Pokedex.CHARMANDER.addMove(MoveEngine.EMBER);
		
		int choice;
		int counter;
		
		System.out.println("PLEASE SELECT YOUR POKEMON:");			
		counter = 1;
		for (Pokedex pokemon : pokemonTeam) {
			System.out.println("(" + counter + "): " + pokemon.getName());
			counter++;
		}			
		choice = input.nextInt();				
		fighter = pokemonTeam.get(choice - 1);
		
		System.out.println("PLEASE SELECT YOUR OPPONENT'S POKEMON:");			
		counter = 1;
		for (Pokedex pokemon : pokemonTeam) {
			System.out.println("(" + counter + "): " + pokemon.getName());
			counter++;
		}			
		choice = input.nextInt();			
		opponent = pokemonTeam.get(choice - 1);
		
		clearContent();
		
		battle = new BattleEngine(fighter, opponent);
	}
	
	public static void move (Pokedex pokemon, Pokedex target) {
		
		ArrayList<MoveEngine> moveSet = pokemon.getMoveSet();
		
		System.out.println("What move will " + pokemon.getName() + " do?\n");
		
		int counter = 1;		
		for (MoveEngine move : moveSet) {
			System.out.println("(" + counter + ") " + move.getName());
			counter++;
		}
		System.out.println("(" + counter + ") <-BACK");
				
		int choice = 0;
		
		try { choice = input.nextInt(); }
		catch (Exception e) {
			System.out.println("You have entered an invalid selection.\n"
					+ "This program will now end.");
		}
		
		if (0 < choice && choice < counter) {
			clearContent();
			battle.move(moveSet.get(choice - 1));
		}
		else if (choice == counter) {
			clearContent();
		}
		else {
			clearContent();
			System.out.println("Invalid choice!");
		}
	}
		
	public static void clearContent() {		
		for(int clear = 0; clear < 200; clear++) 
			System.out.println("\n") ;
	}
}