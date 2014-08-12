package org.sfsoft.drop;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

/**
 * Pantalla del juego, donde el usuario juega la partida
 * @author Santiago Faci
 * @version 2.0
 *
 */
public class GameScreen implements Screen, InputProcessor {

	final Drop juego;
	
	// Texturas e imágenes para los elementos del juego
	Texture spriteGota;
	Texture spriteCubo;
	Texture spriteRoca;
	Sound sonidoGota;
	Music musicaLluvia;
	Sound sonidoRoca;
	
	/*
	 * Representació den los elementos del juego como rectángulos
	 * Se utilizan para comprobar las colisiones entre los mismos
	 */
	Rectangle cubo;
	Array<Rectangle> gotas;
	Array<Rectangle> rocas;
	
	// Controla a que ritmo van apareciendo las gotas y las rocas
	long momentoUltimaGota;
	long momentoUltimaRoca;
	float tiempoJuego;
	
	// Indica si el juego está en pausa
	boolean pausa = false;
	
	OrthographicCamera camara;
	
	public GameScreen(Drop juego) {
		this.juego = juego;
		
		// Duración fija de la partida
		tiempoJuego = 50;
		
		// Carga las imágenes del juego
		spriteGota = new Texture(Gdx.files.internal("droplet.png"));
		spriteCubo = new Texture(Gdx.files.internal("bucket.png"));
		spriteRoca = new Texture(Gdx.files.internal("rock.png"));
		
		// Carga los sonidos del juego
		sonidoGota = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));
		musicaLluvia = Gdx.audio.newMusic(Gdx.files.internal("undertreeinrain.mp3"));
		sonidoRoca = Gdx.audio.newSound(Gdx.files.internal("rock.mp3"));
		
		// Inicia la música de fondo del juego en modo bucle
		musicaLluvia.setLooping(true);
		
		// Representa el cubo en el juego
		// Hay que tener el cuenta que la imagen del cubo es de 64x64 px
		cubo = new Rectangle();
		cubo.x = 1024 / 2 - 64 / 2;
		cubo.y = 20;
		cubo.width = 64;
		cubo.height = 64;
		
		// Genera la lluvia
		gotas = new Array<Rectangle>();
		generarLluvia();
		
		// Comienza lanzando la primera roca
		rocas = new Array<Rectangle>();
		lanzarRoca();
		
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
			comprobarInput();
			actualizar();
		}
		// La pantalla debe se redibujada aunque el juego está pausado
		dibujar();
	}
	
	/*
	 * Comprueba la entrada del usuario (teclado o pantalla si está en el móvil)
	 */
	private void comprobarInput() {
		
		/*
		 * Mueve el cubo pulsando en la pantalla
		 */
		if (Gdx.input.isTouched()) {
			Vector3 posicion = new Vector3();
			posicion.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			/*
			 * Transforma las coordenadas de la posición
			 * al sistema de coordenadas de la cámara
			 */
			//camara.unproject(posicion);
			cubo.x = posicion.x - 64 /2;
		}
		
		/*
		 * Mueve el cubo pulsando las teclas LEFT y RIGHT
		 */
		if (Gdx.input.isKeyPressed(Keys.LEFT))
			cubo.x -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.RIGHT))
			cubo.x += 200 * Gdx.graphics.getDeltaTime();
	}
	
	/*
	 * Actualiza la posición de todos los elementos
	 * del juego
	 */
	private void actualizar() {
			
		/*
		 * Comprueba que el cubo no se salga de los
		 * límites de la pantalla
		 */
		if (cubo.x < 0)
			cubo.x = 0;
		if (cubo.x > 1024 - 64)
			cubo.x = 1024 - 64;
		
		/*
		 * Genera nuevas gotas dependiendo del tiempo que ha
		 * pasado desde la última
		 */
		if (TimeUtils.nanoTime() - momentoUltimaGota > 100000000)
			generarLluvia();
		
		/*
		 * Genera nuevas rocas
		 */
		if (TimeUtils.nanoTime() - momentoUltimaRoca > 1000000000)
			lanzarRoca();
		
		/*
		 * Actualiza las posiciones de las gotas
		 * Si la gota llega al suelo se elimina
		 * Si la gota toca el cubo suena y se elimina
		 */
		Iterator<Rectangle> iter = gotas.iterator();
		while (iter.hasNext()) {
			Rectangle gota = iter.next();
			gota.y -= 200 * Gdx.graphics.getDeltaTime();
			if (gota.y + 64 < 0)
				iter.remove();
			if (gota.overlaps(cubo)) {
				sonidoGota.play();
				iter.remove();
				juego.gotasRecogidas++;
			}
		}
		
		/*
		 * Actualiza las posiciones de las rocas
		 * Si la roca llega al suelo se elimina
		 * Si la roca toca el cubo lo rompe y termina la partida
		 */
		Iterator<Rectangle> iterRoca = rocas.iterator();
		while (iterRoca.hasNext()) {
			Rectangle roca = iterRoca.next();
			roca.y -= 200 * Gdx.graphics.getDeltaTime();
			if (roca.y + 64 < 0)
				iterRoca.remove();
			/*
			 * Si la roca golpea el cubo se termina la partida
			 */
			if (roca.overlaps(cubo)) {
				sonidoRoca.play();
				pausa = true;
				Timer.schedule(new Task(){
				    @Override
				    public void run() {
				        dispose();
						juego.setScreen(new GameOverScreen(juego));
				    }
				}, 2);	// El retraso se indica en segundos
			}
		}
		
		// Actualiza el tiempo de juego
		tiempoJuego -= Gdx.graphics.getDeltaTime();
		if (tiempoJuego < 0) {
			dispose();
			juego.setScreen(new GameOverScreen(juego));
		}
	}
	
	/*
	 * Dibuja los elementos del juego en pantalla
	 */
	private void dibujar() {
		
		// Pinta la imágenes del juego en la pantalla
		/* setProjectionMatrix hace que el objeto utilice el 
		 * sistema de coordenadas de la cámara, que
		 * es ortogonal
		 * Es recomendable pintar todos los elementos del juego
		 * entras las mismas llamadas a begin() y end()
		 */
		//juego.spriteBatch.setProjectionMatrix(camara.combined);
		juego.spriteBatch.begin();
		juego.spriteBatch.draw(spriteCubo, cubo.x, cubo.y);
		for (Rectangle gota : gotas)
			juego.spriteBatch.draw(spriteGota, gota.x, gota.y);
		for (Rectangle roca : rocas)
			juego.spriteBatch.draw(spriteRoca, roca.x, roca.y);
		juego.fuente.draw(juego.spriteBatch, "Puntos: " + juego.gotasRecogidas, 1024 - 100, 768 - 50);
		juego.fuente.draw(juego.spriteBatch, "Tiempo: " + (int) (tiempoJuego), 1024 - 100, 768 - 80);
		juego.spriteBatch.end();
	}
	
	/**
	 * Genera una gota de lluvia en una posición aleatoria
	 * de la pantalla y anota el momento de generarse
	 */
	private void generarLluvia() {
		Rectangle gota = new Rectangle();
		gota.x = MathUtils.random(0, 1024 - 64);
		gota.y = 768;
		gota.width = 64;
		gota.height = 64;
		gotas.add(gota);
		momentoUltimaGota = TimeUtils.nanoTime();
	}
	
	/**
	 * Genera una roca y la deja caer
	 */
	private void lanzarRoca() {
		
		Rectangle roca = new Rectangle();
		roca.x = MathUtils.random(0, 1024 - 64);
		roca.y = 768;
		roca.width = 64;
		roca.height = 64;
		rocas.add(roca);
		momentoUltimaRoca = TimeUtils.nanoTime();
	}
	
	/*
	 * Método que se invoca cuando esta pantalla es
	 * la que se está mostrando
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		musicaLluvia.play();
	}

	/*
	 * Método que se invoca cuando esta pantalla
	 * deja de ser la principal
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override
	public void hide() {
		musicaLluvia.stop();
	}
	
	@Override
	public void dispose() {
		// Libera los recursos utilizados
		spriteGota.dispose();
		spriteCubo.dispose();
		spriteRoca.dispose();
		sonidoGota.dispose();
		musicaLluvia.dispose();
		sonidoRoca.dispose();
		
		gotas.clear();
		rocas.clear();
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
