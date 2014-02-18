package org.sfaci.bombermanx.screens;

import org.sfaci.bombermanx.Bombermanx;
import org.sfaci.bombermanx.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Pantalla de inicio
 * Se presenta el menú de juego
 * @author Santiago Faci
 *
 */
public class MainMenuScreen implements Screen {
	
	final Bombermanx game;
	Stage stage;
	
	public MainMenuScreen(Bombermanx game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		loadScreen();
	}

	@Override
	public void render(float dt) {
		
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// Pinta el menú
		stage.act(dt);
		stage.draw();
	}
	
	private void loadScreen() {
		// Grafo de escena que contendrá todo el menú
		stage = new Stage();
					
		// Crea una tabla, donde añadiremos los elementos de menú
		Table table = new Table();
		table.setPosition(0, Constants.SCREEN_HEIGHT / 1.5f);
		// La tabla ocupa toda la pantalla
	    table.setFillParent(true);
	    table.setHeight(Constants.SCREEN_HEIGHT);
	    stage.addActor(table);
		
	    // Etiqueta de texto
		Label label = new Label("Bienvenido a Bombermanx", game.getSkin());
		label.setPosition(Constants.SCREEN_WIDTH / 2 - label.getWidth() / 2, label.getOriginY() - 20);
		table.addActor(label);
		
		// Botón
		TextButton buttonPlay = new TextButton("Nueva Partida", game.getSkin());
		buttonPlay.setWidth(Constants.SCREEN_WIDTH / 3);
		buttonPlay.setPosition(Constants.SCREEN_WIDTH / 2 - buttonPlay.getWidth() / 2, label.getOriginY() - 40);
		buttonPlay.setHeight(20);
		buttonPlay.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				
				dispose();
				game.setScreen(new GameScreen(game));
			}
		});
		table.addActor(buttonPlay);
		
		// Botón
		TextButton buttonConfig = new TextButton("Configurar", game.getSkin());
		buttonConfig.setWidth(Constants.SCREEN_WIDTH / 3);
		buttonConfig.setPosition(Constants.SCREEN_WIDTH / 2 - buttonConfig.getWidth() / 2, label.getOriginY() - 80);
		buttonConfig.setHeight(20);
		buttonConfig.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				
				//dispose();
				//game.setScreen(new ConfigurationScreen(game));
			}
		});
		table.addActor(buttonConfig);
		
		// Botón
		TextButton buttonQuit = new TextButton("Salir", game.getSkin());
		buttonQuit.setWidth(Constants.SCREEN_WIDTH / 3);
		buttonQuit.setPosition(Constants.SCREEN_WIDTH / 2 - buttonQuit.getWidth() / 2, label.getOriginY() - 120);
		buttonQuit.setHeight(20);
		buttonQuit.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;	
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				
				game.dispose();
				System.exit(0);
			}
		});
		table.addActor(buttonQuit);
		
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}
