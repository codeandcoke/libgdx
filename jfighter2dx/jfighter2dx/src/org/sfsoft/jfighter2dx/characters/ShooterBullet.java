package org.sfsoft.jfighter2dx.characters;

import org.sfsoft.jfighter2dx.managers.ResourceManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Proyectiles lanzados por los enemigos ShooterEnemy
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class ShooterBullet extends Enemy {

	public enum BulletDirection {
		UP, LEFT, RIGHT, DOWN
	}
	
	private final float fallingSpeed = 50f;
	private BulletDirection bulletDirection;
	
	private Texture texture;
	
	/**
	 * 
	 * @param x Posición inicial x
	 * @param y Posición inicial y
	 * @param speed Velocidad en x
	 * @param bulletDirection Dirección de la bala
	 */
	public ShooterBullet(float x, float y, float speed, BulletDirection bulletDirection) {
		super(x, y, speed);
		
		this.bulletDirection = bulletDirection;
		texture = ResourceManager.getTexture("shooter_bullet");
		setRect(new Rectangle(x, y, texture.getWidth(), texture.getHeight()));
	}

	@Override
	public void update(float dt) {
		
		//super.update(dt);
		
		switch (bulletDirection) {
			case UP:
				setY(getY() - fallingSpeed * dt);
				setX(getX() - getSpeed() * dt);
				break;
			case DOWN:
				setY(getY() + fallingSpeed * dt);
				setX(getX() - getSpeed() * dt);
				break;
			case LEFT:
				setX(getX() - getSpeed() * dt);
				break;
			case RIGHT:
				setX(getX() + getSpeed() * dt);
				break;
			default:
				break;
		}			
		
		setRectX(getX());
		setRectY(getY());
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(texture, getX(), getY());
	}
}
