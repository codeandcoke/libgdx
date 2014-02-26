package org.sfaci.jumper2dx;

import org.sfaci.jumper2dx.Jumper2DX;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopJumper2DX {

	public static void main(String[] args) {
		LwjglApplicationConfiguration configuracion = new LwjglApplicationConfiguration();
		configuracion.title = "Jumper2DX";
		configuracion.width = 1024;
		configuracion.height = 600;
				
		new LwjglApplication(new Jumper2DX(), configuracion);
	}
}

