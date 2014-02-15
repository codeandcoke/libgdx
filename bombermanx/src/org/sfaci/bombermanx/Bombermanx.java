package org.sfaci.bombermanx;

import org.sfaci.bombermanx.screens.MainMenuScreen;
import org.sfaci.bombermanx.util.Constants;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Clase principal del proyecto principal del juego
 * 
 * @author Santiago Faci
 *
 */
public class Bombermanx extends Game {

	public OrthographicCamera camera;
	public SpriteBatch spriteBatch;
	public BitmapFont fuente;
	public Skin skin;
	
	@Override
	public void create() {
		spriteBatch = new SpriteBatch();
		fuente = new BitmapFont();
		
		// Crea la cámara y define la zona de visión del juego (toda la pantalla)
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		camera.update();
		
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		spriteBatch.dispose();
		fuente.dispose();
	}
	
	public Skin getSkin() {
		if(skin == null ) {
            skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        }
        return skin;
	}
	
}
