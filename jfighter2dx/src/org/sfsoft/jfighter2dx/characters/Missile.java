package org.sfsoft.jfighter2dx.characters;

import org.sfsoft.jfighter2dx.managers.ResourceManager;
import org.sfsoft.jfighter2dx.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Clase que representa a los mísiles que lanza la nave del personaje
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class Missile extends Bullet {

	private Animation animation;
	private float acceleration;
	
	/**
	 * 
	 * @param x Posición inicial x
	 * @param y Posición inicial y
	 * @param speed Velocidad en x e y
	 */
	public Missile(float x, float y, float speed) {
		super(x, y, speed);
		
		animation = ResourceManager.getAnimation("missile");
		acceleration = 0;
		setRect(new Rectangle(x, y, Constants.MISSILE_WIDTH, Constants.MISSILE_HEIGHT));
	}

	@Override
	public void draw(SpriteBatch batch) {
		
		if (currentFrame != null)
			batch.draw(currentFrame, getX(), getY());
	}

	@Override
	public void update(float dt) {
		
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = animation.getKeyFrame(stateTime, true);
		
		setX(getX() + getSpeed() * dt);
		setY(getY() - getSpeed() * acceleration * dt);
		
		setRectX(getX());
		setRectY(getY());
		
		if (acceleration < 5f)
			acceleration += 0.05f;
	}
}
