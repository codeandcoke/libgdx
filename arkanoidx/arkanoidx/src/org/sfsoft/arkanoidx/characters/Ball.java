package org.sfsoft.arkanoidx.characters;

import org.sfsoft.arkanoidx.managers.SpriteManager;
import static org.sfsoft.arkanoidx.util.Constants.BALL_WIDTH;
import static org.sfsoft.arkanoidx.util.Constants.BALL_SPEED;
import static org.sfsoft.arkanoidx.util.Constants.SCREEN_WIDTH;
import static org.sfsoft.arkanoidx.util.Constants.SCREEN_HEIGHT;
import static org.sfsoft.arkanoidx.util.Constants.BOARD_HEIGHT;
import static org.sfsoft.arkanoidx.util.Constants.BRICK_HEIGHT;

import com.badlogic.gdx.graphics.Texture;

/**
 * Clase que representa la bola
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class Ball extends Character {

	// Al comenzar la partida la bola puede aparecer pausada
	boolean paused;
	float speedX;
	float speedY;
	
	private SpriteManager spriteManager;
	
	public Ball(Texture texture, float x, float y, SpriteManager spriteManager) {
		super(texture, x, y);
		speedX = BALL_SPEED;
		speedY = BALL_SPEED;
		paused = true;
		
		this.spriteManager = spriteManager;
	}
	
	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	
	@Override
	public void update(float dt) {
		
		super.update(dt);
		
		if (paused)
			return;
		
		// Actualiza posición de la bola
		x += speedX * dt;
		y += speedY * dt;
		
		// Comprueba los límites de la pantalla (
		// Rebote en parte izquierda
		if (x <= 0) {
			x = 0;
			speedX = -speedX;
		}
			
		// Rebote en parte derecha
		if ((x + BALL_WIDTH) >= SCREEN_WIDTH) {
			x = SCREEN_WIDTH - BALL_WIDTH;
			speedX = -speedX;
		}
		
		// Rebote en el techo
		if ((y + BALL_WIDTH) >= SCREEN_HEIGHT) {
			y = SCREEN_HEIGHT - BALL_WIDTH;
			speedY = -speedY;
		}
		
		// Rebote con la tabla
		Board board = spriteManager.board; 
		if (board.rect.overlaps(rect)) {
			
			// Si la tabla está en movimiento puede alterar la dirección X de la bola
			if (board.state == Board.State.LEFT) {
				speedX = -BALL_SPEED;
			}
			if (board.state == Board.State.RIGHT) {
				speedX = BALL_SPEED;
			}
			
			y = spriteManager.board.y + BOARD_HEIGHT;
			speedY = -speedY;
		}
		
		// Rebote con ladrillos
		// FIXME Falta comprobar cómo podemos hacer que rebote de lado en un ladrillo
		for (Brick brick : spriteManager.bricks) {
			if (brick.rect.overlaps(rect)) {
				
				// La bola pega desde abajo
				if ((rect.y + BALL_WIDTH) <= (brick.rect.y + BRICK_HEIGHT)) {
					speedY = -speedY;
					y = rect.y = brick.y - BALL_WIDTH;
					
				}
				// La bola pega desde arriba
				else {
					y = rect.y = brick.y + BRICK_HEIGHT;
					speedY = -speedY;	
				}
				brick.lives--;
				if (brick.lives == 0)
					spriteManager.bricks.removeValue(brick, true);
			}
		}
	}
}
