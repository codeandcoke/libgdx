package com.sfaci.link;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sfaci.link.managers.ResourceManager;
import com.sfaci.link.managers.SpriteManager;
import com.sfaci.link.screens.MainMenuScreen;

public class Link extends Game {
	public Batch spriteBatch;
	public BitmapFont font;
	public boolean paused;

	public SpriteManager spriteManager;
	
	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		font = new BitmapFont();

		setScreen(new MainMenuScreen(this));

		ResourceManager.loadAllResources();
		spriteManager = new SpriteManager(this);
	}

	@Override
	public void render () {
		super.render();
	}
}
