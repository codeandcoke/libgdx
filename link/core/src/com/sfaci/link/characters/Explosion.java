package com.sfaci.link.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.sfaci.link.managers.ResourceManager;

public class Explosion {

    Vector2 position;
    private Animation animation;
    private float stateTime;
    TextureRegion currentFrame;
    boolean dead;

    public Explosion(float x, float y, String name) {

        position = new Vector2(x, y);
        animation = new Animation(0.15f,
                ResourceManager.getAtlas().findRegions(name));
    }

    public void update(float dt) {

        stateTime += dt;
        currentFrame = animation.getKeyFrame(stateTime);
        if (animation.isAnimationFinished(stateTime))
            die();
    }

    public void render(Batch batch) {
        batch.draw(currentFrame, position.x, position.y);
    }

    public void die() {
        dead = true;
    }

    public boolean isDead() {
        return dead;
    }
}