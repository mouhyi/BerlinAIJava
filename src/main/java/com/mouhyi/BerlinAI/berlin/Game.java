package berlin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import berlin.infos.InfosInfos;
import berlin.infos.MapInfos;
import berlin.infos.StateInfos;


/**
 * Represents the state of the game.
 * 
 * Contains all the information necessary for your {@link AI AI} to make the best {@link Move moves}.
 */
public class Game
{
	private Infos infos;
	private List<Move> moves;
	private GameMap map;
	private java.util.Map<Integer, Player> players;
	
	
	Game(InfosInfos infos, MapInfos mapInfos, List<StateInfos> states)
	{
		this.infos = new Infos(infos);
		this.players = new HashMap<Integer, Player>();
		this.map = new GameMap(mapInfos, states);
		this.moves = new ArrayList<Move>();
		
		this.setPlayersStates(states);
		this.map.setState(states, players);
	}
	
	
	void onTurn(int turn, List<StateInfos> states)
	{
		moves.clear();
		
		infos.setCurrentTurn(turn);
		setPlayersStates(states);
		map.setState(states, players);
	}
	
	
	/**
	 * Get general information about the game.
	 * @return an object that contains general information about the game.
	 */
	public Infos getInfos()
	{
		return infos;
	}
	
	
	/**
	 * Get the list of {@link Move moves} you must populate on each turn.
	 * @return the list of {@link Move moves} to populate.
	 */
	public List<Move> getMoves()
	{
		return moves;
	}
	
	
	/**
	 * Add a {@link Move move} to the list of {@link Move moves} you must populate on each turn.
	 * 
	 * Attention: See the wiki for what is considered a valid move.
	 * 
	 * @param from the starting {@link Node node} that contains your soldiers.
	 * @param to the ending {@link Node node} where you want to move your soldiers.
	 * @param nbSoldiers the number of soldiers you want to move.
	 */
	public void addMove(Node from, Node to, int nbSoldiers)
	{
		moves.add(new Move(from, to, nbSoldiers));
	}


	/**
	 * Get the {@link Player player} controlled by your {@link AI AI}.
	 * @return the {@link Player player} controlled by your {@link AI AI}.
	 */
	public Player getActivePlayer()
	{
		return this.players.get(infos.getPlayerId());
	}
	
	
	/**
	 * Get all the {@link Player players} in the game.
	 * @return all the {@link Player players} in the game.
	 */
	public List<Player> getPlayers()
	{
		return new ArrayList<Player>(players.values());
	}
	
	
	/**
	 * Get all the {@link Node nodes} in the game.
	 * @return all the {@link Node nodes} in the game.
	 */
	public List<Node> getNodes()
	{
		return map.getNodes();
	}
	
	
	private void setPlayersStates(List<StateInfos> states)
	{
		for (Player p : players.values())
			p.initialize();
		
		for (StateInfos si : states)
		{
			if (si.player_id == null)
				continue;
			
			if (!players.containsKey(si.player_id))
				players.put(si.player_id, new Player(si.player_id));
			
			Player p = players.get(si.player_id);
			
			p.addSoldiers(si.number_of_soldiers);
			p.addNode(map.getNode(si.node_id));
		}
	}	
}
