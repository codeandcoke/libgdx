package org.sfsoft.drop.characters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.sfsoft.drop.util.Constants;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Clase del personaje, el cubo que recoge las gotas
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class Player extends Character {

	public Player(Vector2 position, float speed, TextureRegion texture) {
		super(position, speed, texture);
	}
	
	public void moveTo(Vector2 position) {
		this.position = position;
		rect.x = position.x;
		rect.y = position.y;
	}

	@Override
	public void update(float dt) {

		// Comprueba que no se salga de los bordes de la pantalla
		// a izquierda y derecha
		if (position.x <= 0)
			position.x = 0;
		
		if ((position.x + texture.getRegionWidth() >= Constants.SCREEN_WIDTH))
			position.x = Constants.SCREEN_WIDTH - texture.getRegionWidth();
	}
}
