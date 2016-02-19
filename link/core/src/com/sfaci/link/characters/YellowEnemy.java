package com.sfaci.link.characters;

/**
 * Created by dam on 16/02/16.
 */
public class YellowEnemy extends Enemy {

    private int speed;
    private int offset;
    private float initialPosition;
    private boolean derecha;

    public YellowEnemy(float x, float y, String name,
                       int speed, int offset) {

        super(x, y, name);
        this.speed = speed;
        this.offset = offset;
        initialPosition = x;
        derecha = true;
    }

    public void jump() {

    }

    @Override
    public void update(float dt) {
        super.update(dt);

        if (derecha) {
            position.x += speed * dt;
            if (position.x >= initialPosition + offset)
                derecha = false;
        }
        else {
            position.x -= speed * dt;
            if (position.x <= initialPosition)
                derecha = true;
        }
    }
}
