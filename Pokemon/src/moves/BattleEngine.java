package moves;

import java.util.Random;

import pokemon.Pokedex;
import types.TypeEngine;

public class BattleEngine {
	
	private Pokedex target;
	private Pokedex attacker;
	
	public BattleEngine(Pokedex attacker, Pokedex target) {		
		this.attacker = attacker;
		this.target = target;
	}

	public void move(MoveEngine move) {
		
		if (move.getpp() <= 0) {
			System.out.println("This move is out of PP and cannot be used!");
		}
		else {
			
			if (attacker.isAlive) {			
				
				if (target.isAlive) {
					
					for (MoveEngine m : attacker.getMoveSet()) {
						
						if (m.getName().equals(move.getName())) {	
							
							System.out.println(attacker.getName() + " used " + m.getName());
												
							if (isHit(move)) {
								
								move.setpp(move.getpp() - 1);
								
								double critical = isCritical();								
								if (critical == 1.5) { System.out.println("A critical hit!"); }
												
								int damageDealt = calculateDamageDealt(m, critical);								
								int health = dealDamage(damageDealt);
								
								if (health == 0) {
									
									System.out.println(target.getName() + " fainted!");
									target.setAlive(false);
									
									int xp = calculateXP(); 
									attacker.setXP(xp);									
									System.out.println(attacker.getName() + " earned " + xp + " xp!");
								}
								else {
									System.out.println(target.getName() + " took " + damageDealt + " damage!");
								}
							}					
							else {
								System.out.println("The attack missed!");
								move.setpp(move.getpp() - 1);
							}		
						}
					}
				}
				else {
					System.out.println(target.getName() + " has fainted and cannot battle!");
				}
			}
			else {
				System.out.println(attacker.getName() + " has fainted and cannot battle!");
			}			
		}
	}
	
	private boolean isHit(MoveEngine move) {
		
		Random r = new Random();
		float chance = r.nextFloat();
		
		return (chance <= ((float) move.getAccuracy() / 100)) ? true : false;
	}
	
	private int calculateDamageDealt(MoveEngine move, double crit) {
		
		double level = attacker.getLevel();
		double power = move.getPower();
		
		double A = move.getType().equals(attacker.getType()) ? attacker.getSpAttack() : attacker.getAttack();
		double D = move.getType().equals(target.getType()) ? target.getSpDefense() : target.getDefense();
		
		double type = effectiveness(move.getType(), target.getType());
		
		if (type == 2) {
			System.out.println("It's super effective!");
		}
		else if (type == .5) {
			System.out.println("It's not very effective...");
		}
		else if (type == 0) {
			System.out.println("It has no effect!");
			return 0;
		}
						
		int damageDealt = (int) ((((((2 * level) / 5) + 2) * power * (A / D)) / 50) * crit * type);
		
		return damageDealt;
	}
	
	private double isCritical() {
		
		Random r = new Random();
		float chance = r.nextFloat();
		
		return (chance <= ((float) 1 / 255)) ? 1.5 : 1;
	}
	
	private int dealDamage(int damage) {		
		
		int result = target.getHP() - (int)damage;		
		if (result < 0) result = 0;		
		target.setHP(result);
		
		return target.getHP();
	}

	private double effectiveness (TypeEngine move, TypeEngine target) {
		
		double result = 1.0;
		
		for (TypeEngine type : target.getVulnerability()) {			
			if (type.toString().equals(move.toString())) {
				result = type.getStrength();
				return result;
			}			
		}
		
		for (TypeEngine type : target.getResistance()) {			
			if (type.toString().equals(move.toString())) {
				result = type.getStrength();
				return result;
			}			
		}		
		return result;
	}
	
	private int calculateXP() {
		int result = (int) (target.getXP() * target.getLevel() * 1.5) / 7;
		return result;
	}
}