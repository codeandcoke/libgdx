package org.sfsoft.collisions;

import org.sfsoft.collisions.MyWorld;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Clase principal de la versión de escritorio (PC) del juego
 * @author Santiago Faci
 *
 */
public class DesktopMyWorld {

	public static void main(String[] args) {
		LwjglApplicationConfiguration configuracion = new LwjglApplicationConfiguration();
		configuracion.title = "Box2D Textures";
		configuracion.width = 1024;
		configuracion.height = 768;
				
		new LwjglApplication(new MyWorld(), configuracion);
	}
}
