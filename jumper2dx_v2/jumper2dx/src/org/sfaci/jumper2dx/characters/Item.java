package org.sfaci.jumper2dx.characters;

import org.sfaci.jumper2dx.characters.Player.State;
import org.sfaci.jumper2dx.managers.TiledMapManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;

/**
 * Representa cualquier item del juego
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class Item implements Disposable {

	public Vector2 position;
	public State state;
	public Vector2 velocity;
	public boolean isAlive;
		
	private TextureRegion currentFrame;
	private boolean faceLeft;
	
	private Array<Rectangle> tiles = new Array<Rectangle>();
	// Pool de rectángulos (mejora la eficiencia si se trabaja con muchos)
	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject () {
			return new Rectangle();
		}
	};
	
	// Parámetros de movimiento del Item
	public static float WALKING_SPEED = 1.0f;
	public static float JUMPING_SPEED = 5.0f;
	public static float MAX_JUMPING = 60f;
	public static float GRAVITY = 9f;
	public static float WIDTH = 13;
	public static float HEIGHT = 13;
	
	public Item() {
		
		position = new Vector2();
		velocity = new Vector2();
		faceLeft = true;
			
		// Textura del item
		currentFrame = new Sprite(new Texture(Gdx.files.internal("items/mushroom.png")));
		
		velocity.x = -1.0f;
		isAlive = true;
	}
	
	/**
	 * Pinta la animación en pantalla
	 */
	public void render(SpriteBatch spriteBatch) {
				
		// Pinta en pantalla el frame actual
		spriteBatch.draw(currentFrame, position.x, position.y, WIDTH, HEIGHT);
	}
	
	/**
	 * Actualiza el estado del jugador en función de la tecla pulsada
	 * @param input
	 * @param dt
	 */
	public void update(float dt) {
		
		if (faceLeft)
			velocity.x = -WALKING_SPEED;
		else
			velocity.x = WALKING_SPEED;
		
		// Si sale de la pantalla se marca para eliminar
		if (position.x <= 0) 
			dispose();
		if (position.y <= 0)
			dispose();
		
		// Aplica la fuerza de la gravedad (en el eje y)
		velocity.y -= GRAVITY * dt;
		
		// Controla que el personaje nunca supere una velocidad límite en el eje y
		if (velocity.y > JUMPING_SPEED)
			velocity.y = JUMPING_SPEED;
		else if (velocity.y < -JUMPING_SPEED)
			velocity.y = -JUMPING_SPEED;
		
		// Escala la velocidad para calcular cuanto se avanza en este frame (para mayor precisión)
		velocity.scl(dt);
		
		// Para el chequeo de colisiones
		int startX, endX, startY, endY;
		Rectangle rect = rectPool.obtain();
		rect.set(position.x, position.y, 18, 18);
		
		// Comprueba las colisiones con tiles en el eje Y (he quitado + velocity.y en startY, endY)
		// El item está saltando
		if (velocity.y > 0)
			startY = endY = (int) (position.y + HEIGHT + velocity.y);
		// El item cae o está parado (no se tiene en cuenta su altura)
		else
			startY = endY = (int) (position.y + velocity.y);
		
		startX = (int) position.x;
		endX = (int) (position.x + WIDTH);
		// Obtiene la lista de tiles que ocupan la posición del item
		getTilesPosition(startX, startY, endX, endY, tiles);
		rect.y += velocity.y;
		for (Rectangle tile : tiles) {
			if (tile.overlaps(rect)) {
				if (velocity.y > 0) {
					position.y = tile.y - HEIGHT;
				}
				else {
					position.y = tile.y + tile.height;
				}
				velocity.y = 0;
				break;
			}
		}
		
		// Comprueba las colisiones con tiles en el eje X (he quitado + velocity.x en startX, endX)
		// El item se desplaza hacia la derecha
		if (velocity.x > 0)
			startX = endX = (int) (position.x + WIDTH + velocity.x); 
		// El item se desplaza hacia la izquierda (no se tiene en cuenta la anchura del personaje)
		else
			startX = endX = (int) (position.x + velocity.x);
		
		startY = (int) position.y;
		endY = (int) (position.y + HEIGHT);
		// Obtiene la lista de tiles que ocupan la posición del item
		getTilesPosition(startX, startY, endX, endY, tiles);
		rect.x += velocity.x;
		for (Rectangle tile : tiles) {
			if (rect.overlaps(tile)) {
				faceLeft = !faceLeft;
				velocity.x = 0;
				break;
			}
		}
		rect.x = position.x;
		
		rectPool.free(rect);
		
		velocity.scl(1 / dt);
		position.add(velocity);
	}
	
	/**
	 * Obtiene una lista de celdas con las que colisiona el item
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return
	 */
	private void getTilesPosition(int startX, int startY, int endX, int endY, Array<Rectangle> tiles) {
		
		tiles.clear();
	
		for (int y = startY; y <= endY; y++) {
			for (int x = startX; x <= endX; x++) {
				int xCell = (int) (x / TiledMapManager.collisionLayer.getTileWidth());
				int yCell = (int) (y / TiledMapManager.collisionLayer.getTileHeight());
				Cell cell = TiledMapManager.collisionLayer.getCell(xCell, yCell);
				
				// Si es un bloque se añade para comprobar colisiones
				if ((cell != null) && (cell.getTile().getProperties().containsKey(TiledMapManager.BLOCKED))) {
					Rectangle rect = rectPool.obtain();
					
					rect.set((int) (Math.ceil(x / 16f) * 16), (int) (Math.ceil(y / 16f) * 16), 0, 0);
					tiles.add(rect);
				}
			}
		}
	}
	
	@Override
	public void dispose() {
		isAlive = false;
	}
}
