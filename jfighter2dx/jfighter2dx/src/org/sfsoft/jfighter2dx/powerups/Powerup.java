package org.sfsoft.jfighter2dx.powerups;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Clase base para representar a los items que el personaje puede coger durante el juego (powerups, vida, armas, . . .)
 * @author Santiago Faci
 *
 */
public abstract class Powerup {

	public enum PowerupType {
		BOMB, SHIELD
	}
	
	private float x;
	private float y;
	private float time;
	private float y0;
	private float arc;
	private float speed;
	
	protected Rectangle rect;
	
	public Powerup(float x, float y0, float speed) {
		
		this.x = x;
		this.y = y0;
		this.y0 = y0;
		this.arc = 100;		
		this.time = 0;
		this.speed = speed;
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
	
	public float getSpeed() {
		return speed;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public Rectangle getRect() {
		return rect;
	}
	
	protected void setRect(Rectangle rect) {
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
	
	public abstract void draw(SpriteBatch batch);
	
	public void update(float dt) {
		
		time += dt;
		
		// Describe un movimiento senoidal con centro en y0 y apertura según la variable arc
		setY(arc * (float) Math.sin(time * 300f * Math.PI / 500) + y0);
		setX(getX() + getSpeed() * dt);
		
		setRectX(getX());
		setRectY(getY());	
	}
}
