package org.sfsoft.jfighter2dx;

import org.sfsoft.jfighter2dx.managers.ConfigurationManager;
import org.sfsoft.jfighter2dx.screens.MainMenuScreen;
import org.sfsoft.jfighter2dx.util.Constants;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Clase principal del juego
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class JFighter2DX extends Game {
	
	private Skin skin;
	
	public OrthographicCamera camera;
	public SpriteBatch batch;
	public BitmapFont font;
	
	public int score;
	public boolean paused;
	
	public ConfigurationManager configurationManager;

	public JFighter2DX() {
		super();
	}

	@Override
	public void create() {
		
		configurationManager = new ConfigurationManager();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		camera.update();
		
		batch = new SpriteBatch();
		font = new BitmapFont();
		
		setScreen(new MainMenuScreen(this));
	}
	
	@Override
	public void render() {
		super.render();
	}
	
	public Skin getSkin() {
        if(skin == null ) {
            skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        }
        return skin;
    }	
}
