package com.sfaci.link.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.sfaci.link.Link;
import com.sfaci.link.characters.Enemy;
import com.sfaci.link.characters.GreenEnemy;
import com.sfaci.link.characters.Player;
import com.sfaci.link.characters.YellowEnemy;
import com.sfaci.link.util.Constants;

/**
 * Clase encargada la lógica y renderizado del juego
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class SpriteManager {
	
	Link game;

    LevelManager levelManager;
	public OrthographicCamera camera;
	Batch batch;
	
	Player player;
	Array<Enemy> enemies;

	// Pool de rectángulos (mejora la eficiencia si se trabaja con muchos)
	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject () {
			return new Rectangle();
		}
	};

	public SpriteManager(Link game) {
		this.game = game;

		// Inicia la cámara para jugar
		camera = new OrthographicCamera();

		camera.setToOrtho(false, 20 * Constants.TILE_WIDTH,
				20 * Constants.TILE_HEIGHT);
		//camera.zoom = 1 / 2f;
		camera.update();

        levelManager = new LevelManager(this);
		levelManager.loadCurrentLevel();

		// Hay que utilizar el spritebatch del mapa para pintar el nivel.
		// En caso contrario no ubica ni escala bien al personaje en el mapa
		batch = levelManager.mapRenderer.getBatch();
		
		// Posiciona al jugador en el mapa
		player = new Player(15 * Constants.TILE_WIDTH, 10 * Constants.TILE_HEIGHT);
	}
	
	public void update(float dt) {
		
		camera.position.set(player.position.x, player.position.y, 0);
		
		handleInput();
		player.update(dt);
		checkCollisions();

        for (Enemy enemy : enemies)
            enemy.update(dt);
	}
	
	private void handleInput() {
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			player.velocity.x = -Player.SPEED;
			player.state = Player.State.LEFT;
		}
		else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			player.velocity.x = Player.SPEED;
			player.state = Player.State.RIGHT;
		}
		else if (Gdx.input.isKeyPressed(Keys.UP)) {
			player.velocity.y = Player.SPEED;
			player.state = Player.State.UP;
		}
		else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			player.velocity.y = -Player.SPEED;
			player.state = Player.State.DOWN;
		}
		else {
			player.velocity.x = 0;
			player.velocity.y = 0;
			player.state = Player.State.IDLE;
		}
	}
	
	public void render() {
		
		//camera.zoom = 1 / 2f;
		camera.update();
		// Renderiza el mapa
		levelManager.mapRenderer.setView(camera);
		levelManager.mapRenderer.render();
		
		// Inicia renderizado del juego
		batch.begin();
		player.render(batch);
        for (Enemy enemy : enemies)
            enemy.render(batch);
		batch.end();
	}
	
	/**
	 * Comprueba las colisiones del jugador con los elementos del mapa
	 * Ahora mismo sólo comprueba las colisiones con las casas
	 */
	private void checkCollisions() {
		
		Array<Rectangle> tiles = new Array<>();
		
		// Recoge las celdas de la posición del jugador	
		int startX = (int) (player.position.x + player.velocity.x);
		int endX = (int) (player.position.x + Constants.PLAYER_WIDTH + player.velocity.x);
		int startY = (int) (player.position.y + player.velocity.y);
		int endY = (int) (player.position.y + Constants.PLAYER_HEIGHT + player.velocity.y);
		getTilesPosition(startX, startY, endX, endY, tiles);
		
		// Comprueba las colisiones con el jugador
		for (Rectangle tile : tiles) {
			if (tile.overlaps(player.rect)) {
				
				switch (player.state) {
				case LEFT:
					player.position.x = tile.getX();
					break;
				case RIGHT:
					player.position.x = tile.getX() - Constants.PLAYER_WIDTH - player.velocity.x;
					break;
				case UP:
					player.position.y = tile.getY() - Constants.PLAYER_HEIGHT - player.velocity.y;
					break;
				case DOWN:
					player.position.y = tile.getY() + player.velocity.y + 1;
					break;
				default:
				}
				
			}
		}
	}
	
	/**
	 * Obtiene la lista de celdas de interés en las que está situado el jugador
	 * Ahora mismo sólo comprueba las celdas de las casas
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param tiles
	 */
	private void getTilesPosition(int startX, int startY, int endX, int endY, Array<Rectangle> tiles) {
		
		tiles.clear();
	
		for (int y = startY; y <= endY; y++) {
			for (int x = startX; x <= endX; x++) {
				int xCell = (int) (x / levelManager.collisionLayer.getTileWidth());
				int yCell = (int) (y / levelManager.collisionLayer.getTileHeight());
				Cell cell = levelManager.collisionLayer.getCell(xCell, yCell);
				
				// Si es un bloque se añade para comprobar colisiones
				if ((cell != null) && (cell.getTile().getProperties().containsKey("house"))) {
					Rectangle rect = rectPool.obtain();
					rect.set((int) (Math.ceil(x / 16f) * 16), (int) (Math.ceil(y / 16f) * 16), 0, 0);
					tiles.add(rect);
				}
			}
		}
	}
	
	public void resize(int width, int height) {
		
		camera.viewportHeight = height;
		camera.viewportWidth = width;
		camera.update();
	}
}
