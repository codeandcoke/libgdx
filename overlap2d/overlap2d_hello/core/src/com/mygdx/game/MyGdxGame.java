package com.mygdx.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

public class MyGdxGame extends Game {

	private SceneLoader sceneLoader;
	private Viewport viewport;
	private Player player;
	private OrthographicCamera camera;

	private ParticleEffect fire;
	private SpriteBatch batch;

	Box2DDebugRenderer box2DDebugRenderer;

	public MyGdxGame() {
	}

	@Override
	public void create () {
		batch = new SpriteBatch();

		viewport = new FitViewport(800, 600);
		camera = (OrthographicCamera) viewport.getCamera();
		sceneLoader = new SceneLoader();
		sceneLoader.loadScene("MainScene", viewport);

		player = new Player(sceneLoader.world);

		ItemWrapper root = new ItemWrapper(sceneLoader.getRoot());
		root.getChild("box").addScript(player);

		// Añade un efecto de partícula (fuego)
		fire = new ParticleEffect();
		fire.load(Gdx.files.internal("particles/fire2"), Gdx.files.internal(""));
		fire.getEmitters().first().setPosition(100, 100);
		fire.setDuration(100);
		fire.start();

		WorldGenerator.createBoxBody(sceneLoader.world, 100, 400);
		box2DDebugRenderer = new Box2DDebugRenderer();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.position.set(player.getX(), player.getY(), 0);

		sceneLoader.world.step(1 / 60f, 6, 2);
		sceneLoader.getEngine().update(Gdx.graphics.getDeltaTime());

		fire.update(Gdx.graphics.getDeltaTime());
		batch.begin();
		fire.draw(batch);
		batch.end();

		box2DDebugRenderer.render(sceneLoader.world, camera.combined);
	}
}
