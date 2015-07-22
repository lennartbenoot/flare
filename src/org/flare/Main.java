package org.flare;
	
import java.util.ArrayList;
import java.util.Random;

import org.flare.creatures.Creature;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Main extends Application {
	
	public static int MAX_X = 1024;
	public static int MAX_Y = 768;
	
	public static ArrayList<Creature> creatures = new ArrayList<Creature>();
	public Image desert = new Image ("https://562f98a3b8ddd7d99496959da12de0226dbca265-www.googledrive.com/host/0B7gYPVDBv3F1TmNPWFl4aUFwQms/desert.fw.png");
	public Image desertGrass = new Image ("https://562f98a3b8ddd7d99496959da12de0226dbca265-www.googledrive.com/host/0B7gYPVDBv3F1TmNPWFl4aUFwQms/desert-grass.fw.png");
	private Random rand = new Random();
	
	@Override
	public void start(Stage theStage) {
		try {
			
	        Group root = new Group();
	        Scene theScene = new Scene( root );
	        theStage.setScene( theScene );
	             
	        Canvas canvas = new Canvas( MAX_X, MAX_Y );
	        root.getChildren().add( canvas );
	             
	        GraphicsContext gc = canvas.getGraphicsContext2D();
	        
	        Timeline tl = new Timeline();
	        tl.setCycleCount(Animation.INDEFINITE);
	        
	        
	        KeyFrame animate = new KeyFrame(Duration.seconds(.050),
	                new EventHandler<ActionEvent>() {

	                    public void handle(ActionEvent event) {
	                    	
	                    	gc.clearRect(0, 0, MAX_X, MAX_Y);
	                    	
	                    	// draw background
	                    	for ( int x = 0; x < MAX_X / 16; x++)
	                    		for ( int y = 0; y < MAX_Y / 16; y++) {
	                    			
	                    			int tile = ( 3 * x + 7 * y - x) % 10;
	                    			
	                    			if ( tile == 8) 
	                    				gc.drawImage( desertGrass, x * 16, y * 16);
	                    			else if ( tile == 5) 
	                    				gc.drawImage( desertGrass, x * 16, y * 16);
	                    			else
	                    				gc.drawImage( desert, x * 16, y * 16);
	                    		}
	                    			
	                    	
	                    	
	                    	for ( Creature c : creatures) {

		                    	c.move();
		                    	gc.drawImage( c.getImage(), c.x, c.y);
	                    	}

	                    }
	                });

	        tl.getKeyFrames().add( animate);
	        tl.play();
	        theStage.show();
	        
 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		Creature kebgeennaam= new Creature();
    	kebgeennaam.x = 400;
    	kebgeennaam.y = 300;
    	kebgeennaam.name = "keb geen naam";
    	kebgeennaam.type = "wasp";
    	creatures.add( kebgeennaam);
    	
    	Creature c= new Creature();
    	c.x = 100;
    	c.y = 100;
    	c.type = "wasp";
    	creatures.add( c);
    	
    	c= new Creature();
    	c.x = 200;
    	c.y = 200;
    	c.type = "vann";
    	creatures.add( c);
    	
    	c= new Creature();
    	c.x = 400;
    	c.y = 500;
    	c.type = "rabbit";
    	creatures.add( c);
    	
    	c= new Creature();
    	c.x = 400;
    	c.y = 500;
    	c.type = "rabbit";
    	creatures.add( c);
    	
    	c= new Creature();
    	c.x = 400;
    	c.y = 500;
    	c.type = "rabbit";
    	creatures.add( c);
    	
    	c= new Creature();
    	c.x = 400;
    	c.x = 500;
    	c.type = "rat";
    	creatures.add( c);
		
		launch(args);
	}
}
