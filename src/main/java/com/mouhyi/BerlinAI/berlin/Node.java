package berlin;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents one component of the Map which is a graph (a set of nodes linked together).
 * 
 * A node can be occupied by a certain number of soldiers of a {@link Player player}.
 */
public class Node
{
	private int id;
	private Player player;
	private int nbSoldiers;
	private String type;
	private List<Node> paths;
	

	Node(int id, String type)
	{
		this.id = id;
		this.type = type;
		
		this.player = null;
		this.nbSoldiers = 0;
		
		this.paths = new ArrayList<Node>();
	}
	
	
	int getId()
	{
		return id;
	}
	

	/**
	 * Get the type of the node which should be "node" or "city".
	 * @return the type of the node.
	 */
	public String getType()
	{
		return type;
	}

	
	void addAdjacent(Node to)
	{
		paths.add(to);
	}	
	
	
	/**
	 * Get the nodes that are linked to this node.
	 * @return the nodes linked to this node.
	 */
	public List<Node> getAdjacents()
	{
		return paths;
	}	


	/**
	 * Get the number of soldiers on this node.
	 * @return the number of soldiers on this node.
	 */
	public int getNbSoldiers()
	{
		return nbSoldiers;
	}
	
	
	void setNbSoldiers(int nbSoldiers)
	{
		this.nbSoldiers = nbSoldiers;
	}	
	
	
	/**
	 * Get the owner of this node.
	 * @return the owner of this node.
	 */
	public Player getPlayer()
	{
		return player;
	}


	void setPlayer(Player player)
	{
		this.player = player;
	}
	
	
	/**
	 * Checks if this node is equal to another node
	 */
	public boolean equals(Object obj)
	{
        if (obj == null)
            return false;
        
        if (obj == this)
            return true;
        
        if (obj.getClass() != getClass())
            return false;

        return this.id == ((Node) obj).id;
    }
}
