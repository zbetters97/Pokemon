package pokemon;

import java.util.ArrayList;
import java.util.Arrays;

import moves.MoveEngine;
import types.TypeEngine;

public class Pokemon {

	private String name;
	protected TypeEngine type;
		
	public static TypeEngine normal, fire, water, electric, rock;
	
	private int level, xp, hp, speed, attack, defense, spAttack, spDefense, evLevel;
	
	public boolean isAlive;
	
	private ArrayList<MoveEngine> moveSet;
	private ArrayList<TypeEngine> types;
	
	public Pokemon (String name, TypeEngine type, int level, int hp, int speed, 
			int attack, int defense, int spAttack, int spDefense, int evLevel) {
				
		this.name = name; this.type = type; this.level = level;	this.xp = 1; this.hp = hp; 
		this.speed = speed; this.attack = attack; this.defense = defense; 
		this.spAttack = spAttack; this.spDefense = spDefense; this.evLevel = evLevel; 
		
		this.isAlive = true;	
		
		moveSet = new ArrayList<>();
		
		types = new ArrayList<>();
		types.addAll(Arrays.asList(normal, fire, water, electric, rock));
	}
	
	static {

		normal = new TypeEngine(TypeEngine.Type.NORMAL, 0.5); 
		
		fire = new TypeEngine(TypeEngine.Type.FIRE, 0.5); 
		fire.resistantTo(TypeEngine.Type.FIRE, 0.5); fire.vulnerableTo(TypeEngine.Type.WATER, 2.0);
		fire.vulnerableTo(TypeEngine.Type.ROCK, 2.0);
		
		water = new TypeEngine(TypeEngine.Type.WATER, 0.5); 
		water.resistantTo(TypeEngine.Type.WATER, 0.5); water.resistantTo(TypeEngine.Type.FIRE, 0.5);
		water.vulnerableTo(TypeEngine.Type.ELECTRIC, 2.0);
		
		electric = new TypeEngine(TypeEngine.Type.ELECTRIC, 0.5); 
		electric.resistantTo(TypeEngine.Type.ELECTRIC, 0.5);
		electric.vulnerableTo(TypeEngine.Type.ROCK, 2.0);
		
		rock = new TypeEngine(TypeEngine.Type.ROCK, 0.5); 
		rock.resistantTo(TypeEngine.Type.ROCK, 0.5); rock.resistantTo(TypeEngine.Type.FIRE, 0.5);
		rock.vulnerableTo(TypeEngine.Type.WATER, 2.0);
	}
	
	public TypeEngine getType(String type) {
		
		for (TypeEngine t : types) {
			
			if (t.toString().equals(type)) {
				return t;
			}
		}
		return null;
	}

	public void listMoves() {		
		
		System.out.println("MOVESET FOR " + this.name + ":\n");
		
		for (MoveEngine move : moveSet) {
			System.out.println(move.getName() + " : (TYPE: " + move.getType()  + "), (PP: " + move.getpp() + 
					"), (PWR: " + move.getPower() + ")"	+ ", (ACC: " + move.getAccuracy() + ")");
		}
	}
	
	public boolean addMove(MoveEngine move) {
		
		if (this.getMoveSet().size() == 4) {
			return false;
		}
		else {
			this.getMoveSet().add(move);
			return true;
		}
	}
	
	public String evolve() {
		
		if (canEvolve(this.getLevel())) {
			System.out.println("What? Your pokemon is evolving!");
		}
		else {
			System.out.println("MISSINGNO!");
		}
		
		return null;
	}
	
	public boolean canEvolve(int level) {
		
		if (level == this.getEvLevel()) {
			if (level <= 100) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	/*
	public void move(Pokemon target, Move move) {
		
		if (move.getpp() <= 0) {
			System.out.println("This move is out of PP and cannot be used!");
		}
		else {
			
			if (this.isAlive) {			
				
				if (target.isAlive) {
					
					for (Move m : moveSet) {
						
						if (m.getName().equals(move.getName())) {	
							
							System.out.println(this.getName() + " used " + m.getName());
												
							if (isHit(move)) {
								
								move.setpp(move.getpp() - 1);
												
								int damageDealt = calculateDamageDealt(m, target);
								
								int health = dealDamage(target, damageDealt);
								
								if (health <= 0) {
									System.out.println(target.getName() + " fainted!");
									target.setAlive(false);
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
				System.out.println(this.getName() + " has fainted and cannot battle!");
			}			
		}		
	}
	
	private boolean isHit(Move move) {
		
		Random r = new Random();
		float chance = r.nextFloat();
		
		return (chance <= ((float) move.getAccuracy() / 100)) ? true : false;
	}
	
	private int calculateDamageDealt(Move move, Pokemon target) {
		
		double level = this.getLevel();
		double power = move.getPower();
		
		double A = move.getType().equals(this.getType()) ? this.getSpAttack() : this.getAttack();
		double D = target.getDefense();
		
		double type = effectiveness(move.getType(), target.getType());
		
		if (type == 2) {
			System.out.println("It's super effective!");
		}
		else if (type == .5) {
			System.out.println("It's not very effective...");
		}
						
		int damageDealt = (int) ((((((2 * level) / 5) + 2) * power * (A / D)) / 50) * type);
		
		return damageDealt;
	}
	
	private int dealDamage(Pokemon target, int damage) {		
		target.setHP(target.getHP() - (int)damage);
		return target.getHP();
	}

	private double effectiveness (MoveType move, MoveType target) {
		
		double result = 1.0;
		
		for (MoveType type : target.getVulnerability()) {			
			if (type.toString().equals(move.toString())) {
				result = type.getStrength();
				return result;
			}			
		}
		
		for (MoveType type : target.getResistance()) {			
			if (type.toString().equals(move.toString())) {
				result = type.getStrength();
				return result;
			}			
		}		
		
		return result;
	}
	*/

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public TypeEngine getType() { return type; }

	public int getLevel() {	return level; }
	public void setLevel(int level) { this.level = level; }
	
	public int getXP() { return xp; }
	public void setXP(int xp) { this.xp = xp; }

	public int getHP() {	return (hp < 0) ? 0 : hp; }
	public void setHP(int hp) { this.hp = hp; }

	public int getSpeed() {	return speed; }
	public void setSpeed(int speed) { this.speed = speed; }
	
	public int getAttack() { return attack; }
	public void setAttack(int attack) { this.attack = attack; }
	
	public int getDefense() { return defense; }
	public void setDefense(int defense) { this.defense = defense; }
	
	public int getSpAttack() { return spAttack; }
	public void setSpAttack(int spAttack) { this.spAttack = spAttack; }
	
	public int getSpDefense() { return spDefense; }
	public void setSpDefense(int spDefense) { this.spDefense = spAttack; }
	
	public int getEvLevel() { return evLevel; }

	public boolean isAlive() { return isAlive; }
	public void setAlive(boolean isAlive) { this.isAlive = isAlive; }

	public ArrayList<MoveEngine> getMoveSet() { return moveSet; }	
}