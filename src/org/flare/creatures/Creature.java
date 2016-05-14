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
	public float stamina = 100;
	public float strength = 100;
	public float food = 100; // between 0 and 100
	public float avgSpeed= 50; // average speed in cm per second

	public String name;
	public String type;

	private Random rand = new Random();
	private int mem = rand.nextInt(150);
	public float dx = 0;
	public float dy = 0;

	public int getFood() {
		return (int) food;
	}

	public void eat(float amount) {
		food += amount;

		if (food > 100)
			food = 100;
	}

	public void move() {

		x = x + ((float) ( zeroOneOrMinusOne() * avgSpeed / 100 / 10)); // /10 because 10 move() are calculated per second
		y = y + ((float) ( zeroOneOrMinusOne() * avgSpeed / 100 / 10));
	}

	private int zeroOneOrMinusOne() {

		return (rand.nextInt(3) - 1);
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
