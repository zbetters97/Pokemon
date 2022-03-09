package moves;

import types.TypeEngine;

public enum MoveEngine {
	
	TACKLE ("Tackle", TypeEngine.normal, 35, 40, 100),
	QUICKATTACK ("Quick Attack", TypeEngine.normal, 30, 40, 100, true),
	THUNDERSHOCK ("Thunder Shock", TypeEngine.electric, 40, 60, 100),
	SCRATCH ("Scratch", TypeEngine.normal, 35, 40, 100),
	EMBER ("Ember", TypeEngine.fire, 25, 60, 100),
	WATERGUN ("Water Gun", TypeEngine.water, 25, 60, 100),
	ROLLOUT ("Rollout", TypeEngine.rock, 20, 45, 90),
	ROCKTHROW ("Rock Throw", TypeEngine.ground, 15, 75, 90);
	
	private String name;
	private TypeEngine type;
	
	private int pp;
	private int accuracy;
	private int power;
	private boolean goFirst;
	
	MoveEngine (String name, TypeEngine type, int pp, int power, int accuracy, boolean goFirst) {
		this.name = name;
		this.type = type;
		this.pp = pp;
		this.accuracy = accuracy;
		this.power = power;
		this.goFirst = goFirst;
	}
	
	MoveEngine (String name, TypeEngine type, int pp, int power, int accuracy) {
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