package battle;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import moves.Moves;
import pokemon.Pokedex;
import types.TypeEngine;

/*** BATTLE ENGINE CLASS ***/
public class BattleEngine {

	/** MOVE METHOD **/
	public void move(Pokedex trainer1, Moves move1, Pokedex trainer2, Moves move2) {
					
		// confirm trainer1 can battle
		if (trainer1.isAlive) {			
			
			// confirm trainer2 can battle
			if (trainer2.isAlive) {
				
				// returns true if trainer1 goes first, false if trainer2
				boolean trainer1Turn = getTurn(trainer1, move1, trainer2, move2);
				
				// trainer1 is the attacker
				if (trainer1Turn) {
					startMove(trainer1, move1, trainer2);
					
					// trainer2 becomes attacker if not fainted
					if (trainer2.isAlive) 
						startMove(trainer2, move2, trainer1);
					else {
						System.out.println(trainer2.getName() + " has fainted and cannot battle!");
						sleep(2000);
					}
				}
				// trainer 2 is the attacker
				else {
					startMove(trainer2, move2, trainer1);
					
					// trainer1 becomes attacker if not fainted
					if (trainer1.isAlive) 
						startMove(trainer1, move1, trainer2);
					else {
						System.out.println(trainer1.getName() + " has fainted and cannot battle!");
						sleep(2000);
					}			
				}				
			}
			// if trainer2 can't battle, the fight is over
			else {
				return;
			}
		}
		
		// if trainer1 can't battle
		else {
			System.out.println(trainer1.getName() + " has fainted and cannot battle!");
			sleep(2000);
		}		
	}
	/** END MOVE METHOD **/	
	
	/** GET TURN METHOD **/
	private static boolean getTurn(Pokedex trainer1, Moves move1, Pokedex trainer2, Moves move2) {
		
		// if both moves go first (EX: Quick Attack)
		if (move1.getGoFirst() && move2.getGoFirst()) {			
			// if trainer1 is faster (if equal, trainer1 has advantage)
			if (trainer1.getSpeed() >= trainer2.getSpeed()) { return true; }
			else { return false; }
		}
		// if only move1 goes first (EX: Qukck Attack)
		else if (move1.getGoFirst()) { return true; }
		// if only move2 goes first (EX: Qukck Attack)
		else if (move2.getGoFirst()) { return false; }
		else {
			// if trainer1 is faster (if equal, trainer1 has advantage)
			if (trainer1.getSpeed() >= trainer2.getSpeed()) { return true; }
			else { return false; }
		}
	}
	/** END GET TURN METHOD **/
	
	/** CPU SELECT MOVE METHOD **/
	public static Moves cpuSelectMove(Pokedex attacker, Pokedex target) {
		
		// holds Map of Move and Damage Points
		Map<Moves, Integer> moves = new HashMap<>();
		
		// for each move in attacker's move set
		for (Moves move : attacker.getMoveSet()) {
			
			// find damage value of each move
			int damage = calculateDamage(attacker, move, 1, target, true);
			
			// add move and corresponding damage to list
			moves.put(move, damage);
		}
		
		// find max value in moves list based on value
		return Collections.max(moves.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
	}
	/** END CPU SELECT MOVE METHOD **/
	
	/** START MOVE METHOD **/
	private static void startMove(Pokedex attacker, Moves move, Pokedex target) {
		
		// loop through moveset for attacking pokemon
		for (Moves m : attacker.getMoveSet()) {
			
			// if chosen move is found
			if (m.getName().equals(move.getName())) {	
				
				System.out.println(attacker.getName() + " used " + m.getName() + "!");
				
				// decrease move pp
				move.setpp(move.getpp() - 1);
				
	            sleep(1000);
	            
	            // if attack lands
				if (isHit(move)) {
					
					// play move sound
					soundCard("//moves//" + move.getName());
					
					// get critical damage (if applicable)
					double crit = isCritical();			
					
					// if critical hit
					if (crit == 1.5) { System.out.println("A critical hit!"); }
									
					// calculate damage dealt
					int damageDealt = calculateDamage(attacker, m, crit, target, false);								
					int health = dealDamage(damageDealt, target);
					
					// if pokemon has no hp left
					if (health == 0) {
						setWin(attacker, target, damageDealt);						
					}
					else {
						System.out.println(target.getName() + " took " + damageDealt + " damage!");
						sleep(1700);
					}
				}						
				// move missed pokemon
				else {
					System.out.println("The attack missed!");
					sleep(2000);
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
	private static int calculateDamage(Pokedex attacker, Moves move, double crit, Pokedex target, boolean cpu) {
		
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
		
		soundCard(type);
		
		if (type == 2.0) { System.out.println("It's super effective!"); }
		else if (type == .5) { System.out.println("It's not very effective..."); }
		else if (type == 0) { System.out.println("It has no effect!"); return 0; }
		
		return damageDealt;
	}
	/** END CALCULATE DAMAGE DEALT METHOD **/
	
	/** IS CRITICAL METHOD **/
	private static double isCritical() {	
		
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
	private static void setWin(Pokedex attacker, Pokedex target, int damageDealt) {
		System.out.println(target.getName() + " took " + damageDealt + " damage!");
		sleep(2000);
											
		target.setAlive(false);
		System.out.println(target.getName() + " fainted!");
		sleep(2000);
		
		int exp = calculateXP(target); 
		attacker.setXP(exp);				
		
		System.out.println(attacker.getName() + " earned " + exp + " xp!");
		sleep(2000);
	}
	/** END SET WIN METHOD **/
	
	/** CALCULATE XP METHOD **/
	private static int calculateXP(Pokedex target) {
		int exp = (int) (1.5 * target.getXP() * target.getLevel() * target.getEV()) / 7;
		
		return exp;
	}
	/** END CALCULATE XP METHOD **/	
	
	/** SOUND CARD METHOD **/
	private static void soundCard(String arg) {
		
		try {
			// retrieve sound file based on argument given
			String path = new File("").getAbsolutePath() + "//lib//sounds//" + arg + ".wav";	
	        File sound = new File(path);
	        
            AudioInputStream ais = AudioSystem.getAudioInputStream(sound);
            int duration = (int) ((ais.getFrameLength() + 0.0) / ais.getFormat().getFrameRate()) * 1000; 
            Clip c = AudioSystem.getClip();
            c.open(ais); c.start(); 
            sleep(duration);
        }
        catch (Exception e) { System.out.println(e.getMessage()); }
	}
	/** END SOUND CARD METHOD **/
	
	/** HIT SOUND METHOD **/
	private static void soundCard(double efftiveness) {
		
		String hit = "";
		
		// set hit to corresponding path
		switch (Double.toString(efftiveness)) {
			case "0.5": hit = "//lib//sounds//hit-weak.wav"; break;
			case "1.0": hit = "//lib//sounds//hit-normal.wav"; break;
			case "2.0": hit = "//lib//sounds//hit-super.wav"; break;
			default: return;
		}
		
		String path = new File("").getAbsolutePath() + hit;		
        File sound = new File(path);

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(sound);
            int duration = (int) ((ais.getFrameLength() + 0.0) / ais.getFormat().getFrameRate()) * 1000; 
            Clip c = AudioSystem.getClip();
            c.open(ais); c.start(); 
            sleep(duration);
        }
        catch (Exception e) { System.out.println(e.getMessage()); }
	}
	/** END HIT SOUND METHOD **/
		
	/** SLEEP METHOD **/
	private static void sleep(double time) {
		
		try { Thread.sleep((int) time); } 
		catch (InterruptedException e) { e.printStackTrace(); }
	}
	/** END SLEEP METHOD **/
}
/*** END BATTLE ENGINE CLASS ***/