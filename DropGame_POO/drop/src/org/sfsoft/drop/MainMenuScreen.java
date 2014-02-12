package org.sfsoft.drop;

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
	
	final Drop juego;
	
	OrthographicCamera camara;
	
	public MainMenuScreen(Drop juego) {
		this.juego = juego;
		
		camara = new OrthographicCamera();
		camara.setToOrtho(false, 1024, 768);
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camara.update();
		juego.spriteBatch.setProjectionMatrix(camara.combined);
		
		// Muestra un menú de inicio
		juego.spriteBatch.begin();
		juego.fuente.draw(juego.spriteBatch, "Bienvenido a Drop!!!!", 100, 150);
		juego.fuente.draw(juego.spriteBatch, "Pulsa para empezar", 100, 130);
		juego.fuente.draw(juego.spriteBatch, "Pulsa 'ESCAPE' para SALIR", 100, 110);
		juego.spriteBatch.end();
		
		/*
		 * Si el usuario toca la pantalla se inicia la partida
		 */
		if (Gdx.input.isTouched()) {
			juego.setScreen(new GameScreen(juego));
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
