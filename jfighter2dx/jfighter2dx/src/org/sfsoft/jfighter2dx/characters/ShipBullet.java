package org.sfsoft.jfighter2dx.characters;

import org.sfsoft.jfighter2dx.managers.ResourceManager;

import com.badlogic.gdx.math.Rectangle;

/**
 * Proyectiles que lanza la nave del personaje principal
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class ShipBullet extends Bullet {

	/**
	 * 
	 * @param x Posición inicial x
	 * @param y Posición inicial y
	 * @param speed Velocidad en x
	 */
	public ShipBullet(float x, float y, float speed) {
		super(x, y, speed);
		
		texture = ResourceManager.getTexture("ship_bullet");
		setRect(new Rectangle(x, y, texture.getWidth(), texture.getHeight()));
	}

	@Override
	public void update(float dt) {
		setX(getX() + getSpeed() * dt);
		setRectX(getX());
	}
}
