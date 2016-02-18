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
	
	public Player(float x, float y) {
		super(x, y, "robin");
	}

    public void fire() {

    }
}
