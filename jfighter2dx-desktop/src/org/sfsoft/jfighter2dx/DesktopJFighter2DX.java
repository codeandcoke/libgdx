package org.sfsoft.jfighter2dx;

import org.sfsoft.jfighter2dx.util.Constants;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopJFighter2DX {

	public static void main(String[] args) {
		LwjglApplicationConfiguration configuracion = new LwjglApplicationConfiguration();
		configuracion.title = "JFighter2DX";
		configuracion.width = Constants.SCREEN_WIDTH;
		configuracion.height = Constants.SCREEN_HEIGHT;
		
		configuracion.fullscreen = true;
				
		new LwjglApplication(new JFighter2DX(), configuracion);
	}
}

