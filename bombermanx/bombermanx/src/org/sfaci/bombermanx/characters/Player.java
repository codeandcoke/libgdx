package org.sfaci.bombermanx.characters;

import org.sfaci.bombermanx.managers.ResourceManager;
import org.sfaci.bombermanx.managers.SpriteManager;
import org.sfaci.bombermanx.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

/**
 * Clase que representa al jugador
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class Player extends Character {

	private final int FRAMES = 3;
	// Tasa de colocación de bombas (1000 = 1 por segundo)
	public final float BOMB_RATE = 0.2f;
	
	public enum State {
		RIGHT, UP, DOWN, LEFT, IDLE, EXPLODE;
	}
	// Vida del personaje
	int lives;
	// Indica cuantas bombas hay puestas y el máximo de bombas que puede haber puestas al mismo tiempo
	public int activeBombs, bombsLimit;
	float timeBeetwenBombs;
	public int bombLength, bombStrength;
	public float speed = 50;
	public State state;
	
	Animation rightAnimation;
	Animation leftAnimation;
	Animation upAnimation;
	Animation downAnimation;
	Animation explosionAnimation;
	float stateTime;
	SpriteManager spriteManager;
	
	/**
	 * 
	 * @param texture
	 * @param x
	 * @param y
	 * @param lives
	 * @param spriteManager
	 */
	public Player(Texture texture, float x, float y, int lives, SpriteManager spriteManager) {
		super(texture, x, y);
		this.spriteManager = spriteManager;
		this.lives = lives;
		activeBombs = 0;
		bombsLimit = 1;
		bombLength = 1;
		bombStrength = 1;
		state = State.IDLE;
		
		createAnimations();
	}
	
	/**
	 * Crea las animaciones del personaje
	 */
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
		
		spriteSheet = ResourceManager.getTexture("player_explosion_animation");
		frames = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / (FRAMES * 2), spriteSheet.getHeight());
		rightFrames = new TextureRegion[FRAMES * 2];
		for (int i = 0; i < FRAMES * 2; i++) {
			rightFrames[i] = frames[0][i];
		}
		explosionAnimation = new Animation(0.2f, rightFrames);
	}
	
	/**
	 * Mueve al jugador
	 * @param movement Movimiento en x e y para el jugador
	 */
	public void move(Vector2 movement) {
				
		if (state == State.EXPLODE)
			return;
		
		movement.scl(speed);
		position.add(movement);
	}
	
	/**
	 * Hace explotar al jugador
	 * Ha sido alcanzado por un enemigo o una de sus explosiones
	 */
	public void explode() {
		state = State.EXPLODE;
		stateTime = 0;
	}
	
	/**
	 * Coloca una bomba en la posición actual
	 */
	public void putBomb() {
		if (activeBombs < bombsLimit)
			if (timeBeetwenBombs >= BOMB_RATE) {
				
				// Recalcula la posición para la que la bomba se alinee con los ladrillos
				int x = Math.round(position.x / 16) * 16;
				int y = Math.round(position.y / 16) * 16;
				
				final Bomb bomb = new Bomb(ResourceManager.getTexture("bomb_idle"), x, y, 
					bombLength, bombStrength, spriteManager);
				spriteManager.bombs.add(bomb);
				timeBeetwenBombs = 0;
				activeBombs++;
				
				// Marca la bomba para explotar en 2 segundos y desaparecer en 3
				Timer.schedule(new Task() {
					public void run() {
						bomb.explode();
						activeBombs--;
					}
				}, 2);
				Timer.schedule(new Task() {
					public void run() {
						bomb.die();
					}
				}, 2.5f);
			}
	}
	
	@Override
	public void update(float dt) {
		
		super.update(dt);
		
		timeBeetwenBombs += dt;
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
		case EXPLODE:
			currentFrame = explosionAnimation.getKeyFrame(stateTime, false);
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
