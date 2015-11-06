package berlin.utils;

import java.util.EventObject;

public class ConsoleEvent extends EventObject
{
	private static final long serialVersionUID = 1L;

	private String command;
	
	
	public ConsoleEvent(String command, Object source)
	{	
		super(source);
		
		this.command = command;
	}
	
	
	public String getCommand()
	{
		return command;
	}
}
