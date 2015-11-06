package berlin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import berlin.infos.MapInfos;
import berlin.infos.NodeInfos;
import berlin.infos.PathInfos;
import berlin.infos.StateInfos;


class GameMap
{
	private java.util.Map<Integer, Node> nodes;
	
	
	GameMap(MapInfos infos, List<StateInfos> states)
	{
		this.nodes = new HashMap<Integer, Node>();
		
		// create the nodes
		for (NodeInfos ni : infos.nodes)
			nodes.put(ni.id, new Node(ni.id, ni.type));
		
		// create the paths
		for (PathInfos pi : infos.paths)
		{
			nodes.get(pi.from).addAdjacent(nodes.get(pi.to));
			nodes.get(pi.to).addAdjacent(nodes.get(pi.from));
		}
	}
	
	
	Node getNode(int id)
	{
		return nodes.get(id);
	}
	
	
	List<Node> getNodes()
	{
		return new ArrayList<Node>(nodes.values());
	}
	
	
	void setState(List<StateInfos> states, java.util.Map<Integer, Player> players)
	{		
		for (StateInfos si : states)
		{
			Node n = nodes.get(si.node_id);
			
			n.setPlayer(si.player_id == null ? null : players.get(si.player_id));
			n.setNbSoldiers(si.number_of_soldiers);
		}
	}
}
