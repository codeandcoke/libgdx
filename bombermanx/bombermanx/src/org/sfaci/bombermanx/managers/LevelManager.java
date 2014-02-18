package org.sfaci.bombermanx.managers;

import org.sfaci.bombermanx.characters.Brick;
import org.sfaci.bombermanx.characters.Brick.BrickType;
import org.sfaci.bombermanx.characters.Enemy;
import org.sfaci.bombermanx.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Clase que gestiona los niveles del juego
 * @author Santiago Faci
 *
 */
public class LevelManager {

	public int currentLevel;
	public int powerups;
	// Limite de powerups que aparecen en un mismo nivel
	public int powerupsLimit;
	SpriteManager spriteManager;
	
	public LevelManager(SpriteManager spriteManager) {
		
		this.spriteManager = spriteManager;
		this.spriteManager.levelManager = this;
		
		currentLevel = 1;
		powerupsLimit = 5;
		powerups = 0;
	}
	
	/**
	 * Carga el nivel actual leyendo el fichero 'level' + currentLevel + '.txt'
	 */
	public void loadCurrentLevel() {
		
		FileHandle file = Gdx.files.internal("levels/level" + currentLevel + ".txt");
		String levelInfo = file.readString();
		
		int x = 0, y = Constants.SCREEN_HEIGHT - Constants.BRICK_HEIGHT;
		String[] rows = levelInfo.split("\n");
		Brick brick = null;
		for (String row : rows) {
			String[] brickIds = row.split(",");
			for (String brickId : brickIds) {
	
				if (brickId.trim().equals("-")) {
					x += Constants.BRICK_WIDTH;
					continue;
				}
				
				if (brickId.trim().equals("a")) {
					Enemy enemy = new Enemy(x, y, "enemy_blue", Enemy.Direction.VERTICAL);
					spriteManager.enemies.add(enemy);
					x += Constants.BRICK_WIDTH;
					continue;
				}
				
				if (brickId.trim().equals("u")) {
					Enemy enemy = new Enemy(x, y, "enemy_ugly", Enemy.Direction.VERTICAL);
					spriteManager.enemies.add(enemy);
					x += Constants.BRICK_WIDTH;
					continue;
				}
				
				if (brickId.trim().equals("b")) {
					Enemy enemy = new Enemy(x, y, "enemy_barrel", Enemy.Direction.VERTICAL);
					spriteManager.enemies.add(enemy);
					x += Constants.BRICK_WIDTH;
					continue;
				}
				
				if (brickId.trim().equals("c")) {
					Enemy enemy = new Enemy(x, y, "enemy_cookie", Enemy.Direction.VERTICAL);
					spriteManager.enemies.add(enemy);
					x += Constants.BRICK_WIDTH;
					continue;
				}
				
				brick = new Brick(getTextureBrick(brickId.trim()), x, y, getBrickType(brickId.trim()), 1, 1);
				spriteManager.bricks.add(brick);
				x += Constants.BRICK_WIDTH;
			}
			
			x = 0;
			y -= Constants.BRICK_HEIGHT;
		}
	}
	
	/**
	 * Pasa al siguiente nivel
	 */
	public void passLevel() {
		
		currentLevel++;
		resetLevel();
		loadCurrentLevel();
	}
	
	/**
	 * Resetea el nivel actual
	 */
	private void resetLevel() {
		spriteManager.bricks.clear();
		spriteManager.bombs.clear();
		spriteManager.enemies.clear();
		spriteManager.player.position = new Vector2(0, 0);
	}
	
	/**
	 * Obtiene el tipo de ladrillo según el caracter leído en el fichero de nivel
	 * @param brickId
	 * @return
	 */
	private BrickType getBrickType(String brickId) {
		
		switch (brickId) {
		case "x":
			return BrickType.BRICK;
		case "s":
			return BrickType.STONE;
		case "d":
			return BrickType.DOOR;
		default:
			return null;
		}
	}
	
	/**
	 * Obtiene la textura que corresponde con el tipo de ladrillo dado
	 * @param brickId
	 * @return
	 */
	private Texture getTextureBrick(String brickId) {
		
		switch (brickId) {
		case "x":
			return ResourceManager.getTexture("brick");
		case "s":
			return ResourceManager.getTexture("stone");
		case "d":
			return ResourceManager.getTexture("door");
		default:
			return null;
		}
	}
}
