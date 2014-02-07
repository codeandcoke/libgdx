package org.sfsoft.jfighter2dx.characters;

import org.sfsoft.jfighter2dx.managers.ResourceManager;
import org.sfsoft.jfighter2dx.util.Constants;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Representa a rocas y piedras que pueden aparecer durante el juego en la pantalla
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class Stone extends Enemy {

	public Stone(float x, float y, float speed) {
		super(x, y, speed);
	
		animation = ResourceManager.getAnimation("stone");
		setRect(new Rectangle(x, y, Constants.STONE_WIDTH, Constants.STONE_HEIGHT));
	}

	@Override
	public void update(float dt) {
		
		super.update(dt);
		
		setX(getX() + getSpeed() * dt);
		setRectX(getX());
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		
		batch.draw(currentFrame, getX(), getY());
	}
}
