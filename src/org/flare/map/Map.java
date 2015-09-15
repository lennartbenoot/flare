package org.flare.map;

public class Map {
	
	
	
	public static int getTile( long x, long y) {
		
		long randomnumber = ( ( Math.abs(x*y)) * 214013 + 2531011) % 32768;
		long tile = ((randomnumber) % 100);
		
		return (int)tile;
	}
	

}
