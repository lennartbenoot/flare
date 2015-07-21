package org.flare;
	
import java.util.Random;

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
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


public class Main extends Application {
	
	public static Creature kebgeennaam= new Creature();
	
	@Override
	public void start(Stage theStage) {
		try {
			
			
	        // Create Image and ImageView objects
	        Image image = new Image ("https://562f98a3b8ddd7d99496959da12de0226dbca265-www.googledrive.com/host/0B7gYPVDBv3F1TmNPWFl4aUFwQms/Orange.fw.png");

	        Group root = new Group();
	        Scene theScene = new Scene( root );
	        theStage.setScene( theScene );
	             
	        Canvas canvas = new Canvas( 800, 600 );
	        root.getChildren().add( canvas );
	             
	        GraphicsContext gc = canvas.getGraphicsContext2D();
	        
	        gc.drawImage( image, 100, 100);
	        
	        Timeline tl = new Timeline();
	        tl.setCycleCount(Animation.INDEFINITE);
	        
	        
	        KeyFrame animate = new KeyFrame(Duration.seconds(.050),
	                new EventHandler<ActionEvent>() {

	                    public void handle(ActionEvent event) {
	                    	
//	        	        	writer.setColor( kebgeennaam.x, kebgeennaam.y, Color.WHITE);
	                    	kebgeennaam.move();
	                    	gc.clearRect(0, 0, 800, 600);
	                    	gc.drawImage( image, kebgeennaam.x, kebgeennaam.y);
//	                    	writer.setColor( kebgeennaam.x, kebgeennaam.y, Color.BLACK);
	                    	
	                    	
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
		
    	kebgeennaam.x = 100;
    	kebgeennaam.y = 100;
    	kebgeennaam.name = "keb geen naam";
		
		launch(args);
	}
}
