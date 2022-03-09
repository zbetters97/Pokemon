package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import moves.MoveEngine;
import types.TypeEngine;

/*** POKEDEX ENUM CLASS ***/
public enum Pokedex implements PokedexInterface {
	
	/** INITIALIZE ENUMS **/
	CHARMANDER ("Charmander", TypeEngine.fire, 5, 39, 52, 43, 60, 50, 65, 16, 1),
	SQUIRTLE ("Squirtle", TypeEngine.water, 5, 44, 48, 65, 50, 64, 43, 16, 1),
	WARTORTLE ("Wartortle", TypeEngine.water, 16, 59, 63, 80, 65, 80, 58, 36, 2),
	BLASTOISE ("Blastoise", TypeEngine.water, 36, 79, 83, 100, 85, 105, 78, -1, 3),
	PIKACHU ("Pikachu", TypeEngine.electric, 5, 55, 55, 40, 50, 50, 90, 30, 2),
	GEODUDE ("Geodude", TypeEngine.rock, 5, 40, 80, 100, 30, 30, 20, 25, 1);
	/** END INITIALIZE ENUMS **/
	
	/** INITIALIZE VALUES FOR POKEMON TO HOLD **/
	private String name;
	protected TypeEngine type;	
	private int level, xp, hp, speed, attack, defense, spAttack, spDefense, evLevel, ev;	
	public boolean isAlive;
	/** END INITIALIZE VALUES **/
	
	// initialize arraylist to hold moves of given pokemon
	private ArrayList<MoveEngine> moveSet;
	
	// initialize arraylist to hold all enums in pokemon class
	private static List<Pokedex> POKEDEX = Arrays.asList(Pokedex.values());

	/** CONSTRUCTOR **/
	Pokedex(String name, TypeEngine type, int level, int hp, int attack, int defense, 
			int spAttack, int spDefense, int speed, int evLevel, int ev) {			
		
		this.name = name; this.type = type; this.level = level;	this.xp = 1; this.hp = hp; 
		this.speed = speed; this.attack = attack; this.defense = defense; 
		this.spAttack = spAttack; this.spDefense = spDefense; this.evLevel = evLevel; this.ev = ev;
		
		this.isAlive = true;	
		
		moveSet = new ArrayList<>();
	}
	/** END CONSTRUCTOR **/
	
	/** POKEDEX ARRAYLIST GETTERS **/
	public static Pokedex getPokemon(String name) {
		
		for (Pokedex p : POKEDEX) {
			if (p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}	
	public static Pokedex getPokemon(int index) { 
		return POKEDEX.get(index); 
	}
	
	public static List<Pokedex> getPokedex() { 
		return POKEDEX; 
	}
	public static int getPokedexSize() { 
		return POKEDEX.size(); 
	}	
	/** END POKEDEX ARRAYLIST GETTERS **/
	
	/** EVOLVE POKEMON  METHOD **/
	public Pokedex evolve() {
		
		// check if pekemon can evolve to avoid errors
		if (this.canEvolve()) {
			
			// find current position of calling object in pokedex
			int nextID = POKEDEX.indexOf(this);
			
			// evolved form is next position in pokedex
			Pokedex evolvedPokemon = Pokedex.getPokemon(nextID + 1);
			
			return evolvedPokemon;
		}
		// pokemon can't evolve
		else {
			return null;	
		}
	}
	/** END EVOLVE POKEMON METHOD **/
	
	/** CAN EVOLVE METHOD **/
	public boolean canEvolve() {
		
		// pokemon can't evolve if evLevel is -1
		return this.getEvLevel() != -1;
	}
	/** END CAN EVOLVE METHOD **/
	
/*--- NOT FINISHED ---*/
	/** ADD NEW MOVE METHOD **/
	public boolean addMove(MoveEngine move) { 
		
		if (this.getMoveSet().size() == 4) {
			return false;
		}
		else {
			this.getMoveSet().add(move);
			return true;
		}
	}
	/** END ADD NEW MOVE METHOD **/
/*--- END NOT FINISHED ---*/
	
	/** LIST MOVE SET FOR GIVEN POKEMON METHOD **/
	public void listMoves() {			
		
		System.out.println("MOVESET FOR " + this.name + ":\n");
		
		for (MoveEngine move : moveSet) {
			System.out.println(move.getName() + " : (TYPE: " + move.getType()  + "), (PP: " + move.getpp() + 
					"), (PWR: " + move.getPower() + ")"	+ ", (ACC: " + move.getAccuracy() + ")");
		}
	}
	/** END LIST MOVE SET METHOD **/
	
	/** GETTERS AND SETTERS **/
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public TypeEngine getType() { return type; }
	public void setType(TypeEngine type) { this.type = type; }

	public int getLevel() {	return level; }
	public void setLevel(int level) { this.level = level; }

	public int getXP() { return xp; }
	public void setXP(int xp) {	this.xp = xp; }

	public int getHP() { return hp; }
	public void setHP(int hp) {	this.hp = hp; }

	public int getSpeed() { return speed; }
	public void setSpeed(int speed) { this.speed = speed; }

	public int getAttack() { return attack; }
	public void setAttack(int attack) {	this.attack = attack; }

	public int getDefense() { return defense; }
	public void setDefense(int defense) { this.defense = defense; }

	public int getSpAttack() { return spAttack; }
	public void setSpAttack(int spAttack) { this.spAttack = spAttack; }

	public int getSpDefense() {	return spDefense; }
	public void setSpDefense(int spDefense) { this.spDefense = spDefense; }

	public int getEvLevel() { return evLevel; }
	public void setEvLevel(int evLevel) { this.evLevel = evLevel; }
	
	public int getEV() { return ev; }
	public void setEV(int ev) { this.ev = ev; }

	public boolean isAlive() { return isAlive; }
	public void setAlive(boolean isAlive) {	this.isAlive = isAlive; }
	
	public ArrayList<MoveEngine> getMoveSet() { return moveSet; }
	public void setMoveSet(ArrayList<MoveEngine> moveSet) { this.moveSet = moveSet; }
	/** END GETTERS AND SETTERS **/
}
/*** EDN POKEDEX ENUM CLASS ***/