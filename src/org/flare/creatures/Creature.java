package org.flare.creatures;

import java.util.Random;

import org.flare.Main;

import javafx.scene.image.Image;

public class Creature {
	
	public int x; 
	public int y;
	public String name;
	public String type;
	public Image wasp = new Image ("https://562f98a3b8ddd7d99496959da12de0226dbca265-www.googledrive.com/host/0B7gYPVDBv3F1TmNPWFl4aUFwQms/wasp.fw.png");
	public Image vann = new Image ("https://562f98a3b8ddd7d99496959da12de0226dbca265-www.googledrive.com/host/0B7gYPVDBv3F1TmNPWFl4aUFwQms/creature.fw.png");
	public Image rabbit = new Image ("https://562f98a3b8ddd7d99496959da12de0226dbca265-www.googledrive.com/host/0B7gYPVDBv3F1TmNPWFl4aUFwQms/rabbit.fw.png");
	public Image rat = new Image ("https://562f98a3b8ddd7d99496959da12de0226dbca265-www.googledrive.com/host/0B7gYPVDBv3F1TmNPWFl4aUFwQms/rat.fw.png");
	private Random rand = new Random();
	private int mem = rand.nextInt( 150);
	private int dx = 0;
	private int dy = 0;
	
	public void move() {
		
		if ( type.equals("wasp")) {
			x= x + zeroOneOrMinusOne();
			y= y + zeroOneOrMinusOne();		
		}
		
		
		if ( type.equals("vann")) {
			x= x - 1;
			y= y - 1;		
		}
	
		
		if ( type.equals("rat")) {
			x= x + 1;
			y= y - 1;		
		}
		
		if ( type.equals("rabbit")) {
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
		
		//keep creature within world
		if (x > Main.MAX_X) x=0;
		if (y > Main.MAX_Y) y=0;
		if (x < 0) x= Main.MAX_X;
		if (y < 0) y= Main.MAX_Y;
		 
	}
	
	
	public Image getImage() {
		
		if (type.equals("wasp")) return wasp;
		if (type.equals("rabbit")) return rabbit;
	    if (type.equals("rat")) return rat;
		return vann;
			
	}
	
	
	private int zeroOneOrMinusOne() {
		
		
		
		return ( rand.nextInt( 3) - 1);
	}
}
