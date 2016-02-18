package com.sfaci.link.characters;

/**
 * Created by dam on 16/02/16.
 */
public class GreenEnemy extends Enemy {

    private int speed;

    public GreenEnemy(float x, float y, String name, int speed) {

        super(x, y, name);
        this.speed = speed;
    }

    public void fire() {

    }

    @Override
    public void update(float dt) {
        super.update(dt);

        position.x -= speed*dt;
    }
}
