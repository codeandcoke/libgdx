package org.sfaci.jumper2dx;

import org.sfaci.jumper2dx.managers.GameController;
import org.sfaci.jumper2dx.managers.GameRenderer;

import com.badlogic.gdx.Game;

/**
 * Clase principal del juego
 * 
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class Jumper2DX extends Game {

	public GameController gameController;
	public GameRenderer gameRenderer;
	
	public boolean paused;
	
	public enum GameState {
		START, RESUME;
	}
	public GameState gameState; 
	
	/*
	 * Método invocado en el momento de crearse la aplicación
	 * @see com.badlogic.gdx.ApplicationListener#create()
	 */
	@Override
	public void create() {
		
		gameController = new GameController(this);
		gameRenderer = new GameRenderer(gameController);
		
		paused = false;
		gameState = GameState.START;
		
		setScreen(new MainMenuScreen(this));
	}

	/*
	 * Método que se invoca cada vez que hay que renderizar
	 * Es el método donde se actualiza también la lógica del juego
	 * @see com.badlogic.gdx.ApplicationListener#pause()
	 */
	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void resize(int width, int height) {
		gameRenderer.resize(width, height);
	}
	
	/*
	 * Método invocado cuando se destruye la aplicación
	 * Siempre va precedido de una llamada a 'pause()'
	 * @see com.badlogic.gdx.ApplicationListener#dispose()
	 */
	@Override
	public void dispose() {
		
		gameRenderer.dispose();
	}
	
	/*
	 * Proporciona compatibilidad con Android
	 * El juego puede ser pasado a segundo plano y debería ser pausado
	 * 
	 */
	@Override
	public void pause() {
		paused = true;
	}
	
	/*
	 * Proporciona compatibilidad con Android
	 * El juego puede ser pasado a primer plano después de haber sido pausado
	 * 
	 */
	@Override
	public void resume()  {
		paused = false;
	}
}
