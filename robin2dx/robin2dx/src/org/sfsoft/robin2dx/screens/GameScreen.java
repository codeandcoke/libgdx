package org.sfsoft.robin2dx.screens;

import org.sfsoft.robin2dx.Robin2DX;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Pantalla de Juego, donde el usuario juega la partida
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class GameScreen implements Screen {

	final Robin2DX game;
	
	public GameScreen(Robin2DX game) {
		this.game = game;
	}
	
	@Override
	public void show() {
	}
	
	@Override
	public void render(float dt) {
		
		// En cada frame se limpia la pantalla
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (!game.paused) {
			// Actualizamos primero (es más eficiente)
			game.spriteManager.update(dt);
		}
		
		game.spriteManager.render();
	}
	
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
