package types;

import java.util.List;

public interface TypeInterface {
	
	enum Type {
		NORMAL, FIRE, WATER, ELECTRIC, GRASS, ICE, FIGHTING, 
		POISON, GROUND, FLYING, PSYCHIC, BUG, ROCK, 
		GHOST, DRAGON, DARK, STEEL, FAIRY
    }
	
    public void resistantTo(Type type, Double strength);
    public void vulnerableTo(Type type, Double strength);
    
    public List<TypeEngine> getResistance();
    public List<TypeEngine> getVulnerability();    
    public Double getStrength();
    
    public String toString();
}