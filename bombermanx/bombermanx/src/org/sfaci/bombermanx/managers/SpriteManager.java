package org.sfaci.bombermanx.managers;

import org.sfaci.bombermanx.characters.Brick;
import org.sfaci.bombermanx.characters.Player;
import org.sfaci.bombermanx.util.Constants;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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
	Vector2 oldPosition;
	
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
	 * Devuelve si un punto x,y corresponde a algún elemento con el que colisionar
	 * @param brickX La posición x del ladrillo
	 * @param brickY La pocisión y del ladrillo
	 * @param x La posición x del personaje
	 * @param y La posición y del personaje
	 * @return Si ladrillo y personaje colisionan
	 */
	private boolean isCollidable(float brickX, float brickY, float x, float y) {
				
		
		if ((brickX < (x + Constants.PLAYER_WIDTH)) && ((brickX + Constants.BRICK_WIDTH) > x) 
			&& (brickY < (y + Constants.PLAYER_HEIGHT) && (brickY + Constants.BRICK_HEIGHT) > y))
			
			return true;
		
		return false;
	}
	
	/**
	 * Comprueba las colisiones del juegador con los ladrillos
	 */
	private void checkBrickCollisions() {
	
		for (Brick brick : bricks) {
			
			if (isCollidable(brick.position.x, brick.position.y, player.position.x, player.position.y))
				switch (player.state) {
				case LEFT:
					player.position.x = brick.position.x + Constants.BRICK_WIDTH;
					break;
				case RIGHT:
					player.position.x = brick.position.x - Constants.PLAYER_WIDTH;
					break;
				case UP:
					player.position.y = brick.position.y - Constants.PLAYER_HEIGHT;
					break;
				case DOWN:
					player.position.y = brick.position.y + Constants.BRICK_HEIGHT;
					break;
				default:
				}
		}
	}
}
