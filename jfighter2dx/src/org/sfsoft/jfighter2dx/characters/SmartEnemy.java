package org.sfsoft.jfighter2dx.characters;

import org.sfsoft.jfighter2dx.managers.ResourceManager;
import org.sfsoft.jfighter2dx.util.Constants;

import com.badlogic.gdx.math.Rectangle;

public class SmartEnemy extends Enemy {

	float arc;
	float time;
	float y0;
	
	public SmartEnemy(float x, float y, float speed) {
		super(x, y, speed);
		
		animation = ResourceManager.getAnimation("pursuer_enemy");
		setRect(new Rectangle(x, y, Constants.ENEMY_WIDTH, Constants.ENEMY_HEIGHT));
		setValue(3);
		setLives(1);
		
		y0 = y;
		arc = 250;
		time = 0;
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
		
		time += dt * 100;
		// Describe un movimiento senoidal con centro en y0 y apertura según la variable arc
		setY(arc * (float) Math.sin(time * 2f * Math.PI / 900) + y0);
		setX(getX() + dt * speed);
		
		setRectY(getY());
		setRectX(getX());
	}
}
