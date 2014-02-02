package org.sfsoft.collisions;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Clase ejemplo que comprueba cómo aplicar fuerzas a cuerpos sólidos
 * en un mundo Box2D
 * 
 * @author Santiago Faci
 * @version 2.0
 *
 */
public class MyWorld extends Game implements InputProcessor {

	private static float MAX_SPEED = 40f;
	
	OrthographicCamera camera;
	SpriteBatch spriteBatch;
	BitmapFont font;

	// El primer parámetro indican la gravedad (en x e y) (1 unidad = 1 metro)
	World world;
	// El renderizador de mundos Box2D
	Box2DDebugRenderer renderer;	
	Body car;
	
	MouseJointDef mouseJointDef;
	MouseJoint mouseJoint;
	
	@Override
	public void create() {
		// Cámara del juego
		camera = new OrthographicCamera();
		// Zona visible de la cámara
		camera.viewportWidth = 480;
		camera.viewportHeight = 320;
		// Colca la cámara en el centro de la pantalla
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
		camera.update();
		
		spriteBatch = new SpriteBatch();
		font = new BitmapFont();
		
		world = new World(new Vector2(0, -10f), true);
		renderer = new Box2DDebugRenderer();
		
		// Define un cuerpo (la tierra)
		BodyDef groundDef = new BodyDef();
		groundDef.type = BodyType.StaticBody;
		groundDef.position.set(new Vector2(0, 10));
		Body groundBody= world.createBody(groundDef);

		PolygonShape groundShape = new PolygonShape();
		groundShape.setAsBox(camera.viewportWidth * 2, 10.0f);
		groundBody.createFixture(groundShape, 0.0f);
		
		// Añade un coche al mundo con una velocidad inicial
		car = WorldGenerator.createCar(world, 280, 60);
		car.setLinearVelocity(new Vector2(10f, 0));
		
		Body circle = WorldGenerator.createCircleBody(world, 200, 200);
		
		// Añade una rampa
		Body floor = WorldGenerator.createPolygonBody(world, 250, 70, 100, -50);
		
		mouseJointDef = new MouseJointDef();
		mouseJointDef.bodyA = groundBody;
		mouseJointDef.collideConnected = true;
		mouseJointDef.maxForce = 500000;
		
		Gdx.input.setInputProcessor(this);
	}

	/*
	 * Método que se invoca cada vez que hay que renderizar
	 * Es el método donde se actualiza también la lógica del juego
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
		
		// Pinta algún texto en la pantalla
		spriteBatch.begin();
		font.draw(spriteBatch, "Ejemplo de Cuerpos Compuestos y Joints Box2D", camera.viewportWidth / 2, 600);
		font.draw(spriteBatch, "Pincha y arrastra cualquier objeto", camera.viewportWidth / 2, 570);
		font.draw(spriteBatch, "Velocidad: " + car.getLinearVelocity().x, camera.viewportWidth / 2, 540);
		spriteBatch.end();
		
		handleInput();
	}
	
	private void handleInput() {
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			if (car.getLinearVelocity().x > -MAX_SPEED)
				car.applyLinearImpulse(new Vector2(-4000f, 0), car.getPosition(), true);
			
			Array<Fixture> fixtures = car.getFixtureList();
			for (Fixture fixture : fixtures) {
				
			}
		}
		
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			if (car.getLinearVelocity().x < MAX_SPEED)
				car.applyLinearImpulse(new Vector2(4000f, 0), car.getPosition(), true);
		}
	}
	
	/*
	 * Método invocado cuando se destruye la aplicación
	 * Siempre va precedido de una llamada a 'pause()'
	 * @see com.badlogic.gdx.ApplicationListener#dispose()
	 */
	@Override
	public void dispose() {
		spriteBatch.dispose();
		font.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private Vector3 aux = new Vector3();
	private Vector2 aux2 = new Vector2();
	private QueryCallback query = new QueryCallback() {
		
		@Override
		public boolean reportFixture(Fixture fixture) {
			if (!fixture.testPoint(aux.x, aux.y))
				return true;
				
			mouseJointDef.bodyB = fixture.getBody();
			mouseJointDef.target.set(aux.x, aux.y);
			mouseJoint = (MouseJoint) world.createJoint(mouseJointDef);
			return false;
		}
	};

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		camera.unproject(aux.set(screenX, screenY, 0));
		world.QueryAABB(query, aux.x, aux.y, aux.x, aux.y);
		
		System.out.println("down");
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (mouseJoint == null)
		return false;
		
		world.destroyJoint(mouseJoint);
		mouseJoint = null;
		
		System.out.println("up");
		
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (mouseJoint == null)
			return false;
		
		camera.unproject(aux.set(screenX, screenY, 0));
		mouseJoint.setTarget(aux2.set(aux.x, aux.y));
		
		System.out.println("dragged");
		
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
