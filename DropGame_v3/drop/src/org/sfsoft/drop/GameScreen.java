package org.sfsoft.drop;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import org.sfsoft.drop.managers.ResourceManager;
import org.sfsoft.drop.managers.SpriteManager;

/**
 * Pantalla del juego, donde el usuario juega la partida
 * @author Santiago Faci
 * @version curso 2014-2015
 *
 */
public class GameScreen implements Screen, InputProcessor {

	final Drop juego;
    SpriteManager spriteManager;
    ResourceManager resManager;
	
	// Indica si el juego está en pausa
	public boolean pausa = false;
	
	OrthographicCamera camara;
	
	public GameScreen(Drop juego) {
		this.juego = juego;

        resManager = new ResourceManager();
        resManager.loadAllResources();

        spriteManager = new SpriteManager(juego, this, resManager);

        // Crea la cámara y define la zona de visión del juego (toda la pantalla)
        camara = new OrthographicCamera();
        camara.setToOrtho(false, 1024, 768);

        Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public void render(float delta) {
		// Pinta el fondo de la pantalla de azul oscuro (RGB + alpha)
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		// Limpia la pantalla
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// Actualiza la cámara
		camara.update();
		
		/* Comprueba la entrada del usuario, actualiza
		 * la posición de los elementos del juego y
		 * dibuja en pantalla
		 */
		if (!pausa) {
			spriteManager.comprobarInput();
			spriteManager.actualizar();
		}
		// La pantalla debe se redibujada aunque el juego está pausado
		spriteManager.dibujar();
	}
	
	/*
	 * Método que se invoca cuando esta pantalla es
	 * la que se está mostrando
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
        if (spriteManager != null)
            spriteManager.show();
	}

	/*
	 * Método que se invoca cuando esta pantalla
	 * deja de ser la principal
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override
	public void hide() {
        if (spriteManager != null)
            spriteManager.hide();
	}
	
	@Override
	public void dispose() {
        if (spriteManager != null)
            spriteManager.dispose();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
		pausa = true;
	}

	@Override
	public void resume() {
		pausa = false;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		// Pone el juego en pausa
		if (keycode == Keys.P)
			pausa = !pausa;
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}