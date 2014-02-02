package org.sfsoft.robin2dx.managers;

import static org.sfsoft.robin2dx.utils.Constants.SCREEN_SPEED;

import org.sfsoft.robin2dx.Robin2DX;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;

/**
 * Clase encargada de la lógica del juego
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class GameController implements Disposable {

	public Robin2DX game;
	public TiledMap map;
	
	public GameController(Robin2DX game) {
		this.game = game;
		init();
	}
	
	private void init() {
		Texture.setEnforcePotImages(false);
	}
	
	public void start() {
		// Crea el mapa
		map = new TmxMapLoader().load("tiledmap1.tmx");
		//TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);
	}
	
	public void update(float dt) {
		
		handleInput(dt);
	}
	
	/**
	 * Gestiona el input del usuario
	 * En este caso simplemente vamos a movernos por el mapa
	 * @param dt
	 */
	private void handleInput(float dt) {
		
		OrthographicCamera camera = game.gameRenderer.camera;
		
		if (Gdx.input.isKeyPressed(Keys.LEFT))			
			camera.position.set(camera.position.x - SCREEN_SPEED * dt, camera.position.y, 0);

		if (Gdx.input.isKeyPressed(Keys.RIGHT))
			camera.position.set(camera.position.x + SCREEN_SPEED * dt, camera.position.y, 0);
		
		if (Gdx.input.isKeyPressed(Keys.UP))
			camera.position.set(camera.position.x, camera.position.y + SCREEN_SPEED * dt, 0);
		
		if (Gdx.input.isKeyPressed(Keys.DOWN))
			camera.position.set(camera.position.x, camera.position.y - SCREEN_SPEED * dt, 0);
		
		camera.update();
	}
	
	@Override
	public void dispose() {
		
	}
}
