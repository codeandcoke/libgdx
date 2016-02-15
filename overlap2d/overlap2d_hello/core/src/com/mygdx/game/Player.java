package com.mygdx.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.physics.PhysicsBodyLoader;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Created by Santi on 14/02/16.
 */
public class Player implements IScript {

    private final float GRAVITY = 9.8f;
    private final float HORIZONTAL_SPEED = 150f;
    private final float JUMP_SPEED = -300f;

    private Entity player;
    private TransformComponent transformComponent;
    private DimensionsComponent dimensionsComponent;
    private Vector2 speed;
    private boolean isJumping;

    private World world;

    enum Direction {
        RIGHT, LEFT;
    }
    private Direction direction;

    public Player(World world) {
        this.world = world;
        speed = new Vector2(HORIZONTAL_SPEED, 0);
    }

    @Override
    public void init(Entity entity) {

        player = entity;
        transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
    }

    @Override
    public void act(float dt) {

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            transformComponent.x += speed.x * dt;
            direction = Direction.RIGHT;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            transformComponent.x -= speed.x * dt;
            direction = Direction.LEFT;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (!isJumping) {
                isJumping = true;
                speed.y = JUMP_SPEED;
            }
        }

        if (isJumping) {
            if (direction == Direction.RIGHT)
                transformComponent.rotation -= 90f * dt;
            else
                transformComponent.rotation += 90f * dt;
        }

        speed.y += GRAVITY;
        transformComponent.y -= speed.y * dt;

        rayCast(dt);
    }

    private void rayCast(float dt) {

        float rayGap = dimensionsComponent.height / 2;
        float raySize = -(speed.y) * dt;
        if (raySize < 5f)
            raySize = 5f;

        // Sólo comprobamos colisiones cuando esté cayendo (en este caso)
        if (speed.y < 0)
            return;

        Vector2 rayFrom = new Vector2((transformComponent.x + dimensionsComponent.width / 2) * PhysicsBodyLoader.getScale(),
                (transformComponent.y + rayGap) * PhysicsBodyLoader.getScale());
        Vector2 rayTo = new Vector2((transformComponent.x + dimensionsComponent.width / 2) * PhysicsBodyLoader.getScale(),
                (transformComponent.y - raySize) * PhysicsBodyLoader.getScale());

        world.rayCast(new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                // Detener al jugador
                speed.y = 0;
                // Coloca al jugador un poco encima de donde ha chocado
                transformComponent.y = point.y / PhysicsBodyLoader.getScale() + 0.1f;

                isJumping = false;
                transformComponent.rotation = 0f;

                return 0;
            }
        }, rayFrom, rayTo);

    }

    @Override
    public void dispose() {

    }

    public float getX() {
        return transformComponent.x;
    }

    public float getY() {
        return transformComponent.y;
    }

    public float getWidth() {
        return dimensionsComponent.width;
    }
}
