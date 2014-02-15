package org.sfsoft.jfighter2dx.screens;

import org.sfsoft.jfighter2dx.JFighter2DX;
import org.sfsoft.jfighter2dx.characters.Ship;
import org.sfsoft.jfighter2dx.managers.LevelManager;
import org.sfsoft.jfighter2dx.managers.ResourceManager;
import org.sfsoft.jfighter2dx.managers.SpriteManager;
import org.sfsoft.jfighter2dx.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 * Pantalla de juego, donde el usuario juega su partida
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class GameScreen implements Screen {

	JFighter2DX game;
	
	private SpriteManager spriteManager;
	private LevelManager levelManager;
	
	// Dibuja formas en la pantalla (el óvalo de protección en este caso)
	private ShapeRenderer shapeRenderer;
	
	// Indica el tipo de partida
	public enum GameType {
		QUICK, HISTORY;
	}
	public GameType gameType;
	
	public GameScreen(JFighter2DX game, GameType gameType) {
		this.game = game;
		this.gameType = gameType;
		
		loadScreen();
	}
	
	/**
	 * Carga la pantalla (sólo una vez)
	 */
	private void loadScreen() {
		
		// Carga de todos los recursos del juego (gráficos, sonidos, . . .)
		ResourceManager.loadAllResources();		
		
		// Inicializa los elementos del juego
		spriteManager = new SpriteManager(game);
		// Inicializa el creador de niveles
		levelManager = new LevelManager(spriteManager);
		levelManager.readCurrentLevelFile();
		
		shapeRenderer = new ShapeRenderer();
	}

	/**
	 * Coloca al juego en pausa cada vez que esta pantalla se oculta
	 * (para mostrar el InGameMenuScreen)
	 */
	@Override
	public void show() {
		
		game.paused = false;
	}

	@Override
	public void render(float dt) {
		
		Gdx.gl.glClearColor(0, 1, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			
        game.camera.update();
		
		update(dt);
		
		game.batch.begin();
			// Pinta en pantalla todos los elementos del juego
			spriteManager.draw(game.batch);	
			drawOnscreenText();
		game.batch.end();
	}
	
	/**
	 * Muestra la información de juego del personaje
	 */
	private void drawOnscreenText() {
		
		Ship ship = spriteManager.getShip();
	
		// Muestra la puntuación y nivel del jugador
		game.font.draw(game.batch, "PUNTOS: " + ship.getScore(), 15, 20);
		game.font.draw(game.batch, "NIVEL: " + levelManager.getCurrentLevel(), 130, 20);

		game.batch.draw(ResourceManager.getTexture("live"), 15, 30);
		game.font.draw(game.batch, "X " + spriteManager.getShip().getLives(), 50, 40);
		
		game.batch.draw(ResourceManager.getTexture("bomb_score"), 15, 45);
		game.font.draw(game.batch, "X " + spriteManager.getShip().getBombs(), 50, 55);
		
		game.batch.draw(ResourceManager.getTexture("missile_score"), 15, 60);
		game.font.draw(game.batch, "X " + spriteManager.getShip().getMissiles(), 50, 70);

		// Muestra el escudo protector de la nave
		shapeRenderer.begin(ShapeType.Line);
		if (ship.getShieldTime() > 3) {
			shapeRenderer.ellipse(ship.getX(), ship.getY() - Constants.SHIP_HEIGHT / 1.5f, Constants.SHIP_WIDTH + 1f, Constants.SHIP_WIDTH + 1);
			shapeRenderer.ellipse(ship.getX(), ship.getY() - Constants.SHIP_HEIGHT / 2f, Constants.SHIP_WIDTH + 11, Constants.SHIP_WIDTH + 11);
			shapeRenderer.ellipse(ship.getX(), ship.getY() - Constants.SHIP_HEIGHT / 2.2f, Constants.SHIP_WIDTH + 21, Constants.SHIP_WIDTH + 21);
		}
		else if ((ship.getShieldTime() > 2) && (ship.getShieldTime() <= 3)) {
			
			shapeRenderer.ellipse(ship.getX(), ship.getY() - Constants.SHIP_HEIGHT / 1.5f, Constants.SHIP_WIDTH + 1, Constants.SHIP_WIDTH + 1);
			shapeRenderer.ellipse(ship.getX(), ship.getY() - Constants.SHIP_HEIGHT / 2f, Constants.SHIP_WIDTH + 11, Constants.SHIP_WIDTH + 11);
		}
		else if ((ship.getShieldTime() > 0) && (ship.getShieldTime() <= 2)) {
			shapeRenderer.ellipse(ship.getX(), ship.getY() - Constants.SHIP_HEIGHT / 1.5f, Constants.SHIP_WIDTH + 1, Constants.SHIP_WIDTH + 1);
		}
		shapeRenderer.end();
	}
	
	private void update(float dt) {
		
		//levelManager.generateLevel(level, dt);
		switch (gameType) {
		case QUICK:
			levelManager.generateRandomLevel(dt);
			break;
		case HISTORY:
			levelManager.generateLevelFromFile(dt);
			break;
		default:
		}
		
		/*
		 * Si el juego está en pausa no se refresca la lógica
		 */
		if (!game.paused) {
			spriteManager.update(dt);
			spriteManager.getShip().update(dt, spriteManager);
		}
		
		handleKeyboard();
	}
	
	/**
	 * Controla la entrada de teclado
	 */
	private void handleKeyboard() {
		
		/*
		 * Si el usuario pulsa la tecla ESCAPE se muestra un menú durante el juego
		 */
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			game.setScreen(new InGameMenuScreen(game, this));
		}
	}
	
	@Override
	public void dispose() {
		shapeRenderer.dispose();
	}

	/**
	 * Cuando esta pantalla se oculta, se pausa
	 */
	@Override
	public void hide() {
		
		game.paused = true;
	}

	@Override
	public void pause() {
		
		game.paused = true;
	}

	@Override
	public void resize(int arg0, int arg1) {
	}

	@Override
	public void resume() {
		game.paused = false;
	}
}
