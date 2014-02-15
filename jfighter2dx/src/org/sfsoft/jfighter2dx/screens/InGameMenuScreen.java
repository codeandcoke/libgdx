package org.sfsoft.jfighter2dx.screens;

import org.sfsoft.jfighter2dx.JFighter2DX;
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
 * Menú 'in-game'
 * Se muestra cuando el jugador pulsa la tecla ESCAPE y muestra algunas opciones de la partida
 * y la opción de salir del juego
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class InGameMenuScreen implements Screen {

	JFighter2DX game;
	GameScreen gameScreen;
	Stage stage;
	
	public InGameMenuScreen(JFighter2DX game, GameScreen gameScreen) {
		this.game = game;
		this.gameScreen = gameScreen;
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
		
		Label label = new Label("JFighter2DX", game.getSkin());
		table.addActor(label);
		
		TextButton buttonResume = new TextButton("Continuar", game.getSkin());
		buttonResume.setPosition(label.getOriginX(), label.getOriginY() - 120);
		buttonResume.setWidth(200);
		buttonResume.setHeight(40);
		buttonResume.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;	
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				
				dispose();
				game.setScreen(gameScreen);
			}
		});
		table.addActor(buttonResume);
		
		TextButton buttonMainMenu = new TextButton("Volver al Menu Principal", game.getSkin());
		buttonMainMenu.setPosition(label.getOriginX(), label.getOriginY() - 170);
		buttonMainMenu.setWidth(200);
		buttonMainMenu.setHeight(40);
		buttonMainMenu.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;	
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				
				dispose();
				game.setScreen(new MainMenuScreen(game));
			}
		});
		table.addActor(buttonMainMenu);
		
		TextButton buttonQuit = new TextButton("Salir", game.getSkin());
		buttonQuit.setPosition(label.getOriginX(), label.getOriginY() - 220);
		buttonQuit.setWidth(200);
		buttonQuit.setHeight(40);
		buttonQuit.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;	
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				
				dispose();
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
