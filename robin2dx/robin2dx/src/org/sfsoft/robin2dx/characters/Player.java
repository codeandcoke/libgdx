package org.sfsoft.robin2dx.characters;

import org.sfsoft.robin2dx.managers.ResourceManager;
import org.sfsoft.robin2dx.utils.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {

	public static float SPEED = 50f;
	public enum State {
		RIGHT, LEFT, UP, DOWN, IDLE;
	}
	public State state;
	
	float stateTime;
	Animation rightAnimation;
	Animation leftAnimation;
	Animation upAnimation;
	Animation downAnimation;
	TextureRegion currentFrame;
	TextureRegion idleTexture;
	public Rectangle rect;
	
	public Vector2 position = new Vector2();
	public Vector2 velocity = new Vector2();
	
	public Player(int x, int y) {
		
		leftAnimation = ResourceManager.getAnimation("robin_left");
		rightAnimation = ResourceManager.getAnimation("robin_right");
		upAnimation = ResourceManager.getAnimation("robin_up");
		downAnimation = ResourceManager.getAnimation("robin_down");
		
		idleTexture = ResourceManager.getAtlas("robin").findRegion("robin_right1");
		
		position.x = x;
		position.y = y;
		
		rect = new Rectangle(x, y, idleTexture.getRegionWidth(), idleTexture.getRegionHeight());
	}
	
	public void update(float dt) {

		stateTime += dt;
		
		switch (state) {
		case LEFT:
			currentFrame = leftAnimation.getKeyFrame(stateTime, true);
			break;
		case RIGHT:
			currentFrame = rightAnimation.getKeyFrame(stateTime, true);
			break;
		case UP:
			currentFrame = upAnimation.getKeyFrame(stateTime, true);
			break;
		case DOWN:
			currentFrame = downAnimation.getKeyFrame(stateTime, true);
			break;
		case IDLE:
			currentFrame = idleTexture;
		}
		
		velocity.scl(dt);
		position.add(velocity);
		
		rect.x = position.x;
		rect.y = position.y;
	}
	
	public void render(SpriteBatch batch) {
		
		batch.draw(currentFrame, position.x, position.y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
	}
}
