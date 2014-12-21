package org.sfsoft.frogger;

import org.sfsoft.frogger.util.Constants;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Clase principal de la versión de escritorio (PC) del juego
 * Esta clase hace de lanzadora (para la versión de PC en este caso)
 * del proyecto principal
 * @author Santiago Faci
 * @version curso 2014-2015
 *
 */
public class DesktopFrogger {

	public static void main(String[] args) {
		LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
		configuration.title = "Frogger";
		configuration.width = Constants.SCREEN_WIDTH;
		configuration.height = Constants.SCREEN_HEIGHT;
		//configuration.fullscreen = true;
				
		new LwjglApplication(new Frogger(), configuration);
	}
}
