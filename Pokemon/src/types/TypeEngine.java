package types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TypeEngine implements TypeInterface {

	public static TypeEngine normal, fire, water, electric, rock;
	
 	private List<TypeEngine> resistance;
    private List<TypeEngine> vulnerability; 
    public ArrayList<TypeEngine> types;
    
    private TypeInterface.Type type;
    private Double strength;

    public TypeEngine(TypeInterface.Type type, Double strength) {
    	
        this.type = type;
        this.strength = strength;
        
        resistance = new ArrayList<>();
        vulnerability = new ArrayList<>();  
        
        types = new ArrayList<>();
		types.addAll(Arrays.asList(normal, fire, water, electric, rock));
    }

	static {

		normal = new TypeEngine(TypeEngine.Type.NORMAL, 0.5); 
		
		fire = new TypeEngine(TypeEngine.Type.FIRE, 0.5); 
		fire.resistantTo(TypeEngine.Type.FIRE, 0.5); fire.vulnerableTo(TypeEngine.Type.WATER, 2.0);
		fire.vulnerableTo(TypeEngine.Type.ROCK, 2.0);
		
		water = new TypeEngine(TypeEngine.Type.WATER, 0.5); 
		water.resistantTo(TypeEngine.Type.WATER, 0.5); water.resistantTo(TypeEngine.Type.FIRE, 0.5);
		water.vulnerableTo(TypeEngine.Type.ELECTRIC, 2.0);
		
		electric = new TypeEngine(TypeEngine.Type.ELECTRIC, 0.5); 
		electric.resistantTo(TypeEngine.Type.ELECTRIC, 0.5);
		electric.vulnerableTo(TypeEngine.Type.ROCK, 2.0);
		
		rock = new TypeEngine(TypeEngine.Type.ROCK, 0.5); 
		rock.resistantTo(TypeEngine.Type.ROCK, 0.5); rock.resistantTo(TypeEngine.Type.FIRE, 0.5);
		rock.vulnerableTo(TypeEngine.Type.WATER, 2.0);
	}
	
	public ArrayList<TypeEngine> getTypes(String type) {		
		return types;
	}
    
    public void resistantTo(TypeInterface.Type type, Double strength) {
        resistance.add(new TypeEngine(type, strength));
    }
    public void vulnerableTo(TypeInterface.Type type, Double strength) { 
    	vulnerability.add(new TypeEngine(type, strength));	
    }   
    
    public List<TypeEngine> getResistance() {
    	return this.resistance;
    }
    public List<TypeEngine> getVulnerability() {
    	return this.vulnerability;
    }    
    public Double getStrength() {
    	return strength;
    }

    public String toString() {
    	return type.toString();
    }
}