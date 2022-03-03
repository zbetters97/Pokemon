package pokemon;

import moves.MoveEngine;

public class Squirtle extends Pokemon {
	
	public Squirtle (int level, int hp, int speed, int attack, int defense, int spAttack, int spDefense) {
		
		super("Squirtle", water, level, hp, speed, attack, defense, spAttack, spDefense, 36);
		
		MoveEngine tackle = new MoveEngine("Tackle", normal, 35, 40, 100);
		MoveEngine quickAttack = new MoveEngine("Quick Attack", normal, 30, 40, 100);
		MoveEngine waterGun = new MoveEngine("Water Gun", water, 25, 60, 85);
		
		this.addMove(tackle);
		this.addMove(quickAttack);
		this.addMove(waterGun);
	}	
}