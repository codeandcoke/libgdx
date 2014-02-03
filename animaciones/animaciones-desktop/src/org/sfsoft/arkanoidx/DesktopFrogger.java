package org.sfsoft.arkanoidx;

import org.sfsoft.frogger.Frogger;
import org.sfsoft.frogger.util.Constants;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Clase principal de la versión de escritorio (PC) del juego
 * @author Santiago Faci
 *
 */
public class DesktopFrogger {

	public static void main(String[] args) {
		LwjglApplicationConfiguration configuracion = new LwjglApplicationConfiguration();
		configuracion.title = "Frogger";

		configuracion.width = Constants.SCREEN_WIDTH;
		configuracion.height = Constants.SCREEN_HEIGHT;
				
		new LwjglApplication(new Frogger(), configuracion);
	}
}
