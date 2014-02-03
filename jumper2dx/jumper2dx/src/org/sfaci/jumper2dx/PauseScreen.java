package org.sfaci.jumper2dx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Pantalla de pausa
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class PauseScreen implements Screen {
	
	final Jumper2DX game;
	
	OrthographicCamera camera;
	public State state;
	private Screen oldScreen;
	
	public enum State {
		PAUSE, DAMAGE;
	}
	
	public PauseScreen(Jumper2DX game, State state) {
		this.game = game;
		this.state = state;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1024, 768);
	}
	
	public PauseScreen(Jumper2DX game, State state, Screen oldScreen) {
		this(game, state);
		this.oldScreen = oldScreen;
	}

	@Override
	public void render(float delta) {
			
		camera.update();
		game.spriteBatch.setProjectionMatrix(camera.combined);
		
		game.spriteBatch.begin();
		game.font.setScale(2);
		if (state == State.DAMAGE) {
			game.coins = 0;
			game.font.draw(game.spriteBatch, "¡¡Tocado!!", 250, 500);
			game.font.draw(game.spriteBatch, "Toca la pantalla para volver a intentarlo", 250, 450);
		}
		else if (state == State.PAUSE) {
			game.font.draw(game.spriteBatch, "PAUSA", 250, 500);
			game.font.draw(game.spriteBatch, "Toca la pantalla para seguir jugando", 250, 450);
		}
		game.spriteBatch.end();
		
		/*
		 * Si el usuario toca la pantalla se sigue jugando
		 */
		if (Gdx.input.isTouched()) {
			if (state == State.DAMAGE)
				game.setScreen(new GameScreen(game));
			if (state == State.PAUSE)
				game.setScreen(oldScreen);
			
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