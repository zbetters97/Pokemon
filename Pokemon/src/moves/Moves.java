package moves;

import types.TypeEngine;

public enum Moves {
	
	TACKLE ("Tackle", TypeEngine.normal, 35, 40, 100),
	QUICKATTACK ("Quick Attack", TypeEngine.normal, 30, 40, 100, true),
	SCRATCH ("Scratch", TypeEngine.normal, 35, 40, 100),
	SLASH ("Slash", TypeEngine.normal, 20, 70, 100),
	SLAM ("Slam", TypeEngine.normal, 20, 80, 75),
	DOUBLEEDGE ("Double Edge", TypeEngine.normal, 15, 120, 100),
	TAKEDOWN ("Take Down", TypeEngine.normal, 20, 90, 85),
	SHELLSMASH ("Shell Smash", TypeEngine.normal, 15, 50, 100, true),
	VINEWHIP ("Vine Whip", TypeEngine.grass, 25, 65, 100),
	RAZORLEAF ("Razor Leaf", TypeEngine.grass, 25, 80, 95),
	PETALBLIZZARD ("Petal Blizzard", TypeEngine.grass, 15, 135, 100),
	SOLARBEAM ("Solar Beam", TypeEngine.grass, 10, 180, 100),
	EMBER ("Ember", TypeEngine.fire, 25, 60, 100),
	FIREFANG ("Fire Fang", TypeEngine.fire, 15, 95, 95),
	FLAMETHROWER ("Flamethrower", TypeEngine.fire, 15, 135, 100),
	FLAREBLITZ ("Flare Blitz", TypeEngine.fire, 15, 180, 100),
	WATERGUN ("Water Gun", TypeEngine.water, 25, 60, 100),
	WATERPULSE ("Water Pulse", TypeEngine.water, 20, 90, 100),
	AQUATAIL ("Aqua Tail", TypeEngine.water, 10, 135, 90),
	HYDROPUMP ("Hydro Pump", TypeEngine.water, 5, 165, 80),
	THUNDERSHOCK ("Thunder Shock", TypeEngine.electric, 40, 60, 100),
	THUNDERPUNCH ("Thunder Punch", TypeEngine.electric, 15, 110, 100),
	THUNDERBOLT ("Thunder Bolt", TypeEngine.electric, 15, 135, 100),
	TELEPORT ("Teleport", TypeEngine.psychic, 20, 0, -1),
	CONFUSION ("Confusion", TypeEngine.psychic, 25, 75, 100),
	PSYBEAM ("Psybeam", TypeEngine.psychic, 20, 95, 100),
	PSYCHOCUT ("Psycho Cut", TypeEngine.psychic, 20, 105, 100),
	PSYCHIC ("Psychic", TypeEngine.psychic, 10, 135, 100),
	ROLLOUT ("Rollout", TypeEngine.rock, 20, 45, 90),
	ROCKTHROW ("Rock Throw", TypeEngine.ground, 15, 75, 90),
	DRAGONBREATH ("Dragon Breath", TypeEngine.dragon, 20, 60, 100),
	DRAGONCLAW ("Dragon Claw", TypeEngine.dragon, 15, 80, 100);
	
	private String name;
	private TypeEngine type;
	
	private int pp;
	private int accuracy;
	private int power;
	private boolean goFirst;
	
	Moves (String name, TypeEngine type, int pp, int power, int accuracy, boolean goFirst) {
		this.name = name;
		this.type = type;
		this.pp = pp;
		this.accuracy = accuracy;
		this.power = power;
		this.goFirst = goFirst;
	}
	
	Moves (String name, TypeEngine type, int pp, int power, int accuracy) {
		this.name = name;
		this.type = type;
		this.pp = pp;
		this.accuracy = accuracy;
		this.power = power;
	}

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public TypeEngine getType() { return type; }
	public void setType(TypeEngine type) { this.type = type; }

	public int getpp() { return pp; }
	public void setpp(int pp) {	this.pp = pp; }

	public int getAccuracy() { return accuracy; }
	public void setAccuracy(int accuracy) { this.accuracy = accuracy; }

	public int getPower() {	return power; }
	public void setPower(int power) { this.power = power; }
	
	public boolean getGoFirst() { return goFirst; }
	public void setGoFirst(boolean goFirst) { this.goFirst = goFirst; }
}