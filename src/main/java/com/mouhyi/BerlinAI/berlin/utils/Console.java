package berlin.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Console implements Runnable
{
	private BufferedReader in;
	private List<ConsoleListener> listeners;
	private boolean running;
	
	
	public Console()
	{
		listeners = new ArrayList<ConsoleListener>();
		
		in = new BufferedReader(new InputStreamReader(System.in));
		
		running = true;
	}
	
	
	public void addListener(ConsoleListener listener)
	{
		listeners.add(listener);
	}

	
	@Override
	public void run()
	{
		while (running)
		{
			String message;
	
			try
			{
				message = in.readLine();
			}
			
			catch (IOException e)
			{
				message = "error";
			}
			
			for (ConsoleListener listener : listeners)
			{
				ConsoleEvent event = new ConsoleEvent(message, this);
				
				listener.onConsoleCommand(event);
			}
		}
	}
	
	
	public void cleanStop()
	{
		running = false;
	}
}