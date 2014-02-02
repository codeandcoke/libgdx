package org.sfsoft.robin2dx.managers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import static org.sfsoft.robin2dx.utils.Constants.TILE_WIDTH;
import static org.sfsoft.robin2dx.utils.Constants.TILE_HEIGHT;

/**
 * Clase encargada del renderizado (pintado) del juego
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class GameRenderer {
	
	public GameController gameController;
	
	public SpriteBatch batch;
	public BitmapFont font;
	
	public OrthogonalTiledMapRenderer mapRenderer;
	public OrthographicCamera camera;

	public GameRenderer(GameController gameController) {
		this.gameController = gameController;
		init();
	}
	
	private void init() {
		font = new BitmapFont();
		
		/*
		 *  Crea una cámara y muestra 30x30 unidades del mundo
		 *  En este caso el mapa completo
		 */
		
		camera = new OrthographicCamera();
		// La cámara mostrará 20 celdas de ancho por 20 celdas de alto
		camera.setToOrtho(false, 15 * TILE_WIDTH, 15 * TILE_HEIGHT);
		camera.update();
		
		start();
	}
	
	/**
	 * Inicia algunos componentes del controlador
	 */
	public void start() {
		// Crea el renderizador del tiledmap
		mapRenderer = new OrthogonalTiledMapRenderer(gameController.map);
		batch = mapRenderer.getSpriteBatch();
		
		// Fija la cámara en el centro de la pantalla (x, y, z)
		camera.position.set(30 * TILE_WIDTH / 2, 30 * TILE_HEIGHT / 2, 0);
		camera.update();
		
	}
	
	/**
	 * Renderiza la pantalla en cada frame
	 */
	public void render() {
		
		mapRenderer.setView(camera);
		mapRenderer.render();
		
		// Inicia renderizado del juego
		batch.begin();
		batch.end();
	}
	
	/**
	 * Actualiza la cámara al redimensionar la ventana
	 * Si no se codifica el juego siempre tenderá a ocupar toda la ventana
	 * @param width
	 * @param height
	 */
	public void resize(int width, int height) {
		
		/*
		camera.viewportHeight = height;
		camera.viewportWidth = width;
		camera.setToOrtho(false, 30 * TILE_WIDTH, 30 * TILE_HEIGHT);
		camera.update();
		*/
	}
	
}
