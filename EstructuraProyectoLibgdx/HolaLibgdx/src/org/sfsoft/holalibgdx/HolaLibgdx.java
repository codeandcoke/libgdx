package org.sfsoft.holalibgdx;

import com.badlogic.gdx.ApplicationListener;

/**
 * Clase principal del proyecto principal del juego
 * @author Santiago Faci
 *
 */
public class HolaLibgdx implements ApplicationListener {

	/*
	 * Método invocado en el momento de crearse la aplicación
	 * @see com.badlogic.gdx.ApplicationListener#create()
	 */
	@Override
	public void create() {
	}

	/*
	 * Método invocado cuando se destruye la aplicación
	 * Siempre va precedido de una llamada a 'pause()'
	 * @see com.badlogic.gdx.ApplicationListener#dispose()
	 */
	@Override
	public void dispose() {
	}

	/*
	 * En Android se invoca a este método cuando la aplicación
	 * pasa a segundo plano.
	 * En la versión de PC, se invoca siempre antes de invocar a 'dispose()'
	 * Es el momento de almacenar el estado de la partida
	 * @see com.badlogic.gdx.ApplicationListener#pause()
	 */
	@Override
	public void pause() {
	}

	/*
	 * Método que se invoca cada vez que hay que renderizar
	 * Es el método donde se actualiza también la lógica del juego
	 * @see com.badlogic.gdx.ApplicationListener#pause()
	 */
	@Override
	public void render() {
	}

	/*
	 * Método invocado cada vez que se redimensiona la pantalla sin pausar el juego
	 * También se invoca justo después de 'create()'
	 * @see com.badlogic.gdx.ApplicationListener#dispose()
	 */
	@Override
	public void resize(int arg0, int arg1) {
	}

	/*
	 * Sólo se invoca en Android, cuando la aplicación continua
	 * después de haber sido pausada
	 * @see com.badlogic.gdx.ApplicationListener#resume()
	 */
	@Override
	public void resume() {
	}
}
