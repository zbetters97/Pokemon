package pokemon;

import java.util.Arrays;
import java.util.List;

/*** NATURE CLASS ***/
public enum Nature {
	
	/*** NATURE EFFECTS REFERENCE: https://pokemondb.net/mechanics/natures ***/	
	/** ATTACK: 5, DEFENSE: 6, SP ATTACK: 7, SP DEFENSE: 8, SPEED: 9 **/
	
	ADAMANT ("Adamant", 5, 7),
	BASHFUL ("Bashful", 7, 7),
	BOLD ("Bold", 6, 6),
	BRAVE ("Brave", 5, 9),
	CALM ("Calm", 8, 5);
		
    private String name; 
    private int increase, decrease;
    
    private static List<Nature> NATURES = Arrays.asList(Nature.values());
    
    /** CONSTRUCTOR **/
    Nature(String name, int increase, int decrease) {
    	this.increase = increase;  
    	this.decrease = decrease;
    	this.name = name;    	
    }
    /** END CONSTRUCTOR **/
	
	/** GETTERS **/	
	public String getName() { return this.name; }
	public int increase() { return this.increase; }
	public int decrease() { return this.decrease; }
	
	public static List<Nature> getNatures() {
		return NATURES; 
	}
	/** END GETTERS **/
}
/*** END NATURE CLASS ***/