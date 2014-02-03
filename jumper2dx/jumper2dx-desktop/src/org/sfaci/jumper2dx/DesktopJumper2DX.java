package org.sfaci.jumper2dx;

import org.sfaci.jumper2dx.Jumper2DX;
import org.sfaci.jumper2dx.util.Constants;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopJumper2DX {

	public static void main(String[] args) {
		LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
		configuration.title = "Jumper2DX";
		configuration.width = Constants.SCREEN_WIDTH;
		configuration.height = Constants.SCREEN_HEIGHT;
		configuration.fullscreen = true;
				
		new LwjglApplication(new Jumper2DX(), configuration);
	}
}

