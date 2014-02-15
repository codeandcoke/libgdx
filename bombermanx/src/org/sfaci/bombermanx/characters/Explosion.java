package org.sfaci.bombermanx.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Explosion {

	public enum ExplosionType {
		CENTER, UP, DOWN, LEFT, RIGHT, HORIZONTAL, VERTICAL
	}
	
	TextureRegion currentFrame;
	
	Animation animation;
	float stateTime;
	public Vector2 position;
	
	public Explosion(TextureAtlas textureAtlas, float x, float y, ExplosionType explosionType) {
		
		switch (explosionType) {
		case CENTER:
			animation = new Animation(0.2f, textureAtlas.findRegion("explosion1_center"), 
					textureAtlas.findRegion("explosion2_center"), textureAtlas.findRegion("explosion3_center"),
					textureAtlas.findRegion("explosion4_center"));
			break;
		case UP:
			animation = new Animation(0.2f, textureAtlas.findRegion("explosion1_up"), 
					textureAtlas.findRegion("explosion2_up"), textureAtlas.findRegion("explosion3_up"),
					textureAtlas.findRegion("explosion4_up"));
			break;
		case DOWN:
			animation = new Animation(0.2f, textureAtlas.findRegion("explosion1_down"), 
					textureAtlas.findRegion("explosion2_down"), textureAtlas.findRegion("explosion3_down"),
					textureAtlas.findRegion("explosion4_down"));
			break;
		case LEFT:
			animation = new Animation(0.2f, textureAtlas.findRegion("explosion1_left"), 
					textureAtlas.findRegion("explosion2_left"), textureAtlas.findRegion("explosion3_left"),
					textureAtlas.findRegion("explosion4_left"));
			break;
		case RIGHT:
			animation = new Animation(0.2f, textureAtlas.findRegion("explosion1_right"), 
					textureAtlas.findRegion("explosion2_right"), textureAtlas.findRegion("explosion3_right"),
					textureAtlas.findRegion("explosion4_right"));
			break;
		case HORIZONTAL:
			animation = new Animation(0.2f, textureAtlas.findRegion("explosion1_horizontal"), 
					textureAtlas.findRegion("explosion2_horizontal"), textureAtlas.findRegion("explosion3_horizontal"),
					textureAtlas.findRegion("explosion4_horizontal"));
			break;
		case VERTICAL:
			animation = new Animation(0.2f, textureAtlas.findRegion("explosion1_vertical"), 
					textureAtlas.findRegion("explosion2_vertical"), textureAtlas.findRegion("explosion3_vertical"),
					textureAtlas.findRegion("explosion4_vertical"));
			break;
		default:
		}
		
		currentFrame = animation.getKeyFrame(0);
		position = new Vector2(x, y);
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(currentFrame, position.x, position.y);
	}
	
	public void update(float dt) {
		
		stateTime += dt;
		currentFrame = animation.getKeyFrame(stateTime, false);
	}
}
