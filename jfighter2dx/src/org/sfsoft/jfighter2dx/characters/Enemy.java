package org.sfsoft.jfighter2dx.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Clase base de todos los enemigos del personaje en el juego
 * @author Santiago Faci
 *
 */
public abstract class Enemy extends Character {
	
	public enum EnemyType {
		SMALL_ENEMY, STONE, SHOOTER_ENEMY, PURSUER_ENEMY, BIG_ENEMY, STATIC_SHOOTER_ENEMY, SMART_ENEMY
	}
	
	// Valor en puntos del enemigo (al ser derribado por el jugador)
	private int value;
	
	public Enemy(float x, float y) {
		super(x, y);
	}
	
	public Enemy(float x, float y, float speed) {
		super(x, y, speed);
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * Calcula cuál será el movimiento del enemigo y lo desplaza
	 * @param dt
	 */
	public void update(float dt) {
		
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = animation.getKeyFrame(stateTime, true);
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(currentFrame, getX(), getY());
	}
}
