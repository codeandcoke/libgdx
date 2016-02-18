package com.sfaci.link.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.sfaci.link.Link;

/**
 * Menú principal del juego
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class MainMenuScreen implements Screen {
	
	final Link game;
	OrthographicCamera camera;
	
	public MainMenuScreen(Link game) {
		this.game = game;
		
		/*camera = new OrthographicCamera();
		// Fija la resolución de la cámara del juego
		camera.setToOrtho(false, 1920, 1080);*/
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//camera.update();
		// Redimensiona la pantalla a la resolución de la cámara
//		game.spriteBatch.setProjectionMatrix(camera.combined);
		
		game.spriteBatch.begin();
		game.font.draw(game.spriteBatch, "Robin 2D", 100, 500);
		game.font.draw(game.spriteBatch, "Pulsa cualquier tecla para jugar", 100, 450);
		game.font.draw(game.spriteBatch, "Pulsa ESCAPE para salir", 100, 400);
		game.spriteBatch.end();
		
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			dispose();
			System.exit(0);
		}
		else if (Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
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