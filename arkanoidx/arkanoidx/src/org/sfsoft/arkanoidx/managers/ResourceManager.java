package org.sfsoft.arkanoidx.managers;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/**
 * Clase que gestiona los recursos del juego
 * @author Santiago Faci
 *
 */
public class ResourceManager {

	private static Map<String, Texture> textures = new HashMap<String, Texture>();
	private static Map<String, Sound> sounds = new HashMap<String, Sound>();
	
	/**
	 * Carga en memoria todos los recursos del juego (texturas y sonidos)
	 */
	public static void loadAllResources() {
		
		Texture.setEnforcePotImages(false);
		// Imágenes
		ResourceManager.loadResource("ball", new Texture("player/ball.png"));
		ResourceManager.loadResource("board", new Texture("player/board.png"));
		ResourceManager.loadResource("yellow_brick", new Texture("bricks/yellow_brick.png"));
		ResourceManager.loadResource("green_brick", new Texture("bricks/green_brick.png"));
		ResourceManager.loadResource("gray_brick", new Texture("bricks/gray_brick.png"));
		ResourceManager.loadResource("purple_brick", new Texture("bricks/purple_brick.png"));
		ResourceManager.loadResource("red_brick", new Texture("bricks/red_brick.png"));
		ResourceManager.loadResource("blue_brick", new Texture("bricks/blue_brick.png"));
		ResourceManager.loadResource("black_brick", new Texture("bricks/black_brick.png"));
		ResourceManager.loadResource("white_brick", new Texture("bricks/white_brick.png"));
	}
	
	/**
	 * Carga un recurso en memoria
	 * @param name
	 * @param texture
	 */
	private static void loadResource(String name, Texture texture) {
		
		textures.put(name, texture);
	}
	
	/**
	 * Obtiene un recurso textura de memoria
	 * @param name
	 * @return
	 */
	public static Texture getTexture(String name) {
		
		return textures.get(name);
	}
	
	/**
	 * Obtiene un recurso de sonido de memoria
	 * @param name
	 * @return
	 */
	public static Sound getSound(String name) {
		
		return sounds.get(name);
	}
}
