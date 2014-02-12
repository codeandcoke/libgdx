package org.sfsoft.drop.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public abstract class Enemy extends Character {

	public Enemy(Vector2 position, float speed,
		Texture texture) {
		super(position, speed, texture);
	}
}
