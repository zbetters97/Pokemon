package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import moves.Moves;
import status.StatusEngine;
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
	RAICHU ("Raichu", TypeEngine.electric, 30, 60, 90, 55, 90, 80, 110, -1, 122, 3),
	ABRA ("Abra", TypeEngine.psychic, 5, 25, 20, 15, 105, 55, 90, 16, 62, 1),
	KADABRA ("Kadabra", TypeEngine.psychic, 16, 40, 35, 30, 120, 70, 105, 36, 140, 2),
	ALAKAZAM ("Alakazam", TypeEngine.psychic, 36, 55, 50, 45, 135, 95, 120, -1, 250, 3),
	MACHOP ("Machop", TypeEngine.fighting, 5, 70, 80, 50, 35, 35, 35, 28, 61, 1),
	MACHOKE ("Machoke", TypeEngine.fighting, 28, 80, 100, 70, 50, 60, 45, 40, 142, 2),
	MACHAMP ("Machamp", TypeEngine.fighting, 40, 90, 130, 80, 65, 85, 55, -1, 253, 3),
	GEODUDE ("Geodude", Arrays.asList(TypeEngine.rock, TypeEngine.ground), 5, 40, 80, 100, 30, 30, 20, 25, 60, 1),
	GRAVELER ("Graveler", Arrays.asList(TypeEngine.rock, TypeEngine.ground), 25, 55, 95, 115, 45, 45, 35, 40, 137, 2),
	GOLEM ("Golem", Arrays.asList(TypeEngine.rock, TypeEngine.ground), 40, 80, 120, 130, 55, 65, 45, -1, 248, 3),
	GASTLY ("Gastly", Arrays.asList(TypeEngine.ghost, TypeEngine.poison), 5, 30, 35, 30, 100, 35, 80, 25, 62, 1),
	HAUNTER ("Haunter", Arrays.asList(TypeEngine.ghost, TypeEngine.poison), 25, 45, 50, 45, 115, 55, 96, 40, 142, 2),
	GENGAR ("Gengar", Arrays.asList(TypeEngine.ghost, TypeEngine.poison), 40, 60, 65, 60, 130, 75, 110, -1, 250, 3);
	/** END INITIALIZE ENUMS **/
		
	/** INITIALIZE VALUES FOR POKEMON TO HOLD **/
	private String name;
	protected TypeEngine type;
	private List<TypeEngine> types;
	private int level, bhp, hp, evLevel, xp, ev;
	private double speed, attack, defense, spAttack, spDefense, accuracy;	
	private int speedStg, attackStg, defenseStg, spAttackStg, spDefenseStg, accuracyStg;
	private StatusEngine status;
	private boolean isAlive;

	private int statusCounter, statusLimit;
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
		this.bhp = this.hp;
		
		Calculate getStat = (stat, IV, EV, lev) -> {
			return (int)(Math.floor(0.01 * (2 * stat + IV + Math.floor(EV / 4)) * lev)) + 5;
		};
		
		this.speed = getStat.compute(speed, iv, ev, level);
		this.attack = getStat.compute(attack, iv, ev, level); 
		this.defense = getStat.compute(defense, iv, ev, level);		
		this.spAttack = getStat.compute(spAttack, iv, ev, level); 
		this.spDefense = getStat.compute(spDefense, iv, ev, level);
		this.accuracy = 1;
		
		this.speedStg = 0;
		this.attackStg = 0;
		this.defenseStg = 0;
		this.spAttackStg= 0;
		this.spDefenseStg = 0;
		this.accuracyStg = 0;
		
		this.evLevel = evLevel; this.xp = xp; this.ev = ev; this.types = null;	
		this.status = null;
		this.isAlive = true;	
		
		this.statusCounter = 0;
		this.statusLimit = 0;
		
		moveSet = new ArrayList<>();
	}
	Pokedex(String name, List<TypeEngine> types, int level, int hp, int attack, int defense, 
			int spAttack, int spDefense, int speed, int evLevel, int xp, int ev) {			
		
		this.name = name; this.types = types; this.level = level;
		int iv = 1 + (int)(Math.random() * ((31 - 1) + 1));							
		this.hp = (int)(Math.floor(((2 * hp + iv + Math.floor(ev / 4)) * level) / 100) + level + 10);
		this.bhp = this.hp;
		
		Calculate getStat = (stat, IV, EV, lev) -> {
			return (int)(Math.floor(0.01 * (2 * stat + IV + Math.floor(EV / 4)) * lev)) + 5;
		};
		
		this.speed = getStat.compute(speed, iv, ev, level);
		this.attack = getStat.compute(attack, iv, ev, level); 
		this.defense = getStat.compute(defense, iv, ev, level);		
		this.spAttack = getStat.compute(spAttack, iv, ev, level); 
		this.spDefense = getStat.compute(spDefense, iv, ev, level);
		this.accuracy = 1;
		
		this.speedStg = 0;
		this.attackStg = 0;
		this.defenseStg = 0;
		this.spAttackStg= 0;
		this.spDefenseStg = 0;
		this.accuracyStg = 0;
		
		this.evLevel = evLevel; this.xp = xp; this.ev = ev; this.type = null;
		this.status = null;
		this.isAlive = true;	
		
		this.statusCounter = 0;
		this.statusLimit = 0;
		
		moveSet = new ArrayList<>();		
	}
	/** END CONSTRUCTOR **/
	
	/** CREATE POKEMON METHOD **/
	public static Pokedex createPokemon(Pokedex pokemon) {
		
		// Map of pokemon and corresponding move set
		Map<Pokedex, List<Moves>> pokeMap = new HashMap<>();
		
		// set default moves for each pokemon
        pokeMap.put(BULBASAUR, Arrays.asList(Moves.TACKLE, Moves.VINEWHIP, Moves.GROWL));
        pokeMap.put(IVYSAUR, Arrays.asList(Moves.TACKLE, Moves.POISONPOWDER, Moves.VINEWHIP, 
        		Moves.RAZORLEAF));
		pokeMap.put(VENUSAUR, Arrays.asList(Moves.TAKEDOWN, Moves.DOUBLEEDGE, Moves.PETALBLIZZARD,
				Moves.SOLARBEAM));
		
        pokeMap.put(CHARMANDER, Arrays.asList(Moves.SCRATCH, Moves.EMBER, Moves.GROWL));
		pokeMap.put(CHARMELEON, Arrays.asList(Moves.SLASH, Moves.QUICKATTACK, Moves.EMBER, 
				Moves.FIREFANG));
        pokeMap.put(CHARIZARD, Arrays.asList(Moves.DRAGONCLAW, Moves.DRAGONBREATH, Moves.FLAMETHROWER,
        	Moves.FLAREBLITZ));
        
		pokeMap.put(SQUIRTLE, Arrays.asList(Moves.TACKLE, Moves.WATERGUN, Moves.TAILWHIP));
        pokeMap.put(WARTORTLE, Arrays.asList(Moves.QUICKATTACK, Moves.WATERGUN, Moves.WATERPULSE));
        pokeMap.put(BLASTOISE, Arrays.asList(Moves.FLASHCANNON, Moves.AQUATAIL, 
        		Moves.WATERPULSE, Moves.HYDROPUMP));
        
        pokeMap.put(PIKACHU, Arrays.asList(Moves.TACKLE, Moves.THUNDERWAVE, Moves.THUNDERSHOCK, 
        		Moves.PLAYNICE));
        pokeMap.put(RAICHU, Arrays.asList(Moves.QUICKATTACK, Moves.SLAM, Moves.THUNDERPUNCH,  
        		Moves.THUNDERBOLT));    
        
        pokeMap.put(ABRA, Arrays.asList(Moves.TELEPORT));
        pokeMap.put(KADABRA, Arrays.asList(Moves.CONFUSION, Moves.PSYBEAM, Moves.KINESIS));
        pokeMap.put(ALAKAZAM, Arrays.asList(Moves.PSYCHIC, Moves.CONFUSION, Moves.PSYCHOCUT, 
        		Moves.CALMMIND));     
        
        pokeMap.put(MACHOP, Arrays.asList(Moves.LOWKICK, Moves.LOWSWEEP, Moves.KNOCKOFF)); 
        pokeMap.put(MACHOKE, Arrays.asList(Moves.LOWKICK, Moves.LOWSWEEP, Moves.VITALTHROW, 
        		Moves.SEISMICTOSS)); 
        pokeMap.put(MACHAMP, Arrays.asList(Moves.VITALTHROW, Moves.SEISMICTOSS, Moves.DYNAMICPUNCH, 
        		Moves.CROSSCHOP)); 
        
        pokeMap.put(GEODUDE, Arrays.asList(Moves.TACKLE, Moves.ROCKTHROW, Moves.DEFENSECURL));
        pokeMap.put(GRAVELER, Arrays.asList(Moves.TACKLE, Moves.ROCKTHROW, Moves.ROLLOUT, 
        		Moves.DEFENSECURL));
        pokeMap.put(GOLEM, Arrays.asList(Moves.ROLLOUT, Moves.HEAVYSLAM, Moves.EARTHQUAKE,
        		Moves.DOUBLEEDGE));
        
        pokeMap.put(GASTLY, Arrays.asList(Moves.LICK, Moves.PAYBACK, Moves.HYPNOSIS));   
        pokeMap.put(HAUNTER, Arrays.asList(Moves.PAYBACK, Moves.HEX, Moves.CONFUSERAY,
        		Moves.DARKPULSE));   
        pokeMap.put(GENGAR, Arrays.asList(Moves.HEX, Moves.DARKPULSE, Moves.SHADOWBALL, 
        		Moves.SHADOWPUNCH)); 
        
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
	
	public int getBHP() { return bhp; }
	public void setBHP(int bhp) {	this.bhp = bhp; }

	public double getSpeed() { return speed; }
	public void setSpeed(int speed) { this.speed = speed; }

	public double getAttack() { return attack; }
	public void setAttack(int attack) {	this.attack = attack; }

	public double getDefense() { return defense; }
	public void setDefense(int defense) { this.defense = defense; }

	public double getSpAttack() { return spAttack; }
	public void setSpAttack(int spAttack) { this.spAttack = spAttack; }

	public double getSpDefense() {	return spDefense; }
	public void setSpDefense(int spDefense) { this.spDefense = spDefense; }
	
	public double getAccuracy() { return accuracy; }
	public void setAccuracy(int accuracy) { this.accuracy = accuracy; }

	public int getSpeedStg() { return speedStg; }
	public void setSpeedStg(int speedStg) { this.speedStg = speedStg; }
	public int getAttackStg() { return attackStg; }
	public void setAttackStg(int attackStg) { this.attackStg = attackStg; }
	public int getDefenseStg() { return defenseStg; }
	public void setDefenseStg(int defenseStg) { this.defenseStg = defenseStg; }
	public int getSpAttackStg() { return spAttackStg; }
	public void setSpAttackStg(int spAttackStg) { this.spAttackStg = spAttackStg; }
	public int getSpDefenseStg() { return spDefenseStg; }
	public void setSpDefenseStg(int spDefenseStg) { this.spDefenseStg = spDefenseStg; }
	public int getAccuracyStg() { return accuracyStg; }
	public void setAccuracyStg(int accuracyStg) { this.accuracyStg = accuracyStg; }
	
	public int getEvLevel() { return evLevel; }
	public void setEvLevel(int evLevel) { this.evLevel = evLevel; }
	
	public StatusEngine getStatus() { return status; }
	public void setStatus(StatusEngine status) { this.status = status; }
	
	public int getEV() { return ev; }
	public void setEV(int ev) { this.ev = ev; }

	public boolean isAlive() { return isAlive; }
	public void setAlive(boolean isAlive) {	this.isAlive = isAlive; }
	
	public int getStatusCounter() { return statusCounter; }
	public void setStatusCounter(int statusCounter) { this.statusCounter = statusCounter; }
	
	public int getStatusLimit() { return statusLimit; }
	public void setStatusLimit(int statusLimit) { this.statusLimit = statusLimit; }
	
	public ArrayList<Moves> getMoveSet() { return moveSet; }
	public void setMoveSet(ArrayList<Moves> moveSet) { this.moveSet = moveSet; }
	
	public void changeStat(String stat, int level) {	
		
		switch (stat) {
			case "attack":
				if (this.attackStg + level > 6 || this.attackStg + level < -6) {
					if (level >= 1) 
						System.out.println(this.name + "'s attack won't go any higher!");
					else if (level <= -1) 
						System.out.println(this.name + "'s attack won't go any lower!");
					return;
				}
				else {	
					this.attackStg += level;
					this.attack *= Math.max(2, 2 + (double) this.attackStg) / Math.max(2, 2 - (double) this.attackStg);	
		
					outputChange(stat, level);
				}	
				break;
			case "sp. attack":
				if (this.spAttackStg + level > 6 || this.spAttackStg + level < -6) {
					if (level >= 1) 
						System.out.println(this.name + "'s sp. attack won't go any higher!");
					else if (level <= -1) 
						System.out.println(this.name + "'s sp. attack won't go any lower!");
					return;
				}
				else {	
					this.spAttackStg += level;
					this.spAttack *= Math.max(2, 2 + (double) this.spAttackStg) / Math.max(2, 2 - (double) this.spAttackStg);	
					
					outputChange(stat, level);
				}
				break;
			case "defense":
				if (this.defenseStg + level > 6 || this.defenseStg + level < -6) {
					if (level >= 1) 
						System.out.println(this.name + "'s defense won't go any higher!");
					else if (level <= -1) 
						System.out.println(this.name + "'s defense won't go any lower!");
					return;
				}
				else {	
					this.defenseStg += level;
					this.defense *= Math.max(2, 2 + (double) this.defenseStg) / Math.max(2, 2 - (double) this.defenseStg);
					
					outputChange(stat, level);
				}	
				break;
			case "sp. defense":
				if (this.spDefenseStg + level > 6 || this.spDefenseStg  + level < -6) {
					if (level >= 1) 
						System.out.println(this.name + "'s sp. defense won't go any higher!");
					else if (level <= -1) 
						System.out.println(this.name + "'s sp. defense won't go any lower!");
					return;
				}
				else {	
					this.spDefenseStg  += level;
					this.spDefense *= Math.max(2, 2 + (double) this.spDefenseStg ) / Math.max(2, 2 - (double) this.spDefenseStg);	
					
					outputChange(stat, level);
				}	
				break;
			case "speed":
				if (this.speedStg + level > 6 || this.speedStg + level < -6) {
					if (level >= 1) 
						System.out.println(this.name + "'s speed won't go any higher!");
					else if (level <= -1) 
						System.out.println(this.name + "'s speed won't go any lower!");
					return;
				}
				else {	
					this.speedStg += level;
					this.speed *= Math.max(2, 2 + (double) this.speedStg) / Math.max(2, 2 - (double) this.speedStg);	
					
					outputChange(stat, level);
				}	
				break;
			case "accuracy":
				if (this.accuracyStg + level > 6 || this.accuracyStg + level < -6) {
					if (level >= 1) 
						System.out.println(this.name + "'s accuracy won't go any higher!");
					else if (level <= -1) 
						System.out.println(this.name + "'s accuracy won't go any lower!");
					return;
				}
				else {	
					this.accuracyStg += level;
					this.accuracy = Math.max(3, 3 + (double) this.accuracyStg) / Math.max(3, 3 - (double) this.accuracyStg);	
					
					outputChange(stat, level);
				}	
				break;
		}
	}
	/** END GETTERS AND SETTERS **/
	
	private void outputChange(String stat, int level) {
		
		if (level == 1)
			System.out.println(this.name + "'s " + stat + " rose!");
		else if (level == 2) 
			System.out.println(this.name + "'s " + stat + " greatly rose!");
		else if (level >= 3)
			System.out.println(this.name + "'s " + stat + " drastically!");
		else if (level == -1)
			System.out.println(this.name + "'s " + stat + " fell!");
		else if (level == -2)
			System.out.println(this.name + "'s " + stat + " greatly fell!");
		else if (level <= -3)
			System.out.println(this.name + "'s " + stat + " severely fell!");
	}
}
/*** EDN POKEDEX ENUM CLASS ***/



@FunctionalInterface
interface Calculate {
	public int compute(int j, int k, int l, int m);
}