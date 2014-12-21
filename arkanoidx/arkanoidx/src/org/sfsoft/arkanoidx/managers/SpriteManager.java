package org.sfsoft.arkanoidx.managers;

import org.sfsoft.arkanoidx.Arkanoidx;
import org.sfsoft.arkanoidx.GameOverScreen;
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
 */
public class SpriteManager {

	public Board board;
	public Ball ball;
	public Array<Brick> bricks;
	
	SpriteBatch batch;
	Arkanoidx game;
	
	public SpriteManager(Arkanoidx game, SpriteBatch batch) {

		this.game = game;
		this.batch = batch;
		
		board = new Board(ResourceManager.getTexture("board"), 0, 0, 3);
		ball = new Ball(ResourceManager.getTexture("ball"), Constants.SCREEN_WIDTH / 2, 250, this);
		bricks = new Array<>();
	}

	/**
	 * Pinta el estado de la partida
	 */
	public void render() {
		
		batch.begin();
			board.render(batch);
			ball.render(batch);
			for (Brick brick : bricks)
				brick.render(batch);
		batch.end();
	}

	/**
	 * Actualiza el estado de la partida
	 * @param dt
	 */
	public void update(float dt) {
		
		board.update(dt);
		ball.update(dt);
		for (Brick brick : bricks)
			brick.update(dt);

		// Si la bola cae se pierde una vida
		if (ball.y < 0) {
			board.lives--;
			ball = new Ball(ResourceManager.getTexture("ball"), Constants.SCREEN_WIDTH / 2, 250, this);
			// Cuando se terminan las vida se muestra la pantalla
			// de Game Over
			if (board.lives == 0) {
				game.setScreen(new GameOverScreen(game));
			}
		}
	}
}
