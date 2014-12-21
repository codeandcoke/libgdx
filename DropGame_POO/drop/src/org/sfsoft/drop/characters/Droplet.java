package org.sfsoft.drop.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Las gotas que caen del cielo
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class Droplet extends Item {

	public Droplet(Vector2 position, float speed, 
		TextureRegion texture, int score) {
		super(position, speed, texture, score);
	}

	@Override
	public void update(float dt) {
		
		move(new Vector2(0, -dt));
	}
}
