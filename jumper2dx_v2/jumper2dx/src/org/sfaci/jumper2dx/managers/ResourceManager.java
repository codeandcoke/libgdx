package org.sfaci.jumper2dx.managers;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/**
 * Gestor de recursos de sonido e imagen del juego
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class ResourceManager {

	private static Map<String, Sound> sounds = new HashMap<String, Sound>();
	private static Map<String, Texture> textures = new HashMap<String, Texture>();
	
	/**
	 * Carga los sonidos del juego
	 */
	public static void loadSounds() {
		
		// Carga los sonidos del juego
		loadSound("coin",Gdx.audio.newSound(Gdx.files.internal("sounds/coin.wav")));
		loadSound("jump", Gdx.audio.newSound(Gdx.files.internal("sounds/jump.wav")));
		loadSound("down", Gdx.audio.newSound(Gdx.files.internal("sounds/player_down.wav")));
		loadSound("game_over", Gdx.audio.newSound(Gdx.files.internal("sounds/game_over.wav")));
		loadSound("level_clear", Gdx.audio.newSound(Gdx.files.internal("sounds/level_clear.wav")));
		loadSound("kick", Gdx.audio.newSound(Gdx.files.internal("sounds/kick.wav")));
		loadSound("level1", Gdx.audio.newSound(Gdx.files.internal("sounds/level1.mp3")));
		loadSound("level2", Gdx.audio.newSound(Gdx.files.internal("sounds/level2.mp3")));
		loadSound("level3", Gdx.audio.newSound(Gdx.files.internal("sounds/level3.mp3")));
		loadSound("item", Gdx.audio.newSound(Gdx.files.internal("sounds/item_appears.wav")));
		loadSound("up", Gdx.audio.newSound(Gdx.files.internal("sounds/1up.wav")));
	}
	
	/**
	 * Carga las texturas del juego
	 */
	public static void loadTextures() {
		
		// Para que no nos obligue a que las imágenes tengan tamaños potencia de 2
		Texture.setEnforcePotImages(false);
		
		// Carga algunas textura para la información en pantalla
		loadTexture("item_coin", new Texture(Gdx.files.internal("items/coin.png")));
		loadTexture("item_life", new Texture(Gdx.files.internal("items/life.png")));
		loadTexture("item_cloud", new Texture(Gdx.files.internal("items/cloud.png")));
	}
	
	/**
	 * Carga una textura
	 * @param name Su nombre
	 * @param texture La textura
	 */
	private static void loadTexture(String name, Texture texture) {
		textures.put(name, texture);
	}
	
	/**
	 * Devuelve una textura
	 * @param name Su nombre
	 * @return La textura
	 */
	public static Texture getTexture(String name) {
		return textures.get(name);
	}
	
	/**
	 * Carga un sonido
	 * @param name Su nombre
	 * @param sound El objeto de audio
	 */
	private static void loadSound(String name, Sound sound) {
		sounds.put(name, sound);
	}
	
	/**
	 * Obtiene un sonido del juego
	 * @param name Su nombre
	 * @return El sonido
	 */
	public static Sound getSound(String name) {
		return sounds.get(name);
	}
}
