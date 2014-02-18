package org.sfsoft.jfighter2dx.screens;

import org.sfsoft.jfighter2dx.JFighter2DX;
import org.sfsoft.jfighter2dx.screens.GameScreen.GameType;
import org.sfsoft.jfighter2dx.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Pantalla principal del juego, donde se muestra el menú principal
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class MainMenuScreen implements Screen {

	JFighter2DX game;
	Stage stage;
	
	public MainMenuScreen(JFighter2DX game) {
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
		
	    // Etiqueta de texto
		Label label = new Label("Bienvenido a JFighter2DX", game.getSkin());
		table.addActor(label);
		
		// Botón
		TextButton buttonPlay = new TextButton("Partida Rapida", game.getSkin());
		buttonPlay.setPosition(label.getOriginX(), label.getOriginY() - 120);
		buttonPlay.setWidth(200);
		buttonPlay.setHeight(40);
		buttonPlay.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				
				dispose();
				game.setScreen(new GameScreen(game, GameType.QUICK));
			}
		});
		table.addActor(buttonPlay);
		
		// Botón
		TextButton buttonHistory = new TextButton("Modo Historia", game.getSkin());
		buttonHistory.setPosition(label.getOriginX(), label.getOriginY() - 170);
		buttonHistory.setWidth(200);
		buttonHistory.setHeight(40);
		buttonHistory.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				
				dispose();
				game.setScreen(new GameScreen(game, GameType.HISTORY));
			}
		});
		table.addActor(buttonHistory);
		
		// Botón
		TextButton buttonConfig = new TextButton("Configurar", game.getSkin());
		buttonConfig.setPosition(label.getOriginX(), label.getOriginY() - 220);
		buttonConfig.setWidth(200);
		buttonConfig.setHeight(40);
		buttonConfig.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				
				dispose();
				game.setScreen(new ConfigurationScreen(game));
			}
		});
		table.addActor(buttonConfig);
		
		// Botón
		TextButton buttonQuit = new TextButton("Salir", game.getSkin());
		buttonQuit.setPosition(label.getOriginX(), label.getOriginY() - 270);
		buttonQuit.setWidth(200);
		buttonQuit.setHeight(40);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
}
