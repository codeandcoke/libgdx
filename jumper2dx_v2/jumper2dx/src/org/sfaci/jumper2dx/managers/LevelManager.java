package org.sfaci.jumper2dx.managers;

import org.sfaci.jumper2dx.characters.Enemy;
import org.sfaci.jumper2dx.characters.Item;
import org.sfaci.jumper2dx.characters.Platform;
import org.sfaci.jumper2dx.characters.Platform.Direction;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

/**
 * Gestor de niveles del juego
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class LevelManager {

	// Info del LevelManager
	public static final String LEVEL_DIR = "levels";
	public static final String LEVEL_PREFIX = "level";
	public static final String LEVEL_EXTENSION = ".tmx";

	// NPC del nivel actual
	public static Array<Enemy> enemies = new Array<Enemy>();
	public static Array<Item> items = new Array<Item>();
	public static Array<Platform> platforms = new Array<Platform>();
	
	// Mapa del nivel actual
	public static TiledMap map;
	
	// Parámetros de nivel
	public static int currentLevel = 1;
	public static int currentLives = 3;
	public static int totalCoins;
	public static int currentCoins;

	public static boolean highLevel = false;
	
	public static void passLevel() {
		currentLevel++;
	}
	
	public static String getCurrentLevelName() {
		return LEVEL_PREFIX + LevelManager.currentLevel;
	}
	
	public static String getCurrentLevelPath() {
		return LEVEL_DIR + "/" + LevelManager.getCurrentLevelName() + LEVEL_EXTENSION;
	}
	
	/**
	 * Carga el mapa de la pantalla actual
	 */
	public static void loadMap() {
		
		LevelManager.map = new TmxMapLoader().load(LevelManager.getCurrentLevelPath());
		TiledMapManager.collisionLayer = (TiledMapTileLayer) LevelManager.map.getLayers().get("terrain");
		TiledMapManager.objectLayer = (MapLayer) LevelManager.map.getLayers().get("objects");
		
		loadAnimateTiles();
		loadEnemies();
		loadPlatforms();
	}
	
	/**
	 * Carga los tiles animados
	 */
	private static void loadAnimateTiles() {
		
		// Anima los tiles animados
		TiledMapManager.animateTiles(TiledMapManager.COIN, 4);
		TiledMapManager.animateTiles(TiledMapManager.PLANT, 3);
		TiledMapManager.animateTiles(TiledMapManager.PLANT2, 3);
		TiledMapManager.animateTiles(TiledMapManager.BOX, 4);
		TiledMapManager.animateTiles(TiledMapManager.WATER_UP, 4);
		TiledMapManager.animateTiles(TiledMapManager.WATER_DOWN, 4);
	}
	
	/**
	 * Carga los enemigos del nivel actual
	 */
	private static void loadEnemies() {
		
		Enemy enemy = null;
		
		// Carga los objetos móviles del nivel actual
		for (MapObject object : LevelManager.map.getLayers().get("objects").getObjects()) {
			
			if (object instanceof RectangleMapObject) {
				RectangleMapObject rectangleObject = (RectangleMapObject) object;
				if (rectangleObject.getProperties().containsKey(TiledMapManager.ENEMY)) {
					Rectangle rect = rectangleObject.getRectangle();
					
					enemy = new Enemy();
					enemy.position.set(rect.x, rect.y);
					LevelManager.enemies.add(enemy);
				}
			}
		}
	}
	
	/**
	 * Sitúa un enemigo en la pantalla
	 * @param x Posición x
	 * @param y Posición y
	 */
	public static void addEnemy(float x, float y) {
		
		Enemy enemy = new Enemy();
		enemy.position.set(x * map.getProperties().get("tilewidth", Integer.class), y * map.getProperties().get("tileheight", Integer.class));
		enemies.add(enemy);
	}
	
	/**
	 * Hace aparecer un nuevo Item en la pantalla
	 * @param x Posición x
	 * @param y Posición y
	 */
	public static void raiseItem(final int x, final int y) {
		
		Timer.schedule(new Task(){
		    @Override
		    public void run() {
		    	Item item = new Item();
				item.position.set(x, y);
				LevelManager.items.add(item);
		    }
		}, 1);
	}
	
	/**
	 * Elimina una moneda de la pantalla
	 * @param x Posición x de la moneda
	 * @param y Posición y de la moneda
	 */
	public static void removeCoin(int x, int y) {
		
		TiledMapManager.collisionLayer.setCell(x, y, TiledMapManager.collisionLayer.getCell(0, 5));
		ResourceManager.getSound("coin").play();
		currentCoins++;
	}
	
	/**
	 * Carga las plataformas móviles de la pantalla actual
	 */
	public static void loadPlatforms() {
		
		Platform platform = null;
		
		// Carga los objetos móviles del nivel actual
		for (MapObject object : LevelManager.map.getLayers().get("objects").getObjects()) {
			
			if (object instanceof RectangleMapObject) {
				RectangleMapObject rectangleObject = (RectangleMapObject) object;
				if (rectangleObject.getProperties().containsKey(TiledMapManager.MOBILE)) {
					Rectangle rect = rectangleObject.getRectangle();
					
					Direction direction = null;
					if (Boolean.valueOf((String) rectangleObject.getProperties().get("right_direction")))
						direction = Direction.RIGHT;
					else
						direction = Direction.LEFT;
					
					platform = new Platform(rect.x, rect.y, TiledMapManager.PLATFORM_WIDTH, TiledMapManager.PLATFORM_HEIGHT, 
										    Integer.valueOf((String) rectangleObject.getProperties().get("offset")), direction);
					LevelManager.platforms.add(platform);
				}
			}
		}
	}
	
	/**
	 * Limpia el nivel actual
	 */
	public static void clearLevel() {
		LevelManager.enemies.clear();
		LevelManager.items.clear();
		LevelManager.platforms.clear();
	}
	
	/**
	 * Finaliza el nivel actual y prepara el siguiente
	 */
	public static void finishLevel() {
	
		ResourceManager.getSound(LevelManager.getCurrentLevelName()).stop();
		ResourceManager.getSound("level_clear").play();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ie) {}
	
		LevelManager.clearLevel();
		LevelManager.currentLevel++;
		// FIXME Anotarlo y leerlo en el mapa
		if (LevelManager.currentLevel == 3)
			LevelManager.highLevel = true;
	}
}
