package org.sfsoft.frogger.characters;

import org.sfsoft.frogger.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Clase que representa la rana del juego
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
		animation = new Animation(0.25f, new TextureRegion[]{
				new Sprite(new Texture(Gdx.files.internal("cocodrile1.png"))), new Sprite(new Texture(Gdx.files.internal("cocodrile2.png")))});
	}
	
	// Desplaza la tabla en el eje x
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
