package org.flare.server.player;

import org.flare.creatures.Creature;

import javafx.scene.image.Image;

/**
 * 
 * 
 * @author Slaine
 *
 */
public class Player extends Creature{
	
	private String name;
	private String password;
	private Integer challenge;
	public String image = new String ("https://562f98a3b8ddd7d99496959da12de0226dbca265-www.googledrive.com/host/0B7gYPVDBv3F1TmNPWFl4aUFwQms/Orange.fw.png");
	
	public void move(){
		
		x= x + dx;
		y= y + dy;	
	}
	
	public String getImage() {
		return image;	
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getChallenge() {
		return challenge;
	}
	public void setChallenge(Integer challenge) {
		this.challenge = challenge;
	}

	

}
