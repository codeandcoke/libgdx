package org.sfaci.jumper2dx.managers;

import org.sfaci.jumper2dx.characters.Enemy;
import org.sfaci.jumper2dx.characters.Item;
import org.sfaci.jumper2dx.characters.Platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;

/**
 * Clase que renderiza todos los elementos del juego
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class GameRenderer implements Disposable {

	public OrthographicCamera camera;
	public static float CAMERA_OFFSET = 0;
	public SpriteBatch batch;
	private GameController gameController;
	
	// Fuente para los menús
	public BitmapFont font;
	
	OrthogonalTiledMapRenderer mapRenderer;
	
	public GameRenderer(GameController gameController) {
		this.gameController = gameController;
		init();
	}
	
	/**
	 * Inicializa el GameRenderer
	 */
	private void init() {
		
		font = new BitmapFont();
		
		// Crea una cámara y muestra 30x20 unidades del mundo
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 30, 20);
		camera.update();
		
		// Activa face culling
		Gdx.gl.glCullFace(GL20.GL_CULL_FACE);
		
		start();
	}
	
	/**
	 * Inicia el GameRenderer
	 */
	public void start() {
		mapRenderer = new OrthogonalTiledMapRenderer(LevelManager.map);
		batch = mapRenderer.getSpriteBatch();
	}
	
	public void render() {
		
		// Fija la cámara para seguir al personaje en el centro de la pantalla y altura fija (eje y)
		camera.position.set(gameController.player.position.x + 18 / 2, 125, 0);
		
		// En los niveles de alturas el jugador puede mover la cámara hacia arriba y abajo
		if (LevelManager.highLevel)
			camera.position.set(gameController.player.position.x + 18 / 2, 125 + CAMERA_OFFSET, 0);
		
		camera.zoom = 1 / 2f;
		camera.update();
		mapRenderer.setView(camera);
		mapRenderer.render(new int[]{0, 1});
		
		// Inicia renderizado del juego
		batch.begin();
		// Pinta al jugador
		gameController.player.render(batch);
		for (Enemy enemy : LevelManager.enemies)
			enemy.render(batch);
		for (Item item : LevelManager.items)
			item.render(batch);
		// Pinta la información en partida relativa al jugador
		font.setScale(0.8f);
		batch.draw(ResourceManager.getTexture("item_coin"), camera.position.x - 60, camera.position.y - 135 - 12);
		font.draw(batch, " X " + LevelManager.currentCoins, camera.position.x - 50, camera.position.y - 135);
		batch.draw(ResourceManager.getTexture("item_life"), camera.position.x + 40, camera.position.y - 135 - 12);
		font.draw(batch, " X " + gameController.lives, camera.position.x + 50, camera.position.y - 135);
		font.draw(batch, "level " + LevelManager.currentLevel, camera.position.x + 100, camera.position.y - 135);
		// Pinta las plataformas móviles del nivel actual
		for (Platform platform : LevelManager.platforms)
			platform.render(batch);
		
		// Si el juego está pausado pinta el mensaje en pantalla
		if (gameController.game.paused) {
			font.draw(batch, "PAUSA", camera.position.x - 60, camera.position.y + 50);
			font.draw(batch, "Pulsa P para seguir jugando", camera.position.x - 60, camera.position.y + 35);
		}
		
		batch.end();
	}
	
	/**
	 * Se ejecuta cuando se redimensiona la pantalla del juego
	 * @param width
	 * @param height
	 */
	public void resize(int width, int height) {
		
		camera.viewportHeight = height;
		camera.viewportWidth = width;
		camera.update();
	}
	
	@Override
	public void dispose() {
		
		batch.dispose();
		font.dispose();
	}
	
}
