package org.flare.map;

import java.util.HashMap;
import java.util.logging.Logger;

import org.flare.Main;

public class MapClient {
	
	/** 
	 * Data is cached in blocks of 100x100
	 * when location is requested on position 1055, 754
	 * block (0,0) -> (0,0) - (99,99)
	 * block (1,1) -> (100,100) - (199,199)
	 * block (1,0) -> (100,0) - (199,99)-
	 * **/ 
	private static HashMap<String,String> mapCache = new HashMap<String,String>();
	private static final Logger logger = Logger.getLogger( MapClient.class.getName());
	
	public static int getTile( long x, long y) {
		
		// Identify mapblock and tile within mapblock 
		long blockX = x / 100;
		long blockY = y / 100;
		long xInBlock = x % 100;
		long yInBlock = y % 100;
		
		//check if block is available in cache
		String mapBlock = getMapBlock( blockX, blockY);
		
		// If map not in cache, load map from server and save in cache.
		if (mapBlock == null) {
			try {
				Main.outToServer.writeBytes( "CMD_GETTILE:"+x+":"+y + "\n" );
				String mapblock = Main.inFromServer.readLine();
				setMapBlock( blockX, blockY, mapblock);
				
			}  catch (Exception e) {
				return 0;
			}
		}
		
		return mapBlock.charAt( (int) ( xInBlock * 100 + yInBlock)) - 14;
	}
	
	
	public static String getMapBlock( long blockX, long blockY){

		return mapCache.get( blockX + ":" + blockY);
	}
	
	
	public static void setMapBlock( long blockX, long blockY, String mapBlock)
	{
		if ( mapCache.get( blockX + ":" + blockY) == null )
			mapCache.put(  blockX + ":" + blockY, mapBlock);
	}
}
