package org.sfsoft.holalibgdx;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

/**
 * Clase principal de la versión de escritorio (PC) del juego
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class DesktopHolaLibgdx {

	public static void main(String[] args) {
		new LwjglApplication(new HolaLibgdx(), "Game", 1024, 768, false);
	}
}
