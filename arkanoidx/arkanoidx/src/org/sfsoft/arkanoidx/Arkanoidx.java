package org.sfsoft.arkanoidx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Clase principal del proyecto principal del juego
 * 
 * @author Santiago Faci
 * @version curso 2014-2015
 *
 * TODO Añadir más niveles
 * TODO Añadir sonidos/música
 * TODO Utilizar TextureAtlas para almcenar y cargar las texturas
 * TODO Mostrar HUD (nivel, vidas, puntuación)
 */
public class Arkanoidx extends Game {

	SpriteBatch spriteBatch;
	BitmapFont font;
	
	@Override
	public void create() {
		spriteBatch = new SpriteBatch();
		font = new BitmapFont();
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		spriteBatch.dispose();
		font.dispose();
	}
}
