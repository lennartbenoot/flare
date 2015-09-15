package org.flare;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import org.flare.creatures.Creature;
import org.flare.creatures.Rat;
import org.flare.map.Map;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	
	public static String playerName;
	public static String password;
	public static String hostname;
	
	public static Socket connection;
	
	public static int MAX_X = 1024;
	public static int MAX_Y = 768;
	public static int TILE_SIZE = 16;

	public static int canvasX1 = 0;
	public static int canvasY1 = 0;
	public static int canvasX2;
	public static int canvasY2;

	public static ArrayList<Creature> creatures = new ArrayList<Creature>();
	public Image desert = new Image(
			"https://562f98a3b8ddd7d99496959da12de0226dbca265-www.googledrive.com/host/0B7gYPVDBv3F1TmNPWFl4aUFwQms/desert.fw.png");
	public Image desertGrass = new Image(
			"https://562f98a3b8ddd7d99496959da12de0226dbca265-www.googledrive.com/host/0B7gYPVDBv3F1TmNPWFl4aUFwQms/desert-grass.fw.png");
	public Image desertStone = new Image(
			"https://562f98a3b8ddd7d99496959da12de0226dbca265-www.googledrive.com/host/0B7gYPVDBv3F1TmNPWFl4aUFwQms/desert-stone.fw.png");

	public static Creature you = new Creature();

	@Override
	public void start(Stage theStage) {
		try {
			
			canvasX2 = (canvasX1 + (MAX_X / TILE_SIZE));
			canvasY2 = (canvasY1 + (MAX_Y / TILE_SIZE));

			Group root = new Group();
			Scene scene = new Scene(root);
			theStage.setScene(scene);

			Canvas canvas = new Canvas(MAX_X, MAX_Y);
			root.getChildren().add(canvas);

			GraphicsContext gc = canvas.getGraphicsContext2D();

			Timeline tl = new Timeline();
			tl.setCycleCount(Animation.INDEFINITE);

			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent keyEvent) {
					if (keyEvent.getCode() == KeyCode.UP) {
						you.dy = 1;
						
					}
					if (keyEvent.getCode() == KeyCode.DOWN){
						you.dy = -1;
						
					}
					if (keyEvent.getCode() == KeyCode.LEFT){
						you.dx = -1;
						
					}
					if (keyEvent.getCode() == KeyCode.RIGHT){
						you.dx = 1;
						
					}
					
				}
			});

			scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent keyEvent) {
					if (keyEvent.getCode() == KeyCode.UP)
						you.dy = 0;
					if (keyEvent.getCode() == KeyCode.DOWN)
						you.dy = 0;
					if (keyEvent.getCode() == KeyCode.LEFT)
						you.dx = 0;
					if (keyEvent.getCode() == KeyCode.RIGHT)
						you.dx = 0;

				}
			});

			KeyFrame animate = new KeyFrame(Duration.seconds(.05), new EventHandler<ActionEvent>() {

				public void handle(ActionEvent event) {

					gc.clearRect(0, 0, MAX_X, MAX_Y);

					
					//move the canvas
					canvasX1 += you.dx;
					canvasY1 += you.dy;
					canvasX2 = (canvasX1 + (MAX_X / TILE_SIZE));
					canvasY2 = (canvasY1 + (MAX_Y / TILE_SIZE));
					
					// draw background
					for (int x = 0; x < MAX_X / TILE_SIZE; x++)
						for (int y = 0; y < MAX_Y / TILE_SIZE; y++) {

							long tile = Map.getTile( canvasX1 + x, canvasY1 +y);
							Image tileImage;
							

							if (tile == 9)
								tileImage = desertStone;
							else if ((tile > 90) && (tile < 100))
								tileImage = desertGrass;
							else
								tileImage = desert;
								
							gc.drawImage( tileImage, x * TILE_SIZE, MAX_Y - (  y * TILE_SIZE));
						}
					
					for (Creature c : creatures) {

						c.move();

						// calculate position
						float posX = (c.x - canvasX1 )  * (float) TILE_SIZE;
						float posY = MAX_Y - ( (c.y - canvasY1) * (float) TILE_SIZE);

						gc.drawImage(c.getImage(), posX, posY);
					}
					
					// debug info
					gc.fillText("MAP: (" + canvasX1 + "," + canvasY1 + ")-(" + canvasX2 + "," + canvasY2  + ")", 10, 10);
					gc.fillText("YOU: (" + you.x + "," + you.y + ")", 10, 20 );

				}
			});

			tl.getKeyFrames().add(animate);
			tl.play();
			theStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		// Get command line parameters
		hostname = args[ 0];
		playerName = args[ 1];
		password = args[ 2];
		

		//open connection to server

		try {
		    connection = new Socket(hostname, 9999);
		  
		    DataOutputStream outToServer = new DataOutputStream( connection.getOutputStream());
		    BufferedReader inFromServer = new BufferedReader(new InputStreamReader( connection.getInputStream()));
		    
		    
		    outToServer.writeBytes( "CMD_PLAYER:"+playerName+":"+password + '\n');
//			PrintWriter out =
//		        new PrintWriter(echoSocket.getOutputStream(), true);
//		    BufferedReader in =
//		        new BufferedReader(
//		            new InputStreamReader(echoSocket.getInputStream()));
//		    BufferedReader stdIn =
//		        new BufferedReader(
//		            new InputStreamReader(System.in))
		}
		catch (Exception e){
			e.printStackTrace();
		}

		//
		System.out.println( "Welcome, " + playerName);
		
		you.x = 32;
		you.y = 24;
		you.type = "you";
		creatures.add(you);

		Creature c = new Creature();
		c.x = 1;
		c.y = 1;
		c.type = "vann";
		creatures.add(c);

		c = new Rat();
		c.x = 1;
		c.y = 1;
		c.type = "rat";
		creatures.add(c);

		launch(args);
	}
}
