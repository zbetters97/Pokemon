package status;

import application.Style;

/*** STATUS CLASS ***/
public class StatusEngine implements StatusInterface {

	public static StatusEngine paralyze, poison, confuse, burn, freeze, sleep;

    private String condition, name;
    private StatusInterface.Status effect;
    
    /** CONSTRUCTOR **/
    public StatusEngine(StatusInterface.Status effect, String condition, String name) {    
    	this.effect = effect;
    	this.condition = condition;        
    	this.name = name;    	
    }
    /** END CONSTRUCTOR **/
	
    /** STATIC VARIABLES **/
	static {		
		/*** STATUS CHART REFERENCE: https://pokemon.fandom.com/wiki/Status_Effects ***/	
		
		paralyze = new StatusEngine(StatusEngine.Status.PARALYZE, "paralyzed", "PRZ");
		poison = new StatusEngine(StatusEngine.Status.POISON, "poisoned", "PSN");
		confuse = new StatusEngine(StatusEngine.Status.CONFUSE, "confused", "CNF");
		burn = new StatusEngine(StatusEngine.Status.BURN, "burned", "BRN");
		freeze = new StatusEngine(StatusEngine.Status.FREEZE, "frozen", "FRZ");
		sleep = new StatusEngine(StatusEngine.Status.SLEEP, "asleep", "SLP");
	}
	/** END STATIC VARIABLES **/
	
	/** GETTERS **/
	public String getEffect() { return this.effect.toString(); }
	public String getCondition() { return this.condition; }
	
	public String getName() { return this.name; }
	public String printName() {
		String color = "";
		
		switch (this.effect.toString()) {  	
	    	case ("PARALYZE"): 
	    		color = Style.YELLOW;
	    		break;
	    	case ("POISON"): 
	    		color = Style.PURPLE;
	    		break;
	    	case ("CONFUSE"): 
	    		color = Style.PINK;
	    		break;
	    	case ("BURN"): 
	    		color = Style.RED;
	    		break;
	    	case ("FREEZE"): 
	    		color = Style.BLUE;
	    		break;
	    	case ("SLEEP"): 
	    		color = Style.WHITE;
	    		break;
		}		
		return color + this.name + Style.END; 
	}
	/** END GETTERS **/
}
/*** END STATUS CLASS ***/