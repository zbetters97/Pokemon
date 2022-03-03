package pokemon;

import moves.MoveEngine;

public class Pikachu extends Pokemon {
	
	public Pikachu (int level, int hp, int speed, int attack, int defense, int spAttack, int spDefense) {
		
		super("Pikachu", electric, level, hp, speed, attack, defense, spAttack, spDefense, 30);
				
		MoveEngine tackle = new MoveEngine("Tackle", normal, 35, 40, 100);
		MoveEngine quickAttack = new MoveEngine("Quick Attack", normal, 30, 40, 100);
		MoveEngine thunderShock = new MoveEngine("Thunder Shock", electric, 30, 60, 100);
		
		this.addMove(tackle);
		this.addMove(quickAttack);
		this.addMove(thunderShock);
	}	
}