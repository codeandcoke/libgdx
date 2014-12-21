package org.sfsoft.drop.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Items del juego
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public abstract class Item extends Character {

	public int score;
	
	public Item(Vector2 position, float speed, 
		TextureRegion texture, int score) {
		super(position, speed, texture);
		
		this.score = score;
	}
}
