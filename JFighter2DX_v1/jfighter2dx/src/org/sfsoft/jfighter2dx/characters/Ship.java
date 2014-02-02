package org.sfsoft.jfighter2dx.characters;

import static org.sfsoft.jfighter2dx.util.Constants.HEIGHT;
import static org.sfsoft.jfighter2dx.util.Constants.WIDTH;

import java.util.ArrayList;
import java.util.List;

import org.sfsoft.jfighter2dx.managers.ConfigurationManager;
import org.sfsoft.jfighter2dx.managers.ResourceManager;
import org.sfsoft.jfighter2dx.managers.SpriteManager;
import org.sfsoft.jfighter2dx.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Nave del personaje del juego
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class Ship extends Character {

	// Proyectiles disparados por la nave
	private List<Bullet> bullets;
	private int bombs;
	private int missiles;
	private int score;
	private float shieldTime;
	private float bulletTime;
	private float missileTime;
	private float missileRate;
	private float bombTime;
	private float bombRate;
	
	public Ship(float x, float y, float speed) {
		super(x, y, speed);
		
		bullets = new ArrayList<Bullet>();
		bombs = 3;
		missiles = 10;
		score = 0;
		shieldTime = 0;
		
		missileRate = 0.5f;
		bombRate = 0.5f;
		
		animation = ResourceManager.getAnimation("ship");
		setRect(new Rectangle(x, y, Constants.SHIP_WIDTH, Constants.SHIP_HEIGHT));
	}
	
	public List<Bullet> getBullets() {
		return bullets;
	}
	
	public void setBullets(List<Bullet> bullets) {
		this.bullets = bullets;
	}
	
	public int getBombs() {
		return bombs;
	}
	
	public void setBombs(int bombs) {
		this.bombs = bombs;
	}
	
	public int getMissiles() {
		return missiles;
	}
	
	public void setMissiles(int missiles) {
		this.missiles = missiles;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void addScore(int score) {
		this.score += score;
	}
	
	public float getShieldTime() {
		return shieldTime;
	}
	
	public void setShieldTime(float time) {
		this.shieldTime = time;
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		
		batch.draw(currentFrame, getX(), getY());
	}
	
	public void update(float dt, SpriteManager spriteManager) {
		
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = animation.getKeyFrame(stateTime, true);
		
		bulletTime += dt;
		bombTime += dt;
		missileTime += dt;
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			
			if (getX() > 0)
				setX(getX() - getSpeed() * dt);
		}
		
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			
			if (getX() < WIDTH - Constants.ENEMY_WIDTH - 10f)
				setX(getX() + getSpeed() * dt);
		}
		
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			
			if (getY() > 5f)
				setY(getY() - getSpeed() * dt);
		}
		
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			
			if (getY() < HEIGHT - Constants.ENEMY_HEIGHT)
				setY(getY() + getSpeed() * dt);
		}
		
		setRectX(getX());
		setRectY(getY());
		
		// Disparo estándar
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {

			if (bulletTime >= getBulletRate()) {
				bulletTime = 0;
				
				// Dispara un proyectil
				shoot();
			}
		}
		
		// Lanzar bomba
		if (Gdx.input.isKeyPressed(Keys.Z)) {
			
			if (getBombs() > 0) {
				if (bombTime >= bombRate) {
					bombTime = 0;
					
					spriteManager.killAllEnemies();
					setBombs(getBombs() - 1);
				}
			}
		}
		
		// Lanzar misil
		if (Gdx.input.isKeyPressed(Keys.X)) {
			
			if (getMissiles() > 0) {
				if (missileTime >= missileRate) {
					missileTime = 0;
					
					setMissiles(getMissiles() - 1);
					launchMissile();
				}
			}
		}
		
		setShieldTime(getShieldTime() - dt);
		if (getShieldTime() < 0)
			setShieldTime(0);
	}
	
	/**
	 * Dispara un proyectil
	 */
	public void shoot() {
		
		Bullet bullet = new ShipBullet(getX() + 50, getY(), getBulletSpeed());
		getBullets().add(bullet);
		if (ConfigurationManager.SOUND)
			ResourceManager.getSound("shoot").play();
		
	}
	
	/**
	 * Lanza un misil
	 */
	public void launchMissile() {
		
		Missile missile = new Missile(getX(), getY() + 20, getBulletSpeed() / 2);
		getBullets().add(missile);
	}
	
}
