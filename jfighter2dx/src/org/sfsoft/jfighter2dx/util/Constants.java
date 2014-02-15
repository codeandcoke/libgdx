package org.sfsoft.jfighter2dx.util;

/**
 * Constantes varias del juego
 * @author Santiago Faci
 * @version 1.0
 */
public interface Constants {

	public static final String APP = "jfighter2dx";
	
	// Ancho y alto de la pantalla de juego
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;
	
	// Límite de enemigos al mismo tiempo
	public static final int ENEMY_LIMIT = 30;
	// Pausa de tiempo en la generación de enemigos
	public static final float ENEMY_RATE = 1;
	// Velocidad de generación de items bombas
	public static final float POWERUP_TIME = 15;
	public static final int ITEM_HEIGHT = 30;
	
	// Tamaño de la nave del personaje principal
	public static final int SHIP_WIDTH = 64;
	public static final int SHIP_HEIGHT = 29;
	
	// Tamaño de los personajes estándar (excepto la nave del personaje)
	public static final int ENEMY_WIDTH = 40;
	public static final int ENEMY_HEIGHT = 30;
	
	public static final int MISSILE_WIDTH = 93;
	public static final int MISSILE_HEIGHT = 16;
	
	public static final int STONE_WIDTH = 50;
	public static final int STONE_HEIGHT = 75;
	
	// Tamaño de los bloques
	public static final int BLOCK_WIDTH = 32;
	public static final int BLOCK_HEIGHT = 32;
	
	public static final int SHIP_LIVES = 3;
	public static final float SHIELD_TIME = 4;
}
