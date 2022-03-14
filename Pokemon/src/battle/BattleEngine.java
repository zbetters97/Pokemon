package battle;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import application.Sleeper;
import application.SoundCard;
import moves.Moves;
import pokemon.Pokedex;
import types.TypeEngine;

/*** BATTLE ENGINE CLASS ***/
public class BattleEngine {
	
	Pokedex pokemon1, pokemon2;
	private int winningPokemon;
	
	// establish pokemon in battle
	public BattleEngine(Pokedex pokemon1, Pokedex pokemon2) {
		this.pokemon1 = pokemon1;
		this.pokemon2 = pokemon2;
	}
	
	public void swapTrainer(Pokedex newTrainer, int trainerNum) {
		
		if (trainerNum == 1) {
			if (pokemon1 != null)
				pokemon1 = newTrainer;
		}			
		else if (trainerNum == 2) {
			if (pokemon2 != null) 
				pokemon2 = newTrainer;
		}		
		else 
			System.out.println("INTERNAL ERROR! Cannot swap Pokemon!");
	}

	/** MOVE METHOD **/
	public void move(Moves move1, Moves move2) {
				
		if (canTurn(1)) {
				
			// returns true if pokemon1 goes first, false if pokemon2
			int upNext = getTurn(move1, move2);
			
			// pokemon1 move
			if (upNext == 1) {
				startMove(1, move1);
				
				// pokemon2 becomes attacker if not fainted
				if (canTurn(1)) {
					startMove(2, move2);
				}
			}
			// pokemon2 move
			else if (upNext == 2) {
				startMove(2, move2);
				
				// pokemon1 becomes attacker if not fainted
				if (canTurn(2)) {
					startMove(1, move1);
				}
			}	
			else {
				System.out.println("INTERNAL ERROR! Cannot find next turn!");
			}
		}	
	}
	/** END MOVE METHOD **/
	
	/** CAN TURN METHOD **/ 
	// return false if either trainer cannot battle //
	private boolean canTurn(int upNext) {
		
		Pokedex attacker, target;
		
		if (upNext == 1) {
			attacker = pokemon1; 
			target = pokemon2;
		}			
		else {
			attacker = pokemon2; 
			target = pokemon1;
		}
		
		// pokemon1 can battle
		if (attacker.isAlive) {
			
			// trainer 2 can battle
			if (target.isAlive) {
				return true;
			}			
			//pokemon2 cannot battle
			else {
				//System.out.println(pokemon2.getName() + " has fainted and cannot battle!");
				Sleeper.pause(2000);
				return false;
			}
		}		
		//pokemon1 cannot battle
		else {
			System.out.println(attacker.getName() + " has fainted and cannot battle!");
			Sleeper.pause(2000);
			return false;	
		}		
	}
	
	/** GET TURN METHOD **/
	private int getTurn(Moves move1, Moves move2) {
		
		// if both moves go first (EX: Quick Attack)
		if (move1.getGoFirst() && move2.getGoFirst()) {			
			// if pokemon1 is faster (if equal, pokemon1 has advantage)
			if (pokemon1.getSpeed() >= pokemon2.getSpeed()) { return 1; }
			else { return 2; }
		}
		// if only move1 goes first (EX: Quick Attack)
		else if (move1.getGoFirst()) { return 1; }
		// if only move2 goes first (EX: Quick Attack)
		else if (move2.getGoFirst()) { return 2; }
		else {
			// if pokemon1 is faster (if equal, pokemon1 has advantage)
			if (pokemon1.getSpeed() >= pokemon2.getSpeed()) { return 1; }
			else { return 1; }
		}
	}
	/** END GET TURN METHOD **/
	
	/** CPU SELECT MOVE METHOD **/
	// returns move with max damage //
	public Moves cpuSelectMove() {
		
		// holds Map of Move and Damage Points
		Map<Moves, Integer> moves = new HashMap<>();
		
		// for each move in attacker's move set
		for (Moves move : pokemon2.getMoveSet()) {
			
			// find damage value of each move
			int damage = calculateDamage(2, move, 1.0, true);
			
			// add move and corresponding damage to list
			moves.put(move, damage);
		}
		
		// find max value in moves list based on value
		Moves bestMove = Collections.max(moves.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey(); 
				
		return bestMove;
	}
	/** END CPU SELECT MOVE METHOD **/
	
	/** START MOVE METHOD **/
	private void startMove(int upNext, Moves givenMove) {
		
		Pokedex attacker, target;
		
		if (upNext == 1) {
			attacker = pokemon1; 
			target = pokemon2;
		}			
		else {
			attacker = pokemon2; 
			target = pokemon1;
		}
		
		// loop through moveset for attacking pokemon
		for (Moves move : attacker.getMoveSet()) {
			
			// if chosen move is found
			if (move.getName().equals(givenMove.getName())) {	
				
				System.out.println(attacker.getName() + " used " + move.getName() + "!");
				
				// decrease move pp
				move.setpp(move.getpp() - 1);
				
				Sleeper.pause(1000);
	            
	            // if attack lands
				if (isHit(move)) {
					
					// play move sound
					SoundCard.play("//moves//" + move.getName(), true);
					
					// get critical damage (if applicable)
					double crit = isCritical();			
					
					// if critical hit
					if (crit == 1.5) { System.out.println("A critical hit!"); }
									
					// calculate damage dealt
					int damageDealt = calculateDamage(upNext, move, crit, false);								
					int health = dealDamage(damageDealt, target);
					
					if (health == 0) 
						setWin(upNext, damageDealt);
					
					else {
						System.out.println(target.getName() + " took " + damageDealt + " damage!");
						Sleeper.pause(1700);
						System.out.println(new String(new char[70]).replace("\0", "\r\n"));
					}
				}						
				// move missed
				else {
					System.out.println("The attack missed!");
					Sleeper.pause(2000);
					System.out.println(new String(new char[70]).replace("\0", "\r\n"));
				}		
			}	
		}
	}
	/** END START MOVE METHOD **/

	/** IS HIT METHOD **/
	private static boolean isHit(Moves move) {
		
		// if move never misses, return true
		if (move.getAccuracy() == -1) { return true; }
		
		Random r = new Random();
		float chance = r.nextFloat();
		
		// chance of missing is accuracy value / 100
		return (chance <= ((float) move.getAccuracy() / 100)) ? true : false;
	}
	/** END IS HIT METHOD **/
		
	/** CALCULATE DAMAGE DEALT METHOD **/
	private int calculateDamage(int upNext, Moves move, double crit, boolean cpu) {
		
		Pokedex attacker, target;
		
		if (upNext == 1) {
			attacker = pokemon1; 
			target = pokemon2;
		}			
		else {
			attacker = pokemon2; 
			target = pokemon1;
		}
		
		double level = attacker.getLevel();
		double power = move.getPower();
		
		double A, D, type = 1.0;
		
		if (attacker.getTypes() != null) {			
			for (TypeEngine t : attacker.getTypes()) {
				
				if (t.equals(move.getType())) 
					A = attacker.getSpAttack();
			}
			A = attacker.getAttack();
		}
		else
			A = move.getType().equals(attacker.getType()) ? attacker.getSpAttack() : attacker.getAttack();
		
		if (target.getTypes() != null) {			
			for (TypeEngine t : target.getTypes()) {
				
				if (t.equals(move.getType()))
					D = target.getSpAttack(); 
			}
			D = target.getDefense();
			
			for (TypeEngine t : target.getTypes()) {
				if (effectiveness(move.getType(), t) == 2.0) {
					type = effectiveness(move.getType(), t);
					break;
				}
				type = effectiveness(move.getType(), t);
			}
		}
		else {
			D = move.getType().equals(target.getType()) ? target.getSpDefense() : target.getDefense();			
			type = effectiveness(move.getType(), target.getType());
		}
						
		// damage formula reference: https://bulbapedia.bulbagarden.net/wiki/Damage
		int damageDealt = (int) ((((((2 * level) / 5) + 2) * power * (A / D)) / 50) * crit * type);
		
		if (cpu) return damageDealt;
		
		SoundCard.play(type);
		
		return damageDealt;
	}
	/** END CALCULATE DAMAGE DEALT METHOD **/
	
	/** IS CRITICAL METHOD **/
	private double isCritical() {	
		
		// 1/255 chance of landing critical hit
		Random r = new Random();		
		return (r.nextFloat() <= ((float) 1 / 255)) ? 1.5 : 1;
	}
	/** END IS CRITICAL METHOD **/
	
	/** DEAL DAMAGE METHOD **/
	private static int dealDamage(int damage, Pokedex target) {		
		
		// subtract damage dealt from total hp
		int result = target.getHP() - (int)damage;		
		
		// set hp to 0 if below 0
		if (result < 0) { result = 0; }	
		
		target.setHP(result);
		
		return target.getHP();
	}
	/** END DEAL DAMAGE METHOD **/

	/** EFFECTIVENESS METHOD **/
	private static double effectiveness(TypeEngine move, TypeEngine target) {
		
		double result = 1.0;
		
		// if vulnerable, retrieve and return vulnerability value
		for (TypeEngine type : target.getVulnerability()) {			
			if (type.toString().equals(move.toString())) {
				result = type.getStrength();
				return result;
			}			
		}
		
		// if resistant, retrieve and return resistance value
		for (TypeEngine type : target.getResistance()) {			
			if (type.toString().equals(move.toString())) {
				result = type.getStrength();
				return result;
			}			
		}		
		
		// return 1.0 if neither found
		return result;
	}
	/** END EFFECTIVENESS METHOD **/
	
	/** SET WIN METHOD **/
	private void setWin(int winner, int damageDealt) {
		
		Pokedex attacker, target;
		
		if (winner == 1) {
			attacker = pokemon1; 
			target = pokemon2;
		}			
		else {
			attacker = pokemon2; 
			target = pokemon1;
		}
		
		System.out.println(target.getName() + " took " + damageDealt + " damage!");
		Sleeper.pause(2000);
		
		System.out.println(new String(new char[70]).replace("\0", "\r\n"));
											
		target.setAlive(false);
		System.out.println(target.getName() + " fainted!");
		
		Sleeper.pause(2000);
		
		int xp = calculateXP(winner); 
		attacker.setXP(xp);				
		
		System.out.println(attacker.getName() + " gained " + xp + " Exp. Points!");		
		Sleeper.pause(2000);
		
		System.out.println(new String(new char[70]).replace("\0", "\r\n"));
		
		winningPokemon = winner;
	}
	/** END SET WIN METHOD **/
	
	/** CALCULATE XP METHOD **/
	private int calculateXP(int winner) {
		
		Pokedex target;
		
		if (winner == 1) 
			target = pokemon2; 		
		else 
			target = pokemon1;
		
		int exp = (int) (1.5 * target.getXP() * target.getLevel() * target.getEV()) / 7;
		
		return exp;
	}
	/** END CALCULATE XP METHOD **/	
	
	public int getWinner() {
		if (winningPokemon == 1)
			return 1;
		else if (winningPokemon  == 2)
			return 2;
		else
			return -1;
	}
	
	public int getMoney() {
		
		int money = 1;
		
		if (winningPokemon == 1) 
			money = 24 * pokemon2.getLevel();	
		else 
			money = 24 * pokemon1.getLevel();	
		
		return money;
	}
}
/*** END BATTLE ENGINE CLASS ***/