package org.flare.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FlareServer {
	
	public static String SEPAROTOR = ":";

	/**
	 * Flare Server main program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		ServerSocket serverSocket;
		Socket socket = null;
		
		// create the world & run thread
		World world = World.getInstance();
		world.genesis();
		world.start();
		
		// start server socket to accept client connections
		try {
			serverSocket = new ServerSocket(9999);

			while (true) {
				socket = serverSocket.accept();

				FlareThread ft = new FlareThread();
				ft.setConnection(socket);
				ft.run();

			}

		} catch (IOException e) {
			System.out.println(e);
		}

	}

}
