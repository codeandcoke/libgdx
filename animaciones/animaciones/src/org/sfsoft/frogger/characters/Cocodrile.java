package org.sfsoft.frogger.characters;

import com.badlogic.gdx.graphics.g2d.*;
import org.sfsoft.frogger.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Clase que representa el cocodrilo
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class Cocodrile {

	public static final float SPEED = 100f;
	
	Vector2 position;
	
	Animation animation;
	TextureRegion currentFrame;
	float stateTime;
	
	public Cocodrile(float x, float y) {
		
		position = new Vector2(x, y);
		
		// Carga la animación
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("animaciones.pack"));
        animation = new Animation(0.25f, atlas.findRegions("cocodrile"));
	}
	
	// Desplaza el personaje en el eje x
	public void move(Vector2 movement) {

		movement.scl(SPEED);
		position.add(movement);
	}
	
	public void render(SpriteBatch batch) {
		
		batch.draw(currentFrame, position.x, position.y);
	}
	
	public void update(float dt) {
		
		// Calcula el tiempo para cargar el frame que proceda de la animación
		stateTime += dt;
		currentFrame = animation.getKeyFrame(stateTime, true);
		
		move(new Vector2(dt, 0));
		
		// Comprueba los límites de la pantalla
		if (position.x > Constants.SCREEN_WIDTH + currentFrame.getRegionWidth())
			position.x = 0 - currentFrame.getRegionWidth();
	}
}
