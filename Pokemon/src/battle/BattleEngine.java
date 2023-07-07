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
	
	private Pokedex winningPokemon;
	private Pokedex[] pokemon;
	
	/** GETTERS AND SETTERS **/
	public Pokedex getWinningPokemon() { return winningPokemon; }
	public void setWinningPokemon(Pokedex winningPokemon) { this.winningPokemon = winningPokemon; }
	/** END GETTERS AND SETTERS **/
	
	/** CONSTRUCTOR **/
	public BattleEngine(Pokedex pokemon1, Pokedex pokemon2) {
		
		// array to hold fighting pokemon
		pokemon = new Pokedex[2];
		
		pokemon[0] = pokemon1;
		pokemon[1] = pokemon2;
	}
	/** END CONSTRUCTOR **/

	/** SWAP POKEMON METHOD **/
	public void swapPokemon(Pokedex newPokemon, int spot) {
		pokemon[spot] = newPokemon;
	}
	/** END SWAP POKEMON METHOD **/
	
	/** MOVE METHOD **/
	public void move(Moves move1, Moves move2) {

		// if both pokemon are alive
		if (pokemon[0].isAlive() && pokemon[1].isAlive()) {
				
			// 1 if pokemon1 moves first, 2 if pokemon2 moves first
			int numTurn = getTurn(move1, move2);
			
			if (numTurn == 1) 
				turn(0, move1, move2);	
			else if (numTurn == 2) 			
				turn(1, move2, move1);	
												
			// check if either fighter has status damage
			statusDamage(0, 1);
		}
	}
	/** END MOVE METHOD **/
	
	private void turn(int pokemon1, Moves move1, Moves move2) {
		
		int pokemon2 = (pokemon1 == 0) ? 1 : 0;
		
		// if attacker can fight
		if (canTurn(pokemon1)) 
			attack(pokemon1, pokemon2, move1);
		
		// target becomes attacker if battle not over
		if (pokemon[pokemon1].isAlive() && pokemon[pokemon2].isAlive()) {
			
			// if target can fight
			if (canTurn(pokemon2)) 
				attack(pokemon2, pokemon1, move2);			
		}		
	}
	
	/** CPU SELECT MOVE METHOD **/
	// returns move with max damage //
	public Moves cpuSelectMove() {
		
		Moves bestMove;
		
		// holds Map of Move and Damage Points
		Map<Moves, Integer> moves = new HashMap<>();
		
		// for each move in attacker's move set
		for (Moves move : pokemon[1].getMoveSet()) {
			
			if (!move.isToSelf()) {				
				// find damage value of each move
				int damage = calculateDamage(1, 0, move, 1.0, true);
				
				// add move and corresponding damage to list
				moves.put(move, damage);	
			}		
		}
		
		if (!moves.isEmpty()) {
			// find max value in moves list based on value
			bestMove = Collections.max(moves.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey(); 	
		}	
		else {
			bestMove = pokemon[1].getMoveSet().get(0);
		}
				
		return bestMove;
	}
	/** END CPU SELECT MOVE METHOD **/
	
	/** GET TURN METHOD **/
	// returns 1 if pokemon1 goes first, returns 2 if pokemon2 goes first //
	private int getTurn(Moves move1, Moves move2) {
		
		// if both moves go first (EX: Quick Attack)
		if (move1.getGoFirst() && move2.getGoFirst()) {			
			// if pokemon1 is faster (pokemon1 has advantage if equal)
			if (pokemon[0].getSpeed() >= pokemon[1].getSpeed()) 
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
			if (pokemon[0].getSpeed() >= pokemon[1].getSpeed())
				return 1;
			else 
				return 2;
		}
	}
	/** END GET TURN METHOD **/
	
	/** CAN TURN METHOD **/ 
	// returns false if given pokemon cannot move due to status //
	private boolean canTurn(int atk) {
		
		if (pokemon[atk].getStatus() != null) {
			
			// check pokemon status
			switch (pokemon[atk].getStatus().getName()) {
			
				case "PRZ":		
					
					int val = 1 + (int)(Math.random() * ((4 - 1) + 1));
					if (val == 1) {						
						SoundCard.playStatus(pokemon[atk].getStatus().getName());
						Sleeper.print(pokemon[atk].getName() + " is paralyzed and unable to move!", 1700);
						clearContent();
						
						return false;
					} 
					else { 
						return true;
					}					
					
				case "FZN":
					SoundCard.playStatus(pokemon[atk].getStatus().getName());
					Sleeper.print(pokemon[atk].getName() + " is frozen solid and unable to move!", 1700);
					clearContent();
					
					return false;
					
				case "SLP":
					return getEffect(atk);		
					
				case "CNF":
					return getEffect(atk);
					
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
	private boolean getEffect(int pkm) {
		
		String status = pokemon[pkm].getStatus().getName();		
		
		if (pokemon[pkm].getStatusLimit() == 0) 
			pokemon[pkm].setStatusLimit(2 + (int)(Math.random() * ((5 - 2) + 2)));	
		
		if (pokemon[pkm].getStatusCounter() >= pokemon[pkm].getStatusLimit()) {
			
			pokemon[pkm].setStatusCounter(0); pokemon[pkm].setStatusLimit(0);
			pokemon[pkm].setStatus(null);
			
			if (status.equals("SLP")) 
				Sleeper.print(pokemon[pkm].getName() + " woke up!", 1700);				
			else if (status.equals("CNF")) 
				Sleeper.print(pokemon[pkm].getName() + " snapped out of confusion!", 1700);						
		
			return true;
		}
		else {
			pokemon[pkm].setStatusCounter(pokemon[pkm].getStatusCounter() + 1);
			
			if (status.equals("SLP")) {				
				SoundCard.playStatus(pokemon[pkm].getStatus().getName());
				Sleeper.print(pokemon[pkm].getName() + " is fast asleep!", 1700);
				clearContent();	
				
				return false;
			}
			else if (status.equals("CNF")) {				
				SoundCard.playStatus(pokemon[pkm].getStatus().getName());
				Sleeper.print(pokemon[pkm].getName() + " is confused!", 1700);
				clearContent();
				
				if (confusionDamage(pkm)) return false;
				else return true;				
			}
			return false;
		}
	}
	/**  END GET EFFECT METHOD **/
	
	/** CALCULATE CONFUSION DAMAGE DEALT METHOD **/
	private boolean confusionDamage(int atk) {
		
		int trg = (atk == 1) ? 0 : 1;
		
		int val = 1 + (int)(Math.random() * ((2 - 1) + 1));	
		if (val == 1) {					
			
			double level = pokemon[atk].getLevel();
			double power = Moves.CONFUSE.getPower();
			
			double A = pokemon[atk].getAttack();
			double D = pokemon[atk].getDefense();
					
			// confusion damage reference: https://pokemonlp.fandom.com/wiki/Confusion_(status)
			int damage = (int)((Math.floor(((((Math.floor((2 * level) / 5)) + 2) * power * (A / D)) / 50)) + 2));
		
			SoundCard.playHit(1.0);	
			Sleeper.print(pokemon[atk].getName() + " hurt itself in confusion!", 1700);
			clearContent();
			
			int hp = pokemon[atk].getHP() - damage;
			
			if (hp <= 0) {
				hp = 0;			
				pokemon[atk].setHP(hp);
				pokemon[atk].setAlive(false);
				defeated(trg, atk, damage);				
				return true;
			}
			else {
				pokemon[atk].setHP(hp);				
				return true;	
			}
		}
		else
			return false;
	}
	/** END CALCULATE CONFUSION DAMAGE DEALT METHOD **/	
	
	/** START MOVE METHOD **/
	private void attack(int atk, int trg, Moves slcMove) {
				
		Moves move = null;
		
		// loop through fighter's moveset
		for (Moves m : pokemon[atk].getMoveSet()) {
			
			// if chosen move is found
			if (m.getName().equals(slcMove.getName())) {
				
				// assign it to move
				move = m;
				break;
			}
		}
		
		if (move != null) {
			
			// decrease move pp
			move.setpp(move.getpp() - 1);
			
			Sleeper.print(pokemon[atk].getName() + " used " + move.getName() + "!", 500);
			SoundCard.play("//moves//" + move.getName(), true);
				        
	        // if attack lands
			if (isHit(atk, move)) {
												
				if (move.getMType().equals("Status")) {	
					
					// if pokemon does not already have status affect
					if (pokemon[trg].getStatus() == null) {
						
						pokemon[trg].setStatus(move.getEffect());						
						Sleeper.print(pokemon[trg].getName() + " is " + 
								pokemon[trg].getStatus().getCondition() + "!", 1700);
						clearContent();
					}
					// pokemon already has status affect
					else {
						Sleeper.print(pokemon[trg].getName() + " is already " + 
								pokemon[trg].getStatus().getCondition() + "!", 1700);
						clearContent();
					}
				}
				else if (move.getMType().equals("Attribute")) {		
					
					// if move changes self attributes
					if (move.isToSelf()) {
						
						// loop through each specified attribute to be changed
						for (String stat : move.getStats()) 
							pokemon[atk].changeStat(stat, move.getLevel());	
					}
					// if move changes target attributes
					else {
						
						// loop through each specified attribute to be changed
						for (String stat : move.getStats()) 
							pokemon[trg].changeStat(stat, move.getLevel());							
					}
					
					// attributes raised
					if (move.getLevel() > 0) 
						SoundCard.play("//in-battle//stat-up", true);
					// attributes lowered
					else 
						SoundCard.play("//in-battle//stat-down", true);
					
					Sleeper.pause(1700);						
					clearContent();
				}
				// move is fighting
				else {
					// get critical damage (1/255 chance)
					double crit = isCritical();			
					
					// if critical hit
					if (crit == 1.5) 
						Sleeper.print("A critical hit!");
									
					// calculate damage to be dealt
					int damage = calculateDamage(atk, trg, move, crit, false);
							
					// no damage dealt
					if (damage == 0) {
						Sleeper.print("It had no effect!", 1700);
						clearContent();
					}
					else {
						//dropHealth(pokemon[atk], pokemon[trg], damage, move);
						
						Sleeper.print(pokemon[trg].getName() + " took " + damage + " damage!", 1500);
						
						// damage is fatal
						if (damage >= pokemon[trg].getHP()) {
							dealDamage(trg, damage);
							defeated(atk, trg, damage);	
						}							
						else {
							dealDamage(trg, damage);
							
							// move causes status effect
							if (move.getProbability() != null) {
								
								// chance for status effect
								if (new Random().nextDouble() <= move.getProbability()) {
									pokemon[trg].setStatus(move.getEffect());
									
									Sleeper.print(pokemon[trg].getName() + " is " + 
											pokemon[trg].getStatus().getCondition() + "!", 1700);
								}							
							}						
							clearContent();
						}
					}	
				}				
			}
			// attack missed
			else {
				Sleeper.print("The attack missed!", 2000);
				clearContent();
			}				
		}		
	}
	/** END START MOVE METHOD **/

	/** IS HIT METHOD **/
	private boolean isHit(int atk, Moves move) {
				
		// if move never misses, return true
		if (move.getAccuracy() == -1) { return true; }
		
		double accuracy = move.getAccuracy() * pokemon[atk].getAccuracy();
		
		Random r = new Random();
		float chance = r.nextFloat();
		
		// chance of missing is accuracy value / 100
		return (chance <= ((float) accuracy / 100)) ? true : false;
	}
	/** END IS HIT METHOD **/
	
	/** IS CRITICAL METHOD **/
	private double isCritical() {	
		
		// 1/255 chance of landing critical hit
		Random r = new Random();		
		return (r.nextFloat() <= ((float) 1 / 255)) ? 1.5 : 1;
	}
	/** END IS CRITICAL METHOD **/
	
	/** EFFECTIVENESS METHOD **/
	private static double effectiveness(Pokedex target, TypeEngine move) {
		
		// default value
		double effect = 1.0;
		
		// if target has multiple types
		if (target.getTypes() != null) {
			
			// for each type in target
			for (TypeEngine targetType : target.getTypes()) {		
				
				// for each vulnerability
				vulnerabilityLoop:
				for (TypeEngine vulnType : targetType.getVulnerability()) {		
					
					// if found, multiply by effect and move to next loop
					if (vulnType.toString().equals(move.toString())) {						
						effect *= vulnType.getStrength();						
						break vulnerabilityLoop;
					}
				}
				
				// for each resistance
				resistanceLoop:
				for (TypeEngine resType : targetType.getResistance()) {		
					
					// if found, multiply by effect and move to next loop
					if (resType.toString().equals(move.toString())) {
						effect *= resType.getStrength();
						break resistanceLoop;
					}
				}
			}			
			//  vulnerable and resistant cancel out
			if (effect == 0.75)	effect = 1;
		}
		// if target is single type
		else {
			
			// if vulnerable, retrieve and return vulnerable value
			for (TypeEngine vulnType : target.getType().getVulnerability()) {		
				if (vulnType.toString().equals(move.toString())) {
					effect = vulnType.getStrength();
					return effect;
				}
			}			
			// if resistant, retrieve and return resistance value
			for (TypeEngine resType : target.getType().getResistance()) {			
				if (resType.toString().equals(move.toString())) {
					effect = resType.getStrength();
					return effect;
				}			
			}		
		}			
						
		return effect;
	}
	/** END EFFECTIVENESS METHOD **/
	
	/** CALCULATE DAMAGE DEALT METHOD **/
	private int calculateDamage(int atk, int trg, Moves move, double crit, boolean cpu) {
		
		double level = pokemon[atk].getLevel();
		
		double power;
		if (move.getPower() == -1) 
			power = level;		
		else 
			power = move.getPower();		
		
		double A = 1.0; double D = 1.0; 
		double STAB = 1.0;
		double type = 1.0;

		if (move.getMType().equals("Special")) {
			A = pokemon[atk].getSpAttack();
			D = pokemon[trg].getSpDefense();
		}
		else if (move.getMType().equals("Physical")) {
			A = pokemon[atk].getAttack();
			D = pokemon[trg].getDefense();
		}
		
		// if attacker has more than 1 type
		if (pokemon[atk].getTypes() != null) {	
			
			// cycle through each type of attacker
			for (TypeEngine t : pokemon[atk].getTypes()) {
				
				// if same type move
				if (move.getType() == t) {
					STAB = 1.5;
					break;
				}
			}
		}
		else
			STAB = move.getType() == pokemon[atk].getType() ? 1.5 : 1.0;

		type = effectiveness(pokemon[trg], move.getType());	

		// damage formula reference: https://bulbapedia.bulbagarden.net/wiki/Damage (GEN IV)
		int damageDealt = (int)((Math.floor(((((Math.floor((2 * level) / 5)) + 2) * power * (A / D)) / 50)) + 2) * crit * STAB * type);

		// keep damage dealt less than or equal to remaining HP
		if (damageDealt > pokemon[trg].getHP())
			damageDealt = pokemon[trg].getHP();
		
		// don't play sound if cpu is calling method
		if (cpu) return damageDealt;
		
		SoundCard.playHit(type);
		
		return damageDealt;
	}
	/** END CALCULATE DAMAGE DEALT METHOD **/	
	
	/** DEAL DAMAGE METHOD **/
	private void dealDamage(int trg, int damage) {		
		
		// subtract damage dealt from total hp
		int result = pokemon[trg].getHP() - (int)damage;		
		
		// set HP to 0 if below 0
		if (result < 0)
			result = 0;
		
		pokemon[trg].setHP(result);
	}
	/** END DEAL DAMAGE METHOD **/

	/** STATUS DAMAGE METHOD **/
	private void statusDamage(int pk1, int pk2) {
		
		StatusEffect condition = (Pokedex p) -> {
			
			if (p.getStatus() != null) {				
				
				if (p.getStatus().getName().equals("PSN") || p.getStatus().getName().equals("BRN")) {
					
					// status effects reference: https://pokemon.fandom.com/wiki/Status_Effects			
					int damage = (int) (p.getHP() * 0.16);
					int newHP = p.getHP() - damage;		
					
					if (newHP <= 0) {
						newHP = 0;
						p.setHP(newHP);
						p.setAlive(false);
					}
					
					p.setHP(newHP);			
					
					SoundCard.playStatus(p.getStatus().getName());
					Sleeper.print(p.getName() + " is hurt from the " + p.getStatus().getEffect().toLowerCase() + "!", 1700);
					clearContent();	
										
					return damage;
				}							
			}
			return 0;
		};
		
		if (pokemon[pk1].isAlive()) {
			int damage = condition.dealDamage(pokemon[pk1]);
			
			if (!pokemon[pk1].isAlive())
				defeated(pk2, pk1, damage);
		}
		
		if (pokemon[pk2].isAlive()) {
			int damage = condition.dealDamage(pokemon[pk2]);
			
			if (!pokemon[pk2].isAlive())
				defeated(pk1, pk2, damage);
		}		
	}
	/** END STATUS DAMAGE METHOD **/
	
	/** POKEMON DEFEATED METHOD **/
	private void defeated(int win, int lsr, int damageDealt) {
				
		int xp = setWinGetXP(win, lsr, damageDealt);
		
		clearContent();
		
		Sleeper.print(pokemon[lsr].getName() + " fainted!", 2000);		
		Sleeper.print(pokemon[win].getName() + " gained " + xp + " Exp. Points!", 2000);
		
		return;
	}
	/** END DEFEATED METHOD **/
	
	/** SET WIN METHOD **/
	private int setWinGetXP(int win, int lsr, int damageDealt) {
											
		pokemon[lsr].setAlive(false);
						
		int xp = calculateXP(lsr); 
		pokemon[win].setXP(xp);				
		
		this.setWinningPokemon(pokemon[win]);
		return xp;
	}
	/** END SET WIN METHOD **/
	
	/** CALCULATE XP METHOD **/
	private int calculateXP(int trg) {
				
		// exp formula reference (GEN I-IV): https://bulbapedia.bulbagarden.net/wiki/Experience		
		int exp = (int) (((( pokemon[trg].getXP() * pokemon[trg].getLevel() ) / 7)) * ( 1 / 1 ) * 1 * 1.5 * 1);
		return exp;
	}
	/** END CALCULATE XP METHOD **/	
	
	/** GET WINNER METHOD **/
	public boolean hasWinner() {
		if (winningPokemon != null)
			return true;
		else
			return false;
	}
	/** END GET WINNER METHOD **/
	
	/** GET MONEY METHOD **/
	public int getMoney(int lsr) {		
		int money = 12 * pokemon[lsr].getLevel();		
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