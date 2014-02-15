package org.sfsoft.jfighter2dx.characters;

import org.sfsoft.jfighter2dx.characters.ShooterBullet.BulletDirection;
import org.sfsoft.jfighter2dx.util.Constants;

import com.badlogic.gdx.Gdx;

/**
 * Tipo de enemigo que permanace en la parte derecha de la pantalla, sube y baja y dispara hacia la izquierda
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class StaticShooterEnemy extends ShooterEnemy {

	private float arc;
	private float time;
	private float y0;
	
	/**
	 * 
	 * @param x Posición inicial x
	 * @param y Posición inicial y
	 * @param speed Velocidad
	 * @param bulletDirection Dirección hacia donde dispara
	 */
	public StaticShooterEnemy(float x, float y, float speed, BulletDirection bulletDirection) {
		super(x, y, speed, bulletDirection);
		
		arc = Constants.SCREEN_HEIGHT / 2 - 50;
		time = 0;
		y0 =  Constants.SCREEN_HEIGHT / 2;
	}
	
	@Override
	public void update(float dt) {
		
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = animation.getKeyFrame(stateTime, true);
		
		time += dt * 100;
		
		// Describe un movimiento senoidal con centro en y0 y apertura según la variable arc
		setY(arc * (float) Math.sin(time * 2f * Math.PI / 900) + y0);
		
		setRectY(getY());
	}
}
