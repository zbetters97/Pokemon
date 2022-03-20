package status;

public class StatusEngine implements StatusInterface {

	public static StatusEngine paralyze, poison, confuse, burn, freeze, sleep;

    private String condition, name;
    private StatusInterface.Status effect;
    
    public StatusEngine(StatusInterface.Status effect, String condition, String name) {    
    	this.effect = effect;
    	this.condition = condition;        
    	this.name = name;    	
    }

	static {		
		/*** STATUS CHART REFERENCE: https://pokemon.fandom.com/wiki/Status_Effects ***/
		
		paralyze = new StatusEngine(StatusEngine.Status.PARALYZE, "paralyzed", "PRZ");
		poison = new StatusEngine(StatusEngine.Status.POISON, "poisoned", "PSN");
		confuse = new StatusEngine(StatusEngine.Status.CONFUSE, "confused", "CNF");
		burn = new StatusEngine(StatusEngine.Status.BURN, "burned", "BRN");
		freeze = new StatusEngine(StatusEngine.Status.FREEZE, "frozen", "FRZ");
		sleep = new StatusEngine(StatusEngine.Status.SLEEP, "asleep", "SLP");
	}
	
	public String getEffect() { return this.effect.toString(); }
	public String getCondition() { return this.condition; }
	public String getName() { return this.name; }
}