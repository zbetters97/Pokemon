package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import moves.Moves;
import types.TypeEngine;

/*** POKEDEX ENUM CLASS ***/
public enum Pokedex implements PokedexInterface {
	
	/** INITIALIZE ENUMS **/
	// xp values reference: https://bulbapedia.bulbagarden.net/wiki/List_of_Pok%C3%A9mon_by_effort_value_yield
	BULBASAUR ("Bulbasaur", TypeEngine.grass, 5, 45, 49, 49, 65, 65, 45, 16, 64, 1),
	IVYSAUR ("Ivysaur", Arrays.asList(TypeEngine.grass, TypeEngine.poison), 16, 60, 62, 63, 80, 80, 60, 32, 141, 2),
	VENUSAUR ("Venusaur", Arrays.asList(TypeEngine.grass, TypeEngine.poison), 36, 80, 82, 83, 100, 100, 80, -1, 208, 3),
	CHARMANDER ("Charmander", TypeEngine.fire, 5, 39, 52, 43, 60, 50, 65, 16, 65, 1),
	CHARMELEON ("Charmeleon", TypeEngine.fire, 16, 58, 64, 58, 80, 65, 80, 36, 142, 2),
	CHARIZARD ("Charizard", Arrays.asList(TypeEngine.fire, TypeEngine.flying, TypeEngine.dragon), 36, 78, 84, 78, 109, 85, 100, -1, 209, 3),
	SQUIRTLE ("Squirtle", TypeEngine.water, 5, 44, 48, 65, 50, 64, 43, 16, 66, 1),
	WARTORTLE ("Wartortle", TypeEngine.water, 16, 59, 63, 80, 65, 80, 58, 36, 143, 2),
	BLASTOISE ("Blastoise", TypeEngine.water, 36, 79, 83, 100, 85, 105, 78, -1, 210, 3),
	PIKACHU ("Pikachu", TypeEngine.electric, 5, 55, 55, 40, 50, 50, 90, 30, 82, 2),
	RAICHU ("Raichu", TypeEngine.electric, 30, 60, 90, 55, 90, 80, 110, -1, 122, 3);
	/** END INITIALIZE ENUMS **/
		
	/** INITIALIZE VALUES FOR POKEMON TO HOLD **/
	private String name;
	protected TypeEngine type;
	private List<TypeEngine> types;
	private int level, xp, hp, speed, attack, defense, spAttack, spDefense, evLevel, ev;	
	public boolean isAlive;
	/** END INITIALIZE VALUES **/
	
	// initialize arraylist to hold moves of given pokemon
	private ArrayList<Moves> moveSet;
	
	// initialize arraylist to hold all enums in pokemon class
	private static List<Pokedex> POKEDEX = Arrays.asList(Pokedex.values());

	/** CONSTRUCTOR **/
	Pokedex(String name, TypeEngine type, int level, int hp, int attack, int defense, 
			int spAttack, int spDefense, int speed, int evLevel, int xp, int ev) {	
		
		this.name = name; this.type = type; this.level = level;
		int iv = 1 + (int)(Math.random() * ((31 - 1) + 1));							
		this.hp = (int)(Math.floor(((2 * hp + iv + Math.floor(ev / 4)) * level) / 100) + level + 10);
		
		Calculate getStat = (stat, IV, EV, lev) -> {
			return (int)(Math.floor(0.01 * (2 * stat + IV + Math.floor(EV / 4)) * lev)) + 5;
		};
		
		this.speed = getStat.compute(speed, iv, ev, level);
		this.attack = getStat.compute(attack, iv, ev, level); this.defense = getStat.compute(defense, iv, ev, level);		
		this.spAttack = getStat.compute(spAttack, iv, ev, level); this.spDefense = getStat.compute(spDefense, iv, ev, level);
		
		this.evLevel = evLevel; this.xp = xp; this.ev = ev; this.types = null;		
		this.isAlive = true;	
		
		moveSet = new ArrayList<>();
	}
	Pokedex(String name, List<TypeEngine> types, int level, int hp, int attack, int defense, 
			int spAttack, int spDefense, int speed, int evLevel, int xp, int ev) {			
		
		this.name = name; this.types = types; this.level = level;
		int iv = 1 + (int)(Math.random() * ((31 - 1) + 1));							
		this.hp = (int)(Math.floor(((2 * hp + iv + Math.floor(ev / 4)) * level) / 100) + level + 10);
		
		Calculate getStat = (stat, IV, EV, lev) -> {
			return (int)(Math.floor(0.01 * (2 * stat + IV + Math.floor(EV / 4)) * lev)) + 5;
		};
		
		this.speed = getStat.compute(speed, iv, ev, level);
		this.attack = getStat.compute(attack, iv, ev, level); this.defense = getStat.compute(defense, iv, ev, level);		
		this.spAttack = getStat.compute(spAttack, iv, ev, level); this.spDefense = getStat.compute(spDefense, iv, ev, level);
		
		this.evLevel = evLevel; this.xp = xp; this.ev = ev; this.type = null;		
		this.isAlive = true;	
		
		moveSet = new ArrayList<>();		
	}
	/** END CONSTRUCTOR **/
	
	/** CREATE POKEMON METHOD **/
	public static Pokedex createPokemon(Pokedex pokemon) {
		
		// Map of pokemon and corresponding move set
		Map<Pokedex, List<Moves>> pokeMap = new HashMap<>();
		
		// set default moves for each pokemon
        pokeMap.put(BULBASAUR, Arrays.asList(Moves.TACKLE, Moves.VINEWHIP));
        pokeMap.put(IVYSAUR, Arrays.asList(Moves.TACKLE, Moves.VINEWHIP, Moves.RAZORLEAF));
		pokeMap.put(VENUSAUR, Arrays.asList(Moves.TAKEDOWN, Moves.DOUBLEEDGE, Moves.PETALBLIZZARD,
				Moves.SOLARBEAM));
        pokeMap.put(CHARMANDER, Arrays.asList(Moves.SCRATCH, Moves.QUICKATTACK, Moves.EMBER));
		pokeMap.put(CHARMELEON, Arrays.asList(Moves.SLASH, Moves.QUICKATTACK, Moves.EMBER, 
				Moves.FIREFANG));
        pokeMap.put(CHARIZARD, Arrays.asList(Moves.DRAGONCLAW, Moves.DRAGONBREATH, Moves.FLAMETHROWER,
        	Moves.FLAREBLITZ));
		pokeMap.put(SQUIRTLE, Arrays.asList(Moves.TACKLE, Moves.WATERGUN));
        pokeMap.put(WARTORTLE, Arrays.asList(Moves.SHELLSMASH, Moves.QUICKATTACK, Moves.WATERGUN,
        		Moves.WATERPULSE));
        pokeMap.put(BLASTOISE, Arrays.asList(Moves.SHELLSMASH, Moves.AQUATAIL, 
        		Moves.WATERPULSE, Moves.HYDROPUMP));
        pokeMap.put(PIKACHU, Arrays.asList(Moves.TACKLE, Moves.QUICKATTACK, Moves.THUNDERSHOCK));
        pokeMap.put(RAICHU, Arrays.asList(Moves.QUICKATTACK, Moves.THUNDERPUNCH, Moves.SLAM, 
        		Moves.THUNDERBOLT));
        
        // if found in map, add each move to passed in pokemon object
        for (int i = 0; i < pokeMap.get(pokemon).size(); i++) {
        	pokemon.addMove(pokeMap.get(pokemon).get(i));
        } 
        
        return pokemon;
	}
	/** END CREATE POKEMON METHOD **/
	
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
		Pokedex pokemon = createPokemon(POKEDEX.get(index));
		return pokemon; 
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
	
	/** ADD NEW MOVE METHOD **/
	public boolean addMove(Moves move) { 
		
		if (this.getMoveSet().size() == 4) {
			return false;
		}
		else {
			this.getMoveSet().add(move);
			return true;
		}
	}
	/** END ADD NEW MOVE METHOD **/
	
	/** LIST MOVE SET FOR GIVEN POKEMON METHOD **/
	public void listMoves() {			
		
		System.out.println("MOVESET FOR " + this.name + ":\n");
		
		for (Moves move : moveSet) {
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
	
	public List<TypeEngine> getTypes() { return types; }
	public void setTypes(List<TypeEngine> types) { this.types = types; }

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
	
	public ArrayList<Moves> getMoveSet() { return moveSet; }
	public void setMoveSet(ArrayList<Moves> moveSet) { this.moveSet = moveSet; }
	/** END GETTERS AND SETTERS **/
}
/*** EDN POKEDEX ENUM CLASS ***/



@FunctionalInterface
interface Calculate {
	public int compute(int j, int k, int l, int m);
}