package com.sfaci.link.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sfaci.link.managers.ResourceManager;
import com.sfaci.link.util.Constants;

/**
 * Jugador
 * @author Santiago Faci
 * @version 2014-2015
 */
public class Player extends Character {

	public static float SPEED = 50f;
	public int lives;
	
	public Player(float x, float y) {

		super(x, y, "robin");
		lives = 3;
	}

    public void fire() {

    }

    @Override
    public void die() {
        super.die();
        lives--;
    }

    public void toChicken() {

        leftAnimation = new Animation(0.25f,
                ResourceManager.getAtlas().findRegions("chicken_left"));
        rightAnimation = new Animation(0.25f,
                ResourceManager.getAtlas().findRegions("chicken_right"));
        upAnimation = new Animation(0.25f,
                ResourceManager.getAtlas().findRegions("chicken_idle_up"));
        downAnimation = new Animation(0.25f,
                ResourceManager.getAtlas().findRegions("chicken_idle_down"));

        currentFrame = leftAnimation.getKeyFrame(0);
        rect = new Rectangle(position.x, position.y,
                currentFrame.getRegionWidth() / 2,
                currentFrame.getRegionHeight() / 2);
    }
}
