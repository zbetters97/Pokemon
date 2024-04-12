package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.Sleeper;
import moves.MovesEngine;
import moves.Move;
import status.StatusEngine;
import types.TypeEngine;

/*** POKEDEX ENUM CLASS ***/
public enum Pokedex {
	
	/** INITIALIZE ENUMS **/
	// stat reference: https://www.serebii.net/
	// exp / ev values reference: https://bulbapedia.bulbagarden.net/wiki/List_of_Pok%C3%A9mon_by_effort_value_yield
	BULBASAUR ("Bulbasaur", 1, TypeEngine.grass, 5, 45, 49, 49, 65, 65, 45, 16, 64, 1),
	IVYSAUR ("Ivysaur", 2, Arrays.asList(TypeEngine.grass, TypeEngine.poison), 16, 60, 62, 63, 80, 80, 60, 32, 141, 2),
	VENUSAUR ("Venusaur", 3, Arrays.asList(TypeEngine.grass, TypeEngine.poison), 36, 80, 82, 83, 100, 100, 80, -1, 208, 3),
	CHARMANDER ("Charmander", 4, TypeEngine.fire, 5, 39, 52, 43, 60, 50, 65, 16, 65, 1),
	CHARMELEON ("Charmeleon", 5, TypeEngine.fire, 16, 58, 64, 58, 80, 65, 80, 36, 142, 2),
	CHARIZARD ("Charizard", 6, Arrays.asList(TypeEngine.fire, TypeEngine.flying), 36, 78, 84, 78, 109, 85, 100, -1, 209, 3),
	SQUIRTLE ("Squirtle", 7, TypeEngine.water, 5, 44, 48, 65, 50, 64, 43, 16, 66, 1),
	WARTORTLE ("Wartortle", 8, TypeEngine.water, 16, 59, 63, 80, 65, 80, 58, 36, 143, 2),
	BLASTOISE ("Blastoise", 9, TypeEngine.water, 36, 79, 83, 100, 85, 105, 78, -1, 210, 3),
	PIKACHU ("Pikachu", 25, TypeEngine.electric, 5, 55, 55, 40, 50, 50, 90, 30, 82, 2),
	RAICHU ("Raichu", 26, TypeEngine.electric, 30, 60, 90, 55, 90, 80, 110, -1, 122, 3),
	ABRA ("Abra", 63, TypeEngine.psychic, 5, 25, 20, 15, 105, 55, 90, 16, 62, 1),
	KADABRA ("Kadabra", 64, TypeEngine.psychic, 16, 40, 35, 30, 120, 70, 105, 36, 140, 2),
	ALAKAZAM ("Alakazam", 65, TypeEngine.psychic, 36, 55, 50, 45, 135, 95, 120, -1, 250, 3),
	MACHOP ("Machop", 66, TypeEngine.fighting, 5, 70, 80, 50, 35, 35, 35, 28, 61, 1),
	MACHOKE ("Machoke", 67, TypeEngine.fighting, 28, 80, 100, 70, 50, 60, 45, 40, 142, 2),
	MACHAMP ("Machamp", 68, TypeEngine.fighting, 40, 90, 130, 80, 65, 85, 55, -1, 253, 3),
	GEODUDE ("Geodude", 74, Arrays.asList(TypeEngine.rock, TypeEngine.ground), 5, 40, 80, 100, 30, 30, 20, 25, 60, 1),
	GRAVELER ("Graveler", 75, Arrays.asList(TypeEngine.rock, TypeEngine.ground), 25, 55, 95, 115, 45, 45, 35, 40, 137, 2),
	GOLEM ("Golem", 76, Arrays.asList(TypeEngine.rock, TypeEngine.ground), 40, 80, 120, 130, 55, 65, 45, -1, 248, 3),
	GASTLY ("Gastly", 92, Arrays.asList(TypeEngine.ghost, TypeEngine.poison), 5, 30, 35, 30, 100, 35, 80, 25, 62, 1),
	HAUNTER ("Haunter", 93, Arrays.asList(TypeEngine.ghost, TypeEngine.poison), 25, 45, 50, 45, 115, 55, 96, 40, 142, 2),
	GENGAR ("Gengar", 94, Arrays.asList(TypeEngine.ghost, TypeEngine.poison), 40, 60, 65, 60, 130, 75, 110, -1, 250, 3),
	HORSEA ("Horsea", 116, TypeEngine.water, 5, 30, 40, 70, 70, 25, 60, 32, 59, 1),
	SEADRA ("Seadra", 117, TypeEngine.water, 32, 55, 65, 95, 95, 45, 85, 45, 154, 2),
	KINGDRA ("Kingdra", 230, Arrays.asList(TypeEngine.water, TypeEngine.dragon), 40, 75, 95, 95, 95, 95, 85, -1, 270, 3),
	RAIKOU ("Raikou", 243, TypeEngine.electric, 40, 90, 85, 75, 115, 100, 115, -1, 290, 3),
	ENTEI ("Entei", 244, TypeEngine.fire, 40, 115, 115, 85, 90, 75, 100, -1, 290, 3),
	SUICINE ("Suicine", 245, TypeEngine.water, 40, 100, 75, 115, 90, 115, 85, -1, 290, 3),
	TREECKO ("Treecko", 252, TypeEngine.grass, 5, 40, 45, 35, 65, 55, 70, 16, 62, 1),
	GROVYLE ("Grovyle", 253, TypeEngine.grass, 16, 50, 65, 45, 85, 65, 95, 36, 142, 2),
	SCEPTILE ("Sceptile", 254, TypeEngine.grass, 36, 70, 85, 65, 105, 85, 120, -1, 265, 3),
	TORCHIC ("Torchic", 255, TypeEngine.fire, 5, 45, 60, 40, 70, 50, 45, 16, 62, 1),
	COMBUSKEN ("Combusken", 256, Arrays.asList(TypeEngine.fire, TypeEngine.fighting), 16, 60, 85, 60, 85, 60, 55, 36, 142, 2),
	BLAZIKEN ("Blaziken", 257, Arrays.asList(TypeEngine.fire, TypeEngine.fighting), 36, 80, 120, 70, 110, 70, 80, -1, 265, 3),
	MUDKIP ("Mudkip", 258, TypeEngine.water, 5, 50, 70, 50, 50, 50, 40, 16, 62, 1),
	MARSHTOMP ("Marshtomp", 259, Arrays.asList(TypeEngine.water, TypeEngine.ground), 16, 70, 85, 70, 60, 70, 50, 36, 142, 2),
	SWAMPERT ("Swampert", 260, Arrays.asList(TypeEngine.water, TypeEngine.ground), 36, 100, 110, 90, 85, 90, 60, -1, 268, 3),
	KYOGRE ("Kyogre", 382, TypeEngine.water, 50, 100, 100, 90, 150, 140, 90, -1, 335, 3), 
	GROUDON ("Groudon", 383, TypeEngine.ground, 50, 100, 150, 140, 100, 90, 90, -1, 335, 3),
	RAYQUAZA ("Rayquaza", 384, Arrays.asList(TypeEngine.dragon, TypeEngine.flying), 50, 105, 150, 90, 150, 90, 95, -1, 340, 3);
	/** END INITIALIZE ENUMS **/
		
	/** INITIALIZE VALUES**/
	private String name;
	private int index;
	protected TypeEngine type;
	private List<TypeEngine> types;
	private int level, bhp, hp, evLevel, xp, ev, iv;
	private double speed, attack, defense, spAttack, spDefense, accuracy;	
	private int speedStg, attackStg, defenseStg, spAttackStg, spDefenseStg, accuracyStg;
	private StatusEngine status;
	private boolean isAlive;
	static Sleeper sleeper;
	
	private int statusCounter, statusLimit;
	/** END INITIALIZE VALUES **/
	
	// initialize arraylist to hold moves of given pokemon
	private ArrayList<Move> moveSet;
	
	// initialize arraylist to hold all enums in pokemon class
	private static List<Pokedex> POKEDEX = Arrays.asList(Pokedex.values());

	/** CONSTRUCTORS **/
	Pokedex(String name, int index, TypeEngine type, int level, int hp, int attack, int defense, 
			int spAttack, int spDefense, int speed, int evLevel, int xp, int ev) {	
		
		// hp calculation reference (GEN IV): https://pokemon.fandom.com/wiki/Individual_Values
		this.name = name; this.index = index; this.type = type; this.level = level;
		this.iv = 1 + (int)(Math.random() * ((31 - 1) + 1));							
		this.hp = (int)(Math.floor(((2 * hp + iv + Math.floor(ev / 4)) * level) / 100) + level + 10);
		this.bhp = this.hp;
		
		Calculate getStat = (stat, IV, EV, lev) -> {
			return (int)(Math.floor(0.01 * (2 * stat + IV + Math.floor(EV / 4)) * lev)) + 5;
		};
		
		this.speed = getStat.compute(speed, 1 + (int)(Math.random() * ((31 - 1) + 1)), ev, level);
		this.attack = getStat.compute(attack, 1 + (int)(Math.random() * ((31 - 1) + 1)), ev, level); 
		this.defense = getStat.compute(defense, 1 + (int)(Math.random() * ((31 - 1) + 1)), ev, level);		
		this.spAttack = getStat.compute(spAttack, 1 + (int)(Math.random() * ((31 - 1) + 1)), ev, level); 
		this.spDefense = getStat.compute(spDefense, 1 + (int)(Math.random() * ((31 - 1) + 1)), ev, level);
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
	Pokedex(String name, int index, List<TypeEngine> types, int level, int hp, int attack, int defense, 
			int spAttack, int spDefense, int speed, int evLevel, int xp, int ev) {			
		
		this.name = name; this.index = index; this.types = types; this.level = level;
		this.iv = 1 + (int)(Math.random() * ((31 - 1) + 1));							
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
	/** END CONSTRUCTORS **/
	
	/** MAP MOVES POKEMON METHOD **/
	private static Pokedex mapMoves(Pokedex pokemon) {
		
		// map of pokemon and corresponding move set
		Map<Pokedex, List<Move>> pokeMap = new HashMap<>();
		// set default moves for each pokemon
        pokeMap.put(BULBASAUR, Arrays.asList(new Move(MovesEngine.TACKLE), new Move(MovesEngine.VINEWHIP), new Move(MovesEngine.GROWL)));
        pokeMap.put(IVYSAUR, Arrays.asList(new Move(MovesEngine.TACKLE), new Move(MovesEngine.POISONPOWDER, 45), new Move(MovesEngine.VINEWHIP),
        		new Move(MovesEngine.RAZORLEAF)));
		pokeMap.put(VENUSAUR, Arrays.asList(new Move(MovesEngine.TAKEDOWN), new Move(MovesEngine.DOUBLEEDGE), new Move(MovesEngine.PETALBLIZZARD),
				new Move(MovesEngine.SOLARBEAM, 2)));
		
        pokeMap.put(CHARMANDER, Arrays.asList(new Move(MovesEngine.SCRATCH), new Move(MovesEngine.EMBER), new Move(MovesEngine.GROWL)));
		pokeMap.put(CHARMELEON, Arrays.asList(new Move(MovesEngine.SLASH), new Move(MovesEngine.QUICKATTACK), new Move(MovesEngine.EMBER), 
				new Move(MovesEngine.FIREFANG)));
        pokeMap.put(CHARIZARD, Arrays.asList(new Move(MovesEngine.DRAGONBREATH), new Move(MovesEngine.FLAMETHROWER), new Move(MovesEngine.FLAREBLITZ),
        	new Move(MovesEngine.FLY, 2)));
        
		pokeMap.put(SQUIRTLE, Arrays.asList(new Move(MovesEngine.TACKLE), new Move(MovesEngine.WATERGUN), new Move(MovesEngine.TAILWHIP)));
        pokeMap.put(WARTORTLE, Arrays.asList(new Move(MovesEngine.QUICKATTACK), new Move(MovesEngine.WATERGUN), new Move(MovesEngine.WATERPULSE)));
        pokeMap.put(BLASTOISE, Arrays.asList(new Move(MovesEngine.FLASHCANNON), new Move(MovesEngine.AQUATAIL), 
        		new Move(MovesEngine.WATERPULSE), new Move(MovesEngine.HYDROPUMP)));
        
        pokeMap.put(PIKACHU, Arrays.asList(new Move(MovesEngine.TACKLE), new Move(MovesEngine.THUNDERWAVE), new Move(MovesEngine.THUNDERSHOCK),
        		new Move(MovesEngine.PLAYNICE)));
        pokeMap.put(RAICHU, Arrays.asList(new Move(MovesEngine.QUICKATTACK), new Move(MovesEngine.SLAM), new Move(MovesEngine.THUNDERPUNCH),
        		new Move(MovesEngine.THUNDERBOLT)));    
        
        pokeMap.put(ABRA, Arrays.asList(new Move(MovesEngine.TELEPORT)));
        pokeMap.put(KADABRA, Arrays.asList(new Move(MovesEngine.CONFUSION), new Move(MovesEngine.PSYBEAM), new Move(MovesEngine.KINESIS)));
        pokeMap.put(ALAKAZAM, Arrays.asList(new Move(MovesEngine.PSYCHIC), new Move(MovesEngine.CONFUSION), new Move(MovesEngine.PSYCHOCUT), 
        		new Move(MovesEngine.CALMMIND)));     
        
        pokeMap.put(MACHOP, Arrays.asList(new Move(MovesEngine.LOWKICK), new Move(MovesEngine.LOWSWEEP), new Move(MovesEngine.KNOCKOFF))); 
        pokeMap.put(MACHOKE, Arrays.asList(new Move(MovesEngine.LOWKICK), new Move(MovesEngine.LOWSWEEP,20), new Move(MovesEngine.VITALTHROW),
        		new Move(MovesEngine.SEISMICTOSS))); 
        pokeMap.put(MACHAMP, Arrays.asList(new Move(MovesEngine.VITALTHROW), new Move(MovesEngine.SEISMICTOSS), new Move(MovesEngine.DYNAMICPUNCH),
        		new Move(MovesEngine.CROSSCHOP))); 
        
        pokeMap.put(GEODUDE, Arrays.asList(new Move(MovesEngine.TACKLE), new Move(MovesEngine.ROCKTHROW), new Move(MovesEngine.DEFENSECURL)));
        pokeMap.put(GRAVELER, Arrays.asList(new Move(MovesEngine.TACKLE), new Move(MovesEngine.ROCKTHROW), new Move(MovesEngine.ROLLOUT),
        		new Move(MovesEngine.DEFENSECURL)));
        pokeMap.put(GOLEM, Arrays.asList(new Move(MovesEngine.HEAVYSLAM), new Move(MovesEngine.EARTHQUAKE), new Move(MovesEngine.DOUBLEEDGE),
        		new Move(MovesEngine.DIG, 2)));
        
        pokeMap.put(GASTLY, Arrays.asList(new Move(MovesEngine.LICK), new Move(MovesEngine.PAYBACK), new Move(MovesEngine.HYPNOSIS)));   
        pokeMap.put(HAUNTER, Arrays.asList(new Move(MovesEngine.PAYBACK), new Move(MovesEngine.HEX), new Move(MovesEngine.CONFUSERAY),
        		new Move(MovesEngine.DARKPULSE)));   
        pokeMap.put(GENGAR, Arrays.asList(new Move(MovesEngine.HEX), new Move(MovesEngine.DARKPULSE), new Move(MovesEngine.SHADOWBALL),
        		new Move(MovesEngine.SHADOWPUNCH))); 
        
        pokeMap.put(HORSEA, Arrays.asList(new Move(MovesEngine.BUBBLE), new Move(MovesEngine.WATERGUN), new Move(MovesEngine.LEER))); 
        pokeMap.put(SEADRA, Arrays.asList(new Move(MovesEngine.WATERGUN), new Move(MovesEngine.TWISTER), new Move(MovesEngine.HYDROPUMP),
        		new Move(MovesEngine.AGILITY))); 
        pokeMap.put(KINGDRA, Arrays.asList(new Move(MovesEngine.SURF), new Move(MovesEngine.HYDROPUMP), new Move(MovesEngine.DRAGONPULSE),
        		new Move(MovesEngine.AGILITY))); 
        
        pokeMap.put(RAIKOU, Arrays.asList(new Move(MovesEngine.CRUNCH), new Move(MovesEngine.THUNDERFANG), new Move(MovesEngine.THUNDER),
        		new Move(MovesEngine.CALMMIND)));
        pokeMap.put(ENTEI, Arrays.asList(new Move(MovesEngine.EXTRASENSORY), new Move(MovesEngine.FIREFANG), new Move(MovesEngine.FLAMETHROWER),
        		new Move(MovesEngine.CALMMIND)));
        pokeMap.put(SUICINE, Arrays.asList(new Move(MovesEngine.AURORABEAM), new Move(MovesEngine.ICEFANG), new Move(MovesEngine.HYDROPUMP),
        		new Move(MovesEngine.CALMMIND)));
        
        pokeMap.put(TREECKO, Arrays.asList(new Move(MovesEngine.ABSORB), new Move(MovesEngine.QUICKATTACK), new Move(MovesEngine.LEER))); 
        pokeMap.put(GROVYLE, Arrays.asList(new Move(MovesEngine.QUICKATTACK), new Move(MovesEngine.LEAFBLADE), new Move(MovesEngine.ABSORB),
        		new Move(MovesEngine.AGILITY))); 
        pokeMap.put(SCEPTILE, Arrays.asList(new Move(MovesEngine.LEAFBLADE), new Move(MovesEngine.LEAFSTORM), new Move(MovesEngine.GIGADRAIN),
        		new Move(MovesEngine.AGILITY))); 
        
        pokeMap.put(TORCHIC, Arrays.asList(new Move(MovesEngine.EMBER), new Move(MovesEngine.SCRATCH), new Move(MovesEngine.GROWL))); 
        pokeMap.put(COMBUSKEN, Arrays.asList(new Move(MovesEngine.EMBER), new Move(MovesEngine.DOUBLEKICK), new Move(MovesEngine.SLASH), 
        		new Move(MovesEngine.GROWL))); 
        pokeMap.put(BLAZIKEN, Arrays.asList(new Move(MovesEngine.FIREPUNCH), new Move(MovesEngine.BLAZEKICK), new Move(MovesEngine.SKYUPPERCUT),
        		new Move(MovesEngine.FLAREBLITZ))); 
        
        pokeMap.put(MUDKIP, Arrays.asList(new Move(MovesEngine.TACKLE), new Move(MovesEngine.WATERGUN), new Move(MovesEngine.GROWL))); 
        pokeMap.put(MARSHTOMP, Arrays.asList(new Move(MovesEngine.TACKLE), new Move(MovesEngine.WATERGUN), new Move(MovesEngine.MUDSHOT),
        		new Move(MovesEngine.MUDSLAP))); 
        pokeMap.put(SWAMPERT, Arrays.asList(new Move(MovesEngine.SURF), new Move(MovesEngine.MUDDYWATER), new Move(MovesEngine.MUDBOMB),
        		new Move(MovesEngine.EARTHQUAKE)));
        
        pokeMap.put(KYOGRE, Arrays.asList(new Move(MovesEngine.SURF), new Move(MovesEngine.HYDROPUMP), new Move(MovesEngine.THUNDER),
        		new Move(MovesEngine.CALMMIND)));
        pokeMap.put(GROUDON, Arrays.asList(new Move(MovesEngine.EARTHQUAKE), new Move(MovesEngine.SOLARBEAM, 2), new Move(MovesEngine.FIREFANG),
        		new Move(MovesEngine.DRAGONCLAW)));
        pokeMap.put(RAYQUAZA, Arrays.asList(new Move(MovesEngine.DRAGONCLAW), new Move(MovesEngine.FLY, 2), new Move(MovesEngine.EXTREMESPEED),
        		new Move(MovesEngine.BLIZZARD))); 
        
        // if pokemon not already mapped to moveset
        if (pokemon.getMoveSet().isEmpty()) {
        	// add each move to passed in pokemon object
            for (int i = 0; i < pokeMap.get(pokemon).size(); i++) {        	
            	pokemon.addMove(pokeMap.get(pokemon).get(i));
            } 	
        }
        
        return pokemon;
	}
	/** END MAP MOVES METHOD **/
		
	public static Pokedex getPokemon(int index) { 		
		Pokedex pokemon = mapMoves(POKEDEX.get(index));
		return pokemon; 
	}	
	
	/** POKEDEX ARRAYLIST GETTERS **/
	public static Pokedex getPokemon(String name) {
		
		for (Pokedex pokemon : POKEDEX) {
			if (pokemon.getName().equals(name)) {
				return pokemon;
			}
		}
		return null;
	}
	public static List<Pokedex> getPokedex() {
		return POKEDEX; 
	}
	public static int getPokedexSize() { 
		return POKEDEX.size(); 
	}	
	/** END POKEDEX ARRAYLIST GETTERS **/
	
	/** CAN EVOLVE METHOD **/
	public boolean canEvolve() {		
		
		// pokemon can't evolve if evLevel is -1
		return this.getEvLevel() != -1;
	}
	/** END CAN EVOLVE METHOD **/
	
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
	
	/** ADD NEW MOVE METHOD **/
	public boolean addMove(Move move) { 
		
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
		
		for (Move move : moveSet) {
			System.out.println(move.getName() + " : (TYPE: " + move.getMove().getType()  + "), (PP: " + move.getpp() + 
					"), (PWR: " + move.getMove().getPower() + ")"	+ ", (ACC: " + move.getMove().getAccuracy() + ")");
		}
	}
	/** END LIST MOVE SET METHOD **/
	
	/** GETTERS AND SETTERS **/
	public String getName() { return name.toUpperCase(); }
	public void setName(String name) { this.name = name; }	
	public int getIndex() {	return index; }
	public void setIndex(int index) { this.index = index; }

	public TypeEngine getType() { return type; }
	public void setType(TypeEngine type) { this.type = type; }	
	public List<TypeEngine> getTypes() { return types; }	
	public void setTypes(List<TypeEngine> types) { this.types = types; }	
	public String printTypes() {
		String s = "";		
		int i = types.size() - 1;
		for (TypeEngine t : types) {
			 s += t.printType();		
			 if (i-- != 0)
				 s += " / ";
		}
		return s;
	}
	public String printTypesShort() {
		String s = "";		
		int i = types.size() - 1;
		for (TypeEngine t : types) {
			 s += t.toString().charAt(0);
			 s += t.toString().toLowerCase().charAt(1);
			 if (i-- != 0)
				 s += "/";
		}
		return s;
	}
	
	public int getLevel() {	return level; }	
	public void setLevel(int level) { 	
		
		// don't initiate twice on same pokemon
		if (level != this.level && level != -1) {
			this.level = level;			
			this.hp = (int)(Math.floor(((2 * this.hp + this.iv + Math.floor(ev / 4)) * level) / 100) + level + 10);
			this.bhp = hp;
		}
	}
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
	public int getEV() { return ev; }
	public void setEV(int ev) { this.ev = ev; }	
	public int getIV() { return iv; }
	public void setIV(int iv) { this.iv = iv; }

	public StatusEngine getStatus() { return status; }
	public void setStatus(StatusEngine status) { this.status = status; }	
	public int getStatusCounter() { return statusCounter; }
	public void setStatusCounter(int statusCounter) { this.statusCounter = statusCounter; }	
	public int getStatusLimit() { return statusLimit; }
	public void setStatusLimit(int statusLimit) { this.statusLimit = statusLimit; }
	
	public ArrayList<Move> getMoveSet() { return moveSet; }
	public void setMoveSet(ArrayList<Move> moveSet) { this.moveSet = moveSet; }
	
	public boolean isAlive() { return isAlive; }
	public void setAlive(boolean isAlive) {	this.isAlive = isAlive; }
	/** END GETTERS AND SETTERS **/
	
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
	private void outputChange(String stat, int level) {
		
		if (level == 1)
			System.out.println(this.name + "'s " + stat + " rose!");
		else if (level == 2) 
			System.out.println(this.name + "'s " + stat + " greatly rose!");
		else if (level >= 3)
			System.out.println(this.name + "'s " + stat + " drastically rose!");
		else if (level == -1)
			System.out.println(this.name + "'s " + stat + " fell!");
		else if (level == -2)
			System.out.println(this.name + "'s " + stat + " greatly fell!");
		else if (level <= -3)
			System.out.println(this.name + "'s " + stat + " severely fell!");
	}
}
/*** END POKEDEX CLASS ***/

@FunctionalInterface
interface Calculate {
	public int compute(int j, int k, int l, int m);
}