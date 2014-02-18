package org.sfsfot.robin2dx;

import org.sfsoft.robin2dx.Robin2DX;
import org.sfsoft.robin2dx.utils.Constants;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopRobin2DX {

	public static void main(String[] args) {
		LwjglApplicationConfiguration configuracion = new LwjglApplicationConfiguration();
		configuracion.title = "Robin2DX";
		configuracion.width = Constants.SCREEN_WIDTH;
		configuracion.height = Constants.SCREEN_HEIGHT;
				
		new LwjglApplication(new Robin2DX(), configuracion);
	}
}