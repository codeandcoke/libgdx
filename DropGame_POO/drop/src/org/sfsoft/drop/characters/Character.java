package org.sfsoft.drop.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Clase base para todos los caracteres del Juego
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public abstract class Character {

	public Vector2 position;
	float speed;
	TextureRegion texture;
	public Rectangle rect;
	
	public Character(Vector2 position, float speed, 
		TextureRegion texture) {
		this.position = position;
		this.speed = speed;
		this.texture = texture;
		rect = new Rectangle(position.x, position.y,
			texture.getRegionWidth(), texture.getRegionHeight());
	}

	/**
	 * Mueve el personaje en las unidades y dirección que se indique
	 * @param movement
	 */
	public void move(Vector2 movement) {
		
		movement.scl(speed);
		position.add(movement);
		rect.x = position.x;
		rect.y = position.y;
	}

	/**
	 * Pinta el caracter en la pantalla
	 * @param batch
	 */
	public void render(SpriteBatch batch) {
	
		batch.draw(texture, position.x, position.y);
	}
	
	public abstract void update(float dt);
}
