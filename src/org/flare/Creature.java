package org.flare;

import java.util.Random;

public class Creature {
	
	int x=10; 
	int y=10;
	String name;
	
	
	public void move() {
		
		x= x - 1;
		y= y - 1;		
		 
	}
}
