package org.sfsoft.jfighter2dx.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Es la clase base para cualquier tipo de proyectil en el juego
 * @author Santiago Faci
 * @version 1.0
 *
 */
public abstract class Bullet extends Character {

	protected Texture texture;
	
	/**
	 * 
	 * @param x Posición inicial x
	 * @param y Posición inicial y
	 * @param speed Velocidad en x
	 */
	public Bullet(float x, float y, float speed) {
		super(x, y, speed);
	}
	
	public void draw(SpriteBatch batch) {
		
		batch.draw(texture, getX(), getY());
	}
	
	public abstract void update(float dt);

}
