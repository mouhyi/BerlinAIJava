package berlin.utils;

import java.util.EventListener;


public interface ConsoleListener extends EventListener
{
    public void onConsoleCommand(ConsoleEvent event);
}
