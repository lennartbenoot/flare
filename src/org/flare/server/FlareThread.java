package org.flare.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Logger;

import org.flare.creatures.Creature;
import org.flare.map.Map;
import org.flare.server.player.Player;
import org.flare.server.player.PlayerStore;

public class FlareThread extends Thread {

	private static final Logger logger = Logger.getLogger(FlareThread.class.getName());

	private Socket connection;
	private String playerName;
	private Player player;
	private boolean connected = true;

	/**
	 *  This thread is executed when a new connection arrives from 
	 *  a client.
	 * 
	 */
	public void run() {
		try {

			logger.info( "Connection from: " + connection.getInetAddress());

			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connection.getOutputStream());

			String request;
			String response;
			while (connected) {

				request = inFromClient.readLine();
				response = handleRequest(request);

				outToClient.write(response.getBytes());
			}

		} catch (Exception e) {
			logger.severe( e.toString());
		} finally {
			try {
				connection.close();
			} catch (IOException e) {
			}
		}
	}

	private String handleRequest(String request) {
		String response = "OK\n";

		String parsedRequest[] = request.split( FlareServer.SEPAROTOR);
		String cmd = parsedRequest[0];

		if (cmd.equals( FlareProtocol.CMD_PLAYER)) {
			playerName = parsedRequest[1];
			logger.info("New player connected: " + playerName);

			player = PlayerStore.getInstance().findPlayerByName(playerName);

			// return location and challenge
			response = player.getX() + FlareServer.SEPAROTOR + player.getY() + FlareServer.SEPAROTOR + player.getChallenge() + "\n";
			logger.info("Response: " + response);
		}
		
		if (cmd.equals( FlareProtocol.CMD_GET_CREATURES)) {
			logger.info("Getting creatures for player: " + playerName);

			Creature c =World.getInstance().creatures.get( 0);
			Creature c2 =World.getInstance().creatures.get( 1);
			response = ( c.getX() + FlareServer.SEPAROTOR + c.getY() + FlareServer.SEPAROTOR + c2.getX() + FlareServer.SEPAROTOR + c2.getY()) + "\n";
			logger.info("Response: " + response);
		}

		// Get tiles
		if (cmd.equals("CMD_GETTILE")) {
			String xs = parsedRequest[1];
			String ys = parsedRequest[2];
			long x = Long.parseLong(xs);
			long y = Long.parseLong(ys);

			// identify the block
			long blockX = x / 100;
			long blockY = y / 100;

			String mapBlock = "";

			for (int i = 0; i < 100; i++)
				for (int j = 0; j < 100; j++) {
					int tile = Map.getTile(blockX + i, blockY + j);
					char c = (char) tile;

					mapBlock += ((char) (c + 14)); // +14 to avoid any carriage
													// returns in the stream.
				}

			response = mapBlock + "\n";
		}
		
		// Get tiles
		if (cmd.equals( FlareProtocol.CMD_PLAYER_LOCATION)) {
			logger.info("Player reports location: " + request);
			
			player.setX( Float.parseFloat( parsedRequest[1]));
			player.setY( Float.parseFloat( parsedRequest[2]));
		}
		
		return response;
	}

	public Socket getConnection() {
		return connection;
	}

	public void setConnection(Socket connection) {
		this.connection = connection;
	}

}
