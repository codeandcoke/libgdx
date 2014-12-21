package org.sfsoft.drop;

import org.sfsoft.drop.managers.ResourceManager;
import org.sfsoft.drop.managers.SpriteManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;

/**
 * Pantalla del game, donde el usuario juega la partida
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class GameScreen implements Screen, InputProcessor {

	final Drop game;
	ResourceManager resManager;
	SpriteManager spriteManager;
	
	// Indica si el game está en pause
	public boolean pause = false;
	
	public GameScreen(Drop game) {

		this.game = game;

		resManager = new ResourceManager();
		resManager.loadAllResources();

		spriteManager = new SpriteManager(game, this, resManager);

		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public void render(float dt) {

		// Pinta el fondo de la pantalla de azul oscuro (RGB + alpha)
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		// Limpia la pantalla
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		/* Comprueba la entrada del usuario, actualiza
		 * la posición de los elementos del game y
		 * dibuja en pantalla
		 */
		if (!pause) {
			spriteManager.checkInput(dt);
			spriteManager.update(dt);
		}
		// La pantalla debe se redibujada aunque el game está pausado
		spriteManager.draw();
	}

	@Override
	public void show() {

		spriteManager.show();
	}

	@Override
	public void hide() {

		spriteManager.hide();
	}
	
	@Override
	public void dispose() {
		// Libera los recursos utilizados
		spriteManager.dispose();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
		pause = true;
	}

	@Override
	public void resume() {
		pause = false;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		// Pone el game en pausa
		if (keycode == Keys.P)
			pause = !pause;
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
