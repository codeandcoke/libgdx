package org.sfsoft.drop;

import java.util.Iterator;
import java.util.Random;

import org.sfsoft.drop.characters.Droplet;
import org.sfsoft.drop.characters.Enemy;
import org.sfsoft.drop.characters.Frog;
import org.sfsoft.drop.characters.Item;
import org.sfsoft.drop.characters.Player;
import org.sfsoft.drop.characters.Stone;
import org.sfsoft.drop.util.Constants;

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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

/**
 * Pantalla del juego, donde el usuario juega la partida
 * @author Santiago Faci
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
	 * Representación de los elementos del juego como rectángulos
	 * Se utilizan para comprobar las colisiones entre los mismos
	 */
	Player cubo;
	Array<Enemy> enemies;
	Array<Item> items;
	
	// Controla a que ritmo van apareciendo las gotas y las rocas
	long lastItem;
	long lastEnemy;
	float tiempoJuego;
	
	// Indica si el juego está en pausa
	boolean pausa = false;
	
	OrthographicCamera camara;
	
	public GameScreen(Drop juego) {
		this.juego = juego;
		
		// Duración fija de la partida
		tiempoJuego = 50;
		
		Texture.setEnforcePotImages(false);
		// Carga las imágenes del juego
		
		cubo = new Player(new Vector2(Constants.SCREEN_WIDTH / 2, 0), 
			200f, new Texture(Gdx.files.internal("bucket.png")));
		
		// Carga los sonidos del juego
		sonidoGota = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));
		musicaLluvia = Gdx.audio.newMusic(Gdx.files.internal("undertreeinrain.mp3"));
		sonidoRoca = Gdx.audio.newSound(Gdx.files.internal("rock.mp3"));
		
		// Inicia la música de fondo del juego en modo bucle
		musicaLluvia.setLooping(true);
		
		// Representa el cubo en el juego
		// Hay que tener el cuenta que la imagen del cubo es de 64x64 px
		
		
		// Genera la lluvia
		items = new Array<Item>();
		generarItems();
		
		// Comienza lanzando la primera roca
		enemies = new Array<Enemy>();
		generarEnemigos();
		
		// Crea la cámara y define la zona de visión del juego (toda la pantalla)
		camara = new OrthographicCamera();
		camara.setToOrtho(false, 1024, 768);
		
		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public void render(float dt) {
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
			comprobarInput(dt);
			actualizar(dt);
		}
		// La pantalla debe se redibujada aunque el juego esté pausado
		dibujar();
	}
	
	/*
	 * Comprueba la entrada del usuario (teclado o pantalla si está en el móvil)
	 */
	private void comprobarInput(float dt) {
		
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
			cubo.moveTo(new Vector2(posicion.x - 64 /2, 
				cubo.position.y));
		}
		
		/*
		 * Mueve el cubo pulsando las teclas LEFT y RIGHT
		 */
		if (Gdx.input.isKeyPressed(Keys.LEFT))
			cubo.move(new Vector2(-dt, 0));
		if (Gdx.input.isKeyPressed(Keys.RIGHT))
			cubo.move(new Vector2(dt, 0));
	}
	
	/*
	 * Actualiza la posición de todos los elementos
	 * del juego
	 */
	private void actualizar(float dt) {
			
		/*
		 * Comprueba que el cubo no se salga de los
		 * límites de la pantalla
		 */
		if (cubo.position.x < 0)
			cubo.position.x = 0;
		if (cubo.position.x > Constants.SCREEN_WIDTH - 64)
			cubo.position.x = Constants.SCREEN_WIDTH - 64;
		
		/*
		 * Genera nuevas gotas dependiendo del tiempo que ha
		 * pasado desde la última
		 */
		if (TimeUtils.nanoTime() - lastItem > 100000000)
			generarItems();
		
		/*
		 * Genera nuevas rocas
		 */
		if (TimeUtils.nanoTime() - lastEnemy > 1000000000)
			generarEnemigos();
		
		/*
		 * Actualiza las posiciones de las gotas
		 * Si la gota llega al suelo se elimina
		 * Si la gota toca el cubo suena y se elimina
		 */
		Iterator<Item> iter = items.iterator();
		while (iter.hasNext()) {
			Item item = iter.next();
			item.move(new Vector2(0, -dt));
			if (item.position.y + 64 < 0)
				iter.remove();
			if (item.rect.overlaps(cubo.rect)) {
				
				iter.remove();
				
				switch (item.getClass().getSimpleName()) {
				case "Frog":
					// Sonido de rana
					
					break;
				case "Droplet":
					// Sonido de gota
					sonidoGota.play();
					break;
				default:
				}
				
				juego.gotasRecogidas += item.score;
			}
		}
		
		/*
		 * Actualiza las posiciones de las rocas
		 * Si la roca llega al suelo se elimina
		 * Si la roca toca el cubo lo rompe y termina la partida
		 */
		Iterator<Enemy> iterEnemies = enemies.iterator();
		while (iterEnemies.hasNext()) {
			Enemy enemy = iterEnemies.next();
			enemy.move(new Vector2(0, -dt));
			if (enemy.position.y + 64 < 0)
				iterEnemies.remove();
			/*
			 * Si la roca golpea el cubo se termina la partida
			 */
			if (enemy.rect.overlaps(cubo.rect)) {
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
		cubo.render(juego.spriteBatch);
		for (Item item : items)
			item.render(juego.spriteBatch);
		for (Enemy enemy : enemies)
			enemy.render(juego.spriteBatch);
		juego.fuente.draw(juego.spriteBatch, "Puntos: " + juego.gotasRecogidas, 1024 - 100, 768 - 50);
		juego.fuente.draw(juego.spriteBatch, "Tiempo: " + (int) (tiempoJuego), 1024 - 100, 768 - 80);
		juego.spriteBatch.end();
	}
	
	/**
	 * Genera una gota de lluvia en una posición aleatoria
	 * de la pantalla y anota el momento de generarse
	 */
	private void generarItems() {
		
		int n = MathUtils.random(0, 1000);
		Item item = null;
		int x = MathUtils.random(0, Constants.SCREEN_WIDTH - 64);
		
		if (n < 30) {
			item = new Frog(new Vector2(x, Constants.SCREEN_HEIGHT),
				400f, new Texture(Gdx.files.internal("frog.png")), 5);
		}
		else {
			item = new Droplet(new Vector2(x, Constants.SCREEN_HEIGHT),
				200f, new Texture(Gdx.files.internal("droplet.png")), 1);
		}
		
		items.add(item);
		lastItem = TimeUtils.nanoTime();
	}
	
	/**
	 * Genera una roca y la deja caer
	 */
	private void generarEnemigos() {
		
		int x = MathUtils.random(0, 1024 - 64);
		Enemy enemy = new Stone(new Vector2(x, Constants.SCREEN_HEIGHT),
			200f, new Texture(Gdx.files.internal("rock.png")));
		
		enemies.add(enemy);
		lastEnemy = TimeUtils.nanoTime();
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
	 * Método que se invoca cuando está pantalla
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
		sonidoGota.dispose();
		musicaLluvia.dispose();
		sonidoRoca.dispose();
		
		items.clear();
		enemies.clear();
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
