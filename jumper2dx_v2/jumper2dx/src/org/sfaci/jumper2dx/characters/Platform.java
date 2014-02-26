package org.sfaci.jumper2dx.characters;

import org.sfaci.jumper2dx.managers.ResourceManager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Representa una plataforma móvil en el juego
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class Platform {

	public Vector2 originalPosition = new Vector2();
	public Vector2 position = new Vector2();
	public Vector2 velocity = new Vector2();
	public float width;
	public float height;
	private int offset;
	private Direction direction;
	private Direction currentDirection;
	
	public enum Direction {
		RIGHT, LEFT;
	}

	private float SPEED = 30f;
	
	/**
	 * Construye una plataforma móvil
	 * @param x Posición x inicial
	 * @param y Posición y inicial
	 * @param width Anchura
	 * @param height Altura
	 * @param offset Desplazamiento máximo
	 * @param rightDirection Indica si empieza moviendose a la derecha
	 */
	public Platform(float x, float y, float width, float height, int offset, Direction direction) {
		this.originalPosition.x = x;
		this.originalPosition.y = y;
		
		this.position.x = x;
		this.position.y = y;
		this.width = width;
		this.height = height;
		this.direction = direction;
		this.offset = offset;
		this.currentDirection = direction;
	}
	
	/**
	 * Pinta la plataforma en la pantalla
	 * @param batch
	 */
	public void render(SpriteBatch batch) {
		
		batch.draw(ResourceManager.getTexture("item_cloud"), position.x, position.y);
	}
	
	/**
	 * Actualiza la posición y dirección de la plataforma
	 * @param dt
	 */
	public void update(float dt) {
		
		if (currentDirection == Direction.RIGHT)
			velocity.x = SPEED;
		else
			velocity.x = -SPEED;
		
		position.x += velocity.x * dt;
		
		// La plataforma es de derechas
		if (direction == Direction.RIGHT) {
			// Actualmente se mueve hacia la derecha
			if (currentDirection == Direction.RIGHT) {
				if (position.x >= (originalPosition.x + offset))
					currentDirection = Direction.LEFT;
			}
			// Actualmente se mueve hacia la izquierda
			else {
				if (position.x <= originalPosition.x)
					currentDirection = Direction.RIGHT;
			}
		}
		// La plataforma es de izquierdas
		else {
			// Actualmente se mueve hacia la izquierda
			if (currentDirection == Direction.LEFT) {
				if (position.x < (originalPosition.x - offset))
					currentDirection = Direction.RIGHT;
			}
			// Actualmente se mueve hacia la derecha
			else {
				if (position.x > originalPosition.x)
					currentDirection = Direction.LEFT;
			}
		}
	}
}
