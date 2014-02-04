package org.sfaci.bombermanx.managers;

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
		ResourceManager.loadResource("brick", new Texture("bricks/brick.png"));
		ResourceManager.loadResource("stone", new Texture("bricks/stone.png"));
		ResourceManager.loadResource("player_idle", new Texture("player/player_idle.png"));
		ResourceManager.loadResource("player_right_animation", new Texture("player/player_right_animation.png"));
		ResourceManager.loadResource("player_left_animation", new Texture("player/player_left_animation.png"));
		ResourceManager.loadResource("player_up_animation", new Texture("player/player_up_animation.png"));
		ResourceManager.loadResource("player_down_animation", new Texture("player/player_down_animation.png"));
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
