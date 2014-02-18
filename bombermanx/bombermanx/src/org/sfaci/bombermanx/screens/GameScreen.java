package org.sfaci.bombermanx.screens;

import org.sfaci.bombermanx.Bombermanx;
import org.sfaci.bombermanx.characters.Bomb;
import org.sfaci.bombermanx.characters.Player;
import org.sfaci.bombermanx.managers.LevelManager;
import org.sfaci.bombermanx.managers.ResourceManager;
import org.sfaci.bombermanx.managers.SpriteManager;
import org.sfaci.bombermanx.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;

/**
 * Pantalla del juego, donde el usuario juega la partida
 * @author Santiago Faci
 *
 */
public class GameScreen implements Screen, InputProcessor {

	final Bombermanx game;
	
	// Indica si el juego está en pausa
	boolean paused = false;
	
	LevelManager levelManager;
	SpriteManager spriteManager;
	
	public GameScreen(Bombermanx game) {
		this.game = game;
			
		ResourceManager.loadAllResources();
		
		spriteManager = new SpriteManager(game.spriteBatch);
		levelManager = new LevelManager(spriteManager);
		levelManager.loadCurrentLevel();
		
		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void render(float dt) {
		
		// Pinta el fondo de la pantalla de azul oscuro (RGB + alpha)
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		// Limpia la pantalla
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// Actualiza la cámara
		game.camera.update();
		
		/* Comprueba la entrada del usuario, actualiza
		 * la posición de los elementos del juego y
		 * dibuja en pantalla
		 */
		if (!paused) {
			handleInput(dt);
			spriteManager.update(dt);
		}
		
		spriteManager.render();
	}
	
	/*
	 * Comprueba la entrada del usuario (teclado o pantalla si está en el móvil)
	 */
	private void handleInput(float dt) {
		
		// Si el jugador ha explotado se pierde el control sobre él
		if (spriteManager.player.state == Player.State.EXPLODE)
			return;
		
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			spriteManager.player.move(new Vector2(dt, 0));
			spriteManager.player.state = Player.State.RIGHT;
		}
		else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			spriteManager.player.move(new Vector2(-dt, 0));
			spriteManager.player.state = Player.State.LEFT;
		}
		else if (Gdx.input.isKeyPressed(Keys.UP)) {
			spriteManager.player.move(new Vector2(0, dt));
			spriteManager.player.state = Player.State.UP;
		}
		else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			spriteManager.player.move(new Vector2(0, -dt));
			spriteManager.player.state = Player.State.DOWN;
		}
		else {
			spriteManager.player.state = Player.State.IDLE;
		}
		
		// La tecla espacio coloca una bomba
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			spriteManager.player.putBomb();
		}
	}

	@Override
	public void hide() {
		
	}
	
	@Override
	public void dispose() {
		
	}

	@Override
	public void resize(int width, int height) {
		game.camera.viewportWidth = width;
		game.camera.viewportHeight = height;
		game.camera.update();
	}

	@Override
	public void pause() {
		paused = true;
	}

	@Override
	public void resume() {
		paused = false;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		// Pone el juego en pausa
		if (keycode == Keys.P)
			paused = !paused;
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
