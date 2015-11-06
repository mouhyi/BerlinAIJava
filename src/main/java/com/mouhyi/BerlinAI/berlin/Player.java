package berlin;

import java.util.ArrayList;
import java.util.List;


/**
 * Your {@link AI AI} or the {@link AI AI} of another Berlin's {@link Player player}.
 */
public class Player
{
	private int id;
	private int nbSoldiers;
	private List<Node> nodes;
	
	
	Player(int id)
	{
		this.id = id;
		this.nodes = new ArrayList<Node>();
		
		initialize();
	}
	
	
	void initialize()
	{
		nbSoldiers = 0;
		nodes.clear();
	}
	
	
	void addNode(Node node)
	{
		nodes.add(node);
	}


	void addSoldiers(int nbSoldiers)
	{
		this.nbSoldiers += nbSoldiers;
	}


	/**
	 * Get the nodes controlled by this player
	 * @return a list of nodes controller by this player
	 */
	public List<Node> getNodes()
	{
		return nodes;
	}
	
	
	/**
	 * Checks if this player is equal to another player
	 */
	public boolean equals(Object obj)
	{
        if (obj == null)
            return false;
        
        if (obj == this)
            return true;
        
        if (obj.getClass() != getClass())
            return false;

        return this.id == ((Player) obj).id;
    }
}
