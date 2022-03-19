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
	
	/** CONSTRUCTOR **/
	public BattleEngine(Pokedex pokemon1, Pokedex pokemon2) {
		this.pokemon1 = pokemon1;
		this.pokemon2 = pokemon2;
	}
	/** END CONSTRUCTOR **/
	
	public void swapPokemon(Pokedex newPokemon, int trainerNum) {
		
		if (trainerNum == 1) {
			if (pokemon1 != null)
				pokemon1 = newPokemon;
		}			
		else if (trainerNum == 2) {
			if (pokemon2 != null) 
				pokemon2 = newPokemon;
		}		
		else 
			System.out.println("INTERNAL ERROR! Cannot swap Pokemon!");
	}

	/** MOVE METHOD **/
	public void move(Moves move1, Moves move2) {
				
		// both pokemon can turn
		if (pokemon1.isAlive && pokemon2.isAlive) {
				
			// returns 1 if pokemon1 goes first, 2 if pokemon2
			int numTurn = getTurn(move1, move2);
			
			// pokemon1 move
			if (numTurn == 1) {
				
				// pokemon1 has no status effects
				if (canTurn(1)) 
					startMove(1, move1);
				
				// pokemon2 becomes attacker if not fainted
				if (pokemon2.isAlive) {
					if (canTurn(2))
						startMove(2, move2);					
					
					isPoisoned(pokemon1);
					isPoisoned(pokemon2);					
				}
				else {
					System.out.println(pokemon2.getName() + " has fainted and cannot battle!");
					Sleeper.pause(2000);
				}					
			}
			// pokemon2 move
			else if (numTurn == 2) {
				
				// pokemon2 has no status effects
				if (canTurn(2)) 
					startMove(2, move2);
				
				// pokemon1 becomes attacker if not fainted
				if (pokemon2.isAlive) {
					if (canTurn(2))
						startMove(1, move1);
					
					isPoisoned(pokemon1);
					isPoisoned(pokemon2);	
				}
				else {
					System.out.println(pokemon2.getName() + " has fainted and cannot battle!");
					Sleeper.pause(2000);
				}
			}			
			
		}
	}
	/** END MOVE METHOD **/
	
	// status effects reference: https://pokemon.fandom.com/wiki/Status_Effects //
	private void isPoisoned(Pokedex pokemon) {
		
		if (pokemon.getStatus() != null) {
			
			if (pokemon.getStatus().getName().equals("PSN")) {
				
				int newHP = pokemon.getHP() - (int) (pokemon.getHP() * 0.16);
				
				if (newHP < 0) 
					newHP = 0;
				
				pokemon.setHP(newHP);				
				
				System.out.println(pokemon.getName() + " is hurt from the poison!");
				SoundCard.play(-1.0);
				Sleeper.pause(1700);
				System.out.println(new String(new char[70]).replace("\0", "\r\n"));
			}
		}
	}
	
	/** CAN TURN METHOD **/ 
	// returns false if given pokemon cannot move due to status //
	private boolean canTurn(int numTurn) {
		
		Pokedex attacker = (numTurn == 1) ? pokemon1 : pokemon2;
		
		if (attacker.getStatus() != null) {
							
			int val = 1;
			
			// check pokemon status
			switch (attacker.getStatus().getName()) {
			
				case "PRZ":		
					
					val = 1 + (int)(Math.random() * ((4 - 1) + 1));
					if (val == 1) {						
						System.out.println(attacker.getName() + " is paralyzed and unable to move!");
						Sleeper.pause(1700);
						System.out.println(new String(new char[70]).replace("\0", "\r\n"));						
						return false;
					} 
					else { 
						return true;
					}				
					
				case "SLP":
					System.out.println(attacker.getName() + " is currently asleep!");
					Sleeper.pause(1700);
					System.out.println(new String(new char[70]).replace("\0", "\r\n"));
					return false;	
					
				case "FZN":
					System.out.println(attacker.getName() + " is frozen solid and unable to move!");
					Sleeper.pause(1700);
					System.out.println(new String(new char[70]).replace("\0", "\r\n"));
					return false;
					
				case "CNF":
					
					System.out.println(attacker.getName() + " is confused!");							
					SoundCard.play(-2.0);
					Sleeper.pause(1700);
					
					val = 1 + (int)(Math.random() * ((2 - 1) + 1));	
					if (val == 1) {						
						int newHP = attacker.getHP() - calculateConfusionDamage(attacker);
						if (newHP < 0) newHP = 0;	
						
						attacker.setHP(newHP);
						
						System.out.println(attacker.getName() + " hurt itself in confusion!");
						Sleeper.pause(1700);
						System.out.println(new String(new char[70]).replace("\0", "\r\n"));						
						return false;
					}
					else {
						return true;
					}							
				default:
					return true;
			}
		}	
		else {
			return true;
		}
	}
	/** END GET TURN METHOD **/
	
	/** CALCULATE CONFUSION DAMAGE DEALT METHOD **/
	// confusion damage reference: https://pokemonlp.fandom.com/wiki/Confusion_(status) //
	private int calculateConfusionDamage(Pokedex pokemon) {
		
		double level = pokemon.getLevel();
		double power = Moves.CONFUSE.getPower();
		
		double A = pokemon.getAttack();
		double D = pokemon.getDefense();
				
		// damage formula reference: https://bulbapedia.bulbagarden.net/wiki/Damage
		int damageDealt = (int)((Math.floor(((((Math.floor((2 * level) / 5)) + 2) * power * (A / D)) / 50)) + 2));
				
		SoundCard.play(1.0);
		
		return damageDealt;
	}
	/** END CALCULATE CONFUSION DAMAGE DEALT METHOD **/
	
	/** GET TURN METHOD **/
	// returns 1 if pokemon1 goes first, 2 if pokemon2 goes first //
	private int getTurn(Moves move1, Moves move2) {
		
		// if both moves go first (EX: Quick Attack)
		if (move1.getGoFirst() && move2.getGoFirst()) {			
			// if pokemon1 is faster (pokemon1 has advantage if equal)
			if (pokemon1.getSpeed() >= pokemon2.getSpeed()) 
				return 1;
			else 
				return 2;
		}
		// if only move1 goes first (EX: Quick Attack)
		else if (move1.getGoFirst())
			return 1;
		// if only move2 goes first (EX: Quick Attack)
		else if (move2.getGoFirst()) 
			return 2;
		else {
			// if pokemon1 is faster (if equal, pokemon1 has advantage)
			if (pokemon1.getSpeed() >= pokemon2.getSpeed())
				return 1;
			else 
				return 2;
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
	private void startMove(int numTurn, Moves givenMove) {
		
		Pokedex attacker, target;
		
		if (numTurn == 1) { attacker = pokemon1; target = pokemon2; }			
		else { attacker = pokemon2; target = pokemon1; }
		
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
					
					if (move.getMType().equals("Status")) {	
						
						if (target.getStatus() == null) {
							target.setStatus(move.getEffect());						
							System.out.println(target.getName() + " is " + target.getStatus().getCondition() + "!");
							
							Sleeper.pause(1700);
							System.out.println(new String(new char[70]).replace("\0", "\r\n"));
						}
						else {
							System.out.println(target.getName() + " is already " + target.getStatus().getCondition() + "!");
							
							Sleeper.pause(1700);
							System.out.println(new String(new char[70]).replace("\0", "\r\n"));
						}
						return;
					}
					
					// get critical damage (if applicable)
					double crit = isCritical();			
					
					// if critical hit
					if (crit == 1.5) { System.out.println("A critical hit!"); }
									
					// calculate damage dealt
					int damageDealt = calculateDamage(numTurn, move, crit, false);
					
					// no damage dealt
					if (damageDealt == 0) {
						System.out.println("It had no effect!");
						Sleeper.pause(1700);
						System.out.println(new String(new char[70]).replace("\0", "\r\n"));
					}
					else {
						int health = dealDamage(damageDealt, target);
						
						System.out.println(target.getName() + " took " + damageDealt + " damage!");
						Sleeper.pause(1700);
						System.out.println(new String(new char[70]).replace("\0", "\r\n"));
						
						// pokemon fainted
						if (health == 0) {
							int xp = setWin(numTurn, damageDealt);	
							
							System.out.println(target.getName() + " fainted!");		
							Sleeper.pause(2000);
							
							System.out.println(attacker.getName() + " gained " + xp + " Exp. Points!");		
							Sleeper.pause(2000);						
							System.out.println(new String(new char[70]).replace("\0", "\r\n"));
						}
					}
				}						
				// attack missed
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
	private int calculateDamage(int numTurn, Moves move, double crit, boolean cpu) {
		
		Pokedex attacker, target;
		
		if (numTurn == 1) { attacker = pokemon1; target = pokemon2; }			
		else { attacker = pokemon2; target = pokemon1; }
		
		double level = attacker.getLevel();
		double power = move.getPower();
		
		double A = 1.0; double D = 1.0; 
		double type = 1.0;

		if (move.getMType().equals("Special")) {
			A = attacker.getSpAttack();
			D = target.getSpDefense();
		}
		else if (move.getMType().equals("Physical")) {
			A = attacker.getAttack();
			D = target.getDefense();
		}
		
		// if target has more than 1 type
		if (target.getTypes() != null) {	
			
			// cycle through each type of target
			for (TypeEngine t : target.getTypes()) {
				
				// if super effective
				if (effectiveness(move.getType(), t) == 2.0) {
					type = 2.0;
					break;
				}
				type = effectiveness(move.getType(), t);
			}
		}
		else {		
			type = effectiveness(move.getType(), target.getType());
		}
		
		// damage formula reference: https://bulbapedia.bulbagarden.net/wiki/Damage
		int damageDealt = (int)((Math.floor(((((Math.floor((2 * level) / 5)) + 2) * power * (A / D)) / 50)) + 2) * crit * type);
		
		// don't play sound if cpu is calling method
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
		
		// set HP to 0 if below 0
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
	private int setWin(int winner, int damageDealt) {
		
		Pokedex attacker, target;
		
		if (winner == 1) { attacker = pokemon1; target = pokemon2; }			
		else { attacker = pokemon2; target = pokemon1; }
											
		target.setAlive(false);
						
		int xp = calculateXP(winner); 
		attacker.setXP(xp);				
		
		winningPokemon = winner;
		return xp;
	}
	/** END SET WIN METHOD **/
	
	/** CALCULATE XP METHOD **/
	private int calculateXP(int winner) {
		
		Pokedex target;
		
		if (winner == 1) target = pokemon2; 		
		else target = pokemon1;
		
		// exp formula reference: https://bulbapedia.bulbagarden.net/wiki/Experience
		int exp = (int) (1.5 * 1 * target.getXP() * 1 * target.getLevel() * 1 * 1 * 1) / 7;
		
		return exp;
	}
	/** END CALCULATE XP METHOD **/	
	
	/** GET WINNER METHOD **/
	public int getWinner() {
		if (winningPokemon == 1)
			return 1;
		else if (winningPokemon  == 2)
			return 2;
		else
			return -1;
	}
	/** END GET WINNER METHOD **/
	
	/** GET MONEY METHOD **/
	public int getMoney() {
		
		int money = 1;
		
		if (winningPokemon == 1) 
			money = 24 * pokemon2.getLevel();	
		else 
			money = 24 * pokemon1.getLevel();	
		
		return money;
	}
	/** END GET MONEY METHOD **/
}
/*** END BATTLE ENGINE CLASS ***/