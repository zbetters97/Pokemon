package moves;
import java.util.Arrays;
import java.util.List;

import status.StatusEngine;
import types.TypeEngine;

public enum Moves {
	
	CONFUSE ("Confuse", "Physical", TypeEngine.normal, 1, 40, -1),
	TACKLE ("Tackle", "Physical", TypeEngine.normal, 35, 40, 100),
	QUICKATTACK ("Quick Attack", "Physical", TypeEngine.normal, 30, 40, 100, true),
	SCRATCH ("Scratch", "Physical", TypeEngine.normal, 35, 40, 100),
	SLASH ("Slash", "Physical", TypeEngine.normal, 20, 70, 100),
	SLAM ("Slam", "Physical", TypeEngine.normal, 20, 80, 75),
	HEAVYSLAM ("Heavy Slam", "Physical", TypeEngine.normal, 20, 80, 75),
	DOUBLEEDGE ("Double Edge", "Physical", TypeEngine.steel, 10, 80, 100),
	TAKEDOWN ("Take Down", "Physical", TypeEngine.normal, 20, 90, 85),
	TAILWHIP ("Tail Whip", "Attribute", TypeEngine.normal, false, 30, 100, -1, Arrays.asList("defense")),
	GROWL ("Growl", "Attribute", TypeEngine.normal, false, 40, 100, -1, Arrays.asList("attack")),
	DEFENSECURL ("Defense Curl", "Attribute", TypeEngine.normal, true, 40, -1, 1, Arrays.asList("defense")),
	PLAYNICE ("Play Nice", "Attribute", TypeEngine.normal, false, 20, -1, -1, Arrays.asList("attack")),
	VINEWHIP ("Vine Whip", "Physical", TypeEngine.grass, 25, 65, 100),
	RAZORLEAF ("Razor Leaf", "Physical", TypeEngine.grass, 25, 80, 95),
	PETALBLIZZARD ("Petal Blizzard", "Physical", TypeEngine.grass, 15, 135, 100),
	SOLARBEAM ("Solar Beam", "Special", TypeEngine.grass, 10, 180, 100),
	POISONPOWDER ("Poison Powder", "Status", TypeEngine.poison, StatusEngine.poison, 45, 75),
	EMBER ("Ember", "Special", TypeEngine.fire, StatusEngine.burn, 0.10, 25, 60, 100),
	FIREFANG ("Fire Fang", "Physical", TypeEngine.fire, StatusEngine.burn, 0.10, 15, 95, 95),
	FLAMETHROWER ("Flamethrower", "Special", TypeEngine.fire, StatusEngine.burn, 0.10, 15, 135, 100),
	FLAREBLITZ ("Flare Blitz", "Physical", TypeEngine.fire, StatusEngine.burn, 0.10, 15, 180, 100),
	WATERGUN ("Water Gun", "Special", TypeEngine.water, 25, 60, 100),
	WATERPULSE ("Water Pulse", "Special", TypeEngine.water, 20, 90, 100),
	AQUATAIL ("Aqua Tail", "Physical", TypeEngine.water, 10, 135, 90),
	HYDROPUMP ("Hydro Pump", "Special", TypeEngine.water, 5, 165, 80),
	THUNDERWAVE ("Thunder Wave", "Status", TypeEngine.electric, StatusEngine.paralyze, 20, 90),
	THUNDERSHOCK ("Thunder Shock", "Special", TypeEngine.electric, StatusEngine.paralyze, 0.10, 40, 60, 100),
	THUNDERPUNCH ("Thunder Punch", "Physical", TypeEngine.electric, StatusEngine.paralyze, 0.10, 15, 110, 100),
	THUNDERBOLT ("Thunder Bolt", "Special", TypeEngine.electric, StatusEngine.paralyze, 0.10, 15, 135, 100),
	TELEPORT ("Teleport", "Status", TypeEngine.psychic, 20, 0, -1),
	HYPNOSIS ("Hypnosis", "Status", TypeEngine.psychic, StatusEngine.sleep, 20, 60),
	KINESIS ("Kinesis", "Attribute", TypeEngine.psychic, false, 15, 80, -1, Arrays.asList("accuracy")),
	CONFUSION ("Confusion", "Special", TypeEngine.psychic, 25, 75, 100),
	PSYBEAM ("Psybeam", "Special", TypeEngine.psychic, 20, 95, 100),
	PSYCHOCUT ("Psycho Cut", "Physical", TypeEngine.psychic, 20, 105, 100),
	PSYCHIC ("Psychic", "Special", TypeEngine.psychic, 10, 135, 100),
	CALMMIND ("Calm Mind", "Attribute", TypeEngine.psychic, true, 20, -1, 1, Arrays.asList("sp. attack", "sp. defense")),
	LOWKICK ("Low Kick", "Physical", TypeEngine.fighting, 20, 40, 100),
	LOWSWEEP ("Low Sweep", "Physical", TypeEngine.fighting, 20, 95, 100),
	KNOCKOFF ("Knock Off", "Physical", TypeEngine.dark, 20, 65, 100),
	VITALTHROW ("Vital Throw", "Physical", TypeEngine.fighting, 10, 105, -1),
	SEISMICTOSS ("Seismic Toss", "Physical", TypeEngine.fighting, 20, -1, 100),
	DYNAMICPUNCH ("Dynamic Punch", "Physical", TypeEngine.fighting, StatusEngine.confuse, 1.0, 5, 150, 50),
	CROSSCHOP ("Cross Chop", "Physical", TypeEngine.fighting, 5, 150, 80),
	ROLLOUT ("Rollout", "Physical", TypeEngine.rock, 20, 45, 90),
	ROCKTHROW ("Rock Throw", "Physical", TypeEngine.rock, 15, 75, 90),
	EARTHQUAKE ("Earthquake", "Physical", TypeEngine.ground, 10, 150, 100),
	CONFUSERAY ("Confuse Ray", "Status", TypeEngine.ghost, StatusEngine.confuse, 10, 100),
	LICK ("Lick", "Physical", TypeEngine.ghost, 30, 45, 100),
	PAYBACK ("Payback", "Physical", TypeEngine.dark, 10, 50, 100),
	HEX ("Hex", "Special", TypeEngine.ghost, 10, 95, 100),
	SHADOWPUNCH ("Shadow Punch", "Physical", TypeEngine.ghost, 20, 90, -1),
	DARKPULSE ("Dark Pulse", "Special", TypeEngine.dark, 15, 80, 100),
	SHADOWBALL ("Shadow Ball", "Special", TypeEngine.ghost, 15, 120, 100),
	FLASHCANNON ("Flash Cannon", "Special", TypeEngine.steel, 10, 80, 100),
	DRAGONBREATH ("Dragon Breath", "Special", TypeEngine.dragon, 20, 60, 100),
	DRAGONCLAW ("Dragon Claw", "Physical", TypeEngine.dragon, 15, 80, 100);
	
	private String name;
	private String mtype;
	private TypeEngine type;
	private StatusEngine effect;
	private Double probability;
	
	private boolean toSelf;
	private int pp;
	private int accuracy;
	private int power;
	private int level;
	private List<String> stats;
	private boolean goFirst;
	
	Moves (String name, String mtype, TypeEngine type, int pp, int power, int accuracy, boolean goFirst) {
		this.name = name;
		this.mtype = mtype;
		this.type = type;
		this.pp = pp;		
		this.power = power;
		this.accuracy = accuracy;
		this.goFirst = goFirst;
	}	
	Moves (String name, String mtype, TypeEngine type, int pp, int power, int accuracy) {
		this.name = name;
		this.mtype = mtype;
		this.type = type;
		this.pp = pp;
		this.accuracy = accuracy;
		this.power = power;
	}	
	Moves (String name, String mtype, TypeEngine type, StatusEngine effect, Double probability, int pp, int power, int accuracy) {
		this.name = name;
		this.mtype = mtype;
		this.type = type;
		this.effect = effect;
		this.probability = probability;
		this.pp = pp;
		this.power = power;
		this.accuracy = accuracy;		
	}	
	Moves (String name, String mtype, TypeEngine type, StatusEngine effect, int pp, int accuracy) {
		this.name = name;
		this.mtype = mtype;
		this.type = type;
		this.effect = effect;
		this.pp = pp;
		this.accuracy = accuracy;
	}	
	Moves (String name, String mtype, TypeEngine type, boolean toSelf, int pp, int accuracy, int level, List<String> stats) {
		this.name = name;
		this.mtype = mtype;
		this.type = type;
		this.toSelf = toSelf;
		this.pp = pp;
		this.accuracy = accuracy;
		this.level = level;
		this.stats = stats;
	}

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getMType() { return mtype; }
	public void setMType(String mtype) { this.mtype = mtype; }
	
	public TypeEngine getType() { return type; }
	public void setType(TypeEngine type) { this.type = type; }

	public StatusEngine getEffect() { return effect; }
	public void setEffect(StatusEngine effect) { this.effect = effect; }
	
	public Double getProbability() { return probability; }
	public void setProbability(Double probability) { this.probability = probability; }	
	
	public boolean isToSelf() { return toSelf; }
	public void setToSelf(boolean toSelf) { this.toSelf = toSelf; }
	
	public int getpp() { return pp; }
	public void setpp(int pp) {	this.pp = pp; }

	public int getAccuracy() { return accuracy; }
	public void setAccuracy(int accuracy) { this.accuracy = accuracy; }

	public int getPower() {	return power; }
	public void setPower(int power) { this.power = power; }
	
	public boolean getGoFirst() { return goFirst; }
	public void setGoFirst(boolean goFirst) { this.goFirst = goFirst; }
	
	public int getLevel() { return level; }
	public void setLevel(int level) { this.level = level; }
	
	public List<String> getStats() { return stats; }
	public void setStats(List<String> stats) { this.stats = stats; }
}