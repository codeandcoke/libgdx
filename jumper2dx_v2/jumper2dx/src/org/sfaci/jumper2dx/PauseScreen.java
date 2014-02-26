package org.sfaci.jumper2dx;

import org.sfaci.jumper2dx.Jumper2DX.GameState;
import org.sfaci.jumper2dx.managers.LevelManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Menú intermedio de juego (cuando matan al jugador)
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class PauseScreen implements Screen {
	
	final Jumper2DX game;
	
	OrthographicCamera camera;
	public State state;
	private Screen oldScreen;
	
	// Indica si se ha pausado la pantalla voluntariamente o por haber sido dañado el jugador
	public enum State {
		PAUSE, DAMAGE;
	}
	
	public PauseScreen(Jumper2DX game, State state) {
		this.game = game;
		this.state = state;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1024, 768);
	}

	@Override
	public void render(float delta) {
			
		camera.update();
		game.gameRenderer.batch.setProjectionMatrix(camera.combined);
		
		game.gameRenderer.batch.begin();
		game.gameRenderer.font.setScale(2);
		
		LevelManager.currentCoins = 0;
		game.gameRenderer.font.draw(game.gameRenderer.batch, "¡¡Tocado!!", 250, 500);
		game.gameRenderer.font.draw(game.gameRenderer.batch, "Toca la pantalla o una tecla para volver a intentarlo", 250, 450);
		game.gameRenderer.batch.end();
		
		/*
		 * Si el usuario toca la pantalla o pulsa una tecla se sigue jugando
		 */
		if ((Gdx.input.isTouched() || (Gdx.input.isKeyPressed(Keys.ANY_KEY)))) {
			
			game.gameState = GameState.RESUME;
			game.setScreen(new GameScreen(game));
			dispose();
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}