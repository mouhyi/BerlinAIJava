package berlin;

import berlin.infos.InfosInfos;


/**
 * Contains general informations about the game.
 * 
 * Contains useful information like the current turn and the number of players.
 */
public class Infos
{
	private String gameId;
	private Integer currentTurn;
	private int maximumTurns;
	private int nbPlayers;
	private int timeLimitPerTurn;
	private boolean directed;
	private int playerId;
	
	
	Infos(InfosInfos infos)
	{
		gameId = infos.game_id;
		currentTurn = infos.current_turn;
		maximumTurns = infos.maximum_number_of_turns;
		nbPlayers = infos.number_of_players;
		timeLimitPerTurn = infos.time_limit_per_turn;
		directed = infos.directed;
		playerId = infos.player_id;
	}

	
	/**
	 * Get the {@link Game game}'s unique id.
	 * @return the {@link Game game}'s unique id.
	 */
	public String getGameId()
	{
		return gameId;
	}


	/**
	 * Get the current turn of the {@link Game game}.
	 * @return the current turn of the {@link Game game}.
	 */
	public Integer getCurrentTurn()
	{
		return currentTurn;
	}


	/**
	 * Get the maximum number of turns allowed to win this {@link Game game}.
	 * @return the maximum number of turns allowed to win this {@link Game game}.
	 */
	public int getMaximumTurns()
	{
		return maximumTurns;
	}


	/**
	 * Get the number of {@link Player players} who are playing this {@link Game game}.
	 * @return the number of {@link Player player} who are playing this {@link Game game}.
	 */
	public int getNbPlayers()
	{
		return nbPlayers;
	}


	/**
	 * Get the maximum time allowed by your {@link AI AI} to respond to a Berlin's request.
	 * 
	 * Attention: this time include the latency (ping) on the network!
	 * @return the maximum time allowed by your {@link AI AI} to respond to a Berlin's request.
	 */
	public int getTimeLimitPerTurn()
	{
		return timeLimitPerTurn;
	}


	int getPlayerId()
	{
		return playerId;
	}	
	
	
	void setCurrentTurn(int currentTurn)
	{
		this.currentTurn = currentTurn;
	}
	
	
	/**
	 * Get a String representation of this object.
	 */
	public String toString()
	{
        return String.format("id: %s\ncurrent_turn:%d\nmax turns:%d\nplayers:%d\ntime limit per turn:%d",
        		gameId,
        		currentTurn,
        		maximumTurns,
        		nbPlayers,
        		timeLimitPerTurn);
    }
}
