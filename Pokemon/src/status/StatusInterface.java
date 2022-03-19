package status;

public interface StatusInterface {
	
	enum Status {
		PARALYZE, POISON, CONFUSE, BURN, FREEZE, SLEEP;
	}	
	
	public String getCondition();
}