package org.sfsoft.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Pantalla de fin de partida. Se muestra cuando el usuario termina una partida
 * Se presenta un menú de game
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class GameOverScreen implements Screen {
	
	final Drop game;
	
	Stage menu;
	
	public GameOverScreen(Drop game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0.3f, 0.6f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// Muestra un menú de inicio
		game.spriteBatch.begin();
		game.fuente.draw(game.spriteBatch, "Fin del juego!!!!", 100, 150);
		game.fuente.draw(game.spriteBatch, "Tu puntuación: " + game.dropletCount, 100, 130);
		game.fuente.draw(game.spriteBatch, "Si quieres jugar otra partida pulsa la tecla 'N'", 100, 110);
		game.fuente.draw(game.spriteBatch, "Pulsa 'ESCAPE' para SALIR", 100, 90);
		game.spriteBatch.end();
		
		/*
		 * Si el usuario toca la pantalla se inicia la partida
		 */
		if (Gdx.input.isKeyPressed(Keys.N)) {
			/*
			 * Aquí habrá que reiniciar algunos aspectos del
			 * game de cara a empezar una nueva partida
			 */
			game.dropletCount = 0;
			game.setScreen(new GameScreen(game));
		}
		/*
		 * El usuario pulsa la tecla ESCAPE, se sale del game
		 */
		else if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
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
		game.dispose();
	}
}
