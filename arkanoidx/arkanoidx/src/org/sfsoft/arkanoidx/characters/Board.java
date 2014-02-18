package org.sfsoft.arkanoidx.characters;

import org.sfsoft.arkanoidx.util.Constants;

import com.badlogic.gdx.graphics.Texture;

/**
 * Clase que representa la tabla del jugador
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class Board extends Character {

	int lives;
	public enum State {
		RIGHT, LEFT, IDLE;
	}
	public State state;
	
	public Board(Texture texture, float x, float y, int lives) {
		super(texture, x, y);
		this.lives = lives;
		state = State.IDLE;
	}
	
	// Desplaza la tabla en el eje x
	public void move(float x) {
		
		this.x += x;
	}
	
	@Override
	public void update(float dt) {
		
		super.update(dt);
		
		// Comprueba los límites de la pantalla
		if (x <= 0)
			x = 0;
		
		if ((x + Constants.BOARD_WIDTH) >= Constants.SCREEN_WIDTH)
			x = Constants.SCREEN_WIDTH - Constants.BOARD_WIDTH;
	}
}
