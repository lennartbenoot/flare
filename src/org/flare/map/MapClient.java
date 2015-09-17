package org.flare.map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.flare.Main;
import org.flare.server.FlareThread;

public class MapClient {
	
	
	// data is chached in block of 100x100
	// when location is requested on position 1055, 754
	// block (0,0) -> (0,0) - (99,99)
	// block (1,1) -> (100,100) - (199,199)
	// block (1,0) -> (100,0) - (199,99)
	
	private static HashMap<String,String> map = new HashMap<String,String>();
	private static final Logger logger = Logger.getLogger( MapClient.class.getName());
	
	public static int getTile( long x, long y) {
		
		long blockX = x / 100;
		long blockY = y / 100;
		long xInBlock = x % 100;
		long yInBlock = y % 100;
		
		//check if block is available in cache
		String mapBlock = getMapBlock( blockX, blockY);
		
		// If map not in cache, load map in cache.
		if (mapBlock == null) {
			try {
				Main.outToServer.writeBytes( "CMD_GETTILE:"+x+":"+y + "\n" );
				String mapblock = Main.inFromServer.readLine();
				logger.info( "length " + mapblock.length() );
				setMapBlock( blockX, blockY, mapblock);
				
			}  catch (Exception e) {
				return 0;
			}
		}
		
		return mapBlock.charAt( (int) ( xInBlock * 100 + yInBlock)) - 14;
	}
	
	
	public static String getMapBlock( long blockX, long blockY){
		String ret = null;
		
		return map.get( blockX + ":" + blockY);
	}
	
	
	public static void setMapBlock( long blockX, long blockY, String mapBlock)
	{
		if ( map.get( blockX + ":" + blockY) == null )
			map.put(  blockX + ":" + blockY, mapBlock);
	}
}
