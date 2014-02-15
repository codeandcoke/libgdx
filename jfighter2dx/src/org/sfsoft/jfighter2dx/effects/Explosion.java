package org.sfsoft.jfighter2dx.effects;

import org.sfsoft.jfighter2dx.managers.ResourceManager;
import org.sfsoft.jfighter2dx.util.Constants;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

/**
 * Representa la explosión de cualquier elemento del juego
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class Explosion implements Disposable {

	private final float DURATION = 3f;
	
	private Animation animation;
	private TextureRegion currentFrame;
	private float stateTime;
	
	private float timeToLive;
	private float life;
	private boolean mustDie;
	
	private float x;
	private float y;
	
	public Explosion(float x, float y) {
		
		this.x = x;
		this.y = y;
		timeToLive = DURATION;
		life = 0;
		
		animation = ResourceManager.getAnimation("explosion");
	}
	
	/**
	 * Indica que la explosión debe desaparecer
	 * @return
	 */
	public boolean mustDie() {
		return mustDie;
	}
	
	public void draw(SpriteBatch batch) {
		
		if ((currentFrame != null) && (!mustDie))
			batch.draw(currentFrame, x, y, Constants.ENEMY_WIDTH * 2, Constants.ENEMY_HEIGHT * 2);
	}
	
	public void update(float dt) {
		
		stateTime += dt;
		currentFrame = animation.getKeyFrame(stateTime);
		
		life += dt;
		if (life >= timeToLive) 
			mustDie = true;
	}

	@Override
	public void dispose() {
		
		animation = null;
		currentFrame = null;
	}
}
