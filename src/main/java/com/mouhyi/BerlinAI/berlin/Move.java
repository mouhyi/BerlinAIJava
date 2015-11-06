package berlin;

import berlin.infos.MoveInfos;


/**
 * Represents one component of the output of your {@link AI AI}: a list of moves to crush your enemies!
 */
public class Move
{
	private Node from;
	private Node to;
	private int nbSoldiers;
	
	
	/**
	 * Creates a new move.
	 * 
	 * Attention: See the wiki for what is considered a valid move.
	 * 
	 * @param from the starting {@link Node Node} that contains your soldiers.
	 * @param to the ending {@link Node Node} where you want to move your soldiers.
	 * @param nbSoldiers the number of soldiers you want to move.
	 */
	public Move(Node from, Node to, int nbSoldiers)
	{
		this.from = from;
		this.to = to;
		this.nbSoldiers = nbSoldiers;
	}
	
	
	MoveInfos toMoveInfos()
	{
		MoveInfos m = new MoveInfos();
		
		m.from = from.getId();
		m.to = to.getId();
		m.number_of_soldiers = nbSoldiers;
		
		return m;
	}
}
