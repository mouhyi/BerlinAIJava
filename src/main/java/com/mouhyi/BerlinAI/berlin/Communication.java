package berlin;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;
import com.google.gson.Gson;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import berlin.utils.ActionListener;
import berlin.utils.Console;
import berlin.utils.ConsoleEvent;
import berlin.utils.ConsoleListener;
import berlin.utils.Logger;
import berlin.utils.ParameterFilter;
import berlin.utils.Severity;
import berlin.infos.InfosInfos;
import berlin.infos.MapInfos;
import berlin.infos.MoveInfos;
import berlin.infos.StateInfos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


@SuppressWarnings("restriction")
/**
 * Manages the communication between your AI and the Berlin's server.
 * 
 * This object create an HTTP server that listen to a given port, ready to receives commands from Berlin.
 * It receives and sends JSON objects and transforms them into Java objects.
 */
class Communication
{
	private HttpServer server;
	private HttpHandler handler;
	private String command = "";
	private Console console;
	private Thread threadConsole;
	private ActionListener listener;
	private Gson gSon;
	private int port;

	
	/**
	 * Create the communication layer between your server and Berlin's server.
	 * @param port the given port on which your server will accept HTTP requests.
	 */
	Communication(int port)
	{
		this.port = port;
	}
	
	
	void setListener(ActionListener listener)
	{
		this.listener = listener;
	}
	
	
	void start()
	{
		initialize();
		
		if (server == null)
			return;
		
		server.start();
		threadConsole.start();

		Logger.Log(Severity.Info, "Server started.");
	}
	
	
	boolean getStopRequested()
	{	if(command == null)
			return false;
			
		return command.equalsIgnoreCase("quit");
	}
	
	
	void stop()
	{
		if (server == null)
			return;
		
		server.stop(0);
		console.cleanStop();
		
		server = null;
		console = null;
		threadConsole = null;

		Logger.Log(Severity.Info, "Server stopped.");
	}
	
	
	private void doConsoleCommand(String command)
	{
		this.command = command;
	}
	
	
	private void initialize()
	{
		gSon = new Gson();
		
		try
		{
			server = HttpServer.create(new InetSocketAddress(port), 0);
			
			handler = new HttpHandler()
			{
				@SuppressWarnings({ "unchecked" })
				@Override
				public void handle(HttpExchange exchange) throws IOException
				{
					// get the json
					Map<String, String> params = (Map<String, String>)exchange.getAttribute("parameters");
					
					// parse the json
					String action = gSon.fromJson(params.get("action"), String.class);
					InfosInfos infos = gSon.fromJson(params.get("infos"), InfosInfos.class);
					
					MapInfos mapInfos = gSon.fromJson(params.get("map"), MapInfos.class);
					
					Type collectionType = new TypeToken<Collection<StateInfos>>(){}.getType();
					Collection<StateInfos> statesCollection = gSon.fromJson(params.get("state"), collectionType);									
					List<StateInfos> states = new ArrayList<StateInfos>(statesCollection);
					
					// response data
					String responseData = "";
					
					// execute the query, get the response
					if (action.equalsIgnoreCase("game_start"))
					{
						listener.onGameStart(infos, mapInfos, states);
					}
					
					else if (action.equalsIgnoreCase("game_over"))
					{
						listener.onGameOver(infos.game_id);
					}
						
					else if (action.equalsIgnoreCase("ping"))
					{
						listener.onPing(infos);
						
						responseData = "Pong!";
					}
						
					else if (action.equalsIgnoreCase("turn"))
					{
						List<MoveInfos> moves = listener.onTurn(infos.game_id, infos.current_turn, states);
						responseData = gSon.toJson(moves);
					}
					
					exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, responseData.length());
					exchange.getResponseBody().write(responseData.getBytes());
					exchange.getResponseBody().close();
				}
			};
			
			HttpContext context = server.createContext("/", handler);
			context.getFilters().add(new ParameterFilter());
			
			console = new Console();
			threadConsole = new Thread(console);
			
			console.addListener(new ConsoleListener() {
				
				@Override
				public void onConsoleCommand(ConsoleEvent event)
				{
					doConsoleCommand(event.getCommand());
				}
			});
			

			Logger.Log(Severity.Info, "Server binded at http://localhost:" + port + ".");
		}
		
		catch (IOException e)
		{
			Logger.Log(Severity.Error, "The server could not be created because: " + e.getMessage());
		}
		
		catch (Exception e)
		{
			Logger.Log(Severity.Error, "The server encountered a weird error while responding to a request: " + e.getMessage());
		}
	}	
}
