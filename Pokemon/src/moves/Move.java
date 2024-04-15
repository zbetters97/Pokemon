package moves;

/*** MOVE CLASS ***/
public class Move {
	
	/** INITIALIZE VALUES FOR UNIQUE MOVES **/
	private Moves move;
	private int pp;
	private int bpp;
	private int numTurns;
	private int turns;
	/** END INITIALIZE VALUES **/
	
	/** CONSTRUCTORS **/
	public Move (Moves move) {		
		this.move = move;
		this.pp = move.getpp();
		this.bpp = pp;
	}	
	public Move (Moves move, int numTurns) {
		this.move = move;
		this.pp = move.getpp();
		this.bpp = pp;
		this.numTurns = numTurns;
		this.turns = numTurns;
	}
	/** END CONSTRUCTORS **/
	
	/** GETTERS AND SETTERS **/
	public Moves getMove() { return move; }	
	public String getName() { return move.getName(); }
	
	public int getpp() { return pp; }
	public void setpp(int pp) {	this.pp = pp; }
	
	public int getbpp() { return bpp; }
	public void setbpp(int bpp) { this.bpp = bpp; }	
	
	public int getTurns() {	return turns; }
	public void setTurns(int turns) { this.turns = turns; }
	
	public int getNumTurns() {	return numTurns; }
	public void setNumTurns(int numTurns) {	this.numTurns = numTurns; }	
	/** END GETTERS AND SETTERS **/
}
/*** END MOVE CLASS ***/