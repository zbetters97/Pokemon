package types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TypeEngine implements TypeInterface {

	public static TypeEngine normal, fire, water, electric, grass, ice, fighting, 
							poison, ground, flying, psychic, bug, rock, 
							ghost, dragon, dark, steel, fairy;
							
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
		types.addAll(Arrays.asList(normal, fire, water, electric, grass, ice, 
									fighting, poison, ground, flying, psychic, 
									bug, rock, ghost, dragon, dark, steel, fairy));
    }

	static {
		
		normal = new TypeEngine(TypeEngine.Type.NORMAL, 0.5);
		normal.resistantTo(TypeEngine.Type.ROCK, 0.5); normal.resistantTo(TypeEngine.Type.GHOST, 0.0); normal.resistantTo(TypeEngine.Type.STEEL, 0.5);
		normal.vulnerableTo(TypeEngine.Type.FIGHTING, 2.0);
		
		fire = new TypeEngine(TypeEngine.Type.FIRE, 0.5); 
		fire.resistantTo(TypeEngine.Type.FIRE, 0.5); fire.resistantTo(TypeEngine.Type.GRASS, 0.5); fire.resistantTo(TypeEngine.Type.ICE, 0.5); 
		fire.resistantTo(TypeEngine.Type.BUG, 0.5); fire.resistantTo(TypeEngine.Type.STEEL, 0.5); fire.resistantTo(TypeEngine.Type.FAIRY, 0.5); 		
		fire.vulnerableTo(TypeEngine.Type.WATER, 2.0); fire.vulnerableTo(TypeEngine.Type.GROUND, 2.0); fire.vulnerableTo(TypeEngine.Type.ROCK, 2.0);
		
		water = new TypeEngine(TypeEngine.Type.WATER, 0.5); 
		water.resistantTo(TypeEngine.Type.FIRE, 0.5); water.resistantTo(TypeEngine.Type.WATER, 0.5); water.resistantTo(TypeEngine.Type.ICE, 0.5); 
		water.resistantTo(TypeEngine.Type.STEEL, 0.5);
		water.vulnerableTo(TypeEngine.Type.ELECTRIC, 2.0); water.vulnerableTo(TypeEngine.Type.GRASS, 2.0);
		
		electric = new TypeEngine(TypeEngine.Type.ELECTRIC, 0.5); 
		electric.resistantTo(TypeEngine.Type.ELECTRIC, 0.5); electric.resistantTo(TypeEngine.Type.FLYING, 0.5); electric.resistantTo(TypeEngine.Type.STEEL, 0.5);
		electric.vulnerableTo(TypeEngine.Type.GROUND, 2.0);
		
		grass = new TypeEngine(TypeEngine.Type.GRASS, 0.5); 
		grass.resistantTo(TypeEngine.Type.WATER, 0.5); grass.resistantTo(TypeEngine.Type.ELECTRIC, 0.5); grass.resistantTo(TypeEngine.Type.GRASS, 0.5); 
		grass.resistantTo(TypeEngine.Type.GROUND, 0.5);
		grass.vulnerableTo(TypeEngine.Type.FIRE, 2.0); grass.vulnerableTo(TypeEngine.Type.ICE, 2.0); grass.vulnerableTo(TypeEngine.Type.POISON, 2.0); 
		grass.vulnerableTo(TypeEngine.Type.FLYING, 2.0); grass.vulnerableTo(TypeEngine.Type.BUG, 2.0);
		
		ice = new TypeEngine(TypeEngine.Type.ICE, 0.5); 
		
		fighting = new TypeEngine(TypeEngine.Type.FIGHTING, 0.5); 
		
		poison = new TypeEngine(TypeEngine.Type.POISON, 0.5); 
		
		ground = new TypeEngine(TypeEngine.Type.GROUND, 0.5); 
		
		flying = new TypeEngine(TypeEngine.Type.FLYING, 0.5); 
		
		psychic = new TypeEngine(TypeEngine.Type.PSYCHIC, 0.5); 
		
		bug = new TypeEngine(TypeEngine.Type.BUG, 0.5); 
		
		rock = new TypeEngine(TypeEngine.Type.ROCK, 0.5); 
		
		ghost = new TypeEngine(TypeEngine.Type.GHOST, 0.5); 
		
		dragon = new TypeEngine(TypeEngine.Type.DRAGON, 0.5); 
		
		dark = new TypeEngine(TypeEngine.Type.DARK, 0.5); 
		
		steel = new TypeEngine(TypeEngine.Type.STEEL, 0.5); 
		
		fairy = new TypeEngine(TypeEngine.Type.FAIRY, 0.5); 
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