package org.flare.creatures;

public class Rat extends Creature {

	public String image = new String ("https://562f98a3b8ddd7d99496959da12de0226dbca265-www.googledrive.com/host/0B7gYPVDBv3F1TmNPWFl4aUFwQms/rat.fw.png");
	
	public Rat(){
		avgSpeed = 1; 
	}
	
	public void move() {
		x= x + (avgSpeed / 10 );
		y= y + (avgSpeed / 10 );		
	}
	
	
	public String getImage() {
		return image;	
	}
	
}
