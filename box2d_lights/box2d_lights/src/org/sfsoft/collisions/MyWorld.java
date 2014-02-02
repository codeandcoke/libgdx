package org.sfsoft.collisions;

import box2dLight.ConeLight;
import box2dLight.DirectionalLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
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
public class MyWorld extends Game {

	OrthographicCamera camera;
	SpriteBatch spriteBatch;
	BitmapFont font;
	
	// El primer parámetro indican la gravedad (en x e y) (1 unidad = 1 metro)
	World world;
	// El renderizador de mundos Box2D
	Box2DDebugRenderer renderer;
	
	private long lastShoot;
	
	// Clase que permite gestionar las luces del mundo
	RayHandler rayHandler;
	
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
		
		// Añade un cuerpo con forma de caja al mundo
		Body box1 = WorldGenerator.createBoxBody(world, 200, 30, 10, 10);
		box1.setUserData("solido");
		Body box2 = WorldGenerator.createBoxBody(world, 200, 60, 10, 10);
		box2.setUserData("solido");
		Body box3 = WorldGenerator.createBoxBody(world, 200, 90, 10, 10);
		box3.setUserData("solido");
		Body box4 = WorldGenerator.createBoxBody(world, 200, 120, 10, 10);
		box4.setUserData("solido");
		Body box5 = WorldGenerator.createBoxBody(world, 200, 150, 10, 50);
		box5.setUserData("solido");
		Body box6 = WorldGenerator.createBoxBody(world, 220, 180, 20, 10);
		box6.setUserData("solido");
		Body box7 = WorldGenerator.createBoxBody(world, 180, 210, 20, 10);
		box7.setUserData("solido");
	
		
		// Añade una rampa
		//Body floor = WorldGenerator.createPolygonBody(world, 40, 30, 100, -10);
		
		// Añade un gestor de luces al mundo
		rayHandler = new RayHandler(world);
		rayHandler.setCombinedMatrix(camera.combined);
		
		/*
		 *  Añade una luz en una posición determinada del mundo
		 *  params: gestor de luces, número de rayos, distancia, x, y
		 */
		PointLight pointLight = new PointLight(rayHandler, 5000, Color.CYAN, 100, 240, 200);
		// Asigna la luz a un objeto determinado 
		pointLight.attachToBody(box7, 0, 0);
		
		/*
		 *  Añade una luz direccional (misma iluminación en todo el mundo)
		 *  params: gestor de luces, número de rayos, color, ángulo de inclinación
		 */
		//DirectionalLight directionalLight = new DirectionalLight(rayHandler, 2000, Color.WHITE, -45);
		
		/*
		 *  Añade una dirección con forma de cono (o circula si el ángulo es de 180)
		 *  params: gestor de luces, número de rayos, color, distancia, x, y, angulo posición, angulo luz
		 */
		//ConeLight coneLight = new ConeLight(rayHandler, 2000, Color.YELLOW, 1000, 200, 200, -90, 45);
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
		rayHandler.updateAndRender();
		
		/*
		 * Actualiza el mundo 60 veces por segundo (1 / 60)
		 */
		world.step(1 / 60f, 6, 2);
		
		// Pinta algún texto en la pantalla
		spriteBatch.begin();
		font.draw(spriteBatch, "Ejemplo de Fuerzas Box2D", camera.viewportWidth / 2, 600);
		font.draw(spriteBatch, "Pulsa ESPACIO para disparar", camera.viewportWidth / 2, 570);
		spriteBatch.end();
		
		handleInput();
		
		// Si un cuerpo disparado se detiene se elimina
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		for (Body body : bodies) {
			if (body.getUserData() != null)
				if ((body.getLinearVelocity().x == 0) && (((String) body.getUserData()).equals("disparo")))
					world.destroyBody(body);
		}
	}
	
	/**
	 * Controla la entrada de teclado del usuario
	 */
	private void handleInput() {
		
		if ((Gdx.input.isKeyPressed(Keys.SPACE)) || (Gdx.input.isTouched())) {
			if ((TimeUtils.millis() - lastShoot) > 1000) {
				shootCircle();
				lastShoot = TimeUtils.millis();
			}
		}
	}
	
	/**
	 * Dispara un cuerpo
	 */
	private void shootCircle() {
		
		Body circle = WorldGenerator.createCircleBody(world, 0, 40);
		
		// Aplica una fuerza para "despertar" el objeto
		circle.setLinearVelocity(new Vector2(1000f, 10f));
		// Aplica un impulso al objeto (modifica al instante la velocidad del objeto sobre 
		// el que se aplica
		circle.applyLinearImpulse(10f, 0, circle.getPosition().x, circle.getPosition().y, true);
		circle.setUserData("disparo");
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
		
		renderer.dispose();
		rayHandler.dispose();
	}
	
	
}
