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
 * @version 1.0
 *
 */
public class ResourceManager {

	private static Map<String, TextureAtlas> atlas = new HashMap<String, TextureAtlas>();
	private static Map<String, Animation> animations = new HashMap<String, Animation>();
	
	public static void loadAllResources() {
		
		Texture.setEnforcePotImages(false);
		
		loadResource("robin", new TextureAtlas(Gdx.files.internal("robin/robin.pack")));
		
		loadResource("robin_right", new Animation(0.15f, 
			atlas.get("robin").findRegion("robin_right1"),
			atlas.get("robin").findRegion("robin_right2"),
			atlas.get("robin").findRegion("robin_right3"),
			atlas.get("robin").findRegion("robin_right4"),
			atlas.get("robin").findRegion("robin_right5")));
		loadResource("robin_left", new Animation(0.15f, 
			atlas.get("robin").findRegion("robin_left1"),
			atlas.get("robin").findRegion("robin_left2"),
			atlas.get("robin").findRegion("robin_left3"),
			atlas.get("robin").findRegion("robin_left4"),
			atlas.get("robin").findRegion("robin_left5")));
		loadResource("robin_up", new Animation(0.15f, 
			atlas.get("robin").findRegion("robin_up1"),
			atlas.get("robin").findRegion("robin_up2"),
			atlas.get("robin").findRegion("robin_up3"),
			atlas.get("robin").findRegion("robin_up4"),
			atlas.get("robin").findRegion("robin_up5")));
		loadResource("robin_down", new Animation(0.15f, 
			atlas.get("robin").findRegion("robin_down1"),
			atlas.get("robin").findRegion("robin_down2"),
			atlas.get("robin").findRegion("robin_down3"),
			atlas.get("robin").findRegion("robin_down4"),
			atlas.get("robin").findRegion("robin_down5")));
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
