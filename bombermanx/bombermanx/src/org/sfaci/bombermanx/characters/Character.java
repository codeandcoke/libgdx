package org.sfaci.bombermanx.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Clase base para todos los caracteres del juego
 * @author Santiago Faci
 * @version 1.0
 *
 */
public abstract class Character {

	protected TextureRegion currentFrame;
	public Vector2 position;
	public Rectangle rect;
	
	public Character(Texture texture, float x, float y) {
		currentFrame = new TextureRegion(texture);
		position = new Vector2(x, y);
		rect = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
	}
	
	public void render(SpriteBatch batch) {
		
		batch.draw(currentFrame, position.x, position.y);
	}
	
	public void update(float dt) {
		
		// Actualiza la posición del rectángulo
		rect.x = position.x;
		rect.y = position.y;
	};
}
