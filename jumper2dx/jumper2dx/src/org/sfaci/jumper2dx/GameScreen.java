package org.sfaci.jumper2dx;

import org.sfaci.jumper2dx.characters.Player;
import org.sfaci.jumper2dx.managers.TiledMapManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Pantalla de Juego, donde el usuario juega la partida
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class GameScreen implements Screen {

	final Jumper2DX game;
	
	TiledMap map;
	OrthogonalTiledMapRenderer mapRenderer;
	Player player;
	
	OrthographicCamera camera;
	
	boolean empiezaPartida = true;
	
	public GameScreen(Jumper2DX game) {
		this.game = game;
	}
	
	/*
	 * Método que se invoca cuando esta pantalla es
	 * la que se está mostrando
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		
		// Carga toda la pantalla desde cero
		if (empiezaPartida) {
			// Evita que nos fuerce a que el fichero de tiles sea potencia de 2
			Texture.setEnforcePotImages(false);
			// Crea el mapa y el renderizador
			map = new TmxMapLoader().load("levels/level1.tmx");
			mapRenderer = new OrthogonalTiledMapRenderer(map);
			
			// Crea una cámara y muestra 30x20 unidades del mundo
			camera = new OrthographicCamera();
			camera.setToOrtho(false, 30, 20);
			camera.update();
			
			// Obtiene la referencia a la capa de terreno del mapa
			TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);
			// Crea el jugador y lo posiciona al inicio de la pantalla
			player = new Player(collisionLayer, game);
			// posición inicial del jugador
			player.position.set(0 * map.getProperties().get("tilewidth", Integer.class), 2 * map.getProperties().get("tileheight", Integer.class) + 32);
			
			// Coloca tiles animados
			TiledMapManager.animateTiles(map, collisionLayer, "coin", 4);
			TiledMapManager.animateTiles(map, collisionLayer, "plant", 3);
			TiledMapManager.animateTiles(map, collisionLayer, "box", 4);
			
			// Música durante la partida
			game.levelSound.loop();
			
			empiezaPartida = false;
		}
	}
	
	@Override
	public void render(float dt) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		handleInput();
		player.update(dt);
		
		// Fija la cámara para seguir al personaje en el centro de la pantalla y altura fija (eje y)
		camera.position.set(player.position.x + 18 / 2, 125, 0);
		camera.zoom = 1 / 2f;
		camera.update();
		mapRenderer.setView(camera);
		mapRenderer.render();
		
		mapRenderer.getSpriteBatch().begin();
		// Pinta al jugador
		player.render(mapRenderer.getSpriteBatch());
		// Pinta la información en partida relativa al jugador
		game.font.setScale(0.8f);
		mapRenderer.getSpriteBatch().draw(game.itemCoin, camera.position.x - 60, camera.position.y - 135 - 12);
		game.font.draw(mapRenderer.getSpriteBatch(), " X " + game.coins, camera.position.x - 50, camera.position.y - 135);
		mapRenderer.getSpriteBatch().draw(game.itemLife, camera.position.x + 40, camera.position.y - 135 - 12);
		game.font.draw(mapRenderer.getSpriteBatch(), " X " + game.lives, camera.position.x + 50, camera.position.y - 135);
		mapRenderer.getSpriteBatch().end();
	}
	
	/**
	 * Controla la entrada de teclado
	 */
	private void handleInput() {
		
		// Se pulsa la teclad derecha
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			player.isRunning = true;
			player.velocity.x = Player.WALKING_SPEED;
			player.state = Player.State.WALKING_RIGHT;
			
			if ((!player.isJumping))
				player.isWalking = true;
		}
		// Se pulsa la tecla izquierda
		else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			player.isRunning = true;
			player.velocity.x = -Player.WALKING_SPEED;
			player.state = Player.State.WALKING_LEFT;
			
			if ((!player.isJumping))
				player.isWalking = true;
		}
		// No se pulsa ninguna tecla
		else {
			
			if (player.isRunning)
				if (player.state == Player.State.WALKING_LEFT)
					player.state = Player.State.IDLE_LEFT;
				else
					player.state = Player.State.IDLE_RIGHT;
			
			player.isRunning = false;
			player.isWalking = false;
			player.velocity.x = 0;
		}
		
		// Se pulsa la tecla CONTROL IZQ (salto)
		if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
					
			if (player.canJump) {
				game.jumpSound.play();
				player.velocity.y = Player.JUMPING_SPEED;
				player.canJump = false;
				player.isJumping = true;
				player.isWalking = false;
			}
		}
		
		/*
		 * El usuario pulsa la tecla de Pausa (P)
		 * El juego se detiene almacenando el estado de la partida (this)
		 */
		if (Gdx.input.isKeyPressed(Keys.P)) {
			game.setScreen(new PauseScreen(game, PauseScreen.State.PAUSE, this));
		}
		
		// Controla los límites (por debajo) de la pantalla, cuando cae el personaje
		if (player.position.y < 0) {
			game.lives--;
			if (game.lives == 0) {
				game.gameOverSound.play();
				game.setScreen(new MainMenuScreen(game));
			}
			else {
				game.downSound.play();
				game.setScreen(new GameScreen(game));
			}
		}
		
		// Controla el límite izquierdo de la pantalla
		if (player.position.x <= 0)
			player.position.x = 0;
	}
	
	/*
	 * MÉtodo que se invoca cuando esta pantalla
	 * deja de ser la principal
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override
	public void hide() {
		
	}
	
	@Override
	public void dispose() {

	}

	@Override
	public void resize(int width, int height) {
		camera.viewportHeight = height;
		camera.viewportWidth = width;
		camera.update();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
