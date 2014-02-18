package org.sfsoft.arkanoidx.managers;

import org.sfsoft.arkanoidx.characters.Ball;
import org.sfsoft.arkanoidx.characters.Board;
import org.sfsoft.arkanoidx.characters.Brick;
import org.sfsoft.arkanoidx.util.Constants;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 * Clase que gestiona los sprites del juego
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class SpriteManager {

	public Board board;
	public Ball ball;
	public Array<Brick> bricks;
	
	SpriteBatch batch;
	
	public SpriteManager(SpriteBatch batch) {
		
		this.batch = batch;
		
		board = new Board(ResourceManager.getTexture("board"), 0, 0, 3);
		ball = new Ball(ResourceManager.getTexture("ball"), Constants.SCREEN_WIDTH / 2, 250, this);
		bricks = new Array<Brick>();
	}
	
	public void render() {
		
		batch.begin();
			board.render(batch);
			ball.render(batch);
			for (Brick brick : bricks)
				brick.render(batch);
		batch.end();
	}
	
	public void update(float dt) {
		
		board.update(dt);
		ball.update(dt);
		for (Brick brick : bricks)
			brick.update(dt);
	}
}
