package org.sfsoft.arkanoidx;

import org.sfsoft.arkanoidx.util.Constants;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Clase principal de la versión de escritorio (PC) del game
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class DesktopArkanoidx {

	public static void main(String[] args) {
		LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
		configuration.title = "Arkanoidx";

		configuration.width = Constants.SCREEN_WIDTH;
		configuration.height = Constants.SCREEN_HEIGHT;
				
		new LwjglApplication(new Arkanoidx(), configuration);
	}
}
