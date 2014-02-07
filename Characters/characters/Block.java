package org.sfsoft.jfighter2dx.characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Clase base para cualquier elemento del juego que sea un bloque no animado
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class Block extends Character {
	// FIXME
	// private SpriteSheet spriteSheet;
	private int width;
	private int height;
	
	public Block(float x, float y, int width, int height) {
		super(x, y);
		// FIXME
		// this.spriteSheet = ResourceManager.getSpriteSheet("blocks");
		this.width = width;
		this.height = height;
	}
	
	public void update(float dt) {
		
		setX(getX() + getSpeed() * dt);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				// FIXME
				/*
				spriteSheet.getSprite(14, 5).draw(x + BLOCK_WIDTH * i, y - BLOCK_HEIGHT * (j + 1));*/		
			}
		}
		
	}
	
	
}
