package pokemon;

import moves.MoveEngine;

public interface PokedexInterface {
	
	Pokedex evolve();
	boolean canEvolve();
	boolean addMove(MoveEngine move);
	void listMoves();
}