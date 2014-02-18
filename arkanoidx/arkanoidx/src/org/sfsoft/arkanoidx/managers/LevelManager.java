package org.sfsoft.arkanoidx.managers;

import org.sfsoft.arkanoidx.characters.Brick;
import org.sfsoft.arkanoidx.characters.Brick.BrickType;
import org.sfsoft.arkanoidx.util.Constants;

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
	
				if (brickId.trim().equals("x")) {
					x += Constants.BRICK_WIDTH;
					continue;
				}
	
				brick = new Brick(getTextureBrick(brickId.trim()), x, y, BrickType.values()[Integer.valueOf(brickId.trim())], 1, 1);
				spriteManager.bricks.add(brick);
				x += Constants.BRICK_WIDTH;
			}
			
			x = 0;
			y -= Constants.BRICK_HEIGHT;
		}
	}
	
	/**
	 * Obtiene la textura que corresponde con el tipo de ladrillo dado
	 * @param brickId
	 * @return
	 */
	private Texture getTextureBrick(String brickId) {
		
		BrickType type = BrickType.values()[Integer.valueOf(brickId)];
		
		switch (type) {
		case YELLOW:
			return ResourceManager.getTexture("yellow_brick");
		case BLACK:
			return ResourceManager.getTexture("black_brick");
		case GREEN:
			return ResourceManager.getTexture("green_brick");
		case WHITE:
			return ResourceManager.getTexture("white_brick");
		case PURPLE:
			return ResourceManager.getTexture("purple_brick");
		case RED:
			return ResourceManager.getTexture("red_brick");
		case BLUE:
			return ResourceManager.getTexture("blue_brick");
		case GRAY:
			return ResourceManager.getTexture("gray_brick");
		default:
			return null;
		}
	}
}
