package berlin.utils;


public class Logger
{
	public static void Log(Severity severity, Object object)
	{
		String toPrint = "";
		
		switch (severity)
		{
			case Error: toPrint = "Error: "; break;
			case Warning: toPrint = "Warning: "; break;
		}
		
		toPrint = toPrint.concat(object.toString());
		
		System.out.println(toPrint);
	}
}
