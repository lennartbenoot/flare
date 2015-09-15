package org.flare.creatures;

import javafx.scene.image.Image;

public class Rat extends Creature {

	public Image image = new Image ("https://562f98a3b8ddd7d99496959da12de0226dbca265-www.googledrive.com/host/0B7gYPVDBv3F1TmNPWFl4aUFwQms/rat.fw.png");
	
	public Rat(){
		avgSpeed = 1; 
	}
	
	public void move() {
		x= x + (avgSpeed / 10 );
		y= y + (avgSpeed / 10 );		
	}
	
	
	public Image getImage() {
		return image;	
	}
	
}
