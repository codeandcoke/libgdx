package org.sfaci.bombermanx.characters;

import com.badlogic.gdx.graphics.Texture;

/**
 * Clase que representa a cada uno de los ladrillos del juego
 * @author Santiago Faci
 * @version
 *
 */
public class Brick extends Character {

	// Tipo de ladrillo
	public enum BrickType {
		BRICK, STONE
	}
	
	BrickType type;
	int lives;
	int value;
	
	public Brick(Texture texture, float x, float y, BrickType type, int lives, int value) {
		
		super(texture, x, y);
		this.type = type;
		this.lives = lives;
		this.value = value;
	}
	
	@Override
	public void update(float dt) {
		
		super.update(dt);
	}
}
