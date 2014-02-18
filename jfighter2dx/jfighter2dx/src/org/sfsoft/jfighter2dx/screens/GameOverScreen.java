package org.sfsoft.jfighter2dx.screens;

import java.util.List;

import org.sfsoft.jfighter2dx.JFighter2DX;
import org.sfsoft.jfighter2dx.managers.ConfigurationManager;
import org.sfsoft.jfighter2dx.managers.ConfigurationManager.Score;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

/**
 * Pantalla de fin de partida, donde va el usuario cuando muere su personaje
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class GameOverScreen implements Screen {

	JFighter2DX game;
	Stage stage;
	
	private boolean done;
	
	public GameOverScreen(JFighter2DX game) {
		this.game = game;
		
		done = false;
	}
	
	/**
	 * Carga las puntuaciones en el menú
	 * @param table
	 * @param stage
	 * @param x
	 * @param y
	 */
	private void loadScores(Table table, Stage stage, float x, float y) {
		
		// Lee las puntuaciones
		List<Score> scores = ConfigurationManager.getTopScores();
		
		Label labelList = null;
		for (Score score : scores) {
			labelList = new Label(score.name + " - " + score.score, game.getSkin());
			labelList.setPosition(x, y);
			table.addActor(labelList);
			y -= 20;
		}
	}
	
	private void loadScreen() {
		
		// Grafo de escena que contendrá todo el menú
		stage = new Stage();
					
		// Crea una tabla, donde añadiremos los elementos de menú
		final Table table = new Table();
		table.setPosition(Constants.SCREEN_WIDTH / 2.5f, Constants.SCREEN_HEIGHT / 1.5f);
		// La tabla ocupa toda la pantalla
	    table.setFillParent(true);
	    table.setHeight(500);
	    stage.addActor(table);
		
		final Label label = new Label("Mejores puntuaciones JFighter2DX", game.getSkin());
		table.addActor(label);
		
		// Carga la lista de puntuaciones (top 10)
		loadScores(table, stage, label.getOriginX(), -50);
		
		Label labelScore = new Label("Tu puntuacion: " + game.score, game.getSkin());
		labelScore.setPosition(label.getOriginX(), label.getOriginY() - 300);
		table.addActor(labelScore);
		
		// El usuario ya ha rellenado su nombre
		if (done) {
			TextButton buttonQuit = new TextButton("Volver", game.getSkin());
			buttonQuit.setPosition(label.getOriginX(), label.getOriginY() - 350);
			buttonQuit.setWidth(200);
			buttonQuit.setHeight(40);
			buttonQuit.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					return true;	
				}
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					
					game.setScreen(new MainMenuScreen(game));
				}
			});
			table.addActor(buttonQuit);
		}
		else {
		// El usuario aún no he escrito su nombre
			final TextField textName = new TextField("Introduce tu nombre", game.getSkin());
			textName.setPosition(label.getOriginX(), label.getOriginY() - 350);
			textName.setWidth(200);
			textName.setHeight(40);
			table.addActor(textName);
			
			TextButton buttonQuit = new TextButton("Ok", game.getSkin());
			buttonQuit.setPosition(label.getOriginX(), label.getOriginY() - 400);
			buttonQuit.setWidth(200);
			buttonQuit.setHeight(40);
			buttonQuit.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					return true;	
				}
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					
					ConfigurationManager.addScores(textName.getText(), game.score);
					stage.clear();
					done = true;
					loadScreen();
				}
			});
			table.addActor(buttonQuit);
		}
		
		
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
