package org.sfaci.jumper2dx;

import org.sfaci.jumper2dx.Jumper2DX.GameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

/**
 * Pantalla de Juego, donde el usuario juega la partida
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class GameScreen implements Screen {

	final Jumper2DX game;
	
	public GameScreen(Jumper2DX game) {
		this.game = game;
	}
	
	/*
	 * Método que se invoca cuando esta pantalla es
	 * la que se está mostrando
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		if (game.gameState == GameState.START) {
			game.gameController.start();
		}
		else if (game.gameState == GameState.RESUME) {
			game.gameController.resume();
		}
		
		game.gameRenderer.start();
	}
	
	@Override
	public void render(float dt) {
		
		if (!game.paused) {
			// Actualizamos primero (es más eficiente)
			game.gameController.update(Gdx.graphics.getDeltaTime());
		}
		
		// En cada frame se limpia la pantalla
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.gameRenderer.render();
	}
	
	/*
	 * Método que se invoca cuando está pantalla
	 * deja de ser la principal
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override
	public void hide() {
		
	}
	
	@Override
	public void dispose() {
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
