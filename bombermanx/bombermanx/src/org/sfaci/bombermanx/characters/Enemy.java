package org.sfaci.bombermanx.characters;

import org.sfaci.bombermanx.characters.Player.State;
import org.sfaci.bombermanx.managers.ResourceManager;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Enemy extends Character {

	public enum Direction {
		VERTICAL, HORIZONTAL
	}
	Direction direction;
	boolean exploding;
	boolean dead;
	String name;
	
	public static final int FRAMES = 6;
	Animation animation;
	float stateTime;
	public float speed = 30f;
	
	public Enemy(float x, float y, String name, Direction direction) {
		super(x, y);
		
		this.name = name;
		animation = new Animation(0.15f, 
			ResourceManager.getTextureAtlas("enemies").findRegion(name + "1"),
			ResourceManager.getTextureAtlas("enemies").findRegion(name + "2"),
			ResourceManager.getTextureAtlas("enemies").findRegion(name + "3"),
			ResourceManager.getTextureAtlas("enemies").findRegion(name + "4"),
			ResourceManager.getTextureAtlas("enemies").findRegion(name + "5"),
			ResourceManager.getTextureAtlas("enemies").findRegion(name + "6"));
		
		currentFrame = animation.getKeyFrame(0);
		rect.width = currentFrame.getRegionWidth();
		rect.height = currentFrame.getRegionHeight();
		this.direction = direction;
	}

	public void move(Vector2 movement) {
		
		movement.scl(speed);
		position.add(movement);
	}
	
	public void explode() {
		exploding = true;
		speed = 0f;
		
		// En 1.5 segundos desaparecerá de la pantalla
		Timer.schedule(new Task() {
			public void run() {
				die();
			}
		}, 1.5f);
	}
	
	public void die() {
		dead = true;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	@Override
	public void update(float dt) {
		
		super.update(dt);
		
		stateTime += dt;
		if (!exploding)
			currentFrame = animation.getKeyFrame(stateTime, true);
		else
			currentFrame = ResourceManager.getTextureAtlas("enemies").findRegion(name + "_dead");
		
		switch (direction) {
		case VERTICAL:
			move(new Vector2(0, -dt));
			break;
		case HORIZONTAL:
			move(new Vector2(dt, 0));
			break;
		}
	}
}
