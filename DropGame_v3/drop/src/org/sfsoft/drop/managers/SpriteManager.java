package org.sfsoft.drop.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import org.sfsoft.drop.Drop;
import org.sfsoft.drop.GameOverScreen;
import org.sfsoft.drop.GameScreen;

import java.util.Iterator;

/**
 * Clase encargada de la lógica del juego
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class SpriteManager {

    ResourceManager resManager;
    Drop juego;
    GameScreen gameScreen;

    // Texturas e imágenes para los elementos del juego
    TextureRegion spriteGota;
    TextureRegion spriteCubo;
    TextureRegion spriteRoca;
    Sound sonidoGota;
    Music musicaLluvia;
    Sound sonidoRoca;

    /*
     * Representación de los elementos del juego como rectángulos
     * Se utilizan para comprobar las colisiones entre los mismos
     */
    Rectangle cubo;
    Array<Rectangle> gotas;
    Array<Rectangle> rocas;

    // Controla a que ritmo van apareciendo las gotas y las rocas
    long momentoUltimaGota;
    long momentoUltimaRoca;
    float tiempoJuego;

    public SpriteManager(Drop juego, GameScreen gameScreen, ResourceManager resManager) {

        this.juego = juego;
        this.gameScreen = gameScreen;
        this.resManager = resManager;

        init();
    }

    private void init() {

        // Duración fija de la partida
        tiempoJuego = 50;

        // Carga las imágenes del juego
        spriteGota = resManager.getAtlas().findRegion("droplet");
        spriteCubo = resManager.getAtlas().findRegion("bucket");
        spriteRoca = resManager.getAtlas().findRegion("rock");

        // Carga los sonidos del juego
        sonidoGota = resManager.getSonido("waterdrop");
        musicaLluvia = resManager.getMusica("bso");
        sonidoRoca = resManager.getSonido("rock");

        // Inicia la música de fondo del juego en modo bucle
        musicaLluvia.play();
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
    }

    public void dibujar() {

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

    public void comprobarInput() {

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
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            cubo.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            cubo.x += 200 * Gdx.graphics.getDeltaTime();
    }

    public void actualizar() {

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
                gameScreen.pausa = true;
                Timer.schedule(new Timer.Task() {
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

    private void generarLluvia() {

        Rectangle gota = new Rectangle();
        gota.x = MathUtils.random(0, 1024 - 64);
        gota.y = 768;
        gota.width = 64;
        gota.height = 64;
        gotas.add(gota);
        momentoUltimaGota = TimeUtils.nanoTime();
    }

    private void lanzarRoca() {

        Rectangle roca = new Rectangle();
        roca.x = MathUtils.random(0, 1024 - 64);
        roca.y = 768;
        roca.width = 64;
        roca.height = 64;
        rocas.add(roca);
        momentoUltimaRoca = TimeUtils.nanoTime();
    }

    public void show() {
        musicaLluvia.play();
    }

    public void hide() {
        musicaLluvia.stop();
    }

    public void dispose() {
        // Libera los recursos utilizados
        resManager.dispose();

        gotas.clear();
        rocas.clear();
    }
}
