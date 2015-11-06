/**
 * 
 */
package berlin;

import java.util.List;
import java.util.Random;

/**
 * @author mouhyi
 *
 */
public class RandomAI implements AI {

	private Random randomizer;
	
	/**
	 * 
	 */
	public RandomAI() {
		randomizer = new Random();
	}

	/* 
	 * AI that randomly moves soldiers from node to node
	 */
	@Override
	public void update(Game game) {
		Player myPlayer = game.getActivePlayer();
		List<Node> controlledNodes = myPlayer.getNodes();
		for(Node node: controlledNodes)
		{
			Node toNode = node.getAdjacents().get(randomizer.nextInt(node.getAdjacents().size()));
			game.addMove(node, toNode, randomizer.nextInt(node.getNbSoldiers()+1));

		}

	}

}
