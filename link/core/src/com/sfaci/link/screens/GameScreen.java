package com.sfaci.link.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.sfaci.link.Link;


/**
 * Pantalla de Juego, donde el usuario juega la partida
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class GameScreen implements Screen {

	final Link game;
	
	public GameScreen(Link game) {

		this.game = game;
	}
	
	@Override
	public void show() {
	}
	
	@Override
	public void render(float dt) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.spriteManager.update(dt);
		game.spriteManager.render();
	}
	
	@Override
	public void hide() {
		
	}
	
	@Override
	public void dispose() {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
