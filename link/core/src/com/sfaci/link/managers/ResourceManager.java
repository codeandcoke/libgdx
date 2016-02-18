package com.sfaci.link.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase encargada de cargar los recursos del juego
 * @author Santiago Faci
 * @version curso 2014-2015
 *
 */
public class ResourceManager {

	private static TextureAtlas atlas;
	
	public static void loadAllResources() {
		
		atlas = new TextureAtlas(Gdx.files.internal("robin/link.pack"));
	}
	
	public static TextureAtlas getAtlas() {
		return atlas;
	}
}
