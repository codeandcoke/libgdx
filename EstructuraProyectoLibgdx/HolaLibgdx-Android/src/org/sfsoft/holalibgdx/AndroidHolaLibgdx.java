package org.sfsoft.holalibgdx;

import com.badlogic.gdx.backends.android.AndroidApplication;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * Clase principal para la versión Android del proyecto
 * @author Santiago Faci
 *
 */
public class AndroidHolaLibgdx extends AndroidApplication {
	public void onCreate (android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialize(new HolaLibgdx(), false);
	}
}