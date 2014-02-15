package org.sfsoft.jfighter2dx.characters;

import org.sfsoft.jfighter2dx.managers.ResourceManager;
import org.sfsoft.jfighter2dx.util.Constants;

import com.badlogic.gdx.math.Rectangle;

/**
 * Enemigo estándar, de pequeñas dimensiones y poca movilidad
 * @author Santiago Faci
 *
 */
public class SmallEnemy extends Enemy {

	/**
	 * 
	 * @param x Posición inicial x
	 * @param y Posición inicial y
	 * @param speed Velocidad en x
	 */
	public SmallEnemy(float x, float y, float speed) {
		super(x, y, speed);
		
		animation = ResourceManager.getAnimation("small_enemy");
		setRect(new Rectangle(x, y, Constants.ENEMY_WIDTH, Constants.ENEMY_WIDTH));
		setValue(5);
		setLives(1);
	}
	
	@Override
	public void update(float dt) {
		
		super.update(dt);
		
		setX(getX() + getSpeed() * dt);
		setRectX(getX());
	}
}
