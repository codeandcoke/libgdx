package org.sfaci.bombermanx.managers;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Clase que gestiona los recursos del juego
 * @author Santiago Faci
 *
 */
public class ResourceManager {

	private static Map<String, Texture> textures = new HashMap<String, Texture>();
	private static Map<String, TextureAtlas> atlas = new HashMap<String, TextureAtlas>();
	private static Map<String, Sound> sounds = new HashMap<String, Sound>();
	
	/**
	 * Carga en memoria todos los recursos del juego (texturas y sonidos)
	 */
	public static void loadAllResources() {
		
		Texture.setEnforcePotImages(false);
		// Imágenes
		ResourceManager.loadResource("door", new Texture("bricks/door.png"));
		ResourceManager.loadResource("brick", new Texture("bricks/brick.png"));
		ResourceManager.loadResource("broken_brick_animation", new Texture("bricks/broken_brick_animation.png"));
		ResourceManager.loadResource("stone", new Texture("bricks/stone.png"));
		ResourceManager.loadResource("player_idle", new Texture("player/player_idle.png"));
		ResourceManager.loadResource("player_right_animation", new Texture("player/player_right_animation.png"));
		ResourceManager.loadResource("player_left_animation", new Texture("player/player_left_animation.png"));
		ResourceManager.loadResource("player_up_animation", new Texture("player/player_up_animation.png"));
		ResourceManager.loadResource("player_down_animation", new Texture("player/player_down_animation.png"));
		ResourceManager.loadResource("player_explosion_animation", new Texture("player/player_explosion_animation.png"));
		
		ResourceManager.loadResource("enemies", new TextureAtlas("enemy/enemies.pack"));
		
		ResourceManager.loadResource("bomb_idle", new Texture("player/bomb_idle.png"));
		ResourceManager.loadResource("bomb_animation", new Texture("player/bomb_animation.png"));
		ResourceManager.loadResource("bomb", Gdx.audio.newSound(Gdx.files.internal("sounds/bomb.wav")));
		
		ResourceManager.loadResource("explosion", new TextureAtlas(Gdx.files.internal("effects/explosion.atlas")));
		
		ResourceManager.loadResource("bomb_length", new Texture(Gdx.files.internal("powerups/bomb_length.png")));
		ResourceManager.loadResource("bomb", new Texture(Gdx.files.internal("powerups/bomb.png")));
	}
	
	/**
	 * Carga un recurso en memoria
	 * @param name
	 * @param texture
	 */
	private static void loadResource(String name, Texture texture) {
		
		textures.put(name, texture);
	}
	
	private static void loadResource(String name, Sound sound) {
		
		sounds.put(name, sound);
	}
	
	private static void loadResource(String name, TextureAtlas textureAtlas) {
		
		atlas.put(name, textureAtlas);
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
	 * Obtiene un recurso textura de memoria
	 * @param name
	 * @return
	 */
	public static TextureAtlas getTextureAtlas(String name) {
		
		return atlas.get(name);
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
