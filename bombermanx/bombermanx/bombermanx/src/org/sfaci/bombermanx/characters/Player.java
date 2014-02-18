package org.sfaci.bombermanx.characters;

import org.sfaci.bombermanx.managers.ResourceManager;
import org.sfaci.bombermanx.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Clase que representa al jugador
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class Player extends Character {

	public final float SPEED = 50f;
	private final int FRAMES = 3;
	public enum State {
		RIGHT, UP, DOWN, LEFT, IDLE;
	}
	
	int lives;
	public State state;
	
	Animation rightAnimation;
	Animation leftAnimation;
	Animation upAnimation;
	Animation downAnimation;
	float stateTime;
	
	public Player(Texture texture, float x, float y, int lives) {
		super(texture, x, y);
		this.lives = lives;
		state = State.IDLE;
		
		createAnimations();
	}
	
	private void createAnimations() {
		
		// Carga la animación de un spritesheet (todos los frames están en un mismo fichero)
		Texture spriteSheet = ResourceManager.getTexture("player_right_animation");
		TextureRegion[][] frames = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / FRAMES, spriteSheet.getHeight());
		TextureRegion[] rightFrames = new TextureRegion[FRAMES];
		for (int i = 0; i < FRAMES; i++) {
			rightFrames[i] = frames[0][i];
		}
		rightAnimation = new Animation(0.15f, rightFrames);
		
		spriteSheet = ResourceManager.getTexture("player_left_animation");
		frames = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / FRAMES, spriteSheet.getHeight());
		rightFrames = new TextureRegion[FRAMES];
		for (int i = 0; i < FRAMES; i++) {
			rightFrames[i] = frames[0][i];
		}
		leftAnimation = new Animation(0.15f, rightFrames);
		
		spriteSheet = ResourceManager.getTexture("player_up_animation");
		frames = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / FRAMES, spriteSheet.getHeight());
		rightFrames = new TextureRegion[FRAMES];
		for (int i = 0; i < FRAMES; i++) {
			rightFrames[i] = frames[0][i];
		}
		upAnimation = new Animation(0.15f, rightFrames);
		
		spriteSheet = ResourceManager.getTexture("player_down_animation");
		frames = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / FRAMES, spriteSheet.getHeight());
		rightFrames = new TextureRegion[FRAMES];
		for (int i = 0; i < FRAMES; i++) {
			rightFrames[i] = frames[0][i];
		}
		downAnimation = new Animation(0.15f, rightFrames);
	}
	
	// Desplaza la tabla en el eje x
	public void move(Vector2 movement) {
				
		movement.scl(SPEED);
		position.add(movement);
	}
	
	@Override
	public void update(float dt) {
		
		super.update(dt);
		
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
			currentFrame = downAnimation.getKeyFrame(0, true);
			break;
		default:
			currentFrame = downAnimation.getKeyFrame(0, true);
		}
		
		// Comprueba los límites de la pantalla
		if (position.x <= 0)
			position.x = 0;
		
		if ((position.x + Constants.PLAYER_WIDTH) >= Constants.SCREEN_WIDTH)
			position.x = Constants.SCREEN_WIDTH - Constants.PLAYER_WIDTH;
	}
}
