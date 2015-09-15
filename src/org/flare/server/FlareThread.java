package org.flare.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class FlareThread extends Thread {

	private Socket connection;
	private String playerName;
	
	public void run() {
		try {
			
			System.out.println("Connection from: " + connection.getInetAddress());
			
			BufferedReader inFromClient =  new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			DataOutputStream outToClient = new DataOutputStream(connection.getOutputStream());
			
			String clientSentence = inFromClient.readLine();
			System.out.println("Received: " + clientSentence); 
			
//			BufferedInputStream is = new BufferedInputStream(connection.getInputStream());
//			InputStreamReader isr = new InputStreamReader(is);
//			int character;
//			StringBuffer process = new StringBuffer();
//
//			while ((character = isr.read()) != 13) {
//				process.append((char) character);
//			}
//			System.out.println(process);
//
//			String timeStamp = new java.util.Date().toString();
//			String returnCode = "MultipleSocketServer repsonded at " + timeStamp + (char) 13;
//			BufferedOutputStream os = new BufferedOutputStream(connection.getOutputStream());
//			OutputStreamWriter osw = new OutputStreamWriter(os, "US-ASCII");
//			osw.write(returnCode);
//			osw.flush();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				connection.close();
			} catch (IOException e) {
			}
		}
	}

	public Socket getConnection() {
		return connection;
	}

	public void setConnection(Socket connection) {
		this.connection = connection;
	}

}
