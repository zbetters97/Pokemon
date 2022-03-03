package pokemon;

import moves.MoveEngine;

public interface PokemonBase {
	
	//public void move(Pokemon pokemon, Move move);
	public void listMoves();
	public boolean addMove(MoveEngine move);
}