package pokemon;

import java.util.ArrayList;

import moves.MoveEngine;
import types.TypeEngine;

public enum Pokedex {
	
	CHARMANDER ("Charmander", TypeEngine.fire, 5, 39, 52, 43, 60, 50, 65, 16),
	SQUIRTLE ("Squirtle", TypeEngine.water, 5, 44, 48, 65, 50, 64, 43, 16),
	WARTORTLE ("Wartortle", TypeEngine.water, 36, 59, 63, 80, 65, 80, 58, 36),
	PIKACHU ("Pikachu", TypeEngine.electric, 5, 55, 55, 40, 50, 50, 90, 30),
	GEODUDE ("Geodude", TypeEngine.rock, 5, 40, 80, 100, 30, 30, 20, 25);
	
	private String name;
	protected TypeEngine type;
	
	private int level, xp, hp, speed, attack, defense, spAttack, spDefense, evLevel;	
	public boolean isAlive;
	
	private ArrayList<MoveEngine> moveSet;

	Pokedex(String name, TypeEngine type, int level, int hp, int attack, int defense, 
			int spAttack, int spDefense, int speed, int evLevel) {			
		
		this.name = name; this.type = type; this.level = level;	this.xp = 1; this.hp = hp; 
		this.speed = speed; this.attack = attack; this.defense = defense; 
		this.spAttack = spAttack; this.spDefense = spDefense; this.evLevel = evLevel;
		
		this.isAlive = true;	
		
		moveSet = new ArrayList<>();
	}
	
	private static Pokedex[] POKEDEX = Pokedex.values();
	
	public boolean addMove(MoveEngine move) {
		
		if (this.getMoveSet().size() == 4) {
			return false;
		}
		else {
			this.getMoveSet().add(move);
			return true;
		}
	}
	
	public void listMoves() {		
		
		System.out.println("MOVESET FOR " + this.name + ":\n");
		
		for (MoveEngine move : moveSet) {
			System.out.println(move.getName() + " : (TYPE: " + move.getType()  + "), (PP: " + move.getpp() + 
					"), (PWR: " + move.getPower() + ")"	+ ", (ACC: " + move.getAccuracy() + ")");
		}
	}
	/*
	public TypeEngine getType(String type) {
				
		for (TypeEngine t : Pokemon.) {
			
			if (t.toString().equals(type)) {
				return t;
			}
		}
		return null;
	}
	*/
	
	public static Pokedex getPokemon(String name) {
		for (Pokedex p : POKEDEX) {
			if (p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}	
	public static Pokedex getPokemon(int i) {
		return POKEDEX[i];
	}
	public static int getPokedexSize() {
		return POKEDEX.length;
	}	
	public static Pokedex[] getPokedex() {
		return POKEDEX;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TypeEngine getType() {
		return type;
	}

	public void setType(TypeEngine type) {
		this.type = type;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getXP() {
		return xp;
	}

	public void setXP(int xp) {
		this.xp = xp;
	}

	public int getHP() {
		return hp;
	}

	public void setHP(int hp) {
		this.hp = hp;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getSpAttack() {
		return spAttack;
	}

	public void setSpAttack(int spAttack) {
		this.spAttack = spAttack;
	}

	public int getSpDefense() {
		return spDefense;
	}

	public void setSpDefense(int spDefense) {
		this.spDefense = spDefense;
	}

	public int getEvLevel() {
		return evLevel;
	}

	public void setEvLevel(int evLevel) {
		this.evLevel = evLevel;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public ArrayList<MoveEngine> getMoveSet() { return moveSet; }	
}