package types;

import java.util.List;

public interface TypeInterface {
	
	enum Type {
        WATER, FIRE, ELECTRIC, ROCK, NORMAL;
    }
	
    public void resistantTo(Type type, Double strength);
    public void vulnerableTo(Type type, Double strength);
    
    public List<TypeEngine> getResistance();
    public List<TypeEngine> getVulnerability();    
    public Double getStrength();
    
    public String toString();
}