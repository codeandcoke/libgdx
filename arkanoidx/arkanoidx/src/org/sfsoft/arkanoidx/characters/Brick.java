package org.sfsoft.arkanoidx.characters;

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
		YELLOW, BLACK, GREEN, WHITE, PURPLE, RED, BLUE, GRAY
	}
	
	BrickType type;
	int lives;
	int value;
	
	/**
	 * Constructor
	 * @param texture Textura del ladrillo
	 * @param x Posición x inicial
	 * @param y Posición y inicial
	 * @param type Tipo de ladrillo
	 * @param lives Duración del ladrillo (golpes)
	 * @param value Puntuación que da romper el ladrillo
	 */
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
