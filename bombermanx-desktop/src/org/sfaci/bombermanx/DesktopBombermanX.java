package org.sfaci.bombermanx;

import org.sfaci.bombermanx.Bombermanx;
import org.sfaci.bombermanx.util.Constants;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Clase principal de la versión de escritorio (PC) del juego
 * @author Santiago Faci
 *
 */
public class DesktopBombermanX {

	public static void main(String[] args) {
		LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
		configuration.title = "Bombermanx";

		configuration.width = Constants.SCREEN_WIDTH;
		configuration.height = Constants.SCREEN_HEIGHT;
		configuration.fullscreen = true;
				
		new LwjglApplication(new Bombermanx(), configuration);
	}
}
