package com.sfaci.link.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sfaci.link.managers.ResourceManager;
import com.sfaci.link.util.Constants;

/**
 * Created by dam on 16/02/16.
 */
public abstract class Character {

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
    public Rectangle rect;

    public Vector2 position = new Vector2();
    public Vector2 velocity = new Vector2();

    public Character(float x, float y, String name) {

        leftAnimation = new Animation(0.25f, ResourceManager.getAtlas().findRegions(name + "_left"));
        rightAnimation = new Animation(0.25f, ResourceManager.getAtlas().findRegions(name + "_right"));
        upAnimation = new Animation(0.25f, ResourceManager.getAtlas().findRegions(name + "_up"));
        downAnimation = new Animation(0.25f, ResourceManager.getAtlas().findRegions(name + "_down"));

        currentFrame = leftAnimation.getKeyFrame(0);

        position.x = x;
        position.y = y;

        rect = new Rectangle(x, y,
                currentFrame.getRegionWidth() / 2,
                currentFrame.getRegionHeight() / 2);

        state = State.IDLE;
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
                currentFrame = leftAnimation.getKeyFrame(stateTime, true);
        }

        velocity.scl(dt);
        position.add(velocity);

        rect.x = position.x;
        rect.y = position.y;
    }

    public void render(Batch batch) {

        batch.draw(currentFrame, position.x, position.y,
                rect.getWidth(), rect.getHeight());
    }
}
