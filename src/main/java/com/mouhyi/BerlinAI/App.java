package com.mouhyi.BerlinAI;


import berlin.Server;
import berlin.RandomAI;

/**
 * @author mouhyi
 *
 */
public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int port;
		if (args.length > 0) {
		    try {
		        port = Integer.parseInt(args[0]);
		        Server server = new Server(port, new RandomAI());
				server.start();
		    } catch (NumberFormatException e) {
		        System.err.println("Argument" + args[0] + " must be an integer.");
		        System.exit(1);
		    }
		}
	}

}
