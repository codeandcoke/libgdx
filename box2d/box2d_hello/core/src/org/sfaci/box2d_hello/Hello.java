package org.sfaci.box2d_hello;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Clase ejemplo que crea un mundo Box2D y genera diferentes
 * formas para ver como se comportan y colisionan entre ellas
 * 
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class Hello extends Game {

	OrthographicCamera camera;
	SpriteBatch spriteBatch;
	BitmapFont font;
	
	Texture bucket;
	Texture droplet;
	// El primer par�metro indican la gravedad (en x e y) (1 unidad = 1 metro)
	World world;
	// El renderizador de mundos Box2D
	Box2DDebugRenderer renderer;
	
	@Override
	public void create() {
		// C�mara del juego
		camera = new OrthographicCamera();
		// Zona visible de la c�mara
		camera.viewportWidth = 480;
		camera.viewportHeight = 320;
		// Colca la c�mara en el centro de la pantalla
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
		camera.update();
		
		spriteBatch = new SpriteBatch();
		font = new BitmapFont();
		
		world = new World(new Vector2(0, -WorldGenerator.GRAVITY), true);
		renderer = new Box2DDebugRenderer();
		
		// Define un cuerpo (la tierra)
		BodyDef groundDef = new BodyDef();
		groundDef.type = BodyType.StaticBody;
		groundDef.position.set(new Vector2(0, 10));
		Body groundBody= world.createBody(groundDef);
		PolygonShape groundShape = new PolygonShape();
		groundShape.setAsBox(camera.viewportWidth * 2, 10.0f);
		groundBody.createFixture(groundShape, 0.0f);
		
		// A�ade dos cuerpos circulares al mundo
		Body circle1 = WorldGenerator.createCircleBody(world, 100, 100);
		Body circle2 = WorldGenerator.createCircleBody(world, 105, 120);
		// A�ade un cuerpo con forma de caja al mundo
		Body box = WorldGenerator.createBoxBody(world, 101, 140);
	}

	/*
	 * M�todo que se invoca cada vez que hay que renderizar
	 * Es el m�todo donde se actualiza tambi�n la l�gica del juego
	 * @see com.badlogic.gdx.ApplicationListener#pause()
	 */
	@Override
	public void render() {
		super.render();
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.render(world, camera.combined);
		
		/*
		 * Actualiza el mundo 60 veces por segundo (1 / 60)
		 */
		world.step(1 / 60f, 6, 2);
		
		// Pinta alg�n texto en la pantalla
		spriteBatch.begin();
		font.draw(spriteBatch, "Ejemplo Box2D", camera.viewportWidth / 2, 600);
		font.draw(spriteBatch, "Gravedad: " + WorldGenerator.GRAVITY, camera.viewportWidth / 2, 570);
		font.draw(spriteBatch, "Densidad: " + WorldGenerator.DENSITY, camera.viewportWidth / 2, 540);
		font.draw(spriteBatch, "Fricci�n: " + WorldGenerator.FRICTION, camera.viewportWidth / 2, 510);
		font.draw(spriteBatch, "Elasticidad: " + WorldGenerator.RESTITUTION, camera.viewportWidth / 2, 480);
		spriteBatch.end();
	}
	
	/*
	 * M�todo invocado cuando se destruye la aplicaci�n
	 * Siempre va precedido de una llamada a 'pause()'
	 * @see com.badlogic.gdx.ApplicationListener#dispose()
	 */
	@Override
	public void dispose() {
		spriteBatch.dispose();
		font.dispose();
	}
	
	
}
