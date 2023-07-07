package status;

/*** STATUS INTERFACE ***/
public interface StatusInterface {
	enum Status {
		PARALYZE, POISON, CONFUSE, BURN, FREEZE, SLEEP;
	}	
	public String getCondition();
}
/*** END STATUS INTERFACE ***/