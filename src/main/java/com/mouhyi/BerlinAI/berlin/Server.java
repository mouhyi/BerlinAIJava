package berlin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import berlin.infos.InfosInfos;
import berlin.infos.MapInfos;
import berlin.infos.MoveInfos;
import berlin.infos.StateInfos;
import berlin.utils.ActionListener;
import berlin.utils.Logger;
import berlin.utils.Severity;


/**
 * The server.
 * 
 * This is the main object of this API. It glues together your {@link AI AI} and a homemade HTTP server.
 * It creates, plays and destroys games of Berlin.
 */
public class Server implements ActionListener
{
	private Communication communication;
	private java.util.Map<String, Game> games;
	private AI ai;
	
	
	/**
	 * Creates a new server.
	 * @param port the port on which the HTTP server will listen.
	 * @param ai your amazing AI.
	 */
	public Server(int port, AI ai)
	{
		this.communication = new Communication(port);
		this.communication.setListener(this);
		
		this.games = new HashMap<String, Game>();
		
		this.ai = ai;
	}

	
	/**
	 * Starts the HTTP server and waits for commands from Berlin.
	 */
	public void start()
	{	
		Logger.Log(Severity.Info, " __                  __ __             _______ _______ ");
		Logger.Log(Severity.Info, "|  |--..-----..----.|  |__|.-----.    |   _   |_     _|");
		Logger.Log(Severity.Info, "|  _  ||  -__||   _||  |  ||     |    |       |_|   |_ ");
		Logger.Log(Severity.Info, "|_____||_____||__|  |__|__||__|__|    |___|___|_______|\n");
		
		communication.start();
		
		while (!communication.getStopRequested())
		{
			try { Thread.sleep(1000); }
			catch (InterruptedException e) {}
		}
		
		communication.stop();		
	}
	
	
	@Override
	public void onGameOver(String gameId)
	{
		if (!games.containsKey(gameId))
		{
			Logger.Log(Severity.Warning, "\n--> Game over for a non-existing game.");
			return;
		}
		
		Game game = games.get(gameId);
		
		Logger.Log(Severity.Info, String.format("\n--> Game over at turn %d!", game.getInfos().getCurrentTurn()));
		Logger.Log(Severity.Info, String.format("id: %s", gameId));
		
		games.remove(gameId);
	}


	@Override
	public void onGameStart(InfosInfos infos, MapInfos mapInfos, List<StateInfos> states)
	{
		Game game = new Game(infos, mapInfos, states);
		
		games.put(infos.game_id, game);
		
		Logger.Log(Severity.Info, "\n--> New game started!");
		Logger.Log(Severity.Info, game.getInfos());
	}


	@Override
	public void onPing(InfosInfos infos)
	{
		Logger.Log(Severity.Info, "\n--> Ping!");
		Logger.Log(Severity.Info, new Infos(infos));
		Logger.Log(Severity.Info, "\n");
	}


	@Override
	public List<MoveInfos> onTurn(String gameId, Integer turn, List<StateInfos> states)
	{
		if (!games.containsKey(gameId))
		{
			Logger.Log(Severity.Warning, "\n--> Turn for a non-existing game.");
			return new ArrayList<MoveInfos>();
		}
		
		Logger.Log(Severity.Info, String.format("\n--> Turn #%d for game %s!", turn, gameId));
		
		Game game = games.get(gameId);
		
		game.onTurn(turn, states);
		
		ai.update(game);
		
		List<MoveInfos> movesToSend = new ArrayList<MoveInfos>();
		
		for (Move m : game.getMoves())
			movesToSend.add(m.toMoveInfos());
		
		return movesToSend;
	}
}
