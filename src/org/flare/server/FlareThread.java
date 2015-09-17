package org.flare.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Logger;

import org.flare.map.Map;

public class FlareThread extends Thread {
	
	private static final Logger logger = Logger.getLogger( FlareThread.class.getName());

	private static String SEPAROTOR = ":";
	
	private Socket connection;
	private String playerName;
	private boolean connected=true;
	
	public void run() {
		try {
			
			System.out.println("Connection from: " + connection.getInetAddress());
			
			BufferedReader inFromClient =  new BufferedReader(new InputStreamReader(connection.getInputStream()));	
			DataOutputStream outToClient = new DataOutputStream(connection.getOutputStream());
			
			String request;
			String response;
			while (connected) {
				
				request = inFromClient.readLine();
				response = handleRequest(request);
				
				outToClient.write( response.getBytes());
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				connection.close();
			} catch (IOException e) {
			}
		}
	}

	private String handleRequest( String request){
		String response = "OK\n";
		
		String parsedRequest[] = request.split( SEPAROTOR);
		String cmd = parsedRequest[ 0];
		
		if ( cmd.equals( "CMD_PLAYER")) {
			playerName = parsedRequest[ 1];
			logger.info( "New player connected: " + playerName);
			
		}
		
		if ( cmd.equals( "CMD_GETTILE")) {
			String xs = parsedRequest[ 1];
			String ys = parsedRequest[ 2];
			long x = Long.parseLong( xs);
			long y = Long.parseLong( ys);
			
			// identify the block
			long blockX = x / 100;
			long blockY = y / 100;
			
			String mapBlock = "";
			
			for ( int i=0; i<100; i++)
				for ( int j=0; j<100; j++) {
					int tile = Map.getTile(blockX + i, blockY + j);
					char c = (char) tile;
					
					mapBlock += ( (char) (c+14));
				}
			
			response = mapBlock + "\n";
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
