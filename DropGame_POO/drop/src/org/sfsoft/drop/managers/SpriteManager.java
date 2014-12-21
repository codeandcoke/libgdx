package org.sfsoft.drop.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import org.sfsoft.drop.Drop;
import org.sfsoft.drop.GameOverScreen;
import org.sfsoft.drop.GameScreen;
import org.sfsoft.drop.characters.*;

import static org.sfsoft.drop.util.Constants.SCREEN_WIDTH;
import static org.sfsoft.drop.util.Constants.SCREEN_HEIGHT;
import static org.sfsoft.drop.util.Constants.PLAYER_WIDTH;

import java.util.Iterator;

/**
 * Clase que gestiona la lógica del game
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class SpriteManager {

    ResourceManager resManager;
    Drop game;
    GameScreen gameScreen;

    Player player;
    Array<Item> items;
    Array<Enemy> enemies;

    // Controla a que ritmo van apareciendo las gotas y las rocas
    long lastItem;
    long lastEnemy;
    float gameTime;

    public SpriteManager(Drop game, GameScreen gameScreen, ResourceManager resManager) {

        this.game = game;
        this.gameScreen = gameScreen;
        this.resManager = resManager;

        init();
    }

    private void init() {

        // Duración fija de la partida
        gameTime = 50;

        Texture.setEnforcePotImages(false);

        // Representa el jugador en el juego
        // Hay que tener el cuenta que la imagen del player es de 64x64 px
        player = new Player(new Vector2(SCREEN_WIDTH / 2, 0),
                200f, resManager.getAtlas().findRegion("bucket"));

        // Inicia la música de fondo del game en modo bucle
        resManager.getMusic("bso").setLooping(true);

        // Genera la lluvia
        items = new Array<>();
        generateItems();

        // Comienza lanzando la primera roca
        enemies = new Array<>();
        generateEnemies();
    }

    public void draw() {

        game.spriteBatch.begin();
        player.render(game.spriteBatch);
        for (Item item : items)
            item.render(game.spriteBatch);
        for (Enemy enemy : enemies)
            enemy.render(game.spriteBatch);
        game.fuente.draw(game.spriteBatch, "Puntos: " + game.dropletCount, SCREEN_WIDTH - 100, SCREEN_HEIGHT - 50);
        game.fuente.draw(game.spriteBatch, "Tiempo: " + (int) (gameTime), SCREEN_WIDTH - 100, SCREEN_HEIGHT - 80);
        game.spriteBatch.end();
    }

    public void checkInput(float dt) {

        /*
		 * Mueve el player pulsando en la pantalla
		 */
        if (Gdx.input.isTouched()) {
            Vector2 position = new Vector2();
            position.set(Gdx.input.getX() - PLAYER_WIDTH / 2, 0);
            player.moveTo(position);
        }

		/*
		 * Mueve el player pulsando las teclas LEFT y RIGHT
		 */
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            player.move(new Vector2(-200 * dt, 0));
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            player.move(new Vector2(200 * dt, 0));
    }

    public void update(float dt) {

        /*
		 * Comprueba que el cubo no se salga de los
		 * límites de la pantalla
		 */
        if (player.position.x < 0)
            player.position.x = 0;
        if (player.position.x > SCREEN_WIDTH - PLAYER_WIDTH)
            player.position.x = SCREEN_WIDTH - PLAYER_WIDTH;

		/*
		 * Genera nuevos items dependiendo del tiempo que ha
		 * pasado desde la última
		 */
        if (TimeUtils.nanoTime() - lastItem > 100000000)
            generateItems();

		/*
		 * Genera nuevas rocas
		 */
        if (TimeUtils.nanoTime() - lastEnemy > 1000000000)
            generateEnemies();

		/*
		 * Actualiza las posiciones de los items
		 * Si el item llega al suelo se elimina
		 * Si el item toca el player suena y se elimina
		 */
        Iterator<Item> iter = items.iterator();
        while (iter.hasNext()) {
            Item item = iter.next();
            item.move(new Vector2(0, - dt));
            if (item.position.y + PLAYER_WIDTH < 0)
                iter.remove();
            if (item.rect.overlaps(player.rect)) {
                iter.remove();

                switch (item.getClass().getSimpleName()) {
                    case "Frog":
                        // Sonido de rana
                        resManager.getSound("frog").play();
                        break;
                    case "Droplet":
                        // Sonido de gota
                        resManager.getSound("waterdrop").play();
                        break;
                    default:
                }

                game.dropletCount += item.score;
            }
        }

		/*
		 * Actualiza las posiciones de las rocas
		 * Si la roca llega al suelo se elimina
		 * Si la roca toca el player lo rompe y termina la partida
		 */
        Iterator<Enemy> iterEnemies = enemies.iterator();
        while (iterEnemies.hasNext()) {
            Enemy enemy = iterEnemies.next();
            enemy.move(new Vector2(0, -dt));
            if (enemy.position.y + PLAYER_WIDTH < 0)
                iterEnemies.remove();
			/*
			 * Si la roca golpea el cubo se termina la partida
			 */
            if (enemy.rect.overlaps(player.rect)) {
                resManager.getSound("rock").play();
                gameScreen.pause = true;
                Timer.schedule(new Timer.Task(){
                    @Override
                    public void run() {
                        dispose();
                        game.setScreen(new GameOverScreen(game));
                    }
                }, 2);	// El retraso se indica en segundos
            }
        }

        // Actualiza el tiempo de game
        gameTime -= dt;
        if (gameTime < 0) {
            dispose();
            game.setScreen(new GameOverScreen(game));
        }
    }

    private void generateItems() {

        int n = MathUtils.random(0, 1000);
        Item item;
        int x = MathUtils.random(0, SCREEN_WIDTH - PLAYER_WIDTH);

        if (n < 30) {
            item = new Frog(new Vector2(x, SCREEN_HEIGHT),
                    400f, resManager.getAtlas().findRegion("frog"), 5);
        }
        else {
            item = new Droplet(new Vector2(x, SCREEN_HEIGHT),
                    200f, resManager.getAtlas().findRegion("droplet"), 1);
        }

        items.add(item);
        lastItem = TimeUtils.nanoTime();
    }

    private void generateEnemies() {

        int x = MathUtils.random(0, SCREEN_WIDTH - PLAYER_WIDTH);
        Enemy enemy = new Stone(new Vector2(x, SCREEN_HEIGHT),
                200f, resManager.getAtlas().findRegion("rock"));

        enemies.add(enemy);
        lastEnemy = TimeUtils.nanoTime();
    }

    public void show() {
        resManager.getMusic("bso").play();
    }

    public void hide() {
        if (resManager.getMusic("bso") != null)
            resManager.getMusic("bso").stop();
    }

    public void dispose() {
        // Libera los recursos utilizados
        resManager.dispose();

        items.clear();
        enemies.clear();
    }
}
