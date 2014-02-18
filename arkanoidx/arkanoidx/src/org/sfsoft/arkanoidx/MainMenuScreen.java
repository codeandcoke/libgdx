package org.sfsoft.arkanoidx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Pantalla de inicio
 * Se presenta el menú de juego
 * @author Santiago Faci
 *
 */
public class MainMenuScreen implements Screen {
	
	final Arkanoidx game;
	
	public MainMenuScreen(Arkanoidx game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		game.camera.update();
		
		// Muestra un menú de inicio
		game.spriteBatch.begin();
		game.fuente.draw(game.spriteBatch, "Bienvenido a Arkanoidx!!!!", 100, 150);
		game.fuente.draw(game.spriteBatch, "Pulsa para empezar", 100, 130);
		game.fuente.draw(game.spriteBatch, "Pulsa 'ESCAPE' para SALIR", 100, 110);
		game.spriteBatch.end();
		
		/*
		 * Si el usuario toca la pantalla se inicia la partida
		 */
		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			dispose();
			System.exit(0);
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
