package org.sfsoft.jfighter2dx.powerups;

import org.sfsoft.jfighter2dx.managers.ResourceManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Las bombas son un powerup que permite eliminar a todos los enemigos de la pantalla 
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class Bomb extends Powerup {
	
	private Texture texture;
	
	public Bomb(float x, float y, float speed) {
		
		super(x, y, speed);
		
		texture = ResourceManager.getTexture("bomb");
		setRect(new Rectangle(x, y, texture.getWidth(), texture.getHeight()));
	}

	@Override
	public void draw(SpriteBatch batch) {

		batch.draw(texture, getX(), getY());
	}
}
