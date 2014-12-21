package org.sfsoft.robin2dx.managers;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Clase encargada de cargar los recursos del juego
 * @author Santiago Faci
 * @version curso 2014-2015
 *
 */
public class ResourceManager {

	private static Map<String, TextureAtlas> atlas = new HashMap<String, TextureAtlas>();
	private static Map<String, Animation> animations = new HashMap<String, Animation>();
	
	public static void loadAllResources() {
		
		Texture.setEnforcePotImages(false);
		
		loadResource("robin", new TextureAtlas(Gdx.files.internal("robin/robin.pack")));
		
		loadResource("robin_right", new Animation(0.15f, atlas.get("robin").findRegions("robin_right")));
		loadResource("robin_left", new Animation(0.15f, atlas.get("robin").findRegions("robin_left")));
		loadResource("robin_up", new Animation(0.15f, atlas.get("robin").findRegions("robin_up")));
		loadResource("robin_down", new Animation(0.15f, atlas.get("robin").findRegions("robin_down")));
	}
	
	private static void loadResource(String name, TextureAtlas textureAtlas) {
		atlas.put(name, textureAtlas);
	}
	
	private static void loadResource(String name, Animation animation) {
		animations.put(name, animation);
	}
	
	public static TextureAtlas getAtlas(String name) {
		return atlas.get(name);
	}
	
	public static Animation getAnimation(String name) {
		return animations.get(name);
	}
}
