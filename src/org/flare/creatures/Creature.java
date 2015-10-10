package org.flare.creatures;

import java.util.Random;

/**
 * 
 * This class represents all living and moving creatures in Flare.
 * 
 * https://en.wikipedia.org/wiki/Attribute_(role-playing_games)
 * 
 * @author Lennart
 *
 */
public class Creature {
	
	public float x; 
	public float y;
	public int stamina;
	public int strength;
	public float avgSpeed;  // average speed in meters per second x 10
	
	public String name;
	public String type;
	
	private Random rand = new Random();
	private int mem = rand.nextInt( 150);
	public int dx = 0;
	public int dy = 0;
	

	public void move() {
		
		x= x + zeroOneOrMinusOne();
		y= y + zeroOneOrMinusOne();		
	}
	
	
	private int zeroOneOrMinusOne() {
		
		return ( rand.nextInt( 3) - 1);
	}
	
	public float getX() {
		return x;
	}


	public void setX(float x) {
		this.x = x;
	}


	public float getY() {
		return y;
	}


	public void setY(float y) {
		this.y = y;
	}

}
