package org.sfsfot.robin2dx;

import org.sfsoft.robin2dx.Robin2DX;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopRobin2DX {

	public static void main(String[] args) {
		LwjglApplicationConfiguration configuracion = new LwjglApplicationConfiguration();
		configuracion.title = "Robin2DX";
		configuracion.width = 1024;
		configuracion.height = 600;
				
		new LwjglApplication(new Robin2DX(), configuracion);
	}
}