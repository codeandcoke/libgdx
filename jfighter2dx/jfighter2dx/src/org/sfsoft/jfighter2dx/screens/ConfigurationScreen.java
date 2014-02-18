package org.sfsoft.jfighter2dx.screens;

import org.sfsoft.jfighter2dx.JFighter2DX;
import org.sfsoft.jfighter2dx.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Pantalla de configuración del juego
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class ConfigurationScreen implements Screen {

	JFighter2DX game;
	Stage stage;
	// Almacena las preferencias (en %UserProfile%/.prefs/PreferencesName)
	Preferences prefs;
	
	public ConfigurationScreen(JFighter2DX game) {
		this.game = game;
	}
	
	private void loadScreen() {
		
		// Grafo de escena que contendrá todo el menú
		stage = new Stage();
					
		// Crea una tabla, donde añadiremos los elementos de menú
		Table table = new Table();
		table.setPosition(Constants.SCREEN_WIDTH / 2.5f, Constants.SCREEN_HEIGHT / 1.5f);
		// La tabla ocupa toda la pantalla
	    table.setFillParent(true);
	    table.setHeight(500);
	    stage.addActor(table);
		
		Label label = new Label("Configurar JFighter2DX", game.getSkin());
		table.addActor(label);
		
		final CheckBox checkSound = new CheckBox(" Sonido", game.getSkin());
		checkSound.setChecked(prefs.getBoolean("sound"));
		checkSound.setPosition(label.getOriginX(), label.getOriginY() - 40);
		checkSound.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;	
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				
				prefs.putBoolean("sound", checkSound.isChecked());
			}
		});
		table.addActor(checkSound);
		
		Label resLabel = new Label("Dificultad", game.getSkin());
		resLabel.setPosition(label.getOriginX(), label.getOriginY() - 70);
		table.addActor(resLabel);
		
		String[] resolutionsArray = {"Baja", "Media", "Alta"};
		final List resolutionsList = new List(resolutionsArray, game.getSkin());
		resolutionsList.setPosition(label.getOriginX(),  label.getOriginY() - 150);
		resolutionsList.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;	
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				
				switch (resolutionsList.getSelectedIndex()) {
				case 0:
					prefs.putString("difficulty", "low");
					break;
				case 1:
					prefs.putString("difficulty", "medium");
					break;
				case 2:
					prefs.putString("difficulty", "high");
					break;
				default:
				}
			}
		});
		table.addActor(resolutionsList);
		
		TextButton buttonMainMenu = new TextButton("Volver", game.getSkin());
		buttonMainMenu.setPosition(label.getOriginX(), label.getOriginY() - 220);
		buttonMainMenu.setWidth(200);
		buttonMainMenu.setHeight(40);
		buttonMainMenu.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;	
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				
				prefs.flush();
				dispose();
				game.setScreen(new MainMenuScreen(game));
			}
		});
		table.addActor(buttonMainMenu);
		
		Gdx.input.setInputProcessor(stage);
	}
	
	/**
	 * Carga las preferencias del juego
	 */
	private void loadPreferences() {
		
		prefs = Gdx.app.getPreferences(Constants.APP);
		
		// Coloca los valores por defecto (para la primera ejecución)
		if (!prefs.contains("sound"))
			prefs.putBoolean("sound", true);
	}
	
	@Override
	public void show() {
	
		loadPreferences();
		loadScreen();
	}
	
	@Override
	public void render(float dt) {
	
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
		stage.act(dt);
		stage.draw();
	}

	@Override
	public void dispose() {
	
		stage.dispose();
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resize(int arg0, int arg1) {
	}

	@Override
	public void resume() {
	
		
	}
}
