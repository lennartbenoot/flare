package org.flare.server.player;

public class PlayerStore {
	
	private static PlayerStore playerStore = new PlayerStore( );
	
	public static PlayerStore getInstance() {
		return playerStore;
	}
	
	public Player findPlayerByName( String name) {
		
		Player player = null;
		
		if ( name.equals("Slaine")) {
			
			player = new Player();
			player.x = 1000;
			player.y = 1000;
			player.setName( "Slaine");
			player.setChallenge( 1);
			
		}
		
		return player;
		
	}

}
