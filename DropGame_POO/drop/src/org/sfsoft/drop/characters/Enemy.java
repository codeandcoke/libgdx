package org.sfsoft.drop.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Clase base para todos los enemigos del Juego
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public abstract class Enemy extends Character {

	public Enemy(Vector2 position, float speed,
		TextureRegion texture) {
		super(position, speed, texture);
	}
}