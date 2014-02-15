package org.sfsoft.jfighter2dx.managers;

import static org.sfsoft.jfighter2dx.util.Constants.SCREEN_HEIGHT;
import static org.sfsoft.jfighter2dx.util.Constants.ITEM_HEIGHT;

import java.util.Random;

import org.sfsoft.jfighter2dx.powerups.Bomb;
import org.sfsoft.jfighter2dx.powerups.Powerup;
import org.sfsoft.jfighter2dx.powerups.Shield;
import org.sfsoft.jfighter2dx.powerups.Powerup.PowerupType;

/**
 * Clase que genera los diferentes powerups que aparecen a lo largo de la partida
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class PowerupSpawner {

	public static Powerup createPowerup(PowerupType type) {
		
		Powerup powerup = null;
		
		switch (type) {
			case BOMB:
				powerup = new Bomb(1000, new Random().nextInt(SCREEN_HEIGHT - ITEM_HEIGHT), -100f);
				break;
			case SHIELD:
				powerup = new Shield(1000, new Random().nextInt(SCREEN_HEIGHT - ITEM_HEIGHT), -100f);
				break;
		}
		
		return powerup;
	}
}
