package pokemon;

import moves.Moves;

/*** POKEDEX INTERFACE ***/
public interface PokedexInterface {
	Pokedex evolve();
	boolean canEvolve();
	boolean addMove(Moves move);
	void listMoves();
}
/*** END POKEDEX INTERFACE ***/