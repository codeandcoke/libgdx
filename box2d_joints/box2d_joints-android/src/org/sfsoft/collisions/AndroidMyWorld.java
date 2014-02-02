package org.sfsoft.collisions;

import org.sfsoft.collisions.MyWorld;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * Clase principal para la versión Android del proyecto
 * @author Santiago Faci
 *
 */
public class AndroidMyWorld extends AndroidApplication {
	public void onCreate (android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration configuracion = new AndroidApplicationConfiguration();
		configuracion.useAccelerometer = false;
		configuracion.useCompass = false;
		
		initialize(new MyWorld(), configuracion);
	}
}