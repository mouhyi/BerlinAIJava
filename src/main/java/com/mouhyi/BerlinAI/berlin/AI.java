package berlin;

/**
 * The artificial intelligence interface.
 * 
 * This is the interface you must implement when you create an AI that will play Berlin.
 * Once implemented, your class can access the game state via the {@link Game Game} object.
 */
public interface AI
{
	/**
	 * The update method of your AI.
	 * 
	 * This method is called at every game's turn.
	 * 
	 * @param game contains the state of the game and is also used to create
	 * the list of {@link Move Move} that you must send at the end of each turn.
	 */
	void update(Game game);
}
