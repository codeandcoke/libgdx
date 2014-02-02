package org.sfsoft.jfighter2dx;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopJFighter2DX {

	public static void main(String[] args) {
		LwjglApplicationConfiguration configuracion = new LwjglApplicationConfiguration();
		configuracion.title = "JFighter2DX";
		configuracion.width = 1024;
		configuracion.height = 600;
				
		new LwjglApplication(new JFighter2DX(), configuracion);
	}
}

