package org.sfsoft.drop.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Piedras que caen del cielo
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class Stone extends Enemy {

	public Stone(Vector2 position, float speed, 
		TextureRegion texture) {
		super(position, speed, texture);
	}
	
	@Override
	public void update(float dt) {
		
		move(new Vector2(0, -dt));
	}
}