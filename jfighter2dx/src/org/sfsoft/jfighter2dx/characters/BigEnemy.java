package org.sfsoft.jfighter2dx.characters;

import org.sfsoft.jfighter2dx.managers.ResourceManager;
import org.sfsoft.jfighter2dx.util.Constants;

import com.badlogic.gdx.math.Rectangle;

/**
 * Tipo de enemigo de grandes dimensiones y mayor resistencia
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class BigEnemy extends Enemy {
	
	/**
	 * 
	 * @param x Posición inicial x
	 * @param y Posición inicial y
	 * @param speed Velocidad en x
	 */
	public BigEnemy(float x, float y, float speed) {
		super(x, y, speed);
		
		animation = ResourceManager.getAnimation("big_enemy");
		setRect(new Rectangle(x, y, Constants.ENEMY_WIDTH * 2, Constants.ENEMY_HEIGHT * 2));
		setValue(10);
		setLives(4);
	}
	
	@Override
	public void update(float dt) {
		
		super.update(dt);
		
		setX(getX() + getSpeed() * dt);
		setRectX(getX());
	}
}
