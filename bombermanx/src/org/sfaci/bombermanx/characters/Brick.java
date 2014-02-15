package org.sfaci.bombermanx.characters;

import org.sfaci.bombermanx.managers.ResourceManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Clase que representa a cada uno de los ladrillos del juego
 * @author Santiago Faci
 * @version
 *
 */
public class Brick extends Character {

	// Tipo de ladrillo
	public enum BrickType {
		BRICK, STONE, POWERUP_BOMB_LENGTH, POWERUP_BOMB, DOOR
	}
	
	Animation animation;
	float stateTime;
	
	public BrickType type;
	int lives;
	int value;
	boolean dead;
	
	/**
	 * 
	 * @param texture
	 * @param x posición x
	 * @param y posición y
	 * @param type Tipo de ladrillo
	 * @param lives Duración del ladrillo
	 * @param value Puntuación por romper el ladrillo
	 */
	public Brick(Texture texture, float x, float y, BrickType type, int lives, int value) {
		
		super(texture, x, y);
		this.type = type;
		this.lives = lives;
		this.value = value;
	}
	
	/**
	 * Hace explotar al ladrillo
	 */
	public void explode() {
		
		int nFrames = 6;
		
		Texture spriteSheet = ResourceManager.getTexture("broken_brick_animation");
		TextureRegion[][] frames = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / nFrames, spriteSheet.getHeight());
		TextureRegion[] rightFrames = new TextureRegion[nFrames];
		for (int i = 0; i < nFrames; i++) {
			rightFrames[i] = frames[0][i];
		}
		animation = new Animation(0.15f, rightFrames);
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	@Override
	public void update(float dt) {
		
		super.update(dt);
		
		// Si la animación está creada, se animan los ladrillos rompiéndose
		if (animation != null) {
			stateTime += dt;
			currentFrame = animation.getKeyFrame(stateTime, false);
			
			// Cuando la animación termina se anota el ladrillo como terminado
			if (animation.isAnimationFinished(stateTime))
				dead = true;
		}
	}
}
