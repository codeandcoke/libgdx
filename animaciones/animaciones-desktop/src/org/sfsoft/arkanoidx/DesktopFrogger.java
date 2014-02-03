package org.sfsoft.arkanoidx;

import org.sfsoft.frogger.Frogger;
import org.sfsoft.frogger.util.Constants;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Clase principal de la versi�n de escritorio (PC) del juego
 * @author Santiago Faci
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
