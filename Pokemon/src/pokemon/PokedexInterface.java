package pokemon;

import moves.Moves;

public interface PokedexInterface {
	
	Pokedex evolve();
	boolean canEvolve();
	boolean addMove(Moves move);
	void listMoves();
}