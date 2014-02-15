package org.sfsoft.jfighter2dx.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

/**
 * Representa cualquier elemento animado (juegador o NPC) del juego
 * @author Santiago Faci
 *
 */
public abstract class Character implements Disposable {

	// Velocidad del personaje
	protected float speed;
	// Velocidad de los proyectiles del personaje
	private float bulletSpeed;
	// Ratio de disparo
	private float bulletRate;
	// Puntos (si procede)
	private int points;
	// Vidas
	private int lives;
	
	// Posición y rectángulo representados por el personaje
	protected float x;
	protected float y;
	private Rectangle rect;
	
	// Animación del personaje
	protected Animation animation;
	protected float stateTime;
	protected TextureRegion currentFrame;
	
	// Dirección de salida del personaje
	public enum Direction {
		LEFT, RIGHT
	}
	
	public Character(float x, float y) {
		
		// Default values
		speed = 200f;
		bulletSpeed = 400f;
		bulletRate = 0.2f;
		points = 100;
		lives = 1;
		
		this.x = x;
		this.y = y;
	}
	
	public Character(float x, float y, float speed) {
		
		// Default values
		bulletSpeed = 400f;
		bulletRate = 0.2f;
		points = 100;
		lives = 1;
		
		this.speed = speed;
		this.x = x;
		this.y = y;
	}
	
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getBulletSpeed() {
		return bulletSpeed;
	}

	public void setBulletSpeed(float bulletSpeed) {
		this.bulletSpeed = bulletSpeed;
	}

	public float getBulletRate() {
		return bulletRate;
	}

	public void setBulletRate(float bulletRate) {
		this.bulletRate = bulletRate;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public Rectangle getRect() {
		return rect;
	}
	
	public void setRect(Rectangle rect) {
		this.rect = rect;
	}
	
	public float getRectX() {
		return rect.getX();
	}
	
	public void setRectX(float x) {
		rect.setX(x);
	}
	
	public float getRectY() {
		return rect.getY();
	}
	
	public void setRectY(float y) {
		rect.setY(y);
	}
	
	// Recibe un impacto
	public void hit() {
		lives--;
	}
	
	// Dibuja en pantalla el personaje
	public abstract void draw(SpriteBatch batch);
	
	public void die() {}
	
	@Override
	public void dispose() {
		
		rect = null;
		animation = null;
	}
}
