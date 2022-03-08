package battle;

import java.io.File;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import moves.MoveEngine;
import pokemon.Pokedex;
import types.TypeEngine;

/*** BATTLE ENGINE CLASS ***/
public class OnePlayerBattleEngine {
		
	/** CONSTRUCTOR **/
	public OnePlayerBattleEngine() {		
	}
	/** END CONSTRUCTOR**/

	/** MOVE METHOD **/
	public void move(Pokedex attacker, Pokedex target, MoveEngine move) {
		
		// if move is out of pp
		if (move.getpp() <= 0) {
			System.out.println("This move is out of PP and cannot be used!");
			sleep(1000);
		}
		else {					
			
			// confirm pokemon can battle
			if (attacker.isAlive) {		
				
				// confirm opponent can battle
				if (target.isAlive) {
					
					// loop through moveset for attacking pokemon
					for (MoveEngine m : attacker.getMoveSet()) {
						
						// if chosen move is found
						if (m.getName().equals(move.getName())) {	
							
							System.out.println(attacker.getName() + " used " + m.getName() + "!");
							
							// decrease move pp
							move.setpp(move.getpp() - 1);
							
				            sleep(1000);
				            
				            // if attack lands
							if (isHit(move)) {
								
								// play move sound
								soundCard("\\moves\\" + move.getName());
								
								sleep(1000);
								
								double crit = isCritical();			
								
								// if critical hit
								if (crit == 1.5) { System.out.println("A critical hit!"); }
												
								// calculate damage dealt
								int damageDealt = calculateDamageDealt(attacker, m, crit, target);								
								int health = dealDamage(damageDealt, target);
								
								// if pokemon has no hp left
								if (health == 0) {
									
									System.out.println(target.getName() + " took " + damageDealt + " damage!");
									sleep(2000);
																		
									target.setAlive(false);
									System.out.println(target.getName() + " fainted!");
									sleep(2000);
									
									int xp = calculateXP(target); 
									attacker.setXP(xp);									
									System.out.println(attacker.getName() + " earned " + xp + " xp!");
									sleep(2000);
								}
								else {
									System.out.println(target.getName() + " took " + damageDealt + " damage!");
									sleep(2000);
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
				else {
					System.out.println(target.getName() + " has fainted and cannot battle!");
					sleep(2000);
				}
			}
			else {
				System.out.println(attacker.getName() + " has fainted and cannot battle!");
				sleep(2000);
			}			
		}
	}
	/** END MOVE METHOD **/

	/** IS HIT METHOD **/
	private static boolean isHit(MoveEngine move) {
		
		// if move never misses, return true
		if (move.getAccuracy() == -1) { return true; }
		
		Random r = new Random();
		float chance = r.nextFloat();
		
		return (chance <= ((float) move.getAccuracy() / 100)) ? true : false;
	}
	/** END IS HIT METHOD **/
	
	/** CALCULATE DAMAGE DEALT METHOD **/
	private static int calculateDamageDealt(Pokedex attacker, MoveEngine move, double crit, Pokedex target) {
		
		double level = attacker.getLevel();
		double power = move.getPower();
		
		// if special move, get special attack/defense attribute
		double A = move.getType().equals(attacker.getType()) ? attacker.getSpAttack() : attacker.getAttack();
		double D = move.getType().equals(target.getType()) ? target.getSpDefense() : target.getDefense();
		
		// not effective, super effective, or regular effective
		double type = effectiveness(move.getType(), target.getType());		
		hitSound(type);
		
		if (type == 2.0) { System.out.println("It's super effective!"); }
		else if (type == .5) { System.out.println("It's not very effective..."); }
		else if (type == 0) { System.out.println("It has no effect!"); return 0; }
						
		// damage formula reference: https://bulbapedia.bulbagarden.net/wiki/Damage
		int damageDealt = (int) ((((((2 * level) / 5) + 2) * power * (A / D)) / 50) * crit * type);
		
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
	private static double effectiveness (TypeEngine move, TypeEngine target) {
		
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
	
	/** CALCULATE XP METHOD **/
	private static int calculateXP(Pokedex target) {
		int result = (int) (target.getXP() * target.getLevel() * 1.5) / 7;
		return result;
	}
	/** END CALCULATE XP METHOD **/
	
	/** HIT SOUND METHOD **/
	private static void hitSound(double efftiveness) {
		
		String hit = "";
		
		// set hit to corresponding path
		switch (Double.toString(efftiveness)) {
			case "0.5": hit = "\\lib\\sounds\\hit-weak.wav"; break;
			case "1.0": hit = "\\lib\\sounds\\hit-normal.wav"; break;
			case "2.0": hit = "\\lib\\sounds\\hit-super.wav"; break;
			default: return;
		}
		
		String path = new File("").getAbsolutePath() + hit;		
        File sound = new File(path);

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(sound);
            Clip c = AudioSystem.getClip();
            c.open(ais); c.start(); 
        }
        catch (Exception e) { System.out.println(e.getMessage()); }
	}
	/** END HIT SOUND METHOD **/
	
	/** SOUND CARD METHOD **/
	private static void soundCard(String arg) {
		
		try {
			// retrieve sound file based on argument given
			String path = new File("").getAbsolutePath() + "\\lib\\sounds\\" + arg + ".wav";	
	        File sound = new File(path);
	        
            AudioInputStream ais = AudioSystem.getAudioInputStream(sound);
            Clip c = AudioSystem.getClip();
            c.open(ais); c.start(); 
        }
        catch (Exception e) { System.out.println(e.getMessage()); }
	}
	/** END SOUND CARD METHOD **/
	
	/** SLEEP METHOD **/
	private void sleep(int time) {
		try { Thread.sleep(time); } 
		catch (InterruptedException e) { e.printStackTrace(); }
	}
	/** END SLEEP METHOD **/
}
/*** END BATTLE ENGINE CLASS ***/