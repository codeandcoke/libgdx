package org.sfaci.box2d_textures;

import box2dLight.ConeLight;
import box2dLight.DirectionalLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Clase ejemplo que comprueba c�mo aplicar fuerzas a cuerpos s�lidos
 * en un mundo Box2D
 * 
 * @author Santiago Faci
 * @version 2.0
 *
 */
public class Textures extends Game implements ContactListener {

	private static int STONE_WIDTH = 48;
	private static int STONE_HEIGHT = 48;
	
	private static int BOX_WIDTH = 80;
	private static int BOX_HEIGHT = 80;
	
	OrthographicCamera camera;
	SpriteBatch spriteBatch;
	BitmapFont font;
	
	// Texturas para los cuerpos
	Texture stoneTexture;
	Texture wallTexture;
	Texture grassTexture;
	// Sonidos
	Sound wallCrash;
	Sound stoneCrash;
	Sound shootSound;
	
	// El primer par�metro indican la gravedad (en x e y) (1 unidad = 1 metro)
	World world;
	// El renderizador de mundos Box2D
	Box2DDebugRenderer renderer;
	
	// Momento del �ltimo disparo y la �ltima caja generada
	private long lastShoot;
	private long lastBox;
	
	// Clase que permite gestionar las luces del mundo
	RayHandler rayHandler;
	
	@Override
	public void create() {
		// C�mara del juego
		camera = new OrthographicCamera();
		// Zona visible de la c�mara
		camera.viewportWidth = 1024;
		camera.viewportHeight = 768;
		// Colca la c�mara en el centro de la pantalla
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
		camera.update();
		
		//Texture.setEnforcePotImages(false);
		stoneTexture = new Texture(Gdx.files.internal("stone.png"));
		wallTexture = new Texture(Gdx.files.internal("wall.png"));
		grassTexture = new Texture(Gdx.files.internal("grass.png"));
		wallCrash = Gdx.audio.newSound(Gdx.files.internal("wall_crash.mp3"));
		stoneCrash = Gdx.audio.newSound(Gdx.files.internal("stone_crash.mp3"));
		shootSound = Gdx.audio.newSound(Gdx.files.internal("shoot.mp3"));
		
		spriteBatch = new SpriteBatch();
		font = new BitmapFont();
		
		world = new World(new Vector2(0, -9.8f), true);
		// A�ade un listener de impactos
		world.setContactListener(this);
		renderer = new Box2DDebugRenderer();
		
		// Define un cuerpo (la tierra)
		BodyDef groundDef = new BodyDef();
		groundDef.type = BodyType.StaticBody;
		groundDef.position.set(new Vector2(0, 10));
		Body groundBody= world.createBody(groundDef);
		groundBody.setUserData("ground");

		PolygonShape groundShape = new PolygonShape();
		groundShape.setAsBox(camera.viewportWidth * 2, 10.0f);
		groundBody.createFixture(groundShape, 0.0f);
		
		// A�ade una rampa
		//Body floor = WorldGenerator.createPolygonBody(world, 40, 30, 100, -10);
		
		// A�ade un gestor de luces al mundo
		rayHandler = new RayHandler(world);
		rayHandler.setCombinedMatrix(camera.combined);
		
		/*
		 *  A�ade una luz en una posici�n determinada del mundo
		 *  params: gestor de luces, n�mero de rayos, distancia, x, y
		 */
		// PointLight pointLight = new PointLight(rayHandler, 5000, Color.CYAN, 100, 240, 200);
		// Asigna la luz a un objeto determinado 
		//pointLight.attachToBody(box7, 0, 0);
		
		/*
		 *  A�ade una luz direccional (misma iluminaci�n en todo el mundo)
		 *  params: gestor de luces, n�mero de rayos, color, �ngulo de inclinaci�n
		 */
		// DirectionalLight directionalLight = new DirectionalLight(rayHandler, 2000, Color.WHITE, -45);
		
		/*
		 *  A�ade una direcci�n con forma de cono (o circula si el �ngulo es de 180)
		 *  params: gestor de luces, n�mero de rayos, color, distancia, x, y, angulo posici�n, angulo luz
		 */
		ConeLight coneLight = new ConeLight(rayHandler, 2000, Color.YELLOW, 1000, 200, 200, -90, 45);
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
		// Dibuja las formas Box2D para depurar
		// renderer.render(world, camera.combined);
		// Dibuja las luces
		//rayHandler.updateAndRender();
		
		/*
		 * Actualiza el mundo 40 veces por segundo (1 / 40)
		 */
		world.step(1 / 45f, 6, 2);
		
		// Pinta alg�n texto en la pantalla
		spriteBatch.begin();
		font.draw(spriteBatch, "Ejemplo de Fuerzas Box2D", camera.viewportWidth / 2, 600);
		font.draw(spriteBatch, "Pulsa en la parte de abajo para disparar", camera.viewportWidth / 2, 570);
		font.draw(spriteBatch, "Pulsa en la parte de arriba para generar una caja", camera.viewportWidth / 2, 540);
		// Pinta las texturas de los cuerpos Box2D
		renderStones(spriteBatch);
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
	
	private void renderStones(SpriteBatch spriteBatch) {
		
		// Pinta las rocas lanzadas
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		for (Body body : bodies) {
			if (body.getUserData() != null)
				if (((String) body.getUserData()).equals("shoot"))
				
					spriteBatch.draw(stoneTexture, body.getPosition().x - STONE_WIDTH / 2, body.getPosition().y - STONE_HEIGHT / 2, STONE_WIDTH / 2, STONE_HEIGHT / 2, 
									STONE_WIDTH, STONE_HEIGHT, 1, 1, body.getAngle() * MathUtils.radiansToDegrees, 0, 0, STONE_WIDTH, STONE_HEIGHT, false, false);
				else if (((String) body.getUserData()).equals("solid"))
					
					spriteBatch.draw(wallTexture, body.getPosition().x - BOX_WIDTH / 2, body.getPosition().y - BOX_HEIGHT / 2, BOX_WIDTH / 2, BOX_HEIGHT / 2, 
							BOX_WIDTH, BOX_HEIGHT, 1, 1, body.getAngle() * MathUtils.radiansToDegrees, 0, 0, BOX_WIDTH, BOX_HEIGHT, false, false);
			
				else if (((String) body.getUserData()).equals("ground"))
					
					spriteBatch.draw(grassTexture, 0, 0, 0, 0, 
							camera.viewportWidth, 20, 1, 1, body.getAngle() * MathUtils.radiansToDegrees, 0, 0, BOX_WIDTH, BOX_HEIGHT, false, false);
		}
	}
	
	/**
	 * Controla la entrada de teclado del usuario
	 */
	private void handleInput() {
		
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			
			if ((TimeUtils.millis() - lastShoot) > 1000) {
				shootCircle();
				lastShoot = TimeUtils.millis();
			}
		}
		
		if (Gdx.input.isTouched()) {
			
			// Deja caer una caja
			if (Gdx.input.getY() < 500) {
				if ((TimeUtils.millis() - lastBox) > 200) {
					createBox();
					lastBox = TimeUtils.millis();
				}
			}
			// Dispara una piedra
			else {
				if ((TimeUtils.millis() - lastShoot) > 1000) {
					shootCircle();
					lastShoot = TimeUtils.millis();
				}
			}
		}
	}
	
	/**
	 * Dispara un cuerpo
	 */
	private void shootCircle() {
		
		Body circle = WorldGenerator.createCircleBody(world, 100, 80);
		
		// Velocidad del objeto
		circle.setLinearVelocity(new Vector2(100f, 10f));
		
		// Aplica una fuerza al objeto
		circle.applyForce(new Vector2(100f, 0f), circle.getPosition(), true);
		
		// Aplica un impulso al objeto (modifica al instante la velocidad del objeto sobre 
		// el que se aplica
		circle.applyLinearImpulse(100f, 0f, circle.getPosition().x, circle.getPosition().y, true);
		
		// Aplica velocidad angular al objeto
		//circle.setAngularVelocity(12);
		
		circle.setUserData("shoot");
		shootSound.play();
	}
	
	/**
	 * Genera una caja
	 */
	private void createBox() {
		Body box = WorldGenerator.createBoxBody(world, Gdx.input.getX(), 768 - Gdx.input.getY(), 40, 40);
		box.setUserData("solid");
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
		
		renderer.dispose();
		rayHandler.dispose();
	}

	@Override
	public void beginContact(Contact contact) {
				
		if ((contact.getFixtureA().getBody().getUserData().equals("ground")) &&
		   (contact.getFixtureB().getBody().getUserData().equals("solid"))) {
			wallCrash.play();
		}
		if ((contact.getFixtureA().getBody().getUserData().equals("solid")) &&
		   (contact.getFixtureB().getBody().getUserData().equals("shoot"))) {
			stoneCrash.play();
		} 
	}

	@Override
	public void endContact(Contact contact) {

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}
}
