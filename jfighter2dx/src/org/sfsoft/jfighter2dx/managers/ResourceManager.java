package org.sfsoft.jfighter2dx.managers;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Gestiona todos los recursos del juego (imágenes, animaciones, sonidos, . . .)
 * En el caso de este juego, al comienzo del desarrollo, reducía en más de un 100% el uso de CPU con respecto a cargar los recursos
 * cada vez que se necesitaban
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class ResourceManager {

	private static Map<String, Texture> textures = new HashMap<String, Texture>();
	private static Map<String, Animation> animations = new HashMap<String, Animation>();
	private static Map<String, Sound> sounds = new HashMap<String, Sound>();
	
	public static void loadAllResources() {
		
		Texture.setEnforcePotImages(false);
		// Imágenes
		ResourceManager.loadResource("background", new Texture("backgrounds/farback.png"));
		ResourceManager.loadResource("ship_bullet", new Texture("ship/bullet.png"));
		ResourceManager.loadResource("shooter_bullet", new Texture("enemy/bullet.png"));
		ResourceManager.loadResource("bomb", new Texture("items/bomb.png"));
		ResourceManager.loadResource("shield", new Texture("items/shield.png"));
		
		// Onscreen information
		ResourceManager.loadResource("live", new Texture("ship/live.png"));
		ResourceManager.loadResource("bomb_score", new Texture("items/bomb_score.png"));
		ResourceManager.loadResource("missile_score", new Texture("items/missile_score.png"));
		
		// Animaciones
		ResourceManager.loadResource("ship", new Animation(0.15f, new TextureRegion[]{
				new Sprite(new Texture(Gdx.files.internal("ship/f1.png"))), new Sprite(new Texture(Gdx.files.internal("ship/f2.png"))), 
				new Sprite(new Texture(Gdx.files.internal("ship/f3.png"))), new Sprite(new Texture(Gdx.files.internal("ship/f4.png")))}));
		ResourceManager.loadResource("small_enemy", new Animation(0.15f, new TextureRegion[]{
				new Sprite(new Texture(Gdx.files.internal("enemy/e_f1.png"))), new Sprite(new Texture(Gdx.files.internal("enemy/e_f2.png"))), 
				new Sprite(new Texture(Gdx.files.internal("enemy/e_f3.png"))), new Sprite(new Texture(Gdx.files.internal("enemy/e_f4.png"))),
				new Sprite(new Texture(Gdx.files.internal("enemy/e_f5.png"))), new Sprite(new Texture(Gdx.files.internal("enemy/e_f6.png")))}));
		ResourceManager.loadResource("stone", new Animation(0.15f, new TextureRegion[]{
				new Sprite(new Texture(Gdx.files.internal("enemy/stone1.png"))), new Sprite(new Texture(Gdx.files.internal("enemy/stone2.png"))), 
				new Sprite(new Texture(Gdx.files.internal("enemy/stone3.png"))), new Sprite(new Texture(Gdx.files.internal("enemy/stone4.png"))),
				new Sprite(new Texture(Gdx.files.internal("enemy/stone5.png"))), new Sprite(new Texture(Gdx.files.internal("enemy/stone6.png"))), 
				new Sprite(new Texture(Gdx.files.internal("enemy/stone7.png"))), new Sprite(new Texture(Gdx.files.internal("enemy/stone8.png"))),
				new Sprite(new Texture(Gdx.files.internal("enemy/stone9.png"))), new Sprite(new Texture(Gdx.files.internal("enemy/stone10.png"))), 
				new Sprite(new Texture(Gdx.files.internal("enemy/stone11.png"))), new Sprite(new Texture(Gdx.files.internal("enemy/stone12.png"))),
				new Sprite(new Texture(Gdx.files.internal("enemy/stone13.png"))), new Sprite(new Texture(Gdx.files.internal("enemy/stone14.png"))), 
				new Sprite(new Texture(Gdx.files.internal("enemy/stone15.png"))), new Sprite(new Texture(Gdx.files.internal("enemy/stone16.png")))}));
		ResourceManager.loadResource("pursuer_enemy", ResourceManager.createAnimationFromSpriteSheet("enemy/pursuer.png", 6));
		ResourceManager.loadResource("shooter_enemy", ResourceManager.createAnimationFromSpriteSheet("enemy/shooter.png", 6));
		ResourceManager.loadResource("big_enemy", ResourceManager.createAnimationFromSpriteSheet("enemy/big.png", 6));
		ResourceManager.loadResource("missile", ResourceManager.createAnimationFromSpriteSheet("items/missile.png", 16));
		ResourceManager.loadResource("explosion", new Animation(0.15f, new TextureRegion[]{
				new Sprite(new Texture(Gdx.files.internal("explosion/explosion0000.png"))), new Sprite(new Texture(Gdx.files.internal("explosion/explosion0001.png"))), 
				new Sprite(new Texture(Gdx.files.internal("explosion/explosion0002.png"))), new Sprite(new Texture(Gdx.files.internal("explosion/explosion0003.png"))),
				new Sprite(new Texture(Gdx.files.internal("explosion/explosion0004.png"))), new Sprite(new Texture(Gdx.files.internal("explosion/explosion0005.png"))), 
				new Sprite(new Texture(Gdx.files.internal("explosion/explosion0006.png"))), new Sprite(new Texture(Gdx.files.internal("explosion/explosion0007.png"))),
				new Sprite(new Texture(Gdx.files.internal("explosion/explosion0008.png"))), new Sprite(new Texture(Gdx.files.internal("explosion/explosion0009.png"))), 
				new Sprite(new Texture(Gdx.files.internal("explosion/explosion0010.png"))), new Sprite(new Texture(Gdx.files.internal("explosion/explosion0011.png"))),
				new Sprite(new Texture(Gdx.files.internal("explosion/explosion0012.png"))), new Sprite(new Texture(Gdx.files.internal("explosion/explosion0013.png"))), 
				new Sprite(new Texture(Gdx.files.internal("explosion/explosion0014.png"))), new Sprite(new Texture(Gdx.files.internal("explosion/explosion0015.png"))),
				new Sprite(new Texture(Gdx.files.internal("explosion/explosion0016.png"))), new Sprite(new Texture(Gdx.files.internal("explosion/explosion0017.png"))),
				new Sprite(new Texture(Gdx.files.internal("explosion/explosion0018.png"))), new Sprite(new Texture(Gdx.files.internal("explosion/explosion0019.png"))), 
				new Sprite(new Texture(Gdx.files.internal("explosion/explosion0020.png"))), new Sprite(new Texture(Gdx.files.internal("explosion/explosion0021.png"))),
				new Sprite(new Texture(Gdx.files.internal("explosion/explosion0022.png"))), new Sprite(new Texture(Gdx.files.internal("explosion/explosion0023.png"))), 
				new Sprite(new Texture(Gdx.files.internal("explosion/explosion0024.png"))), new Sprite(new Texture(Gdx.files.internal("explosion/explosion0025.png"))),
				new Sprite(new Texture(Gdx.files.internal("explosion/explosion0026.png"))), new Sprite(new Texture(Gdx.files.internal("explosion/explosion0027.png"))),
				new Sprite(new Texture(Gdx.files.internal("explosion/explosion0028.png"))), new Sprite(new Texture(Gdx.files.internal("explosion/explosion0029.png"))), 
				new Sprite(new Texture(Gdx.files.internal("explosion/explosion0030.png")))}));
		/*
		// SpriteSheets
		ResourceManager.loadResource("block", new SpriteSheet(new Image("res/items/blocks1.png"), 32, 32, 2, 2));
		*/
		// Sonidos
		ResourceManager.loadResource("shoot", Gdx.audio.newSound(Gdx.files.internal("sounds/disparo.mp3")));
		ResourceManager.loadResource("explosion", Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav")));
		ResourceManager.loadResource("item", Gdx.audio.newSound(Gdx.files.internal("sounds/item.wav")));
		ResourceManager.loadResource("buzz", Gdx.audio.newSound(Gdx.files.internal("sounds/buzz.wav")));
	}
	
	/**
	 * Crea una animación a partir de un SpriteSheet
	 * @param spriteSheetName El nombre del fichero que contiene el SpriteSheet
	 * @param frameCount El número de frames que componen el SpriteSheet
	 * @return La animación
	 */
	public static Animation createAnimationFromSpriteSheet(String spriteSheetName, int frameCount) {
		
		Texture spriteSheet = new Texture(Gdx.files.internal(spriteSheetName));
		TextureRegion[][] sheetFrames = TextureRegion.split(spriteSheet, spriteSheet.getWidth(), spriteSheet.getHeight() / frameCount);
		TextureRegion[] frames = new TextureRegion[frameCount];
		for (int i = 0; i < frameCount; i++) {
			frames[i] = sheetFrames[i][0];
		}
		
		return new Animation(0.15f, frames);
	}
	
	/**
	 * Carga un recurso de imagen en memoria
	 * @param name
	 * @param resource
	 */
	public static void loadResource(String name, Texture resource) {
		
		textures.put(name, resource);
	}
	
	/**
	 * Carga un recurso de animación en memoria
	 * @param name
	 * @param resource
	 */
	public static void loadResource(String name, Animation resource) {
		
		animations.put(name, resource);
	}
	
	/**
	 * Carga un recurso de sonido en memoria
	 * @param name
	 * @param sound
	 */
	public static void loadResource(String name, Sound sound) {
		
		sounds.put(name, sound);
	}
	
	/**
	 * Obtiene un recurso de imagen de memoria
	 * @param name
	 * @return
	 */
	public static Texture getTexture(String name) {
		
		return textures.get(name);
	}
	
	/**
	 * Obtiene un recurso de animación de memoria
	 * @param name
	 * @return
	 */
	public static Animation getAnimation(String name) {
		
		return animations.get(name);
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
