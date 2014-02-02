package com.arkabytes.jumper2dx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Clase principal del juego
 * 
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class Jumper2DX extends Game {

	OrthographicCamera camara;
	SpriteBatch spriteBatch;
	BitmapFont font;
	// Sonidos del juego
	public Sound coinSound;
	public Sound jumpSound;
	public Sound downSound;
	public Sound gameOverSound;
	public Sound levelClearSound;
	public Sound levelSound;
	
	public Texture itemCoin;
	public Texture itemLife;
	
	public int coins;
	public int lives;
	public int level;
	
	/*
	 * Método invocado en el momento de crearse la aplicación
	 * @see com.badlogic.gdx.ApplicationListener#create()
	 */
	@Override
	public void create() {
		spriteBatch = new SpriteBatch();
		font = new BitmapFont();
		setScreen(new MainMenuScreen(this));
		
		// Carga los sonidos del juego
		coinSound = Gdx.audio.newSound(Gdx.files.internal("sounds/coin.wav"));
		jumpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.wav"));
		downSound = Gdx.audio.newSound(Gdx.files.internal("sounds/player_down.wav"));
		gameOverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/game_over.wav"));
		levelClearSound = Gdx.audio.newSound(Gdx.files.internal("sounds/level_clear.wav"));
		levelSound = Gdx.audio.newSound(Gdx.files.internal("sounds/level1.mp3"));
		
		// Para que no nos obligue a que las imágenes tengan tamaños potencia de 2
		Texture.setEnforcePotImages(false);
		// Carga algunas textura para la información en pantalla
		itemCoin = new Texture(Gdx.files.internal("items/coin.png"));
		itemLife = new Texture(Gdx.files.internal("items/life.png"));
		
		// Inicia los parámetros de la partida
		coins = 0;
		lives = 3;
		level = 1;
	}
	
	/**
	 * Reinicia la partida para generar una partida nueva
	 */
	public void reset() {
		
		coins = 0;
		lives = 3;
		level = 1;
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
	
	/*
	 * Método invocado cuando se destruye la aplicación
	 * Siempre va precedido de una llamada a 'pause()'
	 * @see com.badlogic.gdx.ApplicationListener#dispose()
	 */
	@Override
	public void dispose() {
		spriteBatch.dispose();
		font.dispose();
		
		coinSound.dispose();
		jumpSound.dispose();
		downSound.dispose();
		gameOverSound.dispose();
		levelClearSound.dispose();
	}
}
