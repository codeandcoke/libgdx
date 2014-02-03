package org.sfaci.jumper2dx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Menú principal del juego
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class MainMenuScreen implements Screen {
	
	final Jumper2DX game;
	
	OrthographicCamera camera;
	
	public MainMenuScreen(Jumper2DX game) {
		this.game = game;
		this.game.reset();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1024, 768);
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		game.spriteBatch.setProjectionMatrix(camera.combined);
		
		game.spriteBatch.begin();
		game.font.setScale(2);
		game.font.draw(game.spriteBatch, "Super Jumper2D", 100, 500);
		game.font.draw(game.spriteBatch, "Toca la pantalla para jugar", 100, 450);
		game.font.draw(game.spriteBatch, "Pulsa ESCAPE para salir", 100, 400);
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