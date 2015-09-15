package org.flare.creatures;

import java.util.Random;

import org.flare.Main;

import javafx.scene.image.Image;

public class Creature {
	
	public float x; 
	public float y;
	
	//
	public int stamina;
	public float avgSpeed;  // average speed in meters per second x 10
	
	public String name;
	public String type;
	public Image wasp = new Image ( "https://562f98a3b8ddd7d99496959da12de0226dbca265-www.googledrive.com/host/0B7gYPVDBv3F1TmNPWFl4aUFwQms/wasp.fw.png") ;
	public Image creature = new Image ("https://562f98a3b8ddd7d99496959da12de0226dbca265-www.googledrive.com/host/0B7gYPVDBv3F1TmNPWFl4aUFwQms/creature.fw.png");
	public Image rabbit = new Image ("https://562f98a3b8ddd7d99496959da12de0226dbca265-www.googledrive.com/host/0B7gYPVDBv3F1TmNPWFl4aUFwQms/rabbit.fw.png");
	public Image you = new Image ("https://562f98a3b8ddd7d99496959da12de0226dbca265-www.googledrive.com/host/0B7gYPVDBv3F1TmNPWFl4aUFwQms/Orange.fw.png");
	private Random rand = new Random();
	private int mem = rand.nextInt( 150);
	public int dx = 0;
	public int dy = 0;
	
	public void move() {
		
		if ( type.equals("wasp")) {
			x= x + zeroOneOrMinusOne();
			y= y + zeroOneOrMinusOne();		
		}
		else if ( type.equals("vann")) {
			x= x - 1;
			y= y - 1;		
		}
		else if ( type.equals("rabbit")) {
			mem ++;
			
			if (mem == 100) {
				dx = 0;
				dy = 0;
			}
			
			if (mem == 150) {
				dx = zeroOneOrMinusOne();
				dy = zeroOneOrMinusOne();
				mem=0;
			}
			
			x= x + dx;
			y= y + dy;		
		}
		else if ( type.equals("you")) {
			x= x + dx;
			y= y + dy;	
		}
		else {
			x= x + dx;
			y= y + dy;	
		}
		
		 
	}
	
	
	public Image getImage() {
		
		if (type.equals("wasp")) return wasp;
		if (type.equals("rabbit")) return rabbit;
	    if (type.equals("you")) return creature;
	    
		return creature;
			
	}
	
	
	private int zeroOneOrMinusOne() {
		
		return ( rand.nextInt( 3) - 1);
	}
}
