package pokemon;

import moves.MoveEngine;

public class Charmander extends Pokemon {

	public Charmander (int level, int hp, int speed, int attack, int defense, int spAttack, int spDefense) {
		
		super("Charmander", fire, level, hp, speed, attack, defense, spAttack, spDefense, 36);
				
		MoveEngine scratch = new MoveEngine("Scratch", normal, 35, 40, 100);
		MoveEngine quickAttack = new MoveEngine("Quick Attack", normal, 30, 40, 100);
		MoveEngine ember = new MoveEngine("Ember", fire, 25, 60, 100);
		
		this.addMove(scratch);
		this.addMove(quickAttack);
		this.addMove(ember);
	}	
}