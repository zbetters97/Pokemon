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
		/*** TYPE CHART REFERENCE: https://pokemondb.net/type ***/
		
		normal = new TypeEngine(TypeEngine.Type.NORMAL, 0.5);
		normal.resistantTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.ROCK, TypeEngine.Type.STEEL)), 0.5);		
		normal.resistantTo(TypeEngine.Type.GHOST, 0.0);		
		normal.vulnerableTo(TypeEngine.Type.FIGHTING, 2.0);
		
		fire = new TypeEngine(TypeEngine.Type.FIRE, 0.5); 	
		fire.resistantTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.FIRE, TypeEngine.Type.GRASS, TypeEngine.Type.ICE, 
				TypeEngine.Type.BUG, TypeEngine.Type.STEEL, TypeEngine.Type.FAIRY)), 0.5); 	
		fire.vulnerableTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.WATER, TypeEngine.Type.GROUND, TypeEngine.Type.ROCK)), 2.0);
		
		water = new TypeEngine(TypeEngine.Type.WATER, 0.5); 
		water.resistantTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.FIRE, TypeEngine.Type.WATER, TypeEngine.Type.ICE, 
				TypeEngine.Type.STEEL)), 0.5);		
		water.vulnerableTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.ELECTRIC, TypeEngine.Type.GRASS)), 2.0);
		
		electric = new TypeEngine(TypeEngine.Type.ELECTRIC, 0.5); 
		electric.resistantTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.ELECTRIC, TypeEngine.Type.FLYING, TypeEngine.Type.STEEL)), 0.5);
		electric.vulnerableTo(TypeEngine.Type.GROUND, 2.0);
		
		grass = new TypeEngine(TypeEngine.Type.GRASS, 0.5); 
		grass.resistantTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.WATER, TypeEngine.Type.ELECTRIC, TypeEngine.Type.GRASS, 
				TypeEngine.Type.GROUND)), 0.5);
		grass.vulnerableTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.FIRE, TypeEngine.Type.ICE, TypeEngine.Type.POISON, 
				TypeEngine.Type.FLYING, TypeEngine.Type.BUG)), 2.0);
		
		ice = new TypeEngine(TypeEngine.Type.ICE, 0.5); 
		ice.resistantTo(TypeEngine.Type.ICE, 0.5);		
		ice.vulnerableTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.FIRE, TypeEngine.Type.FIGHTING, TypeEngine.Type.ROCK, 
				TypeEngine.Type.STEEL)), 2.0);
		
		fighting = new TypeEngine(TypeEngine.Type.FIGHTING, 0.5); 
		fighting.resistantTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.BUG, TypeEngine.Type.ROCK, TypeEngine.Type.DARK)), 0.5);
		fighting.vulnerableTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.FLYING, TypeEngine.Type.PSYCHIC, TypeEngine.Type.FAIRY)), 2.0);
		
		poison = new TypeEngine(TypeEngine.Type.POISON, 0.5); 
		poison.resistantTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.GRASS, TypeEngine.Type.FIGHTING, TypeEngine.Type.POISON, 
				TypeEngine.Type.BUG, TypeEngine.Type.FAIRY)), 0.5);
		poison.vulnerableTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.GROUND, TypeEngine.Type.PSYCHIC)), 2.0);
		
		ground = new TypeEngine(TypeEngine.Type.GROUND, 0.5); 
		ground.resistantTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.POISON, TypeEngine.Type.ROCK)), 0.5);
		ground.resistantTo(TypeEngine.Type.ELECTRIC, 0.0);
		
		flying = new TypeEngine(TypeEngine.Type.FLYING, 0.5); 
		flying.resistantTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.GRASS, TypeEngine.Type.FIGHTING, TypeEngine.Type.BUG)), 0.5);
		flying.resistantTo(TypeEngine.Type.GROUND, 0.0);
		flying.vulnerableTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.ELECTRIC, TypeEngine.Type.ICE, TypeEngine.Type.ROCK)), 2.0);
		
		psychic = new TypeEngine(TypeEngine.Type.PSYCHIC, 0.5); 
		psychic.resistantTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.FIGHTING, TypeEngine.Type.PSYCHIC)), 0.5);
		psychic.vulnerableTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.BUG, TypeEngine.Type.GHOST, TypeEngine.Type.DARK)), 2.0);
		
		bug = new TypeEngine(TypeEngine.Type.BUG, 0.5); 
		bug.resistantTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.GRASS, TypeEngine.Type.FIGHTING, TypeEngine.Type.GROUND)), 0.5);
		bug.vulnerableTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.FIRE, TypeEngine.Type.FLYING, TypeEngine.Type.ROCK)), 2.0);
		
		rock = new TypeEngine(TypeEngine.Type.ROCK, 0.5); 
		rock.resistantTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.NORMAL, TypeEngine.Type.FIRE, TypeEngine.Type.POISON, 
				TypeEngine.Type.FLYING)), 0.5);		
		rock.vulnerableTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.WATER, TypeEngine.Type.GRASS, TypeEngine.Type.FIGHTING, 
				TypeEngine.Type.GROUND, TypeEngine.Type.STEEL)), 2.0);
		
		ghost = new TypeEngine(TypeEngine.Type.GHOST, 0.5); 
		ghost.resistantTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.POISON, TypeEngine.Type.BUG)), 0.5);	
		ghost.resistantTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.NORMAL, TypeEngine.Type.FIGHTING)), 0.0);		
		ghost.vulnerableTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.GHOST, TypeEngine.Type.DARK)), 2.0);
		
		dragon = new TypeEngine(TypeEngine.Type.DRAGON, 0.5);
		dragon.resistantTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.FIRE, TypeEngine.Type.WATER, TypeEngine.Type.ELECTRIC, 
				TypeEngine.Type.GRASS)), 0.5);		
		dragon.vulnerableTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.ICE, TypeEngine.Type.DRAGON, TypeEngine.Type.FAIRY)), 2.0);
		
		dark = new TypeEngine(TypeEngine.Type.DARK, 0.5); 
		dark.resistantTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.GHOST, TypeEngine.Type.DARK)), 0.5);		
		dark.resistantTo(TypeEngine.Type.PSYCHIC, 0.0);
		dark.vulnerableTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.FIGHTING, TypeEngine.Type.BUG, TypeEngine.Type.FAIRY)), 2.0);
		
		steel = new TypeEngine(TypeEngine.Type.STEEL, 0.5); 
		steel.resistantTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.NORMAL, TypeEngine.Type.GRASS, TypeEngine.Type.ICE, 
				TypeEngine.Type.FLYING, TypeEngine.Type.PSYCHIC, TypeEngine.Type.BUG,
				TypeEngine.Type.ROCK, TypeEngine.Type.DRAGON, TypeEngine.Type.STEEL, 
				TypeEngine.Type.FAIRY)), 0.5);	
		steel.resistantTo(TypeEngine.Type.POISON, 0.0);
		steel.vulnerableTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.FIRE, TypeEngine.Type.FIGHTING, TypeEngine.Type.GROUND)), 2.0);
		
		fairy = new TypeEngine(TypeEngine.Type.FAIRY, 0.5); 
		fairy.resistantTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.FIGHTING, TypeEngine.Type.BUG, TypeEngine.Type.DARK)), 0.5);		
		fairy.resistantTo(TypeEngine.Type.DRAGON, 0.0);
		fairy.vulnerableTo(new ArrayList<TypeEngine.Type>(Arrays.asList(
				TypeEngine.Type.POISON, TypeEngine.Type.STEEL)), 2.0);
	}
	
	public ArrayList<TypeEngine> getTypeEngine() {		
		return types;
	}
    
	public void resistantTo(TypeInterface.Type type, Double strength) {
        resistance.add(new TypeEngine(type, strength));
    }
    public void resistantTo(ArrayList<TypeEngine.Type> typeList, Double strength) {    	
    	for (TypeInterface.Type type : typeList) {
    		 resistance.add(new TypeEngine(type, strength));
    	}       
    }
    
    public void vulnerableTo(TypeInterface.Type type, Double strength) { 
    	vulnerability.add(new TypeEngine(type, strength));	
    }
    public void vulnerableTo(ArrayList<TypeEngine.Type> typeList, Double strength) {    	
    	for (TypeInterface.Type type : typeList) {
    		 vulnerability.add(new TypeEngine(type, strength));
    	}       
    }
    
    public List<TypeEngine> getResistance() { return this.resistance; }
    public List<TypeEngine> getVulnerability() { return this.vulnerability; }  
 
    public Double getStrength() { return strength; }

    public String toString() { return type.toString(); }
}