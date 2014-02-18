package org.sfaci.bombermanx.managers;

import org.sfaci.bombermanx.characters.Brick;
import org.sfaci.bombermanx.characters.Brick.BrickType;
import org.sfaci.bombermanx.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

/**
 * Clase que gestiona los niveles del juego
 * @author Santiago Faci
 *
 */
public class LevelManager {

	public int currentLevel;
	SpriteManager spriteManager;
	
	public LevelManager(SpriteManager spriteManager) {
		
		this.spriteManager = spriteManager;
		currentLevel = 1;
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
	
				brick = new Brick(getTextureBrick(brickId.trim()), x, y, getBrickType(brickId.trim()), 1, 1);
				spriteManager.bricks.add(brick);
				x += Constants.BRICK_WIDTH;
			}
			
			x = 0;
			y -= Constants.BRICK_HEIGHT;
		}
	}
	
	private BrickType getBrickType(String brickId) {
		
		switch (brickId) {
		case "x":
			return BrickType.BRICK;
		case "s":
			return BrickType.STONE;
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
		default:
			return null;
		}
	}
}
