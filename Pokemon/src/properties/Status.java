package properties;

import configuration.Style;

/*** STATUS CLASS ***/
public enum Status {
	/*** STATUS CHART REFERENCE: https://pokemon.fandom.com/wiki/Status_Effects ***/
	
	PARALYZE ("Paralyze", "paralyzed", "PRZ"),
	POISON ("Poison", "poisoned", "PSN"),
	CONFUSE ("Confuse", "confused", "CNF"),
	BURN ("Burn", "burned", "BRN"),
	FREEZE ("Freeze", "frozen", "FRZ"),
	SLEEP ("Sleep", "asleep", "SLP");

    private String name, condition, abr;
    
    /** CONSTRUCTOR **/
    Status(String name, String condition, String abr) {    
    	this.name = name;
    	this.condition = condition;        
    	this.abr = abr;    	
    }
    /** END CONSTRUCTOR **/
	
	/** GETTERS **/
    public String getName() { return this.name; }
    public String getCondition() { return this.condition; }
	public String getAbreviation() { return this.abr; }
	
	public String printName() {
		String color = "";
		
		switch (this.abr) {  	
	    	case ("PRZ"): color = Style.YELLOW; break;
	    	case ("PSN"): color = Style.PURPLE; break;
	    	case ("CNF"): color = Style.PINK; break;
	    	case ("BRN"): color = Style.RED; break;
	    	case ("FRZ"): color = Style.BLUE; break;
	    	case ("SLP"): color = Style.WHITE; break;
		}		
		return color + this.abr + Style.END; 
	}
	/** END GETTERS **/
}
/*** END STATUS CLASS ***/