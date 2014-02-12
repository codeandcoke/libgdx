package org.sfsoft.drop.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Character {

	public Vector2 position;
	float speed;
	Texture texture;
	public Rectangle rect;
	
	public Character(Vector2 position, float speed, 
		Texture texture) {
		this.position = position;
		this.speed = speed;
		this.texture = texture;
		rect = new Rectangle(position.x, position.y,
			texture.getWidth(), texture.getHeight());
	}
	
	public void move(Vector2 movement) {
		
		movement.scl(speed);
		position.add(movement);
		rect.x = position.x;
		rect.y = position.y;
	}

	public void render(SpriteBatch batch) {
	
		batch.draw(texture, position.x, position.y);
	}
	
	public abstract void update(float dt);
}
