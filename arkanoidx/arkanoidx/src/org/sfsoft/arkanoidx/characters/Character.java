package org.sfsoft.arkanoidx.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Clase base para todos los caracteres del juego
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public abstract class Character {

	Texture texture;
	public float x;
	public float y;
	Rectangle rect;
	
	public Character(Texture texture, float x, float y) {
		this.texture = texture;
		this.x = x;
		this.y = y;
		rect = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
	}
	
	public void render(SpriteBatch batch) {
		
		batch.draw(texture, x, y);
	}
	
	public void update(float dt) {
		
		// Actualiza la posición del rectángulo
		rect.x = x;
		rect.y = y;
	};
}
