package org.sfsoft.jfighter2dx.characters;

import java.util.ArrayList;
import java.util.List;

import org.sfsoft.jfighter2dx.characters.ShooterBullet.BulletDirection;
import org.sfsoft.jfighter2dx.managers.ResourceManager;
import org.sfsoft.jfighter2dx.util.Constants;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Un tipo de enemigo que es capaz de disparar
 * @author Santiago Faci
 *
 */
public class ShooterEnemy extends Enemy {

	private final float BULLET_RATE = 2f;
	
	private Array<Enemy> enemies;
	private BulletDirection bulletDirection;
	private float elapsedTimeBetweenBullets;
	
	/**
	 * 
	 * @param x Posición inicial x
	 * @param y Posición inicial y
	 * @param speed Velocidad en x
	 * @param bulletDirection Dirección de disparo
	 */
	public ShooterEnemy(float x, float y, float speed, BulletDirection bulletDirection) {
		super(x, y, speed);
		
		animation = ResourceManager.getAnimation("shooter_enemy");
		setRect(new Rectangle(x, y, Constants.ENEMY_WIDTH, Constants.ENEMY_HEIGHT));
		setValue(5);
		setLives(1);
		
		this.bulletDirection = bulletDirection;
		elapsedTimeBetweenBullets = 0;
	}

	/**
	 * Las balas disparadas se incorporan a la lista de enemigos del personaje
	 * @param enemies
	 */
	public void setEnemies(Array<Enemy> enemies) {
		this.enemies = enemies;
	}
	
	public void setBulletDirection(BulletDirection bulletDirection) {
		this.bulletDirection = bulletDirection;
	}
	
	@Override
	public void update(float dt) {
		
		super.update(dt);
		
		setX(getX() + getSpeed() * dt);
		setRectX(getX());
	}
	
	public void shoot(float dt) {
		
		elapsedTimeBetweenBullets += dt;
		
		if (elapsedTimeBetweenBullets >= BULLET_RATE) {
		
			elapsedTimeBetweenBullets = 0;
			Enemy bullet = new ShooterBullet(getX(), getY(), 150f, bulletDirection);
			enemies.add(bullet);
		}
	}
}
