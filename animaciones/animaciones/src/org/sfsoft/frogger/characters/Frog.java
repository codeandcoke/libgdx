package org.sfsoft.frogger.characters;

import com.badlogic.gdx.graphics.g2d.*;
import org.sfsoft.frogger.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Clase que representa la rana del juego
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class Frog {

	public static final float SPEED = 150f;
	
	Vector2 position;
	
	Animation rightAnimation;
	Animation leftAnimation;
	Animation upAnimation;
	Animation downAnimation;
	TextureRegion currentFrame;
	float stateTime;
	
	int lives;
	// Estados del personaje
	public enum State {
		RIGHT, LEFT, UP, DOWN, IDLE
	}
	public State state;
	
	public Frog(float x, float y, int lives) {
		
		position = new Vector2(x, y);
		this.lives = lives;
		state = State.IDLE;
		
		// Carga las animaciones para cada dirección
		rightAnimation = new Animation(0.25f, new TextureAtlas(Gdx.files.internal("animaciones.pack")).findRegions("frog_right"));
		leftAnimation = new Animation(0.25f, new TextureAtlas(Gdx.files.internal("animaciones.pack")).findRegions("frog_left"));
		upAnimation = new Animation(0.25f, new TextureAtlas(Gdx.files.internal("animaciones.pack")).findRegions("frog_up"));
		downAnimation = new Animation(0.25f, new TextureAtlas(Gdx.files.internal("animaciones.pack")).findRegions("frog_down"));
	}
	
	// Desplaza la tabla en el eje x
	public void move(Vector2 movement) {

		movement.scl(SPEED);
		position.add(movement);
	}
	
	public void render(SpriteBatch batch) {
		
		batch.draw(currentFrame, position.x, position.y);
	}

    /**
     * Actualiza el estado del personaje
     * @param dt
     */
	public void update(float dt) {
		
		// Calcula el tiempo para cargar el frame que proceda de la animación
		stateTime += dt;
		
		// Carga el frame según su posición y el momento del juego
		switch (state) {
		case RIGHT:
			currentFrame = rightAnimation.getKeyFrame(stateTime, true);
			break;
		case LEFT:
			currentFrame = leftAnimation.getKeyFrame(stateTime, true);
			break;
		case UP:
			currentFrame = upAnimation.getKeyFrame(stateTime, true);
			break;
		case DOWN:
			currentFrame = downAnimation.getKeyFrame(stateTime, true);
			break;
		case IDLE:
			currentFrame = upAnimation.getKeyFrame(0, true);
			break;
		default:
			currentFrame = upAnimation.getKeyFrame(0, true);
		}
			
		// Comprueba los límites de la pantalla
		if (position.x <= 0)
			position.x = 0;
		
		if ((position.x + currentFrame.getRegionWidth()) >= Constants.SCREEN_WIDTH)
			position.x = Constants.SCREEN_WIDTH - currentFrame.getRegionWidth();
	}
}
