package pokemon;

import moves.MoveEngine;

public class Geodude extends Pokemon {
	
	public Geodude (int level, int hp, int speed, int attack, int defense, int spAttack, int spDefense) {
		
		super("Geodude", rock, level, hp, speed, attack, defense, spAttack, spDefense, 25);
				
		MoveEngine tackle = new MoveEngine("Tackle", normal, 35, 40, 100);
		MoveEngine rollout = new MoveEngine("Rollout", rock, 20, 45, 90);
		MoveEngine rockthrow = new MoveEngine("Rock Throw", rock, 15, 75, 90);
		
		this.addMove(tackle);
		this.addMove(rollout);
		this.addMove(rockthrow);
	}	
}