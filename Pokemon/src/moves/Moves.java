package moves;
import java.util.Arrays;
import java.util.List;

import status.StatusEngine;
import types.TypeEngine;

public enum Moves {
	
	
	// attack descriptions reference (GEN IV): https://www.serebii.net/attackdex-dp/
	CONFUSE("Confuse", "Physical", TypeEngine.normal, 1, 40, -1, null),
	AGILITY ("Agility", "Attribute", TypeEngine.psychic, true, 30, -1, 1, Arrays.asList("speed"), "The user relaxes and lightens its body to move faster. It sharply boosts the Speed stat."),
	AQUATAIL ("Aqua Tail", "Physical", TypeEngine.water, 10, 135, 90, "The user attacks by swinging its tail as if it were a vicious wave in a raging storm."),
	BLAZEKICK ("Blaze Kick", "Physical", TypeEngine.fire, StatusEngine.burn, 0.10, 10, 85, 90, "The user launches a kick with a high critical-hit ratio. It may also leave the target with a burn."),
	BUBBLE ("Bubble", "Special", TypeEngine.water, 30, 20, 100, "A spray of countless bubbles is jetted at the foe."),
	CALMMIND ("Calm Mind", "Attribute", TypeEngine.psychic, true, 20, -1, 1, Arrays.asList("sp. attack", "sp. defense"), "The user quietly focuses its mind and calms its spirit to raise its Sp. Atk and Sp. Def stats."),
	CONFUSERAY ("Confuse Ray", "Status", TypeEngine.ghost, StatusEngine.confuse, 10, 100, "The foe is exposed to a sinister ray that triggers confusion."),
	CONFUSION ("Confusion", "Special", TypeEngine.psychic, 25, 75, 100, "The foe is hit by a weak telekinetic force. It may also leave the foe confused."),
	CROSSCHOP ("Cross Chop", "Physical", TypeEngine.fighting, 5, 150, 80, "The user delivers a double chop with its forearms crossed. It has a high critical-hit ratio."),
	DARKPULSE ("Dark Pulse", "Special", TypeEngine.dark, 15, 80, 100, "The user releases a horrible aura imbued with dark thoughts. It may also make the target flinch."),
	DEFENSECURL ("Defense Curl", "Attribute", TypeEngine.normal, true, 40, -1, 1, Arrays.asList("defense"), "The user curls up to conceal weak spots and raise its Defense stat."),
	DOUBLEEDGE ("Double Edge", "Physical", TypeEngine.steel, 10, 80, 100, "A reckless, life- risking tackle. It also damages the user by a fairly large amount, however."),
	DOUBLEKICK ("Double Kick", "Physical", TypeEngine.fighting, 35, 60, 100, "The foe is quickly kicked twice in succession using both feet."),
	DRAGONBREATH ("Dragon Breath", "Special", TypeEngine.dragon, 20, 60, 100, "The user exhales a mighty gust that inflicts damage. It may also paralyze the target."),
	DRAGONCLAW ("Dragon Claw", "Physical", TypeEngine.dragon, 15, 80, 100, "The user slashes the foe with huge, sharp claws."),
	DRAGONPULSE ("Dragon Pulse", "Physical", TypeEngine.dragon, 10, 90, 100, "The foe is attacked with a shock wave generated by the user's gaping mouth."),
	DYNAMICPUNCH ("Dynamic Punch", "Physical", TypeEngine.fighting, StatusEngine.confuse, 1.0, 5, 150, 50, "The foe is punched with the user's full, concentrated power. It confuses the foe if it hits."),
	EARTHQUAKE ("Earthquake", "Physical", TypeEngine.ground, 10, 150, 100, "The user sets off an earthquake that hits all the Pokémon in the battle."),
	EMBER ("Ember", "Special", TypeEngine.fire, StatusEngine.burn, 0.10, 25, 60, 100, "The foe is attacked with small flames. The target may also be left with a burn."),
	FIREFANG ("Fire Fang", "Physical", TypeEngine.fire, StatusEngine.burn, 0.10, 15, 95, 95, "The user bites with flame-cloaked fangs. It may also make the foe flinch or sustain a burn."),
	FIREPUNCH ("Fire Punch", "Physical", TypeEngine.fire, StatusEngine.burn, 0.10, 15, 75, 100, "The foe is punched with a fiery fist. It may leave the target with a burn."),
	FLAMETHROWER ("Flamethrower", "Special", TypeEngine.fire, StatusEngine.burn, 0.10, 15, 135, 100, "The foe is scorched with an intense blast of fire. The target may also be left with a burn."),
	FLAREBLITZ ("Flare Blitz", "Physical", TypeEngine.fire, StatusEngine.burn, 0.10, 15, 180, 100, "The foe is scorched with an intense blast of fire. The target may also be left with a burn."),
	FLASHCANNON ("Flash Cannon", "Special", TypeEngine.steel, 10, 80, 100, "The user gathers all its light energy and releases it at once."),
	GROWL ("Growl", "Attribute", TypeEngine.normal, false, 40, 100, -1, Arrays.asList("attack"), "The user growls in an endearing way, making the foe less wary. The target's Attack stat is lowered."),
	HEAVYSLAM ("Heavy Slam", "Physical", TypeEngine.normal, 20, 80, 75, "The user slams into the target with its heavy body."),
	HEX ("Hex", "Special", TypeEngine.ghost, 10, 95, 100, "This relentless attack does massive damage to a target affected by status conditions."),
	HYDROPUMP ("Hydro Pump", "Special", TypeEngine.water, 5, 165, 80, "The foe is blasted by a huge volume of water launched under great pressure."),
	HYPNOSIS ("Hypnosis", "Status", TypeEngine.psychic, StatusEngine.sleep, 20, 60, "The user employs hypnotic suggestion to make the target fall into a deep sleep."),
	KINESIS ("Kinesis", "Attribute", TypeEngine.psychic, false, 15, 80, -1, Arrays.asList("accuracy"), "The user distracts the foe by bending a spoon. It may lower the target's accuracy."),
	KNOCKOFF ("Knock Off", "Physical", TypeEngine.dark, 20, 65, 100, "The user slaps down the target's held item, preventing that item from being used in the battle."),
	LEAFBLADE ("Leaf Blade", "Special", TypeEngine.grass, 15, 90, 100, "The foe is slashed with a sharp leaf."),
	LEAFSTORM ("Leaf Storm", "Special", TypeEngine.grass, 5, 140, 90, "A storm of sharp leaves is whipped up."),
	LEER ("Leer", "Attribute", TypeEngine.normal, false, 30, 100, -1, Arrays.asList("defense"), "The foe is given an intimidating leer with sharp eyes. The target's Defense stat is reduced."),
	LICK ("Lick", "Physical", TypeEngine.ghost, StatusEngine.paralyze, 0.10, 30, 45, 100, "The foe is licked with a long tongue, causing damage. It may also paralyze the target."),
	LOWKICK ("Low Kick", "Physical", TypeEngine.fighting, 20, 40, 100, "A powerful low kick that makes the foe fall over. It inflicts greater damage on heavier foes."),
	LOWSWEEP ("Low Sweep", "Physical", TypeEngine.fighting, 20, 95, 100, "The user makes a swift attack on the target's legs."),
	MUDBOMB ("Mud Bomb", "Special", TypeEngine.ground, false, 10, 65, 85, Arrays.asList("accuracy"), "The user launches a hard-packed mud ball to attack. It may also lower the target's accuracy."),
	MUDSHOT ("Mud Shot", "Special", TypeEngine.ground, false, 15, 55, 95, Arrays.asList("defense"), "The user attacks by hurling a blob of mud at the foe. It also reduces the target's Speed."),
	MUDSLAP ("Mud-slap", "Special", TypeEngine.ground, false, 10, 20, 100, Arrays.asList("accuracy"), "The user hurls mud in the foe's face to inflict damage and lower its accuracy."),
	MUDDYWATER ("Muddy Water", "Special", TypeEngine.water, false, 10, 95, 85, Arrays.asList("accuracy"), "The user attacks by shooting out muddy water. It may also lower the foe's accuracy."),
	PAYBACK ("Payback", "Physical", TypeEngine.dark, 10, 50, 100, "If the user can use this attack after the foe attacks, its power is doubled."),
	PETALBLIZZARD ("Petal Blizzard", "Physical", TypeEngine.grass, 15, 135, 100, "The user stirs up a violent petal blizzard and attacks everything around it."),
	PLAYNICE ("Play Nice", "Attribute", TypeEngine.normal, false, 20, -1, -1, Arrays.asList("attack"), "The user and the target become friends, and the target loses its will to fight. This lowers the target's Attack stat."),
	POISONPOWDER ("Poison Powder", "Status", TypeEngine.poison, StatusEngine.poison, 45, 75, "A cloud of poisonous dust is scattered on the foe. It may poison the target."),
	POUND ("Pound", "Physical", TypeEngine.normal, 35, 40, 100, "The foe is physically pounded with a long tail or a foreleg, etc."),
	PSYBEAM ("Psybeam", "Special", TypeEngine.psychic, StatusEngine.confuse, 1.0, 20, 95, 100, "The foe is attacked with a peculiar ray. It may also leave the target confused."),
	PSYCHIC ("Psychic", "Special", TypeEngine.psychic, 10, 135, 100, "The foe is hit by a strong telekinetic force."),
	PSYCHOCUT ("Psycho Cut", "Physical", TypeEngine.psychic, 20, 105, 100, "The user tears at the foe with blades formed by psychic power."),
	QUICKATTACK ("Quick Attack", "Physical", TypeEngine.normal, 30, 40, 100, true, "The user lunges at the foe at a speed that makes it almost invisible. It is sure to strike first."),
	RAZORLEAF ("Razor Leaf", "Physical", TypeEngine.grass, 25, 80, 95, "Sharp-edged leaves are launched to slash at the foe. It has a high critical-hit ratio."),
	ROCKTHROW ("Rock Throw", "Physical", TypeEngine.rock, 15, 75, 90, "The user picks up and throws a small rock at the foe to attack."),
	ROLLOUT ("Rollout", "Physical", TypeEngine.rock, 20, 45, 90, "The user continually rolls into the foe over five turns."),
	SCRATCH ("Scratch", "Physical", TypeEngine.normal, 35, 40, 100, "Hard, pointed, and sharp claws rake the foe to inflict damage."),
	SEISMICTOSS ("Seismic Toss", "Physical", TypeEngine.fighting, 20, -1, 100, "The foe is thrown using the power of gravity. It inflicts damage equal to the user's level."),
	SHADOWBALL ("Shadow Ball", "Special", TypeEngine.ghost, 15, 120, 100, "The user hurls a shadowy blob at the foe."),
	SHADOWPUNCH ("Shadow Punch", "Physical", TypeEngine.ghost, 20, 90, -1, "The user throws a punch at the foe from the shadows. The punch lands without fail."),
	SKYUPPERCUT ("Sky Uppercut", "Physical", TypeEngine.fighting, 15, 120, 100, "The user attacks the foe with an uppercut thrown skyward with force."),
	SLAM ("Slam", "Physical", TypeEngine.normal, 20, 80, 75, "The foe is slammed with a long tail, vines, etc., to inflict damage."),
	SLASH ("Slash", "Physical", TypeEngine.normal, 20, 70, 100, "The foe is attacked with a slash of claws, etc. It has a high critical-hit ratio."),
	SOLARBEAM ("Solar Beam", "Special", TypeEngine.grass, 10, 180, 100, "A two-turn attack. The user gathers light, then blasts a bundled beam on the second turn."),
	SURF ("Surf", "Special", TypeEngine.water, 15, 95, 100, "It swamps the entire battlefield with a giant wave. It can also be used for crossing water."),
	TACKLE ("Tackle", "Physical", TypeEngine.normal, 35, 40, 100, "A physical attack in which the user charges and slams into the foe with its whole body."),
	TAILWHIP ("Tail Whip", "Attribute", TypeEngine.normal, false, 30, 100, -1, Arrays.asList("defense"), "The user wags its tail cutely, making the foe less wary. The target's Defense stat is lowered."),
	TAKEDOWN ("Take Down", "Physical", TypeEngine.normal, 20, 90, 85, "A reckless, full-body charge attack for slamming into the foe. It also damages the user a little."),
	TELEPORT ("Teleport", "Status", TypeEngine.psychic, 20, 0, -1, "Use it to flee from any wild Pokémon."),
	THUNDERBOLT ("Thunder Bolt", "Special", TypeEngine.electric, StatusEngine.paralyze, 0.10, 15, 135, 100, "A strong electric blast is loosed at the foe. It may also leave the foe paralyzed."),
	THUNDERPUNCH ("Thunder Punch", "Physical", TypeEngine.electric, StatusEngine.paralyze, 0.10, 15, 110, 100, "The foe is punched with an electrified fist. It may leave the target with paralysis."),
	THUNDERSHOCK ("Thunder Shock", "Special", TypeEngine.electric, StatusEngine.paralyze, 0.10, 40, 60, 100, "A jolt of electricity is hurled at the foe to inflict damage. It may also leave the foe paralyzed."),
	THUNDERWAVE ("Thunder Wave", "Status", TypeEngine.electric, StatusEngine.paralyze, 20, 90, "A weak electric charge is launched at the foe. It causes paralysis if it hits."),
	TWISTER ("Twister", "Special", TypeEngine.dragon, 20, 40, 100, "The user whips up a vicious tornado to tear at the foe."),
	VINEWHIP ("Vine Whip", "Physical", TypeEngine.grass, 25, 65, 100, "The foe is struck with slender, whiplike vines to inflict damage."),
	VITALTHROW ("Vital Throw", "Physical", TypeEngine.fighting, 10, 105, -1, "The user allows the foe to attack first. In return, this throw move is guaranteed not to miss."),
	WATERGUN ("Water Gun", "Special", TypeEngine.water, 25, 60, 100, "The foe is blasted with a forceful shot of water."),
	WATERPULSE ("Water Pulse", "Special", TypeEngine.water, 20, 90, 100, "The user attacks the foe with a pulsing blast of water. It may also confuse the foe."),
	XSCISSOR ("X-scissor", "Special", TypeEngine.bug, 15, 80, 100, "The user slashes at the foe by crossing its scythes or claws as if they were a pair of scissors.");
	
	private String name;
	private String mtype;
	private TypeEngine type;
	private StatusEngine effect;
	private Double probability;
	
	private String info;
	
	private boolean toSelf;
	private int pp;
	private int accuracy;
	private int power;
	private int level;
	private List<String> stats;
	private boolean goFirst;
	
	Moves (String name, String mtype, TypeEngine type, int pp, int power, int accuracy, boolean goFirst, String info) {
		this.name = name;
		this.mtype = mtype;
		this.type = type;
		this.pp = pp;		
		this.power = power;
		this.accuracy = accuracy;
		this.goFirst = goFirst;
		this.info = info;
	}	
	Moves (String name, String mtype, TypeEngine type, int pp, int power, int accuracy, String info) {
		this.name = name;
		this.mtype = mtype;
		this.type = type;
		this.pp = pp;
		this.accuracy = accuracy;
		this.power = power;
		this.info = info;
	}	
	Moves (String name, String mtype, TypeEngine type, StatusEngine effect, Double probability, int pp, int power, int accuracy, String info) {
		this.name = name;
		this.mtype = mtype;
		this.type = type;
		this.effect = effect;
		this.probability = probability;
		this.pp = pp;
		this.power = power;
		this.accuracy = accuracy;
		this.info = info;
	}	
	Moves (String name, String mtype, TypeEngine type, StatusEngine effect, int pp, int accuracy, String info) {
		this.name = name;
		this.mtype = mtype;
		this.type = type;
		this.effect = effect;
		this.pp = pp;
		this.accuracy = accuracy;
		this.info = info;
	}	
	Moves (String name, String mtype, TypeEngine type, boolean toSelf, int pp, int accuracy, int level, List<String> stats, String info) {
		this.name = name;
		this.mtype = mtype;
		this.type = type;
		this.toSelf = toSelf;
		this.pp = pp;
		this.accuracy = accuracy;
		this.level = level;
		this.stats = stats;
		this.info = info;
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

	public int getAccuracy() { 
		if (accuracy == -1)
			return 100;
		else
			return accuracy; 
	}
	public void setAccuracy(int accuracy) { this.accuracy = accuracy; }

	public int getPower() {	return power; }
	public void setPower(int power) { this.power = power; }
	
	public String getInfo() {	return info; }
	public void setInfo(String info) { this.info = info; }
	
	public boolean getGoFirst() { return goFirst; }
	public void setGoFirst(boolean goFirst) { this.goFirst = goFirst; }
	
	public int getLevel() { return level; }
	public void setLevel(int level) { this.level = level; }
	
	public List<String> getStats() { return stats; }
	public void setStats(List<String> stats) { this.stats = stats; }
}