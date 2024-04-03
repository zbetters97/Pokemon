package battle;

import java.io.File;
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
	public boolean swap = false;
	
	/** GETTERS AND SETTERS **/
	public Pokedex getWinningPokemon() { return winningPokemon; }
	public void setWinningPokemon(Pokedex winningPokemon) { this.winningPokemon = winningPokemon; }
	/** END GETTERS AND SETTERS **/
	
	/** CONSTRUCTOR 
	 * @param trainer 1 pokemon, trainer 2 pokemon
	 **/
	public BattleEngine(Pokedex pokemon1, Pokedex pokemon2) {
		
		// array to hold fighting pokemon
		pokemon = new Pokedex[12];
		
		pokemon[0] = pokemon1;
		pokemon[1] = pokemon2;
	}
	/** END CONSTRUCTOR **/

	/** SWAP POKEMON METHOD
	 * Swap in battle pokemon with new pokemon
	 * @param new pokemon to swap in, int spot
	 **/
	public void swapPokemon(Pokedex newPokemon, int player) {
						
		int slot = 0;
		
		// loop through list of battle pokemon and find open slot
		while (pokemon[slot] != null) {
			
			// if chosen pokemon exists in list...
			if (pokemon[slot].getName() == newPokemon.getName()) {
				
				// re-assign to fighter slot
				pokemon[player] = pokemon[slot];
				
				// and remove original slot
				pokemon[slot] = null;
				
				return;
			}
			slot ++;
			
			// if reached end of list, break loop
			if (slot == pokemon.length)
				break;
		}
		
		// if slot is open, assign new fighter...
		pokemon[slot] = newPokemon;			
		Pokedex temp = pokemon[player];
		
		// and swap fighter to old slot
		pokemon[player] = pokemon[slot];
		pokemon[slot] = temp;
	}
	/** END SWAP POKEMON METHOD **/
	
	/** CPU SELECT MOVE METHOD 
	 * Find which move will cause most damage
	 * @return most powerful move
	 **/
	public Moves cpuSelectMove() {
		
		Moves bestMove;
		
		// holds Map of Move and Damage Points
		Map<Moves, Integer> moves = new HashMap<>();
		
		// for each move in attacker's move set
		for (Moves move : pokemon[1].getMoveSet()) {
			
			if (!move.isToSelf() && move.getpp() != 0) {
				
				// find damage value of each move
				int damage = calculateDamage(1, 0, move, 1.0, true);
				
				// add move and corresponding damage to list
				moves.put(move, damage);	
			}		
		}
		
		// find max value in moves list based on value
		if (!moves.isEmpty()) {
			
			// 33% chance CPU selects random move instead of most powerful move
			int val = 1 + (int)(Math.random() * ((3 - 1) + 1));
			if (val == 1) {
				
				int ranMove = 1 + (int)(Math.random() * (((pokemon[1].getMoveSet().size()) - 1) + 1));	
				ranMove -= 1;
				
				bestMove = pokemon[1].getMoveSet().get(ranMove);
			}
			else
				bestMove = Collections.max(moves.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey(); 	
		}
		else
			bestMove = pokemon[1].getMoveSet().get(0);
		
		return bestMove;
	}
	/** END CPU SELECT MOVE METHOD **/
	
	/** MOVE METHOD 
	 * Find which trainer moves first and initiate turn
	 * @param trainer 1 move, trainer 2 move
	 **/
	public void move(Moves move1, Moves move2) {
		
		// if both pokemon are alive
		if (pokemon[0].isAlive() && pokemon[1].isAlive()) {
			
			int numTurn = 0;			
			
			if (move1 == null && move2 == null)
				numTurn = 3;
			else if (move1 == null)
				numTurn = 4;
			else if (move2 == null)
				numTurn = 5;
			// 1 if pokemon1 moves first, 2 if pokemon2 moves first
			else
				numTurn = getTurn(move1, move2);
			
			if (numTurn == 1) 
				turn(0, 1, move1, move2);	
			else if (numTurn == 2)
				turn(1, 0, move2, move1);	
			else if (numTurn == 4)
				turn(1, 0, move2);
			else if (numTurn == 5)
				turn(0, 1, move1);
		
			// check if either fighter has status damage
			statusDamage();
		}
	}
	/** END MOVE METHOD **/
	
	/** GET TURN METHOD 
	 * Calculate which trainer moves first
	 * @param trainer 1 move, trainer 2 move
	 * @return 1 if trainer 1 moves first, 2 if trainer 2 moves first
	 **/
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
	
	/** TURN METHOD 
	 * Dictate which trainer's turn it is
	 * @param trainer 1 pokemon, trainer 1 move, trainer 2 move
	 **/
	private void turn(int pk1, int pk2, Moves move1, Moves move2) {

		// if attacker can fight
		if (canTurn(pk1)) {
			
			// if move takes 2 turns
			if (move1.getNumTurns() == 2) {
				
				// output delay string
				Sleeper.print(pokemon[pk1].getName() + " used " + move1.getName() + "!", 1200);
				Sleeper.print(move1.getDelay(), 1200);		
								
				// reduce number of turns to wait
				move1.setNumTurns(1);
				
				clearContent();
			}
			// if move takes 2 turns but is ready
			else if (move1.getNumTurns() == 1) {
				
				// reset turns to wait
				move1.setNumTurns(2);
				
				attack(pk1, pk2, move1, move2);
			}
			else 
				attack(pk1, pk2, move1, move2);
		}
		
		// target becomes attacker if battle not over
		if (pokemon[pk1].isAlive() && pokemon[pk2].isAlive()) {
					
			// if target can fight
			if (canTurn(pk2)) {
				
				// if move takes 2 turns
				if (move2.getNumTurns() == 2) {
					
					// output delay string
					Sleeper.print(pokemon[pk2].getName() + " used " + move2.getName() + "!", 1200);
					Sleeper.print(move2.getDelay(), 1200);
					clearContent();
					
					// reduce number of turns to wait
					move2.setNumTurns(1);
				}
				// if move takes 2 turns but is ready
				else if (move2.getNumTurns() == 1) {
					
					// reset turns to wait
					move2.setNumTurns(2);
					
					attack(pk2, pk1, move2, move1);
				}
				else 
					attack(pk2, pk1, move2, move1);
			}
		}
	}
	/** END TURN METHOD **/	
	
	
	private void turn(int pk1, int pk2, Moves move) {
		
		// if target can fight
		if (canTurn(pk1)) {
			
			// if move takes 2 turns
			if (move.getNumTurns() == 2) {
				
				// output delay string
				Sleeper.print(pokemon[pk1].getName() + " used " + move.getName() + "!", 1200);
				Sleeper.print(move.getDelay(), 1200);
				clearContent();
				
				// reduce number of turns to wait
				move.setNumTurns(1);
			}
			// if move takes 2 turns but is ready
			else if (move.getNumTurns() == 1) {
				
				// reset turns to wait
				move.setNumTurns(2);
				
				attack(pk1, pk2, move, null);
			}
			else 
				attack(pk1, pk2, move, null);
		}
	}
	/** END TURN METHOD **/	
	
	

	/** HAS DELAYED MOVE METHOD
	 * Check if a move is waiting and return which player 
	 * @param trainer 1 move, trainer 2 move
	 * @return 3 if both, 1 if player 1, 2 if player 2
	 **/
	public int hasDelayedMove(Moves move1, Moves move2) {		
		
		if (move1 == null || move2 == null)
			return 0;
		
		if (move1.getNumTurns() == 1 && move2.getNumTurns() == 1)
			return 3;
		else if (move1.getNumTurns() == 1)
			return 1;
		else if (move2.getNumTurns() == 1)
			return 2;
		
		return 0;
	}
	/** END DELAYED MOVE METHOD **/	
	
	/** CAN TURN METHOD 
	 * Check status condition to check if pokemon is able to turn 
	 * @param number of attacking pokemon
	 * @return true if can turn, false if can't
	 **/ 
	private boolean canTurn(int atk) {
		
		int val = 0;
		
		if (pokemon[atk].getStatus() != null) {
			
			// check pokemon status
			switch (pokemon[atk].getStatus().getName()) {
			
				case "PRZ":		
					
					val = 1 + (int)(Math.random() * ((4 - 1) + 1));
					if (val == 1) {						
						SoundCard.playStatus(pokemon[atk].getStatus().getName());
						Sleeper.print(pokemon[atk].getName() + " is paralyzed and unable to move!", 1700);
						clearContent();
						
						return false;
					} 
					else 
						return true;			
					
				case "FRZ":
					
					val = 1 + (int)(Math.random() * ((5 - 1) + 1));
					if (val == 1) {
						Sleeper.print(pokemon[atk].getName() + " thawed from the ice!", 1700);
						pokemon[atk].setStatus(null);
						return true;
					}
					else {	
						SoundCard.playStatus(pokemon[atk].getStatus().getName());
						Sleeper.print(pokemon[atk].getName() + " is frozen solid and unable to move!", 1700);
						clearContent();
						
						return false;						
					}
					
				case "SLP":
					return getEffect(atk);		
					
				case "CNF":
					return getEffect(atk);
					
				default:
					return true;
			}
		}	
		else 
			return true;
	}
	/** END CAN TURN METHOD **/	
	
	/**  GET EFFECT METHOD 
	 * Check if pokemon is asleep or confused
	 * @param number of attacking pokemon
	 * @return true if can't turn based on effect (confused or asleep)
	 **/
	private boolean getEffect(int pkm) {
		
		String status = pokemon[pkm].getStatus().getName();		
		
		// if first move under condition, set number of moves until free
		if (pokemon[pkm].getStatusLimit() == 0) 
			pokemon[pkm].setStatusLimit(2 + (int)(Math.random() * ((5 - 2) + 2)));	
		
		// if number of moves under condition hit limit, remove condition
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
				
				if (confusionDamage(pkm)) 
					return false;
				else 
					return true;				
			}
			return false;
		}
	}
	/**  END GET EFFECT METHOD **/
	
	/** CALCULATE CONFUSION DAMAGE DEALT METHOD 
	 * Check if pokemon takes confusion damage
	 * @param number of attacking pokemon
	 * @return true if pokemon hurts itself
	 **/
	private boolean confusionDamage(int atk) {
		
		int trg = (atk == 1) ? 0 : 1;
		
		// 50% chance of hurting self
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
	
	/** START ATTACK METHOD 
	 * Handle selected move, calculate damage, and apply it to target
	 * @param number of attacker, number of target, move of attacker
	 **/
	private void attack(int atk, int trg, Moves move, Moves trgMove) {
		
		// confirm move exists in fighter's moveset
		if (moveIsValid(atk, move)) {
			
			Sleeper.print(pokemon[atk].getName() + " used " + move.getName() + "!", 500);
			SoundCard.play("//moves//" + move.getName(), true);
			
			// decrease move pp
			move.setpp(move.getpp() - 1);	
			
	        // if attack lands
			if (isHit(atk, move, trgMove)) {
												
				if (move.getMType().equals("Status")) {
					
					if (move.getName().equals("Teleport")) {

						Sleeper.print(pokemon[atk].getName() + " teleported away!", 1700);
						clearContent();
						
						Sleeper.print("The battle is over!", 1700);
						System.exit(0);
					}
					
					statusMove(trg, move);
				}
				else if (move.getMType().equals("Attribute")) 	
					attributeMove(atk, trg, move);
				else {						
					// get critical damage (1/255 chance)
					double crit = isCritical();			
									
					// calculate damage to be dealt
					int damage = calculateDamage(atk, trg, move, crit, false);
							
					// no damage dealt
					if (damage == 0) {
						Sleeper.print("It had no effect!", 1700);
						clearContent();
					}
					else {						
						// if critical hit
						if (crit == 1.5) 
							Sleeper.print("A critical hit!", 1700);
						
						Sleeper.print(pokemon[trg].getName() + " took " + damage + " damage!", 1700);
						
						if (move.getName() == "Absorb" || move.getName() == "Giga Drain") {
							
							int gainedHP = (damage / 2);
							
							if (gainedHP > pokemon[atk].getBHP())
								gainedHP = pokemon[atk].getBHP();
							
							pokemon[atk].setHP(gainedHP + pokemon[atk].getHP());	
							Sleeper.print(pokemon[atk].getName() + " absorbed " + gainedHP + " HP!", 1700);
						}
						
						// if damage is fatal
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
				Sleeper.print("The attack missed!", 1700);
				clearContent();
			}				
		}		
	}
	/** END ATTACK METHOD **/
	
	/** START MOVE IS VALID METHOD 
	 * Confirm selected move is a valid option
	 * @param number of attacker, selected move
	 * @return true if valid, false if not
	 **/
	private boolean moveIsValid(int atk, Moves move) {
		
		// loop through fighter's moveset
		for (Moves m : pokemon[atk].getMoveSet()) {
			
			// if chosen move is found
			if (m.getName().equals(move.getName()))
				return true;
		}
		return false;
	}
	/** END MOVE IS VALID METHOD **/
	
	/** STATUS MOVE METHOD 
	 * Adds status effect to pokemon if not already
	 * @param number of target, selected move
	 **/
	private void statusMove(int trg, Moves move) {
		
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
	/** END STATUS MOVE METHOD **/
	
	/** ATTRIBUTE MOVE METHOD 
	 * Raise or lower stat of target or to self
	 * @param number of target, selected move
	 **/
	private void attributeMove(int atk, int trg, Moves move) {
		
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
			SoundCard.play("//battle//stat-up", true);
		// attributes lowered
		else 
			SoundCard.play("//battle//stat-down", true);
		
		Sleeper.pause(1700);						
		clearContent();
	}
	/** END ATTRIBUTE MOVE METHOD **/

	/** IS HIT METHOD 
	 * Calculates if move lands on target
	 * @param number of attacker, selected move
	 * @return true if attack lands, false if not
	 **/
	private boolean isHit(int atk, Moves move, Moves trgMove) {
		
		if (trgMove == null)
			return true;
		
		// if target used delayed move and delayed move protects target		
		if (trgMove.getNumTurns() == 1 && !trgMove.getCanHit())
			return false;
				
		// if move never misses, return true
		if (move.getAccuracy() == -1) { return true; }
		
		double accuracy = move.getAccuracy() * pokemon[atk].getAccuracy();
		
		Random r = new Random();
		float chance = r.nextFloat();
		
		// chance of missing is accuracy value / 100
		return (chance <= ((float) accuracy / 100)) ? true : false;
	}
	/** END IS HIT METHOD **/
	
	/** IS CRITICAL METHOD 
	 * Returns value of critical (1 / 255 chance)
	 * @return critical value
	 **/
	private double isCritical() {	
		
		// 1/255 chance of landing critical hit
		Random r = new Random();		
		return (r.nextFloat() <= ((float) 1 / 255)) ? 1.5 : 1;
	}
	/** END IS CRITICAL METHOD **/
	
	/** CALCULATE DAMAGE DEALT METHOD
	 * Calculates the damage dealt to the attacker 
	 * @param number of attacker, number of target, selected move, critical damage, cpu true/false
	 * @return damage calculated
	 **/
	private int calculateDamage(int atk, int trg, Moves move, double crit, boolean cpu) {
		
		double level = pokemon[atk].getLevel();		
		double power = (move.getPower() == -1) ? level : move.getPower();		
		double A = 1.0, D = 1.0, STAB = 1.0, type = 1.0;

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

		type = effectiveness(trg, move.getType());	

		// damage formula reference: https://bulbapedia.bulbagarden.net/wiki/Damage (GEN IV)
		int damageDealt = (int)((Math.floor(((((Math.floor((2 * level) / 5)) + 2) * 
			power * (A / D)) / 50)) + 2) * crit * STAB * type);

		// keep damage dealt less than or equal to remaining HP
		if (damageDealt > pokemon[trg].getHP())
			damageDealt = pokemon[trg].getHP();
		
		// don't play sound if cpu is calling method
		if (!cpu) 
			SoundCard.playHit(type);
		
		return damageDealt;
	}
	/** END CALCULATE DAMAGE DEALT METHOD **/	
	
	/** EFFECTIVENESS METHOD 
	 * Calculates effectiveness of moves type against target
	 * @param number of target, type of selected move
	 * @return effectiveness
	 **/
	private double effectiveness(int trg, TypeEngine type) {
		
		// default value
		double effect = 1.0;
		
		// if target is single types
		if (pokemon[trg].getTypes() == null) {
			
			// if vulnerable, retrieve and return vulnerable value
			for (TypeEngine vulnType : pokemon[trg].getType().getVulnerability()) {		
				if (vulnType.toString().equals(type.toString())) {
					effect = vulnType.getStrength();
					return effect;
				}
			}			
			// if resistant, retrieve and return resistance value
			for (TypeEngine resType : pokemon[trg].getType().getResistance()) {			
				if (resType.toString().equals(type.toString())) {
					effect = resType.getStrength();
					return effect;
				}			
			}		
		}
		// if target is multi type
		else {
			
			// for each type in target
			for (TypeEngine targetType : pokemon[trg].getTypes()) {		
				
				// for each vulnerability
				vulnerabilityLoop:
				for (TypeEngine vulnType : targetType.getVulnerability()) {		
					
					// if found, multiply by effect and move to next loop
					if (vulnType.toString().equals(type.toString())) {						
						effect *= vulnType.getStrength();						
						break vulnerabilityLoop;
					}
				}	
				
				// for each resistance
				resistanceLoop:
				for (TypeEngine resType : targetType.getResistance()) {		
					
					// if found, multiply by effect and move to next loop
					if (resType.toString().equals(type.toString())) {
						effect *= resType.getStrength();
						break resistanceLoop;
					}
				}
			}			
			// vulnerable and resistant cancel out
			if (effect == 0.75)	
				effect = 1;
		}			
						
		return effect;
	}
	/** END EFFECTIVENESS METHOD **/
	
	/** DEAL DAMAGE METHOD 
	 * Applies damage dealt to target's total HP
	 * @param number of target, damage dealt
	 **/
	private void dealDamage(int trg, int damage) {		
		
		// subtract damage dealt from total hp
		int result = pokemon[trg].getHP() - (int)damage;		
		
		// set HP to 0 if below 0
		if (result < 0)
			result = 0;
		
		pokemon[trg].setHP(result);
	}
	/** END DEAL DAMAGE METHOD **/

	/** STATUS DAMAGE METHOD 
	 * Check if either pokemon has status condition and apply damage
	 **/
	private void statusDamage() {
		
		StatusEffect condition = (Pokedex p) -> {
			
			if (p.getStatus() != null) {				
				
				if (p.getStatus().getName().equals("PSN") || p.getStatus().getName().equals("BRN")) {
					
					// status effects reference: https://pokemon.fandom.com/wiki/Status_Effects			
					int damage = (int) (p.getHP() * 0.16);
					int newHP = p.getHP() - damage;		
					
					if (newHP <= 0) {
						newHP = 0;
						p.setAlive(false);
					}
					
					p.setHP(newHP);			
					
					SoundCard.playStatus(p.getStatus().getName());
					Sleeper.print(p.getName() + " is hurt from the " + 
						p.getStatus().getEffect().toLowerCase() + "!", 1700);
					clearContent();	
										
					return damage;
				}							
			}
			return 0;
		};
		
		if (pokemon[0].isAlive()) {
			int damage = condition.dealDamage(pokemon[0]);

			if (!pokemon[0].isAlive())
				defeated(1, 0, damage);
		}
		
		if (pokemon[1].isAlive()) {
			int damage = condition.dealDamage(pokemon[1]);

			if (!pokemon[1].isAlive())
				defeated(0, 1, damage);
		}		
	}
	/** END STATUS DAMAGE METHOD **/
	
	/** POKEMON DEFEATED METHOD 
	 * Assigns xp gained and announces winner and loser
	 * @param winning pokemon, losing pokemon, damage dealt
	 **/
	private void defeated(int win, int lsr, int damageDealt) {
				
		pokemon[lsr].setAlive(false);
		this.setWinningPokemon(pokemon[win]);
		
		int xp = calculateXP(lsr);
		
		clearContent();
		
		// Pokemon faint cries from: https://www.youtube.com/watch?v=XSlE9IF_S84
		SoundCard.play("pokedex" + File.separator + "faint" + File.separator + pokemon[lsr].getName());		
		Sleeper.print(pokemon[lsr].getName() + " fainted!", 1700);			
		Sleeper.print(pokemon[win].getName() + " gained " + xp + " Exp. Points!", 1700);
		
		return;
	}
	/** END DEFEATED METHOD **/
	
	/** CALCULATE XP METHOD 
	 * Calculates xp earned based on level and existing xp
	 * @param number of loser
	 * @return gained xp
	 **/
	private int calculateXP(int lsr) {
		
		// exp formula reference (GEN I-IV): https://bulbapedia.bulbagarden.net/wiki/Experience		
		int exp = (int) (((( pokemon[lsr].getXP() * pokemon[lsr].getLevel() ) / 7)) * 1.5);		
		return exp;
	}
	/** END CALCULATE XP METHOD **/
	
	/** GET WINNER METHOD 
	 * Asks if a winner has been decided
	 * @return true if winner exists, false if not
	 **/
	public boolean hasWinner() {
		if (winningPokemon != null) return true;
		else return false;
	}
	/** END GET WINNER METHOD **/	
	
	/** GET MONEY METHOD 
	 * Calculates money earned (12 * level of loser)
	 * @param number of loser
	 * @return money earned
	 **/
	public int getMoney(int lsr) {		
		int money = 12 * pokemon[lsr].getLevel();		
		return money;
	}
	/** END GET MONEY METHOD **/

	/** CLEAR SCREEN METHOD **/	
	private static void clearContent() {		
		System.out.println(new String(new char[60]).replace("\0", "\r\n"));
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