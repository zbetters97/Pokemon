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
	
	private Pokedex winningPokemon, losingPokemon;
	
	/** CPU SELECT MOVE METHOD **/
	// returns move with max damage //
	public Moves cpuSelectMove(Pokedex cpupokemon, Pokedex target) {
		
		Moves bestMove;
		
		// holds Map of Move and Damage Points
		Map<Moves, Integer> moves = new HashMap<>();
		
		// for each move in attacker's move set
		for (Moves move : cpupokemon.getMoveSet()) {
			
			if (!move.getMType().equals("Status")) {
				
				// find damage value of each move
				int damage = calculateDamage(cpupokemon, target, move, 1.0, true);
				
				// add move and corresponding damage to list
				moves.put(move, damage);	
			}			
		}
		
		if (!moves.isEmpty()) {
			// find max value in moves list based on value
			bestMove = Collections.max(moves.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey(); 	
		}	
		else {
			bestMove = cpupokemon.getMoveSet().get(0);
		}
				
		return bestMove;
	}
	/** END CPU SELECT MOVE METHOD **/
	
	/** MOVE METHOD **/
	public void move(Pokedex pokemon1, Pokedex pokemon2, Moves move1, Moves move2) {
				
		// if both pokemon are alive
		if (pokemon1.isAlive && pokemon2.isAlive) {
				
			// returns 1 if pokemon1 goes first, returns 2 if pokemon2 goes first
			int numTurn = getTurn(pokemon1, pokemon2, move1, move2);
			
			// if pokemon1 moves first
			if (numTurn == 1) {
				
				// if pokemon1 has no status effects
				if (canTurn(pokemon1, pokemon2)) 
					startMove(pokemon1, pokemon2, move1);
				
				// pokemon2 becomes attacker if not fainted
				if (pokemon1.isAlive && pokemon2.isAlive) {
					
					// if pokemon2 has no status effects
					if (canTurn(pokemon2, pokemon1)) 
						startMove(pokemon2, pokemon1, move2);			
				}				
			}
			// pokemon2 move
			else if (numTurn == 2) {
				
				// pokemon2 has no status effects
				if (canTurn(pokemon2, pokemon1)) 
					startMove(pokemon2, pokemon1, move2);
				
				// pokemon1 becomes attacker if not fainted
				if (pokemon1.isAlive && pokemon2.isAlive) {
					if (canTurn(pokemon1, pokemon2))
						startMove(pokemon1, pokemon2, move1);
				}				
			}
									
			// check if either pokemon has status damage
			statusDamage(pokemon1, pokemon2);
		}
	}
	/** END MOVE METHOD **/
	
	/** CAN TURN METHOD **/ 
	// returns false if given pokemon cannot move due to status //
	private boolean canTurn(Pokedex attacker, Pokedex target) {
		
		if (attacker.getStatus() != null) {
			
			int val = 1;
			
			// check pokemon status
			switch (attacker.getStatus().getName()) {
			
				case "PRZ":						
					val = 1 + (int)(Math.random() * ((4 - 1) + 1));
					if (val == 1) {						
						System.out.println(attacker.getName() + " is paralyzed and unable to move!");
						SoundCard.playStatus(attacker.getStatus().getName());
						Sleeper.pause(1700);
						clearContent();
						
						return false;
					} 
					else { 
						return true;
					}					
					
				case "FZN":
					System.out.println(attacker.getName() + " is frozen solid and unable to move!");
					SoundCard.playStatus(attacker.getStatus().getName());
					Sleeper.pause(1700);
					clearContent();
					
					return false;
					
				case "SLP":
					return getEffect(attacker, target);		
					
				case "CNF":
					return getEffect(attacker, target);
					
				default:
					return true;
			}
		}	
		else {
			return true;
		}
	}
	/** END CAN TURN METHOD **/	
	
	/**  GET EFFECT METHOD **/
	private boolean getEffect(Pokedex attacker, Pokedex target) {
		
		String status = attacker.getStatus().getName();		
		
		if (attacker.getStatusLimit() == 0) 
			attacker.setStatusLimit(2 + (int)(Math.random() * ((5 - 2) + 2)));	
		
		if (attacker.getStatusCounter() >= attacker.getStatusLimit()) {
			
			if (status.equals("SLP")) 
				System.out.println(attacker.getName() + " woke up!");				
			else if (status.equals("CNF")) 
				System.out.println(attacker.getName() + " snapped out of confusion!");						
							
			Sleeper.pause(1700);
			
			attacker.setStatusCounter(0); attacker.setStatusLimit(0);
			attacker.setStatus(null);
			
			return true;
		}
		else {
			attacker.setStatusCounter(attacker.getStatusCounter() + 1);
			
			if (status.equals("SLP")) {				
				System.out.println(attacker.getName() + " is fast asleep!");
				SoundCard.playStatus(attacker.getStatus().getName());
				Sleeper.pause(1700);
				clearContent();	
				return false;
			}
			else if (status.equals("CNF")) {				
				System.out.println(attacker.getName() + " is confused!");							
				SoundCard.playStatus(attacker.getStatus().getName());
				Sleeper.pause(1700);
				
				if (isConfused(attacker, target))
					return false;
				else
					return true;				
			}
			return false;
		}
	}
	/**  END GET EFFECT METHOD **/
	
	/** GET TURN METHOD **/
	// returns 1 if pokemon1 goes first, 2 if pokemon2 goes first //
	private int getTurn(Pokedex pokemon1, Pokedex pokemon2, Moves move1, Moves move2) {
		
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
	
	/** CALCULATE CONFUSION DAMAGE DEALT METHOD **/
	// confusion damage reference: https://pokemonlp.fandom.com/wiki/Confusion_(status) //
	private boolean isConfused(Pokedex attacker, Pokedex target) {
		
		int val = 1 + (int)(Math.random() * ((2 - 1) + 1));	
		if (val == 1) {					
			
			double level = attacker.getLevel();
			double power = Moves.CONFUSE.getPower();
			
			double A = attacker.getAttack();
			double D = attacker.getDefense();
					
			// damage formula reference: https://bulbapedia.bulbagarden.net/wiki/Damage
			int damage = (int)((Math.floor(((((Math.floor((2 * level) / 5)) + 2) * power * (A / D)) / 50)) + 2));
		
			System.out.println(attacker.getName() + " hurt itself in confusion!");
			SoundCard.play(1.0);			
			Sleeper.pause(1700);
			clearContent();
			
			int newHP = attacker.getHP() - damage;
			
			if (newHP <= 0) {
				newHP = 0;			
				attacker.setHP(newHP);
				attacker.setAlive(false);
				defeated(target, attacker, damage);				
				return true;
			}
			
			attacker.setHP(newHP);
			
			return true;
		}
		
		return false;
	}
	/** END CALCULATE CONFUSION DAMAGE DEALT METHOD **/
	
	/** START MOVE METHOD **/
	private void startMove(Pokedex attacker, Pokedex target, Moves givenMove) {
				
		// loop through moveset for attacking pokemon
		for (Moves move : attacker.getMoveSet()) {
			
			// if chosen move is found
			if (move.getName().equals(givenMove.getName())) {	
				
				System.out.println(attacker.getName() + " used " + move.getName() + "!");
				
				// decrease move pp
				move.setpp(move.getpp() - 1);
				
				Sleeper.pause(1000);
	            
	            // if attack lands
				if (isHit(attacker, move)) {
															
					// play move sound
					SoundCard.play("//moves//" + move.getName(), true);
					
					if (move.getMType().equals("Status")) {	
						
						if (target.getStatus() == null) {
							target.setStatus(move.getEffect());						
							System.out.println(target.getName() + " is " + target.getStatus().getCondition() + "!");	
							
							Sleeper.pause(1700);
							clearContent();
						}
						else {
							System.out.println(target.getName() + " is already " + target.getStatus().getCondition() + "!");
							
							Sleeper.pause(1700);
							clearContent();
						}
						return;
					}
					else if (move.getMType().equals("Attribute")) {		
						
						if (move.isToSelf()) {
							for (String stat : move.getStats()) 
								attacker.changeStat(stat, move.getLevel());	
						}
						else {
							for (String stat : move.getStats()) 
								target.changeStat(stat, move.getLevel());							
						}
						
						if (move.getLevel() > 0) SoundCard.play("//in-battle//stat-up", true);
						else SoundCard.play("//in-battle//stat-down", true);
						
						Sleeper.pause(1700);						
						clearContent();
						
						return;
					}
					
					// get critical damage (if applicable)
					double crit = isCritical();			
					
					// if critical hit
					if (crit == 1.5) { System.out.println("A critical hit!"); }
									
					// calculate damage dealt
					int damageDealt = calculateDamage(attacker, target, move, crit, false);
					
					if (damageDealt > target.getHP())
						damageDealt = target.getHP();
					
					// no damage dealt
					if (damageDealt == 0) {
						System.out.println("It had no effect!");
						Sleeper.pause(1700);
						clearContent();
					}
					else {
						int health = dealDamage(target, damageDealt);
						
						System.out.println(target.getName() + " took " + damageDealt + " damage!");
						Sleeper.pause(1700);	
						
						// pokemon fainted
						if (health == 0) {
							defeated(attacker, target, damageDealt);
						}
						else {
							if (move.getProbability() != null) {
								
								if (new Random().nextDouble() <= move.getProbability()) {
									target.setStatus(move.getEffect());
									
									System.out.println(target.getName() + " is " + target.getStatus().getCondition() + "!");							
									Sleeper.pause(1700);
								}
							}
						}						
						clearContent();
					}
				}						
				// attack missed
				else {
					System.out.println("The attack missed!");
					Sleeper.pause(2000);
					clearContent();
				}		
			}	
		}
	}
	/** END START MOVE METHOD **/

	/** IS HIT METHOD **/
	private boolean isHit(Pokedex attacker, Moves move) {
				
		// if move never misses, return true
		if (move.getAccuracy() == -1) { return true; }
		
		double accuracy = move.getAccuracy() * attacker.getAccuracy();
		
		Random r = new Random();
		float chance = r.nextFloat();
		
		// chance of missing is accuracy value / 100
		return (chance <= ((float) accuracy / 100)) ? true : false;
	}
	/** END IS HIT METHOD **/
		
	/** CALCULATE DAMAGE DEALT METHOD **/
	private int calculateDamage(Pokedex attacker, Pokedex target, Moves move, double crit, boolean cpu) {
		
		double level = attacker.getLevel();
		
		double power;
		if (move.getPower() == -1) 
			power = level;		
		else 
			power = move.getPower();		
		
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
	private static int dealDamage(Pokedex target, int damage) {		
		
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

	/** STATUS DAMAGE METHOD **/
	// status effects reference: https://pokemon.fandom.com/wiki/Status_Effects //
	private void statusDamage(Pokedex pokemon1, Pokedex pokemon2) {
		
		StatusEffect condition = (Pokedex p) -> {
			
			if (p.getStatus() != null) {				
				
				if (p.getStatus().getName().equals("PSN") || p.getStatus().getName().equals("BRN")) {
					
					int damage = (int) (p.getHP() * 0.16);
					int newHP = p.getHP() - damage;		
					
					if (newHP <= 0) {
						newHP = 0;
						p.setHP(newHP);
						p.setAlive(false);
					}
					
					p.setHP(newHP);			
					
					System.out.println(p.getName() + " is hurt from the " + p.getStatus().getEffect().toLowerCase() + "!");
					SoundCard.playStatus(p.getStatus().getName());
					Sleeper.pause(1700);
					clearContent();	
										
					return damage;
				}							
			}
			return 0;
		};
		
		if (pokemon1.isAlive) {
			int damage = condition.dealDamage(pokemon1);
			
			if (!pokemon1.isAlive)
				defeated(pokemon1, pokemon2, damage);
		}
		
		if (pokemon2.isAlive) {
			int damage = condition.dealDamage(pokemon2);
			
			if (!pokemon2.isAlive)
				defeated(pokemon2, pokemon1, damage);
		}		
	}
	/** END STATUS DAMAGE METHOD **/
	
	private void defeated(Pokedex attacker, Pokedex target, int damageDealt) {
				
		int xp = setWin(attacker, target, damageDealt);	
		
		System.out.println(target.getName() + " fainted!");		
		Sleeper.pause(2000);
		
		System.out.println(attacker.getName() + " gained " + xp + " Exp. Points!");		
		Sleeper.pause(2000);	
	}
	
	/** SET WIN METHOD **/
	private int setWin(Pokedex winner, Pokedex loser, int damageDealt) {
											
		loser.setAlive(false);
						
		int xp = calculateXP(loser); 
		winner.setXP(xp);				
		
		winningPokemon = winner;
		losingPokemon = loser;
		return xp;
	}
	/** END SET WIN METHOD **/
	
	/** CALCULATE XP METHOD **/
	private int calculateXP(Pokedex target) {
				
		// exp formula reference: https://bulbapedia.bulbagarden.net/wiki/Experience
		int exp = (int) (1.5 * 1 * target.getXP() * 1 * target.getLevel() * 1 * 1 * 1) / 7;
		
		return exp;
	}
	/** END CALCULATE XP METHOD **/	
	
	/** GET WINNER METHOD **/
	public Pokedex getWinner() {
		if (winningPokemon != null)
			return winningPokemon;
		else
			return null;
	}
	/** END GET WINNER METHOD **/
	
	/** GET MONEY METHOD **/
	public int getMoney() {		
		int money = 24 * losingPokemon.getLevel();			
		return money;
	}
	/** END GET MONEY METHOD **/
	
	/** CLEAR SCREEN METHOD **/	
	private static void clearContent() {		
		System.out.println(new String(new char[70]).replace("\0", "\r\n"));
	}
	/** END CLEAR SCREEN METHOD **/
}
/*** END BATTLE ENGINE CLASS ***/

@FunctionalInterface
interface StatusEffect {
	public int dealDamage(Pokedex pokemon);
}

@FunctionalInterface
interface Sleep {
	public boolean trackSleep(int counter, int limit);
}