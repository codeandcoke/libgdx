package org.sfsoft.jfighter2dx.characters;

import org.sfsoft.jfighter2dx.managers.ResourceManager;
import org.sfsoft.jfighter2dx.util.Constants;

import com.badlogic.gdx.math.Rectangle;

/**
 * Tipo de enemigo que es capaz de perseguir al personaje del juego
 * @author Santiago Faci
 *
 */
public class PursuerEnemy extends Enemy {

	private Ship ship;
	
	/**
	 * 
	 * @param x Posición inicial x
	 * @param y Posición inicial y
	 * @param speed Velocidad en x
	 */
	public PursuerEnemy(float x, float y, float speed) {
		super(x, y, speed);
		
		animation = ResourceManager.getAnimation("pursuer_enemy");
		
		setRect(new Rectangle(x, y, Constants.ENEMY_WIDTH, Constants.ENEMY_HEIGHT));
		setValue(10);
		setLives(1);
	}
	
	public void setShip(Ship ship) {
		this.ship = ship;
	}

	@Override
	public void update(float dt) {
		
		super.update(dt);
		
		setX(getX() + getSpeed() * dt);
		
		// Si están por delante del personaje, le seguirán en el eje Y mientras se acerca a él
		if (getX() > ship.getX()) {
			if (ship.getY() > getY()) {
				setY(getY() + (Math.abs(getSpeed()) * dt));
			}
			else if (ship.getY() < getY()) {
				setY(getY() - (Math.abs(getSpeed()) * dt));
			}
		}
		
		setRectX(getX());
		setRectY(getY());
	}
}
