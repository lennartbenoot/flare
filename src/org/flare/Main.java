package org.flare;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.flare.creatures.Creature;
import org.flare.creatures.Rat;
import org.flare.map.Map;
import org.flare.map.MapClient;
import org.flare.map.Tiles;
import org.flare.server.FlareProtocol;
import org.flare.server.FlareServer;
import org.flare.server.player.Challenge;
import org.flare.server.player.Player;

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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

	public static String playerName;
	public static String password;
	public static String hostname;

	public static Socket connection;

	public static int MAX_X = 1920;
	public static int MAX_Y = 1000;
	public static int TILE_SIZE = 32;

	public static int canvasX1 = 0;
	public static int canvasY1 = 0;
	public static int canvasX2;
	public static int canvasY2;

	public static DataOutputStream outToServer;
	public static BufferedReader inFromServer;

	public static ArrayList<Creature> creatures = new ArrayList<Creature>();

	public static Player player = new Player();
	
	private static final Logger logger = Logger.getLogger( Main.class.getName());
	//public static String hostName = "http://localhost:8888/";
	public static String hostName = "https://562f98a3b8ddd7d99496959da12de0226dbca265-www.googledrive.com/host/0B7gYPVDBv3F1TmNPWFl4aUFwQms/";
	
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
						player.dy = 0.1F;

					}
					if (keyEvent.getCode() == KeyCode.DOWN) {
						player.dy = -0.1F;

					}
					if (keyEvent.getCode() == KeyCode.LEFT) {
						player.dx = -0.1F;

					}
					if (keyEvent.getCode() == KeyCode.RIGHT) {
						player.dx = 0.1F;

					}
					if (keyEvent.getCode() == KeyCode.F) {

						logger.info( "Action on tile: " + player.x + ":" +  player.y +":"+ MapClient.getTile( (long) player.x, (long)player.y));
						
						if ( MapClient.getTile( (long) player.x, (long)player.y) == Map.TILE_DESERT_PLANT_WITH_BERRIES)
							player.eat( 10);
						
					}

				}
			});

			scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent keyEvent) {
					if (keyEvent.getCode() == KeyCode.UP)
						player.dy = 0;
					if (keyEvent.getCode() == KeyCode.DOWN)
						player.dy = 0;
					if (keyEvent.getCode() == KeyCode.LEFT)
						player.dx = 0;
					if (keyEvent.getCode() == KeyCode.RIGHT)
						player.dx = 0;

				}
			});

			KeyFrame animate = new KeyFrame(Duration.seconds(0.05), new EventHandler<ActionEvent>() {

				// Keyframe loop
				public void handle(ActionEvent event) {
					
					try {
					
					//report player location to server
					outToServer.writeBytes( FlareProtocol.CMD_PLAYER_LOCATION + ":" + player.x + ":" + player.y +'\n');
					String response = inFromServer.readLine();
					

					gc.clearRect(0, 0, MAX_X, MAX_Y);

					// move the canvas
					canvasX1 = (int) ( player.getX() - (MAX_X / TILE_SIZE / 2) );
					canvasY1 = (int) ( player.getY() - (MAX_Y / TILE_SIZE / 2) );
					canvasX2 = (canvasX1 + (MAX_X / TILE_SIZE));
					canvasY2 = (canvasY1 + (MAX_Y / TILE_SIZE));

					// draw background
					int sdx=0;
					int sdy=0;
					for (int x = -1; x < MAX_X / TILE_SIZE; x++)
						for (int y = 0; y < MAX_Y / TILE_SIZE + 3; y++) {

							long tile = MapClient.getTile(canvasX1 + x, canvasY1 + y);
							Image tileImage;

							if (tile == Map.TILE_DESERT)
								tileImage = Tiles.desert;
							else if (tile == Map.TILE_DESERT_GRASS)
								tileImage = Tiles.desertGrass;
							else if (tile == Map.TILE_DESERT_CAVE)
								tileImage = Tiles.desertCave;
							else if (tile == Map.TILE_DESERT_PLANT_WITH_BERRIES)
								tileImage = Tiles.desertPlantWithBerries;
							else if (tile == Map.TILE_DESERT_STONE)
								tileImage = Tiles.desertStone;
							else
								tileImage = Tiles.desert;
							
							// 
							float sdxf = (player.x % 1 ) * TILE_SIZE;
							float sdyf = (player.y % 1 ) * TILE_SIZE;
							sdx = (int) (((long) sdxf) % TILE_SIZE);
							sdy = (int) (((long) sdyf) % TILE_SIZE);
							gc.drawImage(tileImage, (TILE_SIZE - sdx)  + x * TILE_SIZE, sdy + MAX_Y - (y * TILE_SIZE));

							if (tile == Map.TILE_DESERT_STONE)
								gc.fillText(canvasX1 + x + ":" + canvasY1 + y, x * TILE_SIZE, MAX_Y - (y * TILE_SIZE));
						}
					
					// get creatures
					outToServer.writeBytes( FlareProtocol.CMD_GET_CREATURES +'\n');
					response = inFromServer.readLine();
					String parsedResponse[] = response.split( FlareServer.SEPAROTOR);
					Creature c1 = new Creature();
					Creature c2 = new Creature();
					c1.setX( Float.parseFloat( parsedResponse[0]));
					c1.setY( Float.parseFloat( parsedResponse[1]));
					c2.setX( Float.parseFloat( parsedResponse[2]));
					c2.setY( Float.parseFloat( parsedResponse[3]));
					// calculate position
					float posX = (c1.x - canvasX1) * (float) TILE_SIZE + (TILE_SIZE - sdx);
					float posY = MAX_Y - ((c1.y - canvasY1) * (float) TILE_SIZE) + sdy ;

					
					gc.drawImage(Tiles.hollow, posX, posY);
					
					//
					posX = (c2.x - canvasX1) * (float) TILE_SIZE + (TILE_SIZE - sdx);
					posY = MAX_Y - ((c2.y - canvasY1) * (float) TILE_SIZE) + sdy ;

					gc.drawImage(Tiles.hollow, posX, posY);
						
					// draw creatures
					for (Creature c : creatures) {

						c.move();

						// calculate position
						posX = (c.x - canvasX1) * (float) TILE_SIZE + (TILE_SIZE - sdx);
						posY = MAX_Y - ((c.y - canvasY1) * (float) TILE_SIZE) + sdy ;

						gc.drawImage(Tiles.creatureImage, posX, posY);
					}

					// debug info
					gc.fillText("MAP: (" + canvasX1 + "," + canvasY1 + ")-(" + canvasX2 + "," + canvasY2 + ")", 10, 10);
					gc.fillText("YOU: (" + player.x + "," + player.y + ")", 10, 20);

					// Dashboard
					gc.setFill(Color.WHITE);
					gc.fillRect(5, MAX_Y - 65, MAX_X - 10, 60);
					gc.setFill(Color.RED);
					gc.fillText("Your next challenge", 7, MAX_Y - 52);
					gc.setFill(Color.BLACK);
					gc.fillText(Challenge.challenge[0], 7, MAX_Y - 32);

					gc.fillText("Food", 800, MAX_Y - 52);
					gc.setFill(Color.LIGHTGREY);
					gc.fillRect(850, MAX_Y - 62, 100, 10);
					gc.setFill(Color.LIGHTGREEN);
					gc.fillRect(850, MAX_Y - 62, player.getFood(), 10);

					
					// Show rulers (todo move this code)
//					gc.setFill(Color.BLACK);
//					for (int x = 0; x < MAX_X / TILE_SIZE; x++)
//						gc.fillText(String.valueOf(canvasX1 + x), x * TILE_SIZE, MAX_Y);
//
//					for (int y = 0; y < MAX_Y / TILE_SIZE; y++)
//						gc.fillText(String.valueOf(canvasY1 + y), 0, MAX_Y - (y * TILE_SIZE));
					
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					
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
		hostname = args[0];
		playerName = args[1];
		password = args[2];
		
		// play Flare song
		Media media = new Media( "file:///d:/eclipseflare/workspace/Flare.mp3");
		MediaPlayer mediaPlayer = new MediaPlayer( media);
		mediaPlayer.setCycleCount( MediaPlayer.INDEFINITE);
		mediaPlayer.play();

		// open connection to server

		try {
			connection = new Socket(hostname, 9999);

			outToServer = new DataOutputStream(connection.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			outToServer.writeBytes( FlareProtocol.CMD_PLAYER + ":" + playerName + ":" + password + '\n');
			String response = inFromServer.readLine();
			
			String[] parsedResponse = response.split( FlareServer.SEPAROTOR);
			
			player.setX( Float.parseFloat( parsedResponse[ 0]));
			player.setY( Float.parseFloat( parsedResponse[ 1]));
				
		} catch (Exception e) {
			e.printStackTrace();
		}

		canvasX1 = (int) ( player.getX() - (MAX_X / TILE_SIZE / 2) );
		canvasY1 = (int) ( player.getY() - (MAX_Y / TILE_SIZE / 2) );
		
		player.type = "you";
		creatures.add(player);

		launch(args);
	}
}
