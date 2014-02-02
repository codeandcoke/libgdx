package org.sfsoft.jfighter2dx;

import org.sfsoft.jfighter2dx.characters.Ship;
import org.sfsoft.jfighter2dx.managers.ConfigurationManager;
import org.sfsoft.jfighter2dx.managers.LevelManager;
import org.sfsoft.jfighter2dx.managers.ResourceManager;
import org.sfsoft.jfighter2dx.managers.SpriteManager;
import org.sfsoft.jfighter2dx.util.Constants;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

/**
 * Clase principal del juego
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class JFighter2DX extends Game {
	
	//  Estados del juego
	public enum GameState {
		MENU, PLAY, CREDITS, QUIT
	}
	
	public static GameState gameState;
	private SpriteManager spriteManager;
	private LevelManager levelManager;
	private ConfigurationManager configurationManager;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private BitmapFont font;
	private float dt;
	private Rectangle glViewport;

	public JFighter2DX() {
		super();
	}

	@Override
	public void create() {
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1024, 600);
		camera.update();
		glViewport = new Rectangle(0, 0, 1024, 600);
		
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		
		// Carga de todos los recursos del juego (gráficos, sonidos, . . .)
		ResourceManager.loadAllResources();		
		// Inicializa los elementos del juego
		spriteManager = new SpriteManager(batch);
		// Inicializa la configuración del juego
		configurationManager = new ConfigurationManager();
		// Inicializa el creador de niveles
		levelManager = new LevelManager(spriteManager);
		levelManager.readCurrentLevelFile();
	}
	
	@Override
	public void render() {
		
		dt = Gdx.graphics.getDeltaTime();
		
		// Pinta el fondo de la pantalla de azul oscuro (RGB + alpha)
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		// Limpia la pantalla
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		GL10 gl = Gdx.graphics.getGL10();
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gl.glViewport((int) glViewport.x, (int) glViewport.y,
                        (int) glViewport.width, (int) glViewport.height);
        
        camera.update();
        camera.apply(gl);
		
		update(dt);
		
		batch.begin();
			// Pinta en pantalla todos los elementos del juego
			spriteManager.draw(batch);	
			drawOnscreenText();
		
		batch.end();
	}
	
	/**
	 * Muestra la información de juego del personaje
	 */
	private void drawOnscreenText() {
		
		Ship ship = spriteManager.getShip();
	
		// Muestra la puntuación y nivel del jugador
		font.draw(batch, "PUNTOS: " + ship.getScore(), 15, 20);
		font.draw(batch, "NIVEL: " + levelManager.getCurrentLevel(), 130, 20);

		batch.draw(ResourceManager.getTexture("live"), 15, 30);
		font.draw(batch, "X " + spriteManager.getShip().getLives(), 50, 40);
		
		batch.draw(ResourceManager.getTexture("bomb_score"), 15, 45);
		font.draw(batch, "X " + spriteManager.getShip().getBombs(), 50, 55);
		
		batch.draw(ResourceManager.getTexture("missile_score"), 15, 60);
		font.draw(batch, "X " + spriteManager.getShip().getMissiles(), 50, 70);

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

	public void update(float dt) {
		
		//levelManager.generateLevel(level, dt);
		//levelManager.generateRandomLevel(dt);
		levelManager.generateLevelFromFile(dt);
		spriteManager.update(dt);
		
		spriteManager.getShip().update(dt, spriteManager);
	}
}
