package org.sfaci.jumper2dx.managers;

import org.sfaci.jumper2dx.Jumper2DX;
import org.sfaci.jumper2dx.PauseScreen;
import org.sfaci.jumper2dx.characters.Enemy;
import org.sfaci.jumper2dx.characters.Item;
import org.sfaci.jumper2dx.characters.Platform;
import org.sfaci.jumper2dx.characters.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Controla toda la lógica del juego
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class GameController implements InputProcessor {

	public Jumper2DX game;
	
	Player player;
	
	public int lives;
	public int level;
	
	public GameController(Jumper2DX game) {
		this.game = game;
		
		loadResources();
		restart();
		
		Gdx.input.setInputProcessor(this);
	}
	
	/**
	 * Carga los recursos del juego
	 */
	private void loadResources() {
		
		ResourceManager.loadSounds();
		ResourceManager.loadTextures();
	}
	
	/**
	 * Reinicia los parámetros de la partida
	 */
	private void restart() {
	
		// Inicia los parámetros de la partida
		LevelManager.currentCoins = 0;
		lives = LevelManager.currentLives;
		level = LevelManager.currentLevel;
	}
	
	/**
	 * Inicia el controlador de la partida
	 */
	public void start() {
		
		// Crea y carga el mapa
		LevelManager.loadMap();
		
		// Crea el jugador y lo posiciona al inicio de la pantalla
		player = new Player(this);
		// posición inicial del jugador
		player.position.set(0 * LevelManager.map.getProperties().get("tilewidth", Integer.class), 
							2 * LevelManager.map.getProperties().get("tileheight", Integer.class) + 32);
		
		// Música durante la partida
		ResourceManager.getSound(LevelManager.getCurrentLevelName()).loop(0.5f);
	}
	
	/**
	 * Reinicia el controlador de la partida
	 */
	public void resume() {
			
		LevelManager.clearLevel();
		start();
	}
	
	public void update(float dt) {
		
		// Comprobar entrada de usuario (teclado, pantalla, ratón, . . .)
		handleInput();
		
		if (game.paused)
			return;
			
		// Actualizar jugador
		player.update(dt);
		
		// Comprueba colisiones del jugador con elementos móviles del juego
		checkCollisions();
		
		// Comprueba el estado de los enemigos
		for (Enemy enemy : LevelManager.enemies) {
			
			// Si la cámara no los enfoca no se actualizan
			if (!game.gameRenderer.camera.frustum.pointInFrustum(new Vector3(enemy.position.x, enemy.position.y, 0)))
				continue;
		
			if (enemy.isAlive)
				enemy.update(dt);
			else
				LevelManager.enemies.removeValue(enemy, true);
		}

		for (Platform platform : LevelManager.platforms)
			platform.update(dt);
		
		for (Item item : LevelManager.items)
			if (item.isAlive)
				item.update(dt);
			else
				LevelManager.items.removeValue(item, true);
	}
	
	/**
	 * Comprueba las colisiones del jugador con los elementos móviles del juego
	 * Enemigos e items
	 */
	private void checkCollisions() {
		Rectangle playerRect = new Rectangle();
		playerRect.set(player.position.x, player.position.y, Player.WIDTH, Player.HEIGHT);
		
		// Comprueba si el enemigo ha chocado contra algún enemigo
		for (Enemy enemy : LevelManager.enemies) {
			Rectangle enemyRect = new Rectangle();
			enemyRect.set(enemy.position.x, enemy.position.y, Enemy.WIDTH, Enemy.HEIGHT);
			
			if (enemyRect.overlaps(playerRect)) {
				
				// Si el jugador está por encima elimina el enemigo
				if (player.position.y > (enemy.position.y + 5)) {
					ResourceManager.getSound("kick").play();
					LevelManager.enemies.removeValue(enemy, true);
					
					// El jugador rebota
					player.jump(false);
				}
				// Si está al mismo nivel o por debajo se pierde una vida
				else {
					player.velocity.x = player.velocity.y = 0;
					lives--;
					ResourceManager.getSound(LevelManager.getCurrentLevelName()).stop();
					ResourceManager.getSound("down").play();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException ie) {}
					
					game.setScreen(new PauseScreen(game, PauseScreen.State.DAMAGE));
					game.getScreen().dispose();
				}
			}
		}
		
		// Comprueba si el jugador recoge algún item de la pantalla
		for (Item item : LevelManager.items) {
			Rectangle itemRect = new Rectangle();
			itemRect.set(item.position.x, item.position.y, Item.WIDTH, Item.HEIGHT);
			
			if (itemRect.overlaps(playerRect)) {
				ResourceManager.getSound("up").play();
				LevelManager.items.removeValue(item, true);
				lives++;
			}
		}
		
		boolean stuck = false;
		// Comprueba colisiones con las plataformas móviles de la pantalla
		for (Platform platform : LevelManager.platforms) {
			Rectangle platformRectangle = new Rectangle(platform.position.x, platform.position.y, platform.width, platform.height);
				
			// Si colisiona con una se coloca encima y se "pega" a ella
			if (platformRectangle.overlaps(playerRect)) {
				
				// Si está cayendo y está por encima se coloca en la plataforma
				if ((player.velocity.y < 0) && (player.position.y > platformRectangle.y)) {
					player.position.y = platformRectangle.y + platformRectangle.height;
					player.canJump = true;
					player.isJumping = false;
					Player.stuckPlatform = platform;
					stuck = true;
				}
			}
		}
		// Si ya no está encima de ninguna plataforma, se "despega" de ellas
		if (!stuck)
			Player.stuckPlatform = null;
	}
	
	/**
	 * Controla la entrada de teclado del usuario
	 */
	private void handleInput() {
		
		// Se pulsa la teclad derecha
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			player.isRunning = true;
			Player.stuckPlatform = null;
			player.velocity.x = Player.WALKING_SPEED;
			player.state = Player.State.RUNNING_RIGHT;
			
			if ((!player.isJumping))
				player.isRunning = true;
		}
		// Se pulsa la tecla izquierda
		else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			player.isRunning = true;
			Player.stuckPlatform = null;
			player.velocity.x = -Player.WALKING_SPEED;
			player.state = Player.State.RUNNING_LEFT;
			
			if ((!player.isJumping))
				player.isRunning = true;
		}
		// No se pulsa ninguna tecla
		else {
			
			if (player.isRunning)
				if (player.state == Player.State.RUNNING_LEFT)
					player.state = Player.State.IDLE_LEFT;
				else
					player.state = Player.State.IDLE_RIGHT;
			
			player.isRunning = false;
			player.velocity.x = 0;
		}
		
		// Se pulsa la tecla CONTROL IZQ (salto)
		if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
			
			Player.stuckPlatform = null;
			if (player.canJump) {
				player.jump(true);
			}
		}
		
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			GameRenderer.CAMERA_OFFSET += 40f * Gdx.graphics.getDeltaTime(); 
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			GameRenderer.CAMERA_OFFSET -= 40f * Gdx.graphics.getDeltaTime(); 
		}
		
		// Controla los límites (por debajo) de la pantalla, cuando cae el personaje
		if (player.position.y < 0) {
			player.die();
		}
		
		// Controla el límite izquierdo de la pantalla
		if (player.position.x <= 0)
			player.position.x = 0;
	}
	
	/**
	 * Reinicia la partida para generar una partida nueva
	 */
	public void prepareGame() {
		
		LevelManager.currentCoins = 0;
		lives = 3;
		LevelManager.currentLevel = 1;
		LevelManager.highLevel = true;
		GameRenderer.CAMERA_OFFSET = 0;
	}

	@Override
	public boolean keyDown(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keyCode) {
		
		if (keyCode == Keys.P)
			game.paused = !game.paused;
		return true;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}
}
