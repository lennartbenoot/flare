package org.flare.map;

public class Map {
	
	
	public static int TILE_DESERT = 1;
	public static int TILE_DESERT_GRASS = 2; 
	public static int TILE_DESERT_CAVE = 3; 
	public static int TILE_DESERT_PLANT_WITH_BERRIES = 4; 
	
	private static MWCRandom rand = new MWCRandom( 31415926);
	
	public static int getTile( long x, long y) {
		
		long randomNumber = rand.nextLong( 10000);
		
		if ( isBetween( randomNumber, 	0		, 	500		)) return TILE_DESERT_GRASS;
		if ( isBetween( randomNumber, 	500 	, 	502		)) return TILE_DESERT_CAVE;	
		if ( isBetween( randomNumber, 	502 	, 	512		)) return TILE_DESERT_PLANT_WITH_BERRIES;
	
		return TILE_DESERT;
	}
	
	
	public static boolean isBetween( long number, long low, long high)
	{
		
		return ( (number > low) && ( number  < high));
	}

}
