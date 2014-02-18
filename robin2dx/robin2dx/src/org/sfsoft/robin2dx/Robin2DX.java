package org.sfsoft.robin2dx;

import org.sfsoft.robin2dx.managers.ResourceManager;
import org.sfsoft.robin2dx.managers.SpriteManager;
import org.sfsoft.robin2dx.screens.MainMenuScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Clase principal del juego
 * 
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class Robin2DX extends Game {
	
	public boolean paused;
	public SpriteBatch spriteBatch;
	public BitmapFont font;
	
	public SpriteManager spriteManager;
	
	/*
	 * Método invocado en el momento de crearse la aplicación
	 * @see com.badlogic.gdx.ApplicationListener#create()
	 */
	@Override
	public void create() {
				
		paused = false;
		spriteBatch = new SpriteBatch();
		font = new BitmapFont();
		
		setScreen(new MainMenuScreen(this));
		
		ResourceManager.loadAllResources();
		spriteManager = new SpriteManager(this);
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
		spriteManager.resize(width, height);
	}
	
	/*
	 * Método invocado cuando se destruye la aplicación
	 * Siempre va precedido de una llamada a 'pause()'
	 * @see com.badlogic.gdx.ApplicationListener#dispose()
	 */
	@Override
	public void dispose() {
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
	 * El juego puede ser pasado a primer plano despu�s de haber sido pausado
	 * 
	 */
	@Override
	public void resume()  {
		paused = false;
	}
}
