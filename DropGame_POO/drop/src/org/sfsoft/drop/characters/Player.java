package org.sfsoft.drop.characters;

import org.sfsoft.drop.util.Constants;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Player extends Character {

	public Player(Vector2 position, float speed, 
		Texture texture) {
		super(position, speed, texture);
	}
	
	public void moveTo(Vector2 position) {
		this.position = position;
		rect.x = position.x;
		rect.y = position.y;
	}

	@Override
	public void update(float dt) {
		
		if (position.x <= 0)
			position.x = 0;
		
		if ((position.x + texture.getWidth() >= Constants.SCREEN_WIDTH))
			position.x = Constants.SCREEN_WIDTH - texture.getWidth();
	}
}
