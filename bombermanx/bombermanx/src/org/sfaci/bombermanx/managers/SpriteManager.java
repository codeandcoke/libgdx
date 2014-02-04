package org.sfaci.bombermanx.managers;

import org.sfaci.bombermanx.characters.Brick;
import org.sfaci.bombermanx.characters.Player;
import org.sfaci.bombermanx.util.Constants;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 * Clase que gestiona los sprites del juego
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class SpriteManager {

	public Player player;
	public Array<Brick> bricks;
	
	SpriteBatch batch;
	
	public SpriteManager(SpriteBatch batch) {
		
		this.batch = batch;
		
		player = new Player(ResourceManager.getTexture("player_idle"), 0, 0, 3);
		bricks = new Array<Brick>();
	}
	
	public void render() {
		
		batch.begin();
			player.render(batch);
			for (Brick brick : bricks)
				brick.render(batch);
		batch.end();
	}
	
	public void update(float dt) {
		
		checkBrickCollisions();
		
		player.update(dt);
		for (Brick brick : bricks)
			brick.update(dt);
	}
	
	/**
	 * Comprueba las colisiones del juegador con los ladrillos
	 * TODO Comprobar colisiones arriba y abajo (eje y)
	 */
	private void checkBrickCollisions() {
	
		for (Brick brick : bricks) {
			
			if ((player.position.y + Constants.PLAYER_HEIGHT >= brick.position.y) && (player.position.y <= (brick.position.y + Constants.BRICK_HEIGHT))) {
				// El jugador choca con un ladrillo a su izquierda
				if ((player.position.x >= brick.position.x) && (player.position.x <= (brick.position.x + Constants.BRICK_WIDTH))) {
				
					 player.position.x = brick.position.x + Constants.BRICK_WIDTH;
				}
				// El jugador choca con un ladrillo a su derecha
				if (((player.position.x + Constants.PLAYER_WIDTH) >= brick.position.x) && ((player.position.x + Constants.PLAYER_WIDTH) <= (brick.position.x + Constants.BRICK_WIDTH))) {
					
					 player.position.x = brick.position.x - Constants.PLAYER_WIDTH;
				}
			}
		}
	}
}
